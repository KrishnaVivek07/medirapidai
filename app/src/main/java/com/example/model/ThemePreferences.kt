package com.example.model

import androidx.compose.ui.graphics.Color

enum class ThemeMode(val displayName: String, val description: String) {
    SYSTEM("System Default", "Matches your Android device settings"),
    LIGHT("Light Mode", "Bright, clean clinical layout"),
    DARK("Dark Mode", "Eye-safe midnight OLED palette")
}

enum class AccentPalette(
    val id: String,
    val title: String,
    val subtitle: String,
    val primary: Color,
    val primaryDark: Color,
    val primaryLight: Color,
    val secondary: Color,
    val accent: Color
) {
    OCEAN_BLUE(
        id = "ocean_blue",
        title = "Ocean Clinical",
        subtitle = "Sky blue & clean cyan",
        primary = Color(0xFF0284C7),
        primaryDark = Color(0xFF0369A1),
        primaryLight = Color(0xFFE0F2FE),
        secondary = Color(0xFF06B6D4),
        accent = Color(0xFF38BDF8)
    ),
    EMERALD_VITALITY(
        id = "emerald",
        title = "Emerald Vitality",
        subtitle = "Botanical wellness & mint green",
        primary = Color(0xFF059669),
        primaryDark = Color(0xFF047857),
        primaryLight = Color(0xFFD1FAE5),
        secondary = Color(0xFF10B981),
        accent = Color(0xFF34D399)
    ),
    ROYAL_AMETHYST(
        id = "amethyst",
        title = "Royal Amethyst",
        subtitle = "Modern digital purple healthcare",
        primary = Color(0xFF7C3AED),
        primaryDark = Color(0xFF6D28D9),
        primaryLight = Color(0xFFEDE9FE),
        secondary = Color(0xFF8B5CF6),
        accent = Color(0xFFA78BFA)
    ),
    SUNSET_CORAL(
        id = "sunset",
        title = "Sunset Coral",
        subtitle = "Warm amber & high contrast",
        primary = Color(0xFFEA580C),
        primaryDark = Color(0xFFC2410C),
        primaryLight = Color(0xFFFFEDD5),
        secondary = Color(0xFFF97316),
        accent = Color(0xFFFB923C)
    ),
    CLINICAL_CRIMSON(
        id = "crimson",
        title = "Emergency Crimson",
        subtitle = "Urgent response ruby triage",
        primary = Color(0xFFDC2626),
        primaryDark = Color(0xFFB91C1C),
        primaryLight = Color(0xFFFEE2E2),
        secondary = Color(0xFFEF4444),
        accent = Color(0xFFF87171)
    ),
    MIDNIGHT_CYBER(
        id = "midnight",
        title = "Midnight Slate",
        subtitle = "Deep contrast slate & cyan",
        primary = Color(0xFF334155),
        primaryDark = Color(0xFF1E293B),
        primaryLight = Color(0xFFE2E8F0),
        secondary = Color(0xFF38BDF8),
        accent = Color(0xFF7DD3FC)
    ),
    TEAL_HEALING(
        id = "teal",
        title = "Teal Healing",
        subtitle = "Calm aquatic clinical teal",
        primary = Color(0xFF0D9488),
        primaryDark = Color(0xFF0F766E),
        primaryLight = Color(0xFFCCFBF1),
        secondary = Color(0xFF14B8A6),
        accent = Color(0xFF2DD4BF)
    )
}

data class ThemeCustomization(
    val mode: ThemeMode = ThemeMode.LIGHT,
    val palette: AccentPalette = AccentPalette.OCEAN_BLUE,
    val isHighContrast: Boolean = false,
    val fontScale: Float = 1.0f,
    val enableDynamicColor: Boolean = false
)
