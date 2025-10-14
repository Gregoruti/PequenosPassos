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
 * Testes unitários para GetTasksOrderedByTimeUseCase.
 *
 * Valida a obtenção de tarefas ordenadas por horário.
 *
 * @since MVP-05 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 */
class GetTasksOrderedByTimeUseCaseTest {

    private lateinit var repository: TaskRepository
    private lateinit var useCase: GetTasksOrderedByTimeUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetTasksOrderedByTimeUseCase(repository)
    }

    @Test
    fun `invoke retorna flow com tasks ordenadas`() = runTest {
        // Arrange
        val tasks = listOf(
            Task(id = 1, title = "Café", description = "", iconRes = 1, time = "07:00", stars = 3, status = TaskStatus.PENDING),
            Task(id = 2, title = "Escola", description = "", iconRes = 2, time = "08:00", stars = 5, status = TaskStatus.PENDING),
            Task(id = 3, title = "Almoço", description = "", iconRes = 3, time = "12:00", stars = 4, status = TaskStatus.PENDING)
        )
        every { repository.getAllTasksOrderedByTime() } returns flowOf(tasks)

        // Act
        val result = useCase().first()

        // Assert
        assertEquals(3, result.size)
        assertEquals("Café", result[0].title)
        assertEquals("Escola", result[1].title)
        assertEquals("Almoço", result[2].title)
        verify(exactly = 1) { repository.getAllTasksOrderedByTime() }
    }

    @Test
    fun `invoke retorna flow vazio quando não há tasks`() = runTest {
        // Arrange
        every { repository.getAllTasksOrderedByTime() } returns flowOf(emptyList())

        // Act
        val result = useCase().first()

        // Assert
        assertTrue(result.isEmpty())
        verify(exactly = 1) { repository.getAllTasksOrderedByTime() }
    }

    @Test
    fun `invoke chama repository getAllTasksOrderedByTime`() = runTest {
        // Arrange
        every { repository.getAllTasksOrderedByTime() } returns flowOf(emptyList())

        // Act
        useCase().first()

        // Assert
        verify(exactly = 1) { repository.getAllTasksOrderedByTime() }
    }
}

