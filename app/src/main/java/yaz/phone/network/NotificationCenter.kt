package yaz.phone.network

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import yaz.phone.MainActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AppNotification(
    val id: Long = System.currentTimeMillis(),
    val title: String,
    val message: String,
    val time: Long = System.currentTimeMillis(),
    var read: Boolean = false
)

/**
 * سجل إشعارات التطبيق (يُعرض من الجرس) — أي منشور/بث/تحديث جديد يُضاف هنا
 * ويصل المستخدم كإشعار تلفون فوري.
 */
object NotificationStore {
    private val _items = MutableStateFlow<List<AppNotification>>(emptyList())
    val items: StateFlow<List<AppNotification>> = _items.asStateFlow()

    fun push(title: String, message: String) {
        val updated = listOf(AppNotification(title = title, message = message)) + _items.value
        _items.value = updated.take(30)
    }

    fun unreadCount(): Int = _items.value.count { !it.read }

    fun markAllRead() {
        _items.value = _items.value.map { it.copy(read = true) }
    }
}

/**
 * إشعارات النظام (تظهر على التلفون حتى والتطبيق مغلق بعد استلامها).
 */
object AppNotifications {
    const val CHANNEL_ID = "phone_traffic_channel"
    private const val CHANNEL_NAME = "تحديثات فون ترافيك"

    fun hasPermission(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < 33) return true
        return ActivityCompat.checkSelfPermission(
            context, Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun ensureChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= 26) {
            val channel = NotificationChannel(
                CHANNEL_ID, CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply { description = "تنبيهات المنشورات والتحديثات والبث المباشر" }
            context.getSystemService(NotificationManager::class.java)
                ?.createNotificationChannel(channel)
        }
    }

    fun show(context: Context, title: String, message: String) {
        ensureChannel(context)
        if (!hasPermission(context)) return
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pending = PendingIntent.getActivity(
            context, title.hashCode(), intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notif = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pending)
            .setAutoCancel(true)
            .build()
        try {
            NotificationManagerCompat.from(context).notify(title.hashCode(), notif)
        } catch (_: SecurityException) { }
    }
}
