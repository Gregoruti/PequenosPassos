package com.pequenospassos.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * Theme principal do app PequenosPassos
 *
 * Otimizado para crianças com TEA seguindo:
 * - Material Design 3
 * - Princípios de acessibilidade
 * - Design inclusivo e amigável
 */

/**
 * Color scheme otimizado para TEA
 * Baseado em cores calmas e alto contraste
 */
private val PequenosPassosLightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,

    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,

    tertiary = Tertiary,
    onTertiary = OnTertiary,
    tertiaryContainer = TertiaryContainer,
    onTertiaryContainer = OnTertiaryContainer,

    error = Error,
    onError = OnError,
    errorContainer = ErrorContainer,
    onErrorContainer = OnErrorContainer,

    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,

    background = Background,
    onBackground = OnBackground,

    outline = Outline,
    outlineVariant = OutlineVariant
)

/**
 * CompositionLocal para cores extendidas
 * Permite acesso às cores customizadas em todo o app
 */
val LocalExtendedColors = staticCompositionLocalOf { PequenosPassosExtendedColors }

/**
 * Tema principal do PequenosPassos
 *
 * @param darkTheme Define se usa tema escuro (atualmente apenas claro é suportado)
 * @param content Conteúdo a ser envolvido pelo tema
 */
@Composable
fun PequenosPassosTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    // Por enquanto, apenas tema claro é suportado
    // Tema escuro pode causar problemas de legibilidade para crianças com TEA
    val colorScheme = PequenosPassosLightColorScheme

    CompositionLocalProvider(
        LocalExtendedColors provides PequenosPassosExtendedColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = PequenosPassosTypography,
            shapes = PequenosPassosShapes,
            content = content
        )
    }
}

/**
 * Extension property para acessar cores extendidas
 *
 * Uso: MaterialTheme.extendedColors.Success
 */
val MaterialTheme.extendedColors: PequenosPassosExtendedColors
    @Composable
    get() = LocalExtendedColors.current
