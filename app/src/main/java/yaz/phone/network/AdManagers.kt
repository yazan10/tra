package yaz.phone.network

import android.app.Activity
import android.content.Context
import android.os.SystemClock
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback

/**
 * مدير الإعلان البيني: يظهر عند التنقل بين الأقسام بفاصل زمني (90 ثانية)
 * حتى لا يزعج الفني — يُحمَّل مسبقاً ويُعاد تحميله بعد كل عرض.
 */
object InterstitialManager {
    private var ad: InterstitialAd? = null
    private var lastShownAt = 0L
    private const val COOLDOWN_MS = 90_000L
    private var loading = false

    fun preload(context: Context) {
        if (ad != null || loading) return
        loading = true
        InterstitialAd.load(
            context, AppAds.INTERSTITIAL_AD_UNIT_ID, AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(a: InterstitialAd) { ad = a; loading = false }
                override fun onAdFailedToLoad(e: LoadAdError) { ad = null; loading = false }
            }
        )
    }

    /** يعرض الإعلان إذا جاهز وانتهى الفاصل، ثم ينفذ onDone (التنقل) بعد الإغلاق. */
    fun showIfReady(activity: Activity, onDone: () -> Unit) {
        val current = ad
        val cooled = SystemClock.elapsedRealtime() - lastShownAt >= COOLDOWN_MS
        if (current != null && cooled) {
            lastShownAt = SystemClock.elapsedRealtime()
            current.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    ad = null
                    preload(activity)
                    onDone()
                }
                override fun onAdFailedToShowFullScreenContent(e: AdError) {
                    ad = null
                    preload(activity)
                    onDone()
                }
            }
            current.show(activity)
        } else {
            preload(activity)
            onDone()
        }
    }
}

/**
 * مدير إعلان فتح التطبيق: يظهر مرة واحدة بعد شاشة التحميل.
 */
object AppOpenManager {
    private var ad: AppOpenAd? = null
    private var loading = false

    fun preload(context: Context) {
        if (ad != null || loading) return
        loading = true
        AppOpenAd.load(
            context, AppAds.APP_OPEN_AD_UNIT_ID, AdRequest.Builder().build(),
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(a: AppOpenAd) { ad = a; loading = false }
                override fun onAdFailedToLoad(e: LoadAdError) { ad = null; loading = false }
            }
        )
    }

    fun showIfReady(activity: Activity) {
        val current = ad ?: run { preload(activity); return }
        current.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() { ad = null; preload(activity) }
            override fun onAdFailedToShowFullScreenContent(e: AdError) { ad = null; preload(activity) }
        }
        current.show(activity)
    }
}

/**
 * مدير إعلان المكافأة: مشاهدة كاملة = إخفاء البانرات لمدة ساعة.
 */
object RewardedManager {
    private var ad: RewardedInterstitialAd? = null
    private var loading = false

    fun preload(context: Context) {
        if (ad != null || loading) return
        loading = true
        RewardedInterstitialAd.load(
            context, AppAds.REWARDED_AD_UNIT_ID, AdRequest.Builder().build(),
            object : RewardedInterstitialAdLoadCallback() {
                override fun onAdLoaded(a: RewardedInterstitialAd) { ad = a; loading = false }
                override fun onAdFailedToLoad(e: LoadAdError) { ad = null; loading = false }
            }
        )
    }

    fun isReady(): Boolean = ad != null

    fun show(activity: Activity, onRewarded: () -> Unit, onClosed: () -> Unit) {
        val current = ad ?: run { preload(activity); onClosed(); return }
        current.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() { ad = null; preload(activity); onClosed() }
            override fun onAdFailedToShowFullScreenContent(e: AdError) { ad = null; preload(activity); onClosed() }
        }
        current.show(activity) { onRewarded() }
    }
}

/**
 * تحميل إعلان Native واحد لعرضه داخل قائمة المنشورات.
 */
object NativeAdManager {
    private val cached = mutableMapOf<String, NativeAd?>()

    /** توزيع وحدتي النيتف على المواضع — ترتيب ثابت. */
    fun unitFor(slot: String): String {
        val units = listOf(AppAds.NATIVE_AD_UNIT_ID, AppAds.NATIVE_AD_UNIT_2)
        return units[(slot.hashCode() and Int.MAX_VALUE) % units.size]
    }

    fun get(slot: String): NativeAd? = cached[unitFor(slot)]

    fun preload(context: Context, slot: String = "posts", onLoaded: (() -> Unit)? = null) {
        val unit = unitFor(slot)
        if (cached[unit] != null) return
        val loader = com.google.android.gms.ads.AdLoader.Builder(context, unit)
            .forNativeAd { nativeAd ->
                if (cached[unit] != null) nativeAd.destroy()
                else { cached[unit] = nativeAd; onLoaded?.invoke() }
            }
            .withNativeAdOptions(NativeAdOptions.Builder().build())
            .withAdListener(object : com.google.android.gms.ads.AdListener() {
                override fun onAdFailedToLoad(e: LoadAdError) { /* يبقى فارغاً بصمت */ }
            })
            .build()
        loader.loadAd(AdRequest.Builder().build())
    }
}

/**
 * بوابة الأقسام المهمة (فلاش/رومات/دامب): إعلان Rewarded كامل
 * في كل دخول — لا يُفتح القسم إلا بعد مشاهدة كاملة.
 */
object FlashRewardManager {
    private var ad: com.google.android.gms.ads.rewarded.RewardedAd? = null
    private var loading = false

    fun preload(context: Context) {
        if (ad != null || loading) return
        loading = true
        com.google.android.gms.ads.rewarded.RewardedAd.load(
            context, AppAds.REWARDED_FLASH_UNIT_ID, AdRequest.Builder().build(),
            object : com.google.android.gms.ads.rewarded.RewardedAdLoadCallback() {
                override fun onAdLoaded(a: com.google.android.gms.ads.rewarded.RewardedAd) { ad = a; loading = false }
                override fun onAdFailedToLoad(e: LoadAdError) { ad = null; loading = false }
            }
        )
    }

    fun isReady(): Boolean = ad != null

    fun show(activity: Activity, onRewarded: () -> Unit, onClosed: () -> Unit) {
        val current = ad ?: run { preload(activity); onClosed(); return }
        current.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() { ad = null; preload(activity); onClosed() }
            override fun onAdFailedToShowFullScreenContent(e: AdError) { ad = null; preload(activity); onClosed() }
        }
        current.show(activity) { onRewarded() }
    }
}
