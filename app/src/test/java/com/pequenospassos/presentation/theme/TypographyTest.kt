package com.pequenospassos.presentation.theme

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.junit.Test
import org.junit.Assert.*

/**
 * Testes unitários para validação da tipografia do PequenosPassos
 *
 * Valida:
 * - Hierarquia de tamanhos de fonte
 * - Pesos de fonte adequados
 * - Line heights apropriados
 * - Legibilidade para crianças com TEA
 */
class TypographyTest {

    private val typography = PequenosPassosTypography

    @Test
    fun `display styles use correct font sizes`() {
        // Display Large
        assertEquals(32.sp, typography.displayLarge.fontSize)
        assertEquals(40.sp, typography.displayLarge.lineHeight)
        assertEquals(FontWeight.Bold, typography.displayLarge.fontWeight)

        // Display Medium
        assertEquals(28.sp, typography.displayMedium.fontSize)
        assertEquals(36.sp, typography.displayMedium.lineHeight)
        assertEquals(FontWeight.Bold, typography.displayMedium.fontWeight)

        // Display Small
        assertEquals(24.sp, typography.displaySmall.fontSize)
        assertEquals(32.sp, typography.displaySmall.lineHeight)
        assertEquals(FontWeight.Bold, typography.displaySmall.fontWeight)
    }

    @Test
    fun `headline styles use correct font sizes`() {
        // Headline Large
        assertEquals(24.sp, typography.headlineLarge.fontSize)
        assertEquals(32.sp, typography.headlineLarge.lineHeight)
        assertEquals(FontWeight.Bold, typography.headlineLarge.fontWeight)

        // Headline Medium
        assertEquals(20.sp, typography.headlineMedium.fontSize)
        assertEquals(28.sp, typography.headlineMedium.lineHeight)
        assertEquals(FontWeight.Bold, typography.headlineMedium.fontWeight)

        // Headline Small
        assertEquals(18.sp, typography.headlineSmall.fontSize)
        assertEquals(24.sp, typography.headlineSmall.lineHeight)
        assertEquals(FontWeight.SemiBold, typography.headlineSmall.fontWeight)
    }

    @Test
    fun `title styles use correct font sizes`() {
        // Title Large
        assertEquals(20.sp, typography.titleLarge.fontSize)
        assertEquals(28.sp, typography.titleLarge.lineHeight)
        assertEquals(FontWeight.SemiBold, typography.titleLarge.fontWeight)

        // Title Medium
        assertEquals(18.sp, typography.titleMedium.fontSize)
        assertEquals(24.sp, typography.titleMedium.lineHeight)
        assertEquals(FontWeight.SemiBold, typography.titleMedium.fontWeight)

        // Title Small
        assertEquals(16.sp, typography.titleSmall.fontSize)
        assertEquals(20.sp, typography.titleSmall.lineHeight)
        assertEquals(FontWeight.Medium, typography.titleSmall.fontWeight)
    }

    @Test
    fun `body styles use correct font sizes`() {
        // Body Large
        assertEquals(16.sp, typography.bodyLarge.fontSize)
        assertEquals(24.sp, typography.bodyLarge.lineHeight)
        assertEquals(FontWeight.Normal, typography.bodyLarge.fontWeight)

        // Body Medium
        assertEquals(14.sp, typography.bodyMedium.fontSize)
        assertEquals(20.sp, typography.bodyMedium.lineHeight)
        assertEquals(FontWeight.Normal, typography.bodyMedium.fontWeight)

        // Body Small
        assertEquals(12.sp, typography.bodySmall.fontSize)
        assertEquals(16.sp, typography.bodySmall.lineHeight)
        assertEquals(FontWeight.Normal, typography.bodySmall.fontWeight)
    }

    @Test
    fun `label styles use correct font sizes`() {
        // Label Large
        assertEquals(14.sp, typography.labelLarge.fontSize)
        assertEquals(20.sp, typography.labelLarge.lineHeight)
        assertEquals(FontWeight.Medium, typography.labelLarge.fontWeight)

        // Label Medium
        assertEquals(12.sp, typography.labelMedium.fontSize)
        assertEquals(16.sp, typography.labelMedium.lineHeight)
        assertEquals(FontWeight.Medium, typography.labelMedium.fontWeight)

        // Label Small
        assertEquals(11.sp, typography.labelSmall.fontSize)
        assertEquals(16.sp, typography.labelSmall.lineHeight)
        assertEquals(FontWeight.Medium, typography.labelSmall.fontWeight)
    }

    @Test
    fun `all styles use default font family`() {
        // Verifica que todos os estilos usam FontFamily.Default
        assertEquals(FontFamily.Default, typography.displayLarge.fontFamily)
        assertEquals(FontFamily.Default, typography.headlineLarge.fontFamily)
        assertEquals(FontFamily.Default, typography.titleLarge.fontFamily)
        assertEquals(FontFamily.Default, typography.bodyLarge.fontFamily)
        assertEquals(FontFamily.Default, typography.labelLarge.fontFamily)
    }

    @Test
    fun `line height is greater than font size for readability`() {
        // Verifica que line height é sempre maior que fontSize para legibilidade
        assertTrue(typography.displayLarge.lineHeight > typography.displayLarge.fontSize)
        assertTrue(typography.headlineLarge.lineHeight > typography.headlineLarge.fontSize)
        assertTrue(typography.titleLarge.lineHeight > typography.titleLarge.fontSize)
        assertTrue(typography.bodyLarge.lineHeight > typography.bodyLarge.fontSize)
        assertTrue(typography.labelLarge.lineHeight > typography.labelLarge.fontSize)
    }

    @Test
    fun `font sizes follow hierarchical order`() {
        // Display > Headline > Title > Body > Label
        assertTrue(typography.displayLarge.fontSize > typography.headlineLarge.fontSize)
        assertTrue(typography.headlineLarge.fontSize >= typography.titleLarge.fontSize)
        assertTrue(typography.titleLarge.fontSize > typography.bodyLarge.fontSize)
        assertTrue(typography.bodyLarge.fontSize > typography.labelLarge.fontSize)
    }

    @Test
    fun `minimum font size is readable for children with TEA`() {
        // Verifica que o menor tamanho de fonte (labelSmall) é >= 11sp
        // Fonte muito pequena dificulta leitura para crianças
        assertTrue(typography.labelSmall.fontSize.value >= 11f)
    }

    @Test
    fun `display styles use bold weight for emphasis`() {
        // Displays devem usar Bold para criar hierarquia visual forte
        assertEquals(FontWeight.Bold, typography.displayLarge.fontWeight)
        assertEquals(FontWeight.Bold, typography.displayMedium.fontWeight)
        assertEquals(FontWeight.Bold, typography.displaySmall.fontWeight)
    }

    @Test
    fun `body styles use normal weight for readability`() {
        // Body text deve usar peso normal para não cansar a leitura
        assertEquals(FontWeight.Normal, typography.bodyLarge.fontWeight)
        assertEquals(FontWeight.Normal, typography.bodyMedium.fontWeight)
        assertEquals(FontWeight.Normal, typography.bodySmall.fontWeight)
    }
}

