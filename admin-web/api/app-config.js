// Vercel Serverless Function: /api/app-config
let inMemoryConfig = {
  currentVersionCode: 1,
  latestVersionCode: 1,
  latestVersionName: "1.0.0",
  isMandatory: false,
  updateTitle: "تحديث جديد متوفر لتطبيق فون ترافيك",
  updateMessage: "تمت إضافة توافقات شاشات وأكواد صيانة جديدة وتحديث روابط الفحص لعام 2026.",
  downloadUrl: "https://t.me/Yazunlo",
  broadcastTitle: "تنبيه فني هام 🛠️",
  broadcastMessage: "تم تحديث روابط فحص الآيكلاود وشاومي وقسم الرومات لسرعة مضاعفة.",
  isBroadcastActive: true,
  blogUrl: "https://yaz-blog.blogspot.com/",
  adsense: {
    publisherId: "pub-4752417544013096",
    customerId: "9947688120",
    adsEnabled: true
  }
};

module.exports = (req, res) => {
  res.setHeader("Access-Control-Allow-Origin", "*");
  res.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
  res.setHeader("Access-Control-Allow-Headers", "Content-Type");

  if (req.method === "OPTIONS") {
    return res.status(200).end();
  }

  if (req.method === "POST") {
    try {
      const body = typeof req.body === "string" ? JSON.parse(req.body) : req.body;
      inMemoryConfig = { ...inMemoryConfig, ...body };
      return res.status(200).json({ success: true, config: inMemoryConfig });
    } catch (e) {
      return res.status(400).json({ error: "Invalid JSON format" });
    }
  }

  return res.status(200).json(inMemoryConfig);
};
