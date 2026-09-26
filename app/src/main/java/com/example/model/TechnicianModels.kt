package com.example.model

/**
 * Data models for the Phone Traffic technician maintenance application.
 */

enum class ScreenCategory {
    ALL, OLED, AMOLED, IPS_LCD, INCELL, COPY_GRADES
}

enum class DeviceBrand {
    ALL, APPLE, SAMSUNG, XIAOMI, HUAWEI, OPPO, REALME, INFINIX_TECNO, VIVO
}

data class ScreenTechSpec(
    val id: String,
    val title: String,
    val technology: String,
    val structure: String,
    val brightness: String,
    val powerEfficiency: String,
    val refreshRate: String,
    val touchResponse: String,
    val originalVsCopyDetails: String,
    val icSwapRequired: Boolean,
    val icSwapDetails: String,
    val trueToneDetails: String,
    val commonRepairFaults: List<String>,
    val pros: List<String>,
    val cons: List<String>,
    val technicianRecommendation: String
)

data class CommonFault(
    val symptom: String,
    val likelyComponent: String,
    val solutionGuide: String
)

data class DeviceSpecItem(
    val id: String,
    val brand: DeviceBrand,
    val modelName: String,
    val modelCode: String,
    val cpuChipset: String,
    val pmicChip: String,
    val screenType: String,
    val chargingSpeed: String,
    val batteryCapacity: String,
    val commonFaults: List<CommonFault>,
    val motherboardRepairNotes: String
)

data class TestPointItem(
    val id: String,
    val brand: DeviceBrand,
    val modelName: String,
    val modeType: String, // EDL 9008, MTK BROM, Kirin TestPoint, Exynos EDL
    val chipset: String,
    val pinoutDescription: String,
    val connectionSteps: List<String>,
    val requiresBattery: Boolean,
    val cautionNote: String,
    val deviceManagerPort: String
)

data class SoftwareCourseItem(
    val id: String,
    val title: String,
    val level: String, // مبتدئ، متوسط، متقدم
    val estimatedDuration: String,
    val requiredTools: List<String>,
    val summary: String,
    val detailedSteps: List<String>,
    val commonErrors: List<Pair<String, String>>, // Error -> Solution
    val goldenRule: String
)

data class ScreenCompatibilityItem(
    val id: String,
    val brand: DeviceBrand,
    val primaryModel: String,
    val compatibleModels: List<String>,
    val displayType: String,
    val withFrameOrFlexOnly: String,
    val importantNotes: String,
    val verifiedByTechnicians: Boolean = true
)

data class SystemModeItem(
    val id: String,
    val nameEn: String,
    val nameAr: String,
    val supportedPlatforms: String,
    val purpose: String,
    val enterKeyCombination: String,
    val enterTerminalCommand: String,
    val howToExit: String,
    val commonIssues: String,
    val keyCommands: List<String>
)

data class DeviceCodeItem(
    val id: String,
    val brand: DeviceBrand,
    val factoryCode: String,
    val commercialName: String,
    val processor: String,
    val releaseYear: String,
    val notes: String
)

data class ImeiCheckSiteItem(
    val id: String,
    val title: String,
    val brandCategory: DeviceBrand,
    val url: String,
    val description: String,
    val badge: String,
    val isOfficial: Boolean
)

data class RomDownloadSite(
    val id: String,
    val title: String,
    val url: String,
    val brandTarget: String,
    val description: String,
    val badge: String
)

data class SecretCodeItem(
    val id: String,
    val brandName: String,
    val primaryCode: String,
    val alternativeCode: String? = null,
    val description: String = "تفعيل خيارات المطور (Developer Options) وقوائم فحص الهاردوير للأجهزة المقفلة"
)

data class SoftwareToolItem(
    val id: String,
    val name: String,
    val toolType: String, // أداة احترافية، مجانية، رسمية
    val description: String,
    val supportedCPUs: String,
    val officialUrl: String,
    val mainCapabilities: List<String>
)

data class DumpItem(
    val id: String,
    val title: String,
    val deviceModel: String,
    val cpuAndChipset: String,
    val memoryType: String, // eMMC, UFS 2.2, UFS 3.1, UFS 4.0
    val downloadUrl: String,
    val description: String,
    val isHighlighted: Boolean = false
)

data class AppUpdateConfig(
    val currentVersionCode: Int = 1,
    val latestVersionCode: Int = 2,
    val latestVersionName: String = "1.1.0",
    val isMandatory: Boolean = false,
    val updateTitle: String = "تحديث جديد متوفر لتطبيق فون ترافيك",
    val updateMessage: String = "تمت إضافة توافقات شاشات جديدة، مخططات تيست بوينت لعام 2025/2026، وتحديث روابط تشيك الـ IMEI.",
    val downloadUrl: String = "https://phone-traffic.vercel.app/download",
    val broadcastTitle: String = "تنبيه فني هام 🔔",
    val broadcastMessage: String = "تم تحديث روابط فحص الآيكلاود وشاومي لتوفير أسرع استجابة للفحص بدون كابتشا.",
    val isBroadcastActive: Boolean = true
)
