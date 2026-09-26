package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.AppUpdateConfig
import com.example.network.VercelUpdateManager
import com.example.ui.components.AdBannerPlaceholder
import com.example.ui.components.GlassmorphicBottomBar
import com.example.ui.components.TechnicianDrawerSheet
import com.example.ui.theme.*
import kotlinx.coroutines.launch

data class HomeCategoryItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val cardColor: Color,
    val onClick: () -> Unit
)

data class MaintenancePost(
    val id: String,
    val title: String,
    val category: String,
    val date: String,
    val excerpt: String,
    val fullContent: String,
    val targetUrl: String? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    config: AppUpdateConfig,
    updateManager: VercelUpdateManager,
    onNavigateToCategory: (String) -> Unit,
    onNavigateToSettings: () -> Unit,
    onOpenUrlInApp: (url: String, title: String) -> Unit,
    onDismissBroadcast: () -> Unit,
    initialTab: Int = 0
) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // 0 = قسم الخدمات, 1 = قسم المنشورات
    val pagerState = androidx.compose.foundation.pager.rememberPagerState(initialPage = initialTab) { 2 }
    var searchQuery by remember { mutableStateOf("") }
    var selectedPostForDialog by remember { mutableStateOf<MaintenancePost?>(null) }

    val categories = remember {
        listOf(
            HomeCategoryItem(
                id = "screens",
                title = "مقارنة الشاشات",
                subtitle = "OLED vs AMOLED vs IPS ومشاكل اللمس والـ IC",
                icon = Icons.Default.Tv,
                cardColor = ColorCategoryScreens,
                onClick = { onNavigateToCategory("screens") }
            ),
            HomeCategoryItem(
                id = "devices",
                title = "مقارنة الأجهزة",
                subtitle = "المواصفات، المعالجات، وأعطال البوردات الشائعة",
                icon = Icons.Default.Smartphone,
                cardColor = ColorCategoryDevices,
                onClick = { onNavigateToCategory("devices") }
            ),
            HomeCategoryItem(
                id = "test_points",
                title = "نقاط التيست بوينت",
                subtitle = "EDL 9008 و BROM وملف صور شيميرا للتحميل",
                icon = Icons.Default.ElectricalServices,
                cardColor = ColorCategoryTestPoints,
                onClick = { onNavigateToCategory("test_points") }
            ),
            HomeCategoryItem(
                id = "software",
                title = "دورات السوفت وير",
                subtitle = "التفليش، تخطي FRP، وإصلاح السيريال والشبكة",
                icon = Icons.AutoMirrored.Filled.MenuBook,
                cardColor = ColorCategorySoftware,
                onClick = { onNavigateToCategory("software") }
            ),
            HomeCategoryItem(
                id = "screen_compat",
                title = "توافقات الشاشات",
                subtitle = "دليل الشاشات المتطابقة والبدائل المضمونة",
                icon = Icons.Default.SwapHoriz,
                cardColor = ColorCategoryScreenCompat,
                onClick = { onNavigateToCategory("screen_compat") }
            ),
            HomeCategoryItem(
                id = "systems",
                title = "شرح معنى الأنظمة",
                subtitle = "Fastboot, Recovery, EDL, BROM, DFU والأوامر",
                icon = Icons.Default.DeveloperMode,
                cardColor = ColorCategorySystems,
                onClick = { onNavigateToCategory("systems") }
            ),
            HomeCategoryItem(
                id = "device_models",
                title = "موديلات الأجهزة",
                subtitle = "تحويل كود المصنع إلى الاسم التجاري فوراً",
                icon = Icons.Default.QrCodeScanner,
                cardColor = ColorCategoryDeviceModels,
                onClick = { onNavigateToCategory("device_models") }
            ),
            HomeCategoryItem(
                id = "imei_check",
                title = "فحص IMEI والأجهزة",
                subtitle = "تشيك آيفون، شاومي، سامسونج، والشبكات داخل التطبيق",
                icon = Icons.Default.VerifiedUser,
                cardColor = ColorCategoryImeiCheck,
                onClick = { onNavigateToCategory("imei_check") }
            ),
            HomeCategoryItem(
                id = "rom_downloads",
                title = "تحميل أنظمة الأجهزة",
                subtitle = "مواقع تحميل رومات وفلاشات شاومي، سامسونج، أوبو، وتكنو",
                icon = Icons.Default.CloudDownload,
                cardColor = ColorCategoryRoms,
                onClick = { onNavigateToCategory("rom_downloads") }
            ),
            HomeCategoryItem(
                id = "secret_codes",
                title = "أكواد خيارات المطور",
                subtitle = "رموز تفعيل المطور وقوائم الفحص للأجهزة المقفلة",
                icon = Icons.Default.Password,
                cardColor = ColorCategoryCodes,
                onClick = { onNavigateToCategory("secret_codes") }
            ),
            HomeCategoryItem(
                id = "software_tools",
                title = "أدوات السوفت وير",
                subtitle = "برامج UnlockTool, Chimera, Odin, Mi Flash, MTK",
                icon = Icons.Default.Handyman,
                cardColor = ColorCategoryTools,
                onClick = { onNavigateToCategory("software_tools") }
            ),
            HomeCategoryItem(
                id = "dump_collection",
                title = "قسم ملفات الدامب",
                subtitle = "تجميعة Google Pixel 2026 ودامبات البوت للأجهزة",
                icon = Icons.Default.Save,
                cardColor = ColorCategoryDumps,
                onClick = { onNavigateToCategory("dump_collection") }
            ),
            HomeCategoryItem(
                id = "flash",
                title = "قسم فلاش YAZ",
                subtitle = "تفليش الأجهزة ووضع Odin لأجهزة سامسونج",
                icon = Icons.Default.Bolt,
                cardColor = ColorCategoryFlash,
                onClick = { onNavigateToCategory("flash") }
            )
        )
    }

    val posts = remember {
        listOf(
            MaintenancePost(
                id = "post_blog",
                title = "زيارة وتصفح مدونة الهاتف والصيانة الرسمية (Yaz Blog)",
                category = "المدونة الرسمية",
                date = "2026-09-26",
                excerpt = "المقالات والشروحات التقنية المحدثة أولاً بأول لحلول أعطال الهاردوير والسوفت وير.",
                fullContent = "مدونة متخصصة في شروحات صيانة المحمول وحلول مشاكل المعالجات والذاكرة ومسارات الدوائر الإلكترونية.",
                targetUrl = "https://yaz-blog.blogspot.com/"
            ),
            MaintenancePost(
                id = "post_poco_x3",
                title = "الحل النهائي لعطل شبلنة معالج Poco X3 Pro وإحياء الهاتف الميت",
                category = "هاردوير",
                date = "2026-09-25",
                excerpt = "خطوات رفع الرام والمعالج بدون انتفاخ اللوحة، وضبط حرارة الهوت إير على 330 درجة وفلكس أصلي.",
                fullContent = "يعد جهاز Poco X3 Pro من أشهر الأجهزة التي تعاني من انفصال كرات اللحام تحت معالج Snapdragon 860. يلزم استخدام شبلونة دقيقة 0.12 ملم، وتنظيف الأرضي بشيلد نحاسي ناعم، ووضع معجون حراري كربوني عالي الكفاءة عند إعادة التجميع لضمان عدم عودة العطل."
            ),
            MaintenancePost(
                id = "post_xiaomi_cloud",
                title = "طريقة فك وتجاوز حساب شاومي Mi Cloud واستخدام الموقع الرسمي",
                category = "سوفت وير",
                date = "2026-09-23",
                excerpt = "فصل ميزة Find Device برقم الهاتف أو التخطي عبر سيرفرات شاومي الرسمية بدون بوكسات.",
                fullContent = "يمكن إزالة قفل حساب شاومي رسمياً عبر الرابط us.i.mi.com/mobile/find#/ إذا توفر رقم الهاتف، أو عبر وضع السايد لود Sideload بأدوات Mi Assistant لمعالجات كوالكوم وميدياتيك."
            ),
            MaintenancePost(
                id = "post_iccid_turbo",
                title = "أحدث شفرات وأكواد الـ ICCID النشطة لتشغيل شرائح التوربو سيم",
                category = "شبكات",
                date = "2026-09-20",
                excerpt = "تحديثات دورية لأكواد الـ ICCID لتشغيل شرائح RSIM و MKSD لفك قفل شبكات الآيفون المقيد.",
                fullContent = "تابع دائماً تحديثات الـ ICCID عبر موقع CMD99 المتاح في قسم فحص IMEI؛ حيث يتم إدخال الكود المكون من 20 رقماً عبر القائمة السرية للشريحة ليعمل الهاتف كجهاز مفتوح رسمي."
            ),
            MaintenancePost(
                id = "post_screen_flex",
                title = "نصائح فنية هامة عند تركيب الشاشات التجارية لتجنب مشكلة الـ Ghost Touch",
                category = "شاشات",
                date = "2026-09-18",
                excerpt = "عزل فلاتة الشاشة بشريط كابتون (Kapton Tape) وعدم الإفراط في الشد بالملاقط.",
                fullContent = "أغلب مشاكل جنون اللمس التي تحدث أثناء وضع الهاتف على الشاحن سببها عدم عزل فلاتة الشاشة عن الشاسيه المعدني أو استخدام شاشات Incell رديئة تسحب تياراً زائداً من دارة التغذية."
            )
        )
    }

    val filteredCategories = remember(searchQuery) {
        if (searchQuery.isBlank()) categories
        else categories.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
            it.subtitle.contains(searchQuery, ignoreCase = true)
        }
    }

    // إعلان بين كل قسمين — ترتيب ثابت يعمل تلقائياً لأي أقسام تُضاف مستقبلاً
    val gridEntries = remember(filteredCategories) {
        buildList<Any> {
            filteredCategories.forEachIndexed { index, cat ->
                add(cat)
                if ((index + 1) % 2 == 0) add("AD_AFTER_$index")
            }
        }
    }

    val filteredPosts = remember(searchQuery) {
        if (searchQuery.isBlank()) posts
        else posts.filter {
            it.title.contains(searchQuery, ignoreCase = true) ||
            it.excerpt.contains(searchQuery, ignoreCase = true) ||
            it.category.contains(searchQuery, ignoreCase = true)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            TechnicianDrawerSheet(
                onNavigate = { screenId ->
                    if (screenId == "posts") {
                        scope.launch { pagerState.animateScrollToPage(1) }
                    } else if (screenId == "home") {
                        scope.launch { pagerState.animateScrollToPage(0) }
                    } else {
                        onNavigateToCategory(screenId)
                    }
                },
                onOpenBlog = {
                    onOpenUrlInApp("https://yaz-blog.blogspot.com/", "مدونة الهاتف والصيانة")
                },
                onCloseDrawer = {
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = CircleShape,
                                color = PrimaryBlue,
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Build,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Phone Traffic",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF0F172A)
                                    )
                                )
                                Text(
                                    text = "فون ترافيك - دليل فني الصيانة",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = PrimaryBlue,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "القائمة الجانبية",
                                tint = Color(0xFF0F172A)
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = onNavigateToSettings) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "الإعدادات وعن التطبيق",
                                tint = Color(0xFF0F172A)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                )
            },
            bottomBar = {
                GlassmorphicBottomBar(
                    currentScreen = if (pagerState.currentPage == 0) "home" else "posts",
                    onNavigate = { screenId ->
                        when (screenId) {
                            "home" -> scope.launch { pagerState.animateScrollToPage(0) }
                            "posts" -> scope.launch { pagerState.animateScrollToPage(1) }
                            "settings" -> onNavigateToSettings()
                            else -> onNavigateToCategory(screenId)
                        }
                    }
                )
            }
        ) { innerPadding ->
            val isRefreshing by updateManager.isChecking.collectAsState()
            // السحب من فوق للأسفل لتحديث البيانات من السيرفر
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = { updateManager.checkForUpdates(scope) },
                state = rememberPullToRefreshState(),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(BackgroundLight)
            ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Top Tab Selector: "قسم الخدمات" و "قسم المنشورات"
                Surface(
                    color = Color.White,
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TabRow(
                        selectedTabIndex = pagerState.currentPage,
                        containerColor = Color.White,
                        contentColor = PrimaryBlue,
                        indicator = { tabPositions ->
                            TabRowDefaults.SecondaryIndicator(
                                Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                                color = PrimaryBlue,
                                height = 3.dp
                            )
                        }
                    ) {
                        Tab(
                            selected = pagerState.currentPage == 0,
                            onClick = { scope.launch { pagerState.animateScrollToPage(0) } },
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.HomeRepairService,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "قسم الخدمات",
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                    )
                                }
                            }
                        )
                        Tab(
                            selected = pagerState.currentPage == 1,
                            onClick = { scope.launch { pagerState.animateScrollToPage(1) } },
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Newspaper,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "قسم المنشورات والمدونة",
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                    )
                                }
                            }
                        )
                    }
                }

                // Main Tab Content with HorizontalPager allowing swipe or click
                androidx.compose.foundation.pager.HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    if (page == 0) {
                    // TAB 0: قسم الخدمات (12 Grid Squares)
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(160.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Hero Banner
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(130.dp)
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.phone_repair_hero_1790449400770),
                                            contentDescription = "Phone Maintenance Hero",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(
                                                    Brush.verticalGradient(
                                                        colors = listOf(
                                                            Color.Transparent,
                                                            Color(0xCC0D47A1)
                                                        )
                                                    )
                                                )
                                        )
                                        Column(
                                            modifier = Modifier
                                                .align(Alignment.BottomStart)
                                                .padding(14.dp)
                                        ) {
                                            Text(
                                                text = "منصة فنيي الصيانة الاحترافية",
                                                style = MaterialTheme.typography.titleMedium.copy(
                                                    color = Color.White,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            )
                                            Text(
                                                text = "حلول الهاردوير والسوفت وير وفحص الأجهزة الفوري",
                                                style = MaterialTheme.typography.bodySmall.copy(
                                                    color = Color(0xFFE2E8F0)
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Broadcast Announcement Banner if active
                        if (config.isBroadcastActive && config.broadcastTitle.isNotBlank()) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Card(
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF93C5FD)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Campaign,
                                            contentDescription = null,
                                            tint = PrimaryBlue,
                                            modifier = Modifier.size(28.dp)
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = config.broadcastTitle,
                                                style = MaterialTheme.typography.labelLarge.copy(
                                                    color = PrimaryBlueDark,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            )
                                            if (config.broadcastMessage.isNotBlank()) {
                                                Text(
                                                    text = config.broadcastMessage,
                                                    style = MaterialTheme.typography.bodySmall.copy(
                                                        color = Color(0xFF1E293B)
                                                    )
                                                )
                                            }
                                        }
                                        IconButton(onClick = onDismissBroadcast) {
                                            Icon(
                                                imageVector = Icons.Default.Close,
                                                contentDescription = "إغلاق",
                                                tint = Color.Black,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Search Bar
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                placeholder = {
                                    Text(
                                        text = "ابحث في الأقسام والخدمات...",
                                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "بحث",
                                        tint = PrimaryBlue
                                    )
                                },
                                trailingIcon = {
                                    if (searchQuery.isNotEmpty()) {
                                        IconButton(onClick = { searchQuery = "" }) {
                                            Icon(
                                                imageVector = Icons.Default.Clear,
                                                contentDescription = "مسح",
                                                tint = Color.Black
                                            )
                                        }
                                    }
                                },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    focusedBorderColor = PrimaryBlue,
                                    unfocusedBorderColor = Color(0xFFE2E8F0)
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        // Header for Categories
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "الأقسام والخدمات",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF0F172A)
                                    )
                                )
                                Text(
                                    text = "12 قسماً شاملاً لصيانة المحمول",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color.Black
                                    )
                                )
                            }
                        }

                        // Inline Ad Placement (AdSense) - أعلى الشبكة
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            AdBannerPlaceholder(adSlotName = "إعلان - الصفحة الرئيسية")
                        }

                        // إعلان بين كل قسمين — قائمة مدمجة بترتيب ثابت لأي أقسام مستقبلية
                        items(
                            gridEntries,
                            key = { entry -> if (entry is HomeCategoryItem) entry.id else entry.toString() },
                            span = { entry -> if (entry is HomeCategoryItem) GridItemSpan(1) else GridItemSpan(maxLineSpan) }
                        ) { entry ->
                            if (entry is String) {
                                AdBannerPlaceholder(adSlotName = "إعلان - بين أقسام الخدمات")
                            } else {
                                val cat = entry as HomeCategoryItem
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(160.dp)
                                    .clickable { cat.onClick() },
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                border = androidx.compose.foundation.BorderStroke(1.5.dp, cat.cardColor.copy(alpha = 0.35f)),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(14.dp),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(46.dp)
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(cat.cardColor),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = cat.icon,
                                                contentDescription = cat.title,
                                                tint = Color.White,
                                                modifier = Modifier.size(26.dp)
                                            )
                                        }

                                        Box(
                                            modifier = Modifier
                                                .size(24.dp)
                                                .clip(CircleShape)
                                                .background(cat.cardColor.copy(alpha = 0.12f)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.ArrowOutward,
                                                contentDescription = null,
                                                tint = cat.cardColor,
                                                modifier = Modifier.size(14.dp)
                                            )
                                        }
                                    }

                                    Column {
                                        Text(
                                            text = cat.title,
                                            style = MaterialTheme.typography.titleSmall.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF0F172A),
                                                fontSize = 15.sp
                                            ),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Spacer(modifier = Modifier.height(3.dp))
                                        Text(
                                            text = cat.subtitle,
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                color = Color.Black,
                                                fontSize = 11.sp,
                                                lineHeight = 15.sp
                                            ),
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
                        }
                        }

                        // In-Screen Ad Banner
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            AdBannerPlaceholder(adSlotName = "إعلان - قسم الخدمات الرئيسي")
                        }
                    }
                } else {
                    // TAB 1: قسم المنشورات والمدونة (Posts & Blog)
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Official Blog Feature Card
                        item {
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFFF5722)),
                                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(18.dp)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(46.dp)
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(Color(0xFFFF5722)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.RssFeed,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(26.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column {
                                            Text(
                                                text = "مدونة التطبيق الرسمية",
                                                style = MaterialTheme.typography.titleMedium.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF0F172A)
                                                )
                                            )
                                            Text(
                                                text = "yaz-blog.blogspot.com",
                                                style = MaterialTheme.typography.bodySmall.copy(
                                                    color = Color(0xFFFF5722),
                                                    fontWeight = FontWeight.Bold
                                                )
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = "تابع أحدث المقالات والحلول الحصرية لأعطال الهاردوير والسوفت وير وتحديثات البوكسات والأدوات.",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = Color.Black,
                                            lineHeight = 20.sp
                                        )
                                    )

                                    Spacer(modifier = Modifier.height(14.dp))

                                    Button(
                                        onClick = {
                                            onOpenUrlInApp("https://yaz-blog.blogspot.com/", "مدونة الهاتف والصيانة")
                                        },
                                        shape = RoundedCornerShape(10.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFFFF5722),
                                            contentColor = Color.White
                                        ),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(Icons.Default.OpenInBrowser, contentDescription = null, modifier = Modifier.size(18.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "تصفح المدونة داخل التطبيق الآن",
                                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                                        )
                                    }
                                }
                            }
                        }

                        // Posts Header
                        item {
                            Text(
                                text = "أحدث منشورات ومقالات الصيانة",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0F172A)
                                )
                            )
                        }

                        // Posts List
                        items(filteredPosts, key = { it.id }) { post ->
                            Card(
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        if (post.targetUrl != null) {
                                            onOpenUrlInApp(post.targetUrl, post.title)
                                        } else {
                                            selectedPostForDialog = post
                                        }
                                    }
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    // إعلان أول المنشور
                                    AdBannerPlaceholder(adSlotName = "إعلان - أول المنشور")
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = PrimaryBlue.copy(alpha = 0.12f)
                                        ) {
                                            Text(
                                                text = post.category,
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    color = PrimaryBlue,
                                                    fontWeight = FontWeight.Bold
                                                ),
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                            )
                                        }
                                        Text(
                                            text = post.date,
                                            style = MaterialTheme.typography.labelSmall.copy(color = Color.Black)
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = post.title,
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF0F172A)
                                        )
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    // إعلان وسط المنشور
                                    AdBannerPlaceholder(adSlotName = "إعلان - وسط المنشور")
                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = post.excerpt,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = Color.Black,
                                            lineHeight = 18.sp
                                        )
                                    )

                                    Spacer(modifier = Modifier.height(10.dp))

                                    // إعلان آخر المنشور
                                    AdBannerPlaceholder(adSlotName = "إعلان - آخر المنشور")
                                    Spacer(modifier = Modifier.height(10.dp))

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.End,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = if (post.targetUrl != null) "فتح الرابط" else "قراءة المقال بالكامل",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = PrimaryBlue,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            imageVector = Icons.Default.ArrowBack,
                                            contentDescription = null,
                                            tint = PrimaryBlue,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                            }
                        }

                        // In-Screen Ad Banner
                        item {
                            AdBannerPlaceholder(adSlotName = "إعلان - قسم المنشورات والمدونة")
                        }
                    }
                }
                }
            }
            }

            // Post Details Modal Dialog
            selectedPostForDialog?.let { post ->
                AlertDialog(
                    onDismissRequest = { selectedPostForDialog = null },
                    title = {
                        Text(
                            text = post.title,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    },
                    text = {
                        Column {
                            // إعلان أول المقال
                            AdBannerPlaceholder(adSlotName = "إعلان - أول المقال")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "التصنيف: ${post.category} • التاريخ: ${post.date}",
                                style = MaterialTheme.typography.labelSmall.copy(color = PrimaryBlue, fontWeight = FontWeight.Bold)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            val words = remember(post.id) { post.fullContent.split(" ") }
                            val mid = (words.size + 1) / 2
                            Text(
                                text = words.take(mid).joinToString(" "),
                                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp, color = Color(0xFF1E293B))
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            // إعلان وسط المقال
                            AdBannerPlaceholder(adSlotName = "إعلان - وسط المقال")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = words.drop(mid).joinToString(" "),
                                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp, color = Color(0xFF1E293B))
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            // إعلان آخر المقال
                            AdBannerPlaceholder(adSlotName = "إعلان - آخر المقال")
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = { selectedPostForDialog = null },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
                        ) {
                            Text("إغلاق")
                        }
                    }
                )
            }
        }
    }
}
