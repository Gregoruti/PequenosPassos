package com.pequenospassos.presentation.screens.tasklist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.pequenospassos.domain.model.TaskCategory
import java.io.File

/**
 * Tela de Lista de Tarefas (Atividades).
 *
 * Área de execução para crianças - SIMPLIFICADA
 * Exibe todas as tarefas cadastradas com opções de:
 * - Visualizar detalhes (categoria, imagens, duração)
 * - Miniatura da imagem da tarefa (à esquerda)
 * - Executar tarefa (botão único)
 *
 * SEM opções de edição/exclusão (prevenção de acidentes).
 * Para editar/excluir, use "Edição de Tarefas" na HomeScreen.
 *
 * Melhorias MVP-07 Fase 3:
 * - Miniatura da imagem principal da tarefa (80dp × 80dp)
 * - Emoji da categoria no card
 * - Nome da categoria
 * - Contador de imagens nos steps (🖼️ × N)
 * - Tempo total dos steps (⏱️ + soma em min/seg)
 * - Layout aprimorado com indicadores visuais
 *
 * @param navController Controlador de navegação
 * @param viewModel ViewModel injetado via Hilt
 *
 * @since MVP-07 (18/10/2025)
 * @version v1.9.2 (20/10/2025) - Removida exclusão, adicionada miniatura
 * @author PequenosPassos Development Team
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    navController: NavController,
    viewModel: TaskListViewModel = hiltViewModel()
) {
    val tasks by viewModel.tasks.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Atividades") },
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
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                tasks.isEmpty() -> {
                    EmptyState(
                        onAddTask = { navController.navigate("task_management") }
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(tasks) { taskWithMetadata ->
                            TaskCard(
                                taskWithMetadata = taskWithMetadata,
                                onExecute = {
                                    navController.navigate("task_execution/${taskWithMetadata.task.id}")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Estado vazio quando não há tarefas.
 */
@Composable
private fun EmptyState(
    onAddTask: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "📝",
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Nenhuma tarefa cadastrada",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Use 'Edição de Tarefas' na tela inicial para criar tarefas!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onAddTask) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Ir para Edição de Tarefas")
        }
    }
}

/**
 * Card de tarefa na lista com indicadores visuais MVP-07.
 * Versão simplificada para área de execução (sem edição/exclusão).
 * Inclui miniatura da imagem da tarefa à esquerda.
 */
@Composable
private fun TaskCard(
    taskWithMetadata: TaskWithMetadata,
    onExecute: () -> Unit
) {
    val task = taskWithMetadata.task
    val category = TaskCategory.fromString(task.category) ?: TaskCategory.default()

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Miniatura da imagem da tarefa (à esquerda)
            // Debug: Log para verificar imageUrl
            println("TaskListScreen: Task '${task.title}' - imageUrl: '${task.imageUrl}'")

            if (!task.imageUrl.isNullOrEmpty()) {
                val imageFile = File(task.imageUrl)
                println("TaskListScreen: Verificando arquivo - exists: ${imageFile.exists()}, path: ${imageFile.absolutePath}")

                // Exibir imagem com AsyncImage (Coil lida com arquivos locais)
                AsyncImage(
                    model = task.imageUrl,
                    contentDescription = "Imagem da tarefa ${task.title}",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(4.dp),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                    onSuccess = {
                        println("TaskListScreen: Imagem carregada com sucesso para '${task.title}'")
                    },
                    onError = { error ->
                        println("TaskListScreen: Erro ao carregar imagem para '${task.title}': ${error.result.throwable?.message}")
                    }
                )
            } else {
                println("TaskListScreen: Usando fallback (emoji) para '${task.title}'")
                // Fallback: Box com emoji da categoria
                Surface(
                    modifier = Modifier.size(80.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = category.emoji,
                            style = MaterialTheme.typography.displayMedium
                        )
                    }
                }
            }

            // Conteúdo da tarefa (à direita)
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Linha 1: Título + Horário
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "🕐 ${task.time}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Linha 2: Nome da categoria + Estrelas
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = category.displayName,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "⭐".repeat(task.stars),
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                // Linha 3: Indicadores de Steps (somente se houver steps)
                if (taskWithMetadata.stepCount > 0) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Contador de steps
                        Text(
                            text = "📝 ${taskWithMetadata.stepCount}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        // Contador de imagens (somente se houver)
                        if (taskWithMetadata.imageCount > 0) {
                            Text(
                                text = "🖼️ ${taskWithMetadata.imageCount}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // Duração total
                        Text(
                            text = "⏱️ ${taskWithMetadata.getFormattedDuration()}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Descrição (se houver)
                if (task.description.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = task.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Botão de execução (full width)
                Button(
                    onClick = onExecute,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("▶️ Executar Tarefa")
                }
            }
        }
    }
}

