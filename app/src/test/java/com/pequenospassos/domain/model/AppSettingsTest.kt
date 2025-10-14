package com.pequenospassos.domain.model

import org.junit.Assert.*
import org.junit.Test

/**
 * Testes unitários para AppSettings.
 *
 * Valida:
 * - Criação de configurações válidas
 * - Validação de dados (isValid)
 * - Método isNewDay
 * - Configurações padrão (getDefault)
 *
 * @since MVP-02 (13/10/2025)
 */
class AppSettingsTest {

    @Test
    fun `AppSettings válido deve passar na validação`() {
        // Arrange
        val settings = AppSettings(
            id = "settings",
            isFirstRun = false,
            totalStars = 10,
            currentDate = "2025-10-13",
            notificationsEnabled = true
        )

        // Act
        val isValid = settings.isValid()

        // Assert
        assertTrue("Settings válido deve passar na validação", isValid)
    }

    @Test
    fun `AppSettings com ID diferente de 'settings' deve falhar na validação`() {
        // Arrange
        val settings = AppSettings(
            id = "wrong_id",
            totalStars = 10
        )

        // Act & Assert
        assertFalse(settings.isValid())
    }

    @Test
    fun `AppSettings com totalStars negativo deve falhar na validação`() {
        // Arrange
        val settings = AppSettings(
            id = "settings",
            totalStars = -5
        )

        // Act & Assert
        assertFalse(settings.isValid())
    }

    @Test
    fun `AppSettings com totalStars zero deve passar na validação`() {
        // Arrange
        val settings = AppSettings(
            id = "settings",
            totalStars = 0
        )

        // Act & Assert
        assertTrue(settings.isValid())
    }

    @Test
    fun `isNewDay deve retornar true quando as datas são diferentes`() {
        // Arrange
        val settings = AppSettings(
            currentDate = "2025-10-13"
        )

        // Act
        val isNewDay = settings.isNewDay("2025-10-14")

        // Assert
        assertTrue(isNewDay)
    }

    @Test
    fun `isNewDay deve retornar false quando as datas são iguais`() {
        // Arrange
        val settings = AppSettings(
            currentDate = "2025-10-13"
        )

        // Act
        val isNewDay = settings.isNewDay("2025-10-13")

        // Assert
        assertFalse(isNewDay)
    }

    @Test
    fun `getDefault deve retornar configurações padrão válidas`() {
        // Act
        val defaultSettings = AppSettings.getDefault()

        // Assert
        assertTrue("Settings padrão devem ser válidos", defaultSettings.isValid())
        assertEquals("settings", defaultSettings.id)
        assertTrue("Deve ser primeira execução", defaultSettings.isFirstRun)
        assertEquals(0, defaultSettings.totalStars)
        assertTrue("Notificações devem estar habilitadas", defaultSettings.notificationsEnabled)
        assertNotEquals("", defaultSettings.currentDate)
    }

    @Test
    fun `AppSettings padrão deve ter ID 'settings'`() {
        // Arrange & Act
        val settings = AppSettings()

        // Assert
        assertEquals("settings", settings.id)
    }

    @Test
    fun `AppSettings padrão deve ter isFirstRun true`() {
        // Arrange & Act
        val settings = AppSettings()

        // Assert
        assertTrue(settings.isFirstRun)
    }

    @Test
    fun `AppSettings padrão deve ter totalStars zero`() {
        // Arrange & Act
        val settings = AppSettings()

        // Assert
        assertEquals(0, settings.totalStars)
    }

    @Test
    fun `AppSettings padrão deve ter notificationsEnabled true`() {
        // Arrange & Act
        val settings = AppSettings()

        // Assert
        assertTrue(settings.notificationsEnabled)
    }

    @Test
    fun `AppSettings deve ter timestamp de última sincronização`() {
        // Arrange
        val beforeCreation = System.currentTimeMillis()

        // Act
        val settings = AppSettings()

        val afterCreation = System.currentTimeMillis()

        // Assert
        assertTrue(settings.lastSyncTimestamp in beforeCreation..afterCreation)
    }

    @Test
    fun `getDefault deve gerar currentDate no formato YYYY-MM-DD`() {
        // Act
        val settings = AppSettings.getDefault()

        // Assert
        assertTrue("Data deve estar no formato YYYY-MM-DD",
            settings.currentDate.matches(Regex("^\\d{4}-\\d{2}-\\d{2}$")))
    }

    @Test
    fun `isNewDay deve ser sensível a mudanças de mês`() {
        // Arrange
        val settings = AppSettings(currentDate = "2025-10-31")

        // Act & Assert
        assertTrue(settings.isNewDay("2025-11-01"))
    }

    @Test
    fun `isNewDay deve ser sensível a mudanças de ano`() {
        // Arrange
        val settings = AppSettings(currentDate = "2025-12-31")

        // Act & Assert
        assertTrue(settings.isNewDay("2026-01-01"))
    }
}

