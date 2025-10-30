package com.pequenospassos.data.repository

import com.pequenospassos.data.database.dao.TaskDao
import com.pequenospassos.data.database.dao.TaskCompletionDao
import com.pequenospassos.domain.model.Task
import com.pequenospassos.domain.model.TaskCompletion
import com.pequenospassos.domain.model.TaskStatus
import com.pequenospassos.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementação do repositório de Task.
 *
 * Fornece acesso aos dados de tarefas através do DAO,
 * com tratamento de erros usando Result e operações otimizadas.
 *
 * @property taskDao DAO de Task injetado via Hilt
 * @property completionDao DAO de TaskCompletion injetado via Hilt
 * @since MVP-04 (14/10/2025) - DIA 1 - Fundação
 * @updated MVP-09 (24/10/2025) - Adicionado controle diário de tarefas
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-04
 */
@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao,
    private val completionDao: TaskCompletionDao
) : TaskRepository {

    override fun getAllTasksOrderedByTime(): Flow<List<Task>> {
        return taskDao.getAllTasksOrderedByTime()
    }

    override fun getTaskById(id: Long): Flow<Task?> {
        return taskDao.getTaskById(id)
    }

    override fun getTasksByStatus(status: TaskStatus): Flow<List<Task>> {
        return taskDao.getTasksByStatus(status)
    }

    override suspend fun insertTask(task: Task): Result<Long> {
        return try {
            val taskId = taskDao.insertTask(task)
            Result.success(taskId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateTask(task: Task): Result<Unit> {
        return try {
            taskDao.updateTask(task)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateTaskStatus(taskId: Long, status: TaskStatus): Result<Unit> {
        return try {
            taskDao.updateTaskStatus(taskId, status)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteTask(task: Task): Result<Unit> {
        return try {
            taskDao.deleteTask(task)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTaskCount(): Result<Int> {
        return try {
            val count = taskDao.getTaskCount()
            Result.success(count)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteAllTasks(): Result<Unit> {
        return try {
            taskDao.deleteAll()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ========================================
    // MVP-09: Controle Diário de Tarefas
    // ========================================

    override suspend fun markTaskAsCompleted(
        taskId: String,
        childId: Long,
        starsEarned: Int
    ): Result<Long> {
        println("===== TaskRepositoryImpl: INICIANDO markTaskAsCompleted() =====")
        println("TaskRepositoryImpl: taskId = $taskId")
        println("TaskRepositoryImpl: childId = $childId")
        println("TaskRepositoryImpl: starsEarned = $starsEarned")

        return try {
            println("TaskRepositoryImpl: Criando objeto TaskCompletion...")
            val today = java.time.LocalDate.now()
            val now = java.time.LocalDateTime.now()
            println("TaskRepositoryImpl: today = $today")
            println("TaskRepositoryImpl: now = $now")

            val completion = TaskCompletion(
                taskId = taskId,
                childId = childId,
                date = today,
                completedAt = now,
                starsEarned = starsEarned
            )
            println("TaskRepositoryImpl: TaskCompletion criado = $completion")

            println("TaskRepositoryImpl: Chamando completionDao.insert()...")
            val completionId = completionDao.insert(completion)
            println("TaskRepositoryImpl: ✅ INSERT bem-sucedido! completionId = $completionId")

            Result.success(completionId)
        } catch (e: Exception) {
            println("TaskRepositoryImpl: ❌ EXCEPTION em markTaskAsCompleted")
            println("TaskRepositoryImpl: Tipo: ${e.javaClass.name}")
            println("TaskRepositoryImpl: Mensagem: ${e.message}")
            println("TaskRepositoryImpl: Stack trace:")
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override fun isTaskCompletedToday(taskId: String, childId: Long): Flow<Boolean> {
        return completionDao.isTaskCompletedTodayFlow(
            taskId = taskId,
            childId = childId,
            today = LocalDate.now()
        )
    }

    override fun getStarsForToday(childId: Long): Flow<Int> {
        return completionDao.getStarsForDateFlow(
            childId = childId,
            date = LocalDate.now()
        )
    }

    override fun getCompletedTaskIdsToday(childId: Long): Flow<List<String>> {
        return completionDao.getCompletedTaskIdsForDateFlow(
            childId = childId,
            date = LocalDate.now()
        )
    }

    override fun getCompletionsHistory(childId: Long, limit: Int): Flow<List<TaskCompletion>> {
        return completionDao.getCompletionsHistory(
            childId = childId,
            limit = limit
        )
    }

    override suspend fun deleteCompletionsForToday(childId: Long): Result<Unit> {
        return try {
            completionDao.deleteCompletionsForDate(
                childId = childId,
                date = LocalDate.now()
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteAllCompletions(childId: Long): Result<Unit> {
        return try {
            completionDao.deleteAllForChild(childId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getAvailableTasksCountToday(childId: Long): Flow<Int> {
        return taskDao.getAllTasksOrderedByTime().map { allTasks ->
            val completedIds = completionDao.getCompletedTaskIdsForDate(
                childId = childId,
                date = LocalDate.now()
            )
            allTasks.count { task -> task.id.toString() !in completedIds }
        }
    }

    override suspend fun getStarsForDate(childId: Long, date: LocalDate): Int {
        return completionDao.getStarsForDate(childId, date)
    }

    override suspend fun getTasksCompletedCountForDate(childId: Long, date: LocalDate): Int {
        return completionDao.getTasksCompletedCountForDate(childId, date)
    }
}
