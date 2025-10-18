package com.pequenospassos.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

/**
 * Componente de timer circular visual com animação.
 *
 * Features:
 * - Canvas circular com progress animado
 * - Texto central com tempo restante em segundos
 * - Cores dinâmicas baseadas no tempo restante:
 *   - Verde: > 60% do tempo
 *   - Amarelo: 30-60% do tempo
 *   - Vermelho: < 30% do tempo
 * - Animação suave
 *
 * @param remainingSeconds Segundos restantes no timer
 * @param totalSeconds Total de segundos configurado
 * @param modifier Modificador Compose
 * @param size Tamanho do timer (padrão: 200dp)
 * @param strokeWidth Largura do traço (padrão: 12dp)
 *
 * @since MVP-07 (17/10/2025)
 */
@Composable
fun CircularTimer(
    remainingSeconds: Int,
    totalSeconds: Int,
    modifier: Modifier = Modifier,
    size: Dp = 200.dp,
    strokeWidth: Dp = 12.dp
) {
    // Calcular progresso (0.0 a 1.0)
    val progress = if (totalSeconds > 0) {
        (remainingSeconds.toFloat() / totalSeconds.toFloat()).coerceIn(0f, 1f)
    } else {
        0f
    }

    // Determinar cor baseada no progresso
    val timerColor = when {
        progress > 0.6f -> Color(0xFF4CAF50) // Verde
        progress > 0.3f -> Color(0xFFFFC107) // Amarelo
        else -> Color(0xFFF44336) // Vermelho
    }

    // Animação suave do progresso
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 300, easing = LinearEasing),
        label = "progress_animation"
    )

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        // Canvas do timer circular
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasSize = this.size.minDimension
            val strokeWidthPx = strokeWidth.toPx()

            // Círculo de fundo (cinza claro)
            drawArc(
                color = Color.LightGray.copy(alpha = 0.3f),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2),
                size = Size(canvasSize - strokeWidthPx, canvasSize - strokeWidthPx),
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
            )

            // Arco de progresso (colorido)
            drawArc(
                color = timerColor,
                startAngle = -90f,
                sweepAngle = 360f * animatedProgress,
                useCenter = false,
                topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2),
                size = Size(canvasSize - strokeWidthPx, canvasSize - strokeWidthPx),
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
            )
        }

        // Texto central com segundos restantes
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = remainingSeconds.toString(),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = timerColor
            )
            Text(
                text = if (remainingSeconds == 1) "segundo" else "segundos",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Helper para formatar segundos em formato legível (MM:SS).
 */
fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val secs = seconds % 60
    return String.format(Locale.getDefault(), "%02d:%02d", minutes, secs)
}

/**
 * Versão compacta do CircularTimer (menor).
 */
@Composable
fun CompactCircularTimer(
    remainingSeconds: Int,
    totalSeconds: Int,
    modifier: Modifier = Modifier
) {
    CircularTimer(
        remainingSeconds = remainingSeconds,
        totalSeconds = totalSeconds,
        modifier = modifier,
        size = 120.dp,
        strokeWidth = 8.dp
    )
}
