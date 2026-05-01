// screens-gallery.jsx — Galería principal (masonry 2 col, estilo bitácora)

const QUICK_FILTERS = [
  { id: 'all', label: 'Todas' },
  { id: 'recent', label: 'Recientes' },
  { id: 'fav', label: 'Favoritas' },
  { id: 'unid', label: 'Sin identificar' },
];

function GalleryHeader({ search, setSearch, showSearch, setShowSearch }) {
  return (
    <div style={{ padding: '8px 20px 12px', background: 'var(--bg-cream)' }}>
      <div style={{ display: 'flex', alignItems: 'flex-end', justifyContent: 'space-between', gap: 12 }}>
        <div>
          <div style={{ fontFamily: 'Inter', fontSize: 11, fontWeight: 600, letterSpacing: '0.16em', textTransform: 'uppercase', color: 'var(--moss-600)', marginBottom: 4 }}>
            Bitácora · primavera 2026
          </div>
          <div className="serif" style={{ fontSize: 36, lineHeight: 1, fontWeight: 400, color: 'var(--ink)', letterSpacing: '-0.02em' }}>
            Aviario
            <span style={{ fontStyle: 'italic', color: 'var(--moss-700)' }}> local</span>
          </div>
        </div>
        <button onClick={() => setShowSearch(!showSearch)} style={{
          width: 38, height: 38, borderRadius: '50%',
          border: '1px solid var(--line)',
          background: 'rgba(255,255,255,0.7)',
          display: 'flex', alignItems: 'center', justifyContent: 'center',
          color: 'var(--moss-700)', cursor: 'pointer',
        }}>
          <Icon name="search" size={18} />
        </button>
      </div>

      {showSearch && (
        <div style={{ marginTop: 12, display: 'flex', alignItems: 'center', gap: 8, padding: '10px 14px', borderRadius: 12, background: 'rgba(255,255,255,0.7)', border: '1px solid var(--line)' }}>
          <Icon name="search" size={16} color="var(--ink-mute)" />
          <input
            value={search}
            onChange={e => setSearch(e.target.value)}
            placeholder="Buscar especie, lugar, etiqueta…"
            style={{ flex: 1, border: 'none', background: 'transparent', outline: 'none', fontFamily: 'Inter', fontSize: 14, color: 'var(--ink)' }}
            autoFocus
          />
        </div>
      )}
    </div>
  );
}

function QuickFilters({ active, setActive, activeTags, setActiveTags }) {
  return (
    <div style={{ padding: '0 20px 14px', background: 'var(--bg-cream)' }}>
      <div className="nice-scroll" style={{ display: 'flex', gap: 8, overflowX: 'auto', paddingBottom: 4 }}>
        {QUICK_FILTERS.map(f => (
          <Chip key={f.id} active={active === f.id} onClick={() => setActive(f.id)}>
            {f.label}
          </Chip>
        ))}
        <div style={{ width: 1, background: 'var(--line)', margin: '4px 4px' }} />
        {ALL_TAGS.slice(0, 6).map(tag => (
          <Chip
            key={tag}
            active={activeTags.includes(tag)}
            onClick={() => setActiveTags(activeTags.includes(tag) ? activeTags.filter(t => t !== tag) : [...activeTags, tag])}
          >
            #{tag}
          </Chip>
        ))}
      </div>
    </div>
  );
}

