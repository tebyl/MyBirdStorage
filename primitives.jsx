// primitives.jsx — íconos SVG simples (estilo lineal natural) y componentes base

function Icon({ name, size = 22, color = 'currentColor', stroke = 1.7 }) {
  const p = { width: size, height: size, viewBox: '0 0 24 24', fill: 'none', stroke: color, strokeWidth: stroke, strokeLinecap: 'round', strokeLinejoin: 'round' };
  switch (name) {
    case 'gallery':
      return (<svg {...p}><rect x="3" y="3" width="8" height="8" rx="1.5"/><rect x="13" y="3" width="8" height="8" rx="1.5"/><rect x="3" y="13" width="8" height="8" rx="1.5"/><rect x="13" y="13" width="8" height="8" rx="1.5"/></svg>);
    case 'map':
      return (<svg {...p}><path d="M9 4 3 6v14l6-2 6 2 6-2V4l-6 2-6-2Z"/><path d="M9 4v14"/><path d="M15 6v14"/></svg>);
    case 'plus':
      return (<svg {...p}><path d="M12 5v14M5 12h14"/></svg>);
    case 'stats':
      return (<svg {...p}><path d="M4 20V10"/><path d="M10 20V4"/><path d="M16 20v-8"/><path d="M22 20H2"/></svg>);
    case 'profile':
      return (<svg {...p}><circle cx="12" cy="8" r="4"/><path d="M4 21c1-4 4-6 8-6s7 2 8 6"/></svg>);
    case 'search':
      return (<svg {...p}><circle cx="11" cy="11" r="7"/><path d="m20 20-3.5-3.5"/></svg>);
    case 'filter':
      return (<svg {...p}><path d="M3 5h18M6 12h12M10 19h4"/></svg>);
    case 'star':
      return (<svg {...p}><path d="m12 3 2.6 5.6 6 .7-4.4 4.2 1.1 6L12 16.7 6.7 19.5l1.1-6L3.4 9.3l6-.7L12 3Z"/></svg>);
    case 'star-fill':
      return (<svg width={size} height={size} viewBox="0 0 24 24" fill={color}><path d="m12 3 2.6 5.6 6 .7-4.4 4.2 1.1 6L12 16.7 6.7 19.5l1.1-6L3.4 9.3l6-.7L12 3Z"/></svg>);
    case 'pin':
      return (<svg {...p}><path d="M12 22s7-7.6 7-13a7 7 0 1 0-14 0c0 5.4 7 13 7 13Z"/><circle cx="12" cy="9" r="2.5"/></svg>);
    case 'leaf':
      return (<svg {...p}><path d="M5 19c0-7 6-13 14-13 0 8-6 14-14 14Z"/><path d="M5 19c2-3 5-5 9-7"/></svg>);
    case 'feather':
      return (<svg {...p}><path d="M20 4c-3 0-12 2-12 12 0 0 8 0 12-4s4-8 0-8Z"/><path d="M16 8 4 20"/><path d="M8 16h8"/></svg>);
    case 'calendar':
      return (<svg {...p}><rect x="3" y="5" width="18" height="16" rx="2"/><path d="M3 9h18M8 3v4M16 3v4"/></svg>);
    case 'note':
      return (<svg {...p}><path d="M5 4h11l4 4v12a1 1 0 0 1-1 1H5a1 1 0 0 1-1-1V5a1 1 0 0 1 1-1Z"/><path d="M16 4v4h4"/></svg>);
    case 'tag':
      return (<svg {...p}><path d="M3 12V5a2 2 0 0 1 2-2h7l9 9-9 9-9-9Z"/><circle cx="8" cy="8" r="1.5"/></svg>);
    case 'chevron-left':
      return (<svg {...p}><path d="m15 6-6 6 6 6"/></svg>);
    case 'chevron-right':
      return (<svg {...p}><path d="m9 6 6 6-6 6"/></svg>);
    case 'share':
      return (<svg {...p}><path d="M12 4v12"/><path d="m7 9 5-5 5 5"/><path d="M5 14v5a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-5"/></svg>);
    case 'menu':
      return (<svg {...p}><path d="M4 7h16M4 12h16M4 17h16"/></svg>);
    case 'camera':
      return (<svg {...p}><path d="M5 8h3l2-3h4l2 3h3a1 1 0 0 1 1 1v10a1 1 0 0 1-1 1H5a1 1 0 0 1-1-1V9a1 1 0 0 1 1-1Z"/><circle cx="12" cy="13" r="3.5"/></svg>);
    case 'x':
      return (<svg {...p}><path d="M6 6 18 18M18 6 6 18"/></svg>);
    case 'sparrow': // ave estilizada
      return (<svg {...p}><path d="M3 14c3-1 5-3 7-6 1 2 3 3 6 3 0 4-3 7-7 7-3 0-5-2-6-4Z"/><circle cx="14" cy="9" r="0.6" fill={color}/><path d="M9 14h-4"/></svg>);
    default:
      return null;
  }
}

