package yaz.phone.network

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.concurrent.TimeUnit

/**
 * فاحص الخلفية: يعمل كل 15 دقيقة حتى والتطبيق مغلق،
 * يفحص بث الإدارة والتحديثات من السيرفر، وأي جديد يصل
 * كإشعار على شريط إشعارات التلفون مثل باقي التطبيقات.
 */
class BroadcastCheckWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val prefs = applicationContext.getSharedPreferences(
                "phone_traffic_prefs", Context.MODE_PRIVATE
            )
            val endpoint = prefs.getString(
                "vercel_endpoint",
                "https://phone-traffic-admin.vercel.app/api/app-config"
            ) ?: "https://phone-traffic-admin.vercel.app/api/app-config"

            val request = Request.Builder()
                .url(endpoint)
                .addHeader("Accept", "application/json")
                .addHeader("User-Agent", "PhoneTraffic-Android/1.0")
                .build()

            val response = client.newCall(request).execute()
            if (!response.isSuccessful) return@withContext Result.retry()
            val body = response.body?.string() ?: return@withContext Result.retry()
            val json = JSONObject(body)

            // 1) بث جديد من الإدارة
            val bActive = json.optBoolean("isBroadcastActive", false)
            val bTitle = json.optString("broadcastTitle", "")
            val bMsg = json.optString("broadcastMessage", "")
            if (bActive && bMsg.isNotBlank()) {
                val key = "$bTitle|$bMsg"
                if (prefs.getString("last_broadcast_key", null) != key) {
                    prefs.edit().putString("last_broadcast_key", key).apply()
                    NotificationStore.push(bTitle, bMsg)
                    AppNotifications.show(applicationContext, bTitle, bMsg)
                }
            }

            // 2) إصدار جديد متوفر
            val latestCode = json.optInt("latestVersionCode", 0)
            val latestName = json.optString("latestVersionName", "")
            if (latestCode > prefs.getInt("last_notified_version", 0) && latestCode > 0) {
                // قارن مع المثبت فعلياً حتى لا ننبه عن نسخة قديمة
                val installed = try {
                    val p = applicationContext.packageManager
                        .getPackageInfo(applicationContext.packageName, 0)
                    if (android.os.Build.VERSION.SDK_INT >= 28) p.longVersionCode.toInt()
                    else @Suppress("DEPRECATION") p.versionCode
                } catch (_: Exception) { 0 }
                if (latestCode > installed) {
                    prefs.edit().putInt("last_notified_version", latestCode).apply()
                    val t = "تحديث جديد لتطبيق فون ترافيك 🎉"
                    val m = "الإصدار $latestName متوفر الآن — حدّث لتحصل على الجديد."
                    NotificationStore.push(t, m)
                    AppNotifications.show(applicationContext, t, m)
                }
            }

            Result.success()
        } catch (_: Exception) {
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "broadcast_check_work"

        fun schedule(context: Context) {
            val constraints = androidx.work.Constraints.Builder()
                .setRequiredNetworkType(androidx.work.NetworkType.CONNECTED)
                .build()
            val request = androidx.work.PeriodicWorkRequestBuilder<BroadcastCheckWorker>(
                15, java.util.concurrent.TimeUnit.MINUTES
            )
                .setConstraints(constraints)
                .build()
            androidx.work.WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                androidx.work.ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }
    }
}
