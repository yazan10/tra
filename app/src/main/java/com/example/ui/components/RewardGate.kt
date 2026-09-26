package com.example.ui.components

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.network.FlashRewardManager

/**
 * بوابة الأقسام المهمة: لا يُفتح القسم إلا بعد مشاهدة إعلان مكافأة كامل
 * — تُعرض في كل دخول (الحالة تُصفَّر مع كل فتح للقسم).
 */
@Composable
fun RewardGate(
    sectionName: String,
    accentColor: Color,
    onBack: () -> Unit,
    content: @Composable () -> Unit
) {
    var unlocked by remember { mutableStateOf(false) }
    var isLoadingAd by remember { mutableStateOf(false) }
    var hint by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        (context as? Activity)?.let { FlashRewardManager.preload(it) }
    }

    if (unlocked) {
        content()
        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(88.dp)
                    .clip(RoundedCornerShape(44.dp))
                    .background(accentColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(44.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "قسم محمي: $sectionName 🔒",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "لدخول هذا القسم المهم شاهد إعلاناً قصيراً كاملاً — يُطلب في كل مرة تدخل فيها.",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
                textAlign = TextAlign.Center
            )
            if (hint != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = hint!!,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = accentColor,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    val activity = context as? Activity ?: return@Button
                    isLoadingAd = true
                    hint = null
                    FlashRewardManager.preload(activity)
                    FlashRewardManager.show(
                        activity = activity,
                        onRewarded = {
                            isLoadingAd = false
                            unlocked = true
                        },
                        onClosed = {
                            isLoadingAd = false
                            if (!unlocked) hint = "شاهد الإعلان حتى النهاية لفتح القسم"
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = accentColor,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                if (isLoadingAd) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("جاري تحميل الإعلان...", fontWeight = FontWeight.Bold)
                } else {
                    Icon(Icons.Default.PlayCircle, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("مشاهدة الإعلان والدخول", fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("رجوع للرئيسية", color = Color.Black)
            }
        }
    }
}
