# Aviario Local → Migración a Android nativo (Kotlin + Jetpack Compose)

Esta guía traduce el prototipo HTML/React (`Aviario Local.html` + archivos `.jsx`) a una app Android nativa usando **Kotlin** y **Jetpack Compose**. Conserva la metáfora de bitácora de campo, la paleta verde musgo / crema y la tipografía editorial.

---

## 1. Stack recomendado

| Necesidad | Tecnología |
|---|---|
| UI | Jetpack Compose (Material 3) |
| Navegación | `androidx.navigation:navigation-compose` |
| Persistencia | Room (SQLite) o DataStore para preferencias |
| Imágenes | Coil (`io.coil-kt:coil-compose`) |
| Cámara / galería | `ActivityResultContracts.PickVisualMedia` |
| Mapa | Google Maps Compose (`com.google.maps.android:maps-compose`) o MapLibre/OSMDroid si se quiere offline |
| Tipografías | Google Fonts vía `androidx.compose.ui.text.googlefonts` |
| Min SDK sugerido | 26 (Android 8.0) — Target 34 |

---

## 2. Estructura del proyecto Android

```
app/
 └─ src/main/
    ├─ java/com/tuusuario/aviariolocal/
    │   ├─ MainActivity.kt
    │   ├─ ui/
    │   │   ├─ theme/
    │   │   │   ├─ Color.kt        ← paleta del prototipo
    │   │   │   ├─ Type.kt         ← Fraunces + Inter + Caveat
    │   │   │   └─ Theme.kt
    │   │   ├─ components/
    │   │   │   ├─ Stamp.kt        ← sello circular de bitácora
    │   │   │   ├─ Chip.kt
    │   │   │   ├─ LeafDivider.kt
    │   │   │   ├─ FieldLabel.kt
    │   │   │   └─ Icons.kt        ← íconos lineales
    │   │   └─ screens/
    │   │       ├─ GalleryScreen.kt
    │   │       ├─ DetailScreen.kt
    │   │       ├─ MapScreen.kt
    │   │       ├─ StatsScreen.kt
    │   │       ├─ AddScreen.kt
    │   │       └─ ProfileScreen.kt
    │   ├─ data/
    │   │   ├─ Bird.kt             ← entidad Room
    │   │   ├─ BirdDao.kt
    │   │   ├─ AppDatabase.kt
    │   │   └─ BirdRepository.kt
    │   └─ navigation/
    │       └─ AviarioNav.kt
    └─ res/
        └─ values/strings.xml
```

---

## 3. Mapeo: archivo JSX → archivo Kotlin

| JSX original | Equivalente Kotlin |
|---|---|
| `data.jsx` (BIRDS, MONTHLY, etc.) | `data/Bird.kt` + seed en `AppDatabase.kt` |
| `primitives.jsx` (Icon, Stamp, Chip, LeafDivider, FieldLabel) | `ui/components/*.kt` |
| `screens-gallery.jsx` | `ui/screens/GalleryScreen.kt` |
| `screens-detail.jsx` | `ui/screens/DetailScreen.kt` |
| `screens-map.jsx` | `ui/screens/MapScreen.kt` |
| `screens-stats.jsx` | `ui/screens/StatsScreen.kt` |
| `screens-add.jsx` | `ui/screens/AddScreen.kt` |
| `screens-profile.jsx` | `ui/screens/ProfileScreen.kt` |
| `app.jsx` (BottomNav + router) | `MainActivity.kt` + `navigation/AviarioNav.kt` |
| `ios-frame.jsx` | **eliminar** — el sistema Android provee el chrome real |

---

## 4. Sistema visual

### 4.1 Paleta (`ui/theme/Color.kt`)

Trasladar las CSS variables a `Color`:

```kotlin
val BgCream     = Color(0xFFFAF6EE)
val BgPaper     = Color(0xFFF5EFE0)
val BgWarm      = Color(0xFFFBF9F3)
val Moss900     = Color(0xFF2E3A28)
val Moss700     = Color(0xFF3F4A2E)
val Moss600     = Color(0xFF5C6B47)
val Moss300     = Color(0xFFA8B493)
val Moss100     = Color(0xFFDCE2CC)
val Bark700     = Color(0xFF4A3A28)
val Bark500     = Color(0xFF7A5A3A)
val Bark400     = Color(0xFFA68B5B)
val Bark100     = Color(0xFFE8DCC4)
val InkPrimary  = Color(0xFF1F1B14)
val InkSoft     = Color(0xFF4A4438)
val InkMute     = Color(0xFF8A8270)
val Line        = Color(0xFFE0D6BD)
val AccentRed   = Color(0xFFB5462E)
val AccentGold  = Color(0xFFC9A05A)
```

