package com.example.data

import com.example.model.*

object TechnicianDataProvider {

    // 1. مقارنة الشاشات (Screen Comparison)
    val screenTechSpecs = listOf(
        ScreenTechSpec(
            id = "scr_oled",
            title = "شاشات OLED (Organic Light Emitting Diode)",
            technology = "OLED الأصلية",
            structure = "طبقات عضوية مضيئة ذاتياً لكل بكسل، بدون حاجة لإضاءة خلفية (Backlight). فلاتة اللمس مدمجة مباشرة باللوح العضوي.",
            brightness = "من 800 إلى 2000+ شمعة (Nits) في أشعة الشمس المباشرة.",
            powerEfficiency = "ممتازة جداً، البكسلات السوداء مطفأة تماماً وتستهلك 0 واط.",
            refreshRate = "تدعم 60Hz حتى 120Hz و LTPO التكيفي.",
            touchResponse = "استجابة فائقة (حتى 360Hz Sampling Rate).",
            originalVsCopyDetails = "الشاشات الأصلية مرنة ورفيعة جداً، بينما النسخ المقلدة (TFT/Incell البديلة) تكون سميكة وتضغط على الإطار مما يسبب كسرها بسهولة.",
            icSwapRequired = true,
            icSwapDetails = "في الآيفون (من 11 إلى 15 Pro Max) يلزم نقل آيسي الشاشة الأصلي (Touch IC) أو عمل reprogram عبر جهاز QianLi / iCopy لمنع ظهور رسالة 'Important Display Message'.",
            trueToneDetails = "تحتاج قراءة كود الـ MtSN من الشاشة القديمة وكتابته على الشاشة الجديدة عبر مبرمجة لدعم ميزة True Tone وحساس الإضاءة.",
            commonRepairFaults = listOf(
                "ظهور خط أخضر أو بنفسجي طولي بسبب كسر داخلي عند زاوية الفلاتة (Bonding Damage)",
                "شاشة سوداء مع عمل الهاتف (استقبال اتصالات) بسبب تلف طبقة الـ OLED وبقاء اللمس سليم",
                "وميض أخضر عند تقليل السطوع (تلف متحكم الإضاءة PWM)"
            ),
            pros = listOf("ألوان وسواد حقيقي 100%", "سمك نحيف جداً يطابق الشاسيه الأصلي", "توفير ممتاز للبطارية مع الوضع الليلي"),
            cons = listOf("سعر مرتفع لقطع الغيار", "حساسة للضغط عند تركيب الإطار", "احتمال الاحتراق البكسلي (Burn-in) على المدى البعيد"),
            technicianRecommendation = "استخدم دائماً لاصق B-7000 أو T-7000 بحذر ولا تضغط على زوايا الفلاتة السفلية. افحص اللمس قبل تثبيت اللاصق نهائياً."
        ),
        ScreenTechSpec(
            id = "scr_amoled",
            title = "شاشات Super AMOLED / Dynamic AMOLED",
            technology = "AMOLED (Active Matrix OLED)",
            structure = "مصفوفة عضوية نشطة، مع دمج مستشعر اللمس Digitizer في نفس الطبقة العضوية دون فجوة هوائية.",
            brightness = "تصل إلى 1750 - 2600 شمعة في شاشات سامسونج الرائدة الحديثة.",
            powerEfficiency = "عالية جداً مع دعم التردد المتغير LTPO من 1Hz إلى 120Hz.",
            refreshRate = "120Hz / 144Hz سلسة جداً.",
            touchResponse = "ممتازة حتى 480Hz في وضع الألعاب.",
            originalVsCopyDetails = "احذر من نسخ الـ Incell التي تباع كبديل للـ AMOLED في أجهزة سامسونج الفئة A؛ شاشات الـ Incell تستهلك بطارية مضاعفة وتعطل بصمة الشاشة المدمجة تماماً.",
            icSwapRequired = false,
            icSwapDetails = "في سامسونج لا توجد رسالة تحذيرية، ولكن يلزم إعادة معايرة بصمة الإصبع المدمجة تحت الشاشة (Fingerprint Calibration) عبر كود *#0*# أو عبر برامج الصيانة.",
            trueToneDetails = "تدعم ضبط الألوان التلقائي وتفعيل ميزة Always On Display بدون استنزاف البطارية.",
            commonRepairFaults = listOf(
                "تعطل حساس البصمة البصري تحت الشاشة عند استخدام شاشة كوبي بدون عدسة البصمة",
                "ظهور بقعة حبر سوداء (Bleeding) تكبر تدريجياً نتيجة كسر داخلي دقيق",
                "وميض الشاشة عند تفعيل Always On Display في الشاشات التجارية"
            ),
            pros = listOf("دعم البصمة المدمجة تحت الشاشة", "سطوع خارق وزوايا رؤية 180 درجة", "استجابة لمس خيالية"),
            cons = listOf("سعر الشاشة الأصلية يقارب نصف ثمن الجهاز أحياناً", "لا تقبل الإصلاح في حال كسر اللوح الداخلي"),
            technicianRecommendation = "نبه الزبون دائماً أن شاشة الـ Incell الرخيصة ستلغي البصمة وتثقل الهاتف، وننصح بتركيب شاشة OLED أصلية Service Pack."
        ),
        ScreenTechSpec(
            id = "scr_ips",
            title = "شاشات IPS LCD (In-Plane Switching)",
            technology = "IPS LCD التقليدية",
            structure = "بلورات سائلة متراصفة أفقياً مع طبقة إضاءة خلفية كاملة (LED Backlight Panel).",
            brightness = "من 400 إلى 650 شمعة.",
            powerEfficiency = "متوسطة إلى منخفضة لأن الإضاءة الخلفية تعمل دائماً بكامل طاقتها حتى مع اللون الأسود.",
            refreshRate = "60Hz إلى 120Hz.",
            touchResponse = "جيدة جداً ومستقرة.",
            originalVsCopyDetails = "متوفرة بكثرة بنوعيات: أصلية وكالة، وتجارية درجات (A, AAA). الشاشات المقلدة الرديئة تعاني من تسريب إضاءة على الحواف (Backlight Bleed).",
            icSwapRequired = false,
            icSwapDetails = "لا تحتاج نقل آيسي في معظم أجهزة الأندرويد، متوافقة برمجياً ومباشرة.",
            trueToneDetails = "في آيفون 8 و XR و 11 تحتاج قراءة السيريال فقط لاسترجاع التروتون.",
            commonRepairFaults = listOf(
                "تسريب ضوء أبيض من زوايا الشاشة بسبب زيادة الضغط أو لاصق غير متساوٍ",
                "ظهور بقع بيضاء ساطعة على الخلفية البيضاء بسبب ضغط مسامير غير مناسبة أو بطارية منتفخة",
                "توقف الإضاءة (بقاء البيانات خافتة جداً) بسبب احتراق ملف أو دايود الإضاءة (Backlight Diode/Coil) على المذربورد"
            ),
            pros = listOf("سعر اقتصادي جداً وسهولة توفرها", "لا تعاني من مشكلة احتراق البكسلات (Burn-in)", "قوة تحمل أكبر للضغط السطحي"),
            cons = listOf("لون أسود مائل للرمادي", "سمك أكبر لوجود طبقة الباك لايت", "استهلاك أعلى للبطارية"),
            technicianRecommendation = "قبل استبدال الشاشة عند انقطاع الإضاءة، تأكد من سلامة مسار VREG_MSM ودائرة الباك لايت على البوردة، ولا تستخدم مسامير طويلة!"
        ),
        ScreenTechSpec(
            id = "scr_incell",
            title = "شاشات Incell البديلة التجارية",
            technology = "Incell LCD Replacement",
            structure = "دمج أقطاب اللمس داخل خلايا الـ LCD لتقليل السمك، وتستخدم كبديل تجاري رخيص لشاشات الـ OLED.",
            brightness = "من 350 إلى 500 شمعة كحد أقصى.",
            powerEfficiency = "ضعيفة، تسحب تياراً أعلى من المذربورد بنسبة 30% إلى 45%.",
            refreshRate = "غالباً 60Hz فقط.",
            touchResponse = "قد يحدث لاج أو تجمد مؤقت عند لمس نقاط متعددة أثناء الشحن.",
            originalVsCopyDetails = "مصممة للحلول الاقتصادية. حوافها أوسع (Bezels كبيرة)، وترتفع قليلاً عن مستوى الشاسيه الأصلي.",
            icSwapRequired = false,
            icSwapDetails = "لا تدعم البصمة المدمجة تحت الشاشة مطلقاً في أجهزة سامسونج وشاومي.",
            trueToneDetails = "قد لا تدعم كتابة السيريال في بعض الموديلات.",
            commonRepairFaults = listOf(
                "جنون اللمس (Ghost Touch) خصوصاً أثناء وضع الهاتف على الشاحن",
                "سخونة الهاتف السريعة بسبب استهلاك طاقة أعلى من دارة التغذية",
                "كسر زجاج الشاشة بسرعة من أدنى سقطة لبروزها خارج الإطار"
            ),
            pros = listOf("رخيصة التكلفة ومناسبة للزبائن ذوي الميزانية المنخفضة"),
            cons = listOf("ألوان باهتة", "حواف عريضة", "تعطيل مستشعر البصمة", "ضغط دائم على الشاسيه"),
            technicianRecommendation = "اشرح للزبون بوضوح الفارق بينها وبين شاشة الـ OLED الأصلية، واستخدم مادة لصق مرنة كـ T-7000 دون إفراط في الشد بالملاقط."
        ),
        ScreenTechSpec(
            id = "scr_copy_grades",
            title = "درجات الشاشات الكوبي التجارية (GX - JK - RJ - ZY)",
            technology = "Hard OLED & Soft OLED Copies",
            structure = "الدرجات المتقدمة لشاشات الآيفون وسامسونج: شاشات Soft OLED (مثل مصنع GX و JK) تحاكي الأصلية بالمرونة، بينما Hard OLED تكون صلبة سهلة الكسر.",
            brightness = "Soft OLED تصل إلى 800-900 شمعة، بينما Hard OLED حوالي 600 شمعة.",
            powerEfficiency = "الـ Soft OLED ممتازة ومقاربة للأصلي، بينما الـ Hard تستهلك طاقة أعلى قليلاً.",
            refreshRate = "تدعم 60Hz أو 120Hz حسب موديل الفئة Pro.",
            touchResponse = "GX و JK تقدمان استجابة لمس ممتازة بنسبة 95% من الأصلية.",
            originalVsCopyDetails = "تعتبر ماركات GX و JK و RJ أفضل بديل تجاري لشاشات آيفون X حتى 15 Pro Max. الـ Soft OLED تمنع كسر الشاشة الداخلي عند السقوط مقارنة بالـ Hard.",
            icSwapRequired = true,
            icSwapDetails = "تدعم فك ولحام الآيسي الأصلي بسهولة لاحتواء فلاتاتها على نقاط لحام مخصصة لعملية الـ IC Transfer.",
            trueToneDetails = "قابلة للبرمجة بنسبة 100% لنقل التروتون.",
            commonRepairFaults = listOf(
                "في الـ Hard OLED أي ضغطة قوية على الحافة السفلية تكسر البكسلات",
                "عدم ضبط حرارة الكاوي عند نقل الآيسي قد يؤدي لتلف الفلاتة",
                "ضرورة عزل فلاتة الشاشة بشريط كابتون (Kapton Tape) لمنع التلامس مع الشاسيه المعدني"
            ),
            pros = listOf("سعر يعادل ثلث سعر الشاشة الأصلية", "ألوان وسواد ممتاز مقارب للأصل بنسبة 90%", "إمكانية نقل الآيسي وإلغاء رسالة التنبيه"),
            cons = listOf("أقل سطوعاً في الشمس مقارنة بالأصلية الوكالة", "تحتاج فني ماهر في اللحام"),
            technicianRecommendation = "اختر دائماً Soft OLED من مصنع GX أو JK ولا تشترِ Hard OLED إذا كان الزبون يريد جودة وعمراً طويلاً لجهازه."
        )
    )

