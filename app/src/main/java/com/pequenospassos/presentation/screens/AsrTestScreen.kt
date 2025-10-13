package com.pequenospassos.presentation.screens

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechService
import java.io.File
import org.json.JSONObject

/**
 * Tela de Teste ASR (Automatic Speech Recognition) com Vosk
 * Permite testar o reconhecimento de voz offline em português brasileiro
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun AsrTestScreen(navController: NavController) {
    val context = LocalContext.current
    var asrStatus by remember { mutableStateOf("Inicializando ASR...") }
    var recognizedText by remember { mutableStateOf("") }
    var partialText by remember { mutableStateOf("") }
    var isListening by remember { mutableStateOf(false) }
    var speechService by remember { mutableStateOf<SpeechService?>(null) }
    var model by remember { mutableStateOf<Model?>(null) }

    val micPermissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)

    // Inicializar modelo Vosk
    LaunchedEffect(Unit) {
        try {
            val assetManager = context.assets
            val modelPath = File(context.filesDir, "vosk-model-small-pt-0.3")

            // Função auxiliar para copiar recursivamente com tratamento de erro
            fun copyAssetFolder(srcPath: String, dstPath: File): Boolean {
                return try {
                    val assets = assetManager.list(srcPath)
                    if (assets == null || assets.isEmpty()) {
                        // É um arquivo, não uma pasta
                        assetManager.open(srcPath).use { input ->
                            dstPath.parentFile?.mkdirs()
                            dstPath.outputStream().use { output ->
                                input.copyTo(output)
                            }
                        }
                        true
                    } else {
                        // É uma pasta
                        if (!dstPath.exists()) {
                            dstPath.mkdirs()
                        }
                        var success = true
                        for (asset in assets) {
                            val newSrcPath = if (srcPath.isEmpty()) asset else "$srcPath/$asset"
                            val newDstPath = File(dstPath, asset)
                            if (!copyAssetFolder(newSrcPath, newDstPath)) {
                                success = false
                            }
                        }
                        success
                    }
                } catch (e: Exception) {
                    asrStatus = "❌ Erro ao copiar $srcPath: ${e.message}"
                    false
                }
            }

            // Se o modelo não existe ou está incompleto, força recriação
            val uuidFile = File(modelPath, "uuid")
            val ivectorDir = File(modelPath, "ivector")

            if (!modelPath.exists() || !uuidFile.exists() || !ivectorDir.exists()) {
                asrStatus = "📦 Extraindo modelo do assets..."

                // Remove diretório antigo se existir parcialmente
                if (modelPath.exists()) {
                    modelPath.deleteRecursively()
                }

                // Copia tudo do assets
                if (copyAssetFolder("vosk-model-small-pt-0.3", modelPath)) {
                    // Verifica se todos os arquivos críticos foram copiados
                    val criticalFiles = listOf("uuid", "final.mdl", "Gr.fst", "HCLr.fst")
                    val missingFiles = criticalFiles.filter { !File(modelPath, it).exists() }

                    if (missingFiles.isEmpty() && ivectorDir.exists()) {
                        asrStatus = "✅ Modelo extraído com sucesso"
                    } else {
                        asrStatus = "❌ Arquivos ausentes: ${missingFiles.joinToString(", ")}"
                        return@LaunchedEffect
                    }
                } else {
                    asrStatus = "❌ Falha ao extrair modelo do assets"
                    return@LaunchedEffect
                }
            }

            // Verifica se o UUID existe antes de carregar
            if (!uuidFile.exists()) {
                asrStatus = "❌ UUID não encontrado. Arquivo esperado em: ${uuidFile.absolutePath}"
                return@LaunchedEffect
            }

            // Agora carrega o modelo
            if (modelPath.exists()) {
                asrStatus = "⏳ Carregando modelo Vosk..."
                model = Model(modelPath.toString())
                asrStatus = "✅ ASR Vosk pronto (Modelo PT-BR carregado)"
            } else {
                asrStatus = "❌ Diretório do modelo não encontrado"
            }
        } catch (e: Exception) {
            asrStatus = "❌ Erro: ${e.message}\n${e.stackTraceToString().take(200)}"
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            speechService?.stop()
            speechService?.shutdown()
            model?.close()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Teste ASR (Vosk)") },
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
                    containerColor = when {
                        isListening -> MaterialTheme.colorScheme.tertiaryContainer
                        asrStatus.startsWith("✅") -> MaterialTheme.colorScheme.primaryContainer
                        else -> MaterialTheme.colorScheme.errorContainer
                    }
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (isListening) "🎤 Escutando..." else "Status do ASR",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = asrStatus,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            // Permissão de Microfone
            if (!micPermissionState.status.isGranted) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "⚠️ Permissão de Microfone Necessária",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (micPermissionState.status.shouldShowRationale) {
                                "O reconhecimento de voz precisa de acesso ao microfone"
                            } else {
                                "Clique no botão para solicitar permissão"
                            },
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { micPermissionState.launchPermissionRequest() }) {
                            Text("Solicitar Permissão")
                        }
                    }
                }
            }

            // Texto Reconhecido
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Texto Reconhecido:",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = recognizedText.ifEmpty { "(Aguardando reconhecimento...)" },
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.heightIn(min = 60.dp)
                    )
                    if (partialText.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Parcial: $partialText",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }

            // Controles
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        if (model != null && micPermissionState.status.isGranted) {
                            try {
                                val rec = Recognizer(model, 16000.0f)
                                speechService = SpeechService(rec, 16000.0f)
                                speechService?.startListening(object : RecognitionListener {
                                    override fun onPartialResult(hypothesis: String?) {
                                        hypothesis?.let {
                                            try {
                                                val json = JSONObject(it)
                                                partialText = json.optString("partial", "")
                                            } catch (e: Exception) {
                                                partialText = it
                                            }
                                        }
                                    }

                                    override fun onResult(hypothesis: String?) {
                                        hypothesis?.let {
                                            try {
                                                val json = JSONObject(it)
                                                val text = json.optString("text", "")
                                                if (text.isNotEmpty()) {
                                                    recognizedText = text
                                                    partialText = ""
                                                }
                                            } catch (e: Exception) {
                                                recognizedText = it
                                            }
                                        }
                                    }

                                    override fun onFinalResult(hypothesis: String?) {
                                        hypothesis?.let {
                                            try {
                                                val json = JSONObject(it)
                                                val text = json.optString("text", "")
                                                if (text.isNotEmpty()) {
                                                    recognizedText = text
                                                }
                                            } catch (e: Exception) {
                                                recognizedText = it
                                            }
                                        }
                                        isListening = false
                                        asrStatus = "✅ Reconhecimento finalizado"
                                    }

                                    override fun onError(exception: Exception?) {
                                        asrStatus = "❌ Erro: ${exception?.message}"
                                        isListening = false
                                    }

                                    override fun onTimeout() {
                                        asrStatus = "⏱️ Timeout - Sem áudio detectado"
                                        isListening = false
                                    }
                                })
                                isListening = true
                                asrStatus = "🎤 Escutando... Fale agora!"
                            } catch (e: Exception) {
                                asrStatus = "❌ Erro ao iniciar: ${e.message}"
                                isListening = false
                            }
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = !isListening && model != null && micPermissionState.status.isGranted
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Iniciar")
                }

                Button(
                    onClick = {
                        speechService?.stop()
                        isListening = false
                        asrStatus = "✅ Reconhecimento parado"
                    },
                    modifier = Modifier.weight(1f),
                    enabled = isListening,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(Icons.Default.Close, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Parar")
                }
            }

            Button(
                onClick = {
                    recognizedText = ""
                    partialText = ""
                    asrStatus = "✅ ASR Vosk pronto (Modelo PT-BR carregado)"
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Limpar Texto")
            }

            HorizontalDivider()

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
                    Text("• Engine: Vosk ASR 0.3.70", style = MaterialTheme.typography.bodySmall)
                    Text("• Modelo: vosk-model-small-pt-0.3", style = MaterialTheme.typography.bodySmall)
                    Text("• Idioma: Português Brasil", style = MaterialTheme.typography.bodySmall)
                    Text("• Modo: Offline (sem internet)", style = MaterialTheme.typography.bodySmall)
                    Text("• Sample Rate: 16000 Hz", style = MaterialTheme.typography.bodySmall)
                }
            }

            // Dicas de Uso
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "💡 Dicas de Uso",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• Fale de forma clara e pausada", style = MaterialTheme.typography.bodySmall)
                    Text("• Evite ruídos de fundo", style = MaterialTheme.typography.bodySmall)
                    Text("• Segure o dispositivo próximo à boca", style = MaterialTheme.typography.bodySmall)
                    Text("• O modelo é otimizado para português", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
