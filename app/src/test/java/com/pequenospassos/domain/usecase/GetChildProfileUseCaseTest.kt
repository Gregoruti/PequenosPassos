package com.pequenospassos.domain.usecase

import com.pequenospassos.domain.model.ChildProfile
import com.pequenospassos.domain.model.Gender
import com.pequenospassos.domain.repository.ChildProfileRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Testes unitários para GetChildProfileUseCase.
 *
 * Valida a obtenção do perfil da criança de forma reativa.
 *
 * @since MVP-05 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 */
class GetChildProfileUseCaseTest {

    private lateinit var repository: ChildProfileRepository
    private lateinit var useCase: GetChildProfileUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetChildProfileUseCase(repository)
    }

    @Test
    fun `invoke retorna flow com perfil existente`() = runTest {
        // Arrange
        val profile = ChildProfile(
            id = "default_child",
            name = "João",
            gender = Gender.MALE,
            photoUri = null
        )
        every { repository.getProfile() } returns flowOf(profile)

        // Act
        val result = useCase().first()

        // Assert
        assertNotNull(result)
        assertEquals("João", result?.name)
        assertEquals(Gender.MALE, result?.gender)
        verify(exactly = 1) { repository.getProfile() }
    }

    @Test
    fun `invoke retorna flow com null quando não existe perfil`() = runTest {
        // Arrange
        every { repository.getProfile() } returns flowOf(null)

        // Act
        val result = useCase().first()

        // Assert
        assertNull(result)
        verify(exactly = 1) { repository.getProfile() }
    }

    @Test
    fun `invoke chama repository getProfile`() = runTest {
        // Arrange
        every { repository.getProfile() } returns flowOf(null)

        // Act
        useCase().first()

        // Assert
        verify(exactly = 1) { repository.getProfile() }
    }
}

