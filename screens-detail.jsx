// screens-detail.jsx — Ficha de observación (detalle estilo bitácora)

function DetailScreen({ bird, onBack, onToggleFav }) {
  if (!bird) return null;

  return (
    <div className="paper" style={{ minHeight: '100%', paddingBottom: 100, position: 'relative' }}>
      {/* Foto cabecera */}
      <div style={{ position: 'relative', width: '100%', height: 380, overflow: 'hidden', background: 'var(--bark-200)' }}>
        <img src={bird.photo} alt={bird.species || 'sin identificar'} style={{ width: '100%', height: '100%', objectFit: 'cover' }} />
        <div style={{ position: 'absolute', inset: 0, background: 'linear-gradient(180deg, rgba(31,27,20,0.4) 0%, transparent 25%, transparent 70%, rgba(250,246,238,0.95) 100%)' }} />

        {/* Top bar */}
        <div style={{ position: 'absolute', top: 12, left: 16, right: 16, display: 'flex', justifyContent: 'space-between' }}>
          <button onClick={onBack} style={{ width: 38, height: 38, borderRadius: '50%', border: 'none', background: 'rgba(251,249,243,0.85)', backdropFilter: 'blur(10px)', display: 'flex', alignItems: 'center', justifyContent: 'center', cursor: 'pointer', color: 'var(--ink)' }}>
            <Icon name="chevron-left" size={20} />
          </button>
          <div style={{ display: 'flex', gap: 8 }}>
            <button onClick={onToggleFav} style={{ width: 38, height: 38, borderRadius: '50%', border: 'none', background: 'rgba(251,249,243,0.85)', backdropFilter: 'blur(10px)', display: 'flex', alignItems: 'center', justifyContent: 'center', cursor: 'pointer', color: bird.fav ? 'var(--accent-red)' : 'var(--ink)' }}>
              <Icon name={bird.fav ? 'star-fill' : 'star'} size={18} />
            </button>
            <button style={{ width: 38, height: 38, borderRadius: '50%', border: 'none', background: 'rgba(251,249,243,0.85)', backdropFilter: 'blur(10px)', display: 'flex', alignItems: 'center', justifyContent: 'center', cursor: 'pointer', color: 'var(--ink)' }}>
              <Icon name="share" size={16} />
            </button>
          </div>
        </div>

        {/* Sello de bitácora */}
        <div style={{ position: 'absolute', top: 70, right: 18 }}>
          <Stamp color={bird.species ? 'var(--moss-700)' : 'var(--accent-red)'} rotate={-10} size={62}>
            {bird.species ? 'Registrado' : 'Pendiente'}<br/>{bird.dateShort}
          </Stamp>
        </div>
      </div>

      {/* Cuerpo ficha */}
      <div style={{ padding: '0 24px', marginTop: -20, position: 'relative', zIndex: 2 }}>
        {/* Título */}
        <div style={{ marginBottom: 4 }}>
          <div style={{ fontFamily: 'Inter', fontSize: 10.5, fontWeight: 600, letterSpacing: '0.16em', textTransform: 'uppercase', color: 'var(--moss-600)' }}>
            Observación nº {String(bird.id).padStart(3, '0')}
          </div>
        </div>
        <div className="serif" style={{ fontSize: 30, lineHeight: 1.05, fontWeight: 500, color: 'var(--ink)', letterSpacing: '-0.02em', fontStyle: bird.species ? 'normal' : 'italic' }}>
          {bird.species || 'Especie sin identificar'}
        </div>
        {bird.sci && (
          <div className="serif" style={{ fontSize: 14, fontStyle: 'italic', color: 'var(--ink-mute)', marginTop: 2 }}>
            {bird.sci}
          </div>
        )}

        <div style={{ height: 18 }} />

        {/* Datos en grid 2 col */}
        <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: 14, marginBottom: 20 }}>
          <FieldBlock icon="calendar" label="Fecha" value={bird.date} />
          <FieldBlock icon="pin" label="Lugar" value={bird.location} />
        </div>

        {/* Comportamiento */}
        <FieldLabel>Comportamiento</FieldLabel>
        <div className="serif" style={{ fontSize: 16, lineHeight: 1.5, color: 'var(--ink)', marginTop: 6, fontStyle: 'italic' }}>
          “{bird.behavior}”
        </div>

        <div style={{ height: 20 }} />
        <LeafDivider />
        <div style={{ height: 18 }} />

        {/* Notas — estilo manuscrito */}
        <FieldLabel>Notas de campo</FieldLabel>
        <div style={{
          marginTop: 10,
          padding: '14px 16px',
          background: 'rgba(255,255,255,0.55)',
          border: '1px solid var(--line-soft)',
          borderRadius: 8,
          backgroundImage: 'repeating-linear-gradient(transparent, transparent 23px, rgba(122,90,58,0.12) 23px, rgba(122,90,58,0.12) 24px)',
        }}>
          <div className="hand" style={{ fontSize: 19, lineHeight: '24px', color: 'var(--bark-700)' }}>
            {bird.notes}
          </div>
        </div>

        <div style={{ height: 20 }} />

        {/* Etiquetas */}
        <FieldLabel>Etiquetas</FieldLabel>
        <div style={{ marginTop: 8, display: 'flex', flexWrap: 'wrap', gap: 6 }}>
          {bird.tags.map(t => (
            <span key={t} style={{ fontFamily: 'Inter', fontSize: 12, padding: '5px 10px', borderRadius: 999, background: 'var(--moss-100)', color: 'var(--moss-700)', fontWeight: 500 }}>
              #{t}
            </span>
          ))}
          <button style={{ fontFamily: 'Inter', fontSize: 12, padding: '5px 10px', borderRadius: 999, background: 'transparent', color: 'var(--ink-mute)', fontWeight: 500, border: '1px dashed var(--line)', cursor: 'pointer' }}>
            + añadir
          </button>
        </div>

        <div style={{ height: 28 }} />

        {/* Acciones */}
        <div style={{ display: 'flex', gap: 10 }}>
          <button style={{ flex: 1, padding: '12px', borderRadius: 12, border: '1px solid var(--line)', background: 'rgba(255,255,255,0.6)', color: 'var(--ink)', fontFamily: 'Inter', fontSize: 14, fontWeight: 500, cursor: 'pointer' }}>
            Editar ficha
          </button>
          <button style={{ flex: 1, padding: '12px', borderRadius: 12, border: 'none', background: 'var(--moss-700)', color: '#FBF9F3', fontFamily: 'Inter', fontSize: 14, fontWeight: 500, cursor: 'pointer' }}>
            Ver en mapa
          </button>
        </div>
      </div>
    </div>
  );
}

function FieldBlock({ icon, label, value }) {
  return (
    <div>
      <div style={{ display: 'flex', alignItems: 'center', gap: 5, color: 'var(--moss-600)' }}>
        <Icon name={icon} size={12} />
        <FieldLabel>{label}</FieldLabel>
      </div>
      <div className="serif" style={{ fontSize: 14, color: 'var(--ink)', marginTop: 4, lineHeight: 1.3 }}>
        {value}
      </div>
    </div>
  );
}

Object.assign(window, { DetailScreen });
