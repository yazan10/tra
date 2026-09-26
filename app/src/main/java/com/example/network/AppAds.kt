package com.example.network

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * بيانات حساب AdSense وإعدادات الإعلانات داخل سورس التطبيق.
 * Publisher ID: pub-4752417544013096 / Customer ID: 9947688120
 * التفعيل/الإيقاف يُتحكم به لحظياً من لوحة الأدمن عبر /api/app-config.
 */
object AppAds {
    const val PUBLISHER_ID = "ca-pub-4752417544013096"
    const val CUSTOMER_ID = "9947688120"
    const val BLOG_URL = "https://yaz-blog.blogspot.com/"

    // معرفات AdMob الحقيقية لحساب pub-4752417544013096 (تطبيق Phone Traffic).
    const val AD_MOB_APP_ID = "ca-app-pub-4752417544013096~8334248524"
    const val BANNER_AD_UNIT_ID = "ca-app-pub-4752417544013096/3983939976"
    // وحدات إضافية — تُوزَّع تلقائياً على المواضع
    const val BANNER_AD_UNIT_2 = "ca-app-pub-4752417544013096/1504941289" // app5 بانر
    const val BANNER_AD_UNIT_3 = "ca-app-pub-4752417544013096/7456281128" // app6 بانر
    const val INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-4752417544013096/6829595167" // app2 بيني
    const val NATIVE_AD_UNIT_ID = "ca-app-pub-4752417544013096/4833987212" // app3 نيتف
    const val APP_OPEN_AD_UNIT_ID = "ca-app-pub-4752417544013096/9264186815" // open1 فتح التطبيق
    const val REWARDED_AD_UNIT_ID = "ca-app-pub-4752417544013096/6638023472" // app4 مكافأة
    const val REWARDED_FLASH_UNIT_ID = "ca-app-pub-4752417544013096/8577791109" // بوابة الأقسام المهمة (كل مرة)
    const val NATIVE_AD_UNIT_2 = "ca-app-pub-4752417544013096/6876773183" // app7 نيتف ثانٍ

    /** توزيع البانرات الثلاثة على المواضع حسب الاسم — توزيع ثابت وعادل للظهور. */
    fun bannerUnitFor(slotName: String): String {
        val units = listOf(BANNER_AD_UNIT_ID, BANNER_AD_UNIT_2, BANNER_AD_UNIT_3)
        return units[(slotName.hashCode() and Int.MAX_VALUE) % units.size]
    }

    private val _adsEnabled = MutableStateFlow(true)
    val adsEnabled: StateFlow<Boolean> = _adsEnabled.asStateFlow()

    fun updateFromServer(enabled: Boolean) {
        _adsEnabled.value = enabled
    }

    // إخفاء مؤقت للبانرات كمكافأة بعد مشاهدة إعلان المكافأة (ساعة واحدة)
    private val _bannersHiddenUntil = MutableStateFlow(0L)
    val bannersHiddenUntil: StateFlow<Long> = _bannersHiddenUntil.asStateFlow()

    fun grantNoAdsReward(durationMs: Long = 60 * 60 * 1000L) {
        _bannersHiddenUntil.value = System.currentTimeMillis() + durationMs
    }

    fun isRewardActive(): Boolean =
        System.currentTimeMillis() < _bannersHiddenUntil.value
}
