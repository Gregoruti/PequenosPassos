package com.pequenospassos.domain.model

import org.junit.Assert.*
import org.junit.Test

/**
 * Testes unitários para Step e TaskWithSteps.
 *
 * Valida:
 * - Criação de steps válidos
 * - Validação de dados (isValid)
 * - Método getStepNumber
 * - TaskWithSteps e métodos de progresso
 * - Validação de imageUrl opcional (MVP-07)
 * - Validação de durationSeconds (15-600s) (MVP-07)
 *
 * @since MVP-02 (13/10/2025)
 * @updated MVP-07 (16/10/2025) - Testes para novos campos
 */
class StepTest {

    @Test
    fun `Step válido deve passar na validação`() {
        // Arrange
        val step = Step(
            id = 1,
            taskId = 10,
            title = "Pegar a escova",
            order = 0
        )

        // Act
        val isValid = step.isValid()

        // Assert
        assertTrue("Step válido deve passar na validação", isValid)
    }

    @Test
    fun `Step com título vazio deve falhar na validação`() {
        // Arrange
        val step = Step(
            taskId = 10,
            title = "",
            order = 0
        )

        // Act & Assert
        assertFalse(step.isValid())
    }

    @Test
    fun `Step com ordem negativa deve falhar na validação`() {
        // Arrange
        val step = Step(
            taskId = 10,
            title = "Passo",
            order = -1
        )

        // Act & Assert
        assertFalse(step.isValid())
    }

    @Test
    fun `Step com taskId zero ou negativo deve falhar na validação`() {
        // Arrange & Act & Assert
        assertFalse(Step(taskId = 0, title = "Passo", order = 0).isValid())
        assertFalse(Step(taskId = -1, title = "Passo", order = 0).isValid())
    }

    @Test
    fun `getStepNumber deve retornar order mais 1`() {
        // Arrange & Act & Assert
        assertEquals(1, Step(taskId = 1, title = "Passo", order = 0).getStepNumber())
        assertEquals(2, Step(taskId = 1, title = "Passo", order = 1).getStepNumber())
        assertEquals(5, Step(taskId = 1, title = "Passo", order = 4).getStepNumber())
    }

    @Test
    fun `Step padrão deve ter isCompleted false`() {
        // Arrange & Act
        val step = Step(
            taskId = 1,
            title = "Passo",
            order = 0
        )

        // Assert
        assertFalse(step.isCompleted)
    }

    // ===== TESTES MVP-07: NOVOS CAMPOS =====

    @Test
    fun `Step com imageUrl null deve ser válido`() {
        // Arrange
        val step = Step(
            taskId = 1,
            title = "Pegar escova",
            order = 0,
            imageUrl = null
        )

        // Act & Assert
        assertTrue("Step com imageUrl null deve ser válido", step.isValid())
        assertNull("imageUrl deve ser null", step.imageUrl)
    }

    @Test
    fun `Step com imageUrl preenchida deve ser válido`() {
        // Arrange
        val step = Step(
            taskId = 1,
            title = "Pegar escova",
            order = 0,
            imageUrl = "https://example.com/escova.jpg"
        )

        // Act & Assert
        assertTrue("Step com imageUrl deve ser válido", step.isValid())
        assertEquals("https://example.com/escova.jpg", step.imageUrl)
    }

    @Test
    fun `Step deve ter durationSeconds padrão de 60 segundos`() {
        // Arrange & Act
        val step = Step(
            taskId = 1,
            title = "Passo",
            order = 0
        )

        // Assert
        assertEquals(60, step.durationSeconds)
    }

    @Test
    fun `isValidDuration deve retornar true para duração entre 15 e 600 segundos`() {
        // Arrange & Act & Assert
        assertTrue(Step(taskId = 1, title = "P", order = 0, durationSeconds = 5).isValidDuration())
        assertTrue(Step(taskId = 1, title = "P", order = 0, durationSeconds = 15).isValidDuration())
        assertTrue(Step(taskId = 1, title = "P", order = 0, durationSeconds = 60).isValidDuration())
        assertTrue(Step(taskId = 1, title = "P", order = 0, durationSeconds = 120).isValidDuration())
        assertTrue(Step(taskId = 1, title = "P", order = 0, durationSeconds = 300).isValidDuration())
        assertTrue(Step(taskId = 1, title = "P", order = 0, durationSeconds = 600).isValidDuration())
    }

    @Test
    fun `isValidDuration deve retornar false para duração menor que 15 segundos`() {
        // Arrange & Act & Assert
        assertFalse(Step(taskId = 1, title = "P", order = 0, durationSeconds = 4).isValidDuration())
        assertFalse(Step(taskId = 1, title = "P", order = 0, durationSeconds = 0).isValidDuration())
        assertFalse(Step(taskId = 1, title = "P", order = 0, durationSeconds = -10).isValidDuration())
    }

    @Test
    fun `isValidDuration deve retornar false para duração maior que 600 segundos`() {
        // Arrange & Act & Assert
        assertFalse(Step(taskId = 1, title = "P", order = 0, durationSeconds = 601).isValidDuration())
        assertFalse(Step(taskId = 1, title = "P", order = 0, durationSeconds = 1000).isValidDuration())
    }

    @Test
    fun `Step deve permitir configurar durationSeconds customizado`() {
        // Arrange
        val step = Step(
            taskId = 1,
            title = "Escovar por 2 minutos",
            order = 0,
            durationSeconds = 120
        )

        // Act & Assert
        assertEquals(120, step.durationSeconds)
        assertTrue(step.isValidDuration())
    }

