package com.pequenospassos.domain.usecase

import com.pequenospassos.domain.model.AppResult
import com.pequenospassos.domain.model.ChildProfile
import com.pequenospassos.domain.model.Gender
import com.pequenospassos.domain.repository.ChildProfileRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Testes unitários para SaveChildProfileUseCase.
 *
 * Valida o salvamento de perfil da criança com validações.
 *
 * @since MVP-05 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 */
class SaveChildProfileUseCaseTest {

    private lateinit var repository: ChildProfileRepository
    private lateinit var useCase: SaveChildProfileUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = SaveChildProfileUseCase(repository)
    }

    @Test
    fun `invoke com dados válidos retorna Success`() = runTest {
        // Arrange
        val name = "João"
        val gender = Gender.MALE
        coEvery { repository.saveProfile(any()) } returns Result.success(Unit)

        // Act
        val result = useCase(name, gender)

        // Assert
        assertTrue(result is AppResult.Success)
        coVerify(exactly = 1) { repository.saveProfile(any()) }
    }

    @Test
    fun `invoke com nome vazio retorna Error`() = runTest {
        // Arrange
        val name = ""
        val gender = Gender.FEMALE

        // Act
        val result = useCase(name, gender)

        // Assert
        assertTrue(result is AppResult.Error)
        val error = result as AppResult.Error
        assertTrue(error.exception is IllegalArgumentException)
        assertTrue(error.exception.message?.contains("obrigatório") == true)
    }

    @Test
    fun `invoke com nome em branco retorna Error`() = runTest {
        // Arrange
        val name = "   "
        val gender = Gender.MALE

        // Act
        val result = useCase(name, gender)

        // Assert
        assertTrue(result is AppResult.Error)
        val error = result as AppResult.Error
        assertTrue(error.exception is IllegalArgumentException)
    }

    @Test
    fun `invoke com nome muito curto retorna Error`() = runTest {
        // Arrange
        val name = "J"
        val gender = Gender.FEMALE

        // Act
        val result = useCase(name, gender)

        // Assert
        assertTrue(result is AppResult.Error)
        val error = result as AppResult.Error
        assertTrue(error.exception.message?.contains("pelo menos 2 caracteres") == true)
    }

    @Test
    fun `invoke salva perfil com nome trimmed`() = runTest {
        // Arrange
        val name = "  Maria  "
        val gender = Gender.FEMALE
        coEvery { repository.saveProfile(any()) } returns Result.success(Unit)

        // Act
        val result = useCase(name, gender)

        // Assert
        assertTrue(result is AppResult.Success)
        coVerify(exactly = 1) {
            repository.saveProfile(match {
                it.name == "Maria"
            })
        }
    }

    @Test
    fun `invoke com photoUri salva corretamente`() = runTest {
        // Arrange
        val name = "Pedro"
        val gender = Gender.MALE
        val photoUri = "content://photo/123"
        coEvery { repository.saveProfile(any()) } returns Result.success(Unit)

        // Act
        val result = useCase(name, gender, photoUri)

        // Assert
        assertTrue(result is AppResult.Success)
        coVerify(exactly = 1) {
            repository.saveProfile(match {
                it.photoUri == photoUri
            })
        }
    }

    @Test
    fun `invoke propaga erro do repository`() = runTest {
        // Arrange
        val name = "Ana"
        val gender = Gender.FEMALE
        val exception = RuntimeException("Database error")
        coEvery { repository.saveProfile(any()) } returns Result.failure(exception)

        // Act
        val result = useCase(name, gender)

        // Assert
        assertTrue(result is AppResult.Error)
        assertEquals(exception, (result as AppResult.Error).exception)
    }
}

