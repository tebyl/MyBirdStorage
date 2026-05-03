package com.tebyl.aviariolocal.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.tebyl.aviariolocal.ui.theme.Line
import com.tebyl.aviariolocal.ui.theme.Moss300

@Composable
fun LeafDivider(modifier: Modifier = Modifier) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(20.dp)
    ) {
        val cy = size.height / 2f
        val cx = size.width / 2f
        val gap = 20.dp.toPx()
        val lw = 12.dp.toPx()
        val lh = 6.dp.toPx()

        drawLine(Line, Offset(0f, cy), Offset(cx - gap, cy), strokeWidth = 1f)
        drawLine(Line, Offset(cx + gap, cy), Offset(size.width, cy), strokeWidth = 1f)

        val leafPath = Path().apply {
            moveTo(cx, cy - lh)
            cubicTo(cx + lw, cy - lh / 2f, cx + lw, cy + lh / 2f, cx, cy + lh)
            cubicTo(cx - lw, cy + lh / 2f, cx - lw, cy - lh / 2f, cx, cy - lh)
            close()
        }
        drawPath(leafPath, Moss300.copy(alpha = 0.35f))
        drawPath(leafPath, Moss300, style = Stroke(width = 1f))
    }
}