    // 2. مقارنة الأجهزة والأعطال الشائعة (Device Comparison & Hardware Faults)
    val deviceSpecs = listOf(
        DeviceSpecItem(
            id = "dev_redmi_note10pro",
            brand = DeviceBrand.XIAOMI,
            modelName = "Redmi Note 10 Pro",
            modelCode = "M2101K6G / Sweet",
            cpuChipset = "Qualcomm Snapdragon 732G (8 nm)",
            pmicChip = "PM7150 + PM7150L",
            screenType = "AMOLED 120Hz, HDR10, 1200 nits",
            chargingSpeed = "33W Fast Charging",
            batteryCapacity = "5020 mAh",
            commonFaults = listOf(
                CommonFault("فصل الصوت وسماعة الأذن والكاميرا فجأة", "تلف لحام أرجل المعالج (CPU Cold Solder)", "إعادة شبلنة وتركيب المعالج والرام (Reballing CPU/RAM) بدرجة حرارة 330-350C مع فلكس أصلي."),
                CommonFault("الهاتف يرست باستمرار أو يتوقف على شعار Mi", "عطل زر الباور أو تلف آيسي الباور PM7150", "تنظيف وفحص خط PWR_KEY، والتأكد من ممانعات خروج ملفات الـ Buck حول آيسي الباور."),
                CommonFault("الهاتف ميت ويسحب 0.05A على الباور سبلاي", "شورت في خط VPH_PWR أو تلف آيسي الشحن BQ25970", "حقن فولت 3.7V على خط VPH_PWR وكشف المكون الساخن بكاميرا حرارية أو بالروزين.")
            ),
            motherboardRepairNotes = "المعالج والرام مركب فوق بعض (Sandwich). استخدم شبلونة دقيقة 0.12 ملم ولا ترفع الحرارة أكثر من دقيقة لحماية اللوحة من الانتفاخ."
        ),
        DeviceSpecItem(
            id = "dev_poco_x3_pro",
            brand = DeviceBrand.XIAOMI,
            modelName = "Poco X3 Pro",
            modelCode = "M2102J20SG / Vayu",
            cpuChipset = "Qualcomm Snapdragon 860 (7 nm)",
            pmicChip = "PM8150 + PM8150A + PM8150B",
            screenType = "IPS LCD 120Hz, HDR10",
            chargingSpeed = "33W Turbo",
            batteryCapacity = "5160 mAh",
            commonFaults = listOf(
                CommonFault("موت مفاجئ للجهاز مع ظهور شاشة بيضاء أو رمادية مخططة", "انفصال كرات اللحام تحت معالج SD860", "الحل الجذري: شبلنة المعالج CPU + RAM مع استبدال معجون التبريد الحراري بنوع كربوني عالي الجودة."),
                CommonFault("يسحب 0.02A - 0.07A ثابتة على الباور سبلاي ويتعرف 9008 تلقائياً", "المعالج لا يقرأ ذاكرة الـ UFS", "شبلنة الذاكرة والمعالج أو تغيير آيسي الباور الأساسي PM8150."),
                CommonFault("لا يشحن ويسحب 0.45A فقط", "تلف آيسي الشحن SMB1395 أو فلاتة الربط السفلية", "تغيير فلاتة الشحن الرئيسية والتأكد من وصول 5V على مدخل آيسي الشحن.")
            ),
            motherboardRepairNotes = "من أشهر أجهزة الصيانة في العالم. مهارة شبلنة هذا الجهاز أساسية لكل فني سوفت وير وهاردوير."
        ),
        DeviceSpecItem(
            id = "dev_samsung_a52",
            brand = DeviceBrand.SAMSUNG,
            modelName = "Galaxy A52 / A52s 5G",
            modelCode = "SM-A525F / SM-A528B",
            cpuChipset = "Snapdragon 720G / 778G 5G",
            pmicChip = "PM7250B / PM6150",
            screenType = "Super AMOLED 90Hz / 120Hz",
            chargingSpeed = "25W Super Fast",
            batteryCapacity = "4500 mAh",
            commonFaults = listOf(
                CommonFault("توقف الشحن وظهور علامة مثلث أصفر أو حرارة بطارية مرتفعة", "عطل مقاومة الثرمستور الحراري (Thermistor)", "استبدال مقاومة الثرمستور في البوردة السفلية (Sub-board) أو تغيير لوحة الشحن كاملة بقطعة أصلية."),
                CommonFault("شاشة سوداء واللمس يعمل أو لا توجد إضاءة وبيانات", "تلف كونكتور الشاشة الرئيسي على اللوحة الأم (FPC Connector)", "إعادة لحام أرجل كونكتر الشاشة الرئيسي أو تغييره مع التأكد من سلامة خطوط MIPI."),
                CommonFault("إعادة تشغيل مستمر عند توصيل الشاحن", "شورت في آيسي الحماية OVP", "قفل مسار الـ OVP (Bypass) أو استبدال الآيسي.")
            ),
            motherboardRepairNotes = "البوردة حساسة جداً للتواء الشاسيه، تأكد من تسوية الشاسيه جيداً قبل تقفيل الهاتف."
        ),
        DeviceSpecItem(
            id = "dev_iphone_11",
            brand = DeviceBrand.APPLE,
            modelName = "iPhone 11",
            modelCode = "A2111 / A2221",
            cpuChipset = "Apple A13 Bionic (7 nm+)",
            pmicChip = "Apple Custom PMIC (Dialog/Cirrus)",
            screenType = "Liquid Retina IPS LCD",
            chargingSpeed = "18W PD Fast Charge",
            batteryCapacity = "3110 mAh",
            commonFaults = listOf(
                CommonFault("توقف الجهاز على شعار التفاحة ويعطي خطأ 4013 في الآيتونز", "تلف فلاتة السماعة العلوية وسنسور البروكسيمتي (Flood Illuminator)", "فصل فلاتة السماعة وتشغيل الهاتف بدونها، أو نقل سنسور الـ Face ID بحرص إلى فلاتة جديدة."),
                CommonFault("فقدان الشبكة بالكامل وتوقف الواي فاي والبيسباند غير معروف", "انفصال طبقات اللوحة الأم المزدوجة (Interposer Layer)", "فك اللوحة الأم على بريتر التسخين وإعادة شبلنة طبقات البوردة العلوية والسفلية (Reballing Middle Layer)."),
                CommonFault("تفريغ شحن سريع وسخونة تحت الكاميرا الخلفية", "شورت في مكثف على مسار VDD_MAIN", "حقن 3.8V على مسار VDD_MAIN واكتشاف المكثف التالف باستخدام الروزين.")
            ),
            motherboardRepairNotes = "انتبه عند فك الشاشة بعدم خدش فلاتة حساس الإضاءة الأمامي حتى لا تفقد الـ Face ID نهائياً."
        ),
        DeviceSpecItem(
            id = "dev_infinix_hot10",
            brand = DeviceBrand.INFINIX_TECNO,
            modelName = "Infinix Hot 10 / Hot 10 Play",
            modelCode = "X682 / X688",
            cpuChipset = "MediaTek Helio G70 / Helio G25",
            pmicChip = "MT6357CRV / MT6353",
            screenType = "IPS LCD 6.78-inch",
            chargingSpeed = "10W Standard",
            batteryCapacity = "5200 - 6000 mAh",
            commonFaults = listOf(
                CommonFault("الجهاز طافي تماماً ويسحب 0.12A متذبذبة", "تلف آيسي الباور MT6357CRV", "قياس ممانعات مخارج ملفات الـ VS1 و VCORE حول الآيسي، وتغيير الآيسي مباشرة."),
                CommonFault("الهاتف يعمل بدون إضاءة في الشاشة (بيانات خافتة)", "احتراق دايود أو ملف الإضاءة 22uH", "فحص خط LED+ والتأكد من رفع الجهد من 3.7V إلى 22V عند تشغيل الشاشة."),
                CommonFault("فقدان السيريال IMEI وظهور Unknown Baseband بعد السوفت وير", "تلف ملفات NVRAM و NVDATA في الذاكرة", "كتابة ملفات NVRAM أصلية عبر UnlockTool أو Pandora Box في وضع BROM.")
            ),
            motherboardRepairNotes = "أجهزة إنفينكس وتكنو تعتمد معالجات ميديا تيك بكثرة؛ دائماً خذ نسخة احتياطية من ملفات الشبكة قبل أي تفليش."
        )
    )

