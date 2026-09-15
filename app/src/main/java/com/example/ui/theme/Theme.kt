package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.model.AccentPalette
import com.example.model.ThemeCustomization
import com.example.model.ThemeMode

val LocalThemeCustomization = compositionLocalOf { ThemeCustomization() }

private val DefaultDarkColorScheme =
  darkColorScheme(
    primary = Purple80,
    secondary = MediCyanAccent,
    tertiary = Pink80,
    background = Color(0xFF090D16),
    surface = Color(0xFF131D31),
    surfaceVariant = Color(0xFF1E293B),
    onPrimary = Color.Black,
    onBackground = Color(0xFFF1F5F9),
    onSurface = Color(0xFFF1F5F9)
  )

private val DefaultLightColorScheme =
  lightColorScheme(
    primary = MediBluePrimary,
    secondary = MediCyanAccent,
    tertiary = EmergencyRed,
    background = Slate50,
    surface = Color.White,
    surfaceVariant = Slate100,
    onPrimary = Color.White,
    onBackground = Slate900,
    onSurface = Slate900
  )

@Composable
fun MyApplicationTheme(
    customization: ThemeCustomization = ThemeCustomization(),
    darkTheme: Boolean = when (customization.mode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    },
    dynamicColor: Boolean = customization.enableDynamicColor,
    content: @Composable () -> Unit,
) {
    val palette = customization.palette
    val highContrast = customization.isHighContrast

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> {
            darkColorScheme(
                primary = palette.primary,
                onPrimary = Color.White,
                primaryContainer = palette.primaryDark,
                onPrimaryContainer = palette.primaryLight,
                secondary = palette.secondary,
                onSecondary = Color.White,
                secondaryContainer = Color(0xFF1E293B),
                onSecondaryContainer = palette.accent,
                tertiary = palette.accent,
                onTertiary = Color.Black,
                background = if (highContrast) Color.Black else Color(0xFF090D16),
                onBackground = Color(0xFFF1F5F9),
                surface = if (highContrast) Color(0xFF121212) else Color(0xFF131D31),
                onSurface = Color(0xFFF1F5F9),
                surfaceVariant = if (highContrast) Color(0xFF1E1E1E) else Color(0xFF1E293B),
                onSurfaceVariant = Color(0xFF94A3B8),
                outline = if (highContrast) Color(0xFFCBD5E1) else Color(0xFF334155)
            )
        }
        else -> {
            lightColorScheme(
                primary = palette.primary,
                onPrimary = Color.White,
                primaryContainer = palette.primaryLight,
                onPrimaryContainer = palette.primaryDark,
                secondary = palette.secondary,
                onSecondary = Color.White,
                secondaryContainer = palette.primaryLight.copy(alpha = 0.5f),
                onSecondaryContainer = palette.primaryDark,
                tertiary = EmergencyRed,
                onTertiary = Color.White,
                background = if (highContrast) Color.White else Slate50,
                onBackground = Slate900,
                surface = Color.White,
                onSurface = Slate900,
                surfaceVariant = if (highContrast) Color(0xFFF8FAFC) else Slate100,
                onSurfaceVariant = Slate700,
                outline = if (highContrast) Slate900 else Slate200
            )
        }
    }

    CompositionLocalProvider(
        LocalThemeCustomization provides customization
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
