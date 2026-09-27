package yaz.phone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import yaz.phone.model.AppUpdateConfig
import yaz.phone.network.AppNotifications
import yaz.phone.network.AppOpenManager
import yaz.phone.network.BroadcastCheckWorker
import yaz.phone.network.FlashRewardManager
import yaz.phone.network.InterstitialManager
import yaz.phone.network.NativeAdManager
import yaz.phone.network.NotificationStore
import yaz.phone.network.RewardedManager
import yaz.phone.network.VercelUpdateManager
import yaz.phone.ui.components.ForceUpdateScreen
import yaz.phone.ui.components.MaintenanceScreen
import yaz.phone.ui.components.MandatoryUpdateDialog
import yaz.phone.ui.components.SplashScreen
import yaz.phone.ui.screens.*
import yaz.phone.ui.theme.BackgroundLight
import yaz.phone.ui.theme.PhoneTrafficTheme
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var updateManager: VercelUpdateManager

    private val notifPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateManager = VercelUpdateManager(applicationContext)
        // طلب إذن الإشعارات (أندرويد 13+) + إنشاء القناة
        AppNotifications.ensureChannel(this)
        if (android.os.Build.VERSION.SDK_INT >= 33 && !AppNotifications.hasPermission(this)) {
            notifPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
        // جدولة فاحص الخلفية: إشعارات شريط التلفون كل 15 دقيقة حتى والتطبيق مغلق
        BroadcastCheckWorker.schedule(this)
        // تهيئة إعلانات AdMob (عرض حقيقي داخل التطبيق)
        MobileAds.initialize(this) {}
        // تحميل مسبق: بيني + فتح التطبيق + مكافأة + نيتف + بوابة الفلاش
        InterstitialManager.preload(this)
        AppOpenManager.preload(this)
        RewardedManager.preload(this)
        FlashRewardManager.preload(this)
        NativeAdManager.preload(this, "posts")
        NativeAdManager.preload(this, "flash")
        enableEdgeToEdge()

        setContent {
            PhoneTrafficTheme {
                val config by updateManager.configState.collectAsState()
                var currentScreen by remember { mutableStateOf("home") }
                var previousScreenBeforeBrowser by remember { mutableStateOf("home") }
                var activeBrowserUrl by remember { mutableStateOf("") }
                var activeBrowserTitle by remember { mutableStateOf("") }
                var showOptionalUpdateDialog by remember { mutableStateOf(false) }
                var minTimePassed by remember { mutableStateOf(false) }
                var checkDone by remember { mutableStateOf(false) }
                var appOpenShown by remember { mutableStateOf(false) }
                val isChecking by updateManager.isChecking.collectAsState()

                // شاشة تحميل: أقل مدة 1.5 ثانية + انتظار نتيجة فحص السيرفر
                LaunchedEffect(Unit) {
                    delay(1500)
                    minTimePassed = true
                }
                // Check for updates on startup
                val coroutineScope = rememberCoroutineScope()
                LaunchedEffect(Unit) {
                    updateManager.checkForUpdates(coroutineScope) { checkDone = true }
                }

                val isLoading = !minTimePassed || !checkDone
                val hasUpdate = config.latestVersionCode > config.currentVersionCode
                // التطبيق يتوقف تماماً إذا الإصدار غير الأحدث والإجباري مفعّل
                val mustBlock = checkDone && config.isMandatory && hasUpdate

                // تنبيه اختياري عند توفر تحديث غير إجباري
                LaunchedEffect(checkDone, hasUpdate, config.isMandatory) {
                    if (checkDone && hasUpdate && !config.isMandatory) {
                        showOptionalUpdateDialog = true
                    }
                }

                if (isLoading) {
                    SplashScreen(installedVersion = updateManager.installedVersionName)
                    return@PhoneTrafficTheme
                }

                // وضع الصيانة من الأدمن: سنعود قريباً + تنبيه عبر الإشعارات
                if (checkDone && config.isMaintenanceMode) {
                    MaintenanceScreen(
                        config = config,
                        isChecking = isChecking,
                        onRetry = {
                            checkDone = false
                            updateManager.checkForUpdates(coroutineScope) { checkDone = true }
                        }
                    )
                    return@PhoneTrafficTheme
                }

                if (mustBlock) {
                    ForceUpdateScreen(
                        config = config,
                        isChecking = isChecking,
                        onRetry = {
                            checkDone = false
                            updateManager.checkForUpdates(coroutineScope) { checkDone = true }
                        }
                    )
                    return@PhoneTrafficTheme
                }

                // إعلان فتح التطبيق مرة واحدة بعد التحميل (إذا لا يوجد حظر)
                LaunchedEffect(isLoading) {
                    if (!isLoading && !appOpenShown) {
                        appOpenShown = true
                        AppOpenManager.showIfReady(this@MainActivity)
                    }
                }

                // إشعار فوري عند أي جديد: بث جديد أو تحديث متوفر
                LaunchedEffect(checkDone) {
                    if (!checkDone) return@LaunchedEffect
                    val prefs = updateManager.prefsForNotifications()
                    // 1) بث جديد من الإدارة
                    if (config.isBroadcastActive && config.broadcastMessage.isNotBlank()) {
                        val key = config.broadcastTitle + "|" + config.broadcastMessage
                        if (prefs.getString("last_broadcast_key", null) != key) {
                            prefs.edit().putString("last_broadcast_key", key).apply()
                            NotificationStore.push(config.broadcastTitle, config.broadcastMessage)
                            AppNotifications.show(
                                this@MainActivity,
                                config.broadcastTitle, config.broadcastMessage
                            )
                        }
                    }
                    // 2) تحديث متوفر
                    if (config.latestVersionCode > config.currentVersionCode) {
                        val lastNotified = prefs.getInt("last_notified_version", 0)
                        if (config.latestVersionCode > lastNotified) {
                            prefs.edit().putInt("last_notified_version", config.latestVersionCode).apply()
                            val t = "تحديث جديد لتطبيق فون ترافيك 🎉"
                            val m = "${config.updateTitle} — الإصدار ${config.latestVersionName} متوفر الآن."
                            NotificationStore.push(t, m)
                            AppNotifications.show(this@MainActivity, t, m)
                        }
                    }
                }

                // تنقل بين الأقسام مع إعلان بيني (بفاصل 90 ثانية)
                val goToCategory: (String) -> Unit = { categoryId ->
                    InterstitialManager.showIfReady(this@MainActivity) {
                        currentScreen = categoryId
                    }
                }

                // Check if mandatory update dialog is triggered
                val showMandatoryDialog = config.isMandatory && hasUpdate
                val showUpdateAlert = showMandatoryDialog || (showOptionalUpdateDialog && hasUpdate)

                if (showUpdateAlert) {
                    MandatoryUpdateDialog(
                        config = config,
                        onDismissOptional = { showOptionalUpdateDialog = false }
                    )
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackgroundLight
                ) {
                    // تلاشي ناعم عند التنقل بين الصفحات
                    AnimatedContent(
                        targetState = currentScreen,
                        transitionSpec = {
                            (fadeIn(animationSpec = tween(300)) togetherWith
                                fadeOut(animationSpec = tween(300)))
                        },
                        label = "screen_fade"
                    ) { screen ->
                    when (screen) {
                        "home" -> HomeScreen(
                            config = config,
                            updateManager = updateManager,
                            onNavigateToCategory = { categoryId ->
                                goToCategory(categoryId)
                            },
                            onNavigateToSettings = {
                                currentScreen = "settings"
                            },
                            onOpenUrlInApp = { url, title ->
                                activeBrowserUrl = url
                                activeBrowserTitle = title
                                previousScreenBeforeBrowser = "home"
                                currentScreen = "in_app_browser"
                            },
                            onDismissBroadcast = {
                                updateManager.dismissBroadcast()
                            }
                        )

                        "screens" -> ScreenComparisonScreen(
                            onBack = { currentScreen = "home" }
                        )

                        "devices" -> DeviceComparisonScreen(
                            onBack = { currentScreen = "home" }
                        )

                        "test_points" -> TestPointScreen(
                            onBack = { currentScreen = "home" }
                        )

                        "software" -> SoftwareCoursesScreen(
                            onBack = { currentScreen = "home" },
                            onOpenUrlInApp = { url, title ->
                                activeBrowserUrl = url
                                activeBrowserTitle = title
                                previousScreenBeforeBrowser = "software"
                                currentScreen = "in_app_browser"
                            }
                        )

                        "screen_compat" -> ScreenCompatibilityScreen(
                            onBack = { currentScreen = "home" }
                        )

                        "systems" -> SystemModesScreen(
                            onBack = { currentScreen = "home" }
                        )

                        "device_models" -> DeviceModelsScreen(
                            onBack = { currentScreen = "home" }
                        )

                        "imei_check" -> ImeiCheckScreen(
                            onBack = { currentScreen = "home" },
                            onOpenUrlInApp = { url, title ->
                                activeBrowserUrl = url
                                activeBrowserTitle = title
                                previousScreenBeforeBrowser = "imei_check"
                                currentScreen = "in_app_browser"
                            }
                        )

                        "rom_downloads" -> RomDownloadScreen(
                            onBack = { currentScreen = "home" },
                            onOpenUrlInApp = { url, title ->
                                activeBrowserUrl = url
                                activeBrowserTitle = title
                                previousScreenBeforeBrowser = "rom_downloads"
                                currentScreen = "in_app_browser"
                            }
                        )

                        "secret_codes" -> SecretCodesScreen(
                            onBack = { currentScreen = "home" }
                        )

                        "software_tools" -> SoftwareToolsScreen(
                            onBack = { currentScreen = "home" },
                            onOpenUrlInApp = { url, title ->
                                activeBrowserUrl = url
                                activeBrowserTitle = title
                                previousScreenBeforeBrowser = "software_tools"
                                currentScreen = "in_app_browser"
                            }
                        )

                        "dump_collection" -> DumpCollectionScreen(
                            onBack = { currentScreen = "home" }
                        )

                        "flash" -> FlashScreen(
                            onBack = { currentScreen = "home" }
                        )

                        "in_app_browser" -> InAppBrowserScreen(
                            initialUrl = activeBrowserUrl,
                            title = activeBrowserTitle,
                            onBack = { currentScreen = previousScreenBeforeBrowser }
                        )

                        "settings" -> SettingsAndAdminScreen(
                            updateManager = updateManager,
                            config = config,
                            onBack = { currentScreen = "home" }
                        )

                        else -> HomeScreen(
                            config = config,
                            updateManager = updateManager,
                            onNavigateToCategory = { goToCategory(it) },
                            onNavigateToSettings = { currentScreen = "settings" },
                            onOpenUrlInApp = { url, title ->
                                activeBrowserUrl = url
                                activeBrowserTitle = title
                                previousScreenBeforeBrowser = "home"
                                currentScreen = "in_app_browser"
                            },
                            onDismissBroadcast = { updateManager.dismissBroadcast() }
                        )
                    }
                    }
                }
            }
        }
    }
}
