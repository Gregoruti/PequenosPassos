package com.pequenospassos.data.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pequenospassos.data.database.AppDatabase
import com.pequenospassos.domain.model.ChildProfile
import com.pequenospassos.domain.model.Gender
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

/**
 * Testes instrumentados para ChildProfileDao.
 *
 * Valida operações CRUD de perfil da criança no Room Database.
 * Requer dispositivo/emulador Android para execução.
 *
 * @since MVP-03 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 */
@RunWith(AndroidJUnit4::class)
class ChildProfileDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: ChildProfileDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
        dao = database.childProfileDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertAndGetProfile() = runBlocking {
        // Arrange
        val profile = ChildProfile(
            id = "default_child",
            name = "João",
            gender = Gender.MALE,
            photoUri = null
        )

        // Act
        dao.insertOrUpdate(profile)
        val retrieved = dao.getProfile("default_child").first()

        // Assert
        assertNotNull(retrieved)
        assertEquals("João", retrieved?.name)
        assertEquals(Gender.MALE, retrieved?.gender)
    }

    @Test
    fun updateExistingProfile() = runBlocking {
        // Arrange
        val profile1 = ChildProfile(
            id = "default_child",
            name = "João",
            gender = Gender.MALE
        )
        val profile2 = ChildProfile(
            id = "default_child",
            name = "Maria",
            gender = Gender.FEMALE
        )

        // Act
        dao.insertOrUpdate(profile1)
        dao.insertOrUpdate(profile2) // REPLACE

        val retrieved = dao.getProfile("default_child").first()

        // Assert
        assertNotNull(retrieved)
        assertEquals("Maria", retrieved?.name)
        assertEquals(Gender.FEMALE, retrieved?.gender)
    }

    @Test
    fun getProfileCount() = runBlocking {
        // Arrange
        val profile = ChildProfile(
            id = "default_child",
            name = "João",
            gender = Gender.MALE
        )

        // Act
        val countBefore = dao.getProfileCount()
        dao.insertOrUpdate(profile)
        val countAfter = dao.getProfileCount()

        // Assert
        assertEquals(0, countBefore)
        assertEquals(1, countAfter)
    }

    @Test
    fun getNonExistentProfile() = runBlocking {
        // Act
        val retrieved = dao.getProfile("default_child").first()

        // Assert
        assertNull(retrieved)
    }

    @Test
    fun deleteAllProfiles() = runBlocking {
        // Arrange
        val profile = ChildProfile(
            id = "default_child",
            name = "João",
            gender = Gender.MALE
        )
        dao.insertOrUpdate(profile)

        // Act
        dao.deleteAll()
        val count = dao.getProfileCount()

        // Assert
        assertEquals(0, count)
    }
}

