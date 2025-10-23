package com.pequenospassos.data.database

import androidx.room.TypeConverter
import com.pequenospassos.domain.model.Gender
import com.pequenospassos.domain.model.RewardType
import com.pequenospassos.domain.model.TaskStatus
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

/**
 * TypeConverters para Room Database.
 *
 * Converte tipos customizados (enums) para tipos primitivos que o Room suporta.
 * Necessário para armazenar Gender, TaskStatus e RewardType no SQLite.
 *
 * @since MVP-03 (14/10/2025) - DIA 1 - Fundação
 * @updated MVP-08 - Adicionado suporte a RewardType
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-03, MVP-08
 */
class Converters {

    /**
     * Converte Gender para String (armazenamento)
     */
    @TypeConverter
    fun fromGender(gender: Gender): String = gender.name

    /**
     * Converte String para Gender (leitura)
     */
    @TypeConverter
    fun toGender(value: String): Gender = Gender.valueOf(value)

    /**
     * Converte TaskStatus para String (armazenamento)
     */
    @TypeConverter
    fun fromTaskStatus(status: TaskStatus): String = status.name

    /**
     * Converte String para TaskStatus (leitura)
     */
    @TypeConverter
    fun toTaskStatus(value: String): TaskStatus = TaskStatus.valueOf(value)

    /**
     * Converte RewardType para String (armazenamento)
     * MVP08 - Sistema de Gamificação
     */
    @TypeConverter
    fun fromRewardType(type: RewardType): String = type.name

    /**
     * Converte String para RewardType (leitura)
     * MVP08 - Sistema de Gamificação
     */
    @TypeConverter
    fun toRewardType(value: String): RewardType = RewardType.valueOf(value)

    /**
     * Converte LocalDateTime para Long timestamp (armazenamento)
     * MVP08 - Sistema de Gamificação
     *
     * Usa timestamp em segundos desde epoch (1970-01-01T00:00:00Z)
     */
    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime?): Long? {
        return dateTime?.atZone(java.time.ZoneId.systemDefault())?.toEpochSecond()
    }

    /**
     * Converte Long timestamp para LocalDateTime (leitura)
     * MVP08 - Sistema de Gamificação
     */
    @TypeConverter
    fun toLocalDateTime(timestamp: Long?): LocalDateTime? {
        return timestamp?.let {
            LocalDateTime.ofEpochSecond(it, 0, java.time.ZoneOffset.UTC)
        }
    }
}

