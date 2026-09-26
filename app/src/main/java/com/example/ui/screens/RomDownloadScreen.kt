package com.example.ui.screens

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
import com.example.data.TechnicianDataProvider
import com.example.ui.components.AdBannerPlaceholder
import com.example.ui.components.RewardGate
import com.example.ui.components.SectionFilterStrip
import com.example.ui.theme.ColorCategoryRoms
import com.example.ui.theme.PrimaryBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RomDownloadScreen(
    onBack: () -> Unit,
    onOpenUrlInApp: (url: String, title: String) -> Unit
) {
    BackHandler { onBack() }
    // بوابة المكافأة: مشاهدة كاملة في كل دخول
    RewardGate(
        sectionName = "تحميل الرومات",
        accentColor = ColorCategoryRoms,
        onBack = onBack
    ) {

    val sites = TechnicianDataProvider.romDownloadSites
    var searchQuery by remember { mutableStateOf("") }
    var selectedSection by remember { mutableStateOf(sites.firstOrNull()?.brandTarget ?: "") }

    val filteredSites = remember(searchQuery, selectedSection) {
        sites.filter { site ->
            val matchSection = site.brandTarget == selectedSection
            val matchQuery = searchQuery.isBlank() ||
                site.title.contains(searchQuery, ignoreCase = true) ||
                site.description.contains(searchQuery, ignoreCase = true)
            matchSection && matchQuery
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "تحميل أنظمة ورومات الأجهزة",
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
            AdBannerPlaceholder(adSlotName = "إعلان - مواقع تحميل الرومات")
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
            // Header Info Card
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = ColorCategoryRoms.copy(alpha = 0.1f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ColorCategoryRoms.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CloudDownload,
                            contentDescription = null,
                            tint = ColorCategoryRoms,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "مواقع تحميل فلاشات ورومات الهواتف",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0F172A)
                                )
                            )
                            Text(
                                text = "أفضل المواقع المعتمدة لتحميل فلاشات شاومي، سامسونج، أوبو، تكنو، والأجهزة الصينية",
                                style = MaterialTheme.typography.bodySmall.copy(color = Color.Black)
                            )
                        }
                    }
                }
            }

            // Sites list
            // Search Bar
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("ابحث في الرومات (شاومي، سامسونج، أوبو)...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = ColorCategoryRoms) },
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
                    sections = sites.map { it.brandTarget }.distinct(),
                    selected = selectedSection,
                    onSelect = { selectedSection = it },
                    accentColor = ColorCategoryRoms
                )
            }

            // Inline Ad Placement (AdSense)
            item {
                AdBannerPlaceholder(adSlotName = "إعلان - مواقع تحميل الرومات")
            }

            items(filteredSites, key = { it.id }) { site ->
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
                            Text(
                                text = site.title,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0F172A)
                                ),
                                modifier = Modifier.weight(1f)
                            )
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0xFFE0F2FE)
                            ) {
                                Text(
                                    text = site.badge,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = PrimaryBlue,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "الأجهزة المدعومة: ${site.brandTarget}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = ColorCategoryRoms,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = site.description,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.Black,
                                lineHeight = 19.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Button(
                            onClick = { onOpenUrlInApp(site.url, site.title) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                        ) {
                            Icon(
                                imageVector = Icons.Default.OpenInBrowser,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "فتح موقع التحميل داخل التطبيق",
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }
        }
    }
}

}