    // 3. نقاط التيست بوينت (Test Points - EDL 9008 & BROM & Kirin)
    val testPoints = listOf(
        TestPointItem(
            id = "tp_redmi_note8",
            brand = DeviceBrand.XIAOMI,
            modelName = "Redmi Note 8 (Ginkgo)",
            modeType = "Qualcomm EDL 9008",
            chipset = "Snapdragon 665",
            pinoutDescription = "نقطتان ذهبيتان صغيرتان متجاورتان تقعان بجانب كونكتر فلاتة بصمة الإصبع وأسفل الصاجة المعدنية العلوية مباشرة.",
            connectionSteps = listOf(
                "افصل كابل البطارية تماماً عن اللوحة الأم.",
                "استخدم ملقطاً دقيقاً (Tweezers) لعمل قفلة (Short) بين النقطتين بإحكام.",
                "أثناء التوصيل بالملقط، قم بإدخال كابل الـ USB المتصل بالكمبيوتر.",
                "انتظر ثانيتين ثم ارفع الملقط.",
                "تأكد في إدارة الأجهزة (Device Manager) من ظهور منفذ: Qualcomm HS-USB QDLoader 9008."
            ),
            requiresBattery = false,
            cautionNote = "لا تضغط بشدة بالملقط لتجنب خدش المسارات النحاسية المجاورة في البوردة.",
            deviceManagerPort = "Qualcomm HS-USB QDLoader 9008 (COM...)"
        ),
        TestPointItem(
            id = "tp_redmi_9",
            brand = DeviceBrand.XIAOMI,
            modelName = "Redmi 9 / 9A / 9C",
            modeType = "MediaTek BROM TestPoint",
            chipset = "MediaTek Helio G80 / G35 / G25",
            pinoutDescription = "نقطة TP1 مخصصة لخط (KCOLO / FORCE BROM) تقع بجوار درع المعالج بالقرب من مكثفات خط VCORE.",
            connectionSteps = listOf(
                "افصل البطارية نهائياً.",
                "صل النقطة المحددة بأي نقطة أرضي (GND) في الصاجة المعدنية باستخدام ملقط نحاسي.",
                "ركب كابل الـ USB في الهاتف.",
                "سيتعرف الكمبيوتر فوراً على منفذ MTK USB Port بدون الحاجة للضغط على أزرار الصوت.",
                "ابدأ التفليش أو فك FRP عبر أداة السوفت وير فوراً."
            ),
            requiresBattery = false,
            cautionNote = "تأكد من تثبيت تعريفات LibUSB و MTK Driver لتخطي حماية Auth بنجاح.",
            deviceManagerPort = "MediaTek USB Port_V1632 (COM...)"
        ),
        TestPointItem(
            id = "tp_poco_x3_pro",
            brand = DeviceBrand.XIAOMI,
            modelName = "Poco X3 Pro (Vayu)",
            modeType = "Qualcomm EDL 9008",
            chipset = "Snapdragon 860",
            pinoutDescription = "نقطتان تقعان أسفل كونكتور الشاشة الرئيسي FPC على حافة البوردة العلوية اليسرى.",
            connectionSteps = listOf(
                "افصل البطارية وافصل كابل الشاشة.",
                "اعمل شورت بين النقطتين بالملقط.",
                "اشبك كابل الـ USB.",
                "افتح أداة Mi Flash أو UnlockTool وتأكد من قراءة الجهاز 9008.",
                "إذا كان الهاتف يعاني من عطل المعالج سيتعرف تلقائياً 9008 بدون حتى لمس التيست بوينت!"
            ),
            requiresBattery = false,
            cautionNote = "يحتاج ملف Firehose معتمد أو سيرفر أكونت مفعل للتفليش عبر EDL.",
            deviceManagerPort = "Qualcomm HS-USB QDLoader 9008 (COM...)"
        ),
        TestPointItem(
            id = "tp_huawei_y9_prime",
            brand = DeviceBrand.HUAWEI,
            modelName = "Huawei Y9 Prime 2019 / P30 Lite",
            modeType = "Huawei Kirin 710 TestPoint",
            chipset = "HiSilicon Kirin 710",
            pinoutDescription = "نقطة واحدة صغيرة جداً تقع أسفل كونكتر الكاميرا الخلفية اليمنى، يتم توصيلها مع أرضي البوردة (GND).",
            connectionSteps = listOf(
                "افصل كونكتور البطارية.",
                "صل نقطة التيست بوينت بالأرضي (درع الحماية المعدني).",
                "أدخل كابل الـ USB في الهاتف.",
                "سيظهر الجهاز في الكمبيوتر باسم: HUAWEI USB COM 1.0.",
                "استخدم أداة مثل SigmaKey أو Chimera أو UnlockTool لكتابة فلاشة أو حذف حماية Huawei ID."
            ),
            requiresBattery = false,
            cautionNote = "احذر من كسر كونكتر كاميرا الـ Pop-up أو فلاتة السنسور أثناء فك الغطاء الخلفي.",
            deviceManagerPort = "HUAWEI USB COM 1.0"
        ),
        TestPointItem(
            id = "tp_samsung_a12",
            brand = DeviceBrand.SAMSUNG,
            modelName = "Samsung Galaxy A12 (A125F)",
            modeType = "MediaTek MT6765 BROM TestPoint",
            chipset = "MediaTek Helio P35",
            pinoutDescription = "نقطة اختبار تقع على ظهر البوردة خلف آيسي الذاكرة مباشرة مخصصة لتخطي حماية Sec Boot.",
            connectionSteps = listOf(
                "انزع الغطاء الخلفي وافصل البطارية.",
                "المس النقطة بسلك رفيع أو ملقط إلى أرضي البوردة.",
                "وصل كابل الشحن بالكمبيوتر.",
                "يفتح وضع الـ BROM مباشرة لتخطي FRP بنقرة واحدة."
            ),
            requiresBattery = false,
            cautionNote = "موديل A127F يحمل معالج Exynos 850 وتختلف نقاطه وطريقة تعامله تماماً عن A125F.",
            deviceManagerPort = "MediaTek USB Port (COM...)"
        ),
        TestPointItem(
            id = "tp_oppo_a15",
            brand = DeviceBrand.OPPO,
            modelName = "Oppo A15 / A15s / Realme C11",
            modeType = "MTK BROM Force Connection",
            chipset = "MediaTek Helio P35",
            pinoutDescription = "نقطة CLK / CMD بجوار درع الذاكرة أو استخدام زر خفض ورفع الصوت معاً بدون فتح الهاتف في معظم الإصدارات.",
            connectionSteps = listOf(
                "في حال توقف الهاتف عن الاستجابة للأزرار، افتح الغطاء وافصل البطارية.",
                "وصل نقطة الـ TP1 الموضحة بالمخطط بالأرضي.",
                "اشبك الكابل واستخدم أداة UnlockTool أو DFT Pro لإجراء فورمات أوبو أو إزالة قفل الشاشة."
            ),
            requiresBattery = false,
            cautionNote = "احذر عند فك الغطاء من قطع فلاتة البصمة الملتصقة بالغطاء الخلفي.",
            deviceManagerPort = "MediaTek USB Port"
        )
    )

    // 4. دورات السوفت وير (Software Courses & Flashing Masterclasses)
    val softwareCourses = listOf(
        SoftwareCourseItem(
            id = "course_samsung_odin",
            title = "دورة احتراف تفليش هواتف سامسونج عبر برنامج Odin",
            level = "مبتدئ إلى متوسط",
            estimatedDuration = "ساعتان - عملي",
            requiredTools = listOf("Samsung Odin v3.14.4", "تعريفات Samsung USB Drivers", "موقع SamFw أو Frija لتحميل الفلاشات", "كابل شحن Type-C أصلي"),
            summary = "تعلم اختيار الفلاشة الصحيحة حسب الحماية (Binary)، الفرق بين CSC و HOME_CSC، ومعالجة الوقوف على الشعار والتعليق في وضع الداونلود.",
            detailedSteps = listOf(
                "معرفة حماية الجهاز (Binary): أدخل الجهاز وضع Recovery واقرأ رقم الحماية (الخانة الخامسة من اليمين في كود الفلاشة مثل S5 أو U5). لا يمكن تفليش حماية أقل من حماية الهاتف الحالية أبداً.",
                "تحميل الفلاشة الموجهة لنفس كود الموديل ومطابقة الحماية من موقع SamFw.",
                "فك ضغط الفلاشة؛ ستظهر 5 ملفات رئيسية: BL, AP, CP, CSC, HOME_CSC.",
                "إدخال الهاتف وضع الداونلود (Download Mode): إطفاء الهاتف كلياً، ثم الضغط على زري رفع وخفض الصوت معاً وتوصيل كابل الـ USB، ثم ضغط زر رفع الصوت لتأكيد الدخول.",
                "تعبئة الخانات في برنامج Odin: ملف BL في خانة BL، ملف AP في خانة AP (سيأخذ بعض الوقت للتحقق)، ملف CP في خانة CP.",
                "اختيار ملف الـ CSC: اختر CSC_OXM لعمل فورمات كامل وإصلاح سوفت وير نظيف، أو اختر HOME_CSC لتحديث الهاتف بدون مسح أي بيانات أو صور للزبون.",
                "الضغط على Start وانتظار اكتمال الشريط الأخضر وظهور كلمة PASS!."
            ),
            commonErrors = listOf(
                "FAIL! (Auth Error / SW REV CHECK FAIL)" to "أنت تحاول تفليش فلاشة بحماية أقل (Downgrade) أو موديل مختلف، حمل فلاشة بحماية مطابقة أو أعلى فوراً.",
                "Odin يتعرف على البورت ولا يبدأ التفليش" to "تأكد من فتح برنامج Odin كمسؤول (Run as administrator) واستخدم منفذ USB خلفي بالكمبيوتر.",
                "الهاتف يعلق على شعار سامسونج بعد التفليش" to "أدخل وضع الريكفري واعمل Wipe Cache Partition ثم Wipe Data/Factory Reset."
            ),
            goldenRule = "القاعدة الذهبية: رقم الحماية (Binary/Bit) إما أن يرتفع أو يبقى كما هو، ولا ينزل أبداً في هواتف سامسونج."
        ),
        SoftwareCourseItem(
            id = "course_frp_bypass",
            title = "الدليل الشامل لتخطي حساب جوجل FRP لجميع الماركات",
            level = "متوسط إلى متقدم",
            estimatedDuration = "3 ساعات",
            requiredTools = listOf("UnlockTool", "SamFw FRP Tool", "Chimera Tool", "كابل EDL مخصص", "أداة MTK Client المجانية"),
            summary = "أحدث وأضمن الثغرات لتخطي حماية حساب جوجل (Factory Reset Protection) على أندرويد 11 و 12 و 13 و 14.",
            detailedSteps = listOf(
                "أجهزة سامسونج (وضع MTP / ثغرة الطوارئ): افتح الهاتف على شاشة البداية، اضغط 'مكالمة طوارئ' واطلب الكود *#0*# أو *#*#8888#*#. في حال فتحت شاشة الاختبار، افتح برنامج SamFw واضغط 'Remove FRP' لتمكين تصحيح أخطاء الـ ADB بضغطة واحدة.",
                "أجهزة سامسونج الحديثة (أندرويد 13/14 بدون *#0*#): استخدام طريقة استعادة النسخة الاحتياطية عبر متصفح Alliance Shield أو عبر تفليش كومبينيشن جزئي أو عبر وضع Test Point / EDL لموديلات كوالكوم.",
                "أجهزة شاومي وريدمي: التخطي بنقرة واحدة عبر وضع Fastboot (إذا كان البوتلودر مفتوحاً) أو وضع Sideload المدمج في Recovery 5.0 عبر أداة Mi Assistant، أو وضع BROM لمعالجات ميدياتيك.",
                "أجهزة أوبو وريلمي وفيوفو: استخدام كود الطوارئ القديم *#813# أو التفليش الجزئي لبارتشن الـ FRP عبر وضع الـ Preloader / BROM بأداة UnlockTool.",
                "أجهزة هواوي بدون خدمات جوجل: كتابة ملف Safe Mode أو إزالة FRP عبر كابل HUAWEI USB COM 1.0 بعد إزالة نقطة التيست بوينت."
            ),
            commonErrors = listOf(
                "كود *#0*# لا يفتح قائمة الفحص" to "سامسونج أغلقت الثغرة في تحديثات أمنية بعد يوليو 2023؛ انتقل لطريقة الـ QR Code أو تفليش وضع الداونلود.",
                "الـ ADB لا يظهر رسالة السماح على شاشة الهاتف" to "أعد تثبيت تعريفات Samsung Android ADB وغير كابل الـ USB."
            ),
            goldenRule = "دائماً اسأل الزبون وتأكد من ملكيته للجهاز قبل تخطي أي حماية لتجنب المساءلة القانونية."
        ),
        SoftwareCourseItem(
            id = "course_xiaomi_miflash",
            title = "تفليش هواتف شاومي وفك البوتلودر وتثبيت روم Fastboot",
            level = "متوسط",
            estimatedDuration = "ساعتان",
            requiredTools = listOf("Mi Flash Tool 2020/2021", "Mi Community App (لطلب فك البوتلودر)", "روم Fastboot بصيغة tgz", "تعريفات ADB & Fastboot"),
            summary = "طرق تنزيل وتفليش الرومات الرسمية (Global / EEA / India / China)، الفرق بين Flash All و Flash All Except Storage، وتجنب قفل البوتلودر بالخطأ.",
            detailedSteps = listOf(
                "تحميل فلاشة Fastboot الرسمية المناسبة للموديل بالضبط والتأكد من امتدادها .tgz وفك ضغطها باستخدام برنامج WinRAR أو 7-Zip مرتين.",
                "ضع مجلد الفلاشة مباشرة في المسار C:\\ بدون أي مسافات أو حروف عربية في اسم المجلد.",
                "أدخل الهاتف وضع الفاست بوت بالضغط على زر خفض الصوت + زر الباور معاً حتى تظهر علامة أرنب شاومي أو كلمة Fastboot باللون البرتقالي.",
                "شغل برنامج Mi Flash Tool، اضغط Select واختر مجلد الفلاشة من قرص C.",
                "تنبيه خطير جداً: تأكد من اختيار (clean all) في أسفل البرنامج. إياك واختيار (clean all and lock) إلا إذا كنت متأكداً 100% أن الفلاشة تطابق منطقة الجهاز الأصلية (Global على جهاز Global) حتى لا يموت الهاتف في شاشة 'This MIUI version can't be installed on this device'.",
                "اضغط Flash وانتظر ظهور success باللون الأخضر."
            ),
            commonErrors = listOf(
                "خطأ The system cannot find the file specified" to "المسار يحتوي على مسافات أو مجلدات فرعية متداخلة، ضع ملفات الفلاشة في C:\\ directamente.",
                "الجهاز يعلق على شعار Mi بعد التفليش" to "ادخل الريكفري واعمل Wipe All Data."
            ),
            goldenRule = "إياك وقفل البوت لودر بروم معدلة أو بروم عالمية على جهاز صيني Hardcoded!"
        ),
        SoftwareCourseItem(
            id = "course_mtk_repair",
            title = "إصلاح السيريال والشبكة واسترجاع NVRAM لمعالجات MTK",
            level = "محترف",
            estimatedDuration = "ساعتان ونصف",
            requiredTools = listOf("Maui META / Modem META Tool", "UnlockTool", "BROM Exploit Driver", "Database Files (BPLGU / APDB)"),
            summary = "حل مشاكل الشبكة: Baseband Unknown، فقدان IMEI، عطل عدم قراءة شريحة SIM، وتعديل وإصلاح السيريال القانوني للجهاز.",
            detailedSteps = listOf(
                "استخراج ملفات الـ Database (ملف مودم BPLGUInfo و ملف APDB) من نفس فلاشة الجهاز الرسمية.",
                "توصيل الهاتف بوضع الـ Meta Mode: إطفاء الهاتف ثم توصيله والضغط على أزرار الصوت مع فتح برنامج Modem META حتى تظهر شاشة بيضاء أو صفراء ويكتب البرنامج Connected.",
                "فتح خيار IMEI Download واختيار ملف الـ Database المتطابق مع الفلاشة.",
                "كتابة رقم الـ IMEI المطبوع على ظهر الجهاز أو العلبة في خانة SIM 1 و SIM 2 مع احتساب رقم التحقق الأخير (Checksum).",
                "الضغط على Write to flash والتأكد من إشعار النجاح، ثم إعادة تشغيل الجهاز والتحقق بطلب الكود *#06#."
            ),
            commonErrors = listOf(
                "الهاتف لا يدخل وضع META ويعيد التشغيل" to "قم بتعطيل توقيع التعريفات (Driver Signature) في ويندوز وتثبيت تعريف CDC Driver.",
                "الشبكة تظهر دائرة ومحظورة بعد كتابة السيريال" to "تأكد من كتابة ملف NVRAM و NVDATA سليم لضبط ترددات الشبكة المحلية."
            ),
            goldenRule = "احتفظ دائماً بنسخة احتياطية Backup لبارتشنات NVRAM / NVDATA قبل إجراء أي عملية تفليش أو إصلاح."
        )
    )

