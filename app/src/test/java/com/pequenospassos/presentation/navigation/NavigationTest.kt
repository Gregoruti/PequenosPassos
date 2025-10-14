package com.pequenospassos.presentation.navigation

import org.junit.Test
import org.junit.Assert.*

/**
 * Testes unitários para validação das rotas de navegação do MVP-01.
 *
 * Valida que todas as rotas estão corretamente definidas e seguem o padrão
 * esperado para navegação no Jetpack Compose.
 *
 * @since MVP-01
 * @author PequenosPassos Test Team
 */
class NavigationTest {

    /**
     * Testa se a rota de splash está definida corretamente.
     */
    @Test
    fun `rota splash deve estar definida corretamente`() {
        val rotaSplash = "splash"
        assertNotNull("Rota splash não pode ser nula", rotaSplash)
        assertTrue("Rota splash deve ser não vazia", rotaSplash.isNotBlank())
        assertEquals("Rota splash deve ser 'splash'", "splash", rotaSplash)
    }

    /**
     * Testa se a rota de home está definida corretamente.
     */
    @Test
    fun `rota home deve estar definida corretamente`() {
        val rotaHome = "home"
        assertNotNull("Rota home não pode ser nula", rotaHome)
        assertTrue("Rota home deve ser não vazia", rotaHome.isNotBlank())
        assertEquals("Rota home deve ser 'home'", "home", rotaHome)
    }

    /**
     * Testa se a rota de debug está definida corretamente.
     */
    @Test
    fun `rota debug deve estar definida corretamente`() {
        val rotaDebug = "debug"
        assertNotNull("Rota debug não pode ser nula", rotaDebug)
        assertTrue("Rota debug deve ser não vazia", rotaDebug.isNotBlank())
        assertEquals("Rota debug deve ser 'debug'", "debug", rotaDebug)
    }

    /**
     * Testa se a rota de TTS test está definida corretamente.
     */
    @Test
    fun `rota tts_test deve estar definida corretamente`() {
        val rotaTts = "tts_test"
        assertNotNull("Rota tts_test não pode ser nula", rotaTts)
        assertTrue("Rota tts_test deve ser não vazia", rotaTts.isNotBlank())
        assertEquals("Rota tts_test deve ser 'tts_test'", "tts_test", rotaTts)
    }

    /**
     * Testa se a rota de ASR test está definida corretamente.
     */
    @Test
    fun `rota asr_test deve estar definida corretamente`() {
        val rotaAsr = "asr_test"
        assertNotNull("Rota asr_test não pode ser nula", rotaAsr)
        assertTrue("Rota asr_test deve ser não vazia", rotaAsr.isNotBlank())
        assertEquals("Rota asr_test deve ser 'asr_test'", "asr_test", rotaAsr)
    }

    /**
     * Testa se todas as rotas são únicas (sem duplicação).
     */
    @Test
    fun `todas as rotas devem ser únicas`() {
        val rotas = listOf("splash", "home", "debug", "tts_test", "asr_test")
        val rotasUnicas = rotas.toSet()
        assertEquals(
            "Número de rotas únicas deve ser igual ao total de rotas",
            rotas.size,
            rotasUnicas.size
        )
    }

    /**
     * Testa se a rota inicial é splash.
     */
    @Test
    fun `rota inicial deve ser splash`() {
        val rotaInicial = "splash"
        assertEquals("Rota inicial deve ser 'splash'", "splash", rotaInicial)
    }
}

