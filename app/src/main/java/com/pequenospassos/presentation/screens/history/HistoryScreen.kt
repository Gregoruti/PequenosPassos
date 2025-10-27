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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            Text(
                text = "Ferramentas de Teste",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Teste TTS
            Button(onClick = { navController.navigate("tts_test") }) {
                Icon(Icons.Default.Settings, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Testar TTS")
            }
            // Teste ASR
            Button(onClick = { navController.navigate("asr_test") }) {
                Icon(Icons.Default.Info, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Testar ASR")
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Botão: Zerar Dia (renomeado)
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
            // ... Adicione aqui as estatísticas e ferramentas de zerar tarefas/estrelas se já implementadas ...
        }
    }
}
