package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TechnicianDataProvider
import com.example.model.ScreenTechSpec
import com.example.ui.components.AdBannerPlaceholder
import com.example.ui.components.SectionFilterStrip
import com.example.ui.theme.ColorCategoryScreens
import com.example.ui.theme.PrimaryBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenComparisonScreen(
    onBack: () -> Unit
) {
    BackHandler { onBack() }

    val allSpecs = TechnicianDataProvider.screenTechSpecs
    var selectedTab by remember { mutableStateOf("oled") }
    var expandedItemIds by remember { mutableStateOf(setOf<String>()) }
    var searchQuery by remember { mutableStateOf("") }

    // Compare mode
    var isCompareMode by remember { mutableStateOf(false) }
    var compareSpec1 by remember { mutableStateOf<ScreenTechSpec?>(allSpecs.getOrNull(0)) }
    var compareSpec2 by remember { mutableStateOf<ScreenTechSpec?>(allSpecs.getOrNull(2)) }

    val filteredSpecs = remember(selectedTab, searchQuery) {
        val byTab = when (selectedTab) {
            "oled" -> allSpecs.filter { it.id == "scr_oled" || it.id == "scr_amoled" }
            "ips" -> allSpecs.filter { it.id == "scr_ips" }
            "incell" -> allSpecs.filter { it.id == "scr_incell" }
            "copy" -> allSpecs.filter { it.id == "scr_copy_grades" }
            else -> allSpecs
        }
        if (searchQuery.isBlank()) byTab
        else byTab.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
            it.technology.contains(searchQuery, ignoreCase = true) ||
            it.technicianRecommendation.contains(searchQuery, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "مقارنة الشاشات",
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
                actions = {
                    IconButton(onClick = { isCompareMode = !isCompareMode }) {
                        Icon(
                            imageVector = if (isCompareMode) Icons.Default.ViewAgenda else Icons.Default.CompareArrows,
                            contentDescription = "وضع المقارنة المباشرة",
                            tint = PrimaryBlue
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            AdBannerPlaceholder(adSlotName = "إعلان - قسم مقارنة الشاشات")
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
            // Mode Banner
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = ColorCategoryScreens.copy(alpha = 0.1f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ColorCategoryScreens.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Tv,
                            contentDescription = null,
                            tint = ColorCategoryScreens,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "دليل الشاشات والتقنيات للفنيين",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0F172A)
                                )
                            )
                            Text(
                                text = "الفروقات الجوهرية، مشاكل الـ Touch IC، والتروتون ودرجات الكوبي",
                                style = MaterialTheme.typography.bodySmall.copy(color = Color.Black)
                            )
                        }
                    }
                }
            }

            // Interactive Compare View if enabled
            if (isCompareMode) {
                item {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, PrimaryBlue),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "مقارنة جانبية مباشرة",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryBlue
                                    )
                                )
                                TextButton(onClick = { isCompareMode = false }) {
                                    Text("عرض القائمة")
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Selector rows
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("التقنية الأولى", style = MaterialTheme.typography.labelSmall)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = Color(0xFFEFF6FF),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = compareSpec1?.technology ?: "اختر",
                                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = PrimaryBlue),
                                            modifier = Modifier.padding(8.dp)
                                        )
                                    }
                                }

                                Column(modifier = Modifier.weight(1f)) {
                                    Text("التقنية المقابلة", style = MaterialTheme.typography.labelSmall)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = Color(0xFFEFF6FF),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = compareSpec2?.technology ?: "اختر",
                                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = PrimaryBlue),
                                            modifier = Modifier.padding(8.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            // Comparison Specs Table
                            if (compareSpec1 != null && compareSpec2 != null) {
                                CompareRowTable(
                                    label = "السطوع الأقصى",
                                    val1 = compareSpec1!!.brightness,
                                    val2 = compareSpec2!!.brightness
                                )
                                CompareRowTable(
                                    label = "استهلاك الطاقة",
                                    val1 = compareSpec1!!.powerEfficiency,
                                    val2 = compareSpec2!!.powerEfficiency
                                )
                                CompareRowTable(
                                    label = "معدل التحديث",
                                    val1 = compareSpec1!!.refreshRate,
                                    val2 = compareSpec2!!.refreshRate
                                )
                                CompareRowTable(
                                    label = "نقل آيسي الشاشة IC",
                                    val1 = if (compareSpec1!!.icSwapRequired) "يلزم نقل الآيسي" else "غير مطلوب",
                                    val2 = if (compareSpec2!!.icSwapRequired) "يلزم نقل الآيسي" else "غير مطلوب"
                                )
                                CompareRowTable(
                                    label = "استجابة اللمس",
                                    val1 = compareSpec1!!.touchResponse,
                                    val2 = compareSpec2!!.touchResponse
                                )
                            }
                        }
                    }
                }
            }

            // Search Bar
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("ابحث في الشاشات (OLED، تروتون، آيسي)...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = ColorCategoryScreens) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Filter Chips
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val tabs = listOf(
                        "oled" to "OLED و AMOLED",
                        "ips" to "IPS LCD",
                        "incell" to "Incell البديلة",
                        "copy" to "درجات الكوبي (GX/JK)"
                    )
                    items(tabs) { (key, label) ->
                        FilterChip(
                            selected = selectedTab == key,
                            onClick = { selectedTab = key },
                            label = { Text(label, style = MaterialTheme.typography.labelMedium) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ColorCategoryScreens,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            // Specs Cards List
            // Inline Ad Placement (AdSense)
            item {
                AdBannerPlaceholder(adSlotName = "إعلان - مقارنة الشاشات")
            }

            items(filteredSpecs, key = { it.id }) { spec ->
                val isExpanded = expandedItemIds.contains(spec.id)

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
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = spec.title,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF0F172A)
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = spec.structure,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color.Black,
                                        lineHeight = 18.sp
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Quick info tags
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0xFFE0F2FE),
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(modifier = Modifier.padding(6.dp)) {
                                    Text("السطوع", style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF0369A1)))
                                    Text(
                                        text = spec.brightness.take(20) + "...",
                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                        maxLines = 1
                                    )
                                }
                            }
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (spec.icSwapRequired) Color(0xFFFEF3C7) else Color(0xFFDCFCE7),
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(modifier = Modifier.padding(6.dp)) {
                                    Text("نقل آيسي IC", style = MaterialTheme.typography.labelSmall.copy(color = if (spec.icSwapRequired) Color(0xFFB45309) else Color(0xFF15803D)))
                                    Text(
                                        text = if (spec.icSwapRequired) "مطلوب للآيفون" else "غير مطلوب",
                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                        maxLines = 1
                                    )
                                }
                            }
                        }

                        // Expandable Section
                        AnimatedVisibility(visible = isExpanded) {
                            Column(modifier = Modifier.padding(top = 14.dp)) {
                                Divider(color = Color(0xFFE2E8F0), thickness = 1.dp)
                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = "الأصلي مقابل الكوبي:",
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = ColorCategoryScreens
                                    )
                                )
                                Text(
                                    text = spec.originalVsCopyDetails,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color.Black,
                                        lineHeight = 20.sp
                                    )
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "ملاحظات برمجة True Tone والآيسي:",
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF0F172A)
                                    )
                                )
                                Text(
                                    text = spec.icSwapDetails + " " + spec.trueToneDetails,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color.Black,
                                        lineHeight = 20.sp
                                    )
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = "أشهر الأعطال في ورش الصيانة:",
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFDC2626)
                                    )
                                )
                                spec.commonRepairFaults.forEach { fault ->
                                    Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                        Text("• ", color = Color(0xFFDC2626), fontWeight = FontWeight.Bold)
                                        Text(fault, style = MaterialTheme.typography.bodySmall.copy(color = Color.Black))
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFFF1F5F9),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            text = "نصيحة الفني المعتمدة:",
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = PrimaryBlue
                                            )
                                        )
                                        Text(
                                            text = spec.technicianRecommendation,
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                color = Color(0xFF1E293B),
                                                lineHeight = 18.sp
                                            )
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    expandedItemIds = if (isExpanded) {
                                        expandedItemIds - spec.id
                                    } else {
                                        expandedItemIds + spec.id
                                    }
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isExpanded) "عرض تفاصيل أقل" else "عرض التفاصيل الفنية الكاملة",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = ColorCategoryScreens,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Icon(
                                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = null,
                                tint = ColorCategoryScreens
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CompareRowTable(label: String, val1: String, val2: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = val1,
                style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF0F172A)),
                modifier = Modifier
                    .weight(1f)
                    .background(Color(0xFFF8FAFC), RoundedCornerShape(4.dp))
                    .padding(6.dp)
            )
            Text(
                text = val2,
                style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF0F172A)),
                modifier = Modifier
                    .weight(1f)
                    .background(Color(0xFFF8FAFC), RoundedCornerShape(4.dp))
                    .padding(6.dp)
            )
        }
    }
}
