// Persistent store: admin-web/data-store.json inside the GitHub repo itself.
// GET  -> fresh read via api.github.com (no CDN cache) with 20s memory TTL.
// POST -> read sha + PUT contents (needs ADMIN_GITHUB_TOKEN env on Vercel).
// Falls back to in-memory when GitHub is unreachable, and to endpoint
// defaults when the file does not exist yet.
const REPO = "yazan10/tra";
const PATH = "admin-web/data-store.json";
const TTL_MS = 20 * 1000;

let memDoc = null;
let memAt = 0;

function ghHeaders() {
  const h = {
    "Accept": "application/vnd.github+json",
    "User-Agent": "PhoneTraffic-Admin",
    "X-GitHub-Api-Version": "2022-11-28"
  };
  if (process.env.ADMIN_GITHUB_TOKEN) {
    h["Authorization"] = "Bearer " + process.env.ADMIN_GITHUB_TOKEN;
  }
  return h;
}

async function ghRead() {
  try {
    const r = await fetch(
      `https://api.github.com/repos/${REPO}/contents/${PATH}?ref=main`,
      { headers: ghHeaders() }
    );
    if (r.status === 404) return { sha: null, doc: null };
    if (!r.ok) return null;
    const j = await r.json();
    const text = Buffer.from(j.content || "", "base64").toString("utf-8");
    return { sha: j.sha, doc: JSON.parse(text) };
  } catch (e) {
    return null;
  }
}

async function loadDoc() {
  const now = Date.now();
  if (memDoc && now - memAt < TTL_MS) return memDoc;
  const g = await ghRead();
  if (g) {
    memDoc = g.doc;
    memAt = now;
    return memDoc;
  }
  return memDoc; // may be null -> endpoints use defaults
}

async function saveDoc(patch) {
  // Always re-read sha fresh (multi-instance safe), merge, PUT.
  const g = await ghRead();
  const base = (g && g.doc) || memDoc || {};
  const doc = { ...base, ...patch, _updatedAt: new Date().toISOString() };
  const body = {
    message: "admin: update app data " + new Date().toISOString(),
    content: Buffer.from(JSON.stringify(doc, null, 2), "utf-8").toString("base64")
  };
  if (g && g.sha) body.sha = g.sha;
  if (!process.env.ADMIN_GITHUB_TOKEN) {
    // No token: keep memory-only (dev fallback)
    memDoc = doc;
    memAt = Date.now();
    return { ok: false, reason: "no-token", doc };
  }
  try {
    const r = await fetch(
      `https://api.github.com/repos/${REPO}/contents/${PATH}`,
      { method: "PUT", headers: { ...ghHeaders(), "Content-Type": "application/json" }, body: JSON.stringify(body) }
    );
    if (!r.ok) {
      const t = await r.text();
      return { ok: false, reason: "github-" + r.status + ":" + t.slice(0, 120), doc };
    }
    memDoc = doc;
    memAt = Date.now();
    return { ok: true, doc };
  } catch (e) {
    return { ok: false, reason: String(e).slice(0, 120), doc };
  }
}

module.exports = { loadDoc, saveDoc };
