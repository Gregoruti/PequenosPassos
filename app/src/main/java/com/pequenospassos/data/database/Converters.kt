package com.pequenospassos.data.database

import androidx.room.TypeConverter
import com.pequenospassos.domain.model.Gender
import com.pequenospassos.domain.model.TaskStatus

/**
 * Converters do Room Database.
 *
 * Converte tipos personalizados (enums) para tipos que o Room pode armazenar.
 *
 * @since MVP-02 (13/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 * @validationStatus ✅ Implementado - MVP-02
 */
class Converters {

    // Gender Converters

    @TypeConverter
    fun fromGender(gender: Gender): String = gender.name

    @TypeConverter
    fun toGender(value: String): Gender = Gender.valueOf(value)

    // TaskStatus Converters

    @TypeConverter
    fun fromTaskStatus(status: TaskStatus): String = status.name

    @TypeConverter
    fun toTaskStatus(value: String): TaskStatus = TaskStatus.valueOf(value)
}
