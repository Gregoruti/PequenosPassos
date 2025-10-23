package com.pequenospassos.presentation.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PersonAdd
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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
fun HomeScreen(navController: NavController) {
    // Estado para hora atual (atualiza a cada minuto)
    var currentTime by remember { mutableStateOf(LocalTime.now()) }
    var currentDayOfWeek by remember { mutableStateOf(LocalDate.now().dayOfWeek) }

    // TODO: Carregar do banco de dados quando ChildProfile estiver implementado
    val childName: String? = null // Placeholder
    val totalStars = 0 // Placeholder até MVP08

    // Atualizar hora a cada minuto
    LaunchedEffect(Unit) {
        while (true) {
            delay(60000L) // 1 minuto
            currentTime = LocalTime.now()
            currentDayOfWeek = LocalDate.now().dayOfWeek
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
                    currentTime = currentTime,
                    currentDayOfWeek = currentDayOfWeek
                )
            }

            // Seção de Gamificação (placeholder)
            item {
                GamificationSection(totalStars = totalStars)
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
                    onDebugClick = { navController.navigate("debug") }
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
    currentTime: LocalTime,
    currentDayOfWeek: DayOfWeek
) {
    val timeFormatter = remember { DateTimeFormatter.ofPattern("HH:mm") }

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
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "Ícone de calendário",
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Hoje é ${getDayOfWeekName(currentDayOfWeek)}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Hora
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = "Ícone de relógio",
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Agora são ${currentTime.format(timeFormatter)}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

/**
 * Seção de gamificação com contagem de estrelas
 * Placeholder até implementação do MVP08
 */
@Composable
private fun GamificationSection(totalStars: Int) {
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
            Text(
                text = "⭐ Você já tem $totalStars Estrelas! ⭐",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFFFFB300), // Amarelo escuro
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Exibir até 5 estrelas visualmente
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val starsToShow = minOf(totalStars, 5)
                repeat(starsToShow) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Estrela",
                        tint = Color(0xFFFFD700), // Dourado
                        modifier = Modifier.size(32.dp)
                    )
                }

                if (totalStars > 5) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "x $totalStars",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFFFFB300)
                    )
                }
            }
        }
    }
}

/**
 * Logo e nome do aplicativo
 */
@Composable
private fun LogoSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TODO: Adicionar imagem do logo quando disponível em /images/
        // Image(
        //     painter = painterResource(id = R.drawable.logo_pequenos_passos),
        //     contentDescription = "Logo Pequenos Passos",
        //     modifier = Modifier.size(120.dp)
        // )
        // Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Pequenos Passos",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Botões de navegação principal
 * Mantém todas as funcionalidades de navegação existentes:
 * - Atividades (era "Teste Rápido") -> task_list
 * - Edição de Atividades (era "Edição de Tarefas") -> task_management
 * - Cadastro -> child_registration
 * - Debug -> debug
 *
 * REMOVIDO: Botão "Teste Rápido" (duplicado)
 */
@Composable
private fun NavigationButtons(
    onAtividadesClick: () -> Unit,
    onEdicaoClick: () -> Unit,
    onCadastroClick: () -> Unit,
    onDebugClick: () -> Unit
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
                imageVector = Icons.Default.Assignment,
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
                imageVector = Icons.Default.PersonAdd,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Cadastro",
                style = MaterialTheme.typography.titleMedium
            )
        }

        // Botão 4: Debug (navega para debug)
        OutlinedButton(
            onClick = onDebugClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline
            )
        ) {
            Icon(
                imageVector = Icons.Default.BugReport,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Debug",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

/**
 * Converte DayOfWeek para nome em português
 */
private fun getDayOfWeekName(dayOfWeek: DayOfWeek): String {
    return when (dayOfWeek) {
        DayOfWeek.MONDAY -> "Segunda-feira"
        DayOfWeek.TUESDAY -> "Terça-feira"
        DayOfWeek.WEDNESDAY -> "Quarta-feira"
        DayOfWeek.THURSDAY -> "Quinta-feira"
        DayOfWeek.FRIDAY -> "Sexta-feira"
        DayOfWeek.SATURDAY -> "Sábado"
        DayOfWeek.SUNDAY -> "Domingo"
    }
}