    // 5. توافقات الشاشات (Screen Compatibilities - huge database)
    val screenCompatibilities = listOf(
        ScreenCompatibilityItem(
            id = "sc_redmi_9a_family",
            brand = DeviceBrand.XIAOMI,
            primaryModel = "Redmi 9A",
            compatibleModels = listOf("Redmi 9C", "Redmi 9 Active", "Redmi 10A", "Poco C3", "Poco C31"),
            displayType = "IPS LCD 6.53\"",
            withFrameOrFlexOnly = "بدون شاسيه: متطابقة 100%. بالشاسيه: تختلف فتحة مستشعر البصمة في ظهر الشاسيه بين 9A و 9C.",
            importantNotes = "شاشة واحدة تخدم 5 موديلات كاملة! فلاتة الشاشة وكونكتور الـ FPC متطابق تماماً. متوفرة بأرخص الأسعار في السوق.",
            verifiedByTechnicians = true
        ),
        ScreenCompatibilityItem(
            id = "sc_samsung_a10s_a20s",
            brand = DeviceBrand.SAMSUNG,
            primaryModel = "Samsung Galaxy A10s (SM-A107)",
            compatibleModels = listOf("Samsung Galaxy A20s (SM-A207)"),
            displayType = "IPS LCD 6.5\"",
            withFrameOrFlexOnly = "بدون شاسيه (شاشة زجاج + فلاتة فقط): متوافقة بنسبة 99%. بالشاسيه لا تركب لاختلاف مقاس الإطار الداخلي.",
            importantNotes = "تأكد من رقم إصدار الفلاتة المطبوع على الكابل؛ بعض الشاشات التجارية القديمة قد تسبب عدم انتظام إضاءة الـ A20s.",
            verifiedByTechnicians = true
        ),
        ScreenCompatibilityItem(
            id = "sc_oppo_a15_family",
            brand = DeviceBrand.OPPO,
            primaryModel = "Oppo A15",
            compatibleModels = listOf("Oppo A15s", "Realme C11 (نسخة 2021 RMX3231)", "Realme C20", "Realme C21"),
            displayType = "IPS LCD 6.52\"",
            withFrameOrFlexOnly = "بدون شاسيه تركب 100%. انتبه أن Realme C11 إصدار 2020 (RMX2185) يختلف عن إصدار 2021!",
            importantNotes = "افحص اللمس جيداً بعد اللصق لأن بعض النسخ التجارية لها حساسية لزيادة الغراء على الحواف العلوية.",
            verifiedByTechnicians = true
        ),
        ScreenCompatibilityItem(
            id = "sc_oppo_a16_family",
            brand = DeviceBrand.OPPO,
            primaryModel = "Oppo A16",
            compatibleModels = listOf("Oppo A16s", "Oppo A16k", "Realme C21Y", "Realme C25Y"),
            displayType = "IPS LCD 6.52\"",
            withFrameOrFlexOnly = "تركب الشاشة بدون شاسيه بشكل سليم تماماً مع تطابق كونكتور العرض واللمس.",
            importantNotes = "نسخة الشاشة الـ IC المتطورة تضمن عدم حدوث تهنيج في اللمس عند استخدام الشاحن السريع.",
            verifiedByTechnicians = true
        ),
        ScreenCompatibilityItem(
            id = "sc_infinix_hot10play",
            brand = DeviceBrand.INFINIX_TECNO,
            primaryModel = "Infinix Hot 10 Play (X688)",
            compatibleModels = listOf("Infinix Hot 11 Play (X688B)", "Infinix Smart 5 Pro"),
            displayType = "IPS LCD 6.82\"",
            withFrameOrFlexOnly = "شاشة بدون شاسيه متطابقة وتعمل بالكامل مع اللمس والإضاءة.",
            importantNotes = "شاشة عريضة وضخمة الحجم؛ احرص على تنظيف الشاسيه من بقايا الزجاج القديم لتفادي كسر الشاشة الجديدة عند الضغط.",
            verifiedByTechnicians = true
        ),
        ScreenCompatibilityItem(
            id = "sc_tecno_spark7",
            brand = DeviceBrand.INFINIX_TECNO,
            primaryModel = "Tecno Spark 7 (KF6)",
            compatibleModels = listOf("Tecno Spark 7T (KF6p)", "Tecno Pop 5 LTE (BD4)", "Tecno Pop 5 Pro"),
            displayType = "IPS LCD 6.52\"",
            withFrameOrFlexOnly = "متطابقة تماماً بدون شاسيه.",
            importantNotes = "سعرها اقتصادي ونفس فلاتة وتوزيع خطوط الـ MIPI.",
            verifiedByTechnicians = true
        ),
        ScreenCompatibilityItem(
            id = "sc_samsung_a02_a12",
            brand = DeviceBrand.SAMSUNG,
            primaryModel = "Samsung Galaxy A02 (A022)",
            compatibleModels = listOf("Samsung Galaxy M02 (M022)", "Samsung Galaxy A02s (A025)", "Samsung Galaxy A03s (A037)"),
            displayType = "PLS LCD 6.5\"",
            withFrameOrFlexOnly = "الشاشة بدون شاسيه تركب بين الموديلات مع الانتباه لنوع كونكتور الفلاتة (نسخ 34 pin مقابل 40 pin في بعض الفئات).",
            importantNotes = "افحص كود الفلاتة المطبوع (Rev 0.1 أو Rev 0.2) لتجنب مشكلة الشاشة السوداء في A02s.",
            verifiedByTechnicians = true
        ),
        ScreenCompatibilityItem(
            id = "sc_iphone_12_12pro",
            brand = DeviceBrand.APPLE,
            primaryModel = "iPhone 12",
            compatibleModels = listOf("iPhone 12 Pro (A2403 / A2407)"),
            displayType = "Super Retina XDR OLED 6.1\"",
            withFrameOrFlexOnly = "متطابقة 100% بالشاسيه والفلاتة والكونكتورات! شاشة الآيفون 12 تركب على 12 Pro والعكس صحيح تماماً.",
            importantNotes = "تذكر نقل آيسي الشاشة الأصلي عبر الكاوي أو الشبلونة للتخلص من رسالة 'Important Display Message' ونقل التروتون.",
            verifiedByTechnicians = true
        ),
        ScreenCompatibilityItem(
            id = "sc_redmi_note11_family",
            brand = DeviceBrand.XIAOMI,
            primaryModel = "Redmi Note 11 4G",
            compatibleModels = listOf("Redmi Note 11S 4G", "Poco M4 Pro 4G"),
            displayType = "AMOLED 90Hz 6.43\"",
            withFrameOrFlexOnly = "متطابقة بدون إطار 100%، تدعم معدل التحديث 90Hz وسطوع 1000 nits.",
            importantNotes = "انتبه أن نسخة 5G من نفس هذه الأجهزة تختلف في الحجم ونوع الشاشة (شاشة 5G تكون IPS LCD 6.6 وليست AMOLED).",
            verifiedByTechnicians = true
        )
    )