    // ===== TESTES DE TASKWITHSTEPS =====

    @Test
    fun `TaskWithSteps getTotalSteps deve retornar quantidade correta`() {
        // Arrange
        val task = Task(title = "Tarefa", iconRes = 1, time = "08:00", stars = 3, category = "HIGIENE_PESSOAL")
        val steps = listOf(
            Step(taskId = 1, title = "Passo 1", order = 0),
            Step(taskId = 1, title = "Passo 2", order = 1),
            Step(taskId = 1, title = "Passo 3", order = 2)
        )
        val taskWithSteps = TaskWithSteps(task, steps)

        // Act
        val total = taskWithSteps.getTotalSteps()

        // Assert
        assertEquals(3, total)
    }

    @Test
    fun `TaskWithSteps getCompletedSteps deve contar steps completados`() {
        // Arrange
        val task = Task(title = "Tarefa", iconRes = 1, time = "08:00", stars = 3, category = "HIGIENE_PESSOAL")
        val steps = listOf(
            Step(taskId = 1, title = "Passo 1", order = 0, isCompleted = true),
            Step(taskId = 1, title = "Passo 2", order = 1, isCompleted = false),
            Step(taskId = 1, title = "Passo 3", order = 2, isCompleted = true)
        )
        val taskWithSteps = TaskWithSteps(task, steps)

        // Act
        val completed = taskWithSteps.getCompletedSteps()

        // Assert
        assertEquals(2, completed)
    }

    @Test
    fun `TaskWithSteps getProgressPercentage deve calcular corretamente`() {
        // Arrange
        val task = Task(title = "Tarefa", iconRes = 1, time = "08:00", stars = 3, category = "HIGIENE_PESSOAL")
        val steps = listOf(
            Step(taskId = 1, title = "Passo 1", order = 0, isCompleted = true),
            Step(taskId = 1, title = "Passo 2", order = 1, isCompleted = false),
            Step(taskId = 1, title = "Passo 3", order = 2, isCompleted = false),
            Step(taskId = 1, title = "Passo 4", order = 3, isCompleted = false)
        )
        val taskWithSteps = TaskWithSteps(task, steps)

        // Act
        val progress = taskWithSteps.getProgressPercentage()

        // Assert
        assertEquals(25, progress) // 1 de 4 = 25%
    }

    @Test
    fun `TaskWithSteps com lista vazia deve retornar progresso 0`() {
        // Arrange
        val task = Task(title = "Tarefa", iconRes = 1, time = "08:00", stars = 3, category = "HIGIENE_PESSOAL")
        val taskWithSteps = TaskWithSteps(task, emptyList())

        // Act
        val progress = taskWithSteps.getProgressPercentage()

        // Assert
        assertEquals(0, progress)
    }

    @Test
    fun `TaskWithSteps isFullyCompleted deve retornar true quando todos steps completados`() {
        // Arrange
        val task = Task(title = "Tarefa", iconRes = 1, time = "08:00", stars = 3, category = "HIGIENE_PESSOAL")
        val steps = listOf(
            Step(taskId = 1, title = "Passo 1", order = 0, isCompleted = true),
            Step(taskId = 1, title = "Passo 2", order = 1, isCompleted = true)
        )
        val taskWithSteps = TaskWithSteps(task, steps)

        // Act & Assert
        assertTrue(taskWithSteps.isFullyCompleted())
    }

    @Test
    fun `TaskWithSteps isFullyCompleted deve retornar false quando algum step não completado`() {
        // Arrange
        val task = Task(title = "Tarefa", iconRes = 1, time = "08:00", stars = 3, category = "HIGIENE_PESSOAL")
        val steps = listOf(
            Step(taskId = 1, title = "Passo 1", order = 0, isCompleted = true),
            Step(taskId = 1, title = "Passo 2", order = 1, isCompleted = false)
        )
        val taskWithSteps = TaskWithSteps(task, steps)

        // Act & Assert
        assertFalse(taskWithSteps.isFullyCompleted())
    }

    @Test
    fun `TaskWithSteps com lista vazia deve retornar isFullyCompleted false`() {
        // Arrange
        val task = Task(title = "Tarefa", iconRes = 1, time = "08:00", stars = 3, category = "HIGIENE_PESSOAL")
        val taskWithSteps = TaskWithSteps(task, emptyList())

        // Act & Assert
        assertFalse(taskWithSteps.isFullyCompleted())
    }

    @Test
    fun `TaskWithSteps com 50 porcento completado deve retornar progresso 50`() {
        // Arrange
        val task = Task(title = "Tarefa", iconRes = 1, time = "08:00", stars = 3, category = "HIGIENE_PESSOAL")
        val steps = listOf(
            Step(taskId = 1, title = "Passo 1", order = 0, isCompleted = true),
            Step(taskId = 1, title = "Passo 2", order = 1, isCompleted = true),
            Step(taskId = 1, title = "Passo 3", order = 2, isCompleted = false),
            Step(taskId = 1, title = "Passo 4", order = 3, isCompleted = false)
        )
        val taskWithSteps = TaskWithSteps(task, steps)

        // Act
        val progress = taskWithSteps.getProgressPercentage()

        // Assert
        assertEquals(50, progress)
    }
}
