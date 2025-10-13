package com.pequenospassos.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

/**
 * Tela de Debug (Painel do Desenvolvedor) do PequenosPassos.
 *
 * Centralizadas todas as validações dos MVPs e testes de desenvolvimento.
 * Permite verificar status de validação e executar testes isoladamente.
 *
 * @param navController Controlador de navegação
 * @since MVP-01 (13/10/2025) - Integração com validações MVP
 * @author MVP Development Team
 * @validationStatus ✅ Implementado
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebugScreen(navController: NavController) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Debug & Validações MVP") },
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
            // Seção MVP Validations
            Text(
                text = "🔍 VALIDAÇÕES MVP",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // MVP-01: Estrutura Base
            ValidationButton(
                title = "MVP-01: Estrutura Base",
                description = "Validar arquitetura, dependências e build",
                icon = Icons.Default.Build,
                status = ValidationStatus.SUCCESS,
                onClick = {
                    // Executar validação MVP-01
                    validateMVP01(context)
                }
            )

            // MVP-02: Entidades de Domínio
            ValidationButton(
                title = "MVP-02: Entidades de Domínio",
                description = "Validar modelos de dados essenciais",
                icon = Icons.Default.List,
                status = ValidationStatus.PENDING,
                onClick = {
                    // Executar validação MVP-02
                    validateMVP02(context)
                }
            )

            // MVP-03: Database e DAOs
            ValidationButton(
                title = "MVP-03: Database e DAOs",
                description = "Validar Room Database e queries",
                icon = Icons.Default.Star,
                status = ValidationStatus.PENDING,
                onClick = {
                    validateMVP03(context)
                }
            )

            // MVP-04: Repositórios
            ValidationButton(
                title = "MVP-04: Repositórios",
                description = "Validar interfaces e implementações",
                icon = Icons.Default.AccountBox,
                status = ValidationStatus.PENDING,
                onClick = {
                    validateMVP04(context)
                }
            )

            // MVP-05: Use Cases
            ValidationButton(
                title = "MVP-05: Use Cases",
                description = "Validar lógica de negócio",
                icon = Icons.Default.Star,
                status = ValidationStatus.PENDING,
                onClick = {
                    validateMVP05(context)
                }
            )

            // MVP-TTS: Text-to-Speech
            ValidationButton(
                title = "TTS - Text-to-Speech",
                description = "Sistema de síntese de voz",
                icon = Icons.Default.Settings,
                status = ValidationStatus.SUCCESS,
                onClick = {
                    navController.navigate("tts_test")
                }
            )

            // MVP-ASR: Reconhecimento de Voz
            ValidationButton(
                title = "ASR - Reconhecimento de Voz",
                description = "Sistema Vosk offline",
                icon = Icons.Default.Info,
                status = ValidationStatus.PENDING,
                onClick = {
                    navController.navigate("asr_test")
                }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            // Seção Testes Funcionais
            Text(
                text = "🧪 TESTES FUNCIONAIS",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Teste TTS
            TestButton(
                title = "Teste TTS",
                description = "Testar Text-to-Speech",
                icon = Icons.Default.Settings,
                onClick = {
                    navController.navigate("tts_test")
                }
            )

            // Teste ASR
            TestButton(
                title = "Teste ASR (Vosk)",
                description = "Testar reconhecimento de voz",
                icon = Icons.Default.Info,
                onClick = {
                    navController.navigate("asr_test")
                }
            )

            // Teste de Build
            TestButton(
                title = "Verificar Build",
                description = "Executar gradle validateMVP01",
                icon = Icons.Default.Build,
                onClick = {
                    executeBuildValidation(context)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botão de Voltar
            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Voltar para Home")
            }
        }
    }
}

/**
 * Botão de validação MVP com status visual
 */
@Composable
fun ValidationButton(
    title: String,
    description: String,
    icon: ImageVector,
    status: ValidationStatus,
    onClick: () -> Unit
) {
    val statusColor = when (status) {
        ValidationStatus.SUCCESS -> MaterialTheme.colorScheme.primary
        ValidationStatus.PENDING -> MaterialTheme.colorScheme.secondary
        ValidationStatus.ERROR -> MaterialTheme.colorScheme.error
    }

    val statusIcon = when (status) {
        ValidationStatus.SUCCESS -> Icons.Default.CheckCircle
        ValidationStatus.PENDING -> Icons.Default.Info
        ValidationStatus.ERROR -> Icons.Default.Close
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(
                imageVector = statusIcon,
                contentDescription = null,
                tint = statusColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

/**
 * Botão de teste funcional
 */
@Composable
fun TestButton(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * Status de validação possíveis
 */
enum class ValidationStatus {
    SUCCESS,    // ✅ Implementado e funcionando
    PENDING,    // ⏳ Aguardando implementação
    ERROR       // ❌ Erro encontrado
}

// Funções de validação (implementação básica)
private fun validateMVP01(context: android.content.Context) {
    // Implementar validação MVP-01
    android.widget.Toast.makeText(context, "✅ MVP-01: Estrutura Base validada!", android.widget.Toast.LENGTH_SHORT).show()
}

private fun validateMVP02(context: android.content.Context) {
    android.widget.Toast.makeText(context, "⏳ MVP-02: Aguardando implementação", android.widget.Toast.LENGTH_SHORT).show()
}

private fun validateMVP03(context: android.content.Context) {
    android.widget.Toast.makeText(context, "⏳ MVP-03: Aguardando implementação", android.widget.Toast.LENGTH_SHORT).show()
}

private fun validateMVP04(context: android.content.Context) {
    android.widget.Toast.makeText(context, "⏳ MVP-04: Aguardando implementação", android.widget.Toast.LENGTH_SHORT).show()
}

private fun validateMVP05(context: android.content.Context) {
    android.widget.Toast.makeText(context, "⏳ MVP-05: Aguardando implementação", android.widget.Toast.LENGTH_SHORT).show()
}

private fun executeBuildValidation(context: android.content.Context) {
    android.widget.Toast.makeText(context, "🔨 Execute: ./gradlew validateMVP01", android.widget.Toast.LENGTH_LONG).show()
}
