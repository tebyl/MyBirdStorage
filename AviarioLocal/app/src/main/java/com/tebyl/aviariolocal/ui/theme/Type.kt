package com.tebyl.aviariolocal.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.tebyl.aviariolocal.R

val fontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val Fraunces = FontFamily(
    Font(GoogleFont("Fraunces"), fontProvider, weight = FontWeight.Normal),
    Font(GoogleFont("Fraunces"), fontProvider, weight = FontWeight.SemiBold),
    Font(GoogleFont("Fraunces"), fontProvider, weight = FontWeight.Bold)
)

val Inter = FontFamily(
    Font(GoogleFont("Inter"), fontProvider, weight = FontWeight.Normal),
    Font(GoogleFont("Inter"), fontProvider, weight = FontWeight.Medium),
    Font(GoogleFont("Inter"), fontProvider, weight = FontWeight.SemiBold)
)

val Caveat = FontFamily(
    Font(GoogleFont("Caveat"), fontProvider, weight = FontWeight.Normal),
    Font(GoogleFont("Caveat"), fontProvider, weight = FontWeight.Medium)
)

val AviarioTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = Fraunces,
        fontWeight = FontWeight.SemiBold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = (-0.7).sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Fraunces,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Fraunces,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 1.6.sp
    )
)