    // 6. شرح معنى الأنظمة والأوضاع (Operating Systems & Firmware Modes)
    val systemModes = listOf(
        SystemModeItem(
            id = "mode_fastboot",
            nameEn = "Fastboot Mode",
            nameAr = "وضع الفاست بوت (Bootloader Interface)",
            supportedPlatforms = "شاومي، جوجل بيكسل، ون بلس، موتورولا، هواوي القديم",
            purpose = "بروتوكول تواصل مباشر مع البوتلودر قبل إقلاع نظام أندرويد. يُستخدم لتفليش البارتشنات الأساسية (Boot, Recovery, System, Vendor)، وفك وقفل البوت لودر، وعمل مسح شامل للبيانات.",
            enterKeyCombination = "إطفاء الهاتف بالكامل، ثم الضغط المطول على (زر خفض الصوت + زر الباور) حتى ظهور شاشة Fastboot.",
            enterTerminalCommand = "adb reboot bootloader",
            howToExit = "الضغط المطول على زر الباور لمدة 10 ثوانٍ، أو كتابة الأمر: fastboot reboot",
            commonIssues = "عدم قراءة الهاتف في Fastboot يكون سببه نقص تعريفات Android Bootloader Interface في الكمبيوتر أو استخدام كابل Type-C رديء.",
            keyCommands = listOf(
                "fastboot devices (التحقق من اتصال الهاتف ورقم السيريال)",
                "fastboot oem unlock أو fastboot flashing unlock (طلب فك البوتلودر)",
                "fastboot flash recovery twrp.img (تثبيت ريكفري معدل)",
                "fastboot getvar all (قراءة كامل معلومات النظام وحالة القفل)",
                "fastboot reboot (إعادة تشغيل الهاتف إلى النظام العادي)"
            )
        ),
        SystemModeItem(
            id = "mode_edl_9008",
            nameEn = "EDL Mode (Emergency Download Mode 9008)",
            nameAr = "وضع الطوارئ كوالكوم EDL 9008",
            supportedPlatforms = "جميع الهواتف التي تعمل بمعالجات كوالكوم (Qualcomm Snapdragon)",
            purpose = "أعمق وضع صيانة متاح في معالجات كوالكوم مدمج في الـ ROM الداخلي للمعالج (PBL). يتيح قراءة وكتابة الذاكرة (eMMC / UFS) مباشرة حتى لو كان الهاتف ميتاً تماماً أو مضروب البوتلودر (Hard Bricked).",
            enterKeyCombination = "يتم الدخول إليه عبر: نقاط التيست بوينت (Test Point)، أو كابل EDL مخصص (كابل الـ 9008 بضغطة زر)، أو أمر adb reboot edl (إذا كان مفعلاً).",
            enterTerminalCommand = "adb reboot edl",
            howToExit = "فصل البطارية وإعادة توصيلها، ثم الضغط المطول على زر الباور لمدة 15 ثانية.",
            commonIssues = "ظهور المنفذ برمز مثلث أصفر في Device Manager كـ QHSUSB_BULK؛ حله تثبيت حزمة Qualcomm QDLoader Driver الرسمية.",
            keyCommands = listOf(
                "يتعرف في الكمبيوتر باسم: Qualcomm HS-USB QDLoader 9008",
                "يحتاج ملف لودر مخصص لكل معالج يُعرف باسم Firehose (prog_emmc_firehose_xxxx.mbn/elf)",
                "في الهواتف الحديثة تشترط شاومي حساب توثيق معتمد (Authorized Mi Account) للبدء بالتفليش."
            )
        ),
        SystemModeItem(
            id = "mode_brom",
            nameEn = "BROM Mode (Boot ROM Mode)",
            nameAr = "وضع الـ BROM لمعالجات ميديا تيك (MediaTek)",
            supportedPlatforms = "جميع أجهزة معالجات MediaTek (Helio, Dimensity, MT67xx)",
            purpose = "وضع التحميل الأساسي منخفض المستوى المحفور على سيليكون معالج ميديا تيك. يتيح تخطي البوت لودر، فك FRP، مسح رموز الحماية، سحب الروم، وتفليش الفلاشات المسحوبة.",
            enterKeyCombination = "إطفاء الهاتف تماماً، ثم الضغط على (زر رفع الصوت + زر خفض الصوت معاً) وتوصيل كابل الـ USB، أو استخدام نقطة التيست بوينت (KCOLO to GND).",
            enterTerminalCommand = "غير متاح عبر ADB، يتم هاردوير فقط.",
            howToExit = "فصل الكابل والضغط المطول على زر الباور لمدة 12 ثانية.",
            commonIssues = "انفصال المنفذ بعد 3 ثوانٍ (Disconnection)؛ سببه عدم استخدام ثغرة تخطي الحماية (BROM SLA/DA Auth Bypass) أو تلف تعريف LibUSB.",
            keyCommands = listOf(
                "يتعرف كـ MediaTek USB Port أو MTK Preloader",
                "يستخدم أدوات MTKClient, SP Flash Tool, UnlockTool, Pandora Box",
                "يتيح استرجاع الجهاز الميت نتيجة خطأ تفليش بنسبة نجاح 99%."
            )
        ),
        SystemModeItem(
            id = "mode_samsung_download",
            nameEn = "Download Mode (Odin Mode)",
            nameAr = "وضع الداونلود / وضع الأودين (سامسونج)",
            supportedPlatforms = "هواتف وأجهزة تابلت سامسونج جالاكسي فقط",
            purpose = "الوضع المخصص لتفليش الرومات الرسمية والترقيات وحزم الإصلاح عبر برنامج Odin والكمبيوتر.",
            enterKeyCombination = "إطفاء الهاتف، الضغط المطول على (زر خفض الصوت + زر رفع الصوت معاً) وتركيب كابل الـ USB بالكمبيوتر، ثم ضغط زر رفع الصوت مرة واحدة.",
            enterTerminalCommand = "adb reboot download",
            howToExit = "الضغط معاً على (زر خفض الصوت + زر الباور) لمدة 7 ثوانٍ متواصلة.",
            commonIssues = "توقف الهاتف على شاشة زرقاء 'An error has occurred while updating'; حله تفليش فلاشة 4 ملفات كاملة مع ملف PIT.",
            keyCommands = listOf(
                "يظهر في أعلى الشاشة معلومات مهمة: Product Name, FRP Lock: ON/OFF, OEM Lock: ON/OFF, KG Status, Knox Warranty Void: 0x0 / 0x1",
                "أهم خانة للفني هي Carrier ID و Current Binary لمعرفة حماية الهاتف الحالية."
            )
        ),
        SystemModeItem(
            id = "mode_recovery",
            nameEn = "Recovery Mode (Stock & Custom TWRP)",
            nameAr = "وضع الريكفري (الاسترداد)",
            supportedPlatforms = "جميع أجهزة أندرويد",
            purpose = "بيئة تشغيل مستقلة تتيح عمل مسح بيانات المصنع (Wipe Data)، مسح الكاش، وتثبيت حزم التحديث بصيغة ZIP، وعمل نسخ احتياطي كامل (Nandroid Backup).",
            enterKeyCombination = "إطفاء الهاتف، الضغط على (رفع الصوت + الباور) وفي بعض أجهزة سامسونج الحديثة يجب توصيل الهاتف بكابل شحن بالكمبيوتر أولاً.",
            enterTerminalCommand = "adb reboot recovery",
            howToExit = "اختيار Reboot system now من القائمة باستخدام أزرار الصوت والتأكيد بزر الباور.",
            commonIssues = "عدم القدرة على الدخول للريكفري في سامسونج أندرويد 11+ إلا بعد توصيل كابل USB متصل بكمبيوتر أو شاشة ذكية.",
            keyCommands = listOf(
                "Wipe data/factory reset (مسح بيانات الجهاز كلياً وحل مشاكل التعليق)",
                "Wipe cache partition (تنظيف الذاكرة المؤقتة بدون مسح الملفات)",
                "Apply update from ADB (تثبيت تحديث عبر sideload)"
            )
        ),
        SystemModeItem(
            id = "mode_dfu",
            nameEn = "DFU Mode (Device Firmware Upgrade)",
            nameAr = "وضع DFU لأجهزة آبل (آيفون وآيباد)",
            supportedPlatforms = "جميع أجهزة Apple iOS (iPhone / iPad)",
            purpose = "الوضع الأعمق لأجهزة آيفون الذي يتجاوز إقلاع نظام iBoot بالكامل. يُستخدم لعمل سوفت وير واستعادة نظيفة عندما يفشل وضع الريكفري العادي، أو عند موت الجهاز.",
            enterKeyCombination = "في آيفون 8 فما فوق: اضغط رفع الصوت ثم خفض الصوت ثم اضغط الباور حتى تنطفئ الشاشة، ثم اضغط خفض الصوت مع الباور 5 ثوانٍ، ثم اترك الباور مع الاستمرار بضغط خفض الصوت 10 ثوانٍ (تبقى الشاشة سوداء تماماً).",
            enterTerminalCommand = "عبر أدوات 3uTools أو Mac Configurator.",
            howToExit = "الضغط السريع على رفع الصوت ثم خفض الصوت ثم ضغط زر الباور مطولاً حتى ظهور شعار التفاحة.",
            commonIssues = "إذا ظهر شعار الكمبيوتر والكابل، فهذا وضع Recovery عادي وليس DFU! وضع الـ DFU الحقيقي تبقى شاشته سوداء تماماً دون أي إضاءة والآيتونز يتعرف عليه.",
            keyCommands = listOf(
                "يتعرف في iTunes و 3uTools كـ: iPhone in DFU Mode",
                "الحل النهائي لأخطاء السوفت وير المستعصية (Error 9, Error 4013, Error 4014)."
            )
        )
    )

