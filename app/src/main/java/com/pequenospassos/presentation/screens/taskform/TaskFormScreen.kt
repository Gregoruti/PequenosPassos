package com.pequenospassos.presentation.screens.taskform

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pequenospassos.domain.model.Step
import com.pequenospassos.presentation.components.CategoryPicker
import com.pequenospassos.presentation.components.CompactImagePicker
import com.pequenospassos.presentation.components.ImagePicker
import com.pequenospassos.presentation.components.TimerInput

/**
 * Tela de formulário para criar/editar tarefas.
 *
 * Features:
 * - Campos: título, descrição, horário, estrelas
 * - CategoryPicker integrado (obrigatório - MVP-07)
 * - ImagePicker integrado (opcional - MVP-07)
 * - Gerenciamento de steps (adicionar, editar, remover)
 * - Validação antes de salvar
 * - Loading e error handling
 *
 * @param navController Controlador de navegação
 * @param taskId ID da tarefa (null para criar nova)
 * @param viewModel ViewModel injetado via Hilt
 *
 * @since MVP-07 (16/10/2025)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskFormScreen(
    navController: NavController,
    taskId: Long? = null,
    viewModel: TaskFormViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var showStepDialog by remember { mutableStateOf(false) }
    var editingStepIndex by remember { mutableStateOf<Int?>(null) }

    // Carregar tarefa se for edição
    LaunchedEffect(taskId) {
        taskId?.let { viewModel.loadTask(it) }
    }

    // Navegação após salvar com sucesso
    LaunchedEffect(state.isLoading) {
        if (!state.isLoading && state.errorMessage == null && taskId != null) {
            // Se não está mais carregando e não há erro, a tarefa foi salva
            // Você pode adicionar uma flag no state para controlar melhor isso
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (taskId == null) "Nova Tarefa" else "Editar Tarefa") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
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
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Título
                item {
                    OutlinedTextField(
                        value = state.title,
                        onValueChange = { viewModel.onEvent(TaskFormEvent.TitleChanged(it)) },
                        label = { Text("Título *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = state.title.isBlank() && state.errorMessage != null
                    )
                }

                // Descrição
                item {
                    OutlinedTextField(
                        value = state.description,
                        onValueChange = { viewModel.onEvent(TaskFormEvent.DescriptionChanged(it)) },
                        label = { Text("Descrição") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        maxLines = 5
                    )
                }

                // Horário
                item {
                    OutlinedTextField(
                        value = state.time,
                        onValueChange = { viewModel.onEvent(TaskFormEvent.TimeChanged(it)) },
                        label = { Text("Horário (HH:mm) *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        placeholder = { Text("08:00") },
                        supportingText = { Text("Formato: HH:mm (ex: 08:00)") },
                        isError = state.errorMessage != null &&
                                  !state.time.matches(Regex("^([01][0-9]|2[0-3]):[0-5][0-9]$"))
                    )
                }

                // Estrelas
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Estrelas *",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            (1..5).forEach { star ->
                                FilterChip(
                                    selected = state.stars == star,
                                    onClick = { viewModel.onEvent(TaskFormEvent.StarsChanged(star)) },
                                    label = { Text("$star ⭐") }
                                )
                            }
                        }
                    }
                }

                // CategoryPicker
                item {
                    CategoryPicker(
                        selectedCategory = state.category,
                        onCategorySelected = { viewModel.onEvent(TaskFormEvent.CategorySelected(it)) },
                        isError = state.category == null && state.errorMessage != null,
                        errorMessage = if (state.category == null) "Categoria obrigatória" else null
                    )
                }

                // ImagePicker
                item {
                    ImagePicker(
                        imageUri = state.imageUrl,
                        onImageSelected = { viewModel.onEvent(TaskFormEvent.ImageSelected(it)) },
                        onImageRemoved = { viewModel.onEvent(TaskFormEvent.ImageRemoved) },
                        label = "Imagem (Opcional)"
                    )
                }

                // Steps Section
                item {
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Steps *",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Button(
                            onClick = {
                                editingStepIndex = null
                                showStepDialog = true
                            }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Adicionar Step")
                        }
                    }
                }

                // Lista de steps
                itemsIndexed(state.steps) { index, step ->
                    StepCard(
                        step = step,
                        index = index,
                        onEdit = {
                            editingStepIndex = index
                            showStepDialog = true
                        },
                        onDelete = {
                            viewModel.onEvent(TaskFormEvent.StepRemoved(index))
                        }
                    )
                }

                // Mensagem de erro
                state.errorMessage?.let { errorMsg ->
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer
                            )
                        ) {
                            Text(
                                text = errorMsg,
                                modifier = Modifier.padding(16.dp),
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }
                }

                // Botões de ação
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { navController.navigateUp() },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Cancelar")
                        }
                        Button(
                            onClick = { viewModel.onEvent(TaskFormEvent.SaveTask) },
                            modifier = Modifier.weight(1f),
                            enabled = !state.isLoading
                        ) {
                            if (state.isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            } else {
                                Text("Salvar")
                            }
                        }
                    }
                }

                // Espaçamento final
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Loading overlay
            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }

    // Dialog para adicionar/editar step
    if (showStepDialog) {
        StepDialog(
            step = editingStepIndex?.let { state.steps.getOrNull(it) },
            stepNumber = editingStepIndex?.plus(1) ?: (state.steps.size + 1),
            onDismiss = {
                showStepDialog = false
                editingStepIndex = null
            },
            onConfirm = { step ->
                if (editingStepIndex != null) {
                    viewModel.onEvent(TaskFormEvent.StepUpdated(editingStepIndex!!, step))
                } else {
                    viewModel.onEvent(TaskFormEvent.StepAdded(step))
                }
                showStepDialog = false
                editingStepIndex = null
            }
        )
    }
}

/**
 * Card para exibir um step da tarefa.
 */
