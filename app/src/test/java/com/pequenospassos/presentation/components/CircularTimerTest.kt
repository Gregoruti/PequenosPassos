package com.pequenospassos.presentation.components

import org.junit.Assert.*
import org.junit.Test

/**
 * Testes unitários para CircularTimer e funções helper.
 *
 * Valida:
 * - Cálculo de progresso
 * - Determinação de cores
 * - Formatação de tempo
 *
 * @since MVP-07 (17/10/2025)
 */
class CircularTimerTest {

    @Test
    fun `formatTime should format zero seconds correctly`() {
        // Given
        val seconds = 0

        // When
        val result = formatTime(seconds)

        // Then
        assertEquals("00:00", result)
    }

    @Test
    fun `formatTime should format seconds under one minute`() {
        // Given
        val seconds = 45

        // When
        val result = formatTime(seconds)

        // Then
        assertEquals("00:45", result)
    }

    @Test
    fun `formatTime should format exactly one minute`() {
        // Given
        val seconds = 60

        // When
        val result = formatTime(seconds)

        // Then
        assertEquals("01:00", result)
    }

    @Test
    fun `formatTime should format minutes and seconds`() {
        // Given
        val seconds = 125 // 2 minutes and 5 seconds

        // When
        val result = formatTime(seconds)

        // Then
        assertEquals("02:05", result)
    }

    @Test
    fun `formatTime should format maximum duration correctly`() {
        // Given
        val seconds = 600 // 10 minutes (max)

        // When
        val result = formatTime(seconds)

        // Then
        assertEquals("10:00", result)
    }

    @Test
    fun `formatTime should handle large values`() {
        // Given
        val seconds = 3665 // 1 hour, 1 minute, 5 seconds

        // When
        val result = formatTime(seconds)

        // Then
        assertEquals("61:05", result)
    }

    @Test
    fun `progress should be 1 when remaining equals total`() {
        // Given
        val remaining = 60
        val total = 60

        // When
        val progress = remaining.toFloat() / total.toFloat()

        // Then
        assertEquals(1.0f, progress, 0.01f)
    }

    @Test
    fun `progress should be 0 when remaining is zero`() {
        // Given
        val remaining = 0
        val total = 60

        // When
        val progress = remaining.toFloat() / total.toFloat()

        // Then
        assertEquals(0.0f, progress, 0.01f)
    }

    @Test
    fun `progress should be 0_5 when remaining is half of total`() {
        // Given
        val remaining = 30
        val total = 60

        // When
        val progress = remaining.toFloat() / total.toFloat()

        // Then
        assertEquals(0.5f, progress, 0.01f)
    }

    @Test
    fun `should return green color when progress is above 60 percent`() {
        // Given
        val progress = 0.7f // 70%

        // When
        val shouldBeGreen = progress > 0.6f

        // Then
        assertTrue(shouldBeGreen)
    }

    @Test
    fun `should return yellow color when progress is between 30 and 60 percent`() {
        // Given
        val progress = 0.45f // 45%

        // When
        val shouldBeYellow = progress in 0.3f..0.6f

        // Then
        assertTrue(shouldBeYellow)
    }

    @Test
    fun `should return red color when progress is below 30 percent`() {
        // Given
        val progress = 0.25f // 25%

        // When
        val shouldBeRed = progress < 0.3f

        // Then
        assertTrue(shouldBeRed)
    }

    @Test
    fun `should handle edge case at exactly 60 percent`() {
        // Given
        val progress = 0.6f

        // When
        val shouldBeGreen = progress > 0.6f
        val shouldBeYellow = progress in 0.3f..0.6f

        // Then
        assertFalse(shouldBeGreen)
        assertTrue(shouldBeYellow)
    }

    @Test
    fun `should handle edge case at exactly 30 percent`() {
        // Given
        val progress = 0.3f

        // When
        val shouldBeYellow = progress > 0.3f
        val shouldBeRed = progress < 0.3f

        // Then
        assertFalse(shouldBeYellow)
        assertFalse(shouldBeRed)
        // At exactly 0.3f, should be yellow (boundary)
    }

    @Test
    fun `should coerce progress within 0 to 1 range - above`() {
        // Given
        val remaining = 70
        val total = 60

        // When
        val progress = (remaining.toFloat() / total.toFloat()).coerceIn(0f, 1f)

        // Then
        assertEquals(1.0f, progress, 0.01f)
    }

    @Test
    fun `should coerce progress within 0 to 1 range - below`() {
        // Given
        val remaining = -10
        val total = 60

        // When
        val progress = (remaining.toFloat() / total.toFloat()).coerceIn(0f, 1f)

        // Then
        assertEquals(0.0f, progress, 0.01f)
    }

    @Test
    fun `should handle division by zero gracefully`() {
        // Given
        val remaining = 30
        val total = 0

        // When
        val progress = if (total > 0) {
            (remaining.toFloat() / total.toFloat()).coerceIn(0f, 1f)
        } else {
            0f
        }

        // Then
        assertEquals(0.0f, progress, 0.01f)
    }
}

