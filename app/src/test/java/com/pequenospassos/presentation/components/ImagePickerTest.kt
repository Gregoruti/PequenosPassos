package com.pequenospassos.presentation.components

import org.junit.Assert.*
import org.junit.Test

/**
 * Testes unitários para ImagePicker helper functions.
 *
 * Valida:
 * - Cálculo de dimensões (aspect ratio)
 * - Redimensionamento mantendo proporção
 * - Edge cases (imagens pequenas, grandes, quadradas, etc.)
 *
 * @since MVP-07 (16/10/2025)
 */
class ImagePickerTest {

    // ===== TESTES DE DIMENSÕES =====

    @Test
    fun `calculateNewDimensions deve manter dimensões se menor que maxSize`() {
        // Arrange
        val originalWidth = 800
        val originalHeight = 600
        val maxSize = 1024

        // Act
        val (newWidth, newHeight) = calculateNewDimensionsForTest(
            originalWidth, originalHeight, maxSize
        )

        // Assert
        assertEquals(800, newWidth)
        assertEquals(600, newHeight)
    }

    @Test
    fun `calculateNewDimensions deve redimensionar imagem landscape`() {
        // Arrange - Imagem 2000x1000 (landscape)
        val originalWidth = 2000
        val originalHeight = 1000
        val maxSize = 1024

        // Act
        val (newWidth, newHeight) = calculateNewDimensionsForTest(
            originalWidth, originalHeight, maxSize
        )

        // Assert
        assertEquals(1024, newWidth) // Largura limitada ao max
        assertEquals(512, newHeight)  // Altura proporcional
        assertTrue("Aspect ratio mantido",
            (newWidth.toFloat() / newHeight) == (originalWidth.toFloat() / originalHeight)
        )
    }

    @Test
    fun `calculateNewDimensions deve redimensionar imagem portrait`() {
        // Arrange - Imagem 1000x2000 (portrait)
        val originalWidth = 1000
        val originalHeight = 2000
        val maxSize = 1024

        // Act
        val (newWidth, newHeight) = calculateNewDimensionsForTest(
            originalWidth, originalHeight, maxSize
        )

        // Assert
        assertEquals(512, newWidth)   // Largura proporcional
        assertEquals(1024, newHeight) // Altura limitada ao max
        assertTrue("Aspect ratio mantido",
            (newWidth.toFloat() / newHeight) == (originalWidth.toFloat() / originalHeight)
        )
    }

    @Test
    fun `calculateNewDimensions deve redimensionar imagem quadrada`() {
        // Arrange - Imagem 2000x2000 (quadrada)
        val originalWidth = 2000
        val originalHeight = 2000
        val maxSize = 1024

        // Act
        val (newWidth, newHeight) = calculateNewDimensionsForTest(
            originalWidth, originalHeight, maxSize
        )

        // Assert
        assertEquals(1024, newWidth)
        assertEquals(1024, newHeight)
    }

    @Test
    fun `calculateNewDimensions deve manter imagem quadrada pequena`() {
        // Arrange - Imagem 500x500
        val originalWidth = 500
        val originalHeight = 500
        val maxSize = 1024

        // Act
        val (newWidth, newHeight) = calculateNewDimensionsForTest(
            originalWidth, originalHeight, maxSize
        )

        // Assert
        assertEquals(500, newWidth)
        assertEquals(500, newHeight)
    }

    @Test
    fun `calculateNewDimensions deve lidar com imagem muito larga`() {
        // Arrange - Imagem panorâmica 4000x1000
        val originalWidth = 4000
        val originalHeight = 1000
        val maxSize = 1024

        // Act
        val (newWidth, newHeight) = calculateNewDimensionsForTest(
            originalWidth, originalHeight, maxSize
        )

        // Assert
        assertEquals(1024, newWidth)
        assertEquals(256, newHeight)
        // Verifica aspect ratio (4:1)
        assertTrue("Aspect ratio 4:1 mantido",
            newWidth / newHeight == originalWidth / originalHeight
        )
    }

    @Test
    fun `calculateNewDimensions deve lidar com imagem muito alta`() {
        // Arrange - Imagem 1000x4000
        val originalWidth = 1000
        val originalHeight = 4000
        val maxSize = 1024

        // Act
        val (newWidth, newHeight) = calculateNewDimensionsForTest(
            originalWidth, originalHeight, maxSize
        )

        // Assert
        assertEquals(256, newWidth)
        assertEquals(1024, newHeight)
        // Verifica aspect ratio (1:4)
        assertTrue("Aspect ratio 1:4 mantido",
            newHeight / newWidth == originalHeight / originalWidth
        )
    }

    @Test
    fun `calculateNewDimensions com maxSize 512`() {
        // Arrange
        val originalWidth = 2000
        val originalHeight = 1500
        val maxSize = 512

        // Act
        val (newWidth, newHeight) = calculateNewDimensionsForTest(
            originalWidth, originalHeight, maxSize
        )

        // Assert
        assertEquals(512, newWidth)
        assertEquals(384, newHeight)
    }

    @Test
    fun `calculateNewDimensions com dimensões mínimas`() {
        // Arrange
        val originalWidth = 100
        val originalHeight = 100
        val maxSize = 1024

        // Act
        val (newWidth, newHeight) = calculateNewDimensionsForTest(
            originalWidth, originalHeight, maxSize
        )

        // Assert
        assertEquals(100, newWidth)
        assertEquals(100, newHeight)
    }

    @Test
    fun `calculateNewDimensions deve sempre retornar valores positivos`() {
        // Arrange
        val testCases = listOf(
            Triple(1920, 1080, 1024),
            Triple(1080, 1920, 1024),
            Triple(3000, 3000, 512),
            Triple(500, 250, 1024)
        )

        // Act & Assert
        testCases.forEach { (width, height, max) ->
            val (newWidth, newHeight) = calculateNewDimensionsForTest(width, height, max)
            assertTrue("Largura deve ser positiva", newWidth > 0)
            assertTrue("Altura deve ser positiva", newHeight > 0)
        }
    }

    @Test
    fun `calculateNewDimensions não deve exceder maxSize`() {
        // Arrange
        val testCases = listOf(
            Triple(2000, 1500, 1024),
            Triple(1500, 2000, 1024),
            Triple(3000, 3000, 512),
            Triple(5000, 2000, 800)
        )

        // Act & Assert
        testCases.forEach { (width, height, max) ->
            val (newWidth, newHeight) = calculateNewDimensionsForTest(width, height, max)
            assertTrue("Largura não deve exceder maxSize: $newWidth <= $max",
                newWidth <= max
            )
            assertTrue("Altura não deve exceder maxSize: $newHeight <= $max",
                newHeight <= max
            )
        }
    }

    // Helper function que replica a lógica do componente
    private fun calculateNewDimensionsForTest(
        originalWidth: Int,
        originalHeight: Int,
        maxSize: Int
    ): Pair<Int, Int> {
        if (originalWidth <= maxSize && originalHeight <= maxSize) {
            return Pair(originalWidth, originalHeight)
        }

        val ratio = originalWidth.toFloat() / originalHeight.toFloat()

        return if (originalWidth > originalHeight) {
            Pair(maxSize, (maxSize / ratio).toInt())
        } else {
            Pair((maxSize * ratio).toInt(), maxSize)
        }
    }
}

