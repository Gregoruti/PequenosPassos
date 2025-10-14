package com.pequenospassos.domain.usecase

import com.pequenospassos.domain.model.AppResult
import com.pequenospassos.domain.repository.AppSettingsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Testes unitários para CompleteOnboardingUseCase.
 *
 * Valida a marcação de onboarding como concluído.
 *
 * @since MVP-05 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 */
class CompleteOnboardingUseCaseTest {

    private lateinit var repository: AppSettingsRepository
    private lateinit var useCase: CompleteOnboardingUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = CompleteOnboardingUseCase(repository)
    }

    @Test
    fun `invoke sucesso retorna AppResult Success`() = runTest {
        // Arrange
        coEvery { repository.markFirstRunCompleted() } returns Result.success(Unit)

        // Act
        val result = useCase()

        // Assert
        assertTrue(result is AppResult.Success)
        coVerify(exactly = 1) { repository.markFirstRunCompleted() }
    }

    @Test
    fun `invoke com erro retorna AppResult Error`() = runTest {
        // Arrange
        val exception = RuntimeException("Database error")
        coEvery { repository.markFirstRunCompleted() } returns Result.failure(exception)

        // Act
        val result = useCase()

        // Assert
        assertTrue(result is AppResult.Error)
        assertEquals(exception, (result as AppResult.Error).exception)
    }

    @Test
    fun `invoke chama repository markFirstRunCompleted`() = runTest {
        // Arrange
        coEvery { repository.markFirstRunCompleted() } returns Result.success(Unit)

        // Act
        useCase()

        // Assert
        coVerify(exactly = 1) { repository.markFirstRunCompleted() }
    }
}
