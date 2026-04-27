package com.geekementvotre.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.geekementvotre.R

// --- DÉFINITION DES FAMILLES DE POLICES (LOCALES) ---

// Playfair Display
val PlayfairDisplayFontFamily = FontFamily(
    Font(R.font.playfairdisplay_regular, weight = FontWeight.Normal),
    Font(R.font.playfairdisplay_medium, weight = FontWeight.Medium),
    Font(R.font.playfairdisplay_semibold, weight = FontWeight.SemiBold),
    Font(R.font.playfairdisplay_bold, weight = FontWeight.Bold),
    Font(R.font.playfairdisplay_extrabold, weight = FontWeight.ExtraBold),
    Font(R.font.playfairdisplay_black, weight = FontWeight.Black)
)

// DM Sans
val DMSansFontFamily = FontFamily(
    Font(R.font.dmsans_regular, weight = FontWeight.Normal),
    Font(R.font.dmsans_medium, weight = FontWeight.Medium),
    Font(R.font.dmsans_semibold, weight = FontWeight.SemiBold),
    Font(R.font.dmsans_bold, weight = FontWeight.Bold),
    Font(R.font.dmsans_extrabold, weight = FontWeight.ExtraBold),
    Font(R.font.dmsans_black, weight = FontWeight.Black)
)

// Montserrat
val MontserratFontFamily = FontFamily(
    Font(R.font.montserrat_regular, weight = FontWeight.Normal),
    Font(R.font.montserrat_medium, weight = FontWeight.Medium),
    Font(R.font.montserrat_semibold, weight = FontWeight.SemiBold),
    Font(R.font.montserrat_bold, weight = FontWeight.Bold),
    Font(R.font.montserrat_extrabold, weight = FontWeight.ExtraBold),
    Font(R.font.montserrat_black, weight = FontWeight.Black)
)

// --- CONFIGURATION DE LA TYPOGRAPHIE ---

val Typography = Typography(
    // Titres Premium (Serif)
    displayLarge = TextStyle(
        fontFamily = PlayfairDisplayFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 36.sp,
        letterSpacing = 1.sp
    ),
    displayMedium = TextStyle(
        fontFamily = PlayfairDisplayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),
    displaySmall = TextStyle(
        fontFamily = PlayfairDisplayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    
    // Titres de sections et cartes (Sans-Serif)
    titleLarge = TextStyle(
        fontFamily = DMSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = DMSansFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    ),
    titleSmall = TextStyle(
        fontFamily = DMSansFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    
    // Corps de texte
    bodyLarge = TextStyle(
        fontFamily = DMSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = DMSansFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    
    // Boutons et labels (Montserrat)
    labelLarge = TextStyle(
        fontFamily = MontserratFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        letterSpacing = 0.5.sp
    ),
    labelMedium = TextStyle(
        fontFamily = MontserratFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = MontserratFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp
    )
)
