package com.pequenospassos.presentation.screens.taskmanagement

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import com.pequenospassos.domain.model.TaskCategory
import com.pequenospassos.presentation.screens.tasklist.TaskListViewModel
import com.pequenospassos.presentation.screens.tasklist.TaskWithMetadata

/**
 * Tela de Gerenciamento de Tarefas (Edição de Tarefas).
 *
 * Área de configuração para adultos com opções de:
 * - Visualizar todas as tarefas
 * - Editar tarefa (✏️)
 * - Deletar tarefa (🗑️)
 * - Adicionar nova tarefa (FAB)
 *
 * Esta tela é separada da TaskListScreen (Atividades) para evitar
 * exclusões ou edições acidentais por crianças com TEA.
 *
 * @param navController Controlador de navegação
 * @param viewModel ViewModel injetado via Hilt (reutiliza TaskListViewModel)
 *
 * @since v1.9.2 (20/10/2025)
 * @author PequenosPassos Development Team
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskManagementScreen(
    navController: NavController,
    viewModel: TaskListViewModel = hiltViewModel()
) {
    val tasks by viewModel.tasks.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val deleteSuccess by viewModel.deleteSuccess.collectAsState()
    val deleteError by viewModel.deleteError.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    var taskToDelete by remember { mutableStateOf<TaskWithMetadata?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Mostrar snackbar de sucesso/erro
    LaunchedEffect(deleteSuccess) {
        deleteSuccess?.let {
            snackbarHostState.showSnackbar(
                message = it,
                duration = SnackbarDuration.Short
            )
            viewModel.clearMessages()
        }
    }

    LaunchedEffect(deleteError) {
        deleteError?.let {
            snackbarHostState.showSnackbar(
                message = it,
                duration = SnackbarDuration.Long
            )
            viewModel.clearMessages()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edição de Tarefas") },
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("task_form") }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Nova Tarefa")
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
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
                        onAddTask = { navController.navigate("task_form") }
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(tasks) { taskWithMetadata ->
                            TaskManagementCard(
                                taskWithMetadata = taskWithMetadata,
                                onEdit = {
                                    navController.navigate("task_form/${taskWithMetadata.task.id}")
                                },
                                onDelete = {
                                    taskToDelete = taskWithMetadata
                                    showDeleteDialog = true
                                }
                            )
                        }
                    }
                }
            }
        }

        // Dialog de confirmação de exclusão
        if (showDeleteDialog && taskToDelete != null) {
            AlertDialog(
                onDismissRequest = {
                    showDeleteDialog = false
                    taskToDelete = null
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                },
                title = { Text("Excluir Tarefa?") },
                text = {
                    Column {
                        Text(
                            text = "Tem certeza que deseja excluir a tarefa:",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "\"${taskToDelete?.task?.title}\"",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Esta ação não pode ser desfeita. Todos os passos desta tarefa também serão excluídos.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            taskToDelete?.let {
                                viewModel.deleteTask(it.task.id)
                            }
                            showDeleteDialog = false
                            taskToDelete = null
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text("Excluir")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDeleteDialog = false
                            taskToDelete = null
                        }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
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
            text = "Adicione sua primeira tarefa para começar!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onAddTask) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Adicionar Tarefa")
        }
    }
}

/**
 * Card de tarefa com controles de edição e exclusão.
 */
@Composable
private fun TaskManagementCard(
    taskWithMetadata: TaskWithMetadata,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val task = taskWithMetadata.task
    val category = TaskCategory.fromString(task.category) ?: TaskCategory.default()

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Linha 1: Emoji da categoria + Título + Horário + Botões (Editar e Deletar)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Emoji da categoria
                    Text(
                        text = category.emoji,
                        style = MaterialTheme.typography.titleLarge
                    )
                    // Título da tarefa
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Horário
                    Text(
                        text = "🕐 ${task.time}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    // Botão editar
                    IconButton(
                        onClick = onEdit,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar tarefa",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    // Botão deletar
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Excluir tarefa",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
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
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Contador de steps
                    Text(
                        text = "📝 ${taskWithMetadata.stepCount} ${if (taskWithMetadata.stepCount == 1) "passo" else "passos"}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Contador de imagens (somente se houver)
                    if (taskWithMetadata.imageCount > 0) {
                        Text(
                            text = "🖼️ × ${taskWithMetadata.imageCount}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Duração total
                    Text(
                        text = "⏱️ ${taskWithMetadata.getFormattedDuration()}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
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
        }
    }
}