    // 7. موديلات الأجهزة والترميز (Device Model Codes - 60+ mappings)
    val deviceCodes = listOf(
        DeviceCodeItem("dc_s21u", DeviceBrand.SAMSUNG, "SM-G998B", "Galaxy S21 Ultra 5G (Global)", "Exynos 2100", "2021", "نسخة الشريحتين العالمية"),
        DeviceCodeItem("dc_s22u", DeviceBrand.SAMSUNG, "SM-S908B", "Galaxy S22 Ultra 5G (Global)", "Exynos 2200", "2022", "يدعم قلم S-Pen وشاحن 45W"),
        DeviceCodeItem("dc_s23u", DeviceBrand.SAMSUNG, "SM-S918B", "Galaxy S23 Ultra 5G", "Snapdragon 8 Gen 2 for Galaxy", "2023", "معالج كوالكوم لجميع الأسواق"),
        DeviceCodeItem("dc_s24u", DeviceBrand.SAMSUNG, "SM-S928B", "Galaxy S24 Ultra 5G", "Snapdragon 8 Gen 3 for Galaxy", "2024", "شاشة مسطحة وإطار تيتانيوم"),
        DeviceCodeItem("dc_a54", DeviceBrand.SAMSUNG, "SM-A546E", "Galaxy A54 5G", "Exynos 1380", "2023", "شاشة Super AMOLED 120Hz"),
        DeviceCodeItem("dc_a53", DeviceBrand.SAMSUNG, "SM-A536B", "Galaxy A53 5G", "Exynos 1280", "2022", "بطارية 5000mAh"),
        DeviceCodeItem("dc_a52s", DeviceBrand.SAMSUNG, "SM-A528B", "Galaxy A52s 5G", "Snapdragon 778G 5G", "2021", "من أنجح هواتف الفئة المتوسطة"),
        DeviceCodeItem("dc_a51", DeviceBrand.SAMSUNG, "SM-A515F", "Galaxy A51", "Exynos 9611", "2020", "من أكثر الأجهزة انتشاراً في الصيانة"),
        DeviceCodeItem("dc_a34", DeviceBrand.SAMSUNG, "SM-A346E", "Galaxy A34 5G", "MediaTek Dimensity 1080", "2023", "معالج ميدياتيك قوي"),
        DeviceCodeItem("dc_a24", DeviceBrand.SAMSUNG, "SM-A245F", "Galaxy A24 4G", "MediaTek Helio G99", "2023", "شاشة AMOLED 90Hz"),
        DeviceCodeItem("dc_a14", DeviceBrand.SAMSUNG, "SM-A145F", "Galaxy A14 4G", "MediaTek Helio G80 / Exynos 850", "2023", "نسختان بالأسواق"),
        DeviceCodeItem("dc_a13", DeviceBrand.SAMSUNG, "SM-A135F", "Galaxy A13 4G", "Exynos 850", "2022", "شاشة PLS LCD"),
        DeviceCodeItem("dc_a12", DeviceBrand.SAMSUNG, "SM-A125F", "Galaxy A12", "MediaTek Helio P35", "2020", "يختلف عن A127F (Exynos 850)"),
        DeviceCodeItem("dc_a10s", DeviceBrand.SAMSUNG, "SM-A107F", "Galaxy A10s", "MediaTek Helio P22", "2019", "نفس شاشة A20s بدون شاسيه"),
        DeviceCodeItem("dc_rn10pro", DeviceBrand.XIAOMI, "M2101K6G", "Redmi Note 10 Pro (Global)", "Snapdragon 732G", "2021", "اسم اللوحة: Sweet"),
        DeviceCodeItem("dc_rn11pro", DeviceBrand.XIAOMI, "2201116TG", "Redmi Note 11 Pro 4G", "MediaTek Helio G96", "2022", "اسم اللوحة: Viva"),
        DeviceCodeItem("dc_rn12pro", DeviceBrand.XIAOMI, "22101316G", "Redmi Note 12 Pro 5G", "MediaTek Dimensity 1080", "2023", "اسم اللوحة: Ruby"),
        DeviceCodeItem("dc_rn13pro", DeviceBrand.XIAOMI, "23117RA68G", "Redmi Note 13 Pro 4G", "MediaTek Helio G99 Ultra", "2024", "كاميرا 200MP"),
        DeviceCodeItem("dc_pocox3p", DeviceBrand.XIAOMI, "M2102J20SG", "Poco X3 Pro", "Snapdragon 860", "2021", "اسم اللوحة: Vayu"),
        DeviceCodeItem("dc_pocox3nfc", DeviceBrand.XIAOMI, "M2007J20CG", "Poco X3 NFC", "Snapdragon 732G", "2020", "اسم اللوحة: Surya"),
        DeviceCodeItem("dc_pocox5p", DeviceBrand.XIAOMI, "22101320G", "Poco X5 Pro 5G", "Snapdragon 778G", "2023", "اسم اللوحة: Redwood"),
        DeviceCodeItem("dc_redmi9", DeviceBrand.XIAOMI, "M2004J19G", "Redmi 9", "MediaTek Helio G80", "2020", "اسم اللوحة: Lancelot"),
        DeviceCodeItem("dc_redmi9a", DeviceBrand.XIAOMI, "M2006C3LG", "Redmi 9A", "MediaTek Helio G25", "2020", "اسم اللوحة: Dandelion"),
        DeviceCodeItem("dc_redmi9c", DeviceBrand.XIAOMI, "M2006C3MG", "Redmi 9C", "MediaTek Helio G35", "2020", "اسم اللوحة: Angelica"),
        DeviceCodeItem("dc_oppo_a15", DeviceBrand.OPPO, "CPH2185", "Oppo A15", "MediaTek Helio P35", "2020", "كاميرا ثلاثية 13MP"),
        DeviceCodeItem("dc_oppo_a16", DeviceBrand.OPPO, "CPH2269", "Oppo A16", "MediaTek Helio G35", "2021", "بطارية 5000mAh"),
        DeviceCodeItem("dc_oppo_reno5", DeviceBrand.OPPO, "CPH2159", "Oppo Reno 5 4G", "Snapdragon 720G", "2021", "شاحن 50W SuperVOOC"),
        DeviceCodeItem("dc_oppo_reno6", DeviceBrand.OPPO, "CPH2235", "Oppo Reno 6 4G", "Snapdragon 720G", "2021", "تصميم مسطح مميز"),
        DeviceCodeItem("dc_rmx_c11", DeviceBrand.REALME, "RMX3231", "Realme C11 2021", "Unisoc SC9863A", "2021", "يختلف كلياً عن RMX2185 (MediaTek)"),
        DeviceCodeItem("dc_rmx_c21", DeviceBrand.REALME, "RMX3201", "Realme C21", "MediaTek Helio G35", "2021", "نفس شاشة Oppo A15"),
        DeviceCodeItem("dc_rmx_gtm", DeviceBrand.REALME, "RMX3363", "Realme GT Master Edition", "Snapdragon 778G 5G", "2021", "شاشة Super AMOLED 120Hz"),
        DeviceCodeItem("dc_hw_y9p", DeviceBrand.HUAWEI, "STK-LX1", "Huawei Y9 Prime 2019", "Kirin 710F", "2019", "كاميرا سيلفي منبثقة Pop-up"),
        DeviceCodeItem("dc_hw_nova7i", DeviceBrand.HUAWEI, "JNY-LX1", "Huawei Nova 7i", "Kirin 810", "2020", "بدون خدمات جوجل الرسمية GMS"),
        DeviceCodeItem("dc_hw_p30l", DeviceBrand.HUAWEI, "MAR-LX1A", "Huawei P30 Lite", "Kirin 710", "2019", "يدعم خدمات جوجل رسمياً"),
        DeviceCodeItem("dc_ip_11", DeviceBrand.APPLE, "A2221", "iPhone 11 (Global)", "Apple A13 Bionic", "2019", "شاشة LCD مقاس 6.1 بوصة"),
        DeviceCodeItem("dc_ip_12", DeviceBrand.APPLE, "A2403", "iPhone 12 (Global)", "Apple A14 Bionic", "2020", "شاشة OLED مقاس 6.1 بوصة"),
        DeviceCodeItem("dc_ip_13", DeviceBrand.APPLE, "A2633", "iPhone 13 (Global)", "Apple A15 Bionic", "2021", "نوتش أصغر وبطارية أكبر"),
        DeviceCodeItem("dc_ip_14", DeviceBrand.APPLE, "A2882", "iPhone 14 (Global)", "Apple A15 Bionic (5 GPU cores)", "2022", "زجاج خلفي قابل للفك بسهولة"),
        DeviceCodeItem("dc_ip_15pm", DeviceBrand.APPLE, "A3106", "iPhone 15 Pro Max (Global)", "Apple A17 Pro (3 nm)", "2023", "منفذ Type-C وزر Action Button")
    )

