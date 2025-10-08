package com.example.fonovirtual_v2.ui.asr_test

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pequenospassos.R
import com.example.fonovirtual_v2.speech.AsrInitializationStatus
import com.example.fonovirtual_v2.speech.AsrRecognitionStatus
import com.example.fonovirtual_v2.ui.theme.PequenosPassosTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.PermissionState

/**
 * Tela para Teste da Funcionalidade de Reconhecimento Automático de Fala (ASR).
 *
 * Exibe controles para iniciar o reconhecimento, status da inicialização do modelo,
 * status do reconhecimento e os resultados parciais e finais.
 * Utiliza [AsrTestViewModel] para gerenciar o estado e a lógica.
 *
 * @param navController Controlador de navegação.
 * @param viewModel ViewModel para esta tela.
 * @since 1.0.1
 * @updated 1.0.2 - Coleta estados do AsrTestViewModel e atualiza a UI dinamicamente. Adiciona TODO para permissão.
 * @validationStatus Em Teste
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun AsrTestScreen(
    navController: NavController,
    viewModel: AsrTestViewModel = viewModel()
) {
    val initializationStatus by viewModel.initializationStatus.collectAsStateWithLifecycle()
    val recognitionStatus by viewModel.recognitionStatus.collectAsStateWithLifecycle()
    val partialResult by viewModel.lastPartialResult.collectAsStateWithLifecycle()
    val finalResult by viewModel.finalResult.collectAsStateWithLifecycle()
    val errorMessage by viewModel.errorMessage.collectAsStateWithLifecycle()

    // Fixed permission state declaration with explicit type
    val permissionState: PermissionState = rememberPermissionState(
        permission = Manifest.permission.RECORD_AUDIO,
        onPermissionResult = { isGranted ->
            if (isGranted && viewModel.initializationStatus.value is AsrInitializationStatus.Initialized) {
                viewModel.startRecognition()
            }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.title_screen_asr_test)) },
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp) // Reduzido espaçamento para caber mais info
        ) {
            Button(
                onClick = {
                    if (permissionState.status.isGranted) {
                        viewModel.startRecognition()
                    } else {
                        permissionState.launchPermissionRequest()
                    }
                },
                enabled = initializationStatus is AsrInitializationStatus.Initialized &&
                        (recognitionStatus is AsrRecognitionStatus.Idle || recognitionStatus is AsrRecognitionStatus.Error)
            ) {
                Text(stringResource(id = R.string.asr_test_button_listen))
            }

            // Show permission status
            if (!permissionState.status.isGranted) {
                Text(
                    "Permissão de microfone necessária",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            when (initializationStatus) {
                is AsrInitializationStatus.Initializing -> Text("Modelo ASR: Carregando...")
                is AsrInitializationStatus.Initialized -> Text("Modelo ASR: Pronto", color = Color.Green)
                is AsrInitializationStatus.Error -> Text("Modelo ASR: Erro", color = Color.Red)
                else -> Text("Modelo ASR: Ocioso")
            }

            val recStatusText = when (recognitionStatus) {
                is AsrRecognitionStatus.Idle -> stringResource(id = R.string.asr_test_status_label) + " Ocioso"
                is AsrRecognitionStatus.Starting -> stringResource(id = R.string.asr_test_status_label) + " Iniciando..."
                is AsrRecognitionStatus.Listening -> stringResource(id = R.string.asr_test_status_label) + " Escutando..."
                is AsrRecognitionStatus.Error -> stringResource(id = R.string.asr_test_status_label) + " Erro no Reconhecimento"
            }
            Text(recStatusText, style = MaterialTheme.typography.bodyLarge)

            errorMessage?.let {
                Text(it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(stringResource(id = R.string.asr_test_partial_result_label), style = MaterialTheme.typography.titleMedium)
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = partialResult.ifEmpty { "(Aguardando resultado parcial...)" },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(stringResource(id = R.string.asr_test_final_result_label), style = MaterialTheme.typography.titleMedium)
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = finalResult.ifEmpty { "(Aguardando resultado final...)" },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AsrTestScreenPreview() {
    PequenosPassosTheme {
        AsrTestScreen(navController = rememberNavController())
    }
}
