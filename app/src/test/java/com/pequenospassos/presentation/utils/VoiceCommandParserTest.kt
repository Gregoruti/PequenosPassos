package com.pequenospassos.presentation.utils

import com.pequenospassos.presentation.utils.VoiceCommandParser.CommandResult
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

/**
 * Testes unitários para VoiceCommandParser.
 *
 * Arquivo: test/.../VoiceCommandParserTest.kt
 * Tipo: Unit Test
 * Objetivo: Validar reconhecimento de comandos de voz infantis
 * Correlações: VoiceCommandParser.kt
 *
 * Criado: 2025-11-01
 * Autor: Claude Sonnet 4.5 (GitHub Copilot)
 * Status: MVP-14 Fase 3
 *
 * @since MVP-14 Fase 3 (v2.2.0)
 */
class VoiceCommandParserTest {

    private lateinit var parser: VoiceCommandParser

    @Before
    fun setup() {
        parser = VoiceCommandParser()
    }

    // ========================================
    // TESTES DE COMANDOS POSITIVOS
    // ========================================

    @Test
    fun `parse deve retornar POSITIVE para 'sim'`() {
        assertEquals(CommandResult.POSITIVE, parser.parse("sim"))
    }

    @Test
    fun `parse deve retornar POSITIVE para 'pode'`() {
        assertEquals(CommandResult.POSITIVE, parser.parse("pode"))
    }

    @Test
    fun `parse deve retornar POSITIVE para 'vamos'`() {
        assertEquals(CommandResult.POSITIVE, parser.parse("vamos"))
    }

    @Test
    fun `parse deve retornar POSITIVE para 'tá'`() {
        assertEquals(CommandResult.POSITIVE, parser.parse("tá"))
    }

    @Test
    fun `parse deve retornar POSITIVE para 'ok'`() {
        assertEquals(CommandResult.POSITIVE, parser.parse("ok"))
    }

    @Test
    fun `parse deve retornar POSITIVE para 'pode ir'`() {
        assertEquals(CommandResult.POSITIVE, parser.parse("pode ir"))
    }

    @Test
    fun `parse deve retornar POSITIVE para 'vamos lá'`() {
        assertEquals(CommandResult.POSITIVE, parser.parse("vamos lá"))
    }

    @Test
    fun `parse deve retornar POSITIVE para 'já terminei'`() {
        assertEquals(CommandResult.POSITIVE, parser.parse("já terminei"))
    }

    @Test
    fun `parse deve retornar POSITIVE para 'pronto'`() {
        assertEquals(CommandResult.POSITIVE, parser.parse("pronto"))
    }

    // ========================================
    // TESTES DE COMANDOS NEGATIVOS
    // ========================================

    @Test
    fun `parse deve retornar NEGATIVE para 'não'`() {
        assertEquals(CommandResult.NEGATIVE, parser.parse("não"))
    }

    @Test
    fun `parse deve retornar NEGATIVE para 'espera'`() {
        assertEquals(CommandResult.NEGATIVE, parser.parse("espera"))
    }

    @Test
    fun `parse deve retornar NEGATIVE para 'mais tempo'`() {
        assertEquals(CommandResult.NEGATIVE, parser.parse("mais tempo"))
    }

    @Test
    fun `parse deve retornar NEGATIVE para 'ainda não'`() {
        assertEquals(CommandResult.NEGATIVE, parser.parse("ainda não"))
    }

    @Test
    fun `parse deve retornar NEGATIVE para 'peraí'`() {
        assertEquals(CommandResult.NEGATIVE, parser.parse("peraí"))
    }

    @Test
    fun `parse deve retornar NEGATIVE para 'quero mais tempo'`() {
        assertEquals(CommandResult.NEGATIVE, parser.parse("quero mais tempo"))
    }

    @Test
    fun `parse deve retornar NEGATIVE para 'não terminei'`() {
        assertEquals(CommandResult.NEGATIVE, parser.parse("não terminei"))
    }

    // ========================================
    // TESTES DE COMANDOS DESCONHECIDOS
    // ========================================

    @Test
    fun `parse deve retornar UNKNOWN para texto não reconhecido`() {
        assertEquals(CommandResult.UNKNOWN, parser.parse("banana"))
    }

    @Test
    fun `parse deve retornar UNKNOWN para 'talvez'`() {
        assertEquals(CommandResult.UNKNOWN, parser.parse("talvez"))
    }

    @Test
    fun `parse deve retornar UNKNOWN para 'depois'`() {
        assertEquals(CommandResult.UNKNOWN, parser.parse("depois"))
    }

    @Test
    fun `parse deve retornar UNKNOWN para texto vazio`() {
        assertEquals(CommandResult.UNKNOWN, parser.parse(""))
    }

    @Test
    fun `parse deve retornar UNKNOWN para texto aleatório`() {
        assertEquals(CommandResult.UNKNOWN, parser.parse("xpto 123"))
    }

