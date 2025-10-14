package com.pequenospassos.data.repository

import com.pequenospassos.data.database.dao.StepDao
import com.pequenospassos.domain.model.Step
import com.pequenospassos.domain.model.TaskWithSteps
import com.pequenospassos.domain.repository.StepRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementação do repositório de Step.
 *
 * Fornece acesso aos dados de steps/subtarefas através do DAO,
 * com suporte a operações em lote e relacionamentos.
 *
 * @property dao DAO de Step injetado via Hilt
 * @since MVP-04 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-04
 */
@Singleton
class StepRepositoryImpl @Inject constructor(
    private val dao: StepDao
) : StepRepository {

    override fun getStepsByTask(taskId: Long): Flow<List<Step>> {
        return dao.getStepsByTask(taskId)
    }

    override fun getTaskWithSteps(taskId: Long): Flow<TaskWithSteps?> {
        return dao.getTaskWithSteps(taskId)
    }

    override suspend fun insertStep(step: Step): Result<Long> {
        return try {
            val stepId = dao.insertStep(step)
            Result.success(stepId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun insertSteps(steps: List<Step>): Result<List<Long>> {
        return try {
            val ids = dao.insertSteps(steps)
            Result.success(ids)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateStep(step: Step): Result<Unit> {
        return try {
            dao.updateStep(step)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateStepCompletion(stepId: Long, isCompleted: Boolean): Result<Unit> {
        return try {
            dao.updateStepCompletion(stepId, isCompleted)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteStep(step: Step): Result<Unit> {
        return try {
            dao.deleteStep(step)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteStepsByTask(taskId: Long): Result<Unit> {
        return try {
            dao.deleteStepsByTask(taskId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getStepCountByTask(taskId: Long): Result<Int> {
        return try {
            val count = dao.getStepCountByTask(taskId)
            Result.success(count)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

