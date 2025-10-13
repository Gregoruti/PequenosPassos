package com.pequenospassos.data.database

import androidx.room.TypeConverter
import com.pequenospassos.domain.model.Gender
import com.pequenospassos.domain.model.TaskStatus

/**
 * TypeConverters para Room Database.
 *
 * Converte enums para String e vice-versa para armazenamento no SQLite.
 * Essencial para o funcionamento das entidades com enums no Room.
 *
 * @since MVP 1.3.0 (12/10/2025) - DIA 1 - MVP-02
 * @author MVP Development Team
 * @validationStatus ✅ Implementado - MVP-02
 */
class Converters {

    @TypeConverter
    fun fromGender(gender: Gender): String = gender.name

    @TypeConverter
    fun toGender(gender: String): Gender = Gender.valueOf(gender)

    @TypeConverter
    fun fromTaskStatus(status: TaskStatus): String = status.name

    @TypeConverter
    fun toTaskStatus(status: String): TaskStatus = TaskStatus.valueOf(status)
}