@Composable
private fun StepCard(
    step: Step,
    index: Int,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Step ${index + 1}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = step.title,
                    style = MaterialTheme.typography.bodyMedium
                )
                if (step.imageUrl != null) {
                    Text(
                        text = "🖼️ Imagem anexada",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = onEdit) {
                    Icon(
                        imageVector = Icons.Default.Add, // Use Edit icon if available
                        contentDescription = "Editar step"
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remover step",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

/**
 * Dialog para adicionar/editar um step.
 */
@Composable
private fun StepDialog(
    step: Step?,
    stepNumber: Int,
    onDismiss: () -> Unit,
    onConfirm: (Step) -> Unit
) {
    var title by remember { mutableStateOf(step?.title ?: "") }
    var imageUri by remember { mutableStateOf<Uri?>(step?.imageUrl?.let { Uri.parse(it) }) }
    var duration by remember { mutableStateOf(step?.durationSeconds ?: 60) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (step == null) "Adicionar Step $stepNumber" else "Editar Step $stepNumber") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título do Step") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2,
                    maxLines = 5
                )

                // Image picker for step (compact)
                CompactImagePicker(
                    imageUri = imageUri,
                    onImageSelected = { uri -> imageUri = uri },
                    onImageRemoved = { imageUri = null },
                    modifier = Modifier.fillMaxWidth()
                )

                // Duration input using existing TimerInput component
                TimerInput(
                    durationSeconds = duration,
                    onDurationChange = { duration = it },
                    label = "Duração do Step",
                    showQuickValues = true,
                    isError = false
                )

                Text(
                    text = "Nota: Duração mínima 5s, máxima 600s.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        val newStep = Step(
                            id = step?.id ?: 0,
                            taskId = step?.taskId ?: 0,
                            order = step?.order ?: (stepNumber - 1),
                            title = title,
                            isCompleted = step?.isCompleted ?: false,
                            imageUrl = imageUri?.toString(),
                            durationSeconds = duration.coerceIn(5, 600)
                        )
                        onConfirm(newStep)
                    }
                },
                enabled = title.isNotBlank()
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
