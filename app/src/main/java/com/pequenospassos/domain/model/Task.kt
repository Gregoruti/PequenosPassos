package com.pequenospassos.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidade de tarefa (atividade diária).
 *
 * Representa uma atividade que a criança deve realizar durante o dia.
 * As tarefas são ordenadas automaticamente por horário.
 *
 * @property id Identificador único (auto-gerado)
 * @property title Título da tarefa (ex: "Escovar os dentes")
 * @property description Descrição detalhada (opcional)
 * @property iconRes Recurso de ícone da tarefa
 * @property time Horário da tarefa no formato HH:mm (ex: "08:00")
 * @property stars Quantidade de estrelas que a criança ganha (1-5)
 * @property status Status atual da tarefa (PENDING, COMPLETED, CANCELED)
 * @property createdAt Timestamp de criação da tarefa
 * @property category Categoria da tarefa (27 categorias disponíveis) - MVP-07
 * @property imageUrl URL/path da imagem principal da tarefa (aparece no card da lista) - MVP-07
 *
 * @since MVP-02 (13/10/2025) - DIA 1 - Fundação
 * @updated MVP-07 (16/10/2025) - Adicionado category e imageUrl
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-02, ⏳ Atualizado - MVP-07
 */
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val title: String,

    val description: String = "",

    val iconRes: Int,

    val time: String, // HH:mm para ordenação automática

    val stars: Int, // 1-5 estrelas de recompensa

    val status: TaskStatus = TaskStatus.PENDING,

    val createdAt: Long = System.currentTimeMillis(),

    /**
     * Categoria da tarefa usando TaskCategory enum.
     * Armazenado como String (nome do enum).
     * Obrigatório.
     *
     * Categorias disponíveis (27):
     * - Autocuidado e Rotina Pessoal (6)
     * - Saúde, Regulação e Terapias (9)
     * - Desenvolvimento Cognitivo e Educacional (4)
     * - Interação e Socialização (3)
     * - Atividades Cotidianas e Funcionais (3)
     * - Outros (2)
     *
     * @since MVP-07
     */
    val category: String,

    /**
     * URL ou path local da imagem PRINCIPAL da tarefa.
     * Aparece no card da TaskListScreen (miniatura).
     * Opcional - se null, exibe emoji da categoria.
     *
     * @since MVP-07
     */
    val imageUrl: String? = null
) {
    /**
     * Valida se a tarefa tem dados mínimos necessários
     */
    fun isValid(): Boolean {
        return title.isNotBlank() &&
               time.matches(Regex("^([01][0-9]|2[0-3]):[0-5][0-9]$")) &&
               stars in 1..5 &&
               category.isNotBlank() // MVP-07: categoria obrigatória
    }

    /**
     * Retorna se a tarefa está completa
     */
    fun isCompleted(): Boolean = status == TaskStatus.COMPLETED

    /**
     * Retorna se a tarefa foi cancelada
     */
    fun isCanceled(): Boolean = status == TaskStatus.CANCELED

    /**
     * Retorna se a tarefa está pendente
     */
    fun isPending(): Boolean = status == TaskStatus.PENDING

    /**
     * Converte o horário para minutos desde meia-noite
     * Útil para ordenação
     */
    fun getTimeInMinutes(): Int {
        val parts = time.split(":")
        return parts[0].toInt() * 60 + parts[1].toInt()
    }
}

/**
 * Enum de status de tarefa.
 *
 * Representa os possíveis estados de uma tarefa durante o dia.
 *
 * @property PENDING Tarefa não iniciada/aguardando execução
 * @property COMPLETED Tarefa concluída com sucesso
 * @property CANCELED Tarefa cancelada/não realizada
 */
enum class TaskStatus {
    PENDING,
    COMPLETED,
    CANCELED;

    /**
     * Retorna o emoji correspondente ao status
     */
    fun getEmoji(): String = when(this) {
        PENDING -> "⏳"
        COMPLETED -> "✅"
        CANCELED -> "❌"
    }

    /**
     * Retorna o nome formatado para exibição
     */
    fun getDisplayName(): String = when(this) {
        PENDING -> "Pendente"
        COMPLETED -> "Concluída"
        CANCELED -> "Cancelada"
    }
}
