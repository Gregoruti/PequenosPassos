package com.pequenospassos.domain.model

/**
 * Wrapper de resultado para operações que podem falhar.
 *
 * Encapsula sucesso ou erro de operações de negócio,
 * fornecendo métodos funcionais para manipulação.
 *
 * @param T Tipo do valor de sucesso
 *
 * @since MVP-05 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-05
 */
sealed class AppResult<out T> {

    /**
     * Representa um resultado bem-sucedido
     * @property data Dados retornados pela operação
     */
    data class Success<T>(val data: T) : AppResult<T>()

    /**
     * Representa um resultado com erro
     * @property exception Exceção que causou a falha
     */
    data class Error(val exception: Throwable) : AppResult<Nothing>()

    /**
     * Executa uma ação se o resultado for sucesso
     * @param action Ação a executar com os dados
     * @return O próprio AppResult para encadeamento
     */
    inline fun onSuccess(action: (value: T) -> Unit): AppResult<T> {
        if (this is Success) action(data)
        return this
    }

    /**
     * Executa uma ação se o resultado for erro
     * @param action Ação a executar com a exceção
     * @return O próprio AppResult para encadeamento
     */
    inline fun onError(action: (exception: Throwable) -> Unit): AppResult<T> {
        if (this is Error) action(exception)
        return this
    }

    /**
     * Verifica se o resultado é sucesso
     */
    fun isSuccess(): Boolean = this is Success

    /**
     * Verifica se o resultado é erro
     */
    fun isError(): Boolean = this is Error

    /**
     * Obtém o dado ou null se erro
     */
    fun getOrNull(): T? = when (this) {
        is Success -> data
        is Error -> null
    }

    /**
     * Obtém o dado ou lança exceção se erro
     */
    fun getOrThrow(): T = when (this) {
        is Success -> data
        is Error -> throw exception
    }
}

