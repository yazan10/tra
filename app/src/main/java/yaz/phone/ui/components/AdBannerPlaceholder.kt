package yaz.phone.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import yaz.phone.network.AppAds
import yaz.phone.ui.theme.PrimaryBlue
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

/**
 * بانر إعلاني حقيقي (AdMob) داخل التطبيق — يظهر/يختفي لحظياً حسب
 * إعدادات AdSense في لوحة الأدمن (pub-4752417544013096).
 * ملاحظة: المعرفات حالياً تجريبية من جوجل (إعلانات اختبار)،
 * استبدلها بمعرفات AdMob الحقيقية من AppAds.kt لعرض إعلانات مدفوعة.
 */
@Composable
fun AdBannerPlaceholder(
    modifier: Modifier = Modifier,
    adSlotName: String = "مساحة إعلانية - AdSense / AdMob"
) {
    val adsEnabled by AppAds.adsEnabled.collectAsState()
    val hiddenUntil by AppAds.bannersHiddenUntil.collectAsState()
    if (!adsEnabled) return
    if (System.currentTimeMillis() < hiddenUntil) return
    Column(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "إعلان",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = PrimaryBlue,
                fontSize = 10.sp
            )
        )
        Spacer(modifier = Modifier.height(2.dp))
        AndroidView(
            factory = { ctx ->
                AdView(ctx).apply {
                    setAdSize(AdSize.BANNER)
                    // توزيع الوحدات الثلاث على المواضع تلقائياً
                    adUnitId = AppAds.bannerUnitFor(adSlotName)
                    // إذا لا يوجد ملء (وحدات جديدة) تُخفى الخانة بدل الفراغ
                    adListener = object : AdListener() {
                        override fun onAdFailedToLoad(error: LoadAdError) {
                            visibility = android.view.View.GONE
                        }
                        override fun onAdLoaded() {
                            visibility = android.view.View.VISIBLE
                        }
                    }
                    loadAd(AdRequest.Builder().build())
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFF1F5F9))
                .border(1.dp, Color(0xFFCBD5E1), RoundedCornerShape(8.dp))
        )
    }
}
