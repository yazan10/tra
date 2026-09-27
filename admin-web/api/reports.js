// /api/reports — persistent via GitHub data-store.json
const { loadDoc, saveDoc } = require("./_store");

const DEFAULTS = [
  { id: "r1", model: "Samsung A52s 5G", fault: "علامة مثلث أصفر وارتفاع حرارة", note: "تم تأكيد عطل ثرمستور البوردة السفلية TH1000", status: "محلول", date: "2026-09-26" },
  { id: "r2", model: "Redmi Note 11S", fault: "توقف الصوت بعد التفليش", note: "يحتاج إعادة كتابة NVRAM", status: "قيد المراجعة", date: "2026-09-25" }
];

const requireAdmin = (req) => req.headers["x-admin-password"] === "aylool@#5";

module.exports = async (req, res) => {
  res.setHeader("Access-Control-Allow-Origin", "*");
  res.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,OPTIONS");
  res.setHeader("Access-Control-Allow-Headers", "Content-Type, x-admin-password");
  if (req.method === "OPTIONS") return res.status(200).end();
  const body = () => { try { return typeof req.body === "string" ? JSON.parse(req.body || "{}") : (req.body || {}); } catch { return {}; } };

  const doc = (await loadDoc()) || {};
  let reports = Array.isArray(doc.reports) ? doc.reports : DEFAULTS.map(r => ({ ...r }));

  if (req.method === "GET") {
    return res.status(200).json({
      reports,
      stats: { technicians: 14820, imeiChecks: 85410, testPointQueries: 32190, romDumps: 19650 },
      count: reports.length
    });
  }

  if (!requireAdmin(req)) return res.status(401).json({ error: "Unauthorized" });

  if (req.method === "POST") {
    const b = body();
    if (!b.model || !b.fault) return res.status(400).json({ error: "model & fault required" });
    reports.unshift({ id: "r" + Date.now(), status: "جديد", date: new Date().toISOString().slice(0, 10), note: "", ...b });
    const saved = await saveDoc({ ...doc, reports });
    return res.status(200).json({ success: true, reports, persisted: saved.ok });
  }
  if (req.method === "DELETE") {
    const b = body();
    const id = b.id || req.query.id;
    reports = reports.filter(r => r.id !== id);
    const saved = await saveDoc({ ...doc, reports });
    return res.status(200).json({ success: true, reports, persisted: saved.ok });
  }
  return res.status(405).json({ error: "Method not allowed" });
};
