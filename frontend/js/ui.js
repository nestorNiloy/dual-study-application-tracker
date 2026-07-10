/**
 * ui.js — DualTrack v2
 * Toast notifications, stat cards, badge config.
 */

/* ── Status badge config ─────────────────────────── */
const STATUS_CFG = {
  APPLIED:      { label: 'Applied',      dot: '#60a5fa', text: '#93c5fd', bg: 'rgba(59,130,246,0.1)',  border: 'rgba(59,130,246,0.25)'  },
  INTERVIEWING: { label: 'Interviewing', dot: '#c084fc', text: '#d8b4fe', bg: 'rgba(168,85,247,0.1)', border: 'rgba(168,85,247,0.25)' },
  OFFERED:      { label: 'Offered',      dot: '#34d399', text: '#6ee7b7', bg: 'rgba(16,185,129,0.1)', border: 'rgba(16,185,129,0.25)' },
  REJECTED:     { label: 'Rejected',     dot: '#fb7185', text: '#fda4af', bg: 'rgba(244,63,94,0.1)',  border: 'rgba(244,63,94,0.25)'  },
};

/* ── Toast ───────────────────────────────────────── */
function showToast(message, type = 'info') {
  const container = document.getElementById('toast-container');

  const colors = {
    success: { border: 'rgba(16,185,129,0.35)',  bg: 'rgba(16,185,129,0.1)',  text: '#6ee7b7'  },
    error:   { border: 'rgba(244,63,94,0.35)',   bg: 'rgba(244,63,94,0.1)',   text: '#fda4af'  },
    warn:    { border: 'rgba(251,191,36,0.35)',   bg: 'rgba(251,191,36,0.1)',  text: '#fde68a'  },
    info:    { border: 'rgba(99,102,241,0.35)',   bg: 'rgba(99,102,241,0.1)',  text: '#a5b4fc'  },
  };

  const icons = {
    success: '✓',
    error:   '✕',
    warn:    '⚠',
    info:    'ℹ',
  };

  const c = colors[type] || colors.info;

  const toast = document.createElement('div');
  toast.style.cssText = `
    display:flex; align-items:flex-start; gap:10px;
    padding:12px 16px; border-radius:12px; max-width:320px;
    border:1px solid ${c.border}; background:${c.bg};
    color:${c.text}; font-size:13px; font-family:'DM Sans',sans-serif;
    backdrop-filter:blur(12px); box-shadow:0 8px 24px rgba(0,0,0,0.3);
    animation:toastIn 0.35s cubic-bezier(0.16,1,0.3,1) forwards;
    pointer-events:auto;
  `;

  const icon = document.createElement('span');
  icon.textContent = icons[type] || icons.info;
  icon.style.cssText = 'flex-shrink:0; font-weight:700; margin-top:1px;';

  const text = document.createElement('span');
  text.innerHTML = message;

  toast.appendChild(icon);
  toast.appendChild(text);
  container.appendChild(toast);

  setTimeout(() => {
    toast.style.animation = 'toastOut 0.3s ease forwards';
    setTimeout(() => toast.remove(), 320);
  }, 4000);
}

/* ── Stats ───────────────────────────────────────── */
function updateStats(apps) {
  const counts = { APPLIED: 0, INTERVIEWING: 0, OFFERED: 0, REJECTED: 0 };
  apps.forEach(a => { if (counts[a.status] !== undefined) counts[a.status]++; });

  setText('stat-total',        apps.length);
  setText('stat-applied',      counts.APPLIED);
  setText('stat-interviewing', counts.INTERVIEWING);
  setText('stat-offered',      counts.OFFERED);
}

/* ── Helpers ─────────────────────────────────────── */
function setText(id, value) {
  const el = document.getElementById(id);
  if (el) el.textContent = value;
}

function safeText(str) {
  if (!str) return '';
  const d = document.createElement('div');
  d.textContent = str;
  return d.innerHTML;
}

function formatDate(dateStr) {
  if (!dateStr) return '—';
  try {
    return new Date(dateStr).toLocaleDateString('en-GB', {
      day: '2-digit', month: 'short', year: 'numeric'
    });
  } catch { return dateStr; }
}
