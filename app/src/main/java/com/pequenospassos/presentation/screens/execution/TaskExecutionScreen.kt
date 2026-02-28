package com.pequenospassos.presentation.screens.execution

import android.Manifest
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.pequenospassos.presentation.components.CircularTimer

/**
 * Tela de execução de tarefas com timer visual.
 *
 * Features:
 * - Exibe imagem do step atual (se disponível)
 * - CircularTimer animado baseado em durationSeconds
 * - Indicador de progresso (Passo X de Y)
 * - Botões: Pausar/Retomar, Próximo, Voltar
 * - TTS para leitura do título do step
 * - Navegação: Conclusão → Home
 *
 * Correções v2.5.1 (2026-02-28):
 * - Correção 3: Scroll + imagem adaptativa em landscape (verticalScroll + heightIn)
 *
 * @param navController Controlador de navegação
 * @param taskId ID da tarefa a ser executada
 * @param viewModel ViewModel injetado via Hilt
 *
 * @since MVP-07 (17/10/2025)
 * @updated v2.5.1 (28/02/2026) - Correção landscape
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun TaskExecutionScreen(
    navController: NavController,
    taskId: Long,
    viewModel: TaskExecutionViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    // MVP-14 Fase 5: Estados de reconhecimento de voz
    val isListeningVoice by viewModel.isListeningVoice.collectAsStateWithLifecycle()
    val voiceRecognitionError by viewModel.voiceRecognitionError.collectAsStateWithLifecycle()

    // MVP-14 Fase 6: Permissão de microfone
    val micPermissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)

    // Atualizar ViewModel quando permissão mudar
    LaunchedEffect(micPermissionState.status.isGranted) {
        viewModel.updateMicrophonePermission(micPermissionState.status.isGranted)
    }

    // Solicitar permissão quando pop-up abrir E enableVoiceResponse = true
    // (enableVoiceResponse será verificado no ViewModel)
    LaunchedEffect(state.showTimeUpDialog) {
        if (state.showTimeUpDialog && !micPermissionState.status.isGranted) {
            micPermissionState.launchPermissionRequest()
        }
    }

    // Carregar tarefa ao iniciar
    LaunchedEffect(taskId) {
        viewModel.loadTask(taskId)
    }

    // Navegar para tela de conclusão ao concluir
    LaunchedEffect(state.isCompleted) {
        if (state.isCompleted) {
            // Passar apenas os dados necessários de forma segura
            val taskTitle = state.taskTitle.replace("/", "-") // Remover caracteres problemáticos
            val stars = state.taskStars
            val childName = state.childName.ifEmpty { "Amiguinho" }

            // Usar navegação com argumentos seguros
            navController.navigate("task_completion/${java.net.URLEncoder.encode(taskTitle, "UTF-8")}/$stars/${java.net.URLEncoder.encode(childName, "UTF-8")}") {
                popUpTo("task_list") { inclusive = false }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(state.taskTitle) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (state.isLoading) {
                // Loading
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (state.errorMessage != null) {
                // Erro
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = state.errorMessage ?: "Erro desconhecido",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { navController.navigateUp() }) {
                        Text("Voltar")
                    }
                }
            } else if (state.currentStep != null) {
                // Correção v2.5.1: Detectar orientação para adaptar layout
                val configuration = LocalConfiguration.current
                val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
                val scrollState = rememberScrollState()

                // Conteúdo principal com scroll (Correção 3: suporte a landscape)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Indicador de progresso
                    Text(
                        text = "Passo ${state.currentStepIndex + 1} de ${state.totalSteps}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    // Título do step
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Text(
                            text = state.currentStep?.title ?: "",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }

                    // Imagem do step (se disponível)
                    // Correção v2.5.1: Altura adaptativa para landscape vs portrait
                    if (!state.currentStep?.imageUrl.isNullOrEmpty()) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(
                                    min = if (isLandscape) 120.dp else 150.dp,
                                    max = if (isLandscape) 200.dp else 350.dp
                                ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            AsyncImage(
                                model = state.currentStep?.imageUrl,
                                contentDescription = "Imagem do passo",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit,
                                onError = {
                                    println("TaskExecution: Erro ao carregar imagem: ${state.currentStep?.imageUrl}")
                                },
                                onSuccess = {
                                    println("TaskExecution: Imagem carregada com sucesso: ${state.currentStep?.imageUrl}")
                                }
                            )
                        }
                    } else {
                        println("TaskExecution: Step ${state.currentStepIndex + 1} - imageUrl: '${state.currentStep?.imageUrl}'")
                    }

                    // Timer com barra de progresso horizontal
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Display do tempo
                            Text(
                                text = formatTime(state.remainingSeconds),
                                style = MaterialTheme.typography.displayMedium,
                                color = getTimerColor(
                                    state.remainingSeconds,
                                    state.currentStep?.durationSeconds ?: 60
                                )
                            )

                            // Barra de progresso
                            val progress = if (state.currentStep?.durationSeconds ?: 0 > 0) {
                                state.remainingSeconds.toFloat() / (state.currentStep?.durationSeconds ?: 60).toFloat()
                            } else {
                                0f
                            }

                            LinearProgressIndicator(
                                progress = progress.coerceIn(0f, 1f),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(12.dp),
                                color = getTimerColor(
                                    state.remainingSeconds,
                                    state.currentStep?.durationSeconds ?: 60
                                ),
                                trackColor = MaterialTheme.colorScheme.surfaceVariant
                            )

                            Text(
                                text = "restante de ${formatTime(state.currentStep?.durationSeconds ?: 60)}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Botões de controle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Botão Pausar/Retomar
                        OutlinedButton(
                            onClick = { viewModel.togglePause() },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(if (state.isPaused) "▶️ Retomar" else "⏸️ Pausar")
                        }


                        // Botão Próximo/Concluir
                        Button(
                            onClick = { viewModel.nextStep() },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                if (state.currentStepIndex < state.totalSteps - 1)
                                    "Próximo →"
                                else
                                    "✓ Concluir"
                            )
                        }
                    }
                }
            }

            // MVP-14 Fase 5: Dialog de tempo extra com suporte a ASR
            if (state.showTimeUpDialog) {
                AlertDialog(
                    onDismissRequest = { /* Não permite fechar sem resposta */ },
                    title = {
                        // MVP-14 Fase 5: Removido ícone de microfone que aparecia recortado
                        Text("😊 ${state.timeUpMessage}")
                    },
                    text = {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // MVP-14 Fase 6: Card de permissão se negada
                            if (!micPermissionState.status.isGranted && !isListeningVoice) {
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.errorContainer
                                    )
                                ) {
                                    Column(Modifier.padding(16.dp)) {
                                        Text(
                                            text = "🎤 Permissão Necessária",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(Modifier.height(8.dp))
                                        Text(
                                            text = "Para usar reconhecimento de voz, precisamos da permissão do microfone.",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                        Spacer(Modifier.height(8.dp))
                                        TextButton(
                                            onClick = { micPermissionState.launchPermissionRequest() }
                                        ) {
                                            Text("Solicitar Permissão")
                                        }
                                    }
                                }
                            }

                            // Mensagem de escuta com ícone de microfone
                            if (isListeningVoice) {
                                Text(
                                    text = "🎤 Estou te escutando...",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            // Mensagem de erro de reconhecimento
                            voiceRecognitionError?.let { error ->
                                Text(
                                    text = "❌ $error",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }

                            // Texto explicativo se não está escutando e não há erro
                            if (!isListeningVoice && voiceRecognitionError == null) {
                                Text(
                                    text = "Use os botões abaixo para responder:",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    },
                    confirmButton = {
                        // Botão "Próximo" - SEMPRE VISÍVEL E ATIVO
                        Button(
                            onClick = { viewModel.onManualButtonClick("NEXT") }
                        ) {
                            Text("➡️ Próximo")
                        }
                    },
                    dismissButton = {
                        // Botão "Mais 30s" - SEMPRE VISÍVEL E ATIVO
                        OutlinedButton(
                            onClick = { viewModel.onManualButtonClick("MORE_TIME") }
                        ) {
                            Text("⏱️ +30s")
                        }
                    }
                )
            }
        }
    }
}

/**
 * Formata segundos em formato MM:SS.
 */
private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val secs = seconds % 60
    return String.format("%02d:%02d", minutes, secs)
}

/**
 * Retorna a cor do timer baseada no tempo restante.
 * - Verde: > 60% do tempo
 * - Amarelo: 30-60% do tempo
 * - Vermelho: < 30% do tempo
 */
@Composable
private fun getTimerColor(remainingSeconds: Int, totalSeconds: Int): androidx.compose.ui.graphics.Color {
    val progress = if (totalSeconds > 0) {
        remainingSeconds.toFloat() / totalSeconds.toFloat()
    } else {
        0f
    }

    return when {
        progress > 0.6f -> androidx.compose.ui.graphics.Color(0xFF4CAF50) // Verde
        progress > 0.3f -> androidx.compose.ui.graphics.Color(0xFFFFC107) // Amarelo
        else -> androidx.compose.ui.graphics.Color(0xFFF44336) // Vermelho
    }
}
