// filepath: d:\Softwares\PequenosPassos\app\src\main\java\com\pequenospassos\data\export\ExportModels.kt
package com.pequenospassos.data.export

import kotlinx.serialization.Serializable

/**
 * MODELOS DE EXPORTAÇÃO/IMPORTAÇÃO - v2.5.0
 *
 * HISTÓRICO DE MUDANÇAS:
 * - v2.5.0 (02/12/2025): Criação inicial
 *   - Sistema de export/import de tarefas
 *   - Formato JSON legível e editável
 *   - Suporte a imagens em Base64
 *   - Validação de schema
 *   - Code Assistant: Claude Sonnet 4.5 (GitHub Copilot)
 *
 * PROPÓSITO:
 * - Permitir backup de tarefas personalizadas
 * - Facilitar edição de textos em editor externo
 * - Compartilhar tarefas entre dispositivos
 * - Versionamento de conteúdo
 *
 * RELACIONAMENTOS:
 * - ExportImportManager.kt: Usa estes modelos para serialização
 * - Task.kt / Step.kt: Modelos de domínio originais
 * - TaskRepository.kt: Fonte de dados para export
 *
 * @since v2.5.0
 * @author PequenosPassos Development Team
 */

/**
 * Modelo raiz de exportação contendo metadados e lista de tarefas.
 *
 * Este é o formato JSON que será salvo em arquivo e poderá ser
 * editado diretamente no VSCode ou Notepad++.
 */
@Serializable
data class TaskExportRoot(
    val version: String = "2.5.0",
    val exportDate: String,
    val metadata: ExportMetadata,
    val tasks: List<TaskExportModel>
)

/**
 * Metadados da exportação para rastreabilidade.
 */
@Serializable
data class ExportMetadata(
    val appName: String = "Pequenos Passos",
    val exportedBy: String = "user",
    val totalTasks: Int,
    val deviceModel: String? = null,
    val androidVersion: String? = null
)

/**
 * Modelo de tarefa para exportação/importação.
 *
 * Campos editáveis pelo usuário:
 * - title: Nome da tarefa
 * - categoryName: Categoria (27 opções)
 * - starsReward: Estrelas (1-5)
 * - steps: Lista de passos
 *
 * @property id ID original (preservado para merge)
 * @property isPredefined Marca se é tarefa pré-instalada
 */
@Serializable
data class TaskExportModel(
    val id: Long,
    val title: String,
    val categoryId: Long,
    val categoryName: String,
    val starsReward: Int,
    val isPredefined: Boolean = false,
    val createdAt: String,
    val steps: List<StepExportModel>
)

/**
 * Modelo de passo para exportação/importação.
 *
 * Campos editáveis pelo usuário:
 * - description: Texto do passo (principal campo a editar)
 * - durationSeconds: Tempo do timer (15-600s)
 * - helpText: Dica adicional (opcional)
 *
 * @property imageBase64 Imagem em Base64 (incluída no JSON)
 * @property imageFileName Nome original do arquivo (referência)
 */
@Serializable
data class StepExportModel(
    val stepNumber: Int,
    val description: String,
    val durationSeconds: Int = 60,
    val helpText: String? = null,
    val imageFileName: String? = null,
    val imageBase64: String? = null
)

/**
 * Resultado de uma operação de importação.
 *
 * Fornece feedback detalhado sobre o que foi importado,
 * ignorado ou com erro.
 */
data class ImportResult(
    val success: Boolean,
    val tasksImported: Int,
    val tasksSkipped: Int,
    val tasksOverwritten: Int,
    val errors: List<String> = emptyList(),
    val warnings: List<String> = emptyList()
)

/**
 * Modo de importação para resolver conflitos.
 */
enum class ImportMode {
    /** Mantém tarefas existentes, importa apenas novas */
    MERGE_KEEP_EXISTING,

    /** Sobrescreve tarefas existentes com dados do JSON */
    MERGE_OVERWRITE,

    /** Remove todas as tarefas e importa do JSON */
    REPLACE_ALL
}

/**
 * Erros de validação do JSON.
 */
sealed class ValidationError {
    data class InvalidSchema(val message: String) : ValidationError()
    data class MissingRequiredField(val field: String) : ValidationError()
    data class InvalidFieldValue(val field: String, val value: String) : ValidationError()
    data class UnsupportedVersion(val version: String) : ValidationError()
}

