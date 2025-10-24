package com.pequenospassos.data.repository

import com.pequenospassos.data.database.dao.TaskCompletionDao
import com.pequenospassos.domain.model.TaskCompletion
import com.pequenospassos.domain.repository.TaskCompletionRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

/**
 * Implementação do repositório de TaskCompletion.
 *
 * Gerencia o acesso aos dados de conclusões de tarefas através do DAO,
 * seguindo o padrão Repository da Clean Architecture.
 *
 * ## Responsabilidades:
 * - Encapsula lógica de acesso ao banco de dados
 * - Expõe dados reativos via Flow
 * - Converte entre camadas (Data ↔ Domain)
 * - Gerencia operações de leitura e escrita
 *
 * @param taskCompletionDao DAO para acesso ao banco de dados
 * @since MVP-09 (24/01/2025)
 * @author PequenosPassos Development Team
 * @validationStatus ⏳ Em desenvolvimento
 */
class TaskCompletionRepositoryImpl @Inject constructor(
    private val taskCompletionDao: TaskCompletionDao
) : TaskCompletionRepository {

    override suspend fun saveCompletion(completion: TaskCompletion): Long {
        return taskCompletionDao.insert(completion)
    }

    override suspend fun isTaskCompletedToday(
        taskId: String,
        childId: Long,
        date: LocalDate
    ): Boolean {
        return taskCompletionDao.isTaskCompletedOnDate(taskId, childId, date)
    }

    override fun getCompletionsForDate(
        childId: Long,
        date: LocalDate
    ): Flow<List<TaskCompletion>> {
        return taskCompletionDao.getCompletionsForDate(childId, date)
    }

    override fun getCompletionsForTask(
        taskId: String,
        childId: Long
    ): Flow<List<TaskCompletion>> {
        return taskCompletionDao.getCompletionsForTaskFlow(taskId, childId)
    }

    override suspend fun getTotalStarsForDate(
        childId: Long,
        date: LocalDate
    ): Int {
        return taskCompletionDao.getStarsForDate(childId, date)
    }

    override fun getCompletionHistory(
        childId: Long,
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<TaskCompletion>> {
        return taskCompletionDao.getCompletionsInDateRange(childId, startDate, endDate)
    }

    override suspend fun deleteCompletion(completion: TaskCompletion) {
        taskCompletionDao.deleteById(completion.id)
    }

    override suspend fun deleteCompletionsForDate(
        childId: Long,
        date: LocalDate
    ) {
        taskCompletionDao.deleteCompletionsForDate(childId, date)
    }

    override suspend fun deleteAllCompletionsForChild(childId: Long) {
        taskCompletionDao.deleteAllForChild(childId)
    }

    override suspend fun getLastCompletionForTask(
        taskId: String,
        childId: Long
    ): TaskCompletion? {
        return taskCompletionDao.getLastCompletionForTask(taskId, childId)
    }
}

