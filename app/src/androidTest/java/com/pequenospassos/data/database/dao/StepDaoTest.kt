package com.pequenospassos.data.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pequenospassos.data.database.AppDatabase
import com.pequenospassos.domain.model.Step
import com.pequenospassos.domain.model.Task
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

/**
 * Testes instrumentados para StepDao.
 *
 * Valida operações CRUD de steps e relacionamento com Task.
 * Testa cascade delete e TaskWithSteps.
 *
 * @since MVP-03 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 */
@RunWith(AndroidJUnit4::class)
class StepDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var stepDao: StepDao
    private lateinit var taskDao: TaskDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
        stepDao = database.stepDao()
        taskDao = database.taskDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertAndGetSteps() = runBlocking {
        // Arrange
        val task = Task(title = "Escovar dentes", iconRes = 1, time = "08:00", stars = 3)
        val taskId = taskDao.insertTask(task)

        val step1 = Step(taskId = taskId, title = "Pegar escova", order = 0)
        val step2 = Step(taskId = taskId, title = "Colocar pasta", order = 1)

        // Act
        stepDao.insertStep(step1)
        stepDao.insertStep(step2)

        val steps = stepDao.getStepsByTask(taskId).first()

        // Assert
        assertEquals(2, steps.size)
        assertEquals("Pegar escova", steps[0].title)
        assertEquals("Colocar pasta", steps[1].title)
    }

    @Test
    fun stepsOrderedByOrder() = runBlocking {
        // Arrange
        val task = Task(title = "Tarefa", iconRes = 1, time = "08:00", stars = 3)
        val taskId = taskDao.insertTask(task)

        // Inserir fora de ordem
        val step2 = Step(taskId = taskId, title = "Step 2", order = 2)
        val step0 = Step(taskId = taskId, title = "Step 0", order = 0)
        val step1 = Step(taskId = taskId, title = "Step 1", order = 1)

        stepDao.insertStep(step2)
        stepDao.insertStep(step0)
        stepDao.insertStep(step1)

        // Act
        val steps = stepDao.getStepsByTask(taskId).first()

        // Assert
        assertEquals(3, steps.size)
        assertEquals("Step 0", steps[0].title)
        assertEquals("Step 1", steps[1].title)
        assertEquals("Step 2", steps[2].title)
    }

    @Test
    fun updateStep() = runBlocking {
        // Arrange
        val task = Task(title = "Tarefa", iconRes = 1, time = "08:00", stars = 3)
        val taskId = taskDao.insertTask(task)
        val step = Step(taskId = taskId, title = "Step Original", order = 0, isCompleted = false)
        val stepId = stepDao.insertStep(step)

        // Act
        val updated = step.copy(id = stepId, title = "Step Atualizado", isCompleted = true)
        stepDao.updateStep(updated)

        val steps = stepDao.getStepsByTask(taskId).first()

        // Assert
        assertEquals(1, steps.size)
        assertEquals("Step Atualizado", steps[0].title)
        assertTrue(steps[0].isCompleted)
    }

    @Test
    fun updateStepCompletion() = runBlocking {
        // Arrange
        val task = Task(title = "Tarefa", iconRes = 1, time = "08:00", stars = 3)
        val taskId = taskDao.insertTask(task)
        val step = Step(taskId = taskId, title = "Step", order = 0, isCompleted = false)
        val stepId = stepDao.insertStep(step)

        // Act
        stepDao.updateStepCompletion(stepId, true)

        val steps = stepDao.getStepsByTask(taskId).first()

        // Assert
        assertTrue(steps[0].isCompleted)
    }

    @Test
    fun deleteStep() = runBlocking {
        // Arrange
        val task = Task(title = "Tarefa", iconRes = 1, time = "08:00", stars = 3)
        val taskId = taskDao.insertTask(task)
        val step = Step(taskId = taskId, title = "Step", order = 0)
        val stepId = stepDao.insertStep(step)

        // Act
        val stepToDelete = step.copy(id = stepId)
        stepDao.deleteStep(stepToDelete)

        val steps = stepDao.getStepsByTask(taskId).first()

        // Assert
        assertEquals(0, steps.size)
    }

    @Test
    fun cascadeDeleteStepsWhenTaskDeleted() = runBlocking {
        // Arrange
        val task = Task(title = "Tarefa", iconRes = 1, time = "08:00", stars = 3)
        val taskId = taskDao.insertTask(task)

        stepDao.insertStep(Step(taskId = taskId, title = "Step 1", order = 0))
        stepDao.insertStep(Step(taskId = taskId, title = "Step 2", order = 1))

        // Act
        val taskToDelete = task.copy(id = taskId)
        taskDao.deleteTask(taskToDelete)

        val steps = stepDao.getStepsByTask(taskId).first()

        // Assert
        assertEquals(0, steps.size) // Steps foram deletados automaticamente
    }

    @Test
    fun getTaskWithSteps() = runBlocking {
        // Arrange
        val task = Task(title = "Escovar dentes", iconRes = 1, time = "08:00", stars = 3)
        val taskId = taskDao.insertTask(task)

        stepDao.insertStep(Step(taskId = taskId, title = "Step 1", order = 0))
        stepDao.insertStep(Step(taskId = taskId, title = "Step 2", order = 1))
        stepDao.insertStep(Step(taskId = taskId, title = "Step 3", order = 2))

        // Act
        val taskWithSteps = stepDao.getTaskWithSteps(taskId).first()

        // Assert
        assertNotNull(taskWithSteps)
        assertEquals("Escovar dentes", taskWithSteps?.task?.title)
        assertEquals(3, taskWithSteps?.steps?.size)
        assertEquals(3, taskWithSteps?.getTotalSteps())
    }

    @Test
    fun insertMultipleSteps() = runBlocking {
        // Arrange
        val task = Task(title = "Tarefa", iconRes = 1, time = "08:00", stars = 3)
        val taskId = taskDao.insertTask(task)

        val steps = listOf(
            Step(taskId = taskId, title = "Step 1", order = 0),
            Step(taskId = taskId, title = "Step 2", order = 1),
            Step(taskId = taskId, title = "Step 3", order = 2)
        )

        // Act
        val ids = stepDao.insertSteps(steps)
        val retrieved = stepDao.getStepsByTask(taskId).first()

        // Assert
        assertEquals(3, ids.size)
        assertEquals(3, retrieved.size)
    }

    @Test
    fun getStepCountByTask() = runBlocking {
        // Arrange
        val task = Task(title = "Tarefa", iconRes = 1, time = "08:00", stars = 3)
        val taskId = taskDao.insertTask(task)

        stepDao.insertStep(Step(taskId = taskId, title = "Step 1", order = 0))
        stepDao.insertStep(Step(taskId = taskId, title = "Step 2", order = 1))

        // Act
        val count = stepDao.getStepCountByTask(taskId)

        // Assert
        assertEquals(2, count)
    }

    @Test
    fun deleteStepsByTask() = runBlocking {
        // Arrange
        val task = Task(title = "Tarefa", iconRes = 1, time = "08:00", stars = 3)
        val taskId = taskDao.insertTask(task)

        stepDao.insertStep(Step(taskId = taskId, title = "Step 1", order = 0))
        stepDao.insertStep(Step(taskId = taskId, title = "Step 2", order = 1))

        // Act
        stepDao.deleteStepsByTask(taskId)

        val count = stepDao.getStepCountByTask(taskId)

        // Assert
        assertEquals(0, count)
    }
}

