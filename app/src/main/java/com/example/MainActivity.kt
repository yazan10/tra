package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.model.AppUpdateConfig
import com.example.network.VercelUpdateManager
import com.example.ui.components.ForceUpdateScreen
import com.example.ui.components.MandatoryUpdateDialog
import com.example.ui.components.SplashScreen
import com.example.ui.screens.*
import com.example.ui.theme.BackgroundLight
import com.example.ui.theme.PhoneTrafficTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var updateManager: VercelUpdateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateManager = VercelUpdateManager(applicationContext)
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
                                currentScreen = categoryId
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
                            onBack = { currentScreen = "home" }
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
                            onNavigateToCategory = { currentScreen = it },
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
