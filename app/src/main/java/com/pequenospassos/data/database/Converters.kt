package com.pequenospassos.data.database

import androidx.room.TypeConverter
import com.pequenospassos.domain.model.Gender
import com.pequenospassos.domain.model.TaskStatus

/**
 * TypeConverters para Room Database.
 *
 * Converte tipos customizados (enums) para tipos primitivos que o Room suporta.
 * Necessário para armazenar Gender e TaskStatus no SQLite.
 *
 * @since MVP-03 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-03
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
}