    // 8. فحص IMEI وأفضل مواقع الفحص داخل التطبيق (All requested links)
    val imeiCheckSites = listOf(
        // آبل - كلين أو لوست
        ImeiCheckSiteItem(
            id = "imei_iunlocker_icloud",
            title = "iUnlocker – Check iCloud",
            brandCategory = DeviceBrand.APPLE,
            url = "https://iunlocker.com/check_icloud.php",
            description = "فحص مباشر لمعرفة حالة الآيكلاود (Clean أو Lost / Stolen)، وحالة Find My iPhone و Blacklist.",
            badge = "سريع ومجاني",
            isOfficial = false
        ),
        ImeiCheckSiteItem(
            id = "imei_check_iphone",
            title = "IMEICheck – iPhone IMEI Check",
            brandCategory = DeviceBrand.APPLE,
            url = "https://imeicheck.com/iphone-imei-check",
            description = "فحص كامل لموديل الآيفون، اللون، المساحة، تاريخ التفعيل، وحالة الضمان و AppleCare.",
            badge = "معلومات شاملة",
            isOfficial = false
        ),
        ImeiCheckSiteItem(
            id = "imei_check_icloud",
            title = "IMEICheck – iCloud Check",
            brandCategory = DeviceBrand.APPLE,
            url = "https://imeicheck.com/icloud-check",
            description = "فحص دقيق لحالة قفل تنشيط الآيكلاود (Activation Lock: ON / OFF).",
            badge = "دقيق 100%",
            isOfficial = false
        ),
        ImeiCheckSiteItem(
            id = "imei_ifreeicloud",
            title = "iFreeiCloud – Free Check",
            brandCategory = DeviceBrand.APPLE,
            url = "https://ifreeicloud.co.uk/free-check",
            description = "فحص مجاني لمعرفة سيريال وسعة الآيفون وقفل التنشيط.",
            badge = "مجاني",
            isOfficial = false
        ),
        ImeiCheckSiteItem(
            id = "imei_imei24_apple",
            title = "IMEI24 – iPhone Lock Checker",
            brandCategory = DeviceBrand.APPLE,
            url = "https://imei24.com/check/iphone_lock_checker/",
            description = "فحص حالة قفل الشبكة والـ SIM Lock لمعرفة هل الجهاز مقفول شبكة أم مفتوح رسمي.",
            badge = "Simlock Check",
            isOfficial = false
        ),

        // شاومي
        ImeiCheckSiteItem(
            id = "imei_mifirm_country",
            title = "MiFirm – Xiaomi Country Check",
            brandCategory = DeviceBrand.XIAOMI,
            url = "https://mifirm.net/imei",
            description = "معرفة دولة توجيه هاتف شاومي (Global / EEA / India / Russia / China) وتاريخ البيع.",
            badge = "فحص دولة شاومي",
            isOfficial = false
        ),
        ImeiCheckSiteItem(
            id = "imei_xiaomicheck",
            title = "XiaomiCheck – فحص شاومي كامل",
            brandCategory = DeviceBrand.XIAOMI,
            url = "https://xiaomicheck.com/",
            description = "فحص تفصيلي لأجهزة شاومي وريدمي وبوكو: الموديل الدقيق، تاريخ التفعيل، وحالة حساب Mi Account.",
            badge = "شامل شاومي",
            isOfficial = false
        ),
        ImeiCheckSiteItem(
            id = "imei_xiaomi_official_find",
            title = "حذف حساب شاومي – الموقع الرسمي Mi Cloud",
            brandCategory = DeviceBrand.XIAOMI,
            url = "https://us.i.mi.com/mobile/find#/",
            description = "حذف حساب شاومي وفصل ميزة Find Device باستخدام رقم الهاتف وكلمة السر من الموقع الرسمي.",
            badge = "موقع شاومي الرسمي",
            isOfficial = true
        ),
        ImeiCheckSiteItem(
            id = "imei_evondt_xiaomi",
            title = "Evondt – Xiaomi Find Device Checker (عربي)",
            brandCategory = DeviceBrand.XIAOMI,
            url = "https://www.evondt.com/ar-SA/imei-check/xiaomi-find-device-checker",
            description = "أداة فحص عربية سريعة لحالة تفعيل العثور على الجهاز لحساب شاومي (ON / OFF).",
            badge = "واجهة عربية",
            isOfficial = false
        ),
        ImeiCheckSiteItem(
            id = "imei_info_xiaomi",
            title = "IMEI.info – Xiaomi Find Device Checker",
            brandCategory = DeviceBrand.XIAOMI,
            url = "https://www.imei.info/news/xiaomi-find-device-checker/",
            description = "فحص رسمي من موقع IMEI.info لحالة حماية أجهزة شاومي وريدمي.",
            badge = "IMEI.info",
            isOfficial = false
        ),
        ImeiCheckSiteItem(
            id = "imei_mifirm_vignette",
            title = "MiFirm – IMEI Check Direct",
            brandCategory = DeviceBrand.XIAOMI,
            url = "https://mifirm.net/imei#google_vignette",
            description = "رابط فحص مباشر مع تحميل فلاشات الـ Fastboot و Recovery المطابقة للموديل.",
            badge = "فلاشات + فحص",
            isOfficial = false
        ),
        ImeiCheckSiteItem(
            id = "imei_check_xiaomi_mi",
            title = "IMEICheck – Xiaomi MI Check",
            brandCategory = DeviceBrand.XIAOMI,
            url = "https://imeicheck.com/xiaomi-mi-check",
            description = "التحقق من حالة حساب شاومي وحالة القفل والأمان في سيرفرات شاومي العالمية.",
            badge = "سيرفر شاومي",
            isOfficial = false
        ),

        // سامسونج
        ImeiCheckSiteItem(
            id = "imei_samsung_check",
            title = "Samsung IMEI Check – فحص دولة وموديل سامسونج",
            brandCategory = DeviceBrand.SAMSUNG,
            url = "https://imeicheck.com/samsung-imei-check",
            description = "معرفة موديل سامسونج الدقيق، كود الـ CSC، دولة التصنيع، وتاريخ إنتاج الهاتف ومدة الضمان.",
            badge = "Samsung Official Data",
            isOfficial = false
        ),

        // أوبو وريلمي
        ImeiCheckSiteItem(
            id = "imei_oppo_warranty",
            title = "Oppo Warranty Check – فحص ضمان ودولة أوبو",
            brandCategory = DeviceBrand.OPPO,
            url = "https://support.oppo.com/en/warranty-check/",
            description = "الموقع الرسمي لشركة أوبو للتحقق من الموديل الأصلي وتاريخ الشراء وفترة الضمان الرسمي.",
            badge = "موقع أوبو الرسمي",
            isOfficial = true
        ),
        ImeiCheckSiteItem(
            id = "imei_realme_phonecheck",
            title = "Realme Phone Check – فحص تفعيل وموديل ريلمي",
            brandCategory = DeviceBrand.REALME,
            url = "https://www.realme.com/global/support/phonecheck",
            description = "الموقع الرسمي لشركة ريلمي للتأكد من أصالة الهاتف وتاريخ التفعيل والمواصفات.",
            badge = "موقع ريلمي الرسمي",
            isOfficial = true
        ),

        // شبكات، AT&T، و ICCID
        ImeiCheckSiteItem(
            id = "imei_sickw_carrier",
            title = "Sickw – iPhone Carrier Check",
            brandCategory = DeviceBrand.APPLE,
            url = "https://sickw.com/",
            description = "أشهر سيرفر لفحص شبكة الآيفون ومعرفة اسم الشبكة المقفول عليها وتاريخ الشراء.",
            badge = "سيرفر Sickw الشهير",
            isOfficial = false
        ),
        ImeiCheckSiteItem(
            id = "imei_check_carrier",
            title = "IMEICheck – iPhone Carrier Check",
            brandCategory = DeviceBrand.APPLE,
            url = "https://imeicheck.com/iphone-carrier-check",
            description = "فحص مباشر لمعرفة شركة الاتصالات (Carrier) وحالة القفل المقيد Sim-Lock.",
            badge = "Carrier Status",
            isOfficial = false
        ),
        ImeiCheckSiteItem(
            id = "imei_att_check",
            title = "AT&T IMEI Check – تشيك شبكة AT&T",
            brandCategory = DeviceBrand.ALL,
            url = "https://iunlocker.com/att_check_imei.php",
            description = "معرفة إذا كان الجهاز المقفول على شبكة AT&T الأمريكية كلين (Clean) وجاهز للفتح أم عليه فواتير غير مدفوعة (Unpaid Bills).",
            badge = "AT&T Clean / Bills",
            isOfficial = false
        ),
        ImeiCheckSiteItem(
            id = "imei_cmd99_iccid",
            title = "CMD99 – ICCID Update (شفرة الآيفون)",
            brandCategory = DeviceBrand.APPLE,
            url = "https://cmd99.com/new-iccid-update/",
            description = "متابعة أحدث أكواد الـ ICCID النشطة لتشغيل شرائح التوربو سيم (RSIM / MKSD) لفك شبكات الآيفون.",
            badge = "أكواد الـ ICCID الجديدة",
            isOfficial = false
        ),

        // مواقع تشيك عامة وقوائم مسروقات
        ImeiCheckSiteItem(
            id = "imei_imeipro",
            title = "IMEIPro – Blacklist & Status Checker",
            brandCategory = DeviceBrand.ALL,
            url = "https://www.imeipro.info/",
            description = "فحص حالة البلاك ليست العالمية لجميع أنواع الهواتف لمعرفة ما إذا كان الجهاز محظوراً أو مفقوداً.",
            badge = "Blacklist Check",
            isOfficial = false
        ),
        ImeiCheckSiteItem(
            id = "imei_org_lost_stolen",
            title = "IMEI.org – Lost / Stolen Check",
            brandCategory = DeviceBrand.ALL,
            url = "https://imei.org/check-imei/lost-stolen",
            description = "قاعدة بيانات دولية للتحقق من أرقام الـ IMEI المسجلة كأجهزة مسروقة أو مفقودة قبل الشراء أو الاستلام.",
            badge = "فحص الأجهزة المسروقة",
            isOfficial = false
        )
    )

    // 9. مواقع تحميل فلاشات ورومات (أنظمة الأجهزة)
    val romDownloadSites = listOf(
        RomDownloadSite(
            id = "rom_mifirm",
            title = "موقع تحميل جميع رومات Mi الرسمية",
            url = "https://mifirm.net/",
            brandTarget = "شاومي / ريدمي / بوكو",
            description = "تحميل جميع رومات شاومي الرسمية (Fastboot & Recovery) لجميع التوجيهات (Global, EEA, India, China) مجاناً وبسرعة قصوى.",
            badge = "جميع رومات شاومي"
        ),
        RomDownloadSite(
            id = "rom_naijarom",
            title = "موقع تحميل رومات جميع الأجهزة الصينية",
            url = "https://naijarom.com/?s=spdflashtool.com",
            brandTarget = "الأجهزة الصينية و SPD و MTK",
            description = "المكتبة الأضخم لتحميل فلاشات الأجهزة الصينية، معالجات Spreadtrum (SPD) وفلاشات بصيغة PAC و Scatter.",
            badge = "جميع الأجهزة الصينية"
        ),
        RomDownloadSite(
            id = "rom_oppo_firmware",
            title = "موقع تحميل جميع رومات أجهزة أوبو",
            url = "https://firmwarefile.com/category/oppo",
            brandTarget = "أوبو Oppo الرسمية",
            description = "تحميل الفلاشات الرسمية لجميع أجهزة أوبو القديمة والحديثة بصيغ OFP و Scatter مع ملفات الـ DA و Auth.",
            badge = "جميع رومات أوبو"
        ),
        RomDownloadSite(
            id = "rom_samfw",
            title = "موقع تحميل جميع رومات سامسونج (SamFw)",
            url = "https://samfw.com/",
            brandTarget = "سامسونج جالاكسي الرسمية",
            description = "الموقع الأسرع في العالم لتحميل فلاشات سامسونج الأصلية 4 ملفات مجاناً وبروابط مباشرة وسيرفرات Google Drive سريعة.",
            badge = "جميع رومات سامسونج"
        ),
        RomDownloadSite(
            id = "rom_tecno",
            title = "موقع تحميل جميع رومات تكنو (Tecno Stock ROM)",
            url = "https://androidmtk.com/download-tecno-stock-rom-for-all-models",
            brandTarget = "تكنو Tecno الرسمية",
            description = "تحميل رومات المصنع الرسمية Stock ROM لجميع هواتف وتابلت تكنو بمختلف المعالجات وروابط تفليش SP Flash Tool.",
            badge = "جميع رومات تكنو"
        )
    )

    // 10. أكواد تفعيل خيارات المطور والفحص السري
    val secretCodes = listOf(
        SecretCodeItem(
            id = "code_samsung",
            brandName = "Samsung (سامسونج)",
            primaryCode = "*#0*#",
            alternativeCode = "*#*#88#*#*",
            description = "كود قائمة الاختبار وفحص الهاردوير وتفعيل تصحيح الـ ADB لتخطي FRP"
        ),
        SecretCodeItem(
            id = "code_huawei",
            brandName = "Huawei (هواوي)",
            primaryCode = "*#*#2846579#*#*",
            alternativeCode = null,
            description = "فتح Project Menu لتفعيل وضع تصحيح USB (Background Settings -> USB Ports Setting)"
        ),
        SecretCodeItem(
            id = "code_xiaomi",
            brandName = "Xiaomi / Redmi / Poco (شاومي)",
            primaryCode = "*#*#6484#*#*",
            alternativeCode = null,
            description = "قائمة CIT لفحص شاشة اللمس، حساس التقارب، الميكروفون، ومستشعر البصمة"
        ),
        SecretCodeItem(
            id = "code_vivo",
            brandName = "Vivo (فيفو)",
            primaryCode = "*#*#225#*#*",
            alternativeCode = null,
            description = "كود تشخيص النظام واختبارات المطور في هواتف فيفو المقفلة"
        ),
        SecretCodeItem(
            id = "code_oppo",
            brandName = "Oppo (أوبو)",
            primaryCode = "*#899#",
            alternativeCode = null,
            description = "فتح قائمة المهندس (Engineer Mode) لفحص الموديل وإصدار السوفت وير والـ PCB Number"
        ),
        SecretCodeItem(
            id = "code_infinix",
            brandName = "Infinix (إنفينكس)",
            primaryCode = "*#*#49#*#*",
            alternativeCode = "*#85#",
            description = "قائمة مصنع إنفينكس واختبار أجزاء اللوحة ومسارات الفحص"
        ),
        SecretCodeItem(
            id = "code_tecno",
            brandName = "Tecno (تكنو)",
            primaryCode = "*#*#49#*#*",
            alternativeCode = "*#85#",
            description = "قائمة فحص مكونات هواتف تكنو وتفعيل بروتوكولات الصيانة"
        ),
        SecretCodeItem(
            id = "code_alcatel",
            brandName = "Alcatel (الكاتيل)",
            primaryCode = "*#2886#",
            alternativeCode = null,
            description = "قائمة الـ MMI Test لفحص الهاتف وتفعيل وضع تشخيص الـ COM"
        ),
        SecretCodeItem(
            id = "code_lenovo",
            brandName = "Lenovo (لينوفو)",
            primaryCode = "*#*#4636#*#*",
            alternativeCode = null,
            description = "قائمة معلومات الهاتف واختبار الشبكة وتفعيل خيارات الاختبار"
        ),
        SecretCodeItem(
            id = "code_sony",
            brandName = "Sony Xperia (سوني)",
            primaryCode = "*#*#737378423#*#*",
            alternativeCode = null,
            description = "قائمة Service Menu لفحص حالة فتح البوتلودر (Bootloader unlock allowed: Yes/No)"
        ),
        SecretCodeItem(
            id = "code_motorola",
            brandName = "Motorola (موتورولا)",
            primaryCode = "*#*#2486#*#*",
            alternativeCode = null,
            description = "تفعيل وضع BP Tools و Factory Mode لتفليش وكتابة المودم"
        ),
        SecretCodeItem(
            id = "code_honor",
            brandName = "Honor (هونر)",
            primaryCode = "*#*#2345#*#*",
            alternativeCode = null,
            description = "قائمة تصحيح وفحص أجهزة هونر الحديثة"
        ),
        SecretCodeItem(
            id = "code_oneplus",
            brandName = "OnePlus (ون بلس)",
            primaryCode = "*#*#2346579#*#*",
            alternativeCode = null,
            description = "فتح وضع تصحيح الأخطاء واختبار خطوط الإشارة والـ Log"
        )
    )

