// app.jsx — App principal con bottom nav y enrutado entre pantallas

function BottomNav({ active, onChange, onAdd }) {
  const items = [
    { id: 'gallery', label: 'Galería', icon: 'gallery' },
    { id: 'map', label: 'Mapa', icon: 'map' },
    { id: 'add', label: 'Añadir', icon: 'plus', center: true },
    { id: 'stats', label: 'Estadísticas', icon: 'stats' },
    { id: 'profile', label: 'Biblioteca', icon: 'profile' },
  ];

  return (
    <div style={{
      position: 'absolute', bottom: 0, left: 0, right: 0,
      paddingBottom: 24, paddingTop: 6,
      background: 'linear-gradient(180deg, rgba(250,246,238,0) 0%, rgba(250,246,238,0.95) 30%, rgba(250,246,238,1) 100%)',
      zIndex: 50,
    }}>
      <div style={{
        margin: '0 14px',
        background: 'rgba(255,252,245,0.92)',
        backdropFilter: 'blur(14px) saturate(180%)',
        WebkitBackdropFilter: 'blur(14px) saturate(180%)',
        border: '1px solid var(--line)',
        borderRadius: 22,
        padding: '8px 6px',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
        boxShadow: '0 4px 20px rgba(74,58,40,0.1)',
      }}>
        {items.map(it => {
          const isActive = active === it.id;
          if (it.center) {
            return (
              <button key={it.id} onClick={onAdd} style={{
                width: 46, height: 46, borderRadius: 14,
                border: 'none', background: 'var(--moss-700)',
                color: '#FBF9F3', display: 'flex', alignItems: 'center', justifyContent: 'center',
                cursor: 'pointer', boxShadow: '0 3px 10px rgba(62,74,46,0.3)',
                margin: '0 4px',
              }}>
                <Icon name={it.icon} size={22} stroke={2}/>
              </button>
            );
          }
          return (
            <button key={it.id} onClick={() => onChange(it.id)} style={{
              flex: 1, padding: '6px 4px',
              border: 'none', background: 'transparent',
              display: 'flex', flexDirection: 'column', alignItems: 'center', gap: 2,
              cursor: 'pointer',
              color: isActive ? 'var(--moss-700)' : 'var(--ink-mute)',
            }}>
              <Icon name={it.icon} size={19} stroke={isActive ? 2 : 1.6}/>
              <span style={{ fontFamily: 'Inter', fontSize: 9.5, fontWeight: isActive ? 600 : 500, letterSpacing: '0.02em' }}>
                {it.label}
              </span>
            </button>
          );
        })}
      </div>
    </div>
  );
}

function App() {
  const [tab, setTab] = React.useState('gallery');
  const [openBird, setOpenBird] = React.useState(null);
  const [showAdd, setShowAdd] = React.useState(false);
  const [favsOverride, setFavsOverride] = React.useState({});

  // permite togglear favoritas en sesión
  const enrichBird = (b) => b ? { ...b, fav: favsOverride[b.id] != null ? favsOverride[b.id] : b.fav } : null;
  const toggleFav = () => {
    if (!openBird) return;
    setFavsOverride({ ...favsOverride, [openBird.id]: !enrichBird(openBird).fav });
  };

  let screen;
  if (showAdd) {
    screen = <AddScreen onClose={() => setShowAdd(false)} />;
  } else if (openBird) {
    screen = <DetailScreen bird={enrichBird(openBird)} onBack={() => setOpenBird(null)} onToggleFav={toggleFav} />;
  } else if (tab === 'gallery') {
    screen = <GalleryScreen onOpenBird={setOpenBird} />;
  } else if (tab === 'map') {
    screen = <MapScreen onOpenBird={setOpenBird} />;
  } else if (tab === 'stats') {
    screen = <StatsScreen />;
  } else if (tab === 'profile') {
    screen = <ProfileScreen />;
  }

  // ¿Hay nav visible?
  const showNav = !showAdd && !openBird;

  return (
    <IOSDevice width={390} height={844}>
      <div style={{ position: 'relative', width: '100%', height: '100%', background: 'var(--bg-cream)' }}>
        <div className="nice-scroll" style={{
          position: 'absolute', inset: 0,
          overflowY: 'auto', overflowX: 'hidden',
          paddingTop: 8,
        }}>
          {screen}
        </div>
        {showNav && (
          <BottomNav
            active={tab}
            onChange={setTab}
            onAdd={() => setShowAdd(true)}
          />
        )}
      </div>
    </IOSDevice>
  );
}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(<App />);
