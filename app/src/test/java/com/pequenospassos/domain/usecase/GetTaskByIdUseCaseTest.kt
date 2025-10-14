package com.pequenospassos.domain.usecase

import com.pequenospassos.domain.model.Task
import com.pequenospassos.domain.model.TaskStatus
import com.pequenospassos.domain.repository.TaskRepository
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
 * Testes unitários para GetTaskByIdUseCase.
 *
 * Valida a obtenção de tarefa por ID de forma reativa.
 *
 * @since MVP-05 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 */
class GetTaskByIdUseCaseTest {

    private lateinit var repository: TaskRepository
    private lateinit var useCase: GetTaskByIdUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetTaskByIdUseCase(repository)
    }

    @Test
    fun `invoke retorna flow com task existente`() = runTest {
        // Arrange
        val taskId = 1L
        val task = Task(
            id = taskId,
            title = "Escovar os dentes",
            description = "Escove bem todos os dentes",
            iconRes = 1,
            time = "08:00",
            stars = 3,
            status = TaskStatus.PENDING
        )
        every { repository.getTaskById(taskId) } returns flowOf(task)

        // Act
        val result = useCase(taskId).first()

        // Assert
        assertNotNull(result)
        assertEquals("Escovar os dentes", result?.title)
        assertEquals(taskId, result?.id)
        verify(exactly = 1) { repository.getTaskById(taskId) }
    }

    @Test
    fun `invoke retorna flow com null quando task não existe`() = runTest {
        // Arrange
        val taskId = 999L
        every { repository.getTaskById(taskId) } returns flowOf(null)

        // Act
        val result = useCase(taskId).first()

        // Assert
        assertNull(result)
        verify(exactly = 1) { repository.getTaskById(taskId) }
    }

    @Test
    fun `invoke chama repository com taskId correto`() = runTest {
        // Arrange
        val taskId = 42L
        every { repository.getTaskById(taskId) } returns flowOf(null)

        // Act
        useCase(taskId).first()

        // Assert
        verify(exactly = 1) { repository.getTaskById(taskId) }
    }
}