### 4.2 Tipografía (`ui/theme/Type.kt`)

Usar Downloadable Fonts:

```kotlin
val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val Fraunces = FontFamily(Font(GoogleFont("Fraunces"), provider))
val Inter    = FontFamily(Font(GoogleFont("Inter"), provider))
val Caveat   = FontFamily(Font(GoogleFont("Caveat"), provider))

val AviarioTypography = Typography(
    displayLarge = TextStyle(fontFamily = Fraunces, fontSize = 36.sp, letterSpacing = (-0.7).sp),
    headlineMedium = TextStyle(fontFamily = Fraunces, fontSize = 24.sp),
    bodyLarge = TextStyle(fontFamily = Fraunces, fontSize = 16.sp, lineHeight = 24.sp),
    bodyMedium = TextStyle(fontFamily = Inter, fontSize = 14.sp),
    labelSmall = TextStyle(fontFamily = Inter, fontSize = 11.sp, letterSpacing = 1.6.sp)
)
```

`hand` (Caveat) se aplica con `Modifier` o un `TextStyle` específico para notas manuscritas.

### 4.3 Textura de papel

Crear un `Modifier.paperBackground()` que dibuje los puntos suaves con `drawBehind`:

```kotlin
fun Modifier.paperBackground() = this
    .background(BgCream)
    .drawBehind {
        val dotColor = Bark500.copy(alpha = 0.05f)
        val step = 18.dp.toPx()
        for (x in 0..(size.width / step).toInt()) {
            for (y in 0..(size.height / step).toInt()) {
                drawCircle(dotColor, 1f, Offset(x * step, y * step))
            }
        }
    }
```

---

## 5. Modelo de datos (Room)

```kotlin
@Entity(tableName = "birds")
data class Bird(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val species: String?,        // null = sin identificar
    val sci: String?,
    val photoUri: String,        // file:// o content://
    val date: Long,              // epoch millis
    val location: String,
    val locShort: String,
    val behavior: String,
    val notes: String,
    val tags: List<String>,      // requiere TypeConverter
    val isFavorite: Boolean,
    val lat: Double?,
    val lng: Double?
)

@Dao
interface BirdDao {
    @Query("SELECT * FROM birds ORDER BY date DESC")
    fun observeAll(): Flow<List<Bird>>

    @Query("SELECT * FROM birds WHERE id = :id")
    suspend fun get(id: Long): Bird?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(bird: Bird): Long

    @Query("UPDATE birds SET isFavorite = :fav WHERE id = :id")
    suspend fun setFavorite(id: Long, fav: Boolean)

    @Delete
    suspend fun delete(bird: Bird)
}
```

`TypeConverter` para `List<String>` con coma-separación o JSON.

---

## 6. Mapeo de componentes JSX → Compose

### 6.1 `Chip` (de `primitives.jsx`)

```kotlin
@Composable
fun AviarioChip(
    label: String,
    active: Boolean,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(50),
        color = if (active) Moss700 else Color.White.copy(alpha = 0.6f),
        border = BorderStroke(1.dp, if (active) Moss700 else Line),
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Text(
            label,
            color = if (active) BgWarm else InkSoft,
            fontFamily = Inter,
            fontSize = 12.5.sp,
            modifier = Modifier.padding(horizontal = 11.dp, vertical = 6.dp)
        )
    }
}
```

### 6.2 `Stamp` (sello circular)

```kotlin
@Composable
fun Stamp(
    text: String,
    color: Color = AccentRed,
    rotate: Float = -8f,
    size: Dp = 56.dp
) {
    Box(
        modifier = Modifier
            .size(size)
            .rotate(rotate)
            .border(1.5.dp, color, CircleShape)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            color = color,
            fontFamily = Fraunces,
            fontSize = 9.sp,
            letterSpacing = 1.2.sp,
            textAlign = TextAlign.Center,
            lineHeight = 10.sp,
            modifier = Modifier.offset(y = 0.dp)
        )
    }
}
```

### 6.3 Galería masonry 2 col

