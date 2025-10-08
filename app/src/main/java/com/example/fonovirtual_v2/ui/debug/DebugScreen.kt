package com.example.fonovirtual_v2.ui.debug

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pequenospassos.R
import com.example.fonovirtual_v2.ui.theme.PequenosPassosTheme

/**
 * Tela de Debug (Painel do Desenvolvedor).
 *
 * Esta tela serve como um painel central para desenvolvedores acessarem
 * funcionalidades de teste para diferentes módulos da aplicação, como ASR, TTS, etc.
 * Permite verificar o status de validação e testar componentes isoladamente.
 *
 * @param navController Controlador de navegação para permitir o acesso às telas de teste específicas.
 * @since 0.3.1 (Estrutura inicial)
 * @updated 1.0.0 (DD/MM/AAAA) - Adicionado botão para Teste de ASR e uso de string resource.
 * @validationStatus Em Teste
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebugScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.title_screen_debug)) },
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Selecione um módulo para testar:", // Pode ser uma string resource também, se preferir.
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botão para Testar TTS
            Button(
                onClick = { navController.navigate("tts_test_screen") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.debug_button_test_tts))
            }

            // Botão para Testar ASR
            Button(
                onClick = { navController.navigate("asr_test_screen") }, // Navega para a futura tela de teste ASR
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.debug_button_test_asr))
            }

        }
    }
}

/**
 * Preview para a [DebugScreen].
 * @validationStatus Validado
 */
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun DebugScreenPreview() {
    PequenosPassosTheme {
        DebugScreen(navController = rememberNavController())
    }
}
