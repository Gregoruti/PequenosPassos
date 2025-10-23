package com.pequenospassos.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime

/**
 * Entidade que representa uma recompensa desbloqueável no sistema de gamificação.
 * Recompensas são desbloqueadas ao atingir uma quantidade específica de estrelas.
 *
 * MVP08 - Sistema de Gamificação
 */
@Entity(
    tableName = "rewards",
    foreignKeys = [
        ForeignKey(
            entity = ChildProfile::class,
            parentColumns = ["id"],
            childColumns = ["childId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["childId"]),
        Index(value = ["isUnlocked"])
    ]
)
data class Reward(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    /** ID do perfil da criança */
    val childId: Long,

    /** Título da recompensa */
    val title: String,

    /** Descrição da recompensa */
    val description: String,

    /** Quantidade de estrelas necessárias para desbloquear */
    val starsRequired: Int,

    /** Tipo da recompensa */
    val rewardType: RewardType,

    /** ID do recurso de ícone (se aplicável) */
    val iconResId: Int? = null,

    /** URI da imagem personalizada (se aplicável) */
    val imageUri: String? = null,

    /** Se a recompensa foi desbloqueada */
    val isUnlocked: Boolean = false,

    /** Data e hora do desbloqueio */
    val unlockedAt: LocalDateTime? = null,

    /** Se a recompensa está atualmente ativa/em uso */
    val isActive: Boolean = false
)

