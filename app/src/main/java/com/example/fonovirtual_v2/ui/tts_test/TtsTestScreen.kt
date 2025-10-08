package com.example.fonovirtual_v2.ui.tts_test

// Importações Comuns do Jetpack Compose
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.viewmodel.compose.viewModel // Importação adicionada para viewModel
import com.example.fonovirtual_v2.speech.TtsInitializationStatus // Para usar no status
import com.example.fonovirtual_v2.speech.TtsSpeakingStatus // Para usar no status

/**
 * Tela Composable para testar a funcionalidade de Text-to-Speech (TTS).
 *
 * Esta tela permite ao usuário inserir um texto e solicitar ao motor TTS
 * para convertê-lo em fala. Ela também exibirá o status da
 * inicialização do TTS e o status da fala, através de um ViewModel.
 *
 * @param modifier Modificador para aplicar a este Composable.
 * @param viewModel ViewModel que gerencia a lógica e o estado desta tela.
 * @since 0.4.0
 * @validationStatus Validado (Funcionalidade de entrada de texto e síntese de voz operacionais em DD/MM/AAAA - v0.4.0)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TtsTestScreen(
    modifier: Modifier = Modifier,
    viewModel: TtsTestViewModel = viewModel() // ViewModel injetado e utilizado
) {
    val uiState by viewModel.uiState.collectAsState() // Coleta o estado do ViewModel

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Teste de Síntese de Voz (TTS)") }
                // Pode adicionar um botão de navegação para voltar, se aplicável
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Aplicar padding do Scaffold
                .padding(16.dp), // Padding adicional para o conteúdo
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Digite o texto para ser falado:",
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = uiState.currentInputText, // Usa o texto do uiState
                onValueChange = { viewModel.onInputTextChanged(it) }, // Chama o ViewModel na alteração
                label = { Text("Texto para TTS") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )

            Button(
                onClick = {
                    // A lógica de isNotBlank é gerenciada dentro do viewModel.speakText() ou no TextToSpeechModule
                    viewModel.speakText() // Chama a função do ViewModel para iniciar a fala
                },
                modifier = Modifier.fillMaxWidth(),
                // Habilita o botão apenas se o TTS estiver inicializado e não estiver falando
                enabled = uiState.initializationStatus is TtsInitializationStatus.Initialized &&
                          uiState.speakingStatus !is TtsSpeakingStatus.Speaking
            ) {
                Text("Falar Texto")
            }

            // Mensagem de status mais detalhada baseada no uiState
            val statusText = when (val initStatus = uiState.initializationStatus) {
                is TtsInitializationStatus.Idle -> "TTS ocioso, aguardando inicialização."
                is TtsInitializationStatus.Initializing -> "TTS inicializando..."
                is TtsInitializationStatus.Initialized -> {
                    when (val speakStatus = uiState.speakingStatus) {
                        is TtsSpeakingStatus.Idle -> "TTS pronto. Digite e clique em falar."
                        is TtsSpeakingStatus.Speaking -> "TTS falando..."
                        is TtsSpeakingStatus.Error -> "Erro ao falar: ${speakStatus.message}"
                    }
                }
                is TtsInitializationStatus.Error -> "Erro na inicialização do TTS: ${initStatus.message}"
            }

            Text(
                text = "Status: $statusText",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
