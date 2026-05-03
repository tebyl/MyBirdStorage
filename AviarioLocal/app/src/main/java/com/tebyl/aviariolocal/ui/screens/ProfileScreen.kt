package com.tebyl.aviariolocal.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tebyl.aviariolocal.ui.components.FieldLabel
import com.tebyl.aviariolocal.ui.components.paperBackground
import com.tebyl.aviariolocal.ui.theme.BgPaper
import com.tebyl.aviariolocal.ui.theme.Fraunces
import com.tebyl.aviariolocal.ui.theme.Inter
import com.tebyl.aviariolocal.ui.theme.InkMute
import com.tebyl.aviariolocal.ui.theme.InkSoft
import com.tebyl.aviariolocal.ui.theme.Line
import com.tebyl.aviariolocal.ui.theme.Moss100
import com.tebyl.aviariolocal.ui.theme.Moss600
import com.tebyl.aviariolocal.ui.theme.Moss700
import com.tebyl.aviariolocal.ui.theme.Moss900
import com.tebyl.aviariolocal.viewmodel.BirdViewModel

@Composable
fun ProfileScreen(vm: BirdViewModel) {
    val birds by vm.allBirds.collectAsState()
    val total          = birds.size
    val uniqueSpecies  = birds.mapNotNull { it.species }.distinct().size
    val locationCount  = birds.map { it.locShort }.distinct().size
    val favCount       = birds.count { it.isFavorite }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paperBackground()
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
            FieldLabel("biblioteca personal")
            Spacer(Modifier.height(6.dp))
            Text(
                "Cuaderno de campo",
                fontFamily = Fraunces,
                fontWeight = FontWeight.SemiBold,
                fontSize = 26.sp,
                color = Moss900
            )
        }

        // Identity card
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            color = BgPaper,
            border = BorderStroke(1.dp, Line)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = Moss100,
                    modifier = Modifier.size(52.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Outlined.Person,
                            contentDescription = null,
                            tint = Moss600,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
                Column {
                    Text(
                        "Ornitólogo local",
                        fontFamily = Fraunces,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Moss900
                    )
                    Text(
                        "Aficionado a la observación de aves",
                        fontFamily = Inter,
                        fontSize = 12.sp,
                        color = InkMute
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Stats grid
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatBox("fotos",    "$total",          Modifier.weight(1f))
            StatBox("especies", "$uniqueSpecies",  Modifier.weight(1f))
            StatBox("lugares",  "$locationCount",  Modifier.weight(1f))
        }

        Spacer(Modifier.height(20.dp))

        // Tu archivo section
        ProfileSection(title = "Tu archivo") {
            ProfileRow(label = "Observaciones",   value = "$total")
            ProfileRow(label = "Identificadas",   value = "$uniqueSpecies")
            ProfileRow(label = "Favoritas",        value = "$favCount")
            ProfileRow(label = "Sin identificar",  value = "${birds.count { it.species == null }}")
        }

        Spacer(Modifier.height(10.dp))

        ProfileSection(title = "Opciones") {
            ProfileRow(label = "Exportar datos",  value = "")
            ProfileRow(label = "Importar datos",  value = "")
            ProfileRow(label = "Apariencia",      value = "")
        }

        Spacer(Modifier.height(16.dp))

        // Privacy note
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            color = Moss100.copy(alpha = 0.45f),
            border = BorderStroke(1.dp, Line)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Sobre MyBirdStorage",
                    fontFamily = Inter,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    color = Moss700
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    "Todos tus datos se guardan únicamente en este dispositivo. Sin cuentas, sin servidores, sin seguimiento.",
                    fontFamily = Inter,
                    fontSize = 12.sp,
                    color = InkMute,
                    lineHeight = 18.sp
                )
            }
        }

        Spacer(Modifier.height(28.dp))
    }
}

@Composable
private fun StatBox(label: String, value: String, modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = BgPaper,
        border = BorderStroke(1.dp, Line),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                value,
                fontFamily = Fraunces,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                color = Moss700
            )
            Text(
                label,
                fontFamily = Inter,
                fontSize = 10.sp,
                color = InkMute,
                letterSpacing = 0.8.sp
            )
        }
    }
}

@Composable
private fun ProfileSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        FieldLabel(title)
        Spacer(Modifier.height(6.dp))
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = BgPaper,
            border = BorderStroke(1.dp, Line),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(content = content)
        }
    }
}

@Composable
private fun ProfileRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 16.dp, vertical = 13.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontFamily = Inter, fontSize = 14.sp, color = InkSoft)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (value.isNotEmpty()) {
                Text(
                    value,
                    fontFamily = Fraunces,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Moss700
                )
            }
            Icon(Icons.Outlined.ChevronRight, null, tint = InkMute, modifier = Modifier.size(16.dp))
        }
    }
    HorizontalDivider(color = Line, thickness = 0.5.dp, modifier = Modifier.padding(horizontal = 16.dp))
}
