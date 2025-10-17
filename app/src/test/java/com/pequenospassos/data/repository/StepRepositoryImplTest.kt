package com.pequenospassos.data.repository

import com.pequenospassos.data.database.dao.StepDao
import com.pequenospassos.domain.model.Step
import com.pequenospassos.domain.model.Task
import com.pequenospassos.domain.model.TaskWithSteps
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
 * Testes unitários para StepRepositoryImpl.
 *
 * Valida o comportamento do repositório de steps usando DAO mockado.
 *
 * @since MVP-04 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 */
class StepRepositoryImplTest {

    private lateinit var dao: StepDao
    private lateinit var repository: StepRepositoryImpl

    @Before
    fun setup() {
        dao = mockk()
        repository = StepRepositoryImpl(dao)
    }

    @Test
    fun `getStepsByTask retorna flow do DAO`() = runTest {
        // Arrange
        val steps = listOf(
            Step(id = 1, taskId = 1, title = "Step 1", order = 0),
            Step(id = 2, taskId = 1, title = "Step 2", order = 1)
        )
        coEvery { dao.getStepsByTask(1L) } returns flowOf(steps)

        // Act
        val result = repository.getStepsByTask(1L).first()

        // Assert
        assertEquals(2, result.size)
        assertEquals("Step 1", result[0].title)
        assertEquals(0, result[0].order)
    }

    @Test
    fun `getTaskWithSteps retorna flow do DAO`() = runTest {
        // Arrange
        val task = Task(id = 1, title = "Tarefa", iconRes = 1, time = "10:00", stars = 3, category = "BANHO")
        val steps = listOf(
            Step(id = 1, taskId = 1, title = "Step 1", order = 0)
        )
        val taskWithSteps = TaskWithSteps(task, steps)
        coEvery { dao.getTaskWithSteps(1L) } returns flowOf(taskWithSteps)

        // Act
        val result = repository.getTaskWithSteps(1L).first()

        // Assert
        assertNotNull(result)
        assertEquals("Tarefa", result?.task?.title)
        assertEquals(1, result?.steps?.size)
    }

    @Test
    fun `insertStep sucesso retorna Result success com ID`() = runTest {
        // Arrange
        val step = Step(taskId = 1, title = "Novo Step", order = 0)
        coEvery { dao.insertStep(step) } returns 10L

        // Act
        val result = repository.insertStep(step)

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(10L, result.getOrNull())
        coVerify(exactly = 1) { dao.insertStep(step) }
    }

    @Test
    fun `insertStep com erro retorna Result failure`() = runTest {
        // Arrange
        val step = Step(taskId = 1, title = "Step", order = 0)
        val exception = RuntimeException("Insert error")
        coEvery { dao.insertStep(step) } throws exception

        // Act
        val result = repository.insertStep(step)

        // Assert
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `insertSteps sucesso retorna Result success com IDs`() = runTest {
        // Arrange
        val steps = listOf(
            Step(taskId = 1, title = "Step 1", order = 0),
            Step(taskId = 1, title = "Step 2", order = 1)
        )
        val ids = listOf(1L, 2L)
        coEvery { dao.insertSteps(steps) } returns ids

        // Act
        val result = repository.insertSteps(steps)

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(ids, result.getOrNull())
        coVerify(exactly = 1) { dao.insertSteps(steps) }
    }

    @Test
    fun `updateStep sucesso retorna Result success`() = runTest {
        // Arrange
        val step = Step(id = 1, taskId = 1, title = "Atualizado", order = 0)
        coEvery { dao.updateStep(step) } returns Unit

        // Act
        val result = repository.updateStep(step)

        // Assert
        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { dao.updateStep(step) }
    }

    @Test
    fun `updateStepCompletion sucesso retorna Result success`() = runTest {
        // Arrange
        coEvery { dao.updateStepCompletion(1L, true) } returns Unit

        // Act
        val result = repository.updateStepCompletion(1L, true)

        // Assert
        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { dao.updateStepCompletion(1L, true) }
    }

    @Test
    fun `updateStepCompletion com erro retorna Result failure`() = runTest {
        // Arrange
        val exception = RuntimeException("Update error")
        coEvery { dao.updateStepCompletion(1L, true) } throws exception

        // Act
        val result = repository.updateStepCompletion(1L, true)

        // Assert
        assertTrue(result.isFailure)
    }

    @Test
    fun `deleteStep sucesso retorna Result success`() = runTest {
        // Arrange
        val step = Step(id = 1, taskId = 1, title = "Step", order = 0)
        coEvery { dao.deleteStep(step) } returns Unit

        // Act
        val result = repository.deleteStep(step)

        // Assert
        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { dao.deleteStep(step) }
    }

    @Test
    fun `deleteStepsByTask sucesso retorna Result success`() = runTest {
        // Arrange
        coEvery { dao.deleteStepsByTask(1L) } returns Unit

        // Act
        val result = repository.deleteStepsByTask(1L)

        // Assert
        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { dao.deleteStepsByTask(1L) }
    }

    @Test
    fun `getStepCountByTask sucesso retorna Result success com count`() = runTest {
        // Arrange
        coEvery { dao.getStepCountByTask(1L) } returns 3

        // Act
        val result = repository.getStepCountByTask(1L)

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(3, result.getOrNull())
    }

    @Test
    fun `getStepCountByTask com erro retorna Result failure`() = runTest {
        // Arrange
        val exception = RuntimeException("Count error")
        coEvery { dao.getStepCountByTask(1L) } throws exception

        // Act
        val result = repository.getStepCountByTask(1L)

        // Assert
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
