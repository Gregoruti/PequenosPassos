package com.pequenospassos.domain.model

/**
 * Enum para representar o gênero da criança.
 *
 * Utilizado no cadastro da criança para personalização da interface
 * e adequação às necessidades específicas do TEA.
 *
 * @since MVP 1.3.0 (12/10/2025) - DIA 1 - MVP-02
 * @author MVP Development Team
 * @validationStatus ✅ Implementado - MVP-02
 */
enum class Gender {
    MALE,   // Menino
    FEMALE  // Menina
}

/**
 * Enum para representar o status de execução das tarefas.
 *
 * Sistema de status visual claro para crianças com TEA:
 * - PENDING: Tarefa não iniciada (⏳)
 * - COMPLETED: Tarefa finalizada com sucesso (✅)
 * - CANCELED: Tarefa não realizada (❌)
 *
 * @since MVP 1.3.0 (12/10/2025) - DIA 1 - MVP-02
 * @author MVP Development Team
 * @validationStatus ✅ Implementado - MVP-02
 */
enum class TaskStatus {
    PENDING,    // ⏳ Pendente
    COMPLETED,  // ✅ Concluída
    CANCELED    // ❌ Cancelada
}
