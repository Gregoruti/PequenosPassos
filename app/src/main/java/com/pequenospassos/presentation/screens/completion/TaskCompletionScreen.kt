package com.pequenospassos.presentation.screens.completion

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pequenospassos.presentation.utils.TtsManager

/**
 * Tela de conclusão de tarefa com feedback positivo.
 *
 * Features:
 * - Mensagens motivacionais variadas (aleatórias)
 * - Exibição das estrelas ganhas com animação
 * - Reforço positivo para crianças com TEA
 * - Botão para voltar à tela inicial
 *
 * @param navController Controlador de navegação
 * @param taskTitle Título da tarefa concluída
 * @param stars Quantidade de estrelas ganhas (1-5)
 * @param childName Nome da criança (opcional, padrão "Amiguinho")
 *
 * @since v1.9.3 (20/10/2025)
 * @author PequenosPassos Development Team
 */
@Composable
fun TaskCompletionScreen(
    navController: NavController,
    taskTitle: String,
    stars: Int,
    childName: String = "Amiguinho",
    ttsManager: TtsManager = TtsManager(LocalContext.current)
) {
    // Decodificar o título se necessário
    val decodedTitle = try {
        java.net.URLDecoder.decode(taskTitle, "UTF-8")
    } catch (e: Exception) {
        taskTitle
    }

    // Mensagens motivacionais variadas com nome da criança
    val congratulationsMessages = listOf(
        "$childName, parabéns! 🎉",
        "$childName, muito bem! 👏",
        "$childName, você conseguiu! ✨",
        "$childName, excelente! 🌟",
        "$childName, você tirou nota 10! 🏆",
        "$childName, incrível! 💪",
        "$childName, perfeito! 🎊",
        "$childName, fantástico! ⭐",
        "$childName, você é demais! 🎯",
        "$childName, campeão! 🥇"
    )

    val successMessages = listOf(
        "Você completou a tarefa com sucesso!",
        "Missão cumprida com perfeição!",
        "Você fez um ótimo trabalho!",
        "Continue assim, você está indo muito bem!",
        "Que orgulho de você!",
        "Você é muito dedicado!",
        "Tarefa realizada com maestria!",
        "Seu esforço valeu a pena!"
    )

    // Selecionar mensagens aleatórias
    val randomCongrats = remember { congratulationsMessages.random() }
    val randomSuccess = remember { successMessages.random() }

    // Animação de entrada
    var visible by remember { mutableStateOf(false) }

    // TTS: Ler mensagem de sucesso ao carregar a tela
    LaunchedEffect(Unit) {
        visible = true
        // Aguardar um pouco para a tela aparecer
        kotlinx.coroutines.delay(500)
        // Remover emojis da mensagem para TTS (apenas texto)
        val ttsMessageCongrats = randomCongrats.replace(Regex("[^\\p{L}\\p{N}\\s,!?.]"), "").trim()
        val ttsMessageSuccess = randomSuccess.replace(Regex("[^\\p{L}\\p{N}\\s,!?.]"), "").trim()

        // Refinamento Fase 6: Mensagem de estrelas ganhas
        val starsText = if (stars == 1) "estrela" else "estrelas"
        val ttsMessageStars = "Você ganhou $stars $starsText!"

        // Refinamento Fase 6: Falar TRÊS mensagens (parabéns + sucesso + estrelas)
        ttsManager.speak(ttsMessageCongrats) // Primeira: Nome + parabéns
        ttsManager.speakQueued(ttsMessageSuccess) // Segunda: Mensagem de sucesso
        ttsManager.speakQueued(ttsMessageStars) // Terceira: Estrelas ganhas
    }

    // Limpar TTS ao sair da tela
    DisposableEffect(Unit) {
        onDispose {
            ttsManager.stop()
        }
    }

    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
                .scale(scale),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Emoji grande
            Text(
                text = "🎉",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = MaterialTheme.typography.displayLarge.fontSize * 2
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Mensagem principal (aleatória)
            Text(
                text = randomCongrats,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Nome da tarefa
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = decodedTitle,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Mensagem de sucesso (aleatória)
            Text(
                text = randomSuccess,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Estrelas ganhas
            StarsDisplay(stars = stars)

            Spacer(modifier = Modifier.height(48.dp))

            // Botão para voltar
            Button(
                onClick = {
                    navController.navigate("task_list") {
                        popUpTo("task_list") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "✓ Voltar para Atividades",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

/**
 * Componente para exibir estrelas ganhas com animação.
 */
@Composable
private fun StarsDisplay(stars: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Você ganhou $stars ${if (stars == 1) "Estrela" else "Estrelas"}!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center
        )

        // Estrelas animadas
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(stars) { index ->
                AnimatedStar(delayMillis = index * 100)
            }
        }
    }
}

/**
 * Estrela individual com animação.
 */
@Composable
private fun AnimatedStar(delayMillis: Int = 0) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(delayMillis.toLong())
        visible = true
    }

    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "star_scale"
    )

    val rotation by animateFloatAsState(
        targetValue = if (visible) 360f else 0f,
        animationSpec = tween(
            durationMillis = 600,
            delayMillis = delayMillis,
            easing = FastOutSlowInEasing
        ),
        label = "star_rotation"
    )

    Icon(
        imageVector = Icons.Default.Star,
        contentDescription = "Estrela",
        modifier = Modifier
            .size(48.dp)
            .scale(scale)
            .graphicsLayer { rotationZ = rotation },
        tint = MaterialTheme.colorScheme.tertiary
    )
}

