package com.pequenospassos.presentation.screens.execution

import android.content.Context
import com.pequenospassos.domain.model.Step
import com.pequenospassos.domain.model.Task
import com.pequenospassos.domain.model.AppSettings
import com.pequenospassos.domain.usecase.GetStepsByTaskUseCase
import com.pequenospassos.domain.usecase.GetTaskByIdUseCase
import com.pequenospassos.domain.usecase.GetChildProfileUseCase
import com.pequenospassos.domain.repository.TaskRepository
import com.pequenospassos.domain.repository.AppSettingsRepository
import com.pequenospassos.presentation.utils.TtsManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

/**
 * Testes unitários para TaskExecutionViewModel.
 *
 * Arquivo: app/src/test/java/.../TaskExecutionViewModelTest.kt
 * Tipo: Unit Test
 * Objetivo: Validar comportamento do ViewModel de execução de tarefas
 * Correlações: TaskExecutionViewModel.kt, AppSettings.kt, AppSettingsRepository.kt
 *
 * Histórico de alterações:
 * - 2025-11-07 (Claude Sonnet 4.5): v2.4.0 - Correção de testes após adição de AppSettingsRepository
 *   - Adicionado mock para Context
 *   - Adicionado mock para AppSettingsRepository
 *   - Corrigida criação de AppSettings mock com todos os campos obrigatórios
 *   - Ajustados mocks para usar getSettings() e getEnableVoiceResponse()
 * - 2025-10-17 (MVP-07): v1.9.3 - Adicionado campo taskStars para tela de conclusão
 * - 2025-10-17 (MVP-07): v1.9.0 - Correções de execução (imageUrl, durationSeconds)
 *
 * Última atualização: 2025-11-07 (v2.4.0)
 * Status: ✅ Compilando sem erros
 *
 * Valida:
 * - Carregamento de tarefa e steps com todos os campos (v1.9.0)
 * - Timer com durationSeconds correto (v1.9.0)
 * - Navegação entre steps
 * - Conclusão de tarefa
 * - Campo taskStars para tela de conclusão (v1.9.3)
 * - Integração com AppSettingsRepository (v2.4.0)
 *
 * @since v1.9.0 - Correções de execução
 * @updated v2.4.0 - Correção de testes com AppSettingsRepository
 */
@ExperimentalCoroutinesApi
class TaskExecutionViewModelTest {

    private lateinit var context: Context
    private lateinit var getTaskByIdUseCase: GetTaskByIdUseCase
    private lateinit var getStepsByTaskUseCase: GetStepsByTaskUseCase
    private lateinit var getChildProfileUseCase: GetChildProfileUseCase
    private lateinit var taskRepository: TaskRepository
    private lateinit var ttsManager: TtsManager
    private lateinit var appSettingsRepository: AppSettingsRepository
    private lateinit var viewModel: TaskExecutionViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        context = Mockito.mock(Context::class.java)
        getTaskByIdUseCase = Mockito.mock(GetTaskByIdUseCase::class.java)
        getStepsByTaskUseCase = Mockito.mock(GetStepsByTaskUseCase::class.java)
        getChildProfileUseCase = Mockito.mock(GetChildProfileUseCase::class.java)
        taskRepository = Mockito.mock(TaskRepository::class.java)
        ttsManager = Mockito.mock(TtsManager::class.java)
        appSettingsRepository = Mockito.mock(AppSettingsRepository::class.java)

        // Mock configurações padrão
        val mockSettings = AppSettings(
            id = "settings",
            isFirstRun = false,
            totalStars = 0,
            currentDate = "2025-11-07",
            lastSyncTimestamp = System.currentTimeMillis(),
            notificationsEnabled = true,
            askExtraTimeAtStep = true,
            enableVoiceResponse = false
        )
        Mockito.`when`(appSettingsRepository.getSettings()).thenReturn(flowOf(mockSettings))
        Mockito.`when`(appSettingsRepository.getEnableVoiceResponse()).thenReturn(flowOf(false))

