package com.pequenospassos.data.repository

import com.pequenospassos.data.database.dao.AppSettingsDao
import com.pequenospassos.domain.model.AppSettings
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Testes unitários para AppSettingsRepositoryImpl.
 *
 * Valida o comportamento do repositório de configurações usando DAO mockado.
 *
 * @since MVP-04 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 */
class AppSettingsRepositoryImplTest {

    private lateinit var dao: AppSettingsDao
    private lateinit var repository: AppSettingsRepositoryImpl

    @Before
    fun setup() {
        dao = mockk()
        repository = AppSettingsRepositoryImpl(dao)
    }

    @Test
    fun `getSettings retorna flow do DAO`() = runTest {
        // Arrange
        val settings = AppSettings(
            id = "settings",
            isFirstRun = false,
            totalStars = 10,
            currentDate = "2025-10-14"
        )
        coEvery { dao.getSettings("settings") } returns flowOf(settings)

        // Act
        val result = repository.getSettings().first()

        // Assert
        assertNotNull(result)
        assertEquals(10, result?.totalStars)
        assertFalse(result?.isFirstRun ?: true)
    }

    @Test
    fun `updateSettings sucesso retorna Result success`() = runTest {
        // Arrange
        val settings = AppSettings(
            id = "settings",
            totalStars = 20
        )
        coEvery { dao.updateSettings(settings) } returns Unit

        // Act
        val result = repository.updateSettings(settings)

        // Assert
        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { dao.updateSettings(settings) }
    }

    @Test
    fun `updateSettings com erro retorna Result failure`() = runTest {
        // Arrange
        val settings = AppSettings(id = "settings")
        val exception = RuntimeException("Update error")
        coEvery { dao.updateSettings(settings) } throws exception

        // Act
        val result = repository.updateSettings(settings)

        // Assert
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `isFirstRun retorna true quando settings indica primeira execução`() = runTest {
        // Arrange
        val settings = AppSettings(id = "settings", isFirstRun = true)
        coEvery { dao.getSettings("settings") } returns flowOf(settings)

        // Act
        val result = repository.isFirstRun()

        // Assert
        assertTrue(result)
    }

    @Test
    fun `isFirstRun retorna false quando settings indica não é primeira execução`() = runTest {
        // Arrange
        val settings = AppSettings(id = "settings", isFirstRun = false)
        coEvery { dao.getSettings("settings") } returns flowOf(settings)

        // Act
        val result = repository.isFirstRun()

        // Assert
        assertFalse(result)
    }

    @Test
    fun `isFirstRun retorna true quando settings não existe`() = runTest {
        // Arrange
        coEvery { dao.getSettings("settings") } returns flowOf(null)

        // Act
        val result = repository.isFirstRun()

        // Assert
        assertTrue(result)
    }

    @Test
    fun `isFirstRun retorna true em caso de erro`() = runTest {
        // Arrange
        coEvery { dao.getSettings("settings") } throws RuntimeException("Error")

        // Act
        val result = repository.isFirstRun()

        // Assert
        assertTrue(result)
    }

    @Test
    fun `markFirstRunCompleted sucesso retorna Result success`() = runTest {
        // Arrange
        coEvery { dao.markFirstRunComplete("settings") } returns Unit

        // Act
        val result = repository.markFirstRunCompleted()

        // Assert
        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { dao.markFirstRunComplete("settings") }
    }

    @Test
    fun `markFirstRunCompleted com erro retorna Result failure`() = runTest {
        // Arrange
        val exception = RuntimeException("Mark error")
        coEvery { dao.markFirstRunComplete("settings") } throws exception

        // Act
        val result = repository.markFirstRunCompleted()

        // Assert
        assertTrue(result.isFailure)
    }

    @Test
    fun `updateTotalStars sucesso retorna Result success`() = runTest {
        // Arrange
        coEvery { dao.updateTotalStars(50, "settings") } returns Unit

        // Act
        val result = repository.updateTotalStars(50)

        // Assert
        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { dao.updateTotalStars(50, "settings") }
    }

    @Test
    fun `updateCurrentDate sucesso retorna Result success`() = runTest {
        // Arrange
        val date = "2025-10-14"
        coEvery { dao.updateCurrentDate(date, "settings") } returns Unit

        // Act
        val result = repository.updateCurrentDate(date)

        // Assert
        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { dao.updateCurrentDate(date, "settings") }
    }

    @Test
    fun `getDefaultSettings retorna configurações padrão`() = runTest {
        // Act
        val result = repository.getDefaultSettings()

        // Assert
        assertEquals("settings", result.id)
        assertTrue(result.isFirstRun)
        assertEquals(0, result.totalStars)
        assertTrue(result.notificationsEnabled)
        assertNotNull(result.currentDate)
        assertTrue(result.currentDate.matches(Regex("\\d{4}-\\d{2}-\\d{2}")))
    }
}

