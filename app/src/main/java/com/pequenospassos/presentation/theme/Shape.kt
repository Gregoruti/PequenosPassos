package com.pequenospassos.presentation.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Shapes arredondadas e amigáveis para crianças com TEA
 *
 * Características:
 * - Cantos arredondados para visual amigável
 * - Evita cantos pontiagudos (menos ansiedade)
 * - Consistência visual em todos os componentes
 */
val PequenosPassosShapes = Shapes(
    // Extra Small - Usado para chips pequenos e badges
    extraSmall = RoundedCornerShape(8.dp),

    // Small - Usado para botões pequenos e inputs
    small = RoundedCornerShape(12.dp),

    // Medium - Usado para cards e botões padrão
    medium = RoundedCornerShape(16.dp),

    // Large - Usado para cards grandes e dialogs
    large = RoundedCornerShape(20.dp),

    // Extra Large - Usado para bottom sheets e modals
    extraLarge = RoundedCornerShape(24.dp)
)