    // ========================================
    // TESTES DE CASE INSENSITIVE
    // ========================================

    @Test
    fun `parse deve ser case insensitive - SIM maiúsculo`() {
        assertEquals(CommandResult.POSITIVE, parser.parse("SIM"))
    }

    @Test
    fun `parse deve ser case insensitive - Sim capitalizado`() {
        assertEquals(CommandResult.POSITIVE, parser.parse("Sim"))
    }

    @Test
    fun `parse deve ser case insensitive - sIm misto`() {
        assertEquals(CommandResult.POSITIVE, parser.parse("sIm"))
    }

    @Test
    fun `parse deve ser case insensitive - NÃO maiúsculo`() {
        assertEquals(CommandResult.NEGATIVE, parser.parse("NÃO"))
    }

    @Test
    fun `parse deve ser case insensitive - Não capitalizado`() {
        assertEquals(CommandResult.NEGATIVE, parser.parse("Não"))
    }

    // ========================================
    // TESTES DE ESPAÇOS E TRIM
    // ========================================

    @Test
    fun `parse deve remover espaços extras - sim com espaços`() {
        assertEquals(CommandResult.POSITIVE, parser.parse("  sim  "))
    }

    @Test
    fun `parse deve remover espaços extras - não com espaços`() {
        assertEquals(CommandResult.NEGATIVE, parser.parse("  não  "))
    }

    @Test
    fun `parse deve funcionar com espaços no meio - pode ir`() {
        assertEquals(CommandResult.POSITIVE, parser.parse("pode  ir"))
    }

    // ========================================
    // TESTES DE FRASES COMPLETAS
    // ========================================

    @Test
    fun `parse deve reconhecer frase - pode sim`() {
        assertEquals(CommandResult.POSITIVE, parser.parse("pode sim"))
    }

    @Test
    fun `parse deve reconhecer frase - vamos sim`() {
        assertEquals(CommandResult.POSITIVE, parser.parse("vamos sim"))
    }

    @Test
    fun `parse deve reconhecer frase - mais um pouco`() {
        assertEquals(CommandResult.NEGATIVE, parser.parse("mais um pouco"))
    }

    @Test
    fun `parse deve reconhecer frase - só mais um pouco`() {
        assertEquals(CommandResult.NEGATIVE, parser.parse("só mais um pouco"))
    }

    @Test
    fun `parse deve reconhecer frase - ainda tô fazendo`() {
        assertEquals(CommandResult.NEGATIVE, parser.parse("ainda tô fazendo"))
    }

    // ========================================
    // TESTES DE VARIAÇÕES INFANTIS
    // ========================================

    @Test
    fun `parse deve reconhecer variação infantil - uhum`() {
        assertEquals(CommandResult.POSITIVE, parser.parse("uhum"))
    }

    @Test
    fun `parse deve reconhecer variação infantil - aham`() {
        assertEquals(CommandResult.POSITIVE, parser.parse("aham"))
    }

    @Test
    fun `parse deve reconhecer variação infantil - beleza`() {
        assertEquals(CommandResult.POSITIVE, parser.parse("beleza"))
    }

    @Test
    fun `parse deve reconhecer variação infantil - nãããão`() {
        assertEquals(CommandResult.NEGATIVE, parser.parse("nãããão"))
    }

    @Test
    fun `parse deve reconhecer variação infantil - não não`() {
        assertEquals(CommandResult.NEGATIVE, parser.parse("não não"))
    }

    @Test
    fun `parse deve reconhecer variação infantil - perai`() {
        assertEquals(CommandResult.NEGATIVE, parser.parse("perai"))
    }

    // ========================================
    // TESTES DE ESTATÍSTICAS
    // ========================================

    @Test
    fun `getStats deve retornar informações corretas`() {
        val stats = parser.getStats()

        assertTrue(stats.containsKey("totalPositiveCommands"))
        assertTrue(stats.containsKey("totalNegativeCommands"))
        assertTrue(stats.containsKey("totalCommands"))

        // Verifica que há comandos cadastrados
        val totalPositive = stats["totalPositiveCommands"] as Int
        val totalNegative = stats["totalNegativeCommands"] as Int

        assertTrue("Deve ter comandos positivos", totalPositive > 0)
        assertTrue("Deve ter comandos negativos", totalNegative > 0)

        val total = stats["totalCommands"] as Int
        assertEquals(totalPositive + totalNegative, total)
    }

    @Test
    fun `getStats deve ter pelo menos 20 comandos positivos`() {
        val stats = parser.getStats()
        val totalPositive = stats["totalPositiveCommands"] as Int

        assertTrue("Deve ter pelo menos 20 comandos positivos", totalPositive >= 20)
    }

    @Test
    fun `getStats deve ter pelo menos 20 comandos negativos`() {
        val stats = parser.getStats()
        val totalNegative = stats["totalNegativeCommands"] as Int

        assertTrue("Deve ter pelo menos 20 comandos negativos", totalNegative >= 20)
    }
}

