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
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Pequenos Passos",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { /* Navegar para Cadastro */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Cadastro")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* Navegar para Teste Rápido */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Teste Rápido")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* Navegar para Atividades */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Atividades")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("debug") }, modifier = Modifier.fillMaxWidth()) {
            Text("Debug")
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Versão: ${BuildConfig.VERSION_NAME}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}
