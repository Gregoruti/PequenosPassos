package com.pequenospassos.data.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pequenospassos.data.database.AppDatabase
import com.pequenospassos.domain.model.AppSettings
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

/**
 * Testes instrumentados para AppSettingsDao.
 *
 * Valida operações de configurações do aplicativo.
 * Testa single-instance (ID fixo "settings").
 *
 * @since MVP-03 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 */
@RunWith(AndroidJUnit4::class)
class AppSettingsDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: AppSettingsDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
        dao = database.appSettingsDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertAndGetSettings() = runBlocking {
        // Arrange
        val settings = AppSettings(
            id = "settings",
            isFirstRun = true,
            totalStars = 0,
            currentDate = "2025-10-14",
            notificationsEnabled = true
        )

        // Act
        dao.updateSettings(settings)
        val retrieved = dao.getSettings("settings").first()

        // Assert
        assertNotNull(retrieved)
        assertTrue(retrieved?.isFirstRun ?: false)
        assertEquals(0, retrieved?.totalStars)
        assertEquals("2025-10-14", retrieved?.currentDate)
    }

    @Test
    fun updateExistingSettings() = runBlocking {
        // Arrange
        val settings1 = AppSettings(
            id = "settings",
            isFirstRun = true,
            totalStars = 10,
            currentDate = "2025-10-14"
        )
        val settings2 = AppSettings(
            id = "settings",
            isFirstRun = false,
            totalStars = 25,
            currentDate = "2025-10-15"
        )

        // Act
        dao.updateSettings(settings1)
        dao.updateSettings(settings2) // REPLACE

        val retrieved = dao.getSettings("settings").first()

        // Assert
        assertNotNull(retrieved)
        assertFalse(retrieved?.isFirstRun ?: true)
        assertEquals(25, retrieved?.totalStars)
        assertEquals("2025-10-15", retrieved?.currentDate)
    }

    @Test
    fun updateTotalStars() = runBlocking {
        // Arrange
        val settings = AppSettings(
            id = "settings",
            totalStars = 10
        )
        dao.updateSettings(settings)

        // Act
        dao.updateTotalStars(50)

        val retrieved = dao.getSettings("settings").first()

        // Assert
        assertEquals(50, retrieved?.totalStars)
    }

    @Test
    fun updateCurrentDate() = runBlocking {
        // Arrange
        val settings = AppSettings(
            id = "settings",
            currentDate = "2025-10-14"
        )
        dao.updateSettings(settings)

        // Act
        dao.updateCurrentDate("2025-10-15")

        val retrieved = dao.getSettings("settings").first()

        // Assert
        assertEquals("2025-10-15", retrieved?.currentDate)
    }

    @Test
    fun markFirstRunComplete() = runBlocking {
        // Arrange
        val settings = AppSettings(
            id = "settings",
            isFirstRun = true
        )
        dao.updateSettings(settings)

        // Act
        dao.markFirstRunComplete()

        val retrieved = dao.getSettings("settings").first()

        // Assert
        assertFalse(retrieved?.isFirstRun ?: true)
    }

    @Test
    fun getNonExistentSettings() = runBlocking {
        // Act
        val retrieved = dao.getSettings("settings").first()

        // Assert
        assertNull(retrieved)
    }

    @Test
    fun deleteAllSettings() = runBlocking {
        // Arrange
        val settings = AppSettings(id = "settings", totalStars = 10)
        dao.updateSettings(settings)

        // Act
        dao.deleteAll()
        val retrieved = dao.getSettings("settings").first()

        // Assert
        assertNull(retrieved)
    }
}

