package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = PureWhite,
    primaryContainer = Color(0xFFE3F2FD),
    onPrimaryContainer = PrimaryBlueDark,
    secondary = AccentBlue,
    onSecondary = PureWhite,
    secondaryContainer = Color(0xFFE0F2FE),
    onSecondaryContainer = Color(0xFF0369A1),
    background = BackgroundLight,
    onBackground = TextPrimaryBlack,
    surface = SurfaceCard,
    onSurface = TextPrimaryBlack,
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = TextSecondaryGray,
    outline = SurfaceCardBorder,
    error = StatusError,
    onError = PureWhite
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlueLight,
    onPrimary = PureWhite,
    primaryContainer = PrimaryBlueDark,
    onPrimaryContainer = Color(0xFFBBDEFB),
    secondary = AccentBlue,
    onSecondary = PureWhite,
    secondaryContainer = Color(0xFF075985),
    onSecondaryContainer = Color(0xFFE0F2FE),
    background = Color(0xFF0F172A),
    onBackground = Color(0xFFF8FAFC),
    surface = Color(0xFF1E293B),
    onSurface = Color(0xFFF8FAFC),
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = Color(0xFFCBD5E1),
    outline = Color(0xFF475569),
    error = Color(0xFFEF4444),
    onError = PureWhite
)

@Composable
fun PhoneTrafficTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // User requested Blue, White, Black text style
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides AppTypography.bodyMedium
        ) {
            content()
        }
    }
}
