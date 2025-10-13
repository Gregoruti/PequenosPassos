package com.pequenospassos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pequenospassos.presentation.screens.AsrTestScreen
import com.pequenospassos.presentation.screens.DebugScreen
import com.pequenospassos.presentation.screens.HomeScreen
import com.pequenospassos.presentation.screens.SplashScreen
import com.pequenospassos.presentation.screens.TtsTestScreen
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity principal do PequenosPassos MVP.
 *
 * Configurada com Hilt e Jetpack Compose seguindo Clean Architecture.
 * Implementação do MVP-01: Estrutura Base do Projeto.
 *
 * @since MVP 1.3.0 (12/10/2025) - DIA 1 - Fundação
 * @author MVP Development Team
 * @validationStatus ✅ Implementado - MVP-01
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PequenosPassosTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    PequenosPassosApp()
                }
            }
        }
    }
}

/**
 * Composable raiz da aplicação PequenosPassos MVP.
 * Implementação temporária para validação do MVP-01.
 */
@Composable
fun PequenosPassosApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("debug") { DebugScreen(navController) }
        composable("tts_test") { TtsTestScreen(navController) }
        composable("asr_test") { AsrTestScreen(navController) }
    }
}

/**
 * Theme temporário para MVP-01.
 * Será implementado adequadamente no MVP-06.
 */
@Composable
fun PequenosPassosTheme(content: @Composable () -> Unit) {
    MaterialTheme(content = content)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PequenosPassosTheme {
        PequenosPassosApp()
    }
}
