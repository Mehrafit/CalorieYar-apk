package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

private val CalorieYarDarkColorScheme = darkColorScheme(
    primary = MintGreenPrimary,
    onPrimary = Color(0xFF042F1A),
    primaryContainer = DarkSurfaceCard,
    onPrimaryContainer = TextLightGreen,
    secondary = SecondaryGold,
    onSecondary = Color(0xFF2E1906),
    secondaryContainer = Color(0xFF382500),
    onSecondaryContainer = SecondaryGold,
    tertiary = WaterBlue,
    onTertiary = Color.White,
    background = DarkBackgroundFitness,
    onBackground = Color(0xFFF0FDF4),
    surface = DarkSurfaceForest,
    onSurface = Color(0xFFF0FDF4),
    surfaceVariant = DarkSurfaceCard,
    onSurfaceVariant = TextMuted,
    outline = CardBorderGreen
)

private val CalorieYarLightColorScheme = lightColorScheme(
    primary = MintGreenPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDCFCE7),
    onPrimaryContainer = ForestGreenDark,
    secondary = AccentOrange,
    onSecondary = Color.White,
    tertiary = WaterBlue,
    onTertiary = Color.White,
    background = Color(0xFFF7FDF9),
    onBackground = Color(0xFF052E16),
    surface = Color.White,
    onSurface = Color(0xFF052E16),
    surfaceVariant = Color(0xFFE8F5E9),
    onSurfaceVariant = Color(0xFF374151),
    outline = Color(0xFFBBEFCE)
)

@Composable
fun CalorieYarTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) CalorieYarDarkColorScheme else CalorieYarLightColorScheme

    // Full RTL (Right to Left) for Persian language support
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography,
            content = content
        )
    }
}
