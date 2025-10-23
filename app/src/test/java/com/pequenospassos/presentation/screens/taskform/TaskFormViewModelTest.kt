package com.pequenospassos.presentation.screens.taskform

import android.net.Uri
import com.pequenospassos.domain.model.Step
import com.pequenospassos.domain.model.TaskCategory
import com.pequenospassos.domain.usecase.GetStepsByTaskUseCase
import com.pequenospassos.domain.usecase.GetTaskByIdUseCase
import com.pequenospassos.domain.usecase.SaveTaskUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

/**
 * Testes unitários para TaskFormViewModel.
 *
 * Valida:
 * - Carregamento de tarefa para edição
 * - Carregamento de steps (v1.9.5)
 * - Eventos de formulário
 * - Validações
 *
 * @since v1.9.5 - Correção de carregamento de steps
 */
@ExperimentalCoroutinesApi
class TaskFormViewModelTest {

    private lateinit var saveTaskUseCase: SaveTaskUseCase
    private lateinit var getTaskByIdUseCase: GetTaskByIdUseCase
    private lateinit var getStepsByTaskUseCase: GetStepsByTaskUseCase
    private lateinit var viewModel: TaskFormViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        saveTaskUseCase = mock(SaveTaskUseCase::class.java)
        getTaskByIdUseCase = mock(GetTaskByIdUseCase::class.java)
        getStepsByTaskUseCase = mock(GetStepsByTaskUseCase::class.java)

        viewModel = TaskFormViewModel(
            saveTaskUseCase,
            getTaskByIdUseCase,
            getStepsByTaskUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadTask deve carregar tarefa e steps corretamente`() = runTest {
        // Given
        val taskId = 1L
        val task = com.pequenospassos.domain.model.Task(
            id = taskId,
            title = "Tarefa Teste",
            description = "Descrição teste",
            iconRes = 0,
            time = "08:00",
            stars = 5,
            category = "HIGIENE",
            imageUrl = "/storage/image.jpg"
        )

        val steps = listOf(
            Step(
                id = 1,
                taskId = taskId,
                order = 0,
                title = "Step 1",
                imageUrl = "/storage/step1.jpg",
                durationSeconds = 60
            ),
            Step(
                id = 2,
                taskId = taskId,
                order = 1,
                title = "Step 2",
                imageUrl = "/storage/step2.jpg",
                durationSeconds = 120
            )
        )

        `when`(getTaskByIdUseCase(taskId)).thenReturn(flowOf(task))
        `when`(getStepsByTaskUseCase(taskId)).thenReturn(flowOf(steps))

        // When
        viewModel.loadTask(taskId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertEquals("Tarefa Teste", state.title)
        assertEquals("Descrição teste", state.description)
        assertEquals("08:00", state.time)
        assertEquals(5, state.stars)
        assertEquals(TaskCategory.HIGIENE_PESSOAL, state.category)
        assertNotNull(state.imageUrl)

        // Verificar que steps foram carregados (v1.9.5)
        assertEquals(2, state.steps.size)
        assertEquals("Step 1", state.steps[0].title)
        assertEquals("Step 2", state.steps[1].title)
        assertEquals(60, state.steps[0].durationSeconds)
        assertEquals(120, state.steps[1].durationSeconds)
    }

    @Test
    fun `onEvent TitleChanged deve atualizar titulo`() = runTest {
        // When
        viewModel.onEvent(TaskFormEvent.TitleChanged("Novo Título"))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals("Novo Título", viewModel.state.value.title)
    }

    @Test
    fun `onEvent CategorySelected deve atualizar categoria`() = runTest {
        // When
        viewModel.onEvent(TaskFormEvent.CategorySelected(TaskCategory.ALIMENTACAO))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(TaskCategory.ALIMENTACAO, viewModel.state.value.category)
    }

    @Test
    fun `onEvent StepAdded deve adicionar step a lista`() = runTest {
        // Given
        val step = Step(
            id = 0,
            taskId = 0,
            order = 0,
            title = "Novo Step",
            durationSeconds = 90
        )

        // When
        viewModel.onEvent(TaskFormEvent.StepAdded(step))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(1, viewModel.state.value.steps.size)
        assertEquals("Novo Step", viewModel.state.value.steps[0].title)
    }

    @Test
    fun `onEvent StepRemoved deve remover step e reordenar`() = runTest {
        // Given
        val steps = listOf(
            Step(id = 1, taskId = 0, order = 0, title = "Step 1"),
            Step(id = 2, taskId = 0, order = 1, title = "Step 2"),
            Step(id = 3, taskId = 0, order = 2, title = "Step 3")
        )

        steps.forEach { viewModel.onEvent(TaskFormEvent.StepAdded(it)) }
        testDispatcher.scheduler.advanceUntilIdle()

        // When - Remover step do meio
        viewModel.onEvent(TaskFormEvent.StepRemoved(1))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(2, viewModel.state.value.steps.size)
        assertEquals("Step 1", viewModel.state.value.steps[0].title)
        assertEquals("Step 3", viewModel.state.value.steps[1].title)
        // Verificar reordenação
        assertEquals(0, viewModel.state.value.steps[0].order)
        assertEquals(1, viewModel.state.value.steps[1].order)
    }
}

