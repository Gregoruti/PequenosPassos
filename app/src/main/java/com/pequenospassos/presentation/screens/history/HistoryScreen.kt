package com.pequenospassos.presentation.screens.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

/**
 * Tela de Histórico do PequenosPassos.
 *
 * Exibe estatísticas, ferramentas de teste (ASR/TTS) e permite zerar tarefas/estrelas do dia.
 *
 * @param navController Controlador de navegação
 * @since MVP-07/08 (24/10/2025)
 * @author MVP Development Team
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavController, viewModel: HistoryViewModel = hiltViewModel()) {
    val resetMessage by viewModel.resetMessage.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Histórico & Ferramentas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 1. Histórico Semanal (topo, título azul)
            Text(
                text = "Histórico Semanal",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(Modifier.height(8.dp))
            // Atividades mais executadas
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        text = "Atividades mais executadas:",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    val tasksMap = viewModel.tasksMap.collectAsState().value
                    val mostExecuted = viewModel.mostExecutedTasksWeek.collectAsState().value
                    if (mostExecuted.isEmpty()) {
                        Text("Nenhuma atividade executada na semana.", color = Color.Gray)
                    } else {
                        mostExecuted.forEachIndexed { idx, exec ->
                            val taskTitle = tasksMap[exec.taskId]?.title ?: "Tarefa ID: ${exec.taskId}"
                            Text(text = "${idx+1}. $taskTitle (${exec.total}x)")
                        }
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            // Atividades menos executadas
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        text = "Atividades menos executadas:",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    val tasksMap = viewModel.tasksMap.collectAsState().value
                    val leastExecuted = viewModel.leastExecutedTasksWeek.collectAsState().value
                    if (leastExecuted.isEmpty()) {
                        Text("Nenhuma atividade registrada.", color = Color.Gray)
                    } else {
                        leastExecuted.forEachIndexed { idx, exec ->
                            val taskTitle = tasksMap[exec.taskId]?.title ?: "Tarefa ID: ${exec.taskId}"
                            Text(text = "${idx+1}. $taskTitle (${exec.total}x)")
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            // 2. Médias da Semana
            Text(
                text = "Médias da Semana",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(Modifier.padding(16.dp)) {
                    val avgStars = viewModel.averageStarsPerDayWeek.collectAsState().value
                    val avgTasks = viewModel.averageTasksCompletedPerDayWeek.collectAsState().value
                    Text(
                        text = "Média diária de estrelas: $avgStars ⭐",
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Média diária de tarefas: $avgTasks ✅",
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            // 3. Estrelas do dia / Tarefas completadas
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(Modifier.padding(16.dp)) {
                    val starsToday = viewModel.starsToday.collectAsState().value
                    val completed = viewModel.completedTasksCountToday.collectAsState().value
                    val total = viewModel.totalTasksToday.collectAsState().value
                    Text(
                        text = "Estrelas do Dia: $starsToday⭐",
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Tarefas completadas hoje: $completed/$total ✅",
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            // 4. Ferramentas de Teste
            Text(
                text = "Ferramentas de Teste",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { navController.navigate("tts_test") }) {
                Icon(Icons.Default.Settings, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Testar TTS")
            }
            Button(onClick = { navController.navigate("asr_test") }) {
                Icon(Icons.Default.Info, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Testar ASR")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { viewModel.resetTasksToday() }) {
                Text("Zerar dia")
            }
            if (resetMessage != null) {
                Text(
                    text = resetMessage ?: "",
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
