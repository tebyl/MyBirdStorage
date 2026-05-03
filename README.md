# MyBirdStorage — App Android nativa

Diario de observación de aves para Android. App offline-first con estética de bitácora de campo (papel, sellos, tipografía editorial).

---

## Stack

| Capa | Tecnología | Versión |
|---|---|---|
| Lenguaje | Kotlin | 2.0.21 |
| UI | Jetpack Compose + Material 3 | BOM 2024.12.01 |
| Navegación | Navigation Compose | 2.8.5 |
| Persistencia | Room (SQLite) | 2.6.1 |
| Imágenes | Coil | 2.7.0 |
| Tipografía | Google Fonts (Compose) | BOM |
| Build | AGP | 8.7.0 |
| Gradle | — | 8.9 |
| Java | JBR 21 (Android Studio) | — |
| Min SDK | Android 8.0 | API 26 |
| Target SDK | Android 15 | API 35 |

---

## Arquitectura

```
app/src/main/
├── java/com/tebyl/aviariolocal/
│   ├── MainActivity.kt              ← entry point, inicializa DB y ViewModel
│   │
│   ├── data/
│   │   ├── Bird.kt                  ← entidad Room + TypeConverter (List<String>)
│   │   ├── BirdDao.kt               ← consultas SQL: observeAll, upsert, setFavorite, delete
│   │   ├── AppDatabase.kt           ← singleton Room + seed automático al primer arranque
│   │   ├── BirdRepository.kt        ← capa intermedia entre DAO y ViewModel
│   │   └── SeedData.kt              ← 10 observaciones de muestra con fotos Unsplash
│   │
│   ├── viewmodel/
│   │   └── BirdViewModel.kt         ← StateFlow con filtrado reactivo (query + quickFilter + tags)
│   │
│   ├── navigation/
│   │   └── AviarioNav.kt            ← NavHost + BottomBar (5 destinos)
│   │
│   └── ui/
│       ├── theme/
│       │   ├── Color.kt             ← paleta completa (musgo, crema, tierra, tinta)
│       │   ├── Type.kt              ← Fraunces + Inter + Caveat vía Google Fonts
│       │   └── Theme.kt             ← MaterialTheme con AviarioColorScheme
│       │
│       ├── components/
│       │   ├── Modifiers.kt         ← paperBackground() y ruledPaper() con drawBehind
│       │   ├── Stamp.kt             ← sello circular rotado (estilo bitácora)
│       │   ├── Chip.kt              ← etiqueta interactiva musgo/crema
│       │   ├── LeafDivider.kt       ← divisor con hoja central (Canvas)
│       │   └── FieldLabel.kt        ← label en Inter mayúscula con letter-spacing
│       │
│       └── screens/
│           ├── GalleryScreen.kt     ← LazyVerticalStaggeredGrid 2 col, búsqueda, filtros
│           ├── DetailScreen.kt      ← foto + gradiente, sello, notas en papel rayado
│           ├── AddScreen.kt         ← formulario, PickVisualMedia, etiquetas con Enter
│           ├── MapScreen.kt         ← mapa topográfico Canvas + marcadores interactivos
│           ├── StatsScreen.kt       ← prosa narrativa, gráfico de barras, nube de tags
│           └── ProfileScreen.kt     ← biblioteca personal, estadísticas, ajustes
│
└── res/
    ├── values/
    │   ├── strings.xml              ← app_name: "MyBirdStorage"
    │   ├── themes.xml               ← Theme.AviarioLocal (NoActionBar)
    │   ├── colors.xml               ← colores XML para status bar y launcher
    │   └── font_certs.xml           ← certificados SHA del proveedor Google Fonts GMS
    ├── xml/
    │   └── file_paths.xml           ← FileProvider paths para cámara
    ├── drawable/
    │   └── ic_launcher_foreground.xml  ← pájaro posado en rama (vector)
    └── mipmap-anydpi-v26/
        ├── ic_launcher.xml          ← adaptive icon (fondo musgo + pájaro crema)
        └── ic_launcher_round.xml
```

---

## Flujo de datos

```
Room DB ──► BirdDao ──► BirdRepository ──► BirdViewModel
                                                │
                        StateFlow<List<Bird>>   │   MutableStateFlow
                        filteredBirds ◄─────────┤── searchQuery
                        allBirds                │── quickFilter
                        allTags                 └── activeTags
                              │
                    Pantallas (collectAsState)
```

El `BirdViewModel` combina 4 flows con `combine(allBirds, searchQuery, quickFilter, activeTags)` para filtrar reactivamente sin corrutinas manuales.

---

## Pantallas

| Pantalla | Descripción |
|---|---|
| **Gallery** | Cuadrícula masonry 2 columnas con tarjetas rotadas ±0.35°. Filtros rápidos (Todas / Recientes / Favoritas / Sin ID) y chips de etiquetas. Búsqueda en tiempo real. |
| **Detail** | Foto a pantalla completa con gradiente. Sello de bitácora rotado. Comportamiento en itálica. Notas de campo en fuente Caveat sobre papel rayado (Canvas). |
| **Add** | Formulario con picker de galería (`PickVisualMedia`). Etiquetas creadas al pulsar Enter. Notas con fondo de líneas. Guardado en Room. |
| **Map** | Mapa topográfico dibujado en Canvas (curvas de nivel + río). Marcadores interactivos para cada ave con coordenadas. Sin API key de Google Maps. |
| **Stats** | Estadísticas en prosa narrativa ("registraste *ocho observaciones*…"). Gráfico de barras por mes. Nube de etiquetas con variación tipográfica por frecuencia. |
| **Profile** | Biblioteca personal con contadores. Secciones "Tu archivo" y "Opciones". Nota de privacidad offline. |

---

## Sistema visual

### Paleta
| Token | Hex | Uso |
|---|---|---|
| `Moss700` | `#3F4A2E` | Color primario, botones, marcadores |
| `BgCream` | `#FAF6EE` | Fondo principal con patrón de puntos |
| `BgPaper` | `#F5EFE0` | Fondo de notas y tarjetas |
| `AccentRed` | `#B5462E` | Favoritas, sello "pendiente" |
| `AccentGold` | `#C9A05A` | Estrella favorita |
| `InkPrimary` | `#1F1B14` | Texto principal |

### Tipografía
| Font | Familia | Uso |
|---|---|---|
| **Fraunces** | Serif variable | Títulos, números, prosa narrativa |
| **Inter** | Sans-serif | Labels, metadatos, formularios |
| **Caveat** | Cursiva manuscrita | Notas de campo (sobre papel rayado) |

### Texturas Canvas
- `Modifier.paperBackground()` — fondo crema con puntos sutiles cada 18dp
- `Modifier.ruledPaper()` — líneas horizontales cada 24dp para notas

---

## Cómo ejecutar

```bash
# Compilar APK debug
./gradlew assembleDebug

# APK generado en:
# app/build/outputs/apk/debug/app-debug.apk
```

O abrir el directorio `AviarioLocal/` en **Android Studio** y ejecutar en el emulador **Pixel 8** (API 37).

> Las fuentes Fraunces, Inter y Caveat se descargan al primer arranque vía Google Fonts. En el emulador requiere conexión a internet en la primera ejecución.

---

## Origen

Migrado desde el prototipo web `Aviario Local.html` + archivos `.jsx` en la raíz del repositorio. El README original de migración se conservó como referencia del proceso de diseño.
