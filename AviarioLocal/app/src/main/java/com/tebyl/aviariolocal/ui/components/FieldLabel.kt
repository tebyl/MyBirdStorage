package com.tebyl.aviariolocal.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.tebyl.aviariolocal.ui.theme.Inter
import com.tebyl.aviariolocal.ui.theme.InkMute

@Composable
fun FieldLabel(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text.uppercase(),
        color = InkMute,
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        letterSpacing = 1.6.sp,
        modifier = modifier
    )
}
