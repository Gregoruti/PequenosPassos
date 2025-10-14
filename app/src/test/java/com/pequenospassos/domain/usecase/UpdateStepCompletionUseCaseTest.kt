package com.pequenospassos.domain.usecase

import com.pequenospassos.domain.model.AppResult
import com.pequenospassos.domain.repository.StepRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Testes unitários para UpdateStepCompletionUseCase.
 *
 * Valida a atualização de conclusão de steps.
 *
 * @since MVP-05 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 */
class UpdateStepCompletionUseCaseTest {

    private lateinit var repository: StepRepository
    private lateinit var useCase: UpdateStepCompletionUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = UpdateStepCompletionUseCase(repository)
    }

    @Test
    fun `invoke com sucesso retorna AppResult Success`() = runTest {
        // Arrange
        val stepId = 1L
        val isCompleted = true
        coEvery { repository.updateStepCompletion(stepId, isCompleted) } returns Result.success(Unit)

        // Act
        val result = useCase(stepId, isCompleted)

        // Assert
        assertTrue(result is AppResult.Success)
        coVerify(exactly = 1) { repository.updateStepCompletion(stepId, isCompleted) }
    }

    @Test
    fun `invoke marcando como completo funciona`() = runTest {
        // Arrange
        val stepId = 5L
        coEvery { repository.updateStepCompletion(stepId, true) } returns Result.success(Unit)

        // Act
        val result = useCase(stepId, true)

        // Assert
        assertTrue(result is AppResult.Success)
        coVerify(exactly = 1) { repository.updateStepCompletion(stepId, true) }
    }

    @Test
    fun `invoke desmarcando como completo funciona`() = runTest {
        // Arrange
        val stepId = 5L
        coEvery { repository.updateStepCompletion(stepId, false) } returns Result.success(Unit)

        // Act
        val result = useCase(stepId, false)

        // Assert
        assertTrue(result is AppResult.Success)
        coVerify(exactly = 1) { repository.updateStepCompletion(stepId, false) }
    }

    @Test
    fun `invoke com erro retorna AppResult Error`() = runTest {
        // Arrange
        val stepId = 1L
        val isCompleted = true
        val exception = RuntimeException("Database error")
        coEvery { repository.updateStepCompletion(stepId, isCompleted) } returns Result.failure(exception)

        // Act
        val result = useCase(stepId, isCompleted)

        // Assert
        assertTrue(result is AppResult.Error)
        assertEquals(exception, (result as AppResult.Error).exception)
    }

    @Test
    fun `invoke chama repository com parâmetros corretos`() = runTest {
        // Arrange
        val stepId = 42L
        val isCompleted = true
        coEvery { repository.updateStepCompletion(stepId, isCompleted) } returns Result.success(Unit)

        // Act
        useCase(stepId, isCompleted)

        // Assert
        coVerify(exactly = 1) { repository.updateStepCompletion(stepId, isCompleted) }
    }
}

