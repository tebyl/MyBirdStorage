// screens-profile.jsx — Biblioteca / Perfil

function ProfileScreen() {
  const totalPhotos = BIRDS.length;
  const lugares = [...new Set(BIRDS.map(b => b.locShort))];

  return (
    <div className="paper" style={{ minHeight: '100%', paddingBottom: 100 }}>
      {/* Header */}
      <div style={{ padding: '8px 24px 18px' }}>
        <div style={{ fontFamily: 'Inter', fontSize: 11, fontWeight: 600, letterSpacing: '0.16em', textTransform: 'uppercase', color: 'var(--moss-600)', marginBottom: 4 }}>
          Mi biblioteca
        </div>
        <div className="serif" style={{ fontSize: 32, fontWeight: 400, color: 'var(--ink)', letterSpacing: '-0.02em' }}>
          Cuaderno de<br/>
          <span style={{ fontStyle: 'italic', color: 'var(--moss-700)' }}>campo</span>
        </div>
      </div>

      {/* Tarjeta tipo bitácora — identidad */}
      <div style={{ margin: '0 20px 22px', padding: '20px 22px', background: '#FFFCF5', borderRadius: 14, border: '1px solid var(--line)', boxShadow: '0 1px 6px rgba(74,58,40,0.06)', position: 'relative' }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: 14 }}>
          <div style={{ width: 56, height: 56, borderRadius: '50%', background: 'var(--moss-100)', display: 'flex', alignItems: 'center', justifyContent: 'center', color: 'var(--moss-700)', border: '1.5px solid var(--moss-300)' }}>
            <Icon name="feather" size={26} stroke={1.5}/>
          </div>
          <div>
            <div className="serif" style={{ fontSize: 20, color: 'var(--ink)' }}>Bitácora personal</div>
            <div style={{ fontFamily: 'Inter', fontSize: 12, color: 'var(--ink-mute)', marginTop: 2 }}>iniciada el 15 octubre 2025</div>
          </div>
        </div>

        <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr 1fr', gap: 10, marginTop: 18, paddingTop: 16, borderTop: '1px dashed var(--line)' }}>
          <Stat n={totalPhotos} label="fotos"/>
          <Stat n={SPECIES.length} label="especies"/>
          <Stat n={lugares.length} label="lugares"/>
        </div>
      </div>

      {/* Acciones / configuraciones */}
      <div style={{ padding: '0 20px' }}>
        <FieldLabel>Tu archivo</FieldLabel>
        <div style={{ marginTop: 10, background: 'rgba(255,255,255,0.55)', borderRadius: 12, border: '1px solid var(--line-soft)', overflow: 'hidden' }}>
          <Row icon="leaf" label="Especies registradas" detail={`${SPECIES.length}`}/>
          <Row icon="pin" label="Lugares" detail={`${lugares.length}`}/>
          <Row icon="tag" label="Etiquetas" detail={`${ALL_TAGS.length}`}/>
          <Row icon="star" label="Favoritas" detail={`${BIRDS.filter(b => b.fav).length}`} last/>
        </div>

        <div style={{ height: 18 }}/>

        <FieldLabel>Bitácora</FieldLabel>
        <div style={{ marginTop: 10, background: 'rgba(255,255,255,0.55)', borderRadius: 12, border: '1px solid var(--line-soft)', overflow: 'hidden' }}>
          <Row icon="share" label="Exportar archivo (.zip)" detail="local"/>
          <Row icon="note" label="Importar bitácora" detail=""/>
          <Row icon="menu" label="Apariencia" detail="Cálida" last/>
        </div>

        <div style={{ height: 18 }}/>

        <FieldLabel>Sobre Aviario Local</FieldLabel>
        <div style={{ marginTop: 10, padding: '14px 16px', background: 'rgba(255,255,255,0.4)', borderRadius: 12, border: '1px solid var(--line-soft)' }}>
          <div className="serif" style={{ fontSize: 14, fontStyle: 'italic', lineHeight: 1.55, color: 'var(--ink-soft)' }}>
            Sin cuenta, sin nube, sin algoritmos.
            Tus aves viven en este aparato,
            como en un cuaderno de tela.
          </div>
          <div style={{ marginTop: 10, fontFamily: 'Inter', fontSize: 11, color: 'var(--ink-mute)' }}>
            v0.4 · primavera 2026
          </div>
        </div>
      </div>
    </div>
  );
}

function Stat({ n, label }) {
  return (
    <div style={{ textAlign: 'center' }}>
      <div className="serif" style={{ fontSize: 26, fontWeight: 500, color: 'var(--moss-700)', lineHeight: 1 }}>{n}</div>
      <div style={{ fontFamily: 'Inter', fontSize: 10.5, color: 'var(--ink-mute)', marginTop: 4, textTransform: 'uppercase', letterSpacing: '0.1em' }}>{label}</div>
    </div>
  );
}

function Row({ icon, label, detail, last }) {
  return (
    <div style={{ display: 'flex', alignItems: 'center', gap: 12, padding: '13px 16px', borderBottom: last ? 'none' : '1px solid var(--line-soft)', cursor: 'pointer' }}>
      <Icon name={icon} size={16} color="var(--moss-600)"/>
      <div style={{ flex: 1, fontFamily: 'Inter', fontSize: 14, color: 'var(--ink)' }}>{label}</div>
      <div style={{ fontFamily: 'Inter', fontSize: 12.5, color: 'var(--ink-mute)' }}>{detail}</div>
      <Icon name="chevron-right" size={14} color="var(--ink-mute)"/>
    </div>
  );
}

Object.assign(window, { ProfileScreen });
