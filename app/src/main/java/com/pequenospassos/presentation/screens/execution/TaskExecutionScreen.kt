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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
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
    viewModel: TaskExecutionViewModel = viewModel()
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
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Indicador de progresso
                    Text(
                        text = "Passo ${state.currentStepIndex + 1} de ${state.totalSteps}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Imagem do step (se disponível)
                    state.currentStep?.imageUrl?.let { imageUrl ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .aspectRatio(1f),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            AsyncImage(
                                model = Uri.parse(imageUrl),
                                contentDescription = "Imagem do passo",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Timer circular
                    CircularTimer(
                        remainingSeconds = state.remainingSeconds,
                        totalSeconds = state.currentStep?.durationSeconds ?: 60
                    )

                    Spacer(modifier = Modifier.height(24.dp))

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

                    Spacer(modifier = Modifier.height(32.dp))

                    // Botões de controle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Botão Pausar/Retomar
                        OutlinedButton(
                            onClick = { viewModel.togglePause() },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(if (state.isPaused) "▶️ Retomar" else "⏸️ Pausar")
                        }

                        Spacer(modifier = Modifier.width(16.dp))

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
