// /api/app-config — persistent via GitHub data-store.json (see _store.js)
const { loadDoc, saveDoc } = require("./_store");

const DEFAULTS = {
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
  isMaintenanceMode: false,
  maintenanceTitle: "سنعود قريباً 🛠️",
  maintenanceMessage: "نقوم حالياً بأعمال الصيانة والتطوير — سيتم إبلاغكم بكل جديد عبر الإشعارات.",
  adsense: {
    publisherId: "pub-4752417544013096",
    customerId: "9947688120",
    adsEnabled: true
  }
};

module.exports = async (req, res) => {
  res.setHeader("Access-Control-Allow-Origin", "*");
  res.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
  res.setHeader("Access-Control-Allow-Headers", "Content-Type, x-admin-password");

  if (req.method === "OPTIONS") return res.status(200).end();

  if (req.method === "POST") {
    if (req.headers["x-admin-password"] !== "aylool@#5") {
      return res.status(401).json({ error: "Unauthorized: admin password required" });
    }
    try {
      const body = typeof req.body === "string" ? JSON.parse(req.body || "{}") : (req.body || {});
      const doc = (await loadDoc()) || {};
      const prev = { ...DEFAULTS, ...(doc.config || {}) };
      if (body.adsense && typeof body.adsense === "object") {
        body.adsense = { ...prev.adsense, ...body.adsense };
      }
      const next = { ...prev, ...body };
      const saved = await saveDoc({ ...doc, config: next });
      return res.status(200).json({
        success: true,
        config: next,
        persisted: saved.ok,
        persistNote: saved.ok ? undefined : saved.reason
      });
    } catch (e) {
      return res.status(400).json({ error: "Invalid JSON format" });
    }
  }

  const doc = (await loadDoc()) || {};
  const config = { ...DEFAULTS, ...(doc.config || {}) };

  // فحص الإصدار من التطبيق: ?versionCode=1
  const q = req.query || {};
  if (q.versionCode !== undefined) {
    const current = parseInt(q.versionCode, 10) || 0;
    return res.status(200).json({
      ...config,
      deviceVersionCode: current,
      updateAvailable: config.latestVersionCode > current,
      mustUpdate: config.isMandatory && config.latestVersionCode > current
    });
  }

  return res.status(200).json(config);
};
