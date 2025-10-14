package com.pequenospassos.domain.model

import org.junit.Assert.*
import org.junit.Test

/**
 * Testes unitários para ChildProfile.
 *
 * Valida:
 * - Criação de perfis válidos
 * - Validação de dados (isValid)
 * - Enum Gender e seus métodos
 *
 * @since MVP-02 (13/10/2025)
 */
class ChildProfileTest {

    @Test
    fun `ChildProfile válido com dados mínimos deve passar na validação`() {
        // Arrange
        val profile = ChildProfile(
            id = "child_1",
            name = "João",
            gender = Gender.MALE
        )

        // Act
        val isValid = profile.isValid()

        // Assert
        assertTrue("Perfil válido deve passar na validação", isValid)
    }

    @Test
    fun `ChildProfile com nome vazio deve falhar na validação`() {
        // Arrange
        val profile = ChildProfile(
            id = "child_1",
            name = "",
            gender = Gender.FEMALE
        )

        // Act
        val isValid = profile.isValid()

        // Assert
        assertFalse("Perfil com nome vazio deve falhar na validação", isValid)
    }

    @Test
    fun `ChildProfile com nome de 1 caractere deve falhar na validação`() {
        // Arrange
        val profile = ChildProfile(
            id = "child_1",
            name = "A",
            gender = Gender.MALE
        )

        // Act
        val isValid = profile.isValid()

        // Assert
        assertFalse("Perfil com nome de 1 caractere deve falhar", isValid)
    }

    @Test
    fun `ChildProfile com nome apenas espaços deve falhar na validação`() {
        // Arrange
        val profile = ChildProfile(
            id = "child_1",
            name = "   ",
            gender = Gender.FEMALE
        )

        // Act
        val isValid = profile.isValid()

        // Assert
        assertFalse("Perfil com nome apenas espaços deve falhar", isValid)
    }

    @Test
    fun `ChildProfile com photoUri deve ser válido`() {
        // Arrange
        val profile = ChildProfile(
            id = "child_1",
            name = "Maria",
            gender = Gender.FEMALE,
            photoUri = "content://media/external/images/1"
        )

        // Act & Assert
        assertTrue(profile.isValid())
        assertEquals("content://media/external/images/1", profile.photoUri)
    }

    @Test
    fun `ChildProfile deve ter ID padrão default_child`() {
        // Arrange & Act
        val profile = ChildProfile(
            name = "Pedro",
            gender = Gender.MALE
        )

        // Assert
        assertEquals("default_child", profile.id)
    }

    @Test
    fun `ChildProfile deve ter timestamp de criação`() {
        // Arrange
        val beforeCreation = System.currentTimeMillis()

        // Act
        val profile = ChildProfile(
            name = "Ana",
            gender = Gender.FEMALE
        )

        val afterCreation = System.currentTimeMillis()

        // Assert
        assertTrue("Timestamp deve estar entre antes e depois da criação",
            profile.createdAt in beforeCreation..afterCreation)
    }

    // ===== TESTES DO ENUM GENDER =====

    @Test
    fun `Gender MALE deve retornar 'Menino' no displayName`() {
        // Act
        val displayName = Gender.MALE.getDisplayName()

        // Assert
        assertEquals("Menino", displayName)
    }

    @Test
    fun `Gender FEMALE deve retornar 'Menina' no displayName`() {
        // Act
        val displayName = Gender.FEMALE.getDisplayName()

        // Assert
        assertEquals("Menina", displayName)
    }

    @Test
    fun `Gender deve ter exatamente 2 valores`() {
        // Act
        val genderValues = Gender.values()

        // Assert
        assertEquals(2, genderValues.size)
    }

    @Test
    fun `Gender deve conter MALE e FEMALE`() {
        // Act
        val genderValues = Gender.values()

        // Assert
        assertTrue(genderValues.contains(Gender.MALE))
        assertTrue(genderValues.contains(Gender.FEMALE))
    }
}

