package com.tebyl.aviariolocal.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tebyl.aviariolocal.data.Bird
import com.tebyl.aviariolocal.ui.components.FieldLabel
import com.tebyl.aviariolocal.ui.components.LeafDivider
import com.tebyl.aviariolocal.ui.components.Stamp
import com.tebyl.aviariolocal.ui.components.paperBackground
import com.tebyl.aviariolocal.ui.theme.AccentGold
import com.tebyl.aviariolocal.ui.theme.AccentRed
import com.tebyl.aviariolocal.ui.theme.BgPaper
import com.tebyl.aviariolocal.ui.theme.Caveat
import com.tebyl.aviariolocal.ui.theme.Fraunces
import com.tebyl.aviariolocal.ui.theme.Inter
import com.tebyl.aviariolocal.ui.theme.InkMute
import com.tebyl.aviariolocal.ui.theme.InkSoft
import com.tebyl.aviariolocal.ui.theme.Line
import com.tebyl.aviariolocal.ui.theme.Moss300
import com.tebyl.aviariolocal.ui.theme.Moss600
import com.tebyl.aviariolocal.ui.theme.Moss700
import com.tebyl.aviariolocal.ui.theme.Moss900
import com.tebyl.aviariolocal.viewmodel.BirdViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StatsScreen(vm: BirdViewModel) {
    val birds by vm.allBirds.collectAsState()
    val tags  by vm.allTags.collectAsState()

    val now    = Calendar.getInstance()
    val thisYear = now.get(Calendar.YEAR)
    val season = when (now.get(Calendar.MONTH)) {
        in 2..4  -> "primavera"
        in 5..7  -> "verano"
        in 8..10 -> "otoño"
        else     -> "invierno"
    }

    val birdsThisYear = birds.filter { bird ->
        Calendar.getInstance().run {
            timeInMillis = bird.dateMillis
            get(Calendar.YEAR) == thisYear
        }
    }

    val total         = birdsThisYear.size
    val uniqueSpecies = birdsThisYear.mapNotNull { it.species }.distinct().size
    val favCount      = birdsThisYear.count { it.isFavorite }
    val locationCount = birdsThisYear.map { it.locShort }.distinct().size
    val unidCount     = birdsThisYear.count { it.species == null }

    val monthlyMap = birds
        .groupBy { SimpleDateFormat("MMM yy", Locale("es")).format(Date(it.dateMillis)) }
        .entries
        .sortedBy { it.value.minOfOrNull { b -> b.dateMillis } ?: 0L }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paperBackground()
            .verticalScroll(rememberScrollState())
    ) {
        Column(modifier = Modifier.padding(horizontal = 22.dp, vertical = 18.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f).padding(end = 12.dp)) {
                    FieldLabel("temporada")
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "La temporada\nen números",
                        fontFamily = Fraunces,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 30.sp,
                        color = Moss900,
                        lineHeight = 36.sp
                    )
                }
                Stamp(text = "$season\n$thisYear", color = Moss600, rotation = -10f)
            }

            Spacer(Modifier.height(22.dp))

            // Narrative block
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = BgPaper,
                border = BorderStroke(1.dp, Line),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = buildAnnotatedString {
                            append("Este año registraste ")
                            withStyle(SpanStyle(color = Moss700, fontWeight = FontWeight.SemiBold)) {
                                append("${numToWord(total)} ${if (total == 1) "observación" else "observaciones"}")
                            }
                            append(" de ")
                            withStyle(SpanStyle(color = Moss700, fontWeight = FontWeight.SemiBold)) {
                                append("${numToWord(uniqueSpecies)} ${if (uniqueSpecies == 1) "especie" else "especies"}")
                            }
                            append(" en ")
                            withStyle(SpanStyle(color = Moss700, fontWeight = FontWeight.SemiBold)) {
                                append("${numToWord(locationCount)} ${if (locationCount == 1) "lugar" else "lugares"}")
                            }
                            append(".")
                        },
                        fontFamily = Fraunces,
                        fontSize = 16.sp,
                        color = InkSoft,
                        lineHeight = 26.sp
                    )
                    if (favCount > 0 || unidCount > 0) {
                        Spacer(Modifier.height(10.dp))
                        Text(
                            text = buildAnnotatedString {
                                if (favCount > 0) {
                                    withStyle(SpanStyle(color = AccentRed)) { append("$favCount") }
                                    append(if (favCount == 1) " favorita guardada." else " favoritas guardadas.")
                                }
                                if (unidCount > 0) {
                                    if (favCount > 0) append(" ")
                                    withStyle(SpanStyle(color = AccentGold)) { append("$unidCount") }
                                    append(if (unidCount == 1) " sin identificar todavía." else " sin identificar todavía.")
                                }
                            },
                            fontFamily = Fraunces,
                            fontSize = 14.sp,
                            color = InkSoft,
                            lineHeight = 22.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(22.dp))
            LeafDivider()
            Spacer(Modifier.height(22.dp))

            // Monthly chart
            if (monthlyMap.isNotEmpty()) {
                FieldLabel("observaciones por mes")
                Spacer(Modifier.height(14.dp))
                MonthlyChart(monthlyMap)
            }

            Spacer(Modifier.height(22.dp))
            LeafDivider()
            Spacer(Modifier.height(22.dp))

            // Tag cloud
            if (tags.isNotEmpty()) {
                FieldLabel("etiquetas más usadas")
                Spacer(Modifier.height(12.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    tags.take(12).forEachIndexed { i, tag ->
                        val fontSize = (20f - i * 1.2f).coerceAtLeast(11f).sp
                        val alpha    = (1f - i * 0.07f).coerceAtLeast(0.4f)
                        Text(
                            "#$tag",
                            fontFamily = Fraunces,
                            fontStyle = FontStyle.Italic,
                            fontSize = fontSize,
                            color = Moss600.copy(alpha = alpha)
                        )
                    }
                }
            }

            Spacer(Modifier.height(28.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    "sigue mirando despacio",
                    fontFamily = Caveat,
                    fontSize = 19.sp,
                    color = InkMute
                )
            }
            Spacer(Modifier.height(28.dp))
        }
    }
}

@Composable
private fun MonthlyChart(entries: List<Map.Entry<String, List<Bird>>>) {
    val maxCount = entries.maxOfOrNull { it.value.size } ?: 1
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        entries.forEach { (month, list) ->
            val frac = list.size.toFloat() / maxCount
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(frac)
                        .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                        .background(Moss300)
                )
                Spacer(Modifier.height(4.dp))
                Text(month, fontFamily = Inter, fontSize = 8.sp, color = InkMute)
            }
        }
    }
}

private fun numToWord(n: Int): String = when (n) {
    0  -> "ninguna"; 1  -> "una";   2  -> "dos";   3  -> "tres"
    4  -> "cuatro";  5  -> "cinco"; 6  -> "seis";  7  -> "siete"
    8  -> "ocho";    9  -> "nueve"; 10 -> "diez"
    else -> n.toString()
}