function GalleryCard({ bird, onClick, idx }) {
  const tilt = (idx % 4 === 1) ? 0.4 : (idx % 4 === 3) ? -0.5 : 0;
  return (
    <div
      onClick={onClick}
      style={{
        background: '#FFFCF5',
        borderRadius: 6,
        overflow: 'hidden',
        boxShadow: '0 1px 2px rgba(74,58,40,0.05), 0 6px 16px rgba(74,58,40,0.08)',
        marginBottom: 12,
        cursor: 'pointer',
        transform: `rotate(${tilt}deg)`,
        border: '1px solid rgba(122,90,58,0.08)',
        breakInside: 'avoid',
        position: 'relative',
      }}
    >
      <div style={{ position: 'relative', width: '100%', height: bird.h * 0.55, overflow: 'hidden', background: 'var(--bark-100)' }}>
        <img src={bird.photo} alt={bird.species || 'ave sin identificar'} style={{ width: '100%', height: '100%', objectFit: 'cover', display: 'block' }} />
        {bird.fav && (
          <div style={{ position: 'absolute', top: 8, right: 8, width: 26, height: 26, borderRadius: '50%', background: 'rgba(251,249,243,0.92)', display: 'flex', alignItems: 'center', justifyContent: 'center', color: 'var(--accent-red)' }}>
            <Icon name="star-fill" size={13} />
          </div>
        )}
        {!bird.species && (
          <div style={{ position: 'absolute', top: 8, left: 8, padding: '3px 8px', borderRadius: 4, background: 'rgba(31,27,20,0.78)', color: '#FBF9F3', fontFamily: 'Inter', fontSize: 9.5, fontWeight: 600, letterSpacing: '0.1em', textTransform: 'uppercase' }}>
            Sin identificar
          </div>
        )}
      </div>
      <div style={{ padding: '9px 11px 11px' }}>
        <div className="serif" style={{ fontSize: 14, lineHeight: 1.15, fontWeight: 500, color: 'var(--ink)', fontStyle: bird.species ? 'normal' : 'italic' }}>
          {bird.species || 'Especie sin identificar'}
        </div>
        <div style={{ display: 'flex', alignItems: 'center', gap: 5, marginTop: 4, fontFamily: 'Inter', fontSize: 10.5, color: 'var(--ink-mute)' }}>
          <Icon name="calendar" size={10} />
          <span>{bird.dateShort}</span>
          <span style={{ opacity: 0.5 }}>·</span>
          <span style={{ overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>{bird.locShort}</span>
        </div>
        {bird.tags.length > 0 && (
          <div style={{ marginTop: 7, display: 'flex', flexWrap: 'wrap', gap: 4 }}>
            {bird.tags.slice(0, 2).map(t => (
              <span key={t} style={{ fontFamily: 'Inter', fontSize: 9.5, padding: '2px 6px', borderRadius: 3, background: 'var(--moss-100)', color: 'var(--moss-700)', fontWeight: 500 }}>
                #{t}
              </span>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

function GalleryScreen({ onOpenBird }) {
  const [search, setSearch] = React.useState('');
  const [showSearch, setShowSearch] = React.useState(false);
  const [active, setActive] = React.useState('all');
  const [activeTags, setActiveTags] = React.useState([]);

  const filtered = BIRDS.filter(b => {
    if (active === 'fav' && !b.fav) return false;
    if (active === 'unid' && b.species) return false;
    if (active === 'recent') {
      // últimas 5 (ya están ordenadas desc)
      const top5 = BIRDS.slice(0, 5).map(x => x.id);
      if (!top5.includes(b.id)) return false;
    }
    if (activeTags.length > 0 && !activeTags.some(t => b.tags.includes(t))) return false;
    if (search) {
      const q = search.toLowerCase();
      const hay = [b.species, b.location, ...b.tags].filter(Boolean).join(' ').toLowerCase();
      if (!hay.includes(q)) return false;
    }
    return true;
  });

  const colA = filtered.filter((_, i) => i % 2 === 0);
  const colB = filtered.filter((_, i) => i % 2 === 1);

  return (
    <div className="paper" style={{ minHeight: '100%', paddingBottom: 100 }}>
      <GalleryHeader search={search} setSearch={setSearch} showSearch={showSearch} setShowSearch={setShowSearch} />
      <QuickFilters active={active} setActive={setActive} activeTags={activeTags} setActiveTags={setActiveTags} />

      {/* Resumen pequeño tipo bitácora */}
      <div style={{ padding: '0 20px 16px', display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
        <div className="serif" style={{ fontSize: 13, fontStyle: 'italic', color: 'var(--ink-soft)' }}>
          {filtered.length} {filtered.length === 1 ? 'observación' : 'observaciones'}
          {(active !== 'all' || activeTags.length > 0 || search) && <span style={{ color: 'var(--moss-700)' }}> · filtrado</span>}
        </div>
        <div style={{ fontFamily: 'Inter', fontSize: 10.5, color: 'var(--ink-mute)', letterSpacing: '0.1em', textTransform: 'uppercase' }}>
          Más recientes
        </div>
      </div>

      {filtered.length === 0 ? (
        <EmptyGallery />
      ) : (
        <div style={{ padding: '0 16px', display: 'grid', gridTemplateColumns: '1fr 1fr', gap: 12 }}>
          <div>{colA.map((b, i) => <GalleryCard key={b.id} bird={b} idx={i*2} onClick={() => onOpenBird(b)} />)}</div>
          <div style={{ paddingTop: 14 }}>{colB.map((b, i) => <GalleryCard key={b.id} bird={b} idx={i*2+1} onClick={() => onOpenBird(b)} />)}</div>
        </div>
      )}

      {/* Pie de bitácora */}
      <div style={{ padding: '24px 32px 0', textAlign: 'center' }}>
        <LeafDivider />
        <div className="hand" style={{ marginTop: 10, fontSize: 18, color: 'var(--moss-700)', opacity: 0.7 }}>
          “quien observa, recuerda”
        </div>
      </div>
    </div>
  );
}

function EmptyGallery() {
  return (
    <div style={{ padding: '40px 32px', textAlign: 'center' }}>
      <div style={{ width: 88, height: 88, borderRadius: '50%', background: 'var(--moss-100)', display: 'flex', alignItems: 'center', justifyContent: 'center', margin: '0 auto 16px', color: 'var(--moss-700)' }}>
        <Icon name="sparrow" size={42} stroke={1.4} />
      </div>
      <div className="serif" style={{ fontSize: 22, fontWeight: 500, color: 'var(--ink)', marginBottom: 6 }}>
        Aún no has registrado aves
      </div>
      <div className="sans" style={{ fontSize: 13.5, color: 'var(--ink-soft)', lineHeight: 1.5, marginBottom: 18 }}>
        Cuando captures tu primera observación,<br/>aparecerá aquí, en tu bitácora.
      </div>
      <button style={{ padding: '11px 20px', borderRadius: 999, border: 'none', background: 'var(--moss-700)', color: '#FBF9F3', fontFamily: 'Inter', fontWeight: 500, fontSize: 14, cursor: 'pointer' }}>
        Añadir primera observación
      </button>
    </div>
  );
}

Object.assign(window, { GalleryScreen });
