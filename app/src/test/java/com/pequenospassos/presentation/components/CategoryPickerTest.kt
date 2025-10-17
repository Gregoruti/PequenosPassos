package com.pequenospassos.presentation.components

import com.pequenospassos.domain.model.CategoryGroup
import com.pequenospassos.domain.model.TaskCategory
import org.junit.Assert.*
import org.junit.Test

/**
 * Testes unitários para CategoryPicker.
 *
 * Valida:
 * - Enumeração de categorias
 * - Agrupamento correto
 * - Métodos helper (getFullDisplay, fromString, etc)
 * - Edge cases
 *
 * @since MVP-07 Bugfix (17/10/2025)
 */
class CategoryPickerTest {

    // ===== TESTES DE CATEGORIA =====

    @Test
    fun `TaskCategory deve ter 27 categorias`() {
        // Act
        val totalCategories = TaskCategory.entries.size

        // Assert
        assertEquals("Devem existir exatamente 27 categorias", 27, totalCategories)
    }

    @Test
    fun `TaskCategory getCategoriesByGroup deve retornar 6 grupos`() {
        // Act
        val groupedCategories = TaskCategory.getCategoriesByGroup()

        // Assert
        assertEquals("Devem existir 6 grupos", 6, groupedCategories.size)
    }

    @Test
    fun `TaskCategory getCategoriesByGroup deve ter distribuicao correta`() {
        // Act
        val groupedCategories = TaskCategory.getCategoriesByGroup()

        // Assert - Verificar quantidade por grupo
        assertEquals("AUTOCUIDADO deve ter 6 categorias",
            6, groupedCategories[CategoryGroup.AUTOCUIDADO]?.size)
        assertEquals("SAUDE_TERAPIAS deve ter 9 categorias",
            9, groupedCategories[CategoryGroup.SAUDE_TERAPIAS]?.size)
        assertEquals("COGNITIVO_EDUCACIONAL deve ter 4 categorias",
            4, groupedCategories[CategoryGroup.COGNITIVO_EDUCACIONAL]?.size)
        assertEquals("INTERACAO_SOCIAL deve ter 3 categorias",
            3, groupedCategories[CategoryGroup.INTERACAO_SOCIAL]?.size)
        assertEquals("ATIVIDADES_COTIDIANAS deve ter 3 categorias",
            3, groupedCategories[CategoryGroup.ATIVIDADES_COTIDIANAS]?.size)
        assertEquals("OUTROS deve ter 2 categorias",
            2, groupedCategories[CategoryGroup.OUTROS]?.size)
    }

    @Test
    fun `TaskCategory getFullDisplay deve retornar emoji + nome`() {
        // Arrange
        val category = TaskCategory.HIGIENE_PESSOAL

        // Act
        val display = category.getFullDisplay()

        // Assert
        assertTrue("Deve conter emoji", display.contains("🧼"))
        assertTrue("Deve conter nome", display.contains("Higiene Pessoal"))
        assertEquals("🧼 Higiene Pessoal", display)
    }

    @Test
    fun `TaskCategory fromString deve encontrar categoria por nome`() {
        // Act
        val category = TaskCategory.fromString("HIGIENE_PESSOAL")

        // Assert
        assertNotNull("Categoria deve ser encontrada", category)
        assertEquals(TaskCategory.HIGIENE_PESSOAL, category)
    }

    @Test
    fun `TaskCategory fromString deve ser case-insensitive`() {
        // Act
        val category1 = TaskCategory.fromString("higiene_pessoal")
        val category2 = TaskCategory.fromString("HiGiEnE_PeSsOaL")
        val category3 = TaskCategory.fromString("HIGIENE_PESSOAL")

        // Assert
        assertEquals(TaskCategory.HIGIENE_PESSOAL, category1)
        assertEquals(TaskCategory.HIGIENE_PESSOAL, category2)
        assertEquals(TaskCategory.HIGIENE_PESSOAL, category3)
    }

    @Test
    fun `TaskCategory fromString deve retornar null para nome invalido`() {
        // Act
        val category = TaskCategory.fromString("CATEGORIA_INEXISTENTE")

        // Assert
        assertNull("Categoria não existente deve retornar null", category)
    }

    @Test
    fun `TaskCategory default deve retornar OUTROS`() {
        // Act
        val defaultCategory = TaskCategory.default()

        // Assert
        assertEquals(TaskCategory.OUTROS, defaultCategory)
    }

    @Test
    fun `CategoryGroup deve ter 6 grupos`() {
        // Act
        val totalGroups = CategoryGroup.entries.size

        // Assert
        assertEquals("Devem existir 6 grupos", 6, totalGroups)
    }

    @Test
    fun `CategoryGroup getFullDisplay deve retornar emoji + nome`() {
        // Arrange
        val group = CategoryGroup.AUTOCUIDADO

        // Act
        val display = group.getFullDisplay()

        // Assert
        assertTrue("Deve conter emoji", display.contains("🧍"))
        assertTrue("Deve conter nome", display.contains("Autocuidado"))
        assertEquals("🧍 Autocuidado e Rotina Pessoal", display)
    }

    // ===== TESTES DE REGRESSÃO (Bugfix v1.7.0 → v1.7.1) =====

    @Test
    fun `REGRESSAO - todas categorias devem ter emoji valido`() {
        // Act & Assert
        TaskCategory.entries.forEach { category ->
            assertNotNull("${category.name} deve ter emoji", category.emoji)
            assertTrue("${category.name} emoji não deve estar vazio",
                category.emoji.isNotBlank())
        }
    }

    @Test
    fun `REGRESSAO - todas categorias devem ter displayName valido`() {
        // Act & Assert
        TaskCategory.entries.forEach { category ->
            assertNotNull("${category.name} deve ter displayName", category.displayName)
            assertTrue("${category.name} displayName não deve estar vazio",
                category.displayName.isNotBlank())
        }
    }

    @Test
    fun `REGRESSAO - todas categorias devem pertencer a um grupo`() {
        // Act & Assert
        TaskCategory.entries.forEach { category ->
            assertNotNull("${category.name} deve ter grupo associado", category.group)
        }
    }

    @Test
    fun `REGRESSAO - getCategoriesByGroup nao deve retornar grupos vazios`() {
        // Act
        val groupedCategories = TaskCategory.getCategoriesByGroup()

        // Assert
        groupedCategories.forEach { (group, categories) ->
            assertTrue("Grupo ${group.name} não deve estar vazio",
                categories.isNotEmpty())
        }
    }

    @Test
    fun `REGRESSAO - soma de categorias por grupo deve ser igual ao total`() {
        // Act
        val groupedCategories = TaskCategory.getCategoriesByGroup()
        val totalFromGroups = groupedCategories.values.sumOf { it.size }
        val totalCategories = TaskCategory.entries.size

        // Assert
        assertEquals("Soma das categorias por grupo deve ser igual ao total",
            totalCategories, totalFromGroups)
    }
}
