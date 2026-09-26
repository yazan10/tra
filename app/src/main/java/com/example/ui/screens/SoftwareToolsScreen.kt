package com.example.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TechnicianDataProvider
import com.example.ui.components.AdBannerPlaceholder
import com.example.ui.theme.ColorCategoryTools
import com.example.ui.theme.PrimaryBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoftwareToolsScreen(
    onBack: () -> Unit,
    onOpenUrlInApp: (url: String, title: String) -> Unit
) {
    BackHandler { onBack() }
    val context = LocalContext.current
    val tools = TechnicianDataProvider.softwareTools

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "أدوات وبرامج السوفت وير",
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
            AdBannerPlaceholder(adSlotName = "إعلان - أدوات السوفت وير")
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
                    colors = CardDefaults.cardColors(containerColor = ColorCategoryTools.copy(alpha = 0.1f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ColorCategoryTools.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Handyman,
                            contentDescription = null,
                            tint = ColorCategoryTools,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "دليل أدوات وبرامج السوفت وير الاحترافية",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0F172A)
                                )
                            )
                            Text(
                                text = "أهم برامج التفليش، فك البوت لودر، تخطي FRP، وإصلاح السيريال والشبكة",
                                style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF475569))
                            )
                        }
                    }
                }
            }

            items(tools, key = { it.id }) { tool ->
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
                                text = tool.name,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0F172A)
                                )
                            )
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = ColorCategoryTools.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = tool.toolType,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = ColorCategoryTools,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = tool.description,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFF475569),
                                lineHeight = 19.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "المعالجات المدعومة: ${tool.supportedCPUs}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = PrimaryBlue,
                                fontWeight = FontWeight.SemiBold
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "أهم الميزات والقدرات:",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        tool.mainCapabilities.forEach { cap ->
                            Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                Text("✓ ", color = ColorCategoryTools, fontWeight = FontWeight.Bold)
                                Text(
                                    text = cap,
                                    style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF334155))
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = { onOpenUrlInApp(tool.officialUrl, tool.name) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(42.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Download,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "الموقع الرسمي وتحميل الأداة",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }
        }
    }
}
