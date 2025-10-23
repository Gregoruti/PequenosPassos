package com.pequenospassos.domain.usecase

import com.pequenospassos.domain.repository.StepRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

/**
 * Testes unitários para GetStepsByTaskUseCase.
 *
 * Valida:
 * - Carregamento de steps de uma tarefa
 * - Ordenação por campo 'order'
 * - Steps com imageUrl e durationSeconds
 *
 * @since v1.9.5 - Correção de bug de steps na edição
 */
class GetStepsByTaskUseCaseTest {
    private lateinit var stepRepository: StepRepository
    private lateinit var getStepsByTaskUseCase: GetStepsByTaskUseCase

    @Before
    fun setup() {
        stepRepository = mock(StepRepository::class.java)
        getStepsByTaskUseCase = GetStepsByTaskUseCase(stepRepository)
    }

    @Test
    fun `invoke deve retornar steps ordenados por order`() = runTest {
        // Given
        val taskId = 1L
        val steps = listOf(
            com.pequenospassos.domain.model.Step(
                id = 3,
                taskId = taskId,
                order = 2,
                title = "Step 3",
                durationSeconds = 120
            ),
            com.pequenospassos.domain.model.Step(
                id = 1,
                taskId = taskId,
                order = 0,
                title = "Step 1",
                durationSeconds = 60
            ),
            com.pequenospassos.domain.model.Step(
                id = 2,
                taskId = taskId,
                order = 1,
                title = "Step 2",
                durationSeconds = 90
            )
        )

        `when`(stepRepository.getStepsByTask(taskId)).thenReturn(flowOf(steps))

        // When
        val result = getStepsByTaskUseCase(taskId).first()

        // Then
        assertNotNull(result)
        assertEquals(3, result.size)
        assertEquals("Step 1", result[0].title)
        assertEquals("Step 2", result[1].title)
        assertEquals("Step 3", result[2].title)
        assertEquals(0, result[0].order)
        assertEquals(1, result[1].order)
        assertEquals(2, result[2].order)
    }

    @Test
    fun `invoke deve retornar steps com imageUrl e durationSeconds`() = runTest {
        // Given
        val taskId = 1L
        val steps = listOf(
            com.pequenospassos.domain.model.Step(
                id = 1,
                taskId = taskId,
                order = 0,
                title = "Step com imagem",
                imageUrl = "/storage/image1.jpg",
                durationSeconds = 120
            )
        )

        `when`(stepRepository.getStepsByTask(taskId)).thenReturn(flowOf(steps))

        // When
        val result = getStepsByTaskUseCase(taskId).first()

        // Then
        assertNotNull(result[0].imageUrl)
        assertEquals("/storage/image1.jpg", result[0].imageUrl)
        assertEquals(120, result[0].durationSeconds)
    }

    @Test
    fun `invoke deve retornar lista vazia quando tarefa nao tem steps`() = runTest {
        // Given
        val taskId = 999L
        `when`(stepRepository.getStepsByTask(taskId)).thenReturn(flowOf(emptyList()))

        // When
        val result = getStepsByTaskUseCase(taskId).first()

        // Then
        assertTrue(result.isEmpty())
    }
}

