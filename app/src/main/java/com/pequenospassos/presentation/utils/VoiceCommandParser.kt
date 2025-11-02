package com.pequenospassos.presentation.utils

/**
 * Parser de comandos de voz para reconhecimento de respostas infantis.
 *
 * Arquivo: presentation/utils/VoiceCommandParser.kt
 * Tipo: Utility class
 * Objetivo: Analisar texto reconhecido pelo ASR e determinar intenção (positivo/negativo)
 * Correlações: AsrManager.kt, TaskExecutionViewModel.kt
 *
 * Histórico de alterações:
 * - 2025-11-01 (Claude Sonnet 4.5): MVP-14 Fase 3 - Criação inicial
 *   - Listas de comandos positivos (25 variações)
 *   - Listas de comandos negativos (25 variações)
 *   - Método parse() com normalização e análise
 *
 * Última atualização: 2025-11-01 (MVP-14 Fase 3)
 * Status: Em desenvolvimento - MVP-14
 * Build: PENDING
 *
 * Criado: 2025-11-01
 * Autor: Claude Sonnet 4.5 (GitHub Copilot)
 *
 * @since MVP-14 Fase 3 (v2.2.0)
 * @validationStatus 🔄 Em desenvolvimento - MVP-14 Fase 3
 */
class VoiceCommandParser {

    /**
     * Resultado do parsing de comando de voz.
     *
     * POSITIVE: Avançar para próximo step
     * NEGATIVE: Adicionar mais 30 segundos
     * UNKNOWN: Não reconhecido
     */
    enum class CommandResult {
        POSITIVE,  // Avançar para próximo step
        NEGATIVE,  // Adicionar mais 30 segundos
        UNKNOWN    // Não reconhecido
    }

    companion object {
        /**
         * Lista de comandos POSITIVOS (avançar).
         * Respostas infantis que indicam "sim, pode avançar".
         *
         * Total: 25 variações
         */
        private val POSITIVE_COMMANDS = setOf(
            // Respostas afirmativas diretas (7)
            "sim",
            "pode",
            "vamos",
            "vai",
            "continua",
            "próximo",
            "avança",

            // Variações infantis (7)
            "tá",
            "ok",
            "beleza",
            "isso",
            "é",
            "uhum",
            "aham",

            // Frases completas comuns (8)
            "pode ir",
            "vamos lá",
            "tá bom",
            "pode avançar",
            "próximo passo",
            "já terminei",
            "terminei",
            "pronto",
            "feito",

            // Com artigos/preposições (3)
            "pode sim",
            "vamos sim",
            "tá certo"
        )

        /**
         * Lista de comandos NEGATIVOS (mais tempo).
         * Respostas infantis que indicam "não, quero mais tempo".
         *
         * Total: 25 variações
         */
        private val NEGATIVE_COMMANDS = setOf(
            // Respostas negativas diretas (5)
            "não",
            "espera",
            "calma",
            "para",
            "aguarda",

            // Pedidos de mais tempo (5)
            "mais tempo",
            "mais",
            "tempo",
            "demora",
            "devagar",

            // Variações infantis (6)
            "nãããão",
            "não não",
            "ainda não",
            "peraí",
            "perai",
            "pera",

            // Frases completas comuns (9)
            "mais um pouco",
            "só mais um pouco",
            "quero mais tempo",
            "ainda tô fazendo",
            "ainda não terminei",
            "não terminei",
            "deixa eu terminar",
            "quase lá",
            "quase"
        )
    }

    /**
     * Analisa o texto reconhecido e retorna o comando correspondente.
     *
     * Processo:
     * 1. Normaliza o texto (lowercase, trim)
     * 2. Verifica comandos positivos
     * 3. Verifica comandos negativos
     * 4. Retorna UNKNOWN se não encontrou
     *
     * @param recognizedText Texto reconhecido pelo ASR (Vosk)
     * @return CommandResult indicando a intenção (POSITIVE, NEGATIVE ou UNKNOWN)
     */
    fun parse(recognizedText: String): CommandResult {
        // Normaliza: lowercase e remove espaços extras
        val normalized = recognizedText.lowercase().trim()

        println("[VoiceCommandParser] Texto original: '$recognizedText'")
        println("[VoiceCommandParser] Texto normalizado: '$normalized'")

        // Verifica comandos positivos
        if (containsAnyCommand(normalized, POSITIVE_COMMANDS)) {
            println("[VoiceCommandParser] ✅ Comando POSITIVO reconhecido")
            return CommandResult.POSITIVE
        }

        // Verifica comandos negativos
        if (containsAnyCommand(normalized, NEGATIVE_COMMANDS)) {
            println("[VoiceCommandParser] ❌ Comando NEGATIVO reconhecido")
            return CommandResult.NEGATIVE
        }

        println("[VoiceCommandParser] ❓ Comando DESCONHECIDO: '$normalized'")
        return CommandResult.UNKNOWN
    }

    /**
     * Verifica se o texto contém algum dos comandos da lista.
     *
     * Estratégias de matching:
     * 1. Texto exato (text == command)
     * 2. Palavra isolada no meio (" command ")
     * 3. Palavra no início ("command ")
     * 4. Palavra no final (" command")
     *
     * @param text Texto normalizado
     * @param commands Lista de comandos válidos
     * @return true se encontrou algum comando, false caso contrário
     */
    private fun containsAnyCommand(text: String, commands: Set<String>): Boolean {
        return commands.any { command ->
            // Verifica se o texto contém o comando como:
            // 1. Texto exato
            text == command ||
            // 2. Palavra isolada no meio (com espaços antes e depois)
            text.contains(" $command ") ||
            // 3. Palavra no início (com espaço depois)
            text.startsWith("$command ") ||
            // 4. Palavra no final (com espaço antes)
            text.endsWith(" $command")
        }
    }

    /**
     * Retorna estatísticas sobre os comandos disponíveis.
     * Útil para debug e documentação.
     *
     * @return Map com informações sobre comandos
     */
    fun getStats(): Map<String, Any> {
        return mapOf(
            "totalPositiveCommands" to POSITIVE_COMMANDS.size,
            "totalNegativeCommands" to NEGATIVE_COMMANDS.size,
            "totalCommands" to (POSITIVE_COMMANDS.size + NEGATIVE_COMMANDS.size),
            "positiveCommands" to POSITIVE_COMMANDS.sorted(),
            "negativeCommands" to NEGATIVE_COMMANDS.sorted()
        )
    }
}

