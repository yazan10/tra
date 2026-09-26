package com.example.ui.components

import android.content.Context
import android.graphics.Color as AndroidColor
import android.graphics.Typeface
import android.util.TypedValue
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.network.AppAds
import com.example.network.NativeAdManager
import com.example.ui.theme.PrimaryBlue
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

/**
 * إعلان Native متقدم داخل قائمة المنشورات — يندمج مع تصميم التطبيق
 * (عنوان + وصف + زر) ويُخفى حسب تحكم الأدمن والمكافأة.
 */
@Composable
fun NativeAdCard(modifier: Modifier = Modifier, slot: String = "posts") {
    val adsEnabled by AppAds.adsEnabled.collectAsState()
    val hiddenUntil by AppAds.bannersHiddenUntil.collectAsState()
    if (!adsEnabled) return
    if (System.currentTimeMillis() < hiddenUntil) return

    val context = LocalContext.current
    var nativeAd by remember(slot) { mutableStateOf<NativeAd?>(null) }
    LaunchedEffect(slot) {
        nativeAd = NativeAdManager.get(slot)
        if (nativeAd == null) {
            NativeAdManager.preload(context, slot) { nativeAd = NativeAdManager.get(slot) }
        }
    }
    val ad = nativeAd ?: return

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "إعلان مموَّل",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue,
                    fontSize = 10.sp
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            AndroidView(
                factory = { ctx -> buildNativeAdView(ctx, ad) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

private fun buildNativeAdView(ctx: Context, nativeAd: NativeAd): NativeAdView {
    val dp = { v: Int -> (v * ctx.resources.displayMetrics.density).toInt() }
    val adView = NativeAdView(ctx)

    val root = LinearLayout(ctx).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(dp(4), dp(4), dp(4), dp(4))
        setBackgroundColor(AndroidColor.WHITE)
    }

    val headline = TextView(ctx).apply {
        setTextColor(AndroidColor.BLACK)
        setTypeface(typeface, Typeface.BOLD)
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
    }
    val body = TextView(ctx).apply {
        setTextColor(AndroidColor.BLACK)
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f)
        val p = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        p.topMargin = dp(4)
        layoutParams = p
    }
    val ctaRow = LinearLayout(ctx).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.END
        val p = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        p.topMargin = dp(8)
        layoutParams = p
    }
    val cta = Button(ctx, null, android.R.attr.borderlessButtonStyle).apply {
        setTextColor(AndroidColor.WHITE)
        setBackgroundColor(0xFF1565C0.toInt())
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f)
    }
    ctaRow.addView(cta)

    root.addView(headline)
    root.addView(body)
    root.addView(ctaRow)
    adView.addView(root)

    adView.headlineView = headline
    adView.bodyView = body
    adView.callToActionView = cta
    adView.setNativeAd(nativeAd)
    return adView
}
