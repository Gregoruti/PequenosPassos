package com.pequenospassos.domain.usecase

import com.pequenospassos.domain.repository.AppSettingsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Testes unitários para CheckFirstRunUseCase.
 *
 * Valida a verificação de primeira execução do app.
 *
 * @since MVP-05 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 */
class CheckFirstRunUseCaseTest {

    private lateinit var repository: AppSettingsRepository
    private lateinit var useCase: CheckFirstRunUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = CheckFirstRunUseCase(repository)
    }

    @Test
    fun `invoke retorna true quando é primeira execução`() = runTest {
        // Arrange
        coEvery { repository.isFirstRun() } returns true

        // Act
        val result = useCase()

        // Assert
        assertTrue(result)
        coVerify(exactly = 1) { repository.isFirstRun() }
    }

    @Test
    fun `invoke retorna false quando não é primeira execução`() = runTest {
        // Arrange
        coEvery { repository.isFirstRun() } returns false

        // Act
        val result = useCase()

        // Assert
        assertFalse(result)
        coVerify(exactly = 1) { repository.isFirstRun() }
    }

    @Test
    fun `invoke chama repository isFirstRun`() = runTest {
        // Arrange
        coEvery { repository.isFirstRun() } returns true

        // Act
        useCase()

        // Assert
        coVerify(exactly = 1) { repository.isFirstRun() }
    }
}

