package com.pequenospassos.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.pequenospassos.R
import com.pequenospassos.domain.repository.AppSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Tela de Splash (abertura) do app PequenosPassos.
 *
 * Arquivo: presentation/screens/SplashScreen.kt
 * Objetivo: Exibir logo/versão por 5s e determinar o destino de navegação:
 *   - 1ª instalação → "permissions_onboarding" (explica permissões)
 *   - Demais aberturas → "home"
 * Correlações: PermissionsOnboardingScreen.kt, MainActivity.kt,
 *   AppSettingsRepository, CheckFirstRunUseCase
 *
 * Histórico de alterações:
 * - 2026-03-03 (GitHub Copilot / Claude Sonnet 4.5): v2.5.2
 *   - Adicionado SplashViewModel para verificar isFirstRun
 *   - Navega para "permissions_onboarding" na 1ª execução
 *   - Demais execuções continuam indo direto para "home"
 *
 * @since MVP-01
 * @updated v2.5.2 (03/03/2026) — roteamento para onboarding de permissões
 */

// ──────────────────────────────────────────────────────────────────────────────
// ViewModel
// ──────────────────────────────────────────────────────────────────────────────

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository
) : ViewModel() {

    private val _isFirstRun = MutableStateFlow<Boolean?>(null) // null = ainda carregando
    val isFirstRun: StateFlow<Boolean?> = _isFirstRun.asStateFlow()

    init {
        viewModelScope.launch {
            _isFirstRun.value = appSettingsRepository.isFirstRun()
        }
    }
}

// ──────────────────────────────────────────────────────────────────────────────
// Screen
// ──────────────────────────────────────────────────────────────────────────────

/**
 * Exibe o logo UNIVESP com informações do app por 5 segundos ou até o usuário tocar.
 * Após isso, navega conforme isFirstRun.
 */
@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val isFirstRun by viewModel.isFirstRun.collectAsState()
    var timerDone by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(5000)
        timerDone = true
    }

    // Navega assim que o timer terminar E soubermos se é firstRun
    LaunchedEffect(timerDone, isFirstRun) {
        if (timerDone && isFirstRun != null) {
            val destination = if (isFirstRun == true) "permissions_onboarding" else "home"
            navController.navigate(destination) {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .clickable { timerDone = true },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.univesp),
                contentDescription = "Logo UNIVESP",
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Aplicativo: Pequenos Passos",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "TCC540-Turma3-2025",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Versão 2.5.2",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Feedbacks:",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "2100394@aluno.univesp.br",
                fontSize = 11.sp,
                fontWeight = FontWeight.Normal,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "gregoruti@gmail.com",
                fontSize = 11.sp,
                fontWeight = FontWeight.Normal,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )
        }
    }
}


/**
 * Tela de Splash (abertura) do app PequenosPassos.
 * Exibe o logo UNIVESP com informações do app por 5 segundos ou até o usuário tocar.
 * Após isso, navega para a HomeScreen.
 * @param navController Controlador de navegação para transição de telas.
 */
@Composable
fun SplashScreen(navController: NavController) {
    var navigateNext by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(5000) // Aumentado de 3000 para 5000ms (5 segundos)
        navigateNext = true
    }
    if (navigateNext) {
        navController.navigate("home") {
            popUpTo("splash") { inclusive = true }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .clickable { navigateNext = true },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.univesp),
                contentDescription = "Logo UNIVESP",
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Aplicativo: Pequenos Passos",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "TCC540-Turma3-2025",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Versão 2.5.1", // Correções TTS + Transição Claude Opus 4
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Feedbacks:",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "2100394@aluno.univesp.br",
                fontSize = 11.sp,
                fontWeight = FontWeight.Normal,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "gregoruti@gmail.com",
                fontSize = 11.sp,
                fontWeight = FontWeight.Normal,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )
        }
    }
}