Compose no tiene masonry nativo en stable, pero hay dos opciones:

- `LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2))` — **recomendado**, ya estable.
- O dos `LazyColumn` lado a lado dentro de un `Row`.

```kotlin
LazyVerticalStaggeredGrid(
    columns = StaggeredGridCells.Fixed(2),
    contentPadding = PaddingValues(16.dp),
    verticalItemSpacing = 12.dp,
    horizontalArrangement = Arrangement.spacedBy(12.dp)
) {
    items(birds, key = { it.id }) { bird ->
        GalleryCard(bird, onClick = { nav.navigate("detail/${bird.id}") })
    }
}
```

### 6.4 Notas manuscritas (papel rayado)

El `repeating-linear-gradient` se traduce a `drawBehind` con líneas horizontales:

```kotlin
fun Modifier.ruledPaper() = this.drawBehind {
    val lineColor = Bark500.copy(alpha = 0.12f)
    val step = 24.dp.toPx()
    var y = step
    while (y < size.height) {
        drawLine(lineColor, Offset(0f, y), Offset(size.width, y), 1f)
        y += step
    }
}
```

### 6.5 Bottom navigation

Compose Material3 ya provee `NavigationBar`. Personalizar con la paleta:

```kotlin
NavigationBar(containerColor = BgWarm.copy(alpha = 0.92f)) {
    items.forEach { item ->
        NavigationBarItem(
            selected = current == item.route,
            onClick = { nav.navigate(item.route) },
            icon = { Icon(item.icon, null) },
            label = { Text(item.label, fontSize = 9.5.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Moss700,
                selectedTextColor = Moss700,
                indicatorColor = Color.Transparent,
                unselectedIconColor = InkMute
            )
        )
    }
}
```

El botón central de "Añadir" se hace con un `FloatingActionButton` o un `NavigationBarItem` custom con fondo `Moss700`.

### 6.6 Mapa

Reemplazar el SVG topográfico simulado por mapa real:

```kotlin
GoogleMap(
    modifier = Modifier.fillMaxSize(),
    cameraPositionState = cameraState,
    properties = MapProperties(mapStyleOptions = MapStyleOptions(R.raw.map_style_sepia.toString()))
) {
    birds.forEach { b ->
        if (b.lat != null && b.lng != null) {
            Marker(
                state = MarkerState(LatLng(b.lat, b.lng)),
                title = b.species ?: "Sin identificar",
                onClick = { selectBird(b); true }
            )
        }
    }
}
```

