package com.pequenospassos.data.repository

import com.pequenospassos.data.database.dao.TaskDao
import com.pequenospassos.domain.model.Task
import com.pequenospassos.domain.model.TaskStatus
import com.pequenospassos.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementação do repositório de Task.
 *
 * Fornece acesso aos dados de tarefas através do DAO,
 * com tratamento de erros usando Result e operações otimizadas.
 *
 * @property dao DAO de Task injetado via Hilt
 * @since MVP-04 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-04
 */
@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val dao: TaskDao
) : TaskRepository {

    override fun getAllTasksOrderedByTime(): Flow<List<Task>> {
        return dao.getAllTasksOrderedByTime()
    }

    override fun getTaskById(id: Long): Flow<Task?> {
        return dao.getTaskById(id)
    }

    override fun getTasksByStatus(status: TaskStatus): Flow<List<Task>> {
        return dao.getTasksByStatus(status)
    }

    override suspend fun insertTask(task: Task): Result<Long> {
        return try {
            val taskId = dao.insertTask(task)
            Result.success(taskId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateTask(task: Task): Result<Unit> {
        return try {
            dao.updateTask(task)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateTaskStatus(taskId: Long, status: TaskStatus): Result<Unit> {
        return try {
            dao.updateTaskStatus(taskId, status)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteTask(task: Task): Result<Unit> {
        return try {
            dao.deleteTask(task)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTaskCount(): Result<Int> {
        return try {
            val count = dao.getTaskCount()
            Result.success(count)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteAllTasks(): Result<Unit> {
        return try {
            dao.deleteAll()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

