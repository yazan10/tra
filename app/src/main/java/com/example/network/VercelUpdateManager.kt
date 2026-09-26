package com.example.network

import android.content.Context
import com.example.model.AppUpdateConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class VercelUpdateManager(private val context: Context) {

    private val prefs = context.getSharedPreferences("phone_traffic_prefs", Context.MODE_PRIVATE)

    // رقم إصدار التطبيق الحقيقي المثبت على الجهاز (من PackageManager)
    val installedVersionCode: Int = try {
        val p = context.packageManager.getPackageInfo(context.packageName, 0)
        if (android.os.Build.VERSION.SDK_INT >= 28) p.longVersionCode.toInt() else @Suppress("DEPRECATION") p.versionCode
    } catch (_: Exception) { 1 }

    val installedVersionName: String = try {
        context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "1.0"
    } catch (_: Exception) { "1.0" }

    private val client = OkHttpClient.Builder()
        .connectTimeout(6, TimeUnit.SECONDS)
        .readTimeout(6, TimeUnit.SECONDS)
        .build()

    // Default Vercel production hosting endpoint
    var vercelEndpoint: String
        get() = prefs.getString("vercel_endpoint", "https://phone-traffic-admin.vercel.app/api/app-config") ?: "https://phone-traffic-admin.vercel.app/api/app-config"
        set(value) = prefs.edit().putString("vercel_endpoint", value).apply()

    private val _configState = MutableStateFlow(
        AppUpdateConfig(
            currentVersionCode = installedVersionCode,
            latestVersionCode = installedVersionCode,
            latestVersionName = "1.0.0",
            isMandatory = false,
            updateTitle = "تحديث جديد لتطبيق فون ترافيك",
            updateMessage = "تحديث لقواعد بيانات توافق الشاشات وأكواد التيست بوينت لعام 2026.",
            downloadUrl = "https://phone-traffic-admin.vercel.app/download",
            broadcastTitle = "أهلاً بك في فون ترافيك 🛠️",
            broadcastMessage = "دليلك الأول كفني صيانة محمول: مقارنة الشاشات، نقاط التيست بوينت، ومواقع الفحص السريع.",
            isBroadcastActive = true
        )
    )
    val configState: StateFlow<AppUpdateConfig> = _configState.asStateFlow()

    private val _isChecking = MutableStateFlow(false)
    val isChecking: StateFlow<Boolean> = _isChecking.asStateFlow()

    private val _lastCheckResult = MutableStateFlow<String?>(null)
    val lastCheckResult: StateFlow<String?> = _lastCheckResult.asStateFlow()

    fun checkForUpdates(coroutineScope: CoroutineScope, onComplete: ((Boolean) -> Unit)? = null) {
        coroutineScope.launch(Dispatchers.IO) {
            _isChecking.value = true
            try {
                val request = Request.Builder()
                    .url(vercelEndpoint)
                    .addHeader("Accept", "application/json")
                    .addHeader("User-Agent", "PhoneTraffic-Android/1.0")
                    .build()

                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    if (!responseBody.isNullOrEmpty()) {
                        val json = JSONObject(responseBody)
                        val latestCode = json.optInt("latestVersionCode", 1)
                        val latestName = json.optString("latestVersionName", "1.0.0")
                        val isMandatory = json.optBoolean("isMandatory", false)
                        val title = json.optString("updateTitle", "تحديث إجباري متوفر")
                        val msg = json.optString("updateMessage", "يرجى تثبيت أحدث إصدار لمتابعة استخدام التطبيق.")
                        val downloadUrl = json.optString("downloadUrl", "https://phone-traffic-admin.vercel.app/download")
                        val bTitle = json.optString("broadcastTitle", "إشعار من الإدارة")
                        val bMsg = json.optString("broadcastMessage", "")
                        val bActive = json.optBoolean("isBroadcastActive", true)
                        val blogUrl = json.optString("blogUrl", AppAds.BLOG_URL)
                        // إعدادات AdSense من السيرفر (تتحكم بها لوحة الأدمن)
                        val adsObj = json.optJSONObject("adsense")
                        val adsEnabled = adsObj?.optBoolean("adsEnabled", true) ?: true
                        AppAds.updateFromServer(adsEnabled)

                        _configState.value = _configState.value.copy(
                            currentVersionCode = installedVersionCode,
                            latestVersionCode = latestCode,
                            latestVersionName = latestName,
                            isMandatory = isMandatory,
                            updateTitle = title,
                            updateMessage = msg,
                            downloadUrl = downloadUrl,
                            broadcastTitle = bTitle,
                            broadcastMessage = bMsg,
                            isBroadcastActive = bActive,
                            adsEnabled = adsEnabled,
                            blogUrl = blogUrl
                        )
                        _lastCheckResult.value = "تمت المزامنة بنجاح مع استضافة Vercel"
                        // الرد على الخيط الرئيسي (Toast والواجهة تنهار على خيط الخلفية)
                        withContext(Dispatchers.Main) { onComplete?.invoke(true) }
                        return@launch
                    }
                }
                _lastCheckResult.value = "استجابة السيرفر غير مطابقة، تم استخدام الإعدادات الاحتياطية."
                withContext(Dispatchers.Main) { onComplete?.invoke(false) }
            } catch (e: Exception) {
                // Keep local safe state
                _lastCheckResult.value = "تعذر الاتصال بسيرفر Vercel (${e.message ?: "شبكة"})"
                withContext(Dispatchers.Main) { onComplete?.invoke(false) }
            } finally {
                _isChecking.value = false
            }
        }
    }

    // Allows admin/technician to simulate a mandatory update or test broadcast inside app
    fun simulateAdminPush(isMandatory: Boolean, title: String, message: String) {
        _configState.value = _configState.value.copy(
            latestVersionCode = 2,
            latestVersionName = "2.0.0",
            isMandatory = isMandatory,
            updateTitle = title,
            updateMessage = message,
            broadcastTitle = title,
            broadcastMessage = message,
            isBroadcastActive = true
        )
    }

    fun dismissBroadcast() {
        _configState.value = _configState.value.copy(isBroadcastActive = false)
    }
}
