package yaz.phone.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yaz.phone.ui.theme.PrimaryBlue
import yaz.phone.ui.theme.PrimaryBlueDark

@Composable
fun TechnicianDrawerSheet(
    onNavigate: (String) -> Unit,
    onOpenBlog: () -> Unit,
    onCloseDrawer: () -> Unit
) {
    val context = LocalContext.current

    ModalDrawerSheet(
        drawerContainerColor = Color.White,
        modifier = Modifier.width(310.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = PrimaryBlue,
                    modifier = Modifier.size(46.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Build,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Phone Traffic",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A)
                        )
                    )
                    Text(
                        text = "فون ترافيك - دليل فني المحمول",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = PrimaryBlue,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.sp
                        )
                    )
                }
            }

            Divider(color = Color(0xFFE2E8F0), thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

            // Navigation items
            NavigationDrawerItem(
                icon = { Icon(Icons.Default.Home, contentDescription = null, tint = PrimaryBlue) },
                label = { Text("قسم الخدمات (الأقسام الـ 12)", fontWeight = FontWeight.Bold) },
                selected = false,
                onClick = {
                    onCloseDrawer()
                    onNavigate("home")
                },
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            NavigationDrawerItem(
                icon = { Icon(Icons.Default.Newspaper, contentDescription = null, tint = PrimaryBlue) },
                label = { Text("قسم المنشورات والمقالات", fontWeight = FontWeight.Bold) },
                selected = false,
                onClick = {
                    onCloseDrawer()
                    onNavigate("posts")
                },
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            NavigationDrawerItem(
                icon = { Icon(Icons.Default.RssFeed, contentDescription = null, tint = Color(0xFFFF5722)) },
                label = { Text("مدونة التطبيق (yaz-blog)", fontWeight = FontWeight.Bold) },
                selected = false,
                onClick = {
                    onCloseDrawer()
                    onOpenBlog()
                },
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            NavigationDrawerItem(
                icon = { Icon(Icons.Default.CloudDownload, contentDescription = null, tint = PrimaryBlue) },
                label = { Text("تحميل أنظمة الأجهزة (الرومات)", fontWeight = FontWeight.Bold) },
                selected = false,
                onClick = {
                    onCloseDrawer()
                    onNavigate("rom_downloads")
                },
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            NavigationDrawerItem(
                icon = { Icon(Icons.Default.Password, contentDescription = null, tint = PrimaryBlue) },
                label = { Text("أكواد خيارات المطور", fontWeight = FontWeight.Bold) },
                selected = false,
                onClick = {
                    onCloseDrawer()
                    onNavigate("secret_codes")
                },
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            NavigationDrawerItem(
                icon = { Icon(Icons.Default.Bolt, contentDescription = null, tint = Color(0xFFDC2626)) },
                label = { Text("قسم فلاش YAZ (تفليش الأجهزة)", fontWeight = FontWeight.Bold) },
                selected = false,
                onClick = {
                    onCloseDrawer()
                    onNavigate("flash")
                },
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            NavigationDrawerItem(
                icon = { Icon(Icons.Default.Settings, contentDescription = null, tint = Color.Black) },
                label = { Text("الإعدادات وعن التطبيق", fontWeight = FontWeight.Bold) },
                selected = false,
                onClick = {
                    onCloseDrawer()
                    onNavigate("settings")
                },
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Social channels row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                IconButton(onClick = {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Yazunlo")))
                }) {
                    Icon(Icons.Default.Send, contentDescription = "Telegram", tint = Color(0xFF0088CC))
                }
                IconButton(onClick = {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/yaz.salaqq?stkn=MWoydW42bjNycmtlYw==")))
                }) {
                    Icon(Icons.Default.CameraAlt, contentDescription = "Instagram", tint = Color(0xFFE1306C))
                }
                IconButton(onClick = {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/share/1cLBsrX5Nn/")))
                }) {
                    Icon(Icons.Default.ThumbUp, contentDescription = "Facebook", tint = Color(0xFF1877F2))
                }
            }
        }
    }
}
