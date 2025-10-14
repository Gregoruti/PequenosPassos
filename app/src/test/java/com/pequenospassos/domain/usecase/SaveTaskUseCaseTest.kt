package com.pequenospassos.domain.usecase

import com.pequenospassos.domain.model.AppResult
import com.pequenospassos.domain.repository.StepRepository
import com.pequenospassos.domain.repository.TaskRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Testes unitários para SaveTaskUseCase.
 *
 * Valida o salvamento de tarefas com steps e validações.
 *
 * @since MVP-05 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 */
class SaveTaskUseCaseTest {

    private lateinit var taskRepository: TaskRepository
    private lateinit var stepRepository: StepRepository
    private lateinit var useCase: SaveTaskUseCase

    @Before
    fun setup() {
        taskRepository = mockk()
        stepRepository = mockk()
        useCase = SaveTaskUseCase(taskRepository, stepRepository)
    }

    @Test
    fun `invoke com dados válidos sem steps retorna Success com taskId`() = runTest {
        // Arrange
        val title = "Escovar os dentes"
        val time = "08:00"
        val stars = 3
        val taskId = 1L
        coEvery { taskRepository.insertTask(any()) } returns Result.success(taskId)

        // Act
        val result = useCase(title, "", 1, time, stars, emptyList())

        // Assert
        assertTrue(result is AppResult.Success)
        assertEquals(taskId, (result as AppResult.Success).data)
        coVerify(exactly = 1) { taskRepository.insertTask(any()) }
    }

    @Test
    fun `invoke com título vazio retorna Error`() = runTest {
        // Arrange
        val title = ""
        val time = "08:00"
        val stars = 3

        // Act
        val result = useCase(title, "", 1, time, stars)

        // Assert
        assertTrue(result is AppResult.Error)
        val error = result as AppResult.Error
        assertTrue(error.exception.message?.contains("obrigatório") == true)
    }

    @Test
    fun `invoke com horário inválido retorna Error`() = runTest {
        // Arrange
        val title = "Tarefa"
        val time = "25:99" // Inválido
        val stars = 3

        // Act
        val result = useCase(title, "", 1, time, stars)

        // Assert
        assertTrue(result is AppResult.Error)
        val error = result as AppResult.Error
        assertTrue(error.exception.message?.contains("Horário inválido") == true)
    }

    @Test
    fun `invoke com stars menor que 1 retorna Error`() = runTest {
        // Arrange
        val title = "Tarefa"
        val time = "08:00"
        val stars = 0

        // Act
        val result = useCase(title, "", 1, time, stars)

        // Assert
        assertTrue(result is AppResult.Error)
        val error = result as AppResult.Error
        assertTrue(error.exception.message?.contains("entre 1 e 5") == true)
    }

    @Test
    fun `invoke com stars maior que 5 retorna Error`() = runTest {
        // Arrange
        val title = "Tarefa"
        val time = "08:00"
        val stars = 6

        // Act
        val result = useCase(title, "", 1, time, stars)

        // Assert
        assertTrue(result is AppResult.Error)
        val error = result as AppResult.Error
        assertTrue(error.exception.message?.contains("entre 1 e 5") == true)
    }

    @Test
    fun `invoke com steps válidos salva task e steps`() = runTest {
        // Arrange
        val title = "Escovar os dentes"
        val time = "08:00"
        val stars = 3
        val steps = listOf("Passo 1", "Passo 2", "Passo 3")
        val taskId = 1L

        coEvery { taskRepository.insertTask(any()) } returns Result.success(taskId)
        coEvery { stepRepository.insertStep(any()) } returns Result.success(1L)

        // Act
        val result = useCase(title, "", 1, time, stars, steps)

        // Assert
        assertTrue(result is AppResult.Success)
        assertEquals(taskId, (result as AppResult.Success).data)
        coVerify(exactly = 1) { taskRepository.insertTask(any()) }
        coVerify(exactly = 3) { stepRepository.insertStep(any()) }
    }

    @Test
    fun `invoke propaga erro do taskRepository`() = runTest {
        // Arrange
        val title = "Tarefa"
        val time = "08:00"
        val stars = 3
        val exception = RuntimeException("Database error")
        coEvery { taskRepository.insertTask(any()) } returns Result.failure(exception)

        // Act
        val result = useCase(title, "", 1, time, stars)

        // Assert
        assertTrue(result is AppResult.Error)
        assertEquals(exception, (result as AppResult.Error).exception)
    }

    @Test
    fun `invoke com erro ao salvar step retorna Error`() = runTest {
        // Arrange
        val title = "Tarefa"
        val time = "08:00"
        val stars = 3
        val steps = listOf("Passo 1")
        val taskId = 1L
        val exception = RuntimeException("Step error")

        coEvery { taskRepository.insertTask(any()) } returns Result.success(taskId)
        coEvery { stepRepository.insertStep(any()) } returns Result.failure(exception)

        // Act
        val result = useCase(title, "", 1, time, stars, steps)

        // Assert
        assertTrue(result is AppResult.Error)
        assertEquals(exception, (result as AppResult.Error).exception)
    }
}

