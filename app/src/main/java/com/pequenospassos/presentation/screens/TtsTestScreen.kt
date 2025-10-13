package com.pequenospassos.presentation.screens

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.util.Locale

/**
 * Tela de Teste TTS (Text-to-Speech)
 * Permite testar a síntese de voz em português brasileiro
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TtsTestScreen(navController: NavController) {
    val context = LocalContext.current
    var textToSpeak by remember { mutableStateOf("Olá! Este é um teste de síntese de voz.") }
    var ttsStatus by remember { mutableStateOf("Inicializando TTS...") }
    var isTtsReady by remember { mutableStateOf(false) }

    val tts = remember {
        var ttsInstance: TextToSpeech? = null
        ttsInstance = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                ttsInstance?.let { engine ->
                    val result = engine.setLanguage(Locale.forLanguageTag("pt-BR"))
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        ttsStatus = "❌ Idioma PT-BR não suportado"
                        isTtsReady = false
                    } else {
                        ttsStatus = "✅ TTS pronto para uso"
                        isTtsReady = true
                    }
                }
            } else {
                ttsStatus = "❌ Erro ao inicializar TTS"
                isTtsReady = false
            }
        }
        ttsInstance
    }

    DisposableEffect(Unit) {
        onDispose {
            tts?.stop()
            tts?.shutdown()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Teste TTS (Text-to-Speech)") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Status Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (isTtsReady)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Status do TTS",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = ttsStatus,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            // Input de Texto
            OutlinedTextField(
                value = textToSpeak,
                onValueChange = { textToSpeak = it },
                label = { Text("Texto para falar") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5,
                enabled = isTtsReady
            )

            // Botão de Falar
            Button(
                onClick = {
                    if (textToSpeak.isNotBlank()) {
                        tts.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, null)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isTtsReady && textToSpeak.isNotBlank()
            ) {
                Icon(Icons.Default.Settings, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Falar Texto")
            }

            HorizontalDivider()

            // Testes Pré-Definidos
            Text(
                text = "Testes Rápidos",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            val quickTests = listOf(
                "Bom dia! Como você está?",
                "Vamos fazer uma atividade divertida!",
                "Muito bem! Continue assim!",
                "Um, dois, três, quatro, cinco"
            )

            quickTests.forEach { testText ->
                OutlinedButton(
                    onClick = {
                        tts.speak(testText, TextToSpeech.QUEUE_FLUSH, null, null)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isTtsReady
                ) {
                    Text(testText, maxLines = 1)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Informações Técnicas
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "ℹ️ Informações Técnicas",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• Engine: Android TTS nativo", style = MaterialTheme.typography.bodySmall)
                    Text("• Idioma: Português Brasil (pt-BR)", style = MaterialTheme.typography.bodySmall)
                    Text("• Modo: QUEUE_FLUSH (substitui fala anterior)", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
