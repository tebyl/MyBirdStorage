// screens-add.jsx — Agregar observación (formulario limpio, opcional)

function AddScreen({ onClose }) {
  const [photo, setPhoto] = React.useState(null);
  const [species, setSpecies] = React.useState('');
  const [location, setLocation] = React.useState('');
  const [behavior, setBehavior] = React.useState('');
  const [notes, setNotes] = React.useState('');
  const [tags, setTags] = React.useState([]);
  const [tagInput, setTagInput] = React.useState('');

  const samplePhotos = [
    'https://images.unsplash.com/photo-1452570053594-1b985d6ea890?w=400&q=80',
    'https://images.unsplash.com/photo-1480044965905-02098d419e96?w=400&q=80',
    'https://images.unsplash.com/photo-1551634979-2b11f8c218da?w=400&q=80',
  ];

  return (
    <div className="paper" style={{ minHeight: '100%', paddingBottom: 100 }}>
      {/* Header */}
      <div style={{ padding: '8px 20px 14px', display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
        <button onClick={onClose} style={{ width: 36, height: 36, borderRadius: '50%', border: '1px solid var(--line)', background: 'rgba(255,255,255,0.6)', display: 'flex', alignItems: 'center', justifyContent: 'center', cursor: 'pointer', color: 'var(--ink)' }}>
          <Icon name="x" size={16}/>
        </button>
        <div className="serif" style={{ fontSize: 17, color: 'var(--ink)' }}>Nueva observación</div>
        <div style={{ width: 36 }}/>
      </div>

      {/* Subtítulo */}
      <div style={{ padding: '0 24px 16px', textAlign: 'center' }}>
        <div className="serif" style={{ fontSize: 14, fontStyle: 'italic', color: 'var(--ink-soft)', lineHeight: 1.5 }}>
          Completa solo lo que sepas.<br/>
          Ningún campo es obligatorio.
        </div>
      </div>

      {/* Foto */}
      <div style={{ padding: '0 20px 18px' }}>
        {photo ? (
          <div style={{ position: 'relative', borderRadius: 12, overflow: 'hidden', height: 220 }}>
            <img src={photo} style={{ width: '100%', height: '100%', objectFit: 'cover' }}/>
            <button onClick={() => setPhoto(null)} style={{ position: 'absolute', top: 10, right: 10, width: 32, height: 32, borderRadius: '50%', border: 'none', background: 'rgba(31,27,20,0.7)', color: '#FBF9F3', display: 'flex', alignItems: 'center', justifyContent: 'center', cursor: 'pointer' }}>
              <Icon name="x" size={14}/>
            </button>
          </div>
        ) : (
          <div>
            <div style={{
              border: '1.5px dashed var(--moss-300)',
              borderRadius: 12,
              padding: '30px 20px',
              textAlign: 'center',
              background: 'rgba(255,255,255,0.4)',
            }}>
              <div style={{ width: 52, height: 52, borderRadius: '50%', background: 'var(--moss-100)', display: 'flex', alignItems: 'center', justifyContent: 'center', margin: '0 auto 10px', color: 'var(--moss-700)' }}>
                <Icon name="camera" size={22}/>
              </div>
              <div className="serif" style={{ fontSize: 15, color: 'var(--ink)', marginBottom: 4 }}>
                Añade una foto
              </div>
              <div style={{ fontFamily: 'Inter', fontSize: 12, color: 'var(--ink-mute)' }}>
                desde tu galería o cámara
              </div>
            </div>
            <div style={{ marginTop: 10, display: 'flex', gap: 6 }}>
              {samplePhotos.map(p => (
                <button key={p} onClick={() => setPhoto(p)} style={{ flex: 1, padding: 0, border: 'none', borderRadius: 8, overflow: 'hidden', cursor: 'pointer', height: 60 }}>
                  <img src={p} style={{ width: '100%', height: '100%', objectFit: 'cover' }}/>
                </button>
              ))}
            </div>
            <div style={{ fontFamily: 'Inter', fontSize: 10.5, color: 'var(--ink-mute)', marginTop: 6, textAlign: 'center', fontStyle: 'italic' }}>
              ↑ ejemplo: toca una foto para simular
            </div>
          </div>
        )}
      </div>

      {/* Form */}
      <div style={{ padding: '0 20px', display: 'flex', flexDirection: 'column', gap: 18 }}>
        <Field label="Especie" optional>
          <input value={species} onChange={e => setSpecies(e.target.value)} placeholder="¿Sabes cuál es?"
            style={inputStyle}/>
        </Field>

        <Field label="Lugar" optional icon="pin">
          <input value={location} onChange={e => setLocation(e.target.value)} placeholder="Bosque, jardín, sendero…"
            style={inputStyle}/>
        </Field>

        <Field label="Fecha" optional icon="calendar">
          <div style={{ ...inputStyle, color: 'var(--ink)' }}>
            Hoy · 30 abr 2026
          </div>
        </Field>

        <Field label="Comportamiento observado" optional>
          <input value={behavior} onChange={e => setBehavior(e.target.value)} placeholder="¿Qué hacía?"
            style={inputStyle}/>
        </Field>

        <Field label="Notas libres" optional icon="note">
          <textarea value={notes} onChange={e => setNotes(e.target.value)} placeholder="Lo que recuerdes, como en un cuaderno…"
            rows={3}
            style={{ ...inputStyle, fontFamily: 'Caveat, cursive', fontSize: 17, lineHeight: '24px', resize: 'none',
              backgroundImage: 'repeating-linear-gradient(transparent, transparent 23px, rgba(122,90,58,0.12) 23px, rgba(122,90,58,0.12) 24px)' }}/>
        </Field>

        <Field label="Etiquetas" optional icon="tag">
          <div style={{ ...inputStyle, padding: '8px 12px', display: 'flex', flexWrap: 'wrap', gap: 5, alignItems: 'center', minHeight: 42 }}>
            {tags.map(t => (
              <span key={t} style={{ fontFamily: 'Inter', fontSize: 12, padding: '4px 9px', borderRadius: 999, background: 'var(--moss-100)', color: 'var(--moss-700)', display: 'flex', alignItems: 'center', gap: 4 }}>
                #{t}
                <span onClick={() => setTags(tags.filter(x => x !== t))} style={{ cursor: 'pointer', opacity: 0.6 }}>×</span>
              </span>
            ))}
            <input value={tagInput} onChange={e => setTagInput(e.target.value)}
              onKeyDown={e => { if (e.key === 'Enter' && tagInput.trim()) { setTags([...tags, tagInput.trim()]); setTagInput(''); }}}
              placeholder={tags.length === 0 ? '#canto, #amanecer…' : ''}
              style={{ flex: 1, minWidth: 80, border: 'none', background: 'transparent', outline: 'none', fontFamily: 'Inter', fontSize: 13, color: 'var(--ink)' }}/>
          </div>
        </Field>
      </div>

      {/* CTA */}
      <div style={{ padding: '24px 20px 0' }}>
        <button onClick={onClose} style={{ width: '100%', padding: '15px', borderRadius: 14, border: 'none', background: 'var(--moss-700)', color: '#FBF9F3', fontFamily: 'Inter', fontSize: 15, fontWeight: 600, cursor: 'pointer', boxShadow: '0 4px 14px rgba(62,74,46,0.25)' }}>
          Guardar observación
        </button>
        <div style={{ marginTop: 10, textAlign: 'center', fontFamily: 'Inter', fontSize: 11.5, color: 'var(--ink-mute)' }}>
          se guarda solo en este dispositivo
        </div>
      </div>
    </div>
  );
}

const inputStyle = {
  width: '100%',
  padding: '11px 13px',
  borderRadius: 10,
  border: '1px solid var(--line)',
  background: 'rgba(255,255,255,0.7)',
  fontFamily: 'Inter, sans-serif',
  fontSize: 14,
  color: 'var(--ink)',
  outline: 'none',
  boxSizing: 'border-box',
};

function Field({ label, optional, icon, children }) {
  return (
    <div>
      <div style={{ display: 'flex', alignItems: 'center', gap: 6, marginBottom: 6 }}>
        {icon && <Icon name={icon} size={11} color="var(--moss-600)"/>}
        <FieldLabel>{label}</FieldLabel>
        {optional && <span style={{ fontFamily: 'Inter', fontSize: 9.5, fontStyle: 'italic', color: 'var(--ink-mute)', textTransform: 'lowercase', letterSpacing: 0 }}>opcional</span>}
      </div>
      {children}
    </div>
  );
}

Object.assign(window, { AddScreen });
