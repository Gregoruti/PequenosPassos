package com.example.fonovirtual_v2.ui.exercises.recognition.model

/**
 * Representa um exercício de palavra para reconhecimento de fala.
 *
 * @property word A palavra a ser pronunciada
 * @property syllableCount Número de sílabas na palavra
 * @property attempts Número de tentativas realizadas
 * @property lastAttempt Última tentativa de pronúncia reconhecida
 * @property isCorrect Se a última tentativa foi correta
 *
 * @since 1.0.3
 * @validationStatus Em Desenvolvimento
 */
data class WordExercise(
    val word: String,
    val syllableCount: Int,
    var attempts: Int = 0,
    var lastAttempt: String? = null,
    var isCorrect: Boolean = false
)
