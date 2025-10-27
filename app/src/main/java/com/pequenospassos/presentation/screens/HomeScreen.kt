package com.pequenospassos.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pequenospassos.R
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Tela Home principal do PequenosPassos v2.0
 *
 * Nova estrutura conforme MVP07_WIREFRAMES.md:
 * - Header com saudação personalizada e data/hora
 * - Seção de gamificação (placeholder para MVP08)
 * - Logo Pequenos Passos
 * - Botões de navegação reorganizados
 *
 * Navegação mantida:
 * - Atividades -> task_list
 * - Edição de Atividades -> task_management
 * - Cadastro -> child_registration
 * - Debug -> debug
 *
 * @param navController Controla navegação entre telas
 * @since v1.9.7
 */
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    // Estado para hora atual (atualiza a cada minuto)
    var currentCalendar by remember { mutableStateOf(Calendar.getInstance()) }

    // ✅ CORRIGIDO: Carregar perfil do banco de dados
    val childProfile by viewModel.childProfile.collectAsState()
    val childName = childProfile?.name // Nome carregado do banco

    // MVP-09 v1.11.4: Estatísticas diárias
    val starsToday by viewModel.starsToday.collectAsState()
    val availableTasksCount by viewModel.availableTasksCountToday.collectAsState()

    // Atualizar hora a cada minuto
    LaunchedEffect(Unit) {
        while (true) {
            delay(60000L) // 1 minuto
            currentCalendar = Calendar.getInstance()
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Header com Saudação e Informações
            item {
                HeaderSection(
                    childName = childName,
                    currentCalendar = currentCalendar
                )
            }

            // MVP-09 v1.11.4: Tarefas Disponíveis Hoje
            item {
                AvailableTasksSection(availableTasksCount = availableTasksCount)
            }

            // Seção de Gamificação com estrelas do dia
            item {
                GamificationSection(starsToday = starsToday)
            }

            // Logo Pequenos Passos
            item {
                LogoSection()
            }

            // Botões de Navegação
            item {
                NavigationButtons(
                    onAtividadesClick = { navController.navigate("task_list") },
                    onEdicaoClick = { navController.navigate("task_management") },
                    onCadastroClick = { navController.navigate("child_registration") },
                    onHistoryClick = { navController.navigate("history") }
                )
            }

            // Espaçamento final
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

/**
 * Header com saudação personalizada, data e hora
 */
@Composable
private fun HeaderSection(
    childName: String?,
    currentCalendar: Calendar
) {
    val timeFormatter = remember { SimpleDateFormat("HH:mm", Locale.getDefault()) }
    val currentTime = timeFormatter.format(currentCalendar.time)
    val dayOfWeek = currentCalendar.get(Calendar.DAY_OF_WEEK)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Saudação
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "👋",
                    fontSize = 32.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (childName != null) "Olá, $childName!" else "Olá!",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Data
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "📅",
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Hoje é ${getDayOfWeekName(dayOfWeek)}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Hora
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "🕐",
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Agora são $currentTime",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

/**
 * MVP-09 v1.11.4: Seção que mostra quantas tarefas estão disponíveis para fazer hoje.
 * Atualiza automaticamente conforme tarefas são completadas.
 */
@Composable
private fun AvailableTasksSection(availableTasksCount: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = when {
                    availableTasksCount == 0 -> "🎉 Todas as tarefas de hoje foram completadas!"
                    availableTasksCount == 1 -> "📝 Você tem 1 atividade para fazer hoje!"
                    else -> "📝 Você tem $availableTasksCount atividades para fazer hoje!"
                },
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/**
 * Seção de gamificação com contagem de estrelas do dia.
 * MVP-09 v1.11.4: Atualizada para mostrar estrelas ganhas hoje (não total).
 */
@Composable
private fun GamificationSection(starsToday: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF9E6) // Amarelo claro
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // MVP-09 v1.11.4: Mostra estrelas ganhas HOJE (não total)
            Text(
                text = if (starsToday == 0) {
                    "⭐ Vamos ganhar estrelas? ⭐"
                } else if (starsToday == 1) {
                    "⭐ Você já tem 1 Estrela hoje! ⭐"
                } else {
                    "⭐ Você já tem $starsToday Estrelas hoje! ⭐"
                },
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFFFFB300), // Amarelo escuro
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * Logo do aplicativo (sem texto)
 */
@Composable
private fun LogoSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagem do logo (aumentada em 50%: 120dp -> 180dp)
        Image(
            painter = painterResource(id = R.drawable.logo_pequenos_passos),
            contentDescription = "Logo Pequenos Passos",
            modifier = Modifier.size(180.dp)
        )
    }
}

/**
 * Botões de navegação principal
 * Mantém todas as funcionalidades de navegação existentes:
 * - Atividades -> task_list
 * - Edição de Atividades -> task_management
 * - Cadastro -> child_registration
 * - Histórico -> history (renomeado de Debug)
 */
@Composable
private fun NavigationButtons(
    onAtividadesClick: () -> Unit,
    onEdicaoClick: () -> Unit,
    onCadastroClick: () -> Unit,
    onHistoryClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Botão 1: Atividades (navega para task_list)
        Button(
            onClick = onAtividadesClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                imageVector = Icons.Default.List,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Atividades",
                style = MaterialTheme.typography.titleMedium
            )
        }

        // Botão 2: Edição de Atividades (navega para task_management)
        Button(
            onClick = onEdicaoClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Edição de Atividades",
                style = MaterialTheme.typography.titleMedium
            )
        }

        // Botão 3: Cadastro (navega para child_registration)
        Button(
            onClick = onCadastroClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            )
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Cadastro",
                style = MaterialTheme.typography.titleMedium
            )
        }

        // Botão 4: Histórico (renomeado de Debug - navega para history)
        OutlinedButton(
            onClick = onHistoryClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline
            )
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Histórico",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

/**
 * Converte Calendar.DAY_OF_WEEK para nome em português
 * @param dayOfWeek Valor de Calendar.DAY_OF_WEEK (1=Domingo, 2=Segunda, etc.)
 */
private fun getDayOfWeekName(dayOfWeek: Int): String {
    return when (dayOfWeek) {
        Calendar.SUNDAY -> "Domingo"
        Calendar.MONDAY -> "Segunda-feira"
        Calendar.TUESDAY -> "Terça-feira"
        Calendar.WEDNESDAY -> "Quarta-feira"
        Calendar.THURSDAY -> "Quinta-feira"
        Calendar.FRIDAY -> "Sexta-feira"
        Calendar.SATURDAY -> "Sábado"
        else -> "Dia desconhecido"
    }
}

