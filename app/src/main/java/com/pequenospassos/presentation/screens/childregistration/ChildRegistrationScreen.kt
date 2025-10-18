package com.pequenospassos.presentation.screens.childregistration

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Tela de Cadastro da Criança.
 *
 * Permite registrar informações básicas da criança:
 * - Nome (obrigatório)
 * - Data de nascimento (obrigatório)
 * - Observações (opcional)
 *
 * @param navController Controlador de navegação
 *
 * @since MVP-07 (18/10/2025)
 * @author PequenosPassos Development Team
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChildRegistrationScreen(
    navController: NavController
) {
    var childName by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var observations by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cadastro da Criança") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Ícone grande
            Text(
                text = "👶",
                fontSize = 64.sp
            )

            Text(
                text = "Vamos começar!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo Nome
            OutlinedTextField(
                value = childName,
                onValueChange = {
                    childName = it
                    showError = false
                },
                label = { Text("Nome da criança *") },
                placeholder = { Text("Ex: João Silva") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = showError && childName.length < 2
            )

            // Campo Data de Nascimento
            OutlinedTextField(
                value = birthDate,
                onValueChange = {
                    // Formatar automaticamente DD/MM/AAAA
                    val digits = it.filter { char -> char.isDigit() }
                    birthDate = when {
                        digits.length <= 2 -> digits
                        digits.length <= 4 -> "${digits.substring(0, 2)}/${digits.substring(2)}"
                        digits.length <= 8 -> "${digits.substring(0, 2)}/${digits.substring(2, 4)}/${digits.substring(4)}"
                        else -> "${digits.substring(0, 2)}/${digits.substring(2, 4)}/${digits.substring(4, 8)}"
                    }
                    showError = false
                },
                label = { Text("Data de nascimento *") },
                placeholder = { Text("DD/MM/AAAA") },
                supportingText = { Text("Formato: DD/MM/AAAA") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = showError && !isValidDate(birthDate)
            )

            // Campo Observações
            OutlinedTextField(
                value = observations,
                onValueChange = { observations = it },
                label = { Text("Observações (opcional)") },
                placeholder = { Text("Ex: preferências, alergias, informações importantes") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5
            )

            // Mensagem de erro
            if (showError) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = errorMessage,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botão Salvar
            Button(
                onClick = {
                    val validation = validateForm(childName, birthDate)
                    if (validation.isValid) {
                        // Salvar dados (implementar posteriormente com banco)
                        // Por enquanto, apenas mostrar sucesso e voltar
                        showError = false

                        // Mostrar mensagem de sucesso
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Cadastro realizado com sucesso!",
                                duration = SnackbarDuration.Short
                            )
                            // Aguardar um pouco e voltar
                            delay(500)
                            navController.navigateUp()
                        }
                    } else {
                        showError = true
                        errorMessage = validation.errorMessage
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("💾 SALVAR")
            }

            // Botão Cancelar
            TextButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancelar")
            }
        }
    }
}

/**
 * Valida se a data está no formato correto DD/MM/AAAA.
 */
private fun isValidDate(date: String): Boolean {
    if (date.length != 10) return false

    val parts = date.split("/")
    if (parts.size != 3) return false

    val day = parts[0].toIntOrNull() ?: return false
    val month = parts[1].toIntOrNull() ?: return false
    val year = parts[2].toIntOrNull() ?: return false

    return day in 1..31 && month in 1..12 && year in 1900..2025
}

/**
 * Resultado da validação do formulário.
 */
private data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String
)

/**
 * Valida o formulário de cadastro.
 */
private fun validateForm(name: String, birthDate: String): ValidationResult {
    return when {
        name.trim().length < 2 -> ValidationResult(
            false,
            "Nome deve ter pelo menos 2 caracteres"
        )
        !isValidDate(birthDate) -> ValidationResult(
            false,
            "Data de nascimento inválida. Use o formato DD/MM/AAAA"
        )
        else -> ValidationResult(true, "")
    }
}
