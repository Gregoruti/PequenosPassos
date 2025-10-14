package com.pequenospassos.domain.usecase

import com.pequenospassos.domain.model.AppResult
import com.pequenospassos.domain.model.TaskStatus
import com.pequenospassos.domain.repository.TaskRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Testes unitários para UpdateTaskStatusUseCase.
 *
 * Valida a atualização de status de tarefas.
 *
 * @since MVP-05 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 */
class UpdateTaskStatusUseCaseTest {

    private lateinit var repository: TaskRepository
    private lateinit var useCase: UpdateTaskStatusUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = UpdateTaskStatusUseCase(repository)
    }

    @Test
    fun `invoke com sucesso retorna AppResult Success`() = runTest {
        // Arrange
        val taskId = 1L
        val status = TaskStatus.COMPLETED
        coEvery { repository.updateTaskStatus(taskId, status) } returns Result.success(Unit)

        // Act
        val result = useCase(taskId, status)

        // Assert
        assertTrue(result is AppResult.Success)
        coVerify(exactly = 1) { repository.updateTaskStatus(taskId, status) }
    }

    @Test
    fun `invoke com status PENDING funciona`() = runTest {
        // Arrange
        val taskId = 1L
        val status = TaskStatus.PENDING
        coEvery { repository.updateTaskStatus(taskId, status) } returns Result.success(Unit)

        // Act
        val result = useCase(taskId, status)

        // Assert
        assertTrue(result is AppResult.Success)
        coVerify(exactly = 1) { repository.updateTaskStatus(taskId, status) }
    }

    @Test
    fun `invoke com status COMPLETED funciona`() = runTest {
        // Arrange
        val taskId = 1L
        val status = TaskStatus.COMPLETED
        coEvery { repository.updateTaskStatus(taskId, status) } returns Result.success(Unit)

        // Act
        val result = useCase(taskId, status)

        // Assert
        assertTrue(result is AppResult.Success)
        coVerify(exactly = 1) { repository.updateTaskStatus(taskId, status) }
    }

    @Test
    fun `invoke com status CANCELED funciona`() = runTest {
        // Arrange
        val taskId = 1L
        val status = TaskStatus.CANCELED
        coEvery { repository.updateTaskStatus(taskId, status) } returns Result.success(Unit)

        // Act
        val result = useCase(taskId, status)

        // Assert
        assertTrue(result is AppResult.Success)
        coVerify(exactly = 1) { repository.updateTaskStatus(taskId, status) }
    }

    @Test
    fun `invoke com erro retorna AppResult Error`() = runTest {
        // Arrange
        val taskId = 1L
        val status = TaskStatus.COMPLETED
        val exception = RuntimeException("Database error")
        coEvery { repository.updateTaskStatus(taskId, status) } returns Result.failure(exception)

        // Act
        val result = useCase(taskId, status)

        // Assert
        assertTrue(result is AppResult.Error)
        assertEquals(exception, (result as AppResult.Error).exception)
    }

    @Test
    fun `invoke chama repository com parametros corretos`() = runTest {
        // Arrange
        val taskId = 42L
        val status = TaskStatus.COMPLETED
        coEvery { repository.updateTaskStatus(taskId, status) } returns Result.success(Unit)

        // Act
        useCase(taskId, status)

        // Assert
        coVerify(exactly = 1) { repository.updateTaskStatus(taskId, status) }
    }
}
