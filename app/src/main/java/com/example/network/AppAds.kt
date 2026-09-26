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

    private val _adsEnabled = MutableStateFlow(true)
    val adsEnabled: StateFlow<Boolean> = _adsEnabled.asStateFlow()

    fun updateFromServer(enabled: Boolean) {
        _adsEnabled.value = enabled
    }
}
