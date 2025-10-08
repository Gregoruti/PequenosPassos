package com.example.fonovirtual_v2.data.exercises.recognition

import com.example.fonovirtual_v2.ui.exercises.recognition.model.WordExercise

/**
 * Repositório de palavras fixas para exercícios de reconhecimento.
 * Mantém listas pré-definidas de palavras organizadas por número de sílabas,
 * grupos de sons consonantais (Xr e Xl), dígrafos e tipos de acentuação tônica.
 *
 * @since 1.0.3
 * @updated 1.0.9 (30/09/2025) - Reestruturados exercícios de sílaba tônica por tipo de acentuação
 * @validationStatus Em Desenvolvimento
 */
object WordsRepository {

    // Palavras organizadas por número de sílabas
    private val wordsBySyllables = mapOf(
        2 to listOf(
            WordExercise("casa", 2),
            WordExercise("bola", 2),
            WordExercise("pato", 2),
            WordExercise("mesa", 2),
            WordExercise("vida", 2)
        ),
        3 to listOf(
            WordExercise("projeto", 3),
            WordExercise("caminhar", 3),
            WordExercise("trabalho", 3),
            WordExercise("médico", 3),
            WordExercise("música", 3)
        ),
        4 to listOf(
            WordExercise("computação", 4),
            WordExercise("integrado", 4),
            WordExercise("chocolate", 4),
            WordExercise("telefone", 4),
            WordExercise("abacaxi", 4)
        ),
        5 to listOf(
            WordExercise("geografia", 5),
            WordExercise("matemática", 5),
            WordExercise("aplicativo", 5),
            WordExercise("comunicação", 5),
            WordExercise("universidade", 5)
        )
    )

    // Palavras organizadas por grupos de sons consonantais (Xr)
    private val wordsByConsonantGroups = mapOf(
        "BR" to listOf(
            WordExercise("brasil", 2),
            WordExercise("branco", 2),
            WordExercise("braço", 2),
            WordExercise("bravo", 2),
            WordExercise("biblioteca", 5)
        ),
        "CR" to listOf(
            WordExercise("criança", 3),
            WordExercise("criar", 2),
            WordExercise("cravo", 2),
            WordExercise("cristal", 2),
            WordExercise("crescer", 2)
        ),
        "FR" to listOf(
            WordExercise("fruta", 2),
            WordExercise("frio", 2),
            WordExercise("frase", 2),
            WordExercise("frango", 2),
            WordExercise("frequente", 3)
        ),
        "GR" to listOf(
            WordExercise("grande", 2),
            WordExercise("grupo", 2),
            WordExercise("grau", 1),
            WordExercise("grama", 2),
            WordExercise("gratidão", 3)
        )
    )

    // Palavras organizadas por grupos de sons consonantais (Xl)
    private val wordsByConsonantGroupsXL = mapOf(
        "CL" to listOf(
            WordExercise("classe", 2),
            WordExercise("claro", 2),
            WordExercise("clima", 2),
            WordExercise("cliente", 3),
            WordExercise("clínica", 3)
        ),
        "FL" to listOf(
            WordExercise("flor", 1),
            WordExercise("floresta", 3),
            WordExercise("fluir", 2),
            WordExercise("flecha", 2),
            WordExercise("flauta", 2)
        ),
        "PL" to listOf(
            WordExercise("planta", 2),
            WordExercise("planeta", 3),
            WordExercise("plano", 2),
            WordExercise("plástico", 3),
            WordExercise("playground", 2)
        ),
        "BL" to listOf(
            WordExercise("bloco", 2),
            WordExercise("blusa", 2),
            WordExercise("bloqueio", 3),
            WordExercise("blindado", 3),
            WordExercise("biblioteca", 5)
        )
    )

    // Palavras organizadas por dígrafos
    private val wordsByDigraphs = mapOf(
        "LH" to listOf(
            WordExercise("milho", 2),
            WordExercise("olho", 2),
            WordExercise("folha", 2),
            WordExercise("velho", 2),
            WordExercise("trabalho", 3)
        ),
        "NH" to listOf(
            WordExercise("banho", 2),
            WordExercise("sonho", 2),
            WordExercise("lenha", 2),
            WordExercise("manhã", 2),
            WordExercise("vinho", 2)
        ),
        "RR" to listOf(
            WordExercise("carro", 2),
            WordExercise("barro", 2),
            WordExercise("ferro", 2),
            WordExercise("guerra", 2),
            WordExercise("correio", 3)
        ),
        "SS" to listOf(
            WordExercise("massa", 2),
            WordExercise("passa", 2),
            WordExercise("pessoa", 3),
            WordExercise("cassa", 2),
            WordExercise("pressa", 2)
        )
    )

