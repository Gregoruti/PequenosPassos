package com.pequenospassos.presentation.screens

import android.Manifest
import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.pequenospassos.domain.repository.AppSettingsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Tela de Onboarding de Permissões — exibida apenas na primeira instalação.
 *
 * Arquivo: presentation/screens/PermissionsOnboardingScreen.kt
 * Tipo: Screen (Composable) + ViewModel
 * Objetivo: Explicar ao usuário (responsável pela criança) por que o app
 *   precisa de acesso ao microfone e à câmera/galeria, antes de solicitar.
 * Correlações: SplashScreen.kt, MainActivity.kt, AppSettingsRepository,
 *   CheckFirstRunUseCase, CompleteOnboardingUseCase
 *
 * Histórico de alterações:
 * - 2026-03-03 (GitHub Copilot / Claude Sonnet 4.5): Criação inicial
 *   - Slide 1: Microfone — comandos de voz nas atividades
 *   - Slide 2: Câmera/Galeria — personalização de imagens
 *   - Slide 3: Resumo e confirmação
 *   - Solicita permissões reais via Accompanist
 *   - Marca isFirstRun = false ao concluir
 *
 * Fluxo:
 *   SplashScreen detecta isFirstRun == true → navega para "permissions_onboarding"
 *   Após conclusão → navega para "home" (removendo backstack)
 *
 * @since v2.5.2 (2026-03-03)
 * @validationStatus 🔄 Pendente compilação
 */

// ──────────────────────────────────────────────────────────────────────────────
// ViewModel
// ──────────────────────────────────────────────────────────────────────────────

@HiltViewModel
class PermissionsOnboardingViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository
) : ViewModel() {

    /** Marca o onboarding de permissões como concluído (isFirstRun = false). */
    fun completeOnboarding() {
        viewModelScope.launch {
            appSettingsRepository.markFirstRunCompleted()
        }
    }
}

// ──────────────────────────────────────────────────────────────────────────────
// Modelo de slide
// ──────────────────────────────────────────────────────────────────────────────

private data class PermissionSlide(
    val emoji: String,
    val title: String,
    val description: String,
    val detail: String,
    val backgroundColor: Color,
    val accentColor: Color
)

private val slides = listOf(
    PermissionSlide(
        emoji = "🎤",
        title = "Comandos de Voz",
        description = "O app usa o microfone para que a criança possa responder às perguntas falando!",
        detail = "Quando o tempo de um passo terminar, o app pergunta se podemos avançar. " +
                "A criança pode responder falando \"sim\", \"pode\" ou \"vamos\" — " +
                "sem precisar tocar na tela.",
        backgroundColor = Color(0xFFE3F2FD),
        accentColor = Color(0xFF1565C0)
    ),
    PermissionSlide(
        emoji = "📷",
        title = "Fotos e Imagens",
        description = "O app usa a câmera e a galeria para personalizar as atividades com fotos reais!",
        detail = "Você pode adicionar fotos do rosto da criança no cadastro e imagens " +
                "de objetos reais em cada passo das atividades. " +
                "Isso torna o aprendizado muito mais familiar e eficaz.",
        backgroundColor = Color(0xFFF3E5F5),
        accentColor = Color(0xFF6A1B9A)
    ),
    PermissionSlide(
        emoji = "✅",
        title = "Tudo pronto!",
        description = "Vamos agora solicitar essas permissões para o app funcionar completamente.",
        detail = "Você pode gerenciar as permissões a qualquer momento nas Configurações " +
                "do Android. O app funciona sem elas, mas com funcionalidades reduzidas.",
        backgroundColor = Color(0xFFE8F5E9),
        accentColor = Color(0xFF2E7D32)
    )
)

// ──────────────────────────────────────────────────────────────────────────────
// Screen
// ──────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionsOnboardingScreen(
    navController: NavController,
    viewModel: PermissionsOnboardingViewModel = hiltViewModel()
) {
    var currentSlide by remember { mutableIntStateOf(0) }

    // Permissões a solicitar
    val micPermission = rememberPermissionState(Manifest.permission.RECORD_AUDIO)

    val imagePermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberMultiplePermissionsState(
            listOf(Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.CAMERA)
        )
    } else {
        rememberMultiplePermissionsState(
            listOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
        )
    }

    fun finishOnboarding() {
        // Solicita permissões (o sistema Android mostra o diálogo nativo)
        micPermission.launchPermissionRequest()
        imagePermissions.launchMultiplePermissionRequest()
        // Marca onboarding como concluído
        viewModel.completeOnboarding()
        // Navega para home removendo todo o backstack
        navController.navigate("home") {
            popUpTo(0) { inclusive = true }
        }
    }

    val slide = slides[currentSlide]

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(slide.backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            // ── Indicadores de passo (bolinhas) ─────────────────────────────
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                slides.forEachIndexed { index, _ ->
                    Box(
                        modifier = Modifier
                            .size(if (index == currentSlide) 12.dp else 8.dp)
                            .clip(CircleShape)
                            .background(
                                if (index == currentSlide) slide.accentColor
                                else slide.accentColor.copy(alpha = 0.3f)
                            )
                    )
                }
            }

            // ── Conteúdo do slide ────────────────────────────────────────────
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInHorizontally(),
                exit = fadeOut() + slideOutHorizontally()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.weight(1f, fill = false)
                ) {
                    Spacer(Modifier.height(16.dp))

                    // Emoji grande
                    Text(
                        text = slide.emoji,
                        fontSize = 80.sp,
                        textAlign = TextAlign.Center
                    )

                    // Título
                    Text(
                        text = slide.title,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = slide.accentColor,
                        textAlign = TextAlign.Center
                    )

                    // Descrição principal
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.85f)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Text(
                                text = slide.description,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF212121),
                                textAlign = TextAlign.Center,
                                lineHeight = 26.sp
                            )
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = slide.detail,
                                fontSize = 14.sp,
                                color = Color(0xFF616161),
                                textAlign = TextAlign.Center,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }

            // ── Botão de ação ────────────────────────────────────────────────
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (currentSlide < slides.lastIndex) {
                    // Próximo slide
                    Button(
                        onClick = { currentSlide++ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = slide.accentColor
                        )
                    ) {
                        Text(
                            text = "Próximo",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.width(8.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                    }

                    // Pular (vai direto para home sem pedir permissões agora)
                    TextButton(onClick = {
                        viewModel.completeOnboarding()
                        navController.navigate("home") {
                            popUpTo(0) { inclusive = true }
                        }
                    }) {
                        Text(
                            text = "Pular por agora",
                            color = slide.accentColor.copy(alpha = 0.7f),
                            fontSize = 14.sp
                        )
                    }
                } else {
                    // Último slide — Permitir acesso
                    Button(
                        onClick = { finishOnboarding() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = slide.accentColor
                        )
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "Permitir acesso",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Continuar sem permissões
                    TextButton(onClick = {
                        viewModel.completeOnboarding()
                        navController.navigate("home") {
                            popUpTo(0) { inclusive = true }
                        }
                    }) {
                        Text(
                            text = "Continuar sem permissões",
                            color = slide.accentColor.copy(alpha = 0.7f),
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

