package com.geekementvotre.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = GeekGold,
    secondary = GeekGold,
    tertiary = GeekGold,
    background = GeekBlack,
    surface = GeekDarkGray,
    onPrimary = GeekBlack,
    onSecondary = GeekBlack,
    onTertiary = GeekBlack,
    onBackground = GeekWhite,
    onSurface = GeekWhite,
)

private val LightColorScheme = lightColorScheme(
    primary = GeekGold,
    secondary = GeekGold,
    tertiary = GeekGold,
    background = GeekBlack,
    surface = GeekDarkGray,
    onPrimary = GeekBlack,
    onSecondary = GeekBlack,
    onTertiary = GeekBlack,
    onBackground = GeekWhite,
    onSurface = GeekWhite,
)

@Composable
fun GeekementvotreTheme(
    darkTheme: Boolean = true, // On force le thème sombre pour correspondre à la maquette
    dynamicColor: Boolean = false, // On désactive les couleurs dynamiques pour garder notre identité Gold
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}