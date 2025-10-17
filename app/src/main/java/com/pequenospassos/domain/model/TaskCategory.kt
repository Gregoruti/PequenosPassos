package com.pequenospassos.domain.model

/**
 * Enum de categorias de tarefas.
 *
 * Representa as 27 categorias disponíveis para classificar tarefas,
 * organizadas em 6 grupos principais.
 *
 * Cada categoria tem:
 * - Nome legível (displayName)
 * - Emoji representativo
 * - Grupo ao qual pertence
 *
 * @property displayName Nome exibido na interface
 * @property emoji Emoji representativo da categoria
 * @property group Grupo ao qual a categoria pertence
 *
 * @since MVP-07 (16/10/2025)
 * @author PequenosPassos Development Team
 */
enum class TaskCategory(
    val displayName: String,
    val emoji: String,
    val group: CategoryGroup
) {
    // ===== GRUPO 1: AUTOCUIDADO E ROTINA PESSOAL (6) =====
    HIGIENE_PESSOAL("Higiene Pessoal", "🧼", CategoryGroup.AUTOCUIDADO),
    BANHO("Banho", "🚿", CategoryGroup.AUTOCUIDADO),
    VESTIR("Vestir-se", "👕", CategoryGroup.AUTOCUIDADO),
    SONO("Sono e Descanso", "😴", CategoryGroup.AUTOCUIDADO),
    ALIMENTACAO("Alimentação", "🍽️", CategoryGroup.AUTOCUIDADO),
    USO_BANHEIRO("Uso do Banheiro", "🚽", CategoryGroup.AUTOCUIDADO),

    // ===== GRUPO 2: SAÚDE, REGULAÇÃO E TERAPIAS (9) =====
    TERAPIA_FONO("Terapia Fonoaudiológica", "🗣️", CategoryGroup.SAUDE_TERAPIAS),
    TERAPIA_TO("Terapia Ocupacional", "🤲", CategoryGroup.SAUDE_TERAPIAS),
    TERAPIA_PSICO("Terapia Psicológica", "🧠", CategoryGroup.SAUDE_TERAPIAS),
    MEDICACAO("Medicação", "💊", CategoryGroup.SAUDE_TERAPIAS),
    EXERCICIO_FISICO("Exercício Físico", "🏃", CategoryGroup.SAUDE_TERAPIAS),
    RELAXAMENTO("Relaxamento", "🧘", CategoryGroup.SAUDE_TERAPIAS),
    RESPIRACAO("Exercícios de Respiração", "🌬️", CategoryGroup.SAUDE_TERAPIAS),
    SENSORIAL("Atividade Sensorial", "👐", CategoryGroup.SAUDE_TERAPIAS),
    CONSULTA_MEDICA("Consulta Médica", "⚕️", CategoryGroup.SAUDE_TERAPIAS),

    // ===== GRUPO 3: DESENVOLVIMENTO COGNITIVO E EDUCACIONAL (4) =====
    LEITURA("Leitura", "📚", CategoryGroup.COGNITIVO_EDUCACIONAL),
    ESCRITA("Escrita", "✍️", CategoryGroup.COGNITIVO_EDUCACIONAL),
    MATEMATICA("Matemática", "🔢", CategoryGroup.COGNITIVO_EDUCACIONAL),
    ESTUDO("Estudos", "📖", CategoryGroup.COGNITIVO_EDUCACIONAL),

    // ===== GRUPO 4: INTERAÇÃO E SOCIALIZAÇÃO (3) =====
    INTERACAO_SOCIAL("Interação Social", "👥", CategoryGroup.INTERACAO_SOCIAL),
    COMUNICACAO("Comunicação", "💬", CategoryGroup.INTERACAO_SOCIAL),
    BRINCADEIRA("Brincadeira", "🎮", CategoryGroup.INTERACAO_SOCIAL),

    // ===== GRUPO 5: ATIVIDADES COTIDIANAS E FUNCIONAIS (3) =====
    TAREFAS_CASA("Tarefas Domésticas", "🧹", CategoryGroup.ATIVIDADES_COTIDIANAS),
    ORGANIZACAO("Organização", "📦", CategoryGroup.ATIVIDADES_COTIDIANAS),
    TRANSICAO("Transição de Atividade", "🔄", CategoryGroup.ATIVIDADES_COTIDIANAS),

    // ===== GRUPO 6: OUTROS (2) =====
    LAZER("Lazer", "🎨", CategoryGroup.OUTROS),
    OUTROS("Outros", "📝", CategoryGroup.OUTROS);

    /**
     * Retorna a representação completa da categoria (emoji + nome).
     * Exemplo: "🧼 Higiene Pessoal"
     */
    fun getFullDisplay(): String = "$emoji $displayName"

    companion object {
        /**
         * Retorna todas as categorias agrupadas por CategoryGroup.
         *
         * @return Map<CategoryGroup, List<TaskCategory>>
         */
        fun getCategoriesByGroup(): Map<CategoryGroup, List<TaskCategory>> {
            return entries.groupBy { it.group }
        }

        /**
         * Busca uma categoria pelo nome do enum (case-insensitive).
         *
         * @param name Nome do enum (ex: "HIGIENE_PESSOAL")
         * @return TaskCategory ou null se não encontrado
         */
        fun fromString(name: String): TaskCategory? {
            return entries.find { it.name.equals(name, ignoreCase = true) }
        }

        /**
         * Retorna a categoria padrão (OUTROS).
         */
        fun default(): TaskCategory = OUTROS
    }
}

/**
 * Enum de grupos de categorias.
 *
 * Organiza as 27 categorias em 6 grupos principais para facilitar
 * a navegação e seleção na interface.
 *
 * @property displayName Nome exibido do grupo
 * @property emoji Emoji representativo do grupo
 *
 * @since MVP-07 (16/10/2025)
 */
enum class CategoryGroup(
    val displayName: String,
    val emoji: String
) {
    AUTOCUIDADO("Autocuidado e Rotina Pessoal", "🧍"),
    SAUDE_TERAPIAS("Saúde, Regulação e Terapias", "⚕️"),
    COGNITIVO_EDUCACIONAL("Desenvolvimento Cognitivo e Educacional", "🧠"),
    INTERACAO_SOCIAL("Interação e Socialização", "👥"),
    ATIVIDADES_COTIDIANAS("Atividades Cotidianas e Funcionais", "🏠"),
    OUTROS("Outros", "📋");

    /**
     * Retorna a representação completa do grupo (emoji + nome).
     */
    fun getFullDisplay(): String = "$emoji $displayName"
}
