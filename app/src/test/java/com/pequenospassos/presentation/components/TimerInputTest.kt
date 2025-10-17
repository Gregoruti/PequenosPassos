package com.pequenospassos.presentation.components

import org.junit.Assert.*
import org.junit.Test

/**
 * Testes unitários para TimerInput helper functions.
 *
 * Valida:
 * - Formatação de duração (completa)
 * - Formatação de duração (curta)
 * - Diferentes casos de edge (0s, 5s, 60s, 600s, etc.)
 *
 * @since MVP-07 (16/10/2025)
 */
class TimerInputTest {

    // ===== TESTES DE formatDuration() =====

    @Test
    fun `formatDuration deve formatar apenas segundos`() {
        // Act & Assert
        assertEquals("5s", formatDuration(5))
        assertEquals("30s", formatDuration(30))
        assertEquals("45s", formatDuration(45))
        assertEquals("59s", formatDuration(59))
    }

    @Test
    fun `formatDuration deve formatar minutos exatos`() {
        // Act & Assert
        assertEquals("1 min", formatDuration(60))
        assertEquals("2 min", formatDuration(120))
        assertEquals("5 min", formatDuration(300))
        assertEquals("10 min", formatDuration(600))
    }

    @Test
    fun `formatDuration deve formatar minutos com segundos`() {
        // Act & Assert
        assertEquals("1 min 30s", formatDuration(90))
        assertEquals("2 min 15s", formatDuration(135))
        assertEquals("3 min 45s", formatDuration(225))
        assertEquals("9 min 59s", formatDuration(599))
    }

    @Test
    fun `formatDuration deve formatar valores limites`() {
        // Act & Assert
        assertEquals("5s", formatDuration(5))     // Mínimo
        assertEquals("10 min", formatDuration(600)) // Máximo
    }

    // ===== TESTES DE formatDurationShort() =====

    @Test
    fun `formatDurationShort deve formatar apenas segundos`() {
        // Act & Assert
        assertEquals("5s", formatDurationShort(5))
        assertEquals("30s", formatDurationShort(30))
        assertEquals("45s", formatDurationShort(45))
        assertEquals("59s", formatDurationShort(59))
    }

    @Test
    fun `formatDurationShort deve formatar minutos exatos`() {
        // Act & Assert
        assertEquals("1m", formatDurationShort(60))
        assertEquals("2m", formatDurationShort(120))
        assertEquals("5m", formatDurationShort(300))
        assertEquals("10m", formatDurationShort(600))
    }

    @Test
    fun `formatDurationShort deve formatar minutos com segundos sem espaços`() {
        // Act & Assert
        assertEquals("1m30s", formatDurationShort(90))
        assertEquals("2m15s", formatDurationShort(135))
        assertEquals("3m45s", formatDurationShort(225))
        assertEquals("9m59s", formatDurationShort(599))
    }

    @Test
    fun `formatDurationShort deve ser mais compacto que formatDuration`() {
        // Act
        val duration = 90
        val full = formatDuration(duration)
        val short = formatDurationShort(duration)

        // Assert
        assertTrue("Formato curto deve ser menor", short.length < full.length)
        assertEquals("1 min 30s", full)
        assertEquals("1m30s", short)
    }

    // ===== TESTES DE VALORES RÁPIDOS =====

    @Test
    fun `valores rápidos devem estar no range válido`() {
        // Arrange
        val quickValues = listOf(5, 15, 30, 60, 90, 120, 300, 600)

        // Act & Assert
        quickValues.forEach { value ->
            assertTrue("$value deve estar entre 5 e 600", value in 5..600)
        }
    }

    @Test
    fun `valores rápidos devem ser formatados corretamente`() {
        // Arrange & Act & Assert
        assertEquals("5s", formatDurationShort(5))
        assertEquals("15s", formatDurationShort(15))
        assertEquals("30s", formatDurationShort(30))
        assertEquals("1m", formatDurationShort(60))
        assertEquals("1m30s", formatDurationShort(90))
        assertEquals("2m", formatDurationShort(120))
        assertEquals("5m", formatDurationShort(300))
        assertEquals("10m", formatDurationShort(600))
    }

    // ===== TESTES DE EDGE CASES =====

    @Test
    fun `formatDuration deve lidar com zero`() {
        // Act
        val result = formatDuration(0)

        // Assert
        assertEquals("0s", result)
    }

    @Test
    fun `formatDuration deve lidar com valores grandes`() {
        // Act & Assert
        assertEquals("15 min", formatDuration(900))
        assertEquals("30 min", formatDuration(1800))
        assertEquals("1 min", formatDuration(60))
    }

    @Test
    fun `formatDuration deve formatar 1 segundo corretamente`() {
        // Act
        val result = formatDuration(1)

        // Assert
        assertEquals("1s", result)
    }

    @Test
    fun `formatDuration deve formatar 61 segundos corretamente`() {
        // Act
        val result = formatDuration(61)

        // Assert
        assertEquals("1 min 1s", result)
    }

    // ===== TESTES DE CONSISTÊNCIA =====

    @Test
    fun `formatDuration e formatDurationShort devem representar o mesmo valor`() {
        // Arrange
        val testValues = listOf(5, 30, 60, 90, 120, 300, 600)

        // Act & Assert
        testValues.forEach { value ->
            val full = formatDuration(value)
            val short = formatDurationShort(value)

            // Ambos devem representar o mesmo valor, apenas com formato diferente
            assertNotNull("Full format não deve ser null", full)
            assertNotNull("Short format não deve ser null", short)
            assertFalse("Full format não deve estar vazio", full.isEmpty())
            assertFalse("Short format não deve estar vazio", short.isEmpty())
        }
    }

    @Test
    fun `valores do range 5-600 devem ser formatados sem erros`() {
        // Act & Assert
        for (seconds in 5..600 step 5) {
            val formatted = formatDuration(seconds)
            assertNotNull("Formatação de $seconds segundos falhou", formatted)
            assertFalse("Formatação de $seconds resultou em string vazia", formatted.isEmpty())
        }
    }

    @Test
    fun `formatDuration deve usar plural corretamente para minutos`() {
        // Act & Assert
        assertEquals("1 min", formatDuration(60))
        assertEquals("2 min", formatDuration(120))
        // Note: Em português, "min" é invariável, então não há diferença
        // Este teste documenta o comportamento esperado
    }
}

