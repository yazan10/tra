package yaz.phone.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yaz.phone.data.TechnicianDataProvider
import yaz.phone.ui.components.AdBannerPlaceholder
import yaz.phone.ui.components.RewardGate
import yaz.phone.ui.components.SectionFilterStrip
import yaz.phone.ui.theme.ColorCategoryDumps
import yaz.phone.ui.theme.PrimaryBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DumpCollectionScreen(
    onBack: () -> Unit
) {
    BackHandler { onBack() }
    // بوابة المكافأة: مشاهدة كاملة في كل دخول
    RewardGate(
        sectionName = "ملفات الدامب",
        accentColor = ColorCategoryDumps,
        onBack = onBack
    ) {
    val context = LocalContext.current
    val dumps = TechnicianDataProvider.dumpCollection
    var searchQuery by remember { mutableStateOf("") }
    var selectedSection by remember { mutableStateOf("eMMC") }

    val filteredDumps = remember(searchQuery, selectedSection) {
        dumps.filter { dump ->
            val matchSection = dump.memoryType.contains(selectedSection)
            val matchQuery = searchQuery.isBlank() ||
                dump.title.contains(searchQuery, ignoreCase = true) ||
                dump.deviceModel.contains(searchQuery, ignoreCase = true) ||
                dump.description.contains(searchQuery, ignoreCase = true)
            matchSection && matchQuery
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "قسم ملفات الدامب (Dump & Backups)",
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
            AdBannerPlaceholder(adSlotName = "إعلان - قسم ملفات الدامب")
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
                    colors = CardDefaults.cardColors(containerColor = ColorCategoryDumps.copy(alpha = 0.1f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ColorCategoryDumps.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = null,
                            tint = ColorCategoryDumps,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "أرشيف ملفات الدامب والنسخ الاحتياطية",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0F172A)
                                )
                            )
                            Text(
                                text = "ملفات إحياء الأجهزة بعد تغيير الذواكر وإصلاح البوت الأولي Boot1/Boot2 و UserArea",
                                style = MaterialTheme.typography.bodySmall.copy(color = Color.Black)
                            )
                        }
                    }
                }
            }

            // Dump cards
            // Search Bar
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("ابحث في الدامبات (الموديل أو نوع الذاكرة)...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = ColorCategoryDumps) },
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
                    sections = listOf("eMMC", "UFS 2.2", "UFS 3.1", "UFS 4.0"),
                    selected = selectedSection,
                    onSelect = { selectedSection = it },
                    accentColor = ColorCategoryDumps
                )
            }

            // Inline Ad Placement (AdSense)
            item {
                AdBannerPlaceholder(adSlotName = "إعلان - قسم ملفات الدامب")
            }

            items(filteredDumps, key = { it.id }) { dump ->
                val isSpecial = dump.isHighlighted

                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSpecial) Color(0xFFFFFBEB) else Color.White
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        if (isSpecial) 2.dp else 1.dp,
                        if (isSpecial) ColorCategoryDumps else Color(0xFFE2E8F0)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = dump.title,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSpecial) Color(0xFF9A3412) else Color(0xFF0F172A)
                                ),
                                modifier = Modifier.weight(1f)
                            )
                            if (isSpecial) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = ColorCategoryDumps
                                ) {
                                    Text(
                                        text = "مجموعة حصرية 2026",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "الموديل: ${dump.deviceModel}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = PrimaryBlue,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "المعالج والذاكرة: ${dump.cpuAndChipset} | ${dump.memoryType}",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.Black)
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = dump.description,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.Black,
                                lineHeight = 19.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(dump.downloadUrl))
                                context.startActivity(intent)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isSpecial) ColorCategoryDumps else PrimaryBlue,
                                contentColor = Color.White
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.CloudDownload,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isSpecial) "تحميل تجميعة Google Pixel 2026" else "تحميل ملفات الدامب",
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