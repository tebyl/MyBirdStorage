// screens-map.jsx — Mapa de avistamientos (placeholder con puntos + cards)

function MapScreen({ onOpenBird }) {
  const [selected, setSelected] = React.useState(BIRDS[0].id);
  const sel = BIRDS.find(b => b.id === selected);

  return (
    <div style={{ minHeight: '100%', position: 'relative', background: '#E8E0CB', paddingBottom: 100 }}>
      {/* Mapa simulado (estilo topográfico/papel viejo) */}
      <div style={{ position: 'relative', width: '100%', height: 480, overflow: 'hidden' }}>
        {/* Fondo cálido */}
        <div style={{
          position: 'absolute', inset: 0,
          background: `
            radial-gradient(circle at 25% 35%, #D4DCC0 0 18%, transparent 19%),
            radial-gradient(circle at 70% 25%, #C9D1B0 0 15%, transparent 16%),
            radial-gradient(circle at 50% 70%, #DFE5C8 0 22%, transparent 23%),
            radial-gradient(circle at 15% 80%, #C5CDA8 0 14%, transparent 15%),
            radial-gradient(circle at 85% 75%, #D8E0C2 0 16%, transparent 17%),
            #E8E0CB
          `,
        }} />
        {/* Curvas de nivel SVG */}
        <svg style={{ position: 'absolute', inset: 0, width: '100%', height: '100%', opacity: 0.35 }} viewBox="0 0 400 480" preserveAspectRatio="none">
          <g fill="none" stroke="#7A5A3A" strokeWidth="0.6">
            <path d="M0,80 Q100,60 200,90 T400,75" />
            <path d="M0,140 Q120,120 240,150 T400,135" />
            <path d="M0,210 Q140,190 280,220 T400,210" />
            <path d="M0,280 Q100,260 200,290 T400,275" />
            <path d="M0,350 Q150,330 300,360 T400,345" />
            <path d="M0,420 Q120,400 250,430 T400,415" />
          </g>
          <g fill="none" stroke="#5C6B47" strokeWidth="0.5" opacity="0.5">
            <path d="M50,0 Q70,140 60,280 T80,480" />
            <path d="M180,0 Q200,140 190,280 T210,480" />
            <path d="M320,0 Q340,140 330,280 T350,480" />
          </g>
        </svg>

        {/* Río */}
        <svg style={{ position: 'absolute', inset: 0, width: '100%', height: '100%' }} viewBox="0 0 400 480" preserveAspectRatio="none">
          <path d="M-20,200 Q80,180 130,260 T280,340 T420,360" fill="none" stroke="#A8B8C9" strokeWidth="6" opacity="0.8" />
          <path d="M-20,200 Q80,180 130,260 T280,340 T420,360" fill="none" stroke="#7A8FA0" strokeWidth="1" opacity="0.5" />
        </svg>

        {/* Puntos de avistamiento */}
        {BIRDS.map(b => {
          const isSel = b.id === selected;
          return (
            <button key={b.id} onClick={() => setSelected(b.id)} style={{
              position: 'absolute',
              left: `${b.coords.x}%`, top: `${b.coords.y}%`,
              transform: `translate(-50%, -100%) scale(${isSel ? 1.15 : 1})`,
              width: 30, height: 38,
              border: 'none', background: 'transparent', cursor: 'pointer',
              padding: 0,
              filter: isSel ? 'drop-shadow(0 4px 6px rgba(0,0,0,0.25))' : 'drop-shadow(0 2px 3px rgba(0,0,0,0.2))',
              transition: 'transform 0.2s ease',
              zIndex: isSel ? 10 : 1,
            }}>
              <svg width="30" height="38" viewBox="0 0 30 38">
                <path d="M15 2 C7 2 2 7 2 14 C2 22 15 36 15 36 C15 36 28 22 28 14 C28 7 23 2 15 2Z"
                  fill={isSel ? '#3F4A2E' : (b.fav ? '#B5462E' : '#5C6B47')}
                  stroke="#FBF9F3" strokeWidth="1.5"/>
                <circle cx="15" cy="14" r="4" fill="#FBF9F3"/>
              </svg>
            </button>
          );
        })}

        {/* Brújula decorativa */}
        <div style={{ position: 'absolute', top: 12, right: 12, width: 44, height: 44, borderRadius: '50%', background: 'rgba(251,249,243,0.85)', backdropFilter: 'blur(8px)', display: 'flex', alignItems: 'center', justifyContent: 'center', boxShadow: '0 1px 4px rgba(74,58,40,0.15)' }}>
          <svg width="26" height="26" viewBox="0 0 24 24">
            <circle cx="12" cy="12" r="10" fill="none" stroke="#5C6B47" strokeWidth="0.8"/>
            <path d="M12 4 L14 12 L12 20 L10 12 Z" fill="#B5462E"/>
            <path d="M12 4 L14 12 L12 20 L10 12 Z" transform="rotate(180 12 12)" fill="#3F4A2E"/>
            <text x="12" y="3.5" fontSize="3" textAnchor="middle" fill="#3F4A2E" fontFamily="Fraunces">N</text>
          </svg>
        </div>

        {/* Header overlay */}
        <div style={{ position: 'absolute', top: 12, left: 16, padding: '8px 12px', borderRadius: 10, background: 'rgba(251,249,243,0.85)', backdropFilter: 'blur(8px)', boxShadow: '0 1px 4px rgba(74,58,40,0.1)' }}>
          <div style={{ fontFamily: 'Inter', fontSize: 9.5, fontWeight: 600, letterSpacing: '0.14em', textTransform: 'uppercase', color: 'var(--moss-600)' }}>Mapa de campo</div>
          <div className="serif" style={{ fontSize: 16, color: 'var(--ink)', marginTop: 1 }}>{BIRDS.length} avistamientos</div>
        </div>

        {/* Indicador placeholder */}
        <div style={{ position: 'absolute', bottom: 12, left: 16, fontFamily: 'Inter', fontSize: 9.5, color: 'var(--ink-mute)', fontStyle: 'italic', background: 'rgba(251,249,243,0.7)', padding: '3px 8px', borderRadius: 4 }}>
          Mapa simulado · pronto: mapa real
        </div>
      </div>

      {/* Cards inferiores — observaciones cercanas */}
      <div style={{ padding: '20px 20px 0' }}>
        <div style={{ display: 'flex', alignItems: 'baseline', justifyContent: 'space-between', marginBottom: 12 }}>
          <div className="serif" style={{ fontSize: 19, color: 'var(--ink)' }}>
            Cerca de aquí
          </div>
          <div style={{ fontFamily: 'Inter', fontSize: 11, color: 'var(--ink-mute)' }}>
            radio 2 km
          </div>
        </div>

        {/* Card seleccionada destacada */}
        {sel && (
          <div onClick={() => onOpenBird(sel)} style={{
            display: 'flex', gap: 12, padding: 12, borderRadius: 14,
            background: '#FFFCF5', border: '1px solid var(--line)',
            boxShadow: '0 2px 10px rgba(74,58,40,0.08)',
            marginBottom: 12, cursor: 'pointer',
          }}>
            <img src={sel.photo} style={{ width: 68, height: 68, objectFit: 'cover', borderRadius: 8, flexShrink: 0 }}/>
            <div style={{ flex: 1, minWidth: 0 }}>
              <div className="serif" style={{ fontSize: 16, fontWeight: 500, color: 'var(--ink)', fontStyle: sel.species ? 'normal' : 'italic' }}>
                {sel.species || 'Sin identificar'}
              </div>
              <div style={{ display: 'flex', alignItems: 'center', gap: 4, fontFamily: 'Inter', fontSize: 11.5, color: 'var(--ink-mute)', marginTop: 3 }}>
                <Icon name="pin" size={11}/>
                <span style={{ overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>{sel.location}</span>
              </div>
              <div className="serif" style={{ fontSize: 12.5, fontStyle: 'italic', color: 'var(--ink-soft)', marginTop: 4, lineHeight: 1.35, display: '-webkit-box', WebkitLineClamp: 2, WebkitBoxOrient: 'vertical', overflow: 'hidden' }}>
                {sel.behavior}
              </div>
            </div>
          </div>
        )}

        {/* Mini lista de otros */}
        <div style={{ display: 'flex', flexDirection: 'column', gap: 8 }}>
          {BIRDS.filter(b => b.id !== selected).slice(0, 3).map(b => (
            <div key={b.id} onClick={() => setSelected(b.id)} style={{
              display: 'flex', gap: 10, alignItems: 'center', padding: '8px 10px', borderRadius: 10,
              background: 'rgba(255,255,255,0.55)', border: '1px solid var(--line-soft)', cursor: 'pointer',
            }}>
              <img src={b.photo} style={{ width: 40, height: 40, objectFit: 'cover', borderRadius: 6 }}/>
              <div style={{ flex: 1, minWidth: 0 }}>
                <div className="serif" style={{ fontSize: 13.5, color: 'var(--ink)', fontStyle: b.species ? 'normal' : 'italic' }}>
                  {b.species || 'Sin identificar'}
                </div>
                <div style={{ fontFamily: 'Inter', fontSize: 10.5, color: 'var(--ink-mute)' }}>
                  {b.locShort} · {b.dateShort}
                </div>
              </div>
              <Icon name="chevron-right" size={14} color="var(--ink-mute)"/>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

Object.assign(window, { MapScreen });
