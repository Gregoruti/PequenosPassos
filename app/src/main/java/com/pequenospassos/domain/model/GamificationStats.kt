package com.pequenospassos.domain.model

/**
 * Modelo de dados que representa as estatísticas de gamificação de uma criança.
 * Não é uma entidade do banco, mas sim um modelo calculado a partir dos achievements.
 *
 * MVP08 - Sistema de Gamificação
 */
data class GamificationStats(
    /** Total de estrelas acumuladas */
    val totalStars: Int = 0,

    /** Estrelas ganhas hoje */
    val starsToday: Int = 0,

    /** Estrelas ganhas esta semana */
    val starsThisWeek: Int = 0,

    /** Estrelas ganhas este mês */
    val starsThisMonth: Int = 0,

    /** Total de tarefas completadas */
    val totalTasksCompleted: Int = 0,

    /** Tarefas completadas hoje */
    val tasksCompletedToday: Int = 0,

    /** Sequência atual de dias consecutivos */
    val currentStreak: Int = 0,

    /** Maior sequência alcançada */
    val bestStreak: Int = 0,

    /** Taxa de conclusão (0-100) */
    val completionRate: Double = 0.0,

    /** Média de estrelas por tarefa */
    val averageStars: Double = 0.0,

    /** Total de recompensas desbloqueadas */
    val rewardsUnlocked: Int = 0
)

