package com.tebyl.aviariolocal.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val AviarioColorScheme = lightColorScheme(
    primary          = Moss700,
    onPrimary        = BgWarm,
    primaryContainer = Moss100,
    onPrimaryContainer = Moss900,
    secondary        = Bark500,
    onSecondary      = BgWarm,
    background       = BgCream,
    onBackground     = InkPrimary,
    surface          = BgWarm,
    onSurface        = InkPrimary,
    surfaceVariant   = BgPaper,
    onSurfaceVariant = InkSoft,
    outline          = Line,
    error            = AccentRed,
    onError          = BgWarm
)

@Composable
fun AviarioTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AviarioColorScheme,
        typography  = AviarioTypography,
        content     = content
    )
}
