package com.pequenospassos.presentation.screens

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.pequenospassos.BuildConfig

/**
 * Tela Home principal do PequenosPassos.
 * Exibe título, 4 botões principais e versão do app.
 * @param navController Controla navegação entre telas.
 */
@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Espaçamento superior flexível
        Spacer(modifier = Modifier.weight(1f))

        // Título em negrito, centralizado verticalmente no topo
        Text(
            text = "Pequenos Passos",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        // Espaçamento entre título e botões
        Spacer(modifier = Modifier.weight(1f))

        // Botões
        Button(onClick = { navController.navigate("task_management") }, modifier = Modifier.fillMaxWidth()) {
            Text("Edição de Tarefas")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("child_registration") }, modifier = Modifier.fillMaxWidth()) {
            Text("Cadastro")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("task_list") }, modifier = Modifier.fillMaxWidth()) {
            Text("Teste Rápido")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("task_list") }, modifier = Modifier.fillMaxWidth()) {
            Text("Atividades")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("debug") }, modifier = Modifier.fillMaxWidth()) {
            Text("Debug")
        }

        // Espaçamento flexível antes do rodapé
        Spacer(modifier = Modifier.weight(1f))

        // Rodapé: TCC - 2025
        Text(
            text = "TCC - 2025",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Versão do app
        Text(
            text = "Versão ${BuildConfig.VERSION_NAME}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}
