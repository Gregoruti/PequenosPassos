package com.example.fonovirtual_v2.ui.exercises.result

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pequenospassos.R

/**
 * Tela de resultados do exercício.
 * Exibe um resumo do desempenho do usuário no exercício de reconhecimento.
 *
 * @param navController Controlador de navegação
 * @param accuracy Porcentagem de acerto (0-100)
 * @param correct Número de palavras corretas
 * @param total Total de palavras do exercício
 * @since 1.0.3
 * @validationStatus Em Desenvolvimento
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseResultScreen(
    navController: NavController,
    accuracy: Int = 0,
    correct: Int = 0,
    total: Int = 0
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.exercise_result_title)) },
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
            // Título do resultado
            Text(
                text = "Exercício Concluído!",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Card com estatísticas detalhadas
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Porcentagem principal
                    Text(
                        text = "$accuracy%",
                        style = MaterialTheme.typography.displayLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    // Descrição do desempenho
                    Text(
                        text = when {
                            accuracy >= 90 -> "Excelente!"
                            accuracy >= 75 -> "Muito Bom!"
                            accuracy >= 60 -> "Bom!"
                            accuracy >= 40 -> "Progredindo!"
                            accuracy >= 25 -> "Continue praticando!"
                            else -> "Mais uma tentativa!"
                        },
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    Divider()

                    // Estatísticas detalhadas
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "$correct",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = "Acertos",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${total - correct}",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = "Erros",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "$total",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = "Total",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Botões de ação
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Botão Tentar Novamente - Navega para um novo exercício
                Button(onClick = {
                    navController.navigate("simple_recognition") {
                        // Remove todas as telas até home da pilha
                        popUpTo("home") { inclusive = false }
                    }
                }) {
                    Text(stringResource(R.string.exercise_result_retry))
                }

                // Botão Voltar ao Início - Retorna para home
                Button(onClick = {
                    navController.navigate("home") {
                        // Remove todas as telas anteriores da pilha
                        popUpTo("home") { inclusive = true }
                    }
                }) {
                    Text(stringResource(R.string.exercise_result_home))
                }
            }
        }
    }
}
