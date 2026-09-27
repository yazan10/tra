package yaz.phone.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import yaz.phone.data.TechnicianDataProvider
import yaz.phone.model.DeviceBrand
import yaz.phone.ui.components.AdBannerPlaceholder
import yaz.phone.ui.theme.ColorCategoryTestPoints
import yaz.phone.ui.theme.PrimaryBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestPointScreen(
    onBack: () -> Unit
) {
    BackHandler { onBack() }

    val allPoints = TechnicianDataProvider.testPoints
    var selectedBrand by remember { mutableStateOf(DeviceBrand.XIAOMI) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredPoints = remember(selectedBrand, searchQuery) {
        allPoints.filter { item ->
            val matchBrand = (item.brand == selectedBrand)
            val matchQuery = searchQuery.isBlank() ||
                item.modelName.contains(searchQuery, ignoreCase = true) ||
                item.modeType.contains(searchQuery, ignoreCase = true) ||
                item.chipset.contains(searchQuery, ignoreCase = true)
            matchBrand && matchQuery
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "نقاط التيست بوينت (Test Point)",
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
            AdBannerPlaceholder(adSlotName = "إعلان - نقاط التيست بوينت")
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
                    colors = CardDefaults.cardColors(containerColor = ColorCategoryTestPoints.copy(alpha = 0.1f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ColorCategoryTestPoints.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ElectricalServices,
                            contentDescription = null,
                            tint = ColorCategoryTestPoints,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "دليل نقاط التيست بوينت ومنافذ الـ COM",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0F172A)
                                )
                            )
                            Text(
                                text = "طرق التوصيل لوضع كوالكوم 9008 و BROM لمعالجة موت الأجهزة",
                                style = MaterialTheme.typography.bodySmall.copy(color = Color.Black)
                            )
                        }
                    }
                }
            }

            // Chimera Tool TestPoint Archive Download Card (Requested)
            item {
                val context = androidx.compose.ui.platform.LocalContext.current
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, ColorCategoryTestPoints),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.FolderZip,
                                contentDescription = null,
                                tint = ColorCategoryTestPoints,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "ملف صور التيست بوينت المسحوب من Chimera",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF92400E)
                                    )
                                )
                                Text(
                                    text = "أرشيف رسمي كامل لصور نقاط التيست بوينت بدقة فائقة",
                                    style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFFB45309))
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "البراندات المتوفرة داخل الملف:",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        // Brands chips
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf("HUAWEI", "LENOVO", "NOKIA", "OPPO").forEach { brand ->
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color.White,
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFDE68A))
                                ) {
                                    Text(
                                        text = brand,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF78350F)
                                        ),
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf("VIVO", "XIAOMI", "ZTE").forEach { brand ->
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color.White,
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFDE68A))
                                ) {
                                    Text(
                                        text = brand,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF78350F)
                                        ),
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                val intent = android.content.Intent(
                                    android.content.Intent.ACTION_VIEW,
                                    android.net.Uri.parse("https://t.me/Yazunlo/11")
                                )
                                context.startActivity(intent)
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ColorCategoryTestPoints,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp)
                        ) {
                            Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "تحميل ملف صور شيميرا (Chimera TestPoints)",
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
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
                    placeholder = { Text("ابحث عن الموديل (مثل Redmi Note 8، Y9 Prime)...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = ColorCategoryTestPoints) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Brand Chips
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val brands = listOf(
                        DeviceBrand.XIAOMI to "شاومي",
                        DeviceBrand.HUAWEI to "هواوي",
                        DeviceBrand.SAMSUNG to "سامسونج",
                        DeviceBrand.OPPO to "أوبو وريلمي"
                    )
                    items(brands) { (b, label) ->
                        FilterChip(
                            selected = selectedBrand == b,
                            onClick = { selectedBrand = b },
                            label = { Text(label, style = MaterialTheme.typography.labelMedium) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ColorCategoryTestPoints,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            // Items
            // Inline Ad Placement (AdSense)
            item {
                AdBannerPlaceholder(adSlotName = "إعلان - نقاط التيست بوينت")
            }

            items(filteredPoints, key = { it.id }) { tp ->
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
                                text = tp.modelName,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0F172A)
                                )
                            )
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0xFFFEF3C7)
                            ) {
                                Text(
                                    text = tp.modeType,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color(0xFFB45309),
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "المعالج: ${tp.chipset}",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.Black)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Visual Pinout Illustration Card
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFF1E293B),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "مخطط نقاط التوصيل:",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = Color.Black,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(10.dp)
                                                .clip(CircleShape)
                                                .background(Color(0xFFFBBF24))
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "Gold TP Pins",
                                            style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFFFBBF24))
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = tp.pinoutDescription,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color(0xFFF8FAFC),
                                        lineHeight = 18.sp
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "خطوات التوصيل بالترتيب:",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        tp.connectionSteps.forEachIndexed { idx, step ->
                            Row(
                                modifier = Modifier.padding(vertical = 3.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = ColorCategoryTestPoints.copy(alpha = 0.15f),
                                    modifier = Modifier.size(20.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = "${idx + 1}",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = ColorCategoryTestPoints,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 11.sp
                                            )
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = step,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color.Black,
                                        lineHeight = 18.sp
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Port info
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFF1F5F9),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Usb,
                                    contentDescription = null,
                                    tint = PrimaryBlue,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "المنفذ في Device Manager:",
                                        style = MaterialTheme.typography.labelSmall.copy(color = Color.Black)
                                    )
                                    Text(
                                        text = tp.deviceManagerPort,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = PrimaryBlue
                                        )
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        // Caution note
                        Row(
                            modifier = Modifier.padding(top = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = Color(0xFFDC2626),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = tp.cautionNote,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color(0xFF991B1B),
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
