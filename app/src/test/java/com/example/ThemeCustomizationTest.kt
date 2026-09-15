package com.example

import com.example.model.AccentPalette
import com.example.model.ThemeCustomization
import com.example.model.ThemeMode
import com.example.state.MediRapidViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ThemeCustomizationTest {

    @Test
    fun `default theme customization has expected defaults`() {
        val vm = MediRapidViewModel()
        assertEquals(ThemeMode.LIGHT, vm.themeCustomization.mode)
        assertEquals(AccentPalette.OCEAN_BLUE, vm.themeCustomization.palette)
        assertFalse(vm.themeCustomization.isHighContrast)
        assertEquals(1.0f, vm.themeCustomization.fontScale, 0.001f)
        assertFalse(vm.showThemeModal)
    }

    @Test
    fun `customizing theme updates viewModel state`() {
        val vm = MediRapidViewModel()
        vm.showThemeModal = true
        assertTrue(vm.showThemeModal)

        val newConfig = ThemeCustomization(
            mode = ThemeMode.DARK,
            palette = AccentPalette.EMERALD_VITALITY,
            isHighContrast = true,
            fontScale = 1.15f,
            enableDynamicColor = false
        )
        vm.themeCustomization = newConfig

        assertEquals(ThemeMode.DARK, vm.themeCustomization.mode)
        assertEquals(AccentPalette.EMERALD_VITALITY, vm.themeCustomization.palette)
        assertTrue(vm.themeCustomization.isHighContrast)
        assertEquals(1.15f, vm.themeCustomization.fontScale, 0.001f)
    }

    @Test
    fun `palette catalog contains rich healthcare themes`() {
        val palettes = AccentPalette.values()
        assertTrue(palettes.size >= 6)
        assertTrue(palettes.any { it.id == "ocean_blue" })
        assertTrue(palettes.any { it.id == "emerald" })
        assertTrue(palettes.any { it.id == "amethyst" })
        assertTrue(palettes.any { it.id == "sunset" })
        assertTrue(palettes.any { it.id == "crimson" })
        assertTrue(palettes.any { it.id == "midnight" })
    }
}