// Sello / stamp circular (metáfora de bitácora)
function Stamp({ children, color = 'var(--accent-red)', rotate = -8, size = 56 }) {
  return (
    <div style={{
      width: size, height: size, borderRadius: '50%',
      border: `1.5px solid ${color}`,
      color: color,
      display: 'flex', alignItems: 'center', justifyContent: 'center',
      transform: `rotate(${rotate}deg)`,
      fontFamily: "'Fraunces', serif",
      fontSize: 9, fontWeight: 600, letterSpacing: '0.1em',
      textTransform: 'uppercase', textAlign: 'center',
      lineHeight: 1.1, padding: 4,
      opacity: 0.85,
      boxShadow: 'inset 0 0 0 3px rgba(255,255,255,0.4)',
    }}>
      <div>{children}</div>
    </div>
  );
}

// Chip pequeña (etiquetas)
function Chip({ children, active = false, onClick, icon }) {
  return (
    <button onClick={onClick} style={{
      display: 'inline-flex', alignItems: 'center', gap: 6,
      padding: '6px 11px',
      borderRadius: 999,
      border: `1px solid ${active ? 'var(--moss-700)' : 'var(--line)'}`,
      background: active ? 'var(--moss-700)' : 'rgba(255,255,255,0.6)',
      color: active ? '#FBF9F3' : 'var(--ink-soft)',
      fontSize: 12.5, fontWeight: 500,
      fontFamily: 'Inter, sans-serif',
      cursor: 'pointer',
      whiteSpace: 'nowrap',
      transition: 'all 0.15s ease',
    }}>
      {icon}
      {children}
    </button>
  );
}

// Linea decorativa estilo bitácora (con pequeña hoja al centro)
function LeafDivider({ color = 'var(--moss-300)' }) {
  return (
    <div style={{ display: 'flex', alignItems: 'center', gap: 8, color }}>
      <div style={{ flex: 1, height: 1, background: 'currentColor', opacity: 0.5 }}/>
      <svg width="14" height="14" viewBox="0 0 24 24" fill="currentColor"><path d="M5 19c0-7 6-13 14-13 0 8-6 14-14 14Z"/></svg>
      <div style={{ flex: 1, height: 1, background: 'currentColor', opacity: 0.5 }}/>
    </div>
  );
}

// Pequeña ficha "field-note" — etiqueta tipográfica
function FieldLabel({ children }) {
  return (
    <div style={{
      fontFamily: 'Inter, sans-serif',
      fontSize: 10.5, fontWeight: 600,
      letterSpacing: '0.14em',
      textTransform: 'uppercase',
      color: 'var(--ink-mute)',
    }}>{children}</div>
  );
}

Object.assign(window, { Icon, Stamp, Chip, LeafDivider, FieldLabel });