        viewModel = TaskExecutionViewModel(
            context,
            getTaskByIdUseCase,
            getStepsByTaskUseCase,
            getChildProfileUseCase,
            taskRepository,
            ttsManager,
            appSettingsRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadTask deve carregar tarefa com titulo e stars`() = runTest {
        // Given
        val taskId = 1L
        val task = Task(
            id = taskId,
            title = "Escovar Dentes",
            iconRes = 0,
            time = "08:00",
            stars = 5,
            category = "HIGIENE"
        )

        val steps = listOf(
            Step(
                id = 1,
                taskId = taskId,
                order = 0,
                title = "Molhar escova",
                imageUrl = "/storage/step1.jpg",
                durationSeconds = 60
            )
        )

        Mockito.`when`(getTaskByIdUseCase(taskId)).thenReturn(flowOf(task))
        Mockito.`when`(getStepsByTaskUseCase(taskId)).thenReturn(flowOf(steps))

        // When
        viewModel.loadTask(taskId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Pausar
        val state = viewModel.state.value
        assertEquals("Escovar Dentes", state.taskTitle)
        assertEquals(5, state.taskStars) // v1.9.3 - para tela de conclusão
        assertFalse(state.isLoading)
        assertNull(state.errorMessage)
    }

    @Test
    fun `loadTask deve carregar steps com imageUrl e durationSeconds`() = runTest {
        // Given
        val taskId = 1L
        val task = Task(
            id = taskId,
            title = "Tarefa Teste",
            iconRes = 0,
            time = "08:00",
            stars = 4,
            category = "HIGIENE"
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

        Mockito.`when`(getTaskByIdUseCase(taskId)).thenReturn(flowOf(task))
        Mockito.`when`(getStepsByTaskUseCase(taskId)).thenReturn(flowOf(steps))

        // When
        viewModel.loadTask(taskId)
        testDispatcher.scheduler.advanceUntilIdle()

        // When - Next step
        val state = viewModel.state.value
        assertNotNull(state.currentStep)
        assertEquals("Step 1", state.currentStep?.title)
        assertEquals("/storage/step1.jpg", state.currentStep?.imageUrl) // v1.9.0
        assertEquals(60, state.currentStep?.durationSeconds) // v1.9.0
        assertEquals(60, state.remainingSeconds) // Timer usa durationSeconds correto
        assertEquals(2, state.totalSteps)
    }

    @Test
    fun `nextStep deve avancar para proximo step com timer correto`() = runTest {
        // Given
        val taskId = 1L
        val task = Task(
            id = taskId,
            title = "Tarefa Teste",
            iconRes = 0,
            time = "08:00",
            stars = 3,
            category = "HIGIENE"
        )

        val steps = listOf(
            Step(
                id = 1,
                taskId = taskId,
                order = 0,
                title = "Step 1",
                durationSeconds = 60
            ),
            Step(
                id = 2,
                taskId = taskId,
                order = 1,
                title = "Step 2",
                durationSeconds = 120
            )
        )

        Mockito.`when`(getTaskByIdUseCase(taskId)).thenReturn(flowOf(task))
        Mockito.`when`(getStepsByTaskUseCase(taskId)).thenReturn(flowOf(steps))

        viewModel.loadTask(taskId)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.nextStep()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.state.value
        assertEquals(1, state.currentStepIndex)
        assertEquals("Step 2", state.currentStep?.title)
        assertEquals(120, state.remainingSeconds) // Timer do Step 2
    }

    @Test
    fun `nextStep no ultimo step deve marcar tarefa como completa`() = runTest {
        // Given
        val taskId = 1L
        val task = Task(
            id = taskId,
            title = "Tarefa Teste",
            iconRes = 0,
            time = "08:00",
            stars = 5,
            category = "HIGIENE"
        )

        val steps = listOf(
            Step(
                id = 1,
                taskId = taskId,
                order = 0,
                title = "Único Step",
                durationSeconds = 60
            )
        )

        Mockito.`when`(getTaskByIdUseCase(taskId)).thenReturn(flowOf(task))
        Mockito.`when`(getStepsByTaskUseCase(taskId)).thenReturn(flowOf(steps))

        viewModel.loadTask(taskId)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        viewModel.nextStep()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertTrue(viewModel.state.value.isCompleted)
    }

    @Test
    fun `togglePause deve alternar estado de pausa`() = runTest {
        // Given
        val taskId = 1L
        val task = Task(
            id = taskId,
            title = "Tarefa Teste",
            iconRes = 0,
            time = "08:00",
            stars = 3,
            category = "HIGIENE"
        )

        val steps = listOf(
            Step(
                id = 1,
                taskId = taskId,
                order = 0,
                title = "Step 1",
                durationSeconds = 60
            )
        )

        Mockito.`when`(getTaskByIdUseCase(taskId)).thenReturn(flowOf(task))
        Mockito.`when`(getStepsByTaskUseCase(taskId)).thenReturn(flowOf(steps))

        viewModel.loadTask(taskId)
        testDispatcher.scheduler.advanceUntilIdle()

        // When
        assertFalse(viewModel.state.value.isPaused)

        viewModel.togglePause()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertTrue(viewModel.state.value.isPaused)

        // When - Retomar
        viewModel.togglePause()
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertFalse(viewModel.state.value.isPaused)
    }
}
