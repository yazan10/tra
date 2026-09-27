package yaz.phone.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yaz.phone.data.TechnicianDataProvider
import yaz.phone.model.DeviceBrand
import yaz.phone.ui.components.AdBannerPlaceholder
import yaz.phone.ui.components.SectionFilterStrip
import yaz.phone.ui.theme.ColorCategoryImeiCheck
import yaz.phone.ui.theme.PrimaryBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImeiCheckScreen(
    onBack: () -> Unit,
    onOpenUrlInApp: (url: String, title: String) -> Unit
) {
    BackHandler { onBack() }
    val context = LocalContext.current

    val allSites = TechnicianDataProvider.imeiCheckSites
    var selectedBrand by remember { mutableStateOf(DeviceBrand.APPLE) }
    var imeiInput by remember { mutableStateOf("") }
    var siteQuery by remember { mutableStateOf("") }

    // Luhn algorithm verification for IMEI (15 digits)
    val isImeiValid = remember(imeiInput) {
        val clean = imeiInput.filter { it.isDigit() }
        if (clean.length != 15) {
            null
        } else {
            var sum = 0
            for (i in 0 until 14) {
                var digit = clean[i].digitToInt()
                if (i % 2 == 1) {
                    digit *= 2
                    if (digit > 9) digit -= 9
                }
                sum += digit
            }
            val checkDigit = (10 - (sum % 10)) % 10
            checkDigit == clean[14].digitToInt()
        }
    }

    val filteredSites = remember(selectedBrand, siteQuery) {
        allSites.filter { site ->
            val matchBrand = site.brandCategory == selectedBrand || site.brandCategory == DeviceBrand.ALL
            val matchQuery = siteQuery.isBlank() ||
                site.title.contains(siteQuery, ignoreCase = true) ||
                site.description.contains(siteQuery, ignoreCase = true)
            matchBrand && matchQuery
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "فحص IMEI ومواقع الفحص",
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
            AdBannerPlaceholder(adSlotName = "إعلان - فحص IMEI والشبكات")
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
                    colors = CardDefaults.cardColors(containerColor = ColorCategoryImeiCheck.copy(alpha = 0.1f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ColorCategoryImeiCheck.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.VerifiedUser,
                            contentDescription = null,
                            tint = ColorCategoryImeiCheck,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "بوابة فحص السيريال وتشيك الأجهزة",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0F172A)
                                )
                            )
                            Text(
                                text = "تفتح جميع المواقع داخل التطبيق مباشرة بدون الحاجة للخروج",
                                style = MaterialTheme.typography.bodySmall.copy(color = Color.Black)
                            )
                        }
                    }
                }
            }

            // IMEI Assistant Box
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "أداة التحقق ونسخ السيريال (IMEI Helper):",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = imeiInput,
                            onValueChange = { if (it.length <= 15) imeiInput = it.filter { char -> char.isDigit() } },
                            placeholder = { Text("أدخل رقم الـ IMEI المكون من 15 رقماً...") },
                            trailingIcon = {
                                if (imeiInput.isNotEmpty()) {
                                    IconButton(onClick = {
                                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                        val clip = ClipData.newPlainText("IMEI", imeiInput)
                                        clipboard.setPrimaryClip(clip)
                                        Toast.makeText(context, "تم نسخ الـ IMEI", Toast.LENGTH_SHORT).show()
                                    }) {
                                        Icon(Icons.Default.ContentCopy, contentDescription = "نسخ", tint = PrimaryBlue)
                                    }
                                }
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        // Status of IMEI
                        if (imeiInput.isNotEmpty()) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                when (isImeiValid) {
                                    true -> {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF16A34A), modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("رقم الـ IMEI سليم مطابق لخوارزمية Luhn (15 رقم)", style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF16A34A), fontWeight = FontWeight.Bold))
                                    }
                                    false -> {
                                        Icon(Icons.Default.Cancel, contentDescription = null, tint = Color(0xFFDC2626), modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("رقم الـ IMEI غير سليم (خطأ في الرقم الأخير Checksum)", style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFFDC2626), fontWeight = FontWeight.Bold))
                                    }
                                    null -> {
                                        Text("تبقى ${15 - imeiInput.length} أرقام لاكتمال الـ 15 رقماً", style = MaterialTheme.typography.bodySmall.copy(color = Color.Black))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Site Search Bar
            item {
                OutlinedTextField(
                    value = siteQuery,
                    onValueChange = { siteQuery = it },
                    placeholder = { Text("ابحث في مواقع الفحص (آيكلاود، شاومي)...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = ColorCategoryImeiCheck) },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Brand Selection Strip (scrollable)
            item {
                Text(
                    text = "اختر ماركة الجهاز لفحصها:",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                )
            }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        BrandSelectorBox(
                            title = "فحص أبل",
                            icon = Icons.Default.PhoneIphone,
                            isSelected = selectedBrand == DeviceBrand.APPLE,
                            brandColor = Color(0xFF1E293B),
                            onClick = { selectedBrand = DeviceBrand.APPLE },
                            modifier = Modifier.width(112.dp)
                        )
                    }
                    item {
                        BrandSelectorBox(
                            title = "فحص سامسونج",
                            icon = Icons.Default.Smartphone,
                            isSelected = selectedBrand == DeviceBrand.SAMSUNG,
                            brandColor = Color(0xFF0D47A1),
                            onClick = { selectedBrand = DeviceBrand.SAMSUNG },
                            modifier = Modifier.width(112.dp)
                        )
                    }
                    item {
                        BrandSelectorBox(
                            title = "فحص شاومي",
                            icon = Icons.Default.Devices,
                            isSelected = selectedBrand == DeviceBrand.XIAOMI,
                            brandColor = Color(0xFFFF6900),
                            onClick = { selectedBrand = DeviceBrand.XIAOMI },
                            modifier = Modifier.width(112.dp)
                        )
                    }
                    item {
                        BrandSelectorBox(
                            title = "فحص أوبو",
                            icon = Icons.Default.MobileFriendly,
                            isSelected = selectedBrand == DeviceBrand.OPPO,
                            brandColor = Color(0xFF059669),
                            onClick = { selectedBrand = DeviceBrand.OPPO },
                            modifier = Modifier.width(112.dp)
                        )
                    }
                    item {
                        BrandSelectorBox(
                            title = "فحص ريلمي",
                            icon = Icons.Default.FlashOn,
                            isSelected = selectedBrand == DeviceBrand.REALME,
                            brandColor = Color(0xFFD97706),
                            onClick = { selectedBrand = DeviceBrand.REALME },
                            modifier = Modifier.width(112.dp)
                        )
                    }
                }
            }

            // Results count
            item {
                Text(
                    text = "المواقع المتاحة (${filteredSites.size} موقع معتمد):",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                )
            }

            // Sites List
            // Inline Ad Placement (AdSense)
            item {
                AdBannerPlaceholder(adSlotName = "إعلان - فحص IMEI")
            }

            items(filteredSites, key = { it.id }) { site ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
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
                                color = if (site.isOfficial) Color(0xFFDCFCE7) else Color(0xFFE0F2FE)
                            ) {
                                Text(
                                    text = site.badge,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (site.isOfficial) Color(0xFF166534) else PrimaryBlue,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = site.description,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.Black,
                                lineHeight = 18.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // In-App Open Button
                        Button(
                            onClick = {
                                onOpenUrlInApp(site.url, site.title)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryBlue,
                                contentColor = Color.White
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Launch,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "فتح الفحص داخل التطبيق",
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BrandSelectorBox(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    brandColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) brandColor else Color.White,
        border = androidx.compose.foundation.BorderStroke(
            if (isSelected) 2.dp else 1.dp,
            if (isSelected) brandColor else Color(0xFFE2E8F0)
        ),
        modifier = modifier
            .height(84.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (isSelected) Color.White else brandColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) Color.White else Color(0xFF0F172A),
                    fontSize = 11.sp
                )
            )
        }
    }
}
