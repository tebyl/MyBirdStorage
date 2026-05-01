// Datos ficticios — observaciones de aves
// Fotos de Unsplash con tamaño optimizado (w=600 q=80)

const BIRDS = [
  {
    id: 1,
    species: 'Petirrojo europeo',
    sci: 'Erithacus rubecula',
    photo: 'https://images.unsplash.com/photo-1452570053594-1b985d6ea890?w=600&q=80',
    h: 320,
    date: '12 abr 2026',
    dateShort: '12 abr',
    location: 'Bosque de los Robles, sendero norte',
    locShort: 'Bosque de los Robles',
    behavior: 'Posado en rama baja, vocalizando al amanecer.',
    notes: 'Lo escuché antes de verlo. Se quedó muy quieto mientras ajustaba el lente.',
    tags: ['canto', 'amanecer', 'sendero norte'],
    fav: true,
    coords: { x: 38, y: 42 },
  },
  {
    id: 2,
    species: 'Carbonero común',
    sci: 'Parus major',
    photo: 'https://images.unsplash.com/photo-1444464666168-49d633b86797?w=600&q=80',
    h: 260,
    date: '10 abr 2026',
    dateShort: '10 abr',
    location: 'Jardín trasero',
    locShort: 'Jardín de casa',
    behavior: 'Comiendo semillas del comedero colgante.',
    notes: 'Visita habitual. Muy poco tímido últimamente.',
    tags: ['jardín', 'comedero'],
    fav: false,
    coords: { x: 62, y: 28 },
  },
  {
    id: 3,
    species: null,
    photo: 'https://images.unsplash.com/photo-1555169062-013468b47731?w=600&q=80',
    h: 380,
    date: '08 abr 2026',
    dateShort: '08 abr',
    location: 'Laguna del Mirador',
    locShort: 'Laguna del Mirador',
    behavior: 'Nadando en pareja cerca del juncal.',
    notes: 'No alcancé a distinguir bien el plumaje. Volver con prismáticos.',
    tags: ['agua', 'pareja', 'pendiente'],
    fav: false,
    coords: { x: 24, y: 70 },
  },
  {
    id: 4,
    species: 'Mirlo común',
    sci: 'Turdus merula',
    photo: 'https://images.unsplash.com/photo-1551634979-2b11f8c218da?w=600&q=80',
    h: 300,
    date: '05 abr 2026',
    dateShort: '05 abr',
    location: 'Parque de la Alameda',
    locShort: 'Parque Alameda',
    behavior: 'Buscando lombrices en el césped tras la lluvia.',
    notes: 'Muy activo después del chubasco. El macho cantó desde el ciprés.',
    tags: ['parque', 'lluvia', 'canto'],
    fav: true,
    coords: { x: 70, y: 58 },
  },
  {
    id: 5,
    species: 'Jilguero europeo',
    sci: 'Carduelis carduelis',
    photo: 'https://images.unsplash.com/photo-1591608971362-f08b2a75731a?w=600&q=80',
    h: 340,
    date: '02 abr 2026',
    dateShort: '02 abr',
    location: 'Camino de los Cardos',
    locShort: 'Camino de los Cardos',
    behavior: 'Bandada pequeña alimentándose de semillas.',
    notes: 'Cinco o seis individuos. Colores muy vivos.',
    tags: ['bandada', 'cardos'],
    fav: false,
    coords: { x: 48, y: 35 },
  },
  {
    id: 6,
    species: 'Herrerillo común',
    sci: 'Cyanistes caeruleus',
    photo: 'https://images.unsplash.com/photo-1486365227551-f3f90034a57c?w=600&q=80',
    h: 280,
    date: '28 mar 2026',
    dateShort: '28 mar',
    location: 'Jardín trasero',
    locShort: 'Jardín de casa',
    behavior: 'Inspeccionando una caja nido vacía.',
    notes: 'Posible pareja explorando. Esperar y ver.',
    tags: ['jardín', 'nido', 'pareja'],
    fav: true,
    coords: { x: 60, y: 30 },
  },
  {
    id: 7,
    species: null,
    photo: 'https://images.unsplash.com/photo-1480044965905-02098d419e96?w=600&q=80',
    h: 360,
    date: '25 mar 2026',
    dateShort: '25 mar',
    location: 'Mirador del río',
    locShort: 'Mirador del río',
    behavior: 'Vuelo bajo sobre el agua.',
    notes: 'Solo lo vi de pasada. Plumaje oscuro, pico largo.',
    tags: ['vuelo', 'río', 'pendiente'],
    fav: false,
    coords: { x: 18, y: 55 },
  },
  {
    id: 8,
    species: 'Cernícalo vulgar',
    sci: 'Falco tinnunculus',
    photo: 'https://images.unsplash.com/photo-1551717743-49959800b1f6?w=600&q=80',
    h: 310,
    date: '20 mar 2026',
    dateShort: '20 mar',
    location: 'Campos de la dehesa',
    locShort: 'Campos de la dehesa',
    behavior: 'Cerniéndose sobre el campo abierto.',
    notes: 'Bajó en picado dos veces. No conseguí ver si capturó algo.',
    tags: ['rapaz', 'dehesa'],
    fav: true,
    coords: { x: 80, y: 75 },
  },
  {
    id: 9,
    species: 'Gorrión común',
    sci: 'Passer domesticus',
    photo: 'https://images.unsplash.com/photo-1574068468668-a05a11f871da?w=600&q=80',
    h: 260,
    date: '18 mar 2026',
    dateShort: '18 mar',
    location: 'Plaza del pueblo',
    locShort: 'Plaza del pueblo',
    behavior: 'Bandada bañándose en charco.',
    notes: 'Muy ruidosos. Alegraron la tarde gris.',
    tags: ['plaza', 'bandada', 'agua'],
    fav: false,
    coords: { x: 50, y: 50 },
  },
  {
    id: 10,
    species: 'Pinzón vulgar',
    sci: 'Fringilla coelebs',
    photo: 'https://images.unsplash.com/photo-1606567595334-d39972c85dbe?w=600&q=80',
    h: 290,
    date: '15 mar 2026',
    dateShort: '15 mar',
    location: 'Bosque de los Robles, claro central',
    locShort: 'Bosque de los Robles',
    behavior: 'Macho cantando desde lo alto de un fresno.',
    notes: 'Canto característico, descendente. Repetido cada minuto aprox.',
    tags: ['canto', 'sendero norte'],
    fav: false,
    coords: { x: 40, y: 45 },
  },
];

