// screens-stats.jsx — Estadísticas estilo bitácora (números en prosa)

function StatsScreen() {
  const total = BIRDS.length;
  const speciesCount = SPECIES.length;
  const unidCount = BIRDS.filter(b => !b.species).length;
  const favCount = BIRDS.filter(b => b.fav).length;
  const maxMonth = Math.max(...MONTHLY.map(m => m.count));

  return (
    <div className="paper" style={{ minHeight: '100%', paddingBottom: 100 }}>
      {/* Header */}
      <div style={{ padding: '8px 24px 12px' }}>
        <div style={{ fontFamily: 'Inter', fontSize: 11, fontWeight: 600, letterSpacing: '0.16em', textTransform: 'uppercase', color: 'var(--moss-600)', marginBottom: 4 }}>
          Resumen de campo
        </div>
        <div className="serif" style={{ fontSize: 32, fontWeight: 400, color: 'var(--ink)', letterSpacing: '-0.02em' }}>
          La temporada<br/>
          <span style={{ fontStyle: 'italic', color: 'var(--moss-700)' }}>en números</span>
        </div>
      </div>

      {/* Bloque narrativo principal */}
      <div style={{ margin: '0 20px 20px', padding: '22px 22px', background: '#FFFCF5', borderRadius: 14, border: '1px solid var(--line)', boxShadow: '0 1px 6px rgba(74,58,40,0.06)', position: 'relative' }}>
        <div style={{ position: 'absolute', top: 14, right: 14 }}>
          <Stamp color="var(--moss-700)" rotate={6} size={50}>
            primavera<br/>2026
          </Stamp>
        </div>

        <FieldLabel>Hasta hoy</FieldLabel>
        <div className="serif" style={{ fontSize: 19, lineHeight: 1.55, color: 'var(--ink)', marginTop: 10, paddingRight: 50 }}>
          Has guardado <NumWord n={total} /> observaciones,
          de <NumWord n={speciesCount} /> especies distintas.
          De ellas, <NumWord n={unidCount} /> aún esperan
          <span style={{ fontStyle: 'italic', color: 'var(--moss-700)' }}> identificación</span>, y <NumWord n={favCount} /> guardas como favoritas.
        </div>

        <div style={{ height: 14 }} />
        <LeafDivider />
        <div style={{ height: 14 }} />

        <FieldLabel>Lo más visto</FieldLabel>
        <div className="serif" style={{ fontSize: 18, lineHeight: 1.5, color: 'var(--ink)', marginTop: 8 }}>
          La especie que más se asoma a tu bitácora es el{' '}
          <span style={{ fontStyle: 'italic', fontWeight: 500, color: 'var(--moss-700)' }}>{TOP_SPECIES.toLowerCase()}</span>,
          visto en varias jornadas distintas.
        </div>
      </div>

      {/* Avistamientos por mes — visual minimalista */}
      <div style={{ margin: '0 20px 20px' }}>
        <FieldLabel>Avistamientos por mes</FieldLabel>
        <div style={{ marginTop: 12, padding: '20px 18px 14px', background: 'rgba(255,255,255,0.55)', borderRadius: 12, border: '1px solid var(--line-soft)' }}>
          <div style={{ display: 'flex', alignItems: 'flex-end', justifyContent: 'space-between', gap: 8, height: 110 }}>
            {MONTHLY.map(m => {
              const h = (m.count / maxMonth) * 90;
              const isMax = m.count === maxMonth;
              return (
                <div key={m.month} style={{ flex: 1, display: 'flex', flexDirection: 'column', alignItems: 'center', gap: 6 }}>
                  <div style={{ fontFamily: 'Fraunces', fontSize: 12, color: isMax ? 'var(--moss-700)' : 'var(--ink-mute)', fontWeight: isMax ? 600 : 400 }}>
                    {m.count}
                  </div>
                  <div style={{
                    width: '100%', height: h, borderRadius: 4,
                    background: isMax ? 'var(--moss-700)' : 'var(--moss-300)',
                    opacity: isMax ? 1 : 0.7,
                  }}/>
                </div>
              );
            })}
          </div>
          <div style={{ display: 'flex', justifyContent: 'space-between', marginTop: 8, fontFamily: 'Inter', fontSize: 10.5, color: 'var(--ink-mute)', textTransform: 'uppercase', letterSpacing: '0.1em' }}>
            {MONTHLY.map(m => <div key={m.month} style={{ flex: 1, textAlign: 'center' }}>{m.month}</div>)}
          </div>
        </div>
        <div className="serif" style={{ marginTop: 10, fontSize: 13, fontStyle: 'italic', color: 'var(--ink-soft)', textAlign: 'center' }}>
          marzo fue tu mes más activo — nueve salidas con cuaderno.
        </div>
      </div>

      {/* Etiquetas más usadas */}
      <div style={{ margin: '0 20px 20px' }}>
        <FieldLabel>Etiquetas más usadas</FieldLabel>
        <div style={{ marginTop: 12, display: 'flex', flexWrap: 'wrap', gap: 6 }}>
          {TAG_COUNTS.slice(0, 8).map(([tag, count], i) => {
            const sizes = [16, 14.5, 14, 13, 12.5, 12, 11.5, 11];
            return (
              <span key={tag} style={{
                fontFamily: 'Fraunces', fontSize: sizes[i] || 11, fontWeight: i < 2 ? 500 : 400,
                color: i < 2 ? 'var(--moss-700)' : 'var(--ink-soft)',
                fontStyle: i % 2 === 1 ? 'italic' : 'normal',
              }}>
                #{tag}
                <span style={{ fontFamily: 'Inter', fontSize: 10, color: 'var(--ink-mute)', marginLeft: 2 }}>·{count}</span>
              </span>
            );
          })}
        </div>
      </div>

      {/* Cierre */}
      <div style={{ padding: '10px 32px 0', textAlign: 'center' }}>
        <LeafDivider />
        <div className="hand" style={{ marginTop: 12, fontSize: 17, color: 'var(--moss-700)', opacity: 0.75, lineHeight: 1.3 }}>
          “sigue mirando despacio.<br/>la primavera apenas empieza.”
        </div>
      </div>
    </div>
  );
}

// Convierte número en palabra para los más comunes (estilo prosa)
function NumWord({ n }) {
  const words = ['cero', 'una', 'dos', 'tres', 'cuatro', 'cinco', 'seis', 'siete', 'ocho', 'nueve', 'diez', 'once', 'doce'];
  return <span style={{ fontWeight: 600, color: 'var(--moss-700)' }}>{words[n] || n}</span>;
}

Object.assign(window, { StatsScreen });
