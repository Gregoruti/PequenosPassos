package com.pequenospassos.domain.usecase

import com.pequenospassos.domain.model.Step
import com.pequenospassos.domain.repository.StepRepository
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
 * Testes unitários para GetStepsByTaskUseCase.
 *
 * Valida a obtenção de steps por tarefa de forma reativa.
 *
 * @since MVP-05 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 */
class GetStepsByTaskUseCaseTest {

    private lateinit var repository: StepRepository
    private lateinit var useCase: GetStepsByTaskUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetStepsByTaskUseCase(repository)
    }

    @Test
    fun `invoke retorna flow com steps da tarefa`() = runTest {
        // Arrange
        val taskId = 1L
        val steps = listOf(
            Step(id = 1, taskId = taskId, title = "Passo 1", order = 0, isCompleted = false),
            Step(id = 2, taskId = taskId, title = "Passo 2", order = 1, isCompleted = false),
            Step(id = 3, taskId = taskId, title = "Passo 3", order = 2, isCompleted = false)
        )
        every { repository.getStepsByTask(taskId) } returns flowOf(steps)

        // Act
        val result = useCase(taskId).first()

        // Assert
        assertEquals(3, result.size)
        assertEquals("Passo 1", result[0].title)
        assertEquals("Passo 2", result[1].title)
        assertEquals("Passo 3", result[2].title)
        verify(exactly = 1) { repository.getStepsByTask(taskId) }
    }

    @Test
    fun `invoke retorna flow vazio quando nao ha steps`() = runTest {
        // Arrange
        val taskId = 1L
        every { repository.getStepsByTask(taskId) } returns flowOf(emptyList())

        // Act
        val result = useCase(taskId).first()

        // Assert
        assertTrue(result.isEmpty())
        verify(exactly = 1) { repository.getStepsByTask(taskId) }
    }

    @Test
    fun `invoke chama repository com taskId correto`() = runTest {
        // Arrange
        val taskId = 42L
        every { repository.getStepsByTask(taskId) } returns flowOf(emptyList())

        // Act
        useCase(taskId).first()

        // Assert
        verify(exactly = 1) { repository.getStepsByTask(taskId) }
    }
}
