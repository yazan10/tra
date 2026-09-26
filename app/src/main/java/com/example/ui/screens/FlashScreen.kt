package com.example.ui.screens

import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.core.content.FileProvider
import com.example.ui.components.AdBannerPlaceholder
import com.example.ui.components.NativeAdCard
import com.example.ui.components.RewardGate
import com.example.ui.components.SectionFilterStrip
import com.example.ui.theme.ColorCategoryFlash
import java.io.File

private const val BROKKR_PACKAGE = "com.oops.eros"
private const val BROKKR_ASSET = "brokkr_248.apk"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashScreen(
    onBack: () -> Unit
) {
    BackHandler { onBack() }
    // بوابة المكافأة: مشاهدة كاملة في كل دخول
    RewardGate(
        sectionName = "قسم فلاش YAZ",
        accentColor = ColorCategoryFlash,
        onBack = onBack
    ) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var selectedSection by remember { mutableStateOf("Odin") }
    var isInstalling by remember { mutableStateOf(false) }

    fun isBrokkrInstalled(): Boolean {
        return try {
            context.packageManager.getPackageInfo(BROKKR_PACKAGE, 0)
            true
        } catch (_: Exception) { false }
    }

    var brokkrInstalled by remember { mutableStateOf(isBrokkrInstalled()) }

    fun launchOrInstallBrokkr() {
        if (isBrokkrInstalled()) {
            val intent = context.packageManager.getLaunchIntentForPackage(BROKKR_PACKAGE)
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "تعذر تشغيل التطبيق", Toast.LENGTH_SHORT).show()
            }
            return
        }
        // تثبيت من النسخة المرفقة داخل التطبيق
        isInstalling = true
        try {
            val outFile = File(File(context.cacheDir, "apk"), BROKKR_ASSET)
            outFile.parentFile?.mkdirs()
            context.assets.open(BROKKR_ASSET).use { input ->
                outFile.outputStream().use { output -> input.copyTo(output) }
            }
            val uri = FileProvider.getUriForFile(context, "yaz.phone.fileprovider", outFile)
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/vnd.android.package-archive")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(intent)
            Toast.makeText(context, "اضغط (تثبيت) ثم عُد واضغط تشغيل", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(context, "فشل التثبيت: ${e.message}", Toast.LENGTH_LONG).show()
        } finally {
            isInstalling = false
            brokkrInstalled = isBrokkrInstalled()
        }
    }

    val odinSteps = listOf(
        "أطفئ جهاز السامسونج تماماً" to "تأكد من شحن البطارية فوق 50% قبل الدخول لوضع التنزيل.",
        "ادخل وضع التنزيل (Download Mode)" to "اضغط مطولاً: زر خفض الصوت + زر التشغيل معاً، وعند الاهتزاز أفلت زر التشغيل مع الاستمرار بخفض الصوت. في الأجهزة الحديثة: وصّل كابل USB بالكمبيوتر ثم اضغط رفع + خفض الصوت معاً.",
        "أكّد الدخول بزر رفع الصوت" to "ستظهر شاشة زرقاء/خضراء مكتوب عليها Downloading... لا تفصل الكابل أبداً.",
        "شغّل أداة Brokkr من الأسفل" to "سيتعرف البرنامج على الجهاز في خانة ID:COM باللون الأزرق، ثم اختر ملفات الفلاشة وابدأ."
    )

    val showOdin = selectedSection == "Odin"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "قسم فلاش YAZ",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "رجوع",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            AdBannerPlaceholder(adSlotName = "إعلان - قسم فلاش YAZ")
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
                    colors = CardDefaults.cardColors(containerColor = ColorCategoryFlash.copy(alpha = 0.1f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ColorCategoryFlash.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bolt,
                            contentDescription = null,
                            tint = ColorCategoryFlash,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "تفليش الأجهزة مباشرة من التطبيق",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            )
                            Text(
                                text = "وضع Odin لأجهزة سامسونج وتشغيل أداة Brokkr",
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
                    placeholder = { Text("ابحث في خطوات التفليش...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = ColorCategoryFlash) },
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
                    sections = listOf("Odin"),
                    selected = selectedSection,
                    onSelect = { selectedSection = it },
                    accentColor = ColorCategoryFlash
                )
            }

            // Inline Ad Placement (AdSense)
            item {
                AdBannerPlaceholder(adSlotName = "إعلان - قسم فلاش YAZ")
            }

            // إعلان Native مدمج بتصميم القسم
            item {
                NativeAdCard(slot = "flash")
            }

            if (showOdin) {
                // Odin / Download Mode Card
                item {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Download,
                                    contentDescription = null,
                                    tint = ColorCategoryFlash,
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "وضع التنزيل Odin لأجهزة سامسونج",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            odinSteps
                                .filter { (t, d) ->
                                    searchQuery.isBlank() ||
                                        t.contains(searchQuery, ignoreCase = true) ||
                                        d.contains(searchQuery, ignoreCase = true)
                                }
                                .forEachIndexed { index, (title, desc) ->
                                    Row(modifier = Modifier.padding(vertical = 6.dp)) {
                                        Surface(
                                            shape = RoundedCornerShape(12.dp),
                                            color = ColorCategoryFlash,
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Text(
                                                    text = "${index + 1}",
                                                    style = MaterialTheme.typography.labelMedium.copy(
                                                        color = Color.White,
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column {
                                            Text(
                                                text = title,
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.Black
                                                )
                                            )
                                            Text(
                                                text = desc,
                                                style = MaterialTheme.typography.bodySmall.copy(color = Color.Black)
                                            )
                                        }
                                    }
                                }
                            Spacer(modifier = Modifier.height(8.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFFFEF3C7)
                            ) {
                                Text(
                                    text = "⚠️ تحذير: لا تفصل الكابل أو تطفئ الجهاز أثناء التفليش — قد يموت الجهاز (Brick).",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }
                    }
                }

                // Brokkr Launch / Install Card
                item {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.FlashOn,
                                contentDescription = null,
                                tint = Color(0xFFFBBF24),
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "أداة Brokkr 2.4.8",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                            Text(
                                text = if (brokkrInstalled) "مثبتة وجاهزة للتشغيل" else "غير مثبتة — ثبّتها بضغطة واحدة",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color.White.copy(alpha = 0.75f)
                                )
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                            Button(
                                onClick = { launchOrInstallBrokkr() },
                                enabled = !isInstalling,
                                modifier = Modifier.fillMaxWidth().height(52.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = ColorCategoryFlash,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                if (isInstalling) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(20.dp),
                                        strokeWidth = 2.dp,
                                        color = Color.White
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("جاري التثبيت...", fontWeight = FontWeight.Bold)
                                } else {
                                    Icon(
                                        imageVector = if (brokkrInstalled) Icons.Default.PlayArrow else Icons.Default.GetApp,
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        if (brokkrInstalled) "تشغيل Brokkr الآن" else "تثبيت Brokkr 2.4.8",
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            if (!brokkrInstalled) {
                                Spacer(modifier = Modifier.height(8.dp))
                                TextButton(onClick = {
                                    brokkrInstalled = isBrokkrInstalled()
                                }) {
                                    Text(
                                        "ثبّتّها؟ اضغط هنا للتحديث",
                                        color = Color.White.copy(alpha = 0.8f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

}