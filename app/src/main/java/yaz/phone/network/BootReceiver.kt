package yaz.phone.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * إعادة جدولة فاحص الإشعارات بعد إقلاع الهاتف —
 * حتى تصل الإشعارات ولو لم يُفتح التطبيق أبداً بعد التشغيل.
 */
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            AppNotifications.ensureChannel(context)
            BroadcastCheckWorker.schedule(context)
            InterstitialManager.preload(context)
            FlashRewardManager.preload(context)
            RewardedManager.preload(context)
            NativeAdManager.preload(context, "posts")
            NativeAdManager.preload(context, "flash")
        }
    }
}
