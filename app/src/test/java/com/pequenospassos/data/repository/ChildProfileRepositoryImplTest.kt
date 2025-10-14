package com.pequenospassos.data.repository

import com.pequenospassos.data.database.dao.ChildProfileDao
import com.pequenospassos.domain.model.ChildProfile
import com.pequenospassos.domain.model.Gender
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
 * Testes unitários para ChildProfileRepositoryImpl.
 *
 * Valida o comportamento do repositório usando DAO mockado.
 *
 * @since MVP-04 (14/10/2025) - DIA 1 - Fundação
 * @author PequenosPassos Development Team
 */
class ChildProfileRepositoryImplTest {

    private lateinit var dao: ChildProfileDao
    private lateinit var repository: ChildProfileRepositoryImpl

    @Before
    fun setup() {
        dao = mockk()
        repository = ChildProfileRepositoryImpl(dao)
    }

    @Test
    fun `getProfile retorna flow do DAO`() = runTest {
        // Arrange
        val profile = ChildProfile(
            id = "default_child",
            name = "João",
            gender = Gender.MALE
        )
        coEvery { dao.getProfile() } returns flowOf(profile)

        // Act
        val result = repository.getProfile().first()

        // Assert
        assertNotNull(result)
        assertEquals("João", result?.name)
        assertEquals(Gender.MALE, result?.gender)
        coVerify(exactly = 1) { dao.getProfile() }
    }

    @Test
    fun `saveProfile sucesso retorna Result success`() = runTest {
        // Arrange
        val profile = ChildProfile(
            id = "default_child",
            name = "Maria",
            gender = Gender.FEMALE
        )
        coEvery { dao.insertOrUpdate(profile) } returns Unit

        // Act
        val result = repository.saveProfile(profile)

        // Assert
        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { dao.insertOrUpdate(profile) }
    }

    @Test
    fun `saveProfile com erro retorna Result failure`() = runTest {
        // Arrange
        val profile = ChildProfile(
            id = "default_child",
            name = "João",
            gender = Gender.MALE
        )
        val exception = RuntimeException("Database error")
        coEvery { dao.insertOrUpdate(profile) } throws exception

        // Act
        val result = repository.saveProfile(profile)

        // Assert
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `hasProfile retorna true quando existe perfil`() = runTest {
        // Arrange
        coEvery { dao.getProfileCount() } returns 1

        // Act
        val result = repository.hasProfile()

        // Assert
        assertTrue(result)
        coVerify(exactly = 1) { dao.getProfileCount() }
    }

    @Test
    fun `hasProfile retorna false quando não existe perfil`() = runTest {
        // Arrange
        coEvery { dao.getProfileCount() } returns 0

        // Act
        val result = repository.hasProfile()

        // Assert
        assertFalse(result)
    }

    @Test
    fun `hasProfile retorna false em caso de erro`() = runTest {
        // Arrange
        coEvery { dao.getProfileCount() } throws RuntimeException("Error")

        // Act
        val result = repository.hasProfile()

        // Assert
        assertFalse(result)
    }

    @Test
    fun `deleteAllProfiles sucesso retorna Result success`() = runTest {
        // Arrange
        coEvery { dao.deleteAll() } returns Unit

        // Act
        val result = repository.deleteAllProfiles()

        // Assert
        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { dao.deleteAll() }
    }

    @Test
    fun `deleteAllProfiles com erro retorna Result failure`() = runTest {
        // Arrange
        val exception = RuntimeException("Delete error")
        coEvery { dao.deleteAll() } throws exception

        // Act
        val result = repository.deleteAllProfiles()

        // Assert
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}