Crear un JSON de estilo de mapa en `res/raw/map_style_sepia.json` con tonos cálidos para conservar el look de bitácora. (Generar en https://mapstyle.withgoogle.com).

### 6.7 Cámara / selección de foto

Usar `rememberLauncherForActivityResult` + `PickVisualMedia`:

```kotlin
val pickPhoto = rememberLauncherForActivityResult(
    PickVisualMedia()
) { uri -> uri?.let { onPhotoPicked(it.toString()) } }

Button(onClick = { pickPhoto.launch(PickVisualMediaRequest(ImageOnly)) }) {
    Text("Añadir foto")
}
```

Para cámara directa: `ActivityResultContracts.TakePicture()` + un `FileProvider`.

### 6.8 Permisos

`AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.CAMERA"/>
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
<uses-feature android:name="android.hardware.camera" android:required="false"/>
```

Solicitar runtime con `rememberPermissionState` (Accompanist Permissions).

---

## 7. Navegación (`navigation/AviarioNav.kt`)

```kotlin
@Composable
fun AviarioNav() {
    val nav = rememberNavController()
    val backStack by nav.currentBackStackEntryAsState()
    val current = backStack?.destination?.route

    Scaffold(
        bottomBar = {
            if (current?.startsWith("detail") != true && current != "add") {
                AviarioBottomBar(current, nav)
            }
        }
    ) { padding ->
        NavHost(nav, startDestination = "gallery", modifier = Modifier.padding(padding)) {
            composable("gallery") { GalleryScreen(onOpenBird = { nav.navigate("detail/$it") }) }
            composable("map")     { MapScreen(onOpenBird = { nav.navigate("detail/$it") }) }
            composable("add")     { AddScreen(onClose = { nav.popBackStack() }) }
            composable("stats")   { StatsScreen() }
            composable("profile") { ProfileScreen() }
            composable(
                "detail/{id}",
                arguments = listOf(navArgument("id") { type = NavType.LongType })
            ) { entry ->
                DetailScreen(
                    id = entry.arguments!!.getLong("id"),
                    onBack = { nav.popBackStack() }
                )
            }
        }
    }
}
```

---

## 8. ViewModel sugerido

```kotlin
class GalleryViewModel(repo: BirdRepository) : ViewModel() {
    val birds: StateFlow<List<Bird>> = repo.observeAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    var query by mutableStateOf("")
    var activeTags by mutableStateOf<Set<String>>(emptySet())
    var quickFilter by mutableStateOf("all")

    val filtered: StateFlow<List<Bird>> = combine(birds, snapshotFlow { query to activeTags to quickFilter }) { all, _ ->
        all.filter { /* lógica equivalente al GalleryScreen.filtered del JSX */ }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
```

---

## 9. Datos semilla

Migrar el array `BIRDS` de `data.jsx` a una función `seed()` que se ejecute la primera vez:

```kotlin
suspend fun seedIfEmpty(dao: BirdDao) {
    if (dao.count() > 0) return
    listOf(
        Bird(species = "Petirrojo europeo", sci = "Erithacus rubecula", /* … */),
        // resto de las 10 entradas
    ).forEach { dao.upsert(it) }
}
```

Las URLs de Unsplash funcionan con Coil sin cambios; ideal para pre-poblar la app de demo. Para producción, sustituir por `Uri` locales.

---

## 10. Checklist de paridad visual

- [ ] Fondo crema con puntos sutiles (`paperBackground`)
- [ ] Headers con Fraunces + sub-label en Inter mayúscula con letter-spacing
- [ ] Sellos circulares con rotación leve (`Stamp`)
- [ ] Notas en Caveat sobre "papel rayado" (`ruledPaper`)
- [ ] Divisor con hoja central (`LeafDivider` con `Canvas`)
- [ ] Tarjetas de galería con leve rotación alterna (`Modifier.rotate(0.4f)`)
- [ ] Bottom nav con botón "+" central elevado
- [ ] Brújula y curvas de nivel en mapa (overlay decorativo opcional)
- [ ] Estadísticas como prosa (números resaltados en Moss700, palabras en Fraunces)

---

## 11. Pasos de arranque

1. **Crear proyecto** en Android Studio: *New Project → Empty Activity (Compose)* — Kotlin, Min SDK 26.
2. Añadir dependencias en `build.gradle.kts (app)`:
   ```kotlin
   implementation("androidx.compose.material3:material3:1.2.1")
   implementation("androidx.navigation:navigation-compose:2.7.7")
   implementation("androidx.room:room-runtime:2.6.1")
   ksp("androidx.room:room-compiler:2.6.1")
   implementation("androidx.room:room-ktx:2.6.1")
   implementation("io.coil-kt:coil-compose:2.6.0")
   implementation("com.google.maps.android:maps-compose:4.3.3")
   implementation("com.google.android.gms:play-services-maps:18.2.0")
   implementation("androidx.compose.ui:ui-text-google-fonts:1.6.7")
   ```
3. Pegar `Color.kt`, `Type.kt`, `Theme.kt`.
4. Crear modelo Room + repositorio + seed.
5. Implementar pantallas en orden: Gallery → Detail → Add → Stats → Profile → Map.
6. Cablear navegación.
7. Probar en emulador (Pixel 6 API 34 recomendado).

---

## 12. Notas de fidelidad

- **iOS frame**: descártalo — el chrome del sistema Android (status bar, gesture bar) lo reemplaza.
- **`backdrop-filter`**: en Compose usa `Modifier.blur()` (sólo Android 12+) o degradados sólidos como fallback.
- **Animaciones**: el JSX no tiene animaciones complejas; en Compose añade `AnimatedVisibility` y `animateContentSize()` para suavizar transiciones de filtros y apertura de detalle.
- **Búsqueda**: usar `derivedStateOf` para filtrar reactivamente.
- **Modo oscuro**: definir un `darkColorScheme` con Moss900 de fondo e InkPrimary invertido si se quiere soportar (no estaba en el prototipo).

---

Con esto tienes una correspondencia 1:1 entre cada archivo JSX del prototipo y su equivalente Kotlin/Compose. La paleta, tipografía y metáforas (sellos, papel rayado, hojas, prosa narrativa) se mantienen fielmente.
