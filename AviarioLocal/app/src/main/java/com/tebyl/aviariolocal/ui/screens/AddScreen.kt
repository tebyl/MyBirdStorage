package com.tebyl.aviariolocal.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tebyl.aviariolocal.data.Bird
import com.tebyl.aviariolocal.ui.components.AviarioChip
import com.tebyl.aviariolocal.ui.components.FieldLabel
import com.tebyl.aviariolocal.ui.components.paperBackground
import com.tebyl.aviariolocal.ui.components.ruledPaper
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
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddScreen(vm: BirdViewModel, onClose: () -> Unit) {
    var photoUri  by remember { mutableStateOf<String?>(null) }
    var species   by remember { mutableStateOf("") }
    var location  by remember { mutableStateOf("") }
    var dateStr   by remember {
        mutableStateOf(SimpleDateFormat("dd MMM yyyy", Locale("es")).format(Date()))
    }
    var behavior  by remember { mutableStateOf("") }
    var notes     by remember { mutableStateOf("") }
    var tagInput  by remember { mutableStateOf("") }
    var tags      by remember { mutableStateOf<List<String>>(emptyList()) }

    val pickPhoto = rememberLauncherForActivityResult(PickVisualMedia()) { uri ->
        uri?.let { photoUri = it.toString() }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paperBackground()
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Nueva observación",
                fontFamily = Fraunces,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                color = Moss900
            )
            IconButton(onClick = onClose) {
                Icon(Icons.Outlined.Close, "Cerrar", tint = InkSoft)
            }
        }

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            // Photo picker
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(BgPaper)
                    .border(1.dp, Line, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (photoUri != null) {
                    AsyncImage(
                        model = photoUri,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Outlined.PhotoCamera,
                            null,
                            tint = InkMute,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(Modifier.height(8.dp))
                        TextButton(onClick = {
                            pickPhoto.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
                        }) {
                            Text("Añadir foto", color = Moss600, fontFamily = Inter, fontSize = 14.sp)
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            AddField("Especie (opcional)", species, { species = it }, "Ej. Petirrojo europeo")
            Spacer(Modifier.height(14.dp))
            AddField("Lugar", location, { location = it }, "Ej. Parque del Retiro")
            Spacer(Modifier.height(14.dp))
            AddField("Fecha", dateStr, { dateStr = it }, "dd mmm yyyy")
            Spacer(Modifier.height(14.dp))
            AddField("Comportamiento", behavior, { behavior = it }, "Qué estaba haciendo…")
            Spacer(Modifier.height(14.dp))

            // Notes — ruled paper
            FieldLabel("Notas de campo")
            Spacer(Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(BgPaper)
                    .ruledPaper()
                    .border(1.dp, Line, RoundedCornerShape(10.dp))
                    .padding(4.dp)
            ) {
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 100.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    ),
                    textStyle = LocalTextStyle.current.copy(
                        fontFamily = Caveat,
                        fontSize = 17.sp,
                        color = InkSoft
                    ),
                    placeholder = {
                        Text(
                            "Escribe libremente…",
                            fontFamily = Caveat,
                            color = InkMute,
                            fontSize = 17.sp
                        )
                    },
                    minLines = 4
                )
            }

            Spacer(Modifier.height(14.dp))

            // Tags
            FieldLabel("Etiquetas")
            Spacer(Modifier.height(6.dp))
            OutlinedTextField(
                value = tagInput,
                onValueChange = { tagInput = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        "Escribe y pulsa Enter…",
                        fontFamily = Inter,
                        fontSize = 13.sp,
                        color = InkMute
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    val t = tagInput.trim().lowercase().replace(" ", "-")
                    if (t.isNotEmpty() && t !in tags) tags = tags + t
                    tagInput = ""
                }),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Moss300,
                    unfocusedBorderColor = Line
                )
            )
            if (tags.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    tags.forEach { tag ->
                        AviarioChip(label = "#$tag", active = true) {
                            tags = tags - tag
                        }
                    }
                }
            }

            Spacer(Modifier.height(28.dp))

            // Save
            Button(
                onClick = {
                    val sdf = SimpleDateFormat("dd MMM yyyy", Locale("es"))
                    val date = try { sdf.parse(dateStr)?.time ?: System.currentTimeMillis() }
                              catch (_: Exception) { System.currentTimeMillis() }
                    vm.upsertBird(
                        Bird(
                            species  = species.trim().ifEmpty { null },
                            sci      = null,
                            photoUri = photoUri ?: "",
                            dateMillis = date,
                            dateStr  = dateStr,
                            location = location.trim().ifEmpty { "Sin especificar" },
                            locShort = location.trim().take(14).ifEmpty { "Desconocido" },
                            behavior = behavior.trim(),
                            notes    = notes.trim(),
                            tags     = tags
                        )
                    )
                    onClose()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Moss700)
            ) {
                Text(
                    "Guardar observación",
                    fontFamily = Inter,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp
                )
            }
            Spacer(Modifier.height(6.dp))
            Text(
                "se guarda solo en este dispositivo",
                color = InkMute,
                fontFamily = Inter,
                fontSize = 11.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(28.dp))
        }
    }
}

@Composable
private fun AddField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    FieldLabel(label)
    Spacer(Modifier.height(4.dp))
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(placeholder, fontFamily = Inter, fontSize = 14.sp, color = InkMute)
        },
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Moss300,
            unfocusedBorderColor = Line
        )
    )
}
