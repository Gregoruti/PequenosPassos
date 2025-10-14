package com.pequenospassos.presentation.theme

import androidx.compose.ui.graphics.Color

/**
 * Cores otimizadas para crianças com TEA
 *
 * Baseado em princípios de acessibilidade e design inclusivo:
 * - Cores calmas e não agressivas
 * - Alto contraste para legibilidade
 * - Consistência visual
 */

// Cores primárias
val Primary = Color(0xFF4A90E2)        // Azul calmo
val OnPrimary = Color.White
val PrimaryContainer = Color(0xFFE3F2FD)
val OnPrimaryContainer = Color(0xFF1565C0)

// Cores secundárias
val Secondary = Color(0xFF4CAF50)      // Verde sucesso
val OnSecondary = Color.White
val SecondaryContainer = Color(0xFFE8F5E8)
val OnSecondaryContainer = Color(0xFF1B5E20)

// Cores terciárias
val Tertiary = Color(0xFFFF9500)       // Laranja atenção
val OnTertiary = Color.White
val TertiaryContainer = Color(0xFFFFE0B2)
val OnTertiaryContainer = Color(0xFFE65100)

// Cores de erro
val Error = Color(0xFFF44336)          // Vermelho cancelada
val OnError = Color.White
val ErrorContainer = Color(0xFFFFEBEE)
val OnErrorContainer = Color(0xFFC62828)

// Cores de superfície
val Surface = Color.White
val OnSurface = Color(0xFF212121)
val SurfaceVariant = Color(0xFFF5F5F5)
val OnSurfaceVariant = Color(0xFF757575)

// Cores de fundo
val Background = Color(0xFFFAFAFA)
val OnBackground = Color(0xFF212121)

// Cores de contorno
val Outline = Color(0xFFE0E0E0)
val OutlineVariant = Color(0xFFEEEEEE)

/**
 * Cores extendidas específicas do app
 *
 * Usadas para status de tarefas e feedback visual
 */
object PequenosPassosExtendedColors {
    // Cores semânticas
    val Success = Color(0xFF4CAF50)
    val Warning = Color(0xFFFF9500)
    val Info = Color(0xFF2196F3)

    // Cores de status de tarefas
    val Pending = Color(0xFFFF9500)
    val Completed = Color(0xFF4CAF50)
    val Canceled = Color(0xFFF44336)

    // Fundos de status de tarefas (mais claros)
    val TaskPending = Color(0xFFFFF3E0)      // Laranja claro
    val TaskCompleted = Color(0xFFE8F5E8)    // Verde claro
    val TaskCanceled = Color(0xFFFFEBEE)     // Vermelho claro

    // Cores auxiliares
    val StarYellow = Color(0xFFFFD700)       // Dourado para estrelas
    val StarGray = Color(0xFFE0E0E0)         // Cinza para estrelas não ativas
}

