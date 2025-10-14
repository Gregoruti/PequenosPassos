package com.pequenospassos.presentation.theme

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import org.junit.Test
import org.junit.Assert.*

/**
 * Testes unitários para validação das shapes do PequenosPassos
 *
 * Valida:
 * - Shapes definidas corretamente
 * - Arredondamento adequado
 * - Consistência visual
 * - Amigabilidade para crianças com TEA
 */
class ShapeTest {

    private val shapes = PequenosPassosShapes

    @Test
    fun `extraSmall shape has correct corner radius`() {
        val shape = shapes.extraSmall as RoundedCornerShape
        assertEquals(CornerSize(8.dp), shape.topStart)
        assertEquals(CornerSize(8.dp), shape.topEnd)
        assertEquals(CornerSize(8.dp), shape.bottomStart)
        assertEquals(CornerSize(8.dp), shape.bottomEnd)
    }

    @Test
    fun `small shape has correct corner radius`() {
        val shape = shapes.small as RoundedCornerShape
        assertEquals(CornerSize(12.dp), shape.topStart)
        assertEquals(CornerSize(12.dp), shape.topEnd)
        assertEquals(CornerSize(12.dp), shape.bottomStart)
        assertEquals(CornerSize(12.dp), shape.bottomEnd)
    }

    @Test
    fun `medium shape has correct corner radius`() {
        val shape = shapes.medium as RoundedCornerShape
        assertEquals(CornerSize(16.dp), shape.topStart)
        assertEquals(CornerSize(16.dp), shape.topEnd)
        assertEquals(CornerSize(16.dp), shape.bottomStart)
        assertEquals(CornerSize(16.dp), shape.bottomEnd)
    }

    @Test
    fun `large shape has correct corner radius`() {
        val shape = shapes.large as RoundedCornerShape
        assertEquals(CornerSize(20.dp), shape.topStart)
        assertEquals(CornerSize(20.dp), shape.topEnd)
        assertEquals(CornerSize(20.dp), shape.bottomStart)
        assertEquals(CornerSize(20.dp), shape.bottomEnd)
    }

    @Test
    fun `extraLarge shape has correct corner radius`() {
        val shape = shapes.extraLarge as RoundedCornerShape
        assertEquals(CornerSize(24.dp), shape.topStart)
        assertEquals(CornerSize(24.dp), shape.topEnd)
        assertEquals(CornerSize(24.dp), shape.bottomStart)
        assertEquals(CornerSize(24.dp), shape.bottomEnd)
    }

    @Test
    fun `all shapes are rounded corners`() {
        // Verifica que todas as shapes são RoundedCornerShape
        assertTrue(shapes.extraSmall is RoundedCornerShape)
        assertTrue(shapes.small is RoundedCornerShape)
        assertTrue(shapes.medium is RoundedCornerShape)
        assertTrue(shapes.large is RoundedCornerShape)
        assertTrue(shapes.extraLarge is RoundedCornerShape)
    }

    @Test
    fun `shapes follow progressive size order`() {
        // Verifica que os tamanhos são progressivos comparando valores em dp
        val extraSmallShape = shapes.extraSmall as RoundedCornerShape
        val smallShape = shapes.small as RoundedCornerShape
        val mediumShape = shapes.medium as RoundedCornerShape
        val largeShape = shapes.large as RoundedCornerShape
        val extraLargeShape = shapes.extraLarge as RoundedCornerShape

        // Extraindo valores para comparação
        assertTrue(extraSmallShape == RoundedCornerShape(8.dp))
        assertTrue(smallShape == RoundedCornerShape(12.dp))
        assertTrue(mediumShape == RoundedCornerShape(16.dp))
        assertTrue(largeShape == RoundedCornerShape(20.dp))
        assertTrue(extraLargeShape == RoundedCornerShape(24.dp))
    }

    @Test
    fun `minimum corner radius is suitable for TEA`() {
        val extraSmallShape = shapes.extraSmall as RoundedCornerShape

        // Mínimo de 8dp garante cantos arredondados visíveis
        // Evita cantos pontiagudos que podem causar desconforto visual
        assertEquals(RoundedCornerShape(8.dp), extraSmallShape)
    }

    @Test
    fun `shapes use uniform corner radii`() {
        // Verifica que todos os cantos têm o mesmo raio (simétrico)
        val medium = shapes.medium as RoundedCornerShape
        assertEquals(medium.topStart, medium.topEnd)
        assertEquals(medium.topEnd, medium.bottomStart)
        assertEquals(medium.bottomStart, medium.bottomEnd)
    }

    @Test
    fun `shape sizes have consistent increments`() {
        // Verifica que há incrementos de 4dp entre cada nível
        val extraSmallShape = shapes.extraSmall as RoundedCornerShape
        val smallShape = shapes.small as RoundedCornerShape
        val mediumShape = shapes.medium as RoundedCornerShape
        val largeShape = shapes.large as RoundedCornerShape
        val extraLargeShape = shapes.extraLarge as RoundedCornerShape

        // Verifica os valores esperados
        assertEquals(RoundedCornerShape(8.dp), extraSmallShape)   // 8dp
        assertEquals(RoundedCornerShape(12.dp), smallShape)       // 12dp (8 + 4)
        assertEquals(RoundedCornerShape(16.dp), mediumShape)      // 16dp (12 + 4)
        assertEquals(RoundedCornerShape(20.dp), largeShape)       // 20dp (16 + 4)
        assertEquals(RoundedCornerShape(24.dp), extraLargeShape)  // 24dp (20 + 4)
    }
}