    // 11. أدوات وبرامج السوفت وير الاحترافية
    val softwareTools = listOf(
        SoftwareToolItem(
            id = "tool_unlocktool",
            name = "UnlockTool",
            toolType = "أداة احترافية (سيرفر)",
            description = "الأداة الأولى عالمياً في صيانة السوفت وير وتخطي FRP وفك شفرات شاومي، سامسونج، أوبو، فيفو، آيفون.",
            supportedCPUs = "Qualcomm, MediaTek, Exynos, Spreadtrum, Kirin, Apple Bionic",
            officialUrl = "https://unlocktool.net/",
            mainCapabilities = listOf(
                "تخطي FRP لسامسونج بضغطة واحدة في ثوانٍ",
                "تخطي حساب Mi Account وفك البوتلودر بضغطة زر",
                "تفليش معالجات MTK في وضع BROM بدون كريدت",
                "جيلبريك وتخطي الآيكلاود من 5s حتى X مع تشغيل الشبكة"
            )
        ),
        SoftwareToolItem(
            id = "tool_chimera",
            name = "Chimera Tool",
            toolType = "دونجل / سوفت وير مرخص",
            description = "أداة عريقة لتصليح السيريال IMEI، باتش السيرت Patch Cert، وقراءة الأكواد الحية لهواتف سامسونج وهواوي وشاومي.",
            supportedCPUs = "Exynos, Snapdragon, Kirin, MTK",
            officialUrl = "https://chimeratool.com/",
            mainCapabilities = listOf(
                "إصلاح سيريال سامسونج وباتش سيرت دائم",
                "قراءة أكواد شبكات سامسونج عبر الخادم",
                "تخطي حماية هواوي ID على معالجات Kirin عبر Test Point",
                "سحب وإرجاع الدامبات والنسخ الاحتياطية"
            )
        ),
        SoftwareToolItem(
            id = "tool_miflash",
            name = "Mi Flash Tool",
            toolType = "أداة رسمية مجانية",
            description = "برنامج شركة شاومي الرسمي لتفليش رومات Fastboot بصيغة tgz وإحياء الأجهزة المعلقة على شعار Mi.",
            supportedCPUs = "Qualcomm Snapdragon, MediaTek",
            officialUrl = "https://xiaomiflashtool.com/",
            mainCapabilities = listOf(
                "تفليش رومات Fastboot الرسمية",
                "إمكانية الاحتفاظ ببيانات المستخدم (save user data)",
                "إعادة قفل البوتلودر مع الروم الرسمي الأصلي",
                "التفليش عبر وضع EDL 9008 لحسابات الوكالة"
            )
        ),
        SoftwareToolItem(
            id = "tool_odin",
            name = "Samsung Odin v3.14.4",
            toolType = "برنامج رسمي مجاني",
            description = "أشهر وأهم برنامج لتفليش هواتف وتطوير أنظمة سامسونج جالاكسي بملفات BL, AP, CP, CSC.",
            supportedCPUs = "Exynos, Qualcomm, MediaTek (Samsung)",
            officialUrl = "https://samsungodin.com/",
            mainCapabilities = listOf(
                "تفليش الفلاشات الرسمية المكونة من 4 ملفات",
                "توزيع بارتشنات الذاكرة بملف PIT",
                "ترقية وتنزيل إصدارات أندرويد و One UI",
                "تثبيت الريكفري المعدل TWRP وملفات الروت Magisk"
            )
        ),
        SoftwareToolItem(
            id = "tool_spflashtool",
            name = "SP Flash Tool (Smart Phone Flash Tool)",
            toolType = "أداة رسمية مجانية",
            description = "البرنامج القياسي لتفليش جميع الهواتف التي تعمل بمعالجات ميديا تيك عبر ملف الـ Scatter.",
            supportedCPUs = "MediaTek MT65xx / MT67xx / MT68xx",
            officialUrl = "https://spflashtool.com/",
            mainCapabilities = listOf(
                "تفليش الرومات الكاملة بملف MTxxxx_Android_scatter.txt",
                "عمل فورمات كامل للذاكرة بما فيها حماية FRP",
                "اختبار وقراءة قطاعات الذاكرة eMMC / UFS",
                "سحب نسخ احتياطية Readback من الذاكرة"
            )
        ),
        SoftwareToolItem(
            id = "tool_mtkclient",
            name = "MTK Client Tool",
            toolType = "أداة مجانية مفتوحة المصدر",
            description = "الأداة المفتوحة الأقوى لاستغلال ثغرة BROM SLA/DA وتخطي حماية البوت لودر لمعالجات MTK بدون أي تكلفة.",
            supportedCPUs = "MediaTek Helio & Dimensity",
            officialUrl = "https://github.com/bkerler/mtkclient",
            mainCapabilities = listOf(
                "تخطي حماية DAA و SLA في وضع BROM",
                "سحب وتفليش أي بارتشن منفرد كـ boot أو recovery أو nvram",
                "مسح حماية FRP وقفل الشاشة بضغطة واحدة",
                "إلغاء قفل البوتلودر فوراً بدون انتظار 168 ساعة"
            )
        ),
        SoftwareToolItem(
            id = "tool_3utools",
            name = "3uTools for iOS",
            toolType = "برنامج شامل مجاني",
            description = "أفضل وأسهل برنامج لإدارة وتفليش وعمل ريستور لأجهزة iPhone و iPad، وفحص نسبة البطارية والقطع الأصلية.",
            supportedCPUs = "Apple A-Series Bionic",
            officialUrl = "https://www.3u.com/",
            mainCapabilities = listOf(
                "عمل سوفت وير سريع بنقرة واحدة (Quick Flash)",
                "فحص أصالة الشاشة والبطارية والكاميرات (Verification Report)",
                "الخروج من وضع الريكفري Recovery بضغطة زر",
                "النسخ الاحتياطي واستعادة البيانات بسهولة"
            )
        ),
        SoftwareToolItem(
            id = "tool_qpst",
            name = "Qualcomm QPST / QFIL",
            toolType = "حزمة كوالكوم الرسمية",
            description = "الأداة الهندسية الرسمية من شركة كوالكوم لتفليش وسحب الرومات في وضع EDL 9008 عبر ملفات Firehose و Rawprogram.",
            supportedCPUs = "Qualcomm Snapdragon All Series",
            officialUrl = "https://qfiltool.com/",
            mainCapabilities = listOf(
                "تفليش وضع الطوارئ EDL 9008",
                "سحب وكتابة ملفات الشبكة QCN لاسترجاع السيريال",
                "إصلاح الموت المفاجئ للهواتف Hard Bricked",
                "دعم كامل لملفات rawprogram0.xml و patch0.xml"
            )
        )
    )

    // 12. قسم ملفات الدامب (Dump & Full Backups)
    val dumpCollection = listOf(
        DumpItem(
            id = "dump_pixel_2026",
            title = "GOOGLE PIXEL | DUMP & BACKUP COLLECTION (2026)",
            deviceModel = "Google Pixel (جميع الأجيال 6 / 7 / 8 / 9)",
            cpuAndChipset = "Google Tensor G1 / G2 / G3 / G4",
            memoryType = "UFS 3.1 / UFS 4.0",
            downloadUrl = "https://t.me/Yazunlo/68",
            description = "أكبر وأحدث تجميعة دامبات وملفات نسخ احتياطي كاملة ومسحوبة بأحدث الأجهزة لسلسلة جوجل بيكسل لإصلاح مشاكل التوقف على البوت والشبكة.",
            isHighlighted = true
        ),
        DumpItem(
            id = "dump_samsung_series",
            title = "Samsung Galaxy DUMP Collection (Exynos & Qualcomm)",
            deviceModel = "Samsung A-Series, S-Series, M-Series",
            cpuAndChipset = "Exynos 850/1280/1380 & Snapdragon 720G/778G",
            memoryType = "eMMC 5.1 & UFS 2.2 / 3.1",
            downloadUrl = "https://t.me/Yazunlo",
            description = "ملفات دامب مسحوبة عبر EasyJTAG و UFI Box لإحياء هواتف سامسونج الميتة بعد استبدال الذواكر وإصلاح البوت الأولي (BL Loader).",
            isHighlighted = false
        ),
        DumpItem(
            id = "dump_xiaomi_redmi",
            title = "Xiaomi & Redmi eMMC / UFS Dump Files",
            deviceModel = "Redmi 9, 9A, Note 8, Note 10 Pro, Poco X3 Pro",
            cpuAndChipset = "Snapdragon 732G/860 & Helio G25/G80",
            memoryType = "UFS 2.2 & eMMC 5.1",
            downloadUrl = "https://t.me/Yazunlo",
            description = "ملفات بوت كاملة (BOOT1, BOOT2, USERAREA 512MB) مع ملفات RPMB الموثقة لموديلات شاومي الأكثر طلباً في ورش الصيانة.",
            isHighlighted = false
        ),
        DumpItem(
            id = "dump_oppo_realme",
            title = "Oppo & Realme BROM Dump & Scatter Factory",
            deviceModel = "Oppo A15, A16, Reno 5, Realme C11, C21",
            cpuAndChipset = "Helio P35 / G35 & Snapdragon 720G",
            memoryType = "eMMC & UFS",
            downloadUrl = "https://t.me/Yazunlo",
            description = "دامبات كاملة مسحوبة بنسبة 100% لإصلاح مشكلة الموت على الخط الأحمر في SP Flash Tool واسترجاع البارتشنات المفقودة.",
            isHighlighted = false
        ),
        DumpItem(
            id = "dump_infinix_tecno",
            title = "Infinix & Tecno MediaTek Full Dump Archive",
            deviceModel = "Hot 10, Hot 11, Spark 7, Pop 5, Camon 18",
            cpuAndChipset = "Helio G70 / G85 / G88 / G96",
            memoryType = "eMMC 5.1 / UFS 2.1",
            downloadUrl = "https://t.me/Yazunlo",
            description = "ملفات حصرية تتضمن NVRAM و NVDATA السليمة لحل مشاكل Baseband Unknown ومشاكل الموت بعد الفورمات الخاطئ.",
            isHighlighted = false
        )
    )

    // ملفات التيست بوينت المسحوبة من شيميرا (Chimera TestPoint Archive)
    val chimeraTestPointDownloadUrl = "https://t.me/Yazunlo/11"
    val chimeraBrands = listOf("HUAWEI", "LENOVO", "NOKIA", "OPPO", "VIVO", "XIAOMI", "ZTE")
}
