package com.tebyl.aviariolocal.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tebyl.aviariolocal.data.Bird
import com.tebyl.aviariolocal.ui.components.FieldLabel
import com.tebyl.aviariolocal.ui.theme.AccentRed
import com.tebyl.aviariolocal.ui.theme.BgWarm
import com.tebyl.aviariolocal.ui.theme.Caveat
import com.tebyl.aviariolocal.ui.theme.Fraunces
import com.tebyl.aviariolocal.ui.theme.Inter
import com.tebyl.aviariolocal.ui.theme.InkMute
import com.tebyl.aviariolocal.ui.theme.InkPrimary
import com.tebyl.aviariolocal.ui.theme.InkSoft
import com.tebyl.aviariolocal.ui.theme.Moss100
import com.tebyl.aviariolocal.ui.theme.Moss300
import com.tebyl.aviariolocal.ui.theme.Moss600
import com.tebyl.aviariolocal.ui.theme.Moss700
import com.tebyl.aviariolocal.ui.theme.Moss900
import com.tebyl.aviariolocal.viewmodel.BirdViewModel

@Composable
fun MapScreen(vm: BirdViewModel, onOpenBird: (Long) -> Unit) {
    val birds by vm.allBirds.collectAsState()
    val mapped = birds.filter { it.lat != null && it.lng != null }
    var selected by remember { mutableStateOf<Bird?>(null) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8E3D0))
    ) {
        val density = LocalDensity.current
        val wPx = constraints.maxWidth.toFloat()
        val hPx = constraints.maxHeight.toFloat()

        val latMin = mapped.minOfOrNull { it.lat!! } ?: 40.395
        val latMax = mapped.maxOfOrNull { it.lat!! } ?: 40.425
        val lngMin = mapped.minOfOrNull { it.lng!! } ?: -3.725
        val lngMax = mapped.maxOfOrNull { it.lng!! } ?: -3.695
        val pad = 0.003
        val latRange = (latMax - latMin + 2 * pad).coerceAtLeast(0.01)
        val lngRange = (lngMax - lngMin + 2 * pad).coerceAtLeast(0.01)

        fun lngToX(lng: Double) = ((lng - lngMin + pad) / lngRange * wPx).toFloat()
        fun latToY(lat: Double) = ((latMax + pad - lat) / latRange * hPx).toFloat()

        // Topographic canvas background + markers
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Contour rings
            val cx = size.width * 0.48f
            val cy = size.height * 0.40f
            for (i in 1..7) {
                drawCircle(
                    color = Moss300.copy(alpha = 0.13f),
                    radius = size.minDimension * 0.085f * i,
                    center = Offset(cx, cy),
                    style = Stroke(1.dp.toPx())
                )
            }
            // Secondary hill
            for (i in 1..4) {
                drawCircle(
                    color = Moss300.copy(alpha = 0.08f),
                    radius = size.minDimension * 0.07f * i,
                    center = Offset(size.width * 0.78f, size.height * 0.65f),
                    style = Stroke(0.8.dp.toPx())
                )
            }
            // River path
            val river = Path().apply {
                moveTo(0f, size.height * 0.62f)
                cubicTo(
                    size.width * 0.22f, size.height * 0.56f,
                    size.width * 0.55f, size.height * 0.70f,
                    size.width, size.height * 0.60f
                )
            }
            drawPath(river, Color(0xFFADCCE8).copy(alpha = 0.50f), style = Stroke(10.dp.toPx()))
            drawPath(river, Color(0xFF8FB8D8).copy(alpha = 0.25f), style = Stroke(5.dp.toPx()))

            // Bird markers
            mapped.forEach { bird ->
                val x = lngToX(bird.lng!!)
                val y = latToY(bird.lat!!)
                val isSelected = bird.id == selected?.id
                val col = if (bird.isFavorite) AccentRed else Moss700
                val r = if (isSelected) 18.dp.toPx() else 11.dp.toPx()
                drawCircle(col, r, Offset(x, y))
                drawCircle(Color.White, r * 0.45f, Offset(x, y))
                if (isSelected) {
                    drawCircle(col.copy(alpha = 0.25f), r + 6.dp.toPx(), Offset(x, y))
                }
            }
        }

        // Transparent tap targets over each marker
        mapped.forEach { bird ->
            val xPx = lngToX(bird.lng!!)
            val yPx = latToY(bird.lat!!)
            with(density) {
                val tapSize = 44.dp
                Box(
                    modifier = Modifier
                        .offset(x = xPx.toDp() - tapSize / 2, y = yPx.toDp() - tapSize / 2)
                        .size(tapSize)
                        .clickable { selected = bird }
                )
            }
        }

        // Header overlay
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth()
                .background(BgWarm.copy(alpha = 0.90f))
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            Column {
                Text(
                    "Mapa de campo",
                    fontFamily = Fraunces,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 22.sp,
                    color = Moss900
                )
                Text(
                    "${mapped.size} avistamientos registrados",
                    fontFamily = Inter,
                    fontSize = 11.sp,
                    color = InkMute,
                    letterSpacing = 0.5.sp
                )
            }
        }

        // Bottom overlay
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(BgWarm.copy(alpha = 0.93f))
                .padding(12.dp)
        ) {
            if (selected != null) {
                MapDetailCard(
                    bird = selected!!,
                    onOpen = { onOpenBird(selected!!.id) },
                    onClose = { selected = null }
                )
            } else {
                FieldLabel("en el mapa")
                Spacer(Modifier.height(8.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    items(mapped) { bird ->
                        BirdMapChip(bird = bird, onClick = { selected = bird })
                    }
                }
            }
        }
    }
}

@Composable
private fun MapDetailCard(bird: Bird, onOpen: () -> Unit, onClose: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onOpen),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = bird.photoUri,
            contentDescription = bird.species
                ?.let { "Foto de $it en ${bird.location}" }
                ?: "Ave sin identificar en ${bird.location}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = bird.species ?: "Sin identificar",
                fontFamily = Fraunces,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Moss900,
                fontStyle = if (bird.species == null) FontStyle.Italic else FontStyle.Normal
            )
            Text(
                text = "${bird.locShort} · ${bird.dateStr}",
                fontFamily = Inter,
                fontSize = 11.sp,
                color = InkMute
            )
            if (bird.tags.isNotEmpty()) {
                Text(
                    text = bird.tags.take(3).joinToString(" ") { "#$it" },
                    fontFamily = Inter,
                    fontSize = 10.sp,
                    color = Moss600
                )
            }
        }
        IconButton(onClick = onClose) {
            Icon(Icons.Outlined.Close, "Cerrar", tint = InkMute)
        }
    }
}

@Composable
private fun BirdMapChip(bird: Bird, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(50),
        color = if (bird.isFavorite) AccentRed.copy(alpha = 0.10f) else Moss100,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (bird.isFavorite) Text("★", color = AccentRed, fontSize = 10.sp)
            Text(
                text = bird.species ?: "Sin ID",
                fontFamily = Inter,
                fontSize = 12.sp,
                color = InkSoft
            )
        }
    }
}
