package com.pequenospassos.domain.model

import org.junit.Assert.*
import org.junit.Test

/**
 * Testes unitários para Task.
 *
 * Valida:
 * - Criação de tarefas válidas
 * - Validação de formato de horário (HH:mm)
 * - Validação de estrelas (1-5)
 * - Métodos de status (isCompleted, isPending, isCanceled)
 * - Conversão de horário para minutos
 * - Enum TaskStatus
 *
 * @since MVP-02 (13/10/2025)
 */
class TaskTest {

    @Test
    fun `Task válida deve passar na validação`() {
        // Arrange
        val task = Task(
            id = 1,
            title = "Escovar os dentes",
            iconRes = 1,
            time = "08:00",
            stars = 3
        )

        // Act
        val isValid = task.isValid()

        // Assert
        assertTrue("Task válida deve passar na validação", isValid)
    }

    @Test
    fun `Task com título vazio deve falhar na validação`() {
        // Arrange
        val task = Task(
            title = "",
            iconRes = 1,
            time = "08:00",
            stars = 3
        )

        // Act & Assert
        assertFalse(task.isValid())
    }

    @Test
    fun `Task com horário inválido deve falhar na validação`() {
        // Arrange & Act & Assert
        assertFalse(Task(title = "Tarefa", iconRes = 1, time = "25:00", stars = 3).isValid())
        assertFalse(Task(title = "Tarefa", iconRes = 1, time = "08:60", stars = 3).isValid())
        assertFalse(Task(title = "Tarefa", iconRes = 1, time = "8:00", stars = 3).isValid())
        assertFalse(Task(title = "Tarefa", iconRes = 1, time = "08-00", stars = 3).isValid())
        assertFalse(Task(title = "Tarefa", iconRes = 1, time = "0800", stars = 3).isValid())
    }

    @Test
    fun `Task com horários válidos deve passar na validação`() {
        // Arrange & Act & Assert
        assertTrue(Task(title = "Tarefa", iconRes = 1, time = "00:00", stars = 3).isValid())
        assertTrue(Task(title = "Tarefa", iconRes = 1, time = "08:30", stars = 3).isValid())
        assertTrue(Task(title = "Tarefa", iconRes = 1, time = "12:45", stars = 3).isValid())
        assertTrue(Task(title = "Tarefa", iconRes = 1, time = "23:59", stars = 3).isValid())
    }

    @Test
    fun `Task com stars fora do intervalo 1-5 deve falhar na validação`() {
        // Arrange & Act & Assert
        assertFalse(Task(title = "Tarefa", iconRes = 1, time = "08:00", stars = 0).isValid())
        assertFalse(Task(title = "Tarefa", iconRes = 1, time = "08:00", stars = 6).isValid())
        assertFalse(Task(title = "Tarefa", iconRes = 1, time = "08:00", stars = -1).isValid())
    }

    @Test
    fun `Task com stars entre 1-5 deve passar na validação`() {
        // Arrange & Act & Assert
        assertTrue(Task(title = "Tarefa", iconRes = 1, time = "08:00", stars = 1).isValid())
        assertTrue(Task(title = "Tarefa", iconRes = 1, time = "08:00", stars = 3).isValid())
        assertTrue(Task(title = "Tarefa", iconRes = 1, time = "08:00", stars = 5).isValid())
    }

    @Test
    fun `isCompleted deve retornar true apenas para status COMPLETED`() {
        // Arrange & Act & Assert
        assertTrue(Task(title = "T", iconRes = 1, time = "08:00", stars = 3, status = TaskStatus.COMPLETED).isCompleted())
        assertFalse(Task(title = "T", iconRes = 1, time = "08:00", stars = 3, status = TaskStatus.PENDING).isCompleted())
        assertFalse(Task(title = "T", iconRes = 1, time = "08:00", stars = 3, status = TaskStatus.CANCELED).isCompleted())
    }

    @Test
    fun `isPending deve retornar true apenas para status PENDING`() {
        // Arrange & Act & Assert
        assertTrue(Task(title = "T", iconRes = 1, time = "08:00", stars = 3, status = TaskStatus.PENDING).isPending())
        assertFalse(Task(title = "T", iconRes = 1, time = "08:00", stars = 3, status = TaskStatus.COMPLETED).isPending())
        assertFalse(Task(title = "T", iconRes = 1, time = "08:00", stars = 3, status = TaskStatus.CANCELED).isPending())
    }

    @Test
    fun `isCanceled deve retornar true apenas para status CANCELED`() {
        // Arrange & Act & Assert
        assertTrue(Task(title = "T", iconRes = 1, time = "08:00", stars = 3, status = TaskStatus.CANCELED).isCanceled())
        assertFalse(Task(title = "T", iconRes = 1, time = "08:00", stars = 3, status = TaskStatus.PENDING).isCanceled())
        assertFalse(Task(title = "T", iconRes = 1, time = "08:00", stars = 3, status = TaskStatus.COMPLETED).isCanceled())
    }

    @Test
    fun `getTimeInMinutes deve converter horário corretamente`() {
        // Arrange & Act & Assert
        assertEquals(0, Task(title = "T", iconRes = 1, time = "00:00", stars = 3).getTimeInMinutes())
        assertEquals(480, Task(title = "T", iconRes = 1, time = "08:00", stars = 3).getTimeInMinutes())
        assertEquals(510, Task(title = "T", iconRes = 1, time = "08:30", stars = 3).getTimeInMinutes())
        assertEquals(720, Task(title = "T", iconRes = 1, time = "12:00", stars = 3).getTimeInMinutes())
        assertEquals(1439, Task(title = "T", iconRes = 1, time = "23:59", stars = 3).getTimeInMinutes())
    }

    @Test
    fun `Task padrão deve ter status PENDING`() {
        // Arrange & Act
        val task = Task(
            title = "Tarefa",
            iconRes = 1,
            time = "08:00",
            stars = 3
        )

        // Assert
        assertEquals(TaskStatus.PENDING, task.status)
        assertTrue(task.isPending())
    }

    @Test
    fun `Task deve ter timestamp de criação`() {
        // Arrange
        val beforeCreation = System.currentTimeMillis()

        // Act
        val task = Task(
            title = "Tarefa",
            iconRes = 1,
            time = "08:00",
            stars = 3
        )

        val afterCreation = System.currentTimeMillis()

        // Assert
        assertTrue(task.createdAt in beforeCreation..afterCreation)
    }

    // ===== TESTES DO ENUM TASKSTATUS =====

    @Test
    fun `TaskStatus PENDING deve retornar emoji correto`() {
        assertEquals("⏳", TaskStatus.PENDING.getEmoji())
    }

    @Test
    fun `TaskStatus COMPLETED deve retornar emoji correto`() {
        assertEquals("✅", TaskStatus.COMPLETED.getEmoji())
    }

    @Test
    fun `TaskStatus CANCELED deve retornar emoji correto`() {
        assertEquals("❌", TaskStatus.CANCELED.getEmoji())
    }

    @Test
    fun `TaskStatus PENDING deve retornar displayName correto`() {
        assertEquals("Pendente", TaskStatus.PENDING.getDisplayName())
    }

    @Test
    fun `TaskStatus COMPLETED deve retornar displayName correto`() {
        assertEquals("Concluída", TaskStatus.COMPLETED.getDisplayName())
    }

    @Test
    fun `TaskStatus CANCELED deve retornar displayName correto`() {
        assertEquals("Cancelada", TaskStatus.CANCELED.getDisplayName())
    }

    @Test
    fun `TaskStatus deve ter exatamente 3 valores`() {
        assertEquals(3, TaskStatus.values().size)
    }
}

