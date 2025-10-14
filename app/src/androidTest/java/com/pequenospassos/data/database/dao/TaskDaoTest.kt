package com.pequenospassos.data.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pequenospassos.data.database.AppDatabase
import com.pequenospassos.domain.model.Task
import com.pequenospassos.domain.model.TaskStatus
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

/**
 * Testes instrumentados para TaskDao.
 *
 * Valida operações CRUD de tarefas no Room Database.
 * Testa ordenação automática por horário.
 *
 * @since MVP-03 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 */
@RunWith(AndroidJUnit4::class)
class TaskDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: TaskDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
        dao = database.taskDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertAndGetTask() = runBlocking {
        // Arrange
        val task = Task(
            title = "Escovar os dentes",
            description = "Escovação matinal",
            iconRes = 1,
            time = "08:00",
            stars = 3,
            status = TaskStatus.PENDING
        )

        // Act
        val taskId = dao.insertTask(task)
        val retrieved = dao.getTaskById(taskId).first()

        // Assert
        assertNotNull(retrieved)
        assertEquals("Escovar os dentes", retrieved?.title)
        assertEquals("08:00", retrieved?.time)
        assertEquals(3, retrieved?.stars)
    }

    @Test
    fun getAllTasksOrderedByTime() = runBlocking {
        // Arrange
        val task1 = Task(title = "Almoço", iconRes = 1, time = "12:00", stars = 5)
        val task2 = Task(title = "Café", iconRes = 1, time = "07:00", stars = 3)
        val task3 = Task(title = "Jantar", iconRes = 1, time = "19:00", stars = 5)

        // Act
        dao.insertTask(task1)
        dao.insertTask(task2)
        dao.insertTask(task3)

        val tasks = dao.getAllTasksOrderedByTime().first()

        // Assert
        assertEquals(3, tasks.size)
        assertEquals("07:00", tasks[0].time) // Café
        assertEquals("12:00", tasks[1].time) // Almoço
        assertEquals("19:00", tasks[2].time) // Jantar
    }

    @Test
    fun updateTask() = runBlocking {
        // Arrange
        val task = Task(
            title = "Tarefa Original",
            iconRes = 1,
            time = "10:00",
            stars = 2
        )
        val taskId = dao.insertTask(task)

        // Act
        val updated = task.copy(id = taskId, title = "Tarefa Atualizada")
        dao.updateTask(updated)

        val retrieved = dao.getTaskById(taskId).first()

        // Assert
        assertEquals("Tarefa Atualizada", retrieved?.title)
    }

    @Test
    fun updateTaskStatus() = runBlocking {
        // Arrange
        val task = Task(
            title = "Tarefa",
            iconRes = 1,
            time = "10:00",
            stars = 3,
            status = TaskStatus.PENDING
        )
        val taskId = dao.insertTask(task)

        // Act
        dao.updateTaskStatus(taskId, TaskStatus.COMPLETED)

        val retrieved = dao.getTaskById(taskId).first()

        // Assert
        assertEquals(TaskStatus.COMPLETED, retrieved?.status)
    }

    @Test
    fun deleteTask() = runBlocking {
        // Arrange
        val task = Task(
            title = "Tarefa",
            iconRes = 1,
            time = "10:00",
            stars = 3
        )
        val taskId = dao.insertTask(task)
        val taskToDelete = task.copy(id = taskId)

        // Act
        dao.deleteTask(taskToDelete)

        val retrieved = dao.getTaskById(taskId).first()

        // Assert
        assertNull(retrieved)
    }

    @Test
    fun getTasksByStatus() = runBlocking {
        // Arrange
        val task1 = Task(title = "Pendente 1", iconRes = 1, time = "08:00", stars = 3, status = TaskStatus.PENDING)
        val task2 = Task(title = "Completa", iconRes = 1, time = "09:00", stars = 3, status = TaskStatus.COMPLETED)
        val task3 = Task(title = "Pendente 2", iconRes = 1, time = "10:00", stars = 3, status = TaskStatus.PENDING)

        dao.insertTask(task1)
        dao.insertTask(task2)
        dao.insertTask(task3)

        // Act
        val pendingTasks = dao.getTasksByStatus(TaskStatus.PENDING).first()
        val completedTasks = dao.getTasksByStatus(TaskStatus.COMPLETED).first()

        // Assert
        assertEquals(2, pendingTasks.size)
        assertEquals(1, completedTasks.size)
        assertEquals("Completa", completedTasks[0].title)
    }

    @Test
    fun getTaskCount() = runBlocking {
        // Arrange
        val task1 = Task(title = "Tarefa 1", iconRes = 1, time = "08:00", stars = 3)
        val task2 = Task(title = "Tarefa 2", iconRes = 1, time = "09:00", stars = 3)

        // Act
        val countBefore = dao.getTaskCount()
        dao.insertTask(task1)
        dao.insertTask(task2)
        val countAfter = dao.getTaskCount()

        // Assert
        assertEquals(0, countBefore)
        assertEquals(2, countAfter)
    }

    @Test
    fun deleteAllTasks() = runBlocking {
        // Arrange
        dao.insertTask(Task(title = "Tarefa 1", iconRes = 1, time = "08:00", stars = 3))
        dao.insertTask(Task(title = "Tarefa 2", iconRes = 1, time = "09:00", stars = 3))

        // Act
        dao.deleteAll()
        val count = dao.getTaskCount()

        // Assert
        assertEquals(0, count)
    }
}