    // Palavras organizadas por tipo de sílaba tônica acentuada
    private val wordsByTonicAccent = mapOf(
        "Á" to listOf(
            WordExercise("matemática", 5),
            WordExercise("prática", 3),
            WordExercise("fantástico", 4),
            WordExercise("rápido", 3),
            WordExercise("árvore", 3)
        ),
        "É" to listOf(
            WordExercise("café", 2),
            WordExercise("pé", 1),
            WordExercise("mané", 2),
            WordExercise("chulé", 2),
            WordExercise("filé", 2)
        ),
        "Ê" to listOf(
            WordExercise("você", 2),
            WordExercise("bebê", 2),
            WordExercise("português", 3),
            WordExercise("inglês", 2),
            WordExercise("três", 1)
        ),
        "Í" to listOf(
            WordExercise("país", 2),
            WordExercise("saída", 3),
            WordExercise("família", 4),
            WordExercise("memória", 4),
            WordExercise("polícia", 4)
        ),
        "Ó" to listOf(
            WordExercise("avó", 2),
            WordExercise("robô", 2),
            WordExercise("história", 4),
            WordExercise("território", 5),
            WordExercise("próximo", 3)
        ),
        "Ú" to listOf(
            WordExercise("último", 3),
            WordExercise("público", 3),
            WordExercise("úmido", 3),
            WordExercise("número", 3),
            WordExercise("fúria", 3)
        ),
        "ÃO" to listOf(
            WordExercise("computação", 4),
            WordExercise("comunicação", 5),
            WordExercise("educação", 4),
            WordExercise("informação", 4),
            WordExercise("aplicação", 4)
        ),
        "ÕE" to listOf(
            WordExercise("decisões", 4),
            WordExercise("emoções", 3),
            WordExercise("reflexões", 4),
            WordExercise("tradições", 4),
            WordExercise("dimensões", 4)
        )
    )

    /**
     * Retorna a lista de palavras para um determinado número de sílabas.
     *
     * @param syllableCount Número de sílabas desejado (2 a 5)
     * @return Lista de exercícios de palavras ou lista vazia se número de sílabas inválido
     */
    fun getWordsBySyllables(syllableCount: Int): List<WordExercise> {
        return wordsBySyllables[syllableCount] ?: emptyList()
    }

    /**
     * Retorna a lista de palavras para um determinado grupo consonantal Xr.
     *
     * @param consonantGroup Grupo consonantal desejado (BR, CR, FR, GR)
     * @return Lista de exercícios de palavras ou lista vazia se grupo inválido
     */
    fun getWordsByConsonantGroup(consonantGroup: String): List<WordExercise> {
        return wordsByConsonantGroups[consonantGroup.uppercase()] ?: emptyList()
    }

    /**
     * Retorna a lista de palavras para um determinado grupo consonantal Xl.
     *
     * @param consonantGroup Grupo consonantal desejado (CL, FL, PL, BL)
     * @return Lista de exercícios de palavras ou lista vazia se grupo inválido
     */
    fun getWordsByConsonantGroupXL(consonantGroup: String): List<WordExercise> {
        return wordsByConsonantGroupsXL[consonantGroup.uppercase()] ?: emptyList()
    }

    /**
     * Retorna a lista de palavras para um determinado dígrafo.
     *
     * @param digraph Dígrafo desejado (CH, LH, NH, RR, SS)
     * @return Lista de exercícios de palavras ou lista vazia se dígrafo inválido
     */
    fun getWordsByDigraph(digraph: String): List<WordExercise> {
        return wordsByDigraphs[digraph.uppercase()] ?: emptyList()
    }

    /**
     * Retorna a lista de dígrafos disponíveis.
     *
     * @return Lista de dígrafos (CH, LH, NH, RR, SS)
     */
    fun getAvailableDigraphs(): List<String> {
        return wordsByDigraphs.keys.sorted()
    }

    /**
     * Retorna a lista de contagens de sílabas disponíveis.
     *
     * @return Lista de números de sílabas (2, 3, 4, 5)
     */
    fun getAvailableSyllableCounts(): List<Int> {
        return wordsBySyllables.keys.sorted()
    }

    /**
     * Retorna a lista de grupos consonantais Xr disponíveis.
     *
     * @return Lista de grupos consonantais (BR, CR, FR, GR)
     */
    fun getAvailableConsonantGroups(): List<String> {
        return wordsByConsonantGroups.keys.sorted()
    }

    /**
     * Retorna a lista de grupos consonantais Xl disponíveis.
     *
     * @return Lista de grupos consonantais (BL, CL, FL, PL)
     */
    fun getAvailableConsonantGroupsXL(): List<String> {
        return wordsByConsonantGroupsXL.keys.sorted()
    }

    /**
     * Retorna a lista de palavras com sílaba tônica marcada.
     *
     * @return Lista de exercícios de palavras com sílaba tônica
     */
    fun getWordsWithTonic(): List<WordExercise> {
        val deprecatedWarning = "A funcionalidade de palavras com sílaba tônica marcada está obsoleta. " +
                "Utilize os métodos de busca por tipo de sílaba tônica acentuada."
        println(deprecatedWarning)
        return emptyList()
    }

    /**
     * Retorna a lista de palavras para um determinado tipo de acentuação.
     *
     * @param accentType Tipo de acentuação desejado (Á, É, Í, Ó, Ú, ÃO, ÕE)
     * @return Lista de exercícios de palavras ou lista vazia se tipo inválido
     */
    fun getWordsByTonicAccent(accentType: String): List<WordExercise> {
        return wordsByTonicAccent[accentType.uppercase()] ?: emptyList()
    }

    /**
     * Retorna a lista de tipos de acentuação disponíveis.
     *
     * @return Lista de tipos de acentuação (Á, É, Í, Ó, Ú, ÃO, ÕE)
     */
    fun getAvailableTonicAccents(): List<String> {
        return wordsByTonicAccent.keys.sorted()
    }
}
