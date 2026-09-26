// Vercel Serverless: /api/sections — إدارة أقسام التطبيق (نشر/حذف/تعديل)
let sections = [
  { id: "screens", name: "مقارنة الشاشات", icon: "fa-display", color: "#0284C7", desc: "OLED vs AMOLED vs IPS LCD", active: true },
  { id: "devices", name: "مقارنة الأجهزة", icon: "fa-mobile-screen", color: "#0284C7", desc: "مقارنة المعالجات والبطاريات", active: true },
  { id: "test_points", name: "نقاط التيست بوينت", icon: "fa-microchip", color: "#D97706", desc: "Qualcomm 9008, BROM, Kirin", active: true },
  { id: "software", name: "دورات السوفت وير", icon: "fa-graduation-cap", color: "#16A34A", desc: "شروحات التفليش وفك الحماية", active: true },
  { id: "screen_compat", name: "توافق الشاشات", icon: "fa-clone", color: "#7C3AED", desc: "أي شاشة تركب على أي موديل", active: true },
  { id: "systems", name: "أوضاع النظام", icon: "fa-gears", color: "#64748B", desc: "EDL, Fastboot, Recovery, DFU", active: true },
  { id: "device_models", name: "موديلات الأجهزة", icon: "fa-barcode", color: "#0D47A1", desc: "الأكواد المصنعية والتجارية", active: true },
  { id: "imei_check", name: "فحص IMEI", icon: "fa-shield-halved", color: "#16A34A", desc: "فحص الآيكلاود وشاومي", active: true },
  { id: "rom_downloads", name: "تحميل الرومات", icon: "fa-download", color: "#0284C7", desc: "رومات شاومي وسامسونج وأوبو", active: true },
  { id: "secret_codes", name: "الأكواد السرية", icon: "fa-key", color: "#7C3AED", desc: "أكواد المطور وفحص الهاردوير", active: true },
  { id: "software_tools", name: "أدوات السوفت وير", icon: "fa-screwdriver-wrench", color: "#EA580C", desc: "بوكسات ودنغل وأدوات مجانية", active: true },
  { id: "dump_collection", name: "ملفات الدامب", icon: "fa-database", color: "#EA580C", desc: "دامبات البوت وتجميعة Pixel 2026", active: true }
];

module.exports = (req, res) => {
  res.setHeader("Access-Control-Allow-Origin", "*");
  res.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
  res.setHeader("Access-Control-Allow-Headers", "Content-Type");
  if (req.method === "OPTIONS") return res.status(200).end();
  const body = () => { try { return typeof req.body === "string" ? JSON.parse(req.body || "{}") : (req.body || {}); } catch { return {}; } };

  if (req.method === "GET") return res.status(200).json({ sections, count: sections.length });

  if (req.method === "POST" || req.method === "PUT") {
    const b = body();
    if (!b.name) return res.status(400).json({ error: "name required" });
    if (b.id) {
      const i = sections.findIndex(s => s.id === b.id);
      if (i >= 0) sections[i] = { ...sections[i], ...b };
      else sections.push({ id: b.id, icon: "fa-cube", color: "#1565C0", desc: "", active: true, ...b });
    } else {
      sections.push({ id: "sec_" + Date.now(), icon: "fa-cube", color: "#1565C0", desc: "", active: true, ...b });
    }
    return res.status(200).json({ success: true, sections });
  }
  if (req.method === "DELETE") {
    const b = body();
    const id = b.id || req.query.id;
    sections = sections.filter(s => s.id !== id);
    return res.status(200).json({ success: true, sections });
  }
  return res.status(405).json({ error: "Method not allowed" });
};
