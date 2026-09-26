// Vercel Serverless: /api/reports — تقارير وأعطال الفنيين + إحصائيات
let reports = [
  { id: "r1", model: "Samsung A52s 5G", fault: "علامة مثلث أصفر وارتفاع حرارة", note: "تم تأكيد عطل ثرمستور البوردة السفلية TH1000", status: "محلول", date: "2026-09-26" },
  { id: "r2", model: "Redmi Note 11S", fault: "توقف الصوت بعد التفليش", note: "يحتاج إعادة كتابة NVRAM", status: "قيد المراجعة", date: "2026-09-25" }
];

module.exports = (req, res) => {
  res.setHeader("Access-Control-Allow-Origin", "*");
  res.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,OPTIONS");
  res.setHeader("Access-Control-Allow-Headers", "Content-Type");
  if (req.method === "OPTIONS") return res.status(200).end();
  const body = () => { try { return typeof req.body === "string" ? JSON.parse(req.body || "{}") : (req.body || {}); } catch { return {}; } };

  if (req.method === "GET") {
    return res.status(200).json({
      reports,
      stats: { technicians: 14820, imeiChecks: 85410, testPointQueries: 32190, romDumps: 19650 },
      count: reports.length
    });
  }
  if (req.method === "POST") {
    const b = body();
    if (!b.model || !b.fault) return res.status(400).json({ error: "model & fault required" });
    reports.unshift({ id: "r" + Date.now(), status: "جديد", date: new Date().toISOString().slice(0, 10), note: "", ...b });
    return res.status(200).json({ success: true, reports });
  }
  if (req.method === "DELETE") {
    const b = body();
    const id = b.id || req.query.id;
    reports = reports.filter(r => r.id !== id);
    return res.status(200).json({ success: true, reports });
  }
  return res.status(405).json({ error: "Method not allowed" });
};
