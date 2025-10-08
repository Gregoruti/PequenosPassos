package com.example.fonovirtual_v2.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pequenospassos.R
import com.example.pequenospassos.BuildConfig

/**
 * Tela Home (Principal) da aplicação PequenosPassos.
 *
 * Exibe o título da aplicação, opções de navegação principais (botões),
 * a versão atual do app (obtida dinamicamente do BuildConfig)
 * e informações adicionais do projeto. Atualizada com modo claro.
 *
 * @param navController Controla a navegação entre as telas.
 * @since 0.2.1
 * @updated 1.0.3 (30/09/2025) - Modo claro implementado, background branco e textos pretos
 * @validationStatus Validado
 */
@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Fundo branco para modo claro
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Título da Tela (agora "FonoVirtual") - Texto preto
        Text(
            text = stringResource(id = R.string.title_screen_home),
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Botão Cadastro (anteriormente "Cadastro/Seleção de Profissional e Paciente")
        Button(
            onClick = { /* TODO: Implementar navegação ou lógica */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Cadastro")
        }

        // Botão Teste Rápido - Navega para o exercício de reconhecimento
        Button(
            onClick = { navController.navigate("simple_recognition") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.home_button_quick_test))
        }

        // Botão Atividades (novo)
        Button(
            onClick = { /* TODO: Implementar navegação para atividades */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Atividades")
        }

        // Botão Resultados (novo)
        Button(
            onClick = { /* TODO: Implementar navegação para resultados */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Resultados")
        }

        // Botão Debug
        Button(
            onClick = { navController.navigate("debug") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.home_button_debug))
        }

        Spacer(modifier = Modifier.weight(1f)) // Empurra as informações da versão para baixo

        // Versão do App (obtida dinamicamente) - Texto preto
        Text(
            text = "Versão: ${BuildConfig.VERSION_NAME}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        // Informação Adicional do Projeto - Texto preto
        Text(
            text = stringResource(id = R.string.home_project_integrator_info),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Preview para a HomeScreen com modo claro.
 * @validationStatus Validado
 */
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(navController = rememberNavController())
    }
}
