/**
 * table.js — DualTrack v2
 * Builds table rows using DOM methods only.
 * No innerHTML + template literals = no blank row bug.
 */

function renderTable(apps, filter, searchQuery) {
  const tbody      = document.getElementById('applications-table-body');
  const emptyState = document.getElementById('empty-state');
  const countEl    = document.getElementById('record-count');

  /* 1 — Filter by status */
  let filtered = filter === 'ALL' ? apps : apps.filter(a => a.status === filter);

  /* 2 — Filter by search */
  if (searchQuery) {
    const q = searchQuery.toLowerCase();
    filtered = filtered.filter(a =>
      (a.companyName   || '').toLowerCase().includes(q) ||
      (a.positionTitle || '').toLowerCase().includes(q) ||
      (a.notes         || '').toLowerCase().includes(q)
    );
  }

  /* 3 — Update count label */
  countEl.textContent = filtered.length === apps.length
    ? `(${apps.length})`
    : `(${filtered.length} / ${apps.length})`;

  /* 4 — Clear table */
  while (tbody.firstChild) tbody.removeChild(tbody.firstChild);

  /* 5 — Empty state */
  if (filtered.length === 0) {
    emptyState.classList.remove('hidden');
    document.getElementById('empty-title').textContent =
      apps.length > 0 ? 'No matches' : 'No applications yet';
    document.getElementById('empty-sub').textContent =
      apps.length > 0 ? 'Try a different filter or search' : 'Add your first application →';
    return;
  }

  emptyState.classList.add('hidden');

  /* 6 — Build rows with DOM methods */
  filtered.forEach((app, index) => {
    const cfg     = STATUS_CFG[app.status] || STATUS_CFG.APPLIED;
    const dateStr = formatDate(app.applicationDate);

    const tr = document.createElement('tr');
    tr.className    = 'app-row';
    tr.dataset.id   = app.id;
    tr.style.cssText = 'border-bottom:1px solid rgba(51,65,85,0.25); transition:background 0.15s;';

    /* — ID cell — */
    const tdId = document.createElement('td');
    tdId.style.cssText = 'padding:14px 20px; font-family:"DM Mono",monospace; font-size:11px; color:#475569;';
    tdId.textContent = app.id ?? (index + 1);
    tr.appendChild(tdId);

    /* — Company cell — */
    const tdCompany = document.createElement('td');
    tdCompany.style.cssText = 'padding:14px 20px;';
    const companySpan = document.createElement('span');
    companySpan.style.cssText = 'font-family:"Syne",sans-serif; font-weight:600; font-size:14px; color:#e2e8f0;';
    companySpan.textContent = app.companyName || '—';
    tdCompany.appendChild(companySpan);
    tr.appendChild(tdCompany);

    /* — Position cell — */
    const tdPosition = document.createElement('td');
    tdPosition.style.cssText = 'padding:14px 20px; font-size:13px; color:#cbd5e1;';
    tdPosition.textContent = app.positionTitle || '—';
    tr.appendChild(tdPosition);

    /* — Status badge cell — */
    const tdStatus = document.createElement('td');
    tdStatus.style.cssText = 'padding:14px 20px;';
    const badge = document.createElement('span');
    badge.style.cssText = `
      display:inline-flex; align-items:center; gap:6px;
      font-family:"DM Mono",monospace; font-size:11px; font-weight:500;
      border-radius:999px; padding:3px 10px; white-space:nowrap;
      background:${cfg.bg}; border:1px solid ${cfg.border}; color:${cfg.text};
    `;
    const dot = document.createElement('span');
    dot.style.cssText = `width:6px; height:6px; border-radius:50%; background:${cfg.dot}; flex-shrink:0;`;
    const badgeText = document.createElement('span');
    badgeText.textContent = cfg.label;
    badge.appendChild(dot);
    badge.appendChild(badgeText);
    tdStatus.appendChild(badge);
    tr.appendChild(tdStatus);

    /* — Date cell — */
    const tdDate = document.createElement('td');
    tdDate.style.cssText = 'padding:14px 20px; font-family:"DM Mono",monospace; font-size:11px; color:#94a3b8; white-space:nowrap;';
    tdDate.textContent = dateStr;
    tr.appendChild(tdDate);

    /* — Notes cell — */
    const tdNotes = document.createElement('td');
    tdNotes.style.cssText = 'padding:14px 20px; font-size:12px; color:#64748b; max-width:180px;';
    const notesSpan = document.createElement('span');
    notesSpan.style.cssText = 'display:block; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;';
    notesSpan.textContent   = app.notes || '';
    notesSpan.title         = app.notes || '';
    tdNotes.appendChild(notesSpan);
    tr.appendChild(tdNotes);

    /* — Delete cell — */
    const tdDel = document.createElement('td');
    tdDel.style.cssText = 'padding:14px 20px; text-align:center;';
    const delBtn = document.createElement('button');
    delBtn.className  = 'delete-btn';
    delBtn.dataset.id = app.id;
    delBtn.title      = `Delete ${app.companyName}`;
    delBtn.style.cssText = `
      background:rgba(244,63,94,0.08); border:1px solid rgba(244,63,94,0.2);
      color:#fb7185; border-radius:6px; padding:6px 8px; cursor:pointer;
      display:inline-flex; align-items:center; justify-content:center;
      transition:all 0.2s;
    `;
    delBtn.innerHTML = `<svg width="14" height="14" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" viewBox="0 0 24 24"><path d="M3 6h18"/><path d="M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6"/><path d="M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2"/></svg>`;
    tdDel.appendChild(delBtn);
    tr.appendChild(tdDel);

    /* — Row hover — */
    tr.addEventListener('mouseenter', () => tr.style.background = 'rgba(99,102,241,0.04)');
    tr.addEventListener('mouseleave', () => tr.style.background = '');

    tbody.appendChild(tr);
  });
}
