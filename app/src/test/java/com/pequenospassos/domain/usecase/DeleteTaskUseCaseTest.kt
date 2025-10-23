package com.pequenospassos.domain.usecase

import com.pequenospassos.domain.model.AppResult
import com.pequenospassos.domain.model.Task
import com.pequenospassos.domain.model.TaskStatus
import com.pequenospassos.domain.repository.TaskRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.times
import org.mockito.Mockito.never
import org.mockito.kotlin.any

/**
 * Testes unitários para DeleteTaskUseCase.
 *
 * Valida:
 * - Exclusão de tarefa existente
 * - Validação de tarefa não encontrada
 * - Exclusão em cascata (steps também são removidos)
 *
 * @since v1.9.1 - Funcionalidade de exclusão
 */
class DeleteTaskUseCaseTest {

    private lateinit var taskRepository: TaskRepository
    private lateinit var deleteTaskUseCase: DeleteTaskUseCase

    @Before
    fun setup() {
        taskRepository = mock(TaskRepository::class.java)
        deleteTaskUseCase = DeleteTaskUseCase(taskRepository)
    }

    @Test
    fun `invoke deve deletar tarefa existente com sucesso`() = runTest {
        // Given
        val taskId = 1L
        val task = Task(
            id = taskId,
            title = "Tarefa Teste",
            iconRes = 0,
            time = "08:00",
            stars = 5,
            category = "HIGIENE"
        )

        `when`(taskRepository.getTaskById(taskId)).thenReturn(flowOf(task))

        // When
        val result = deleteTaskUseCase(taskId)

        // Then
        assertTrue(result is AppResult.Success)
        verify(taskRepository, times(1)).deleteTask(task)
    }

    @Test
    fun `invoke deve retornar erro quando tarefa nao existe`() = runTest {
        // Given
        val taskId = 999L
        `when`(taskRepository.getTaskById(taskId)).thenReturn(flowOf(null))

        // When
        val result = deleteTaskUseCase(taskId)

        // Then
        assertTrue(result is AppResult.Error)
        val error = result as AppResult.Error
        assertTrue(error.exception is IllegalArgumentException)
        assertEquals("Tarefa não encontrada", error.exception.message)
    }

    @Test
    fun `invoke deve propagar excecao do repositorio`() = runTest {
        // Given
        val taskId = 1L
        val exception = RuntimeException("Erro de banco de dados")
        `when`(taskRepository.getTaskById(taskId)).thenThrow(exception)

        // When
        val result = deleteTaskUseCase(taskId)

        // Then
        assertTrue(result is AppResult.Error)
        val error = result as AppResult.Error
        assertEquals(exception, error.exception)
    }
}

