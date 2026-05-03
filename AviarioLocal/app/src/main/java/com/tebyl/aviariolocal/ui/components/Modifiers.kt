package com.tebyl.aviariolocal.ui.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.tebyl.aviariolocal.ui.theme.Bark500
import com.tebyl.aviariolocal.ui.theme.BgCream

fun Modifier.paperBackground(): Modifier = this.drawBehind {
    drawRect(BgCream)
    val dotColor = Bark500.copy(alpha = 0.04f)
    val step = 18.dp.toPx()
    val cols = (size.width / step).toInt() + 1
    val rows = (size.height / step).toInt() + 1
    for (x in 0..cols) {
        for (y in 0..rows) {
            drawCircle(dotColor, 1f, Offset(x * step, y * step))
        }
    }
}

fun Modifier.ruledPaper(): Modifier = this.drawBehind {
    val lineColor = Bark500.copy(alpha = 0.12f)
    val step = 24.dp.toPx()
    var y = step
    while (y < size.height) {
        drawLine(lineColor, Offset(0f, y), Offset(size.width, y), strokeWidth = 1f)
        y += step
    }
}
