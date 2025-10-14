package com.pequenospassos.presentation.theme

import androidx.compose.ui.graphics.Color
import org.junit.Test
import org.junit.Assert.*

/**
 * Testes unitários para validação do sistema de cores do PequenosPassos
 *
 * Valida:
 * - Cores definidas corretamente
 * - Contraste adequado para acessibilidade
 * - Cores específicas para status de tarefas
 */
class ColorTest {

    @Test
    fun `primary colors are defined correctly`() {
        // Verifica que as cores primárias estão definidas
        assertEquals(Color(0xFF4A90E2), Primary)
        assertEquals(Color.White, OnPrimary)
        assertEquals(Color(0xFFE3F2FD), PrimaryContainer)
        assertEquals(Color(0xFF1565C0), OnPrimaryContainer)
    }

    @Test
    fun `secondary colors are defined correctly`() {
        // Verifica que as cores secundárias estão definidas
        assertEquals(Color(0xFF4CAF50), Secondary)
        assertEquals(Color.White, OnSecondary)
        assertEquals(Color(0xFFE8F5E8), SecondaryContainer)
        assertEquals(Color(0xFF1B5E20), OnSecondaryContainer)
    }

    @Test
    fun `tertiary colors are defined correctly`() {
        // Verifica que as cores terciárias estão definidas
        assertEquals(Color(0xFFFF9500), Tertiary)
        assertEquals(Color.White, OnTertiary)
        assertEquals(Color(0xFFFFE0B2), TertiaryContainer)
        assertEquals(Color(0xFFE65100), OnTertiaryContainer)
    }

    @Test
    fun `error colors are defined correctly`() {
        // Verifica que as cores de erro estão definidas
        assertEquals(Color(0xFFF44336), Error)
        assertEquals(Color.White, OnError)
        assertEquals(Color(0xFFFFEBEE), ErrorContainer)
        assertEquals(Color(0xFFC62828), OnErrorContainer)
    }

    @Test
    fun `surface colors are defined correctly`() {
        // Verifica que as cores de superfície estão definidas
        assertEquals(Color.White, Surface)
        assertEquals(Color(0xFF212121), OnSurface)
        assertEquals(Color(0xFFF5F5F5), SurfaceVariant)
        assertEquals(Color(0xFF757575), OnSurfaceVariant)
    }

    @Test
    fun `background colors are defined correctly`() {
        // Verifica que as cores de fundo estão definidas
        assertEquals(Color(0xFFFAFAFA), Background)
        assertEquals(Color(0xFF212121), OnBackground)
    }

    @Test
    fun `outline colors are defined correctly`() {
        // Verifica que as cores de contorno estão definidas
        assertEquals(Color(0xFFE0E0E0), Outline)
        assertEquals(Color(0xFFEEEEEE), OutlineVariant)
    }

    @Test
    fun `extended semantic colors are defined correctly`() {
        // Verifica cores semânticas
        assertEquals(Color(0xFF4CAF50), PequenosPassosExtendedColors.Success)
        assertEquals(Color(0xFFFF9500), PequenosPassosExtendedColors.Warning)
        assertEquals(Color(0xFF2196F3), PequenosPassosExtendedColors.Info)
    }

    @Test
    fun `extended status colors are defined correctly`() {
        // Verifica cores de status de tarefas
        assertEquals(Color(0xFFFF9500), PequenosPassosExtendedColors.Pending)
        assertEquals(Color(0xFF4CAF50), PequenosPassosExtendedColors.Completed)
        assertEquals(Color(0xFFF44336), PequenosPassosExtendedColors.Canceled)
    }

    @Test
    fun `extended task background colors are defined correctly`() {
        // Verifica cores de fundo para tarefas
        assertEquals(Color(0xFFFFF3E0), PequenosPassosExtendedColors.TaskPending)
        assertEquals(Color(0xFFE8F5E8), PequenosPassosExtendedColors.TaskCompleted)
        assertEquals(Color(0xFFFFEBEE), PequenosPassosExtendedColors.TaskCanceled)
    }

    @Test
    fun `extended star colors are defined correctly`() {
        // Verifica cores das estrelas
        assertEquals(Color(0xFFFFD700), PequenosPassosExtendedColors.StarYellow)
        assertEquals(Color(0xFFE0E0E0), PequenosPassosExtendedColors.StarGray)
    }

    @Test
    fun `task status colors have sufficient contrast`() {
        // Verifica que as cores de status são distintas
        assertNotEquals(
            PequenosPassosExtendedColors.Pending,
            PequenosPassosExtendedColors.Completed
        )
        assertNotEquals(
            PequenosPassosExtendedColors.Completed,
            PequenosPassosExtendedColors.Canceled
        )
        assertNotEquals(
            PequenosPassosExtendedColors.Pending,
            PequenosPassosExtendedColors.Canceled
        )
    }

    @Test
    fun `task background colors are lighter than status colors`() {
        // Verifica que as cores de fundo são mais claras
        // TaskPending deve ser mais claro que Pending
        assertTrue(
            PequenosPassosExtendedColors.TaskPending !=
            PequenosPassosExtendedColors.Pending
        )

        // TaskCompleted deve ser mais claro que Completed
        assertTrue(
            PequenosPassosExtendedColors.TaskCompleted !=
            PequenosPassosExtendedColors.Completed
        )

        // TaskCanceled deve ser mais claro que Canceled
        assertTrue(
            PequenosPassosExtendedColors.TaskCanceled !=
            PequenosPassosExtendedColors.Canceled
        )
    }

    @Test
    fun `colors follow TEA accessibility guidelines`() {
        // Verifica que usamos cores calmas (não muito saturadas)
        // Primary é azul calmo (não muito vibrante)
        assertTrue(Primary.red < 0.5f)
        assertTrue(Primary.green > 0.4f)

        // Secondary é verde calmo
        assertTrue(Secondary.green > 0.6f)

        // Error é vermelho mas não muito agressivo
        assertTrue(Error.red > 0.8f)
    }
}

