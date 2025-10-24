package com.pequenospassos.presentation.screens.childregistration

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.pequenospassos.domain.model.Gender
import kotlinx.coroutines.delay

/**
 * Tela de Cadastro da Criança (CORRIGIDA).
 *
 * Permite registrar informações básicas da criança:
 * - Nome (obrigatório)
 * - Sexo (obrigatório) - Masculino/Feminino
 * - Data de nascimento (opcional)
 * - Observações (opcional)
 *
 * CORREÇÕES v1.10.1:
 * - ✅ Dados agora são salvos no banco Room
 * - ✅ Carrega dados existentes para edição
 * - ✅ Campo Sexo adicionado com seleção visual
 * - ✅ Campos tornados opcionais (exceto nome e sexo)
 * - ✅ Integração com ViewModel e Repository
 *
 * @param navController Controlador de navegação
 * @param viewModel ViewModel injetado via Hilt
 *
 * @since MVP-07 (18/10/2025)
 * @updated MVP-08 (23/10/2025) - Correções de salvamento
 * @author PequenosPassos Development Team
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChildRegistrationScreen(
    navController: NavController,
    viewModel: ChildRegistrationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val existingProfile by viewModel.existingProfile.collectAsStateWithLifecycle()

    var childName by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf<Gender?>(null) }
    var birthDate by remember { mutableStateOf("") }
    var observations by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }

    // Carregar dados existentes quando disponível
    LaunchedEffect(existingProfile) {
        existingProfile?.let { profile ->
            childName = profile.name
            selectedGender = profile.gender
            // birthDate e observations não estão no modelo atual, então ficam vazios
        }
    }

    // Responder ao estado do ViewModel
    LaunchedEffect(uiState) {
        when (uiState) {
            is RegistrationUiState.Success -> {
                snackbarHostState.showSnackbar(
                    message = "Cadastro salvo com sucesso! ✅",
                    duration = SnackbarDuration.Short
                )
                delay(500)
                viewModel.resetState()
                navController.navigateUp()
            }
            is RegistrationUiState.Error -> {
                snackbarHostState.showSnackbar(
                    message = (uiState as RegistrationUiState.Error).message,
                    duration = SnackbarDuration.Long
                )
                viewModel.resetState()
            }
            else -> { /* Idle ou Loading */ }
        }
    }

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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Ícone grande
            Text(
                text = "👶",
                fontSize = 64.sp
            )

            Text(
                text = if (existingProfile != null) "Editar Cadastro" else "Vamos começar!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo Nome (OBRIGATÓRIO)
            OutlinedTextField(
                value = childName,
                onValueChange = { childName = it },
                label = { Text("Nome da criança *") },
                placeholder = { Text("Ex: João Silva") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = uiState !is RegistrationUiState.Loading
            )

            // Campo Sexo (OBRIGATÓRIO) - NOVO!
            Text(
                text = "Sexo *",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectableGroup(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Opção Masculino
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .selectable(
                            selected = selectedGender == Gender.MALE,
                            onClick = { selectedGender = Gender.MALE },
                            role = Role.RadioButton,
                            enabled = uiState !is RegistrationUiState.Loading
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedGender == Gender.MALE)
                            MaterialTheme.colorScheme.primaryContainer
                        else
                            MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "👦", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Masculino",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = if (selectedGender == Gender.MALE) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }

                // Opção Feminino
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .selectable(
                            selected = selectedGender == Gender.FEMALE,
                            onClick = { selectedGender = Gender.FEMALE },
                            role = Role.RadioButton,
                            enabled = uiState !is RegistrationUiState.Loading
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedGender == Gender.FEMALE)
                            MaterialTheme.colorScheme.primaryContainer
                        else
                            MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "👧", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Feminino",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = if (selectedGender == Gender.FEMALE) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Campo Data de Nascimento (OPCIONAL)
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
                },
                label = { Text("Data de nascimento (opcional)") },
                placeholder = { Text("DD/MM/AAAA") },
                supportingText = { Text("Formato: DD/MM/AAAA") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = uiState !is RegistrationUiState.Loading
            )

            // Campo Observações (OPCIONAL)
            OutlinedTextField(
                value = observations,
                onValueChange = { observations = it },
                label = { Text("Observações (opcional)") },
                placeholder = { Text("Ex: preferências, alergias, informações importantes") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5,
                enabled = uiState !is RegistrationUiState.Loading
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botão Salvar
            Button(
                onClick = {
                    if (childName.trim().length < 2) {
                        // Mostrar erro inline
                    } else if (selectedGender == null) {
                        // Mostrar erro inline
                    } else {
                        // Salvar no banco via ViewModel
                        viewModel.saveProfile(
                            name = childName.trim(),
                            gender = selectedGender!!,
                            birthDate = birthDate.takeIf { it.isNotBlank() },
                            observations = observations.takeIf { it.isNotBlank() }
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState !is RegistrationUiState.Loading &&
                         childName.trim().length >= 2 &&
                         selectedGender != null
            ) {
                if (uiState is RegistrationUiState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Salvando...")
                } else {
                    Text("💾 SALVAR")
                }
            }

            // Botão Cancelar
            TextButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState !is RegistrationUiState.Loading
            ) {
                Text("Cancelar")
            }
        }
    }
}


