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
         * Lista de palavras que fazem parte da PERGUNTA e devem ser IGNORADAS.
         * MVP-14 Fase 5: Evita que criança apenas repita a pergunta SEM intenção.
         *
         * NOTA: Removidas palavras que podem ser respostas afirmativas:
         * - "podemos", "vamos", "quero", "continuar", "avançar", "próximo" → POSITIVE_COMMANDS
         */
        private val QUESTION_WORDS = setOf(
            "deseja", // parte da pergunta sem intenção clara
            "quer", // parte da pergunta sem intenção clara
            "seguir", // pode ser ambíguo
            "passo" // apenas palavra da pergunta
        )

        /**
         * Lista de comandos POSITIVOS (avançar).
         * Respostas infantis que indicam "sim, pode avançar".
         *
         * Total: 32 variações (atualizado MVP-14 Fase 5)
         */
        private val POSITIVE_COMMANDS = setOf(
            // Respostas afirmativas diretas (10) - EXPANDIDO
            "sim",
            "pode",
            "podemos", // CORRIGIDO: resposta afirmativa à pergunta
            "vamos", // CORRIGIDO: resposta afirmativa à pergunta
            "vai",
            "continua", // variação de "continuar"
            "continuar", // ADICIONADO: resposta afirmativa à pergunta
            "próximo", // CORRIGIDO: resposta afirmativa à pergunta
            "avança", // variação de "avançar"
            "avançar", // ADICIONADO: resposta afirmativa à pergunta

            // Variações infantis (12)
            "tá",
            "ok",
            "beleza",
            "isso",
            "é",
            "uhum",
            "aham",
            "bora", // "vamos bora"
            "vou", // "vou [continuar]"
            "dale", // gíria infantil
            "quero", // CORRIGIDO: "quero" = quero continuar (POSITIVO)
            "claro", // "claro que sim"

            // Frases completas comuns (10) - EXPANDIDO
            "pode ir",
            "vamos lá",
            "tá bom",
            "pode avançar",
            "próximo passo",
            "já terminei",
            "terminei",
            "pronto",
            "feito",
            "tô pronto"
        )

        /**
         * Lista de comandos NEGATIVOS (mais tempo).
         * Respostas infantis que indicam "não, quero mais tempo".
         *
         * Total: 30 variações (atualizado MVP-14 Fase 5)
         */
        private val NEGATIVE_COMMANDS = setOf(
            // Respostas negativas diretas (7) - EXPANDIDO
            "não",
            "espera",
            "calma",
            "para",
            "aguarda",
            "não quero", // ADICIONADO: "não quero [continuar]"
            "nem", // "nem quero"

            // Pedidos de mais tempo (7)
            "mais tempo",
            "mais",
            "tempo",
            "demora",
            "devagar",
            "preciso", // "preciso [de mais tempo]"
            "espere", // variação de "espera"

            // Variações infantis (6)
            "nãããão",
            "não não",
            "ainda não",
            "peraí",
            "perai",
            "pera",

            // Frases completas comuns (10) - EXPANDIDO
            "mais um pouco",
            "só mais um pouco",
            "quero mais tempo",
            "ainda tô fazendo",
            "ainda não terminei",
            "não terminei",
            "deixa eu terminar",
            "quase lá",
            "quase",
            "um pouco mais" // variação
        )
    }

    /**
     * Analisa o texto reconhecido e retorna o comando correspondente.
     *
     * Processo:
     * 1. Normaliza o texto (lowercase, trim)
     * 2. Verifica se é apenas palavra da pergunta (IGNORA)
     * 3. Verifica comandos positivos
     * 4. Verifica comandos negativos
     * 5. Retorna UNKNOWN se não encontrou
     *
     * @param recognizedText Texto reconhecido pelo ASR (Vosk)
     * @return CommandResult indicando a intenção (POSITIVE, NEGATIVE ou UNKNOWN)
     */
    fun parse(recognizedText: String): CommandResult {
        // Normaliza: lowercase e remove espaços extras
        val normalized = recognizedText.lowercase().trim()

        println("[VoiceCommandParser] Texto original: '$recognizedText'")
        println("[VoiceCommandParser] Texto normalizado: '$normalized'")

        // MVP-14 Fase 5: Ignora se for apenas palavra da pergunta
        if (QUESTION_WORDS.contains(normalized)) {
            println("[VoiceCommandParser] ⚠️ Palavra da PERGUNTA ignorada: '$normalized'")
            return CommandResult.UNKNOWN
        }

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
     * MVP-14 Fase 5: Validação de palavra completa para evitar matches parciais.
     * Exemplo: "pode" NÃO deve match com "podemos"
     *
     * @param text Texto normalizado
     * @param commands Lista de comandos válidos
     * @return true se encontrou algum comando, false caso contrário
     */
    private fun containsAnyCommand(text: String, commands: Set<String>): Boolean {
        // Split em palavras para matching exato
        val words = text.split(" ").filter { it.isNotBlank() }

        return commands.any { command ->
            // Para comandos de múltiplas palavras (ex: "pode ir", "mais tempo")
            if (command.contains(" ")) {
                // Verifica se a frase completa existe no texto
                text == command ||
                text.contains(" $command ") ||
                text.startsWith("$command ") ||
                text.endsWith(" $command")
            } else {
                // Para comandos de palavra única, verifica match exato na lista de palavras
                words.contains(command)
            }
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

