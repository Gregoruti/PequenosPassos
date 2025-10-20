package com.pequenospassos.presentation.screens.execution

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
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
 * @param navController Controlador de navegação
 * @param taskId ID da tarefa a ser executada
 * @param viewModel ViewModel injetado via Hilt
 *
 * @since MVP-07 (17/10/2025)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskExecutionScreen(
    navController: NavController,
    taskId: Long,
    viewModel: TaskExecutionViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    // Carregar tarefa ao iniciar
    LaunchedEffect(taskId) {
        viewModel.loadTask(taskId)
    }

    // Navegar para home ao concluir
    LaunchedEffect(state.isCompleted) {
        if (state.isCompleted) {
            navController.navigate("home") {
                popUpTo("home") { inclusive = false }
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
                // Conteúdo principal
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
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
                    if (!state.currentStep?.imageUrl.isNullOrEmpty()) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            AsyncImage(
                                model = state.currentStep?.imageUrl,
                                contentDescription = "Imagem do passo",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit,
                                onError = {
                                    // Log de erro para debug
                                    println("TaskExecution: Erro ao carregar imagem: ${state.currentStep?.imageUrl}")
                                },
                                onSuccess = {
                                    println("TaskExecution: Imagem carregada com sucesso: ${state.currentStep?.imageUrl}")
                                }
                            )
                        }
                    } else {
                        // Debug: verificar se imageUrl está null ou vazia
                        println("TaskExecution: Step ${state.currentStepIndex + 1} - imageUrl: '${state.currentStep?.imageUrl}'")
                        Spacer(modifier = Modifier.weight(1f))
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

            // Dialog de tempo extra
            if (state.showTimeUpDialog) {
                AlertDialog(
                    onDismissRequest = { viewModel.dismissTimeUpDialog() },
                    title = { Text("⏰ Tempo Esgotado!") },
                    text = { Text("O tempo para este passo terminou. Deseja adicionar mais tempo ou continuar?") },
                    confirmButton = {
                        Button(onClick = { viewModel.nextStep() }) {
                            Text("Próximo")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { viewModel.addExtraTime(30) }) {
                            Text("+30s")
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
