package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.PrimaryBlue

data class GlassNavItem(
    val id: String,
    val title: String,
    val icon: ImageVector
)

/**
 * شريط سفلي زجاجي عائم بأسلوب iOS 26 (Liquid Glass):
 * بدون أي خلفية وراءه، يطفو فوق المحتوى وفوق أزرار التنقل بحواف منحنية.
 */
@Composable
fun GlassmorphicBottomBar(
    currentScreen: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        GlassNavItem("home", "الرئيسية", Icons.Default.Home),
        GlassNavItem("posts", "المنشورات", Icons.Default.Newspaper),
        GlassNavItem("rom_downloads", "الرومات", Icons.Default.CloudDownload),
        GlassNavItem("imei_check", "فحص IMEI", Icons.Default.VerifiedUser),
        GlassNavItem("settings", "الإعدادات", Icons.Default.Settings)
    )

    // حاوية شفافة تماماً + إزاحة فوق أزرار النظام (أسهم التنقل)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(28.dp),
                    ambientColor = PrimaryBlue.copy(alpha = 0.25f),
                    spotColor = PrimaryBlue.copy(alpha = 0.25f)
                )
                .clip(RoundedCornerShape(28.dp))
                // زجاج شفاف: أبيض بشفافية عالية + لمعة علوية
                .background(Color.White.copy(alpha = 0.62f))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.35f),
                            Color.White.copy(alpha = 0.05f)
                        )
                    )
                )
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.9f),
                            Color(0xFFCBD5E1).copy(alpha = 0.45f),
                            Color.White.copy(alpha = 0.7f)
                        )
                    ),
                    shape = RoundedCornerShape(28.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp, vertical = 5.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEach { item ->
                    val isSelected = currentScreen == item.id || (item.id == "home" && currentScreen == "services")

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { onNavigate(item.id) }
                            .padding(horizontal = 9.dp, vertical = 5.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(17.dp))
                                .background(
                                    if (isSelected) PrimaryBlue
                                    else Color.White.copy(alpha = 0.5f)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = if (isSelected) Color.White else Color.Black,
                                modifier = Modifier.size(19.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) PrimaryBlue else Color.Black,
                                fontSize = 10.sp
                            )
                        )
                    }
                }
            }
        }
    }
}
