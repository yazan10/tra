package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.model.AppUpdateConfig
import com.example.network.VercelUpdateManager
import com.example.ui.components.MandatoryUpdateDialog
import com.example.ui.screens.*
import com.example.ui.theme.BackgroundLight
import com.example.ui.theme.PhoneTrafficTheme

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

                // Check for updates on startup
                val coroutineScope = rememberCoroutineScope()
                LaunchedEffect(Unit) {
                    updateManager.checkForUpdates(coroutineScope)
                }

                // Check if mandatory update dialog is triggered
                val showMandatoryDialog = config.isMandatory && config.latestVersionCode > config.currentVersionCode
                val showUpdateAlert = showMandatoryDialog || (showOptionalUpdateDialog && config.latestVersionCode > config.currentVersionCode)

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
                    when (currentScreen) {
                        "home" -> HomeScreen(
                            config = config,
                            onNavigateToCategory = { categoryId ->
                                currentScreen = categoryId
                            },
                            onNavigateToSettings = {
                                currentScreen = "settings"
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
                            onNavigateToCategory = { currentScreen = it },
                            onNavigateToSettings = { currentScreen = "settings" },
                            onDismissBroadcast = { updateManager.dismissBroadcast() }
                        )
                    }
                }
            }
        }
    }
}
