package com.pequenospassos.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidade de perfil da criança.
 *
 * Armazena informações básicas da criança que utiliza o app.
 * MVP-02: Entidade essencial para cadastro e personalização.
 *
 * @property id Identificador único (padrão: "default_child" para single-user MVP)
 * @property name Nome da criança (obrigatório)
 * @property gender Gênero da criança (MALE ou FEMALE)
 * @property photoUri URI da foto da criança (opcional, capturada da câmera/galeria)
 * @property createdAt Timestamp de criação do perfil
 *
 * @since MVP-02 (13/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-02
 */
@Entity(tableName = "child_profile")
data class ChildProfile(
    @PrimaryKey
    val id: String = "default_child",

    val name: String,

    val gender: Gender,

    val photoUri: String? = null,

    val createdAt: Long = System.currentTimeMillis()
) {
    /**
     * Valida se o perfil tem dados mínimos necessários
     */
    fun isValid(): Boolean {
        return name.isNotBlank() && name.length >= 2
    }
}

/**
 * Enum de gênero da criança.
 * Usado no cadastro para personalização de interface e atividades.
 *
 * @property MALE Masculino
 * @property FEMALE Feminino
 */
enum class Gender {
    MALE,
    FEMALE;

    /**
     * Retorna o nome formatado para exibição
     */
    fun getDisplayName(): String = when(this) {
        MALE -> "Menino"
        FEMALE -> "Menina"
    }
}

