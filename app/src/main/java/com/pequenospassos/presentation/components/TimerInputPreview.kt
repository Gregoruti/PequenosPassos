package com.pequenospassos.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pequenospassos.presentation.theme.PequenosPassosTheme

/**
 * Preview do TimerInput Component.
 *
 * Mostra diferentes estados do componente:
 * - Valor padrão (60s)
 * - Valor mínimo (5s)
 * - Valor máximo (600s)
 * - Com erro
 * - Versão simplificada
 * - Dark mode
 *
 * @since MVP-07 (16/10/2025)
 */

@Preview(name = "TimerInput - Padrão (60s)", showBackground = true)
@Composable
fun TimerInputDefaultPreview() {
    PequenosPassosTheme {
        Surface {
            var duration by remember { mutableStateOf(60) }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                TimerInput(
                    durationSeconds = duration,
                    onDurationChange = { duration = it }
                )
            }
        }
    }
}

@Preview(name = "TimerInput - Mínimo (5s)", showBackground = true)
@Composable
fun TimerInputMinPreview() {
    PequenosPassosTheme {
        Surface {
            var duration by remember { mutableStateOf(5) }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                TimerInput(
                    durationSeconds = duration,
                    onDurationChange = { duration = it },
                    label = "Timer Mínimo"
                )
            }
        }
    }
}

@Preview(name = "TimerInput - Máximo (600s)", showBackground = true)
@Composable
fun TimerInputMaxPreview() {
    PequenosPassosTheme {
        Surface {
            var duration by remember { mutableStateOf(600) }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                TimerInput(
                    durationSeconds = duration,
                    onDurationChange = { duration = it },
                    label = "Timer Máximo"
                )
            }
        }
    }
}

@Preview(name = "TimerInput - Valor Médio (120s)", showBackground = true)
@Composable
fun TimerInputMediumPreview() {
    PequenosPassosTheme {
        Surface {
            var duration by remember { mutableStateOf(120) }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                TimerInput(
                    durationSeconds = duration,
                    onDurationChange = { duration = it },
                    label = "Duração do Step"
                )
            }
        }
    }
}

@Preview(name = "TimerInput - Com Erro", showBackground = true)
@Composable
fun TimerInputErrorPreview() {
    PequenosPassosTheme {
        Surface {
            var duration by remember { mutableStateOf(3) } // Valor inválido

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                TimerInput(
                    durationSeconds = duration,
                    onDurationChange = { duration = it },
                    label = "Timer com Erro",
                    isError = true,
                    errorMessage = "Duração deve ser entre 5 e 600 segundos"
                )
            }
        }
    }
}

@Preview(name = "SimpleTimerInput", showBackground = true)
@Composable
fun SimpleTimerInputPreview() {
    PequenosPassosTheme {
        Surface {
            var duration by remember { mutableStateOf(90) }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                SimpleTimerInput(
                    durationSeconds = duration,
                    onDurationChange = { duration = it },
                    label = "Timer Simples"
                )
            }
        }
    }
}

@Preview(name = "TimerInput - Sem Valores Rápidos", showBackground = true)
@Composable
fun TimerInputNoQuickValuesPreview() {
    PequenosPassosTheme {
        Surface {
            var duration by remember { mutableStateOf(45) }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                TimerInput(
                    durationSeconds = duration,
                    onDurationChange = { duration = it },
                    label = "Timer Personalizado",
                    showQuickValues = false
                )
            }
        }
    }
}

@Preview(name = "TimerInput - Múltiplos", showBackground = true)
@Composable
fun TimerInputMultiplePreview() {
    PequenosPassosTheme {
        Surface {
            var duration1 by remember { mutableStateOf(30) }
            var duration2 by remember { mutableStateOf(60) }
            var duration3 by remember { mutableStateOf(120) }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                TimerInput(
                    durationSeconds = duration1,
                    onDurationChange = { duration1 = it },
                    label = "Step 1"
                )

                TimerInput(
                    durationSeconds = duration2,
                    onDurationChange = { duration2 = it },
                    label = "Step 2"
                )

                TimerInput(
                    durationSeconds = duration3,
                    onDurationChange = { duration3 = it },
                    label = "Step 3"
                )
            }
        }
    }
}
