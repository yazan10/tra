// Vercel Serverless: /api/posts — منشورات داخل الأقسام + مقالات المدونة
let posts = [
  { id: "p1", sectionId: "devices", title: "حل مشكلة موت Poco X3 Pro وشبلنة المعالج خطوة بخطوة", category: "هاردوير", date: "2026-09-26", views: 2450, body: "شرح تفصيلي لريبولنغ المعالج وإحياء الجهاز الميت.", blogUrl: "https://yaz-blog.blogspot.com/" },
  { id: "p2", sectionId: "software", title: "طريقة فك وتجاوز حماية حساب شاومي Mi Cloud بدون بوكسات", category: "سوفت وير", date: "2026-09-24", views: 3890, body: "أحدث الثغرات المدعومة لموديلات 2025/2026.", blogUrl: "https://yaz-blog.blogspot.com/" },
  { id: "p3", sectionId: "systems", title: "أحدث أكواد شفرات التوربو سيم والـ ICCID لفك شبكات الآيفون", category: "شبكات", date: "2026-09-20", views: 5120, body: "قائمة ICCID محدثة أسبوعياً.", blogUrl: "https://yaz-blog.blogspot.com/" }
];

module.exports = (req, res) => {
  res.setHeader("Access-Control-Allow-Origin", "*");
  res.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
  res.setHeader("Access-Control-Allow-Headers", "Content-Type");
  if (req.method === "OPTIONS") return res.status(200).end();
  const body = () => { try { return typeof req.body === "string" ? JSON.parse(req.body || "{}") : (req.body || {}); } catch { return {}; } };

  if (req.method === "GET") {
    const { sectionId, q } = req.query || {};
    let out = posts;
    if (sectionId) out = out.filter(p => p.sectionId === sectionId);
    if (q) out = out.filter(p => p.title.includes(q));
    return res.status(200).json({ posts: out, count: out.length, blog: "https://yaz-blog.blogspot.com/" });
  }
  if (req.method === "POST" || req.method === "PUT") {
    const b = body();
    if (!b.title) return res.status(400).json({ error: "title required" });
    if (b.id) {
      const i = posts.findIndex(p => p.id === b.id);
      if (i >= 0) posts[i] = { ...posts[i], ...b };
      else posts.unshift({ id: b.id, date: new Date().toISOString().slice(0, 10), views: 0, category: "عام", sectionId: "general", ...b });
    } else {
      posts.unshift({ id: "p" + Date.now(), date: new Date().toISOString().slice(0, 10), views: 0, category: "عام", sectionId: "general", blogUrl: "https://yaz-blog.blogspot.com/", ...b });
    }
    return res.status(200).json({ success: true, posts });
  }
  if (req.method === "DELETE") {
    const b = body();
    const id = b.id || req.query.id;
    posts = posts.filter(p => p.id !== id);
    return res.status(200).json({ success: true, posts });
  }
  return res.status(405).json({ error: "Method not allowed" });
};
