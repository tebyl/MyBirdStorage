package com.tebyl.aviariolocal.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tebyl.aviariolocal.ui.theme.AccentRed
import com.tebyl.aviariolocal.ui.theme.Fraunces

@Composable
fun Stamp(
    text: String,
    color: Color = AccentRed,
    rotation: Float = -8f,
    size: Dp = 60.dp
) {
    Box(
        modifier = Modifier
            .size(size)
            .rotate(rotation)
            .border(1.5.dp, color, CircleShape)
            .padding(6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = color,
            fontFamily = Fraunces,
            fontSize = 8.sp,
            letterSpacing = 1.sp,
            textAlign = TextAlign.Center,
            lineHeight = 10.sp
        )
    }
}