// Cuenta de tags
const TAG_COUNTS = (() => {
  const m = {};
  BIRDS.forEach(b => b.tags.forEach(t => { m[t] = (m[t] || 0) + 1; }));
  return Object.entries(m).sort((a,b) => b[1] - a[1]);
})();

const ALL_TAGS = TAG_COUNTS.map(([t]) => t);

// Avistamientos por mes (para estadísticas)
const MONTHLY = [
  { month: 'oct', count: 2 },
  { month: 'nov', count: 4 },
  { month: 'dic', count: 3 },
  { month: 'ene', count: 5 },
  { month: 'feb', count: 6 },
  { month: 'mar', count: 9 },
  { month: 'abr', count: 5 },
];

// Especies registradas
const SPECIES = [...new Set(BIRDS.filter(b => b.species).map(b => b.species))];

// Especie más frecuente — calculada
const TOP_SPECIES = (() => {
  const m = {};
  BIRDS.forEach(b => { if (b.species) m[b.species] = (m[b.species] || 0) + 1; });
  const sorted = Object.entries(m).sort((a,b) => b[1] - a[1]);
  return sorted[0] ? sorted[0][0] : '—';
})();

Object.assign(window, { BIRDS, TAG_COUNTS, ALL_TAGS, MONTHLY, SPECIES, TOP_SPECIES });
