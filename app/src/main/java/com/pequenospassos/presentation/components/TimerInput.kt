package com.pequenospassos.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

/**
 * TimerInput Component.
 *
 * Componente para seleção de duração de timer em segundos.
 * Exibe um slider visual com valores predefinidos e conversão automática.
 *
 * Features:
 * - Slider de 5 a 600 segundos (5s a 10 minutos)
 * - Valores predefinidos rápidos (5s, 15s, 30s, 60s, 90s, 120s, 300s, 600s)
 * - Conversão automática para formato legível (ex: "2 min 30s")
 * - Validação de range (5-600s)
 * - Suporte a tema Material 3
 *
 * @param durationSeconds Duração atual em segundos (5-600)
 * @param onDurationChange Callback quando a duração muda
 * @param modifier Modificador do componente
 * @param label Label do campo (padrão: "Duração do Timer")
 * @param showQuickValues Se deve exibir botões de valores rápidos (padrão: true)
 * @param isError Se o campo está em estado de erro
 * @param errorMessage Mensagem de erro a exibir
 *
 * @since MVP-07 (16/10/2025)
 * @author PequenosPassos Development Team
 *
 * @sample TimerInputPreview
 */
@Composable
fun TimerInput(
    durationSeconds: Int,
    onDurationChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Duração do Timer",
    showQuickValues: Boolean = true,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    // Garantir que o valor está no range válido
    val validDuration = durationSeconds.coerceIn(5, 600)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Label
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = if (isError) {
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            }
        )

        // Valor atual em formato legível
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "⏱️ Tempo selecionado:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = formatDuration(validDuration),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Slider
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Slider(
                value = validDuration.toFloat(),
                onValueChange = { onDurationChange(it.roundToInt()) },
                valueRange = 5f..600f,
                steps = 118, // 595 valores possíveis / 5 = 119 steps (0-indexed = 118)
                modifier = Modifier.fillMaxWidth(),
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primary,
                    activeTrackColor = MaterialTheme.colorScheme.primary,
                    inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )

            // Indicadores de range (min/max)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "5s",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "600s (10 min)",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Botões de valores rápidos
        if (showQuickValues) {
            QuickValueButtons(
                currentValue = validDuration,
                onValueSelected = onDurationChange
            )
        }

        // Mensagem de erro
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

/**
 * Botões de valores rápidos predefinidos.
 */
@Composable
private fun QuickValueButtons(
    currentValue: Int,
    onValueSelected: (Int) -> Unit
) {
    val quickValues = listOf(5, 15, 30, 60, 90, 120, 300, 600)

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Valores rápidos:",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // Primeira linha: 5s, 15s, 30s, 60s
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            quickValues.take(4).forEach { value ->
                QuickValueChip(
                    value = value,
                    isSelected = value == currentValue,
                    onClick = { onValueSelected(value) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Segunda linha: 90s, 120s, 300s, 600s
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            quickValues.drop(4).forEach { value ->
                QuickValueChip(
                    value = value,
                    isSelected = value == currentValue,
                    onClick = { onValueSelected(value) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

/**
 * Chip individual de valor rápido.
 */
@Composable
private fun QuickValueChip(
    value: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = {
            Text(
                text = formatDurationShort(value),
                style = MaterialTheme.typography.labelMedium
            )
        },
        modifier = modifier,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

/**
 * Formata duração em segundos para formato legível completo.
 * Exemplos:
 * - 5 → "5s"
 * - 60 → "1 min"
 * - 90 → "1 min 30s"
 * - 120 → "2 min"
 * - 600 → "10 min"
 *
 * @param seconds Duração em segundos
 * @return String formatada
 */
fun formatDuration(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60

    return when {
        minutes == 0 -> "${seconds}s"
        remainingSeconds == 0 -> "$minutes min"
        else -> "$minutes min ${remainingSeconds}s"
    }
}

/**
 * Formata duração em segundos para formato curto (para botões).
 * Exemplos:
 * - 5 → "5s"
 * - 60 → "1m"
 * - 90 → "1m30s"
 * - 120 → "2m"
 * - 600 → "10m"
 *
 * @param seconds Duração em segundos
 * @return String formatada curta
 */
fun formatDurationShort(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60

    return when {
        minutes == 0 -> "${seconds}s"
        remainingSeconds == 0 -> "${minutes}m"
        else -> "${minutes}m${remainingSeconds}s"
    }
}

/**
 * Versão simplificada do TimerInput sem valores rápidos.
 * Apenas slider básico.
 */
@Composable
fun SimpleTimerInput(
    durationSeconds: Int,
    onDurationChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Duração"
) {
    TimerInput(
        durationSeconds = durationSeconds,
        onDurationChange = onDurationChange,
        modifier = modifier,
        label = label,
        showQuickValues = false
    )
}

