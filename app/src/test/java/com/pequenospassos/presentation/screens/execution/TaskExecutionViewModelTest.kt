package com.pequenospassos.presentation.screens.execution

import com.pequenospassos.domain.model.Step
import com.pequenospassos.domain.model.Task
import com.pequenospassos.domain.usecase.GetStepsByTaskUseCase
import com.pequenospassos.domain.usecase.GetTaskByIdUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Testes unitários para TaskExecutionViewModel.
 *
 * Valida:
 * - Carregamento de tarefa e steps
 * - Timer countdown
 * - Pause/Resume
 * - Navegação entre steps
 * - Adição de tempo extra
 * - Conclusão de tarefa
 *
 * @since MVP-07 (17/10/2025)
 */
@OptIn(ExperimentalCoroutinesApi::class)
class TaskExecutionViewModelTest {

    private lateinit var viewModel: TaskExecutionViewModel
    private lateinit var getTaskByIdUseCase: GetTaskByIdUseCase
    private lateinit var getStepsByTaskUseCase: GetStepsByTaskUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getTaskByIdUseCase = mockk()
        getStepsByTaskUseCase = mockk()
        viewModel = TaskExecutionViewModel(getTaskByIdUseCase, getStepsByTaskUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should load task and steps successfully`() = runTest {
        // Given
        val taskId = 1L
        val task = Task(
            id = taskId,
            title = "Escovar Dentes",
            description = "Rotina de higiene",
            iconRes = 0,
            time = "08:00",
            stars = 5,
            category = "HIGIENE_PESSOAL",
            imageUrl = null
        )
        val steps = listOf(
            Step(id = 1, taskId = taskId, order = 0, title = "Pegar escova", isCompleted = false,
                 imageUrl = null, durationSeconds = 30),
            Step(id = 2, taskId = taskId, order = 1, title = "Aplicar pasta", isCompleted = false,
                 imageUrl = null, durationSeconds = 60)
        )

        coEvery { getTaskByIdUseCase(taskId) } returns flowOf(task)
        coEvery { getStepsByTaskUseCase(taskId) } returns flowOf(steps)

        // When
        viewModel.loadTask(taskId)
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertFalse("isLoading should be false", state.isLoading)
        assertNull("errorMessage should be null", state.errorMessage)
        assertEquals("Task title mismatch", "Escovar Dentes", state.taskTitle)
        assertEquals("Total steps mismatch", 2, state.totalSteps)
        assertEquals("Current step index mismatch", 0, state.currentStepIndex)
        assertEquals("Current step title mismatch", "Pegar escova", state.currentStep?.title)
        assertEquals("Remaining seconds mismatch", 30, state.remainingSeconds)
    }

    @Test
    fun `should show error when task not found`() = runTest {
        // Given
        val taskId = 999L
        coEvery { getTaskByIdUseCase(taskId) } returns flowOf(null)

        // When
        viewModel.loadTask(taskId)
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertEquals("Tarefa não encontrada", state.errorMessage)
    }

    @Test
    fun `should show error when task has no steps`() = runTest {
        // Given
        val taskId = 1L
        val task = Task(
            id = taskId,
            title = "Tarefa Vazia",
            description = "",
            iconRes = 0,
            time = "08:00",
            stars = 3,
            category = "OUTROS",
            imageUrl = null
        )

        coEvery { getTaskByIdUseCase(taskId) } returns flowOf(task)
        coEvery { getStepsByTaskUseCase(taskId) } returns flowOf(emptyList())

        // When
        viewModel.loadTask(taskId)
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertEquals("Esta tarefa não possui passos", state.errorMessage)
    }

    @Test
    fun `should pause and resume timer`() = runTest {
        // Given
        val taskId = 1L
        val task = Task(id = taskId, title = "Task", description = "", iconRes = 0,
                       time = "08:00", stars = 3, category = "OUTROS", imageUrl = null)
        val steps = listOf(
            Step(id = 1, taskId = taskId, order = 0, title = "Step 1", isCompleted = false,
                 imageUrl = null, durationSeconds = 60)
        )

        coEvery { getTaskByIdUseCase(taskId) } returns flowOf(task)
        coEvery { getStepsByTaskUseCase(taskId) } returns flowOf(steps)

        viewModel.loadTask(taskId)
        advanceUntilIdle()

        // When - Pause
        viewModel.togglePause()
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.state.value.isPaused)

        // When - Resume
        viewModel.togglePause()
        advanceUntilIdle()

        // Then
        assertFalse(viewModel.state.value.isPaused)
    }

    @Test
    fun `should navigate to next step`() = runTest {
        // Given
        val taskId = 1L
        val task = Task(id = taskId, title = "Task", description = "", iconRes = 0,
                       time = "08:00", stars = 3, category = "OUTROS", imageUrl = null)
        val steps = listOf(
            Step(id = 1, taskId = taskId, order = 0, title = "Step 1", isCompleted = false,
                 imageUrl = null, durationSeconds = 30),
            Step(id = 2, taskId = taskId, order = 1, title = "Step 2", isCompleted = false,
                 imageUrl = null, durationSeconds = 45)
        )

        coEvery { getTaskByIdUseCase(taskId) } returns flowOf(task)
        coEvery { getStepsByTaskUseCase(taskId) } returns flowOf(steps)

        viewModel.loadTask(taskId)
        advanceUntilIdle()

        // When
        viewModel.nextStep()
        advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertEquals("Current step index should be 1", 1, state.currentStepIndex)
        assertEquals("Current step title should be Step 2", "Step 2", state.currentStep?.title)
        assertEquals("Remaining seconds should be 45", 45, state.remainingSeconds)
    }

    @Test
    fun `should complete task on last step`() = runTest {
        // Given
        val taskId = 1L
        val task = Task(id = taskId, title = "Task", description = "", iconRes = 0,
                       time = "08:00", stars = 3, category = "OUTROS", imageUrl = null)
        val steps = listOf(
            Step(id = 1, taskId = taskId, order = 0, title = "Step 1", isCompleted = false,
                 imageUrl = null, durationSeconds = 30)
        )

        coEvery { getTaskByIdUseCase(taskId) } returns flowOf(task)
        coEvery { getStepsByTaskUseCase(taskId) } returns flowOf(steps)

        viewModel.loadTask(taskId)
        advanceUntilIdle()

        // When
        viewModel.nextStep()
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.state.value.isCompleted)
    }

    @Test
    fun `should add extra time when requested`() = runTest {
        // Given
        val taskId = 1L
        val task = Task(id = taskId, title = "Task", description = "", iconRes = 0,
                       time = "08:00", stars = 3, category = "OUTROS", imageUrl = null)
        val steps = listOf(
            Step(id = 1, taskId = taskId, order = 0, title = "Step 1", isCompleted = false,
                 imageUrl = null, durationSeconds = 60)
        )

        coEvery { getTaskByIdUseCase(taskId) } returns flowOf(task)
        coEvery { getStepsByTaskUseCase(taskId) } returns flowOf(steps)

        viewModel.loadTask(taskId)
        advanceUntilIdle()

        val initialTime = viewModel.state.value.remainingSeconds
        assertTrue("Initial time should be greater than 0", initialTime > 0)

        // When
        viewModel.addExtraTime(30)
        advanceUntilIdle()

        // Then
        assertEquals("Time should be increased by 30 seconds", initialTime + 30, viewModel.state.value.remainingSeconds)
        assertFalse("Dialog should be dismissed", viewModel.state.value.showTimeUpDialog)
    }

    @Test
    fun `should dismiss time up dialog`() = runTest {
        // Given
        val taskId = 1L
        val task = Task(id = taskId, title = "Task", description = "", iconRes = 0,
                       time = "08:00", stars = 3, category = "OUTROS", imageUrl = null)
        val steps = listOf(
            Step(id = 1, taskId = taskId, order = 0, title = "Step 1", isCompleted = false,
                 imageUrl = null, durationSeconds = 1)
        )

        coEvery { getTaskByIdUseCase(taskId) } returns flowOf(task)
        coEvery { getStepsByTaskUseCase(taskId) } returns flowOf(steps)

        viewModel.loadTask(taskId)
        advanceUntilIdle()

        // Wait for timer to reach zero
        advanceTimeBy(2000)
        advanceUntilIdle()

        // Verify dialog is shown
        assertTrue(viewModel.state.value.showTimeUpDialog)

        // When
        viewModel.dismissTimeUpDialog()
        advanceUntilIdle()

        // Then
        assertFalse(viewModel.state.value.showTimeUpDialog)
    }
}
