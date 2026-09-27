package yaz.phone.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yaz.phone.data.TechnicianDataProvider
import yaz.phone.ui.components.AdBannerPlaceholder
import yaz.phone.ui.components.SectionFilterStrip
import yaz.phone.ui.theme.ColorCategorySystems
import yaz.phone.ui.theme.PrimaryBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SystemModesScreen(
    onBack: () -> Unit
) {
    BackHandler { onBack() }

    val modes = TechnicianDataProvider.systemModes
    var searchQuery by remember { mutableStateOf("") }
    var selectedSection by remember { mutableStateOf("آيفون iOS") }

    val modeKeywords = mapOf(
        "آيفون iOS" to "Apple",
        "أندرويد" to "أندرويد",
        "كوالكوم" to "كوالكوم",
        "ميدياتك" to "MediaTek",
        "سامسونج" to "سامسونج",
        "شاومي" to "شاومي"
    )

    val filteredModes = remember(searchQuery, selectedSection) {
        val keyword = modeKeywords[selectedSection] ?: "Apple"
        modes.filter { mode ->
            val matchSection =
                mode.supportedPlatforms.contains(keyword) ||
                mode.nameAr.contains(keyword) ||
                mode.nameEn.contains(keyword, ignoreCase = true)
            val matchQuery = searchQuery.isBlank() ||
                mode.nameAr.contains(searchQuery, ignoreCase = true) ||
                mode.nameEn.contains(searchQuery, ignoreCase = true) ||
                mode.purpose.contains(searchQuery, ignoreCase = true)
            matchSection && matchQuery
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "شرح معنى الأنظمة والأوضاع",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A)
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "رجوع",
                            tint = Color(0xFF0F172A)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            AdBannerPlaceholder(adSlotName = "إعلان - شرح الأنظمة")
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF8FAFC)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header Info
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = ColorCategorySystems.copy(alpha = 0.1f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ColorCategorySystems.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeveloperMode,
                            contentDescription = null,
                            tint = ColorCategorySystems,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "دليل بيئات التشغيل وأوضاع الصيانة",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0F172A)
                                )
                            )
                            Text(
                                text = "أزرار الدخول والخروج، أوامر الـ CMD، وتخطي الأخطاء",
                                style = MaterialTheme.typography.bodySmall.copy(color = Color.Black)
                            )
                        }
                    }
                }
            }

            // Search Bar
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("ابحث في الأوضاع (EDL، فاست بوت، ريكفري)...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = ColorCategorySystems) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Sections Strip
            item {
                SectionFilterStrip(
                    sections = listOf("آيفون iOS", "أندرويد", "كوالكوم", "ميدياتك", "سامسونج", "شاومي"),
                    selected = selectedSection,
                    onSelect = { selectedSection = it },
                    accentColor = ColorCategorySystems
                )
            }

            // Inline Ad Placement (AdSense)
            item {
                AdBannerPlaceholder(adSlotName = "إعلان - شرح الأنظمة")
            }

            items(filteredModes, key = { it.id }) { mode ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = mode.nameAr,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF0F172A)
                                    )
                                )
                                Text(
                                    text = mode.nameEn,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = ColorCategorySystems,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0xFFEFF6FF)
                            ) {
                                Text(
                                    text = mode.supportedPlatforms.take(15) + "...",
                                    style = MaterialTheme.typography.labelSmall.copy(color = PrimaryBlue),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = mode.purpose,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.Black,
                                lineHeight = 19.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // How to enter & exit
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFF8FAFC),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = "طريقة الدخول بالأزرار:",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF0F172A)
                                    )
                                )
                                Text(
                                    text = mode.enterKeyCombination,
                                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Black)
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = "أمر الدخول بالطرفية (ADB):",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF0F172A)
                                    )
                                )
                                Text(
                                    text = mode.enterTerminalCommand,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryBlue
                                    )
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = "طريقة الخروج من الوضع:",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF0F172A)
                                    )
                                )
                                Text(
                                    text = mode.howToExit,
                                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Black)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Terminal commands or keys
                        Text(
                            text = "أهم الأوامر والوظائف الأساسية:",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = ColorCategorySystems
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        mode.keyCommands.forEach { cmd ->
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0xFF0F172A),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 2.dp)
                            ) {
                                Text(
                                    text = cmd,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color(0xFF38BDF8),
                                        fontSize = 11.sp
                                    ),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
