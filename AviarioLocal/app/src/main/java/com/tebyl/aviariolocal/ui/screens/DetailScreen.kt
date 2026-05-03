package com.tebyl.aviariolocal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tebyl.aviariolocal.ui.components.AviarioChip
import com.tebyl.aviariolocal.ui.components.FieldLabel
import com.tebyl.aviariolocal.ui.components.LeafDivider
import com.tebyl.aviariolocal.ui.components.Stamp
import com.tebyl.aviariolocal.ui.components.paperBackground
import com.tebyl.aviariolocal.ui.components.ruledPaper
import com.tebyl.aviariolocal.ui.theme.AccentRed
import com.tebyl.aviariolocal.ui.theme.BgPaper
import com.tebyl.aviariolocal.ui.theme.Caveat
import com.tebyl.aviariolocal.ui.theme.Fraunces
import com.tebyl.aviariolocal.ui.theme.InkMute
import com.tebyl.aviariolocal.ui.theme.InkPrimary
import com.tebyl.aviariolocal.ui.theme.InkSoft
import com.tebyl.aviariolocal.ui.theme.Moss600
import com.tebyl.aviariolocal.ui.theme.Moss900
import com.tebyl.aviariolocal.viewmodel.BirdViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailScreen(id: Long, vm: BirdViewModel, onBack: () -> Unit) {
    val birds by vm.allBirds.collectAsState()
    val bird = birds.find { it.id == id } ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paperBackground()
            .verticalScroll(rememberScrollState())
    ) {
        // Photo header with gradient overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        ) {
            AsyncImage(
                model = bird.photoUri,
                contentDescription = bird.species,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Black.copy(alpha = 0.25f),
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.35f)
                            )
                        )
                    )
            )
            // Top controls
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Outlined.ArrowBack, "Volver", tint = Color.White)
                }
                Row {
                    IconButton(onClick = { vm.toggleFavorite(bird) }) {
                        Icon(
                            imageVector = if (bird.isFavorite) Icons.Outlined.Favorite
                                          else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorita",
                            tint = if (bird.isFavorite) AccentRed else Color.White
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Outlined.Share, "Compartir", tint = Color.White)
                    }
                }
            }
        }

        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
            // Title row + stamp
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f).padding(end = 12.dp)) {
                    Text(
                        text = bird.species ?: "Sin identificar",
                        fontFamily = Fraunces,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 26.sp,
                        color = Moss900,
                        fontStyle = if (bird.species == null) FontStyle.Italic else FontStyle.Normal,
                        lineHeight = 30.sp
                    )
                    bird.sci?.let { sci ->
                        Text(
                            text = sci,
                            fontFamily = Fraunces,
                            fontStyle = FontStyle.Italic,
                            fontSize = 13.sp,
                            color = InkMute
                        )
                    }
                }
                Stamp(
                    text = if (bird.species != null) "REGISTRADO\n${bird.dateStr.take(6)}"
                           else "PENDIENTE\nIDENTIF.",
                    color = if (bird.species != null) Moss600 else AccentRed
                )
            }

            Spacer(Modifier.height(18.dp))

            // Date + Location
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    FieldLabel("Fecha")
                    Spacer(Modifier.height(4.dp))
                    Text(bird.dateStr, fontFamily = Fraunces, fontSize = 15.sp, color = InkPrimary)
                }
                Column(modifier = Modifier.weight(1f)) {
                    FieldLabel("Lugar")
                    Spacer(Modifier.height(4.dp))
                    Text(bird.location, fontFamily = Fraunces, fontSize = 15.sp, color = InkPrimary)
                }
            }

            Spacer(Modifier.height(18.dp))
            LeafDivider()
            Spacer(Modifier.height(18.dp))

            // Behavior
            FieldLabel("Comportamiento observado")
            Spacer(Modifier.height(6.dp))
            Text(
                text = bird.behavior.ifEmpty { "Sin describir." },
                fontFamily = Fraunces,
                fontStyle = FontStyle.Italic,
                fontSize = 15.sp,
                color = InkSoft,
                lineHeight = 22.sp
            )

            Spacer(Modifier.height(18.dp))

            // Notes — ruled paper
            FieldLabel("Notas de campo")
            Spacer(Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(BgPaper)
                    .ruledPaper()
                    .padding(14.dp)
            ) {
                Text(
                    text = bird.notes.ifEmpty { "Sin notas." },
                    fontFamily = Caveat,
                    fontSize = 17.sp,
                    color = InkSoft,
                    lineHeight = 24.sp
                )
            }

            if (bird.tags.isNotEmpty()) {
                Spacer(Modifier.height(18.dp))
                FieldLabel("Etiquetas")
                Spacer(Modifier.height(8.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    bird.tags.forEach { tag ->
                        AviarioChip(label = "#$tag", active = false) {}
                    }
                }
            }

            Spacer(Modifier.height(28.dp))
        }
    }
}
