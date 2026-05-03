package com.tebyl.aviariolocal.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tebyl.aviariolocal.data.Bird
import com.tebyl.aviariolocal.ui.components.AviarioChip
import com.tebyl.aviariolocal.ui.components.paperBackground
import com.tebyl.aviariolocal.ui.theme.AccentGold
import com.tebyl.aviariolocal.ui.theme.BgPaper
import com.tebyl.aviariolocal.ui.theme.BgWarm
import com.tebyl.aviariolocal.ui.theme.Caveat
import com.tebyl.aviariolocal.ui.theme.Fraunces
import com.tebyl.aviariolocal.ui.theme.Inter
import com.tebyl.aviariolocal.ui.theme.InkMute
import com.tebyl.aviariolocal.ui.theme.InkPrimary
import com.tebyl.aviariolocal.ui.theme.InkSoft
import com.tebyl.aviariolocal.ui.theme.Line
import com.tebyl.aviariolocal.ui.theme.Moss300
import com.tebyl.aviariolocal.ui.theme.Moss600
import com.tebyl.aviariolocal.ui.theme.Moss900
import com.tebyl.aviariolocal.viewmodel.BirdViewModel

@Composable
fun GalleryScreen(vm: BirdViewModel, onOpenBird: (Long) -> Unit) {
    val filtered by vm.filteredBirds.collectAsState()
    val allTags  by vm.allTags.collectAsState()
    val query    by vm.searchQuery.collectAsState()
    val qFilter  by vm.quickFilter.collectAsState()
    val aTags    by vm.activeTags.collectAsState()

    Column(modifier = Modifier.fillMaxSize().paperBackground()) {
        GalleryHeader(query = query, onQueryChange = { vm.searchQuery.value = it })

        // Quick filter chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                "all" to "Todas",
                "recent" to "Recientes",
                "fav" to "Favoritas",
                "unid" to "Sin ID"
            ).forEach { (key, label) ->
                AviarioChip(label = label, active = qFilter == key) {
                    vm.quickFilter.value = key
                }
            }
        }

        // Tag chips
        if (allTags.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                allTags.take(14).forEach { tag ->
                    AviarioChip(
                        label = "#$tag",
                        active = tag in aTags
                    ) {
                        vm.activeTags.value =
                            if (tag in aTags) aTags - tag else aTags + tag
                    }
                }
            }
        }

        if (filtered.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    "Sin observaciones",
                    color = InkMute,
                    fontFamily = Fraunces,
                    fontStyle = FontStyle.Italic,
                    fontSize = 16.sp
                )
            }
        } else {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                verticalItemSpacing = 12.dp,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filtered, key = { it.id }) { bird ->
                    GalleryCard(
                        bird = bird,
                        index = filtered.indexOf(bird),
                        onClick = { onOpenBird(bird.id) }
                    )
                }
                item(span = StaggeredGridItemSpan.FullLine) {
                    Text(
                        text = "quien observa, recuerda",
                        color = InkMute,
                        fontFamily = Caveat,
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun GalleryHeader(query: String, onQueryChange: (String) -> Unit) {
    var showSearch by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "MyBirdStorage",
                    fontFamily = Fraunces,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 28.sp,
                    color = Moss900
                )
                Text(
                    text = "bitácora de campo",
                    fontFamily = Inter,
                    fontSize = 11.sp,
                    color = InkMute,
                    letterSpacing = 1.sp
                )
            }
            IconButton(onClick = { showSearch = !showSearch }) {
                Icon(Icons.Outlined.Search, contentDescription = "Buscar", tint = InkSoft)
            }
        }
        if (showSearch) {
            Spacer(Modifier.height(8.dp))
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerField ->
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = BgPaper,
                        border = BorderStroke(1.dp, Line)
                    ) {
                        Box(Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
                            if (query.isEmpty()) {
                                Text(
                                    "Especie, lugar, etiqueta…",
                                    color = InkMute,
                                    fontFamily = Inter,
                                    fontSize = 14.sp
                                )
                            }
                            innerField()
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun GalleryCard(bird: Bird, index: Int, onClick: () -> Unit) {
    val rotation    = if (index % 2 == 0) 0.35f else -0.35f
    val aspectRatio = if (index % 3 == 0) 0.88f else 1.18f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .rotate(rotation)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = BgWarm),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column {
            AsyncImage(
                model = bird.photoUri,
                contentDescription = bird.species,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(aspectRatio)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            )
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = bird.species ?: "Sin identificar",
                    fontFamily = Fraunces,
                    fontWeight = if (bird.species != null) FontWeight.SemiBold else FontWeight.Normal,
                    fontStyle = if (bird.species == null) FontStyle.Italic else FontStyle.Normal,
                    fontSize = 13.sp,
                    color = if (bird.species != null) InkPrimary else InkMute,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = bird.locShort,
                    fontFamily = Inter,
                    fontSize = 10.sp,
                    color = InkMute,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (bird.isFavorite) {
                    Spacer(Modifier.height(4.dp))
                    Text("★", color = AccentGold, fontSize = 11.sp)
                }
                if (bird.tags.isNotEmpty()) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = bird.tags.take(2).joinToString(" ") { "#$it" },
                        fontFamily = Inter,
                        fontSize = 9.sp,
                        color = Moss600,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
