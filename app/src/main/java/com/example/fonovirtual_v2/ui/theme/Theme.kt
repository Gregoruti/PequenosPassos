package com.example.fonovirtual_v2.ui.theme

// Importações necessárias para Material 3
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color // Importe a classe Color

// Defina suas paletas de cores.
// Substitua estes exemplos pelas cores reais do seu projeto.
// É comum definir estas cores em um arquivo Color.kt no mesmo pacote.
// Exemplo:
// val Purple40 = Color(0xFF6650a4)
// val PurpleGrey40 = Color(0xFF625b71)
// val Pink40 = Color(0xFF7D5260)
//
// val Purple80 = Color(0xFFD0BCFF)
// val PurpleGrey80 = Color(0xFFCCC2DC)
// val Pink80 = Color(0xFFEFB8C8)

/**
 * Paleta de cores claras para o tema FonoVirtualv2.
 * Adapte estas cores conforme a identidade visual do seu aplicativo.
 * @validationStatus Validado (Modo claro implementado)
 */
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1976D2), // Azul primário
    secondary = Color(0xFF03DAC5), // Verde secundário
    tertiary = Color(0xFF03DAC5), // Verde terciário
    background = Color.White, // Fundo branco
    surface = Color.White, // Superfície branca
    onPrimary = Color.White, // Texto sobre primário branco
    onSecondary = Color.Black, // Texto sobre secundário preto
    onTertiary = Color.Black, // Texto sobre terciário preto
    onBackground = Color.Black, // Texto sobre fundo preto
    onSurface = Color.Black, // Texto sobre superfície preto
)

/**
 * Paleta de cores escuras para o tema FonoVirtualv2.
 * Adapte estas cores conforme a identidade visual do seu aplicativo.
 * @validationStatus Pendente (Cores de exemplo, precisam de definição)
 */
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFBB86FC), // Cor primária de exemplo para tema escuro
    secondary = Color(0xFF03DAC5), // Cor secundária de exemplo para tema escuro
    tertiary = Color(0xFF03DAC5) // Cor terciária de exemplo para tema escuro
)

/**
 * Composable principal do tema da aplicação PequenosPassos.
 *
 * Este Composable envolve a UI da aplicação, aplicando o [MaterialTheme]
 * com as cores e tipografia definidas para o PequenosPassos.
 * Ele agora está configurado para sempre usar o tema claro.
 *
 * @param darkTheme Boolean ignorado - sempre usa tema claro
 * @param content O conteúdo Composable que será envolvido pelo tema.
 * @validationStatus Validado (Modo claro forçado)
 */
@Composable
fun PequenosPassosTheme(
    darkTheme: Boolean = false, // Sempre false para forçar modo claro
    content: @Composable () -> Unit
) {
    // Sempre usa o esquema de cores claro
    val colorScheme = LightColorScheme

    // Aplica o MaterialTheme com o esquema de cores claro
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
