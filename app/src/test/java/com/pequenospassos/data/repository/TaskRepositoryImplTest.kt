package com.pequenospassos.data.repository

import com.pequenospassos.data.database.dao.TaskDao
import com.pequenospassos.domain.model.Task
import com.pequenospassos.domain.model.TaskStatus
import com.pequenospassos.domain.model.TaskCategory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Testes unitários para TaskRepositoryImpl.
 *
 * Valida o comportamento do repositório de tarefas usando DAO mockado.
 *
 * @since MVP-04 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 */
class TaskRepositoryImplTest {

    private lateinit var dao: TaskDao
    private lateinit var repository: TaskRepositoryImpl

    @Before
    fun setup() {
        dao = mockk()
        repository = TaskRepositoryImpl(dao)
    }

    @Test
    fun `getAllTasksOrderedByTime retorna flow do DAO`() = runTest {
        // Arrange
        val tasks = listOf(
            Task(id = 1, title = "Tarefa 1", iconRes = 1, time = "08:00", stars = 3, category = "HIGIENE_PESSOAL"),
            Task(id = 2, title = "Tarefa 2", iconRes = 1, time = "09:00", stars = 4, category = "ALIMENTACAO")
        )
        coEvery { dao.getAllTasksOrderedByTime() } returns flowOf(tasks)

        // Act
        val result = repository.getAllTasksOrderedByTime().first()

        // Assert
        assertEquals(2, result.size)
        assertEquals("Tarefa 1", result[0].title)
        assertEquals("08:00", result[0].time)
    }

    @Test
    fun `getTaskById retorna flow do DAO`() = runTest {
        // Arrange
        val task = Task(id = 1, title = "Tarefa", iconRes = 1, time = "10:00", stars = 3, category = "BANHO")
        coEvery { dao.getTaskById(1L) } returns flowOf(task)

        // Act
        val result = repository.getTaskById(1L).first()

        // Assert
        assertNotNull(result)
        assertEquals("Tarefa", result?.title)
    }

    @Test
    fun `getTasksByStatus retorna flow filtrado`() = runTest {
        // Arrange
        val tasks = listOf(
            Task(id = 1, title = "Pendente", iconRes = 1, time = "08:00", stars = 3, status = TaskStatus.PENDING, category = "VESTIR")
        )
        coEvery { dao.getTasksByStatus(TaskStatus.PENDING) } returns flowOf(tasks)

        // Act
        val result = repository.getTasksByStatus(TaskStatus.PENDING).first()

        // Assert
        assertEquals(1, result.size)
        assertEquals(TaskStatus.PENDING, result[0].status)
    }

    @Test
    fun `insertTask sucesso retorna Result success com ID`() = runTest {
        // Arrange
        val task = Task(title = "Nova Tarefa", iconRes = 1, time = "10:00", stars = 3, category = "LEITURA")
        coEvery { dao.insertTask(task) } returns 42L

        // Act
        val result = repository.insertTask(task)

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(42L, result.getOrNull())
        coVerify(exactly = 1) { dao.insertTask(task) }
    }

    @Test
    fun `insertTask com erro retorna Result failure`() = runTest {
        // Arrange
        val task = Task(title = "Tarefa", iconRes = 1, time = "10:00", stars = 3, category = "ESCRITA")
        val exception = RuntimeException("Insert error")
        coEvery { dao.insertTask(task) } throws exception

        // Act
        val result = repository.insertTask(task)

        // Assert
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `updateTask sucesso retorna Result success`() = runTest {
        // Arrange
        val task = Task(id = 1, title = "Atualizada", iconRes = 1, time = "10:00", stars = 3, category = "MATEMATICA")
        coEvery { dao.updateTask(task) } returns Unit

        // Act
        val result = repository.updateTask(task)

        // Assert
        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { dao.updateTask(task) }
    }

    @Test
    fun `updateTaskStatus sucesso retorna Result success`() = runTest {
        // Arrange
        coEvery { dao.updateTaskStatus(1L, TaskStatus.COMPLETED) } returns Unit

        // Act
        val result = repository.updateTaskStatus(1L, TaskStatus.COMPLETED)

        // Assert
        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { dao.updateTaskStatus(1L, TaskStatus.COMPLETED) }
    }

    @Test
    fun `updateTaskStatus com erro retorna Result failure`() = runTest {
        // Arrange
        val exception = RuntimeException("Update error")
        coEvery { dao.updateTaskStatus(1L, TaskStatus.COMPLETED) } throws exception

        // Act
        val result = repository.updateTaskStatus(1L, TaskStatus.COMPLETED)

        // Assert
        assertTrue(result.isFailure)
    }

    @Test
    fun `deleteTask sucesso retorna Result success`() = runTest {
        // Arrange
        val task = Task(id = 1, title = "Tarefa", iconRes = 1, time = "10:00", stars = 3, category = "OUTROS")
        coEvery { dao.deleteTask(task) } returns Unit

        // Act
        val result = repository.deleteTask(task)

        // Assert
        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { dao.deleteTask(task) }
    }

    @Test
    fun `getTaskCount sucesso retorna Result success com count`() = runTest {
        // Arrange
        coEvery { dao.getTaskCount() } returns 5

        // Act
        val result = repository.getTaskCount()

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(5, result.getOrNull())
    }

    @Test
    fun `getTaskCount com erro retorna Result failure`() = runTest {
        // Arrange
        val exception = RuntimeException("Count error")
        coEvery { dao.getTaskCount() } throws exception

        // Act
        val result = repository.getTaskCount()

        // Assert
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `deleteAllTasks sucesso retorna Result success`() = runTest {
        // Arrange
        coEvery { dao.deleteAll() } returns Unit

        // Act
        val result = repository.deleteAllTasks()

        // Assert
        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { dao.deleteAll() }
    }
}
