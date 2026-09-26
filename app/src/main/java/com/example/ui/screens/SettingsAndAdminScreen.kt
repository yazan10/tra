package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.sp
import com.example.model.AppUpdateConfig
import com.example.network.VercelUpdateManager
import com.example.ui.components.AdBannerPlaceholder
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.StatusSuccess

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsAndAdminScreen(
    updateManager: VercelUpdateManager,
    config: AppUpdateConfig,
    onBack: () -> Unit
) {
    BackHandler { onBack() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val isChecking by updateManager.isChecking.collectAsState()
    val lastResult by updateManager.lastCheckResult.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "الإعدادات وعن التطبيق",
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
            AdBannerPlaceholder(adSlotName = "إعلان - شاشة الإعدادات")
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
            // App Updates Card (Clean user-facing update check)
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.SystemUpdate,
                                contentDescription = null,
                                tint = PrimaryBlue,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "تحديثات التطبيق وقواعد البيانات",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0F172A)
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "تحقق من توفر أحدث إصدار لتحديث توافقات الشاشات، نقاط التيست بوينت وروابط الفحص السريع.",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF475569))
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                updateManager.checkForUpdates(scope) { success ->
                                    val msg = if (success) "أنت تستخدم أحدث إصدار متوفر" else "تعذر التحقق، تأكد من الاتصال بالإنترنت"
                                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                }
                            },
                            enabled = !isChecking,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                        ) {
                            if (isChecking) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(18.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("جاري التحقق...")
                            } else {
                                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("التحقق من وجود تحديثات جديدة")
                            }
                        }

                        if (!lastResult.isNullOrEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = if (lastResult?.contains("نجاح") == true) "تم التحقق: التطبيق محدث لأحدث إصدار" else "حالة الاتصال مستقرة",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = StatusSuccess,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                }
            }

            // Social & Contact Official Pages
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "صفحات التواصل والمتابعة الرسمية",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        ListItem(
                            headlineContent = { Text("قناة التليجرام الرسمية", fontWeight = FontWeight.Bold) },
                            supportingContent = { Text("t.me/Yazunlo", color = Color(0xFF0088CC)) },
                            leadingContent = {
                                Icon(Icons.Default.Send, contentDescription = null, tint = Color(0xFF0088CC))
                            },
                            modifier = Modifier.clickable {
                                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Yazunlo")))
                            }
                        )

                        Divider(color = Color(0xFFF1F5F9))

                        ListItem(
                            headlineContent = { Text("حساب انستغرام", fontWeight = FontWeight.Bold) },
                            supportingContent = { Text("instagram.com/yaz.salaqq", color = Color(0xFFE1306C)) },
                            leadingContent = {
                                Icon(Icons.Default.CameraAlt, contentDescription = null, tint = Color(0xFFE1306C))
                            },
                            modifier = Modifier.clickable {
                                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/yaz.salaqq?stkn=MWoydW42bjNycmtlYw==")))
                            }
                        )

                        Divider(color = Color(0xFFF1F5F9))

                        ListItem(
                            headlineContent = { Text("صفحة الفيسبوك", fontWeight = FontWeight.Bold) },
                            supportingContent = { Text("Facebook Community Page", color = Color(0xFF1877F2)) },
                            leadingContent = {
                                Icon(Icons.Default.ThumbUp, contentDescription = null, tint = Color(0xFF1877F2))
                            },
                            modifier = Modifier.clickable {
                                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/share/1cLBsrX5Nn/")))
                            }
                        )
                    }
                }
            }

            // Google Play & Policy Compliance
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "معلومات التطبيق والأمان",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "• التطبيق: Phone Traffic (فون ترافيك)\n• الحزمة: yaz.phone\n• متوافق من أندرويد 6.0 فصاعداً\n• لا يطلب أي صلاحيات خطيرة أو وصول لملفات المستخدم\n• خط المنصة: IBM Plex Sans Arabic Bold\n• الإصدار: v1.0.0",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFF475569),
                                lineHeight = 20.sp
                            )
                        )
                    }
                }
            }
        }
    }
}
