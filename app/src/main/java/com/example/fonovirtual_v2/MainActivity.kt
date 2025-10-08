package com.example.fonovirtual_v2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fonovirtual_v2.ui.debug.DebugScreen
import com.example.fonovirtual_v2.ui.home.HomeScreen
import com.example.fonovirtual_v2.ui.splash.SplashScreen
import com.example.fonovirtual_v2.ui.theme.PequenosPassosTheme
import com.example.fonovirtual_v2.ui.tts_test.TtsTestScreen
import com.example.fonovirtual_v2.ui.asr_test.AsrTestScreen
import com.example.fonovirtual_v2.ui.exercises.recognition.SimpleRecognitionExerciseScreen
import com.example.fonovirtual_v2.ui.exercises.result.ExerciseResultScreen

/**
 * Activity principal do app PequenosPassos.
 *
 * Gerencia a navegação entre todas as telas do app usando Jetpack Compose Navigation.
 *
 * @since 0.2.0 (Estrutura inicial de navegação)
 * @updated 1.0.3 - Adicionadas rotas para exercícios de reconhecimento
 * @validationStatus Em Desenvolvimento
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PequenosPassosTheme {
                PequenosPassosApp()
            }
        }
    }
}

/**
 * Composable raiz da aplicação PequenosPassos.
 *
 * Define o NavHost e as rotas de navegação para todas as telas.
 *
 * @since 0.2.0 (Estrutura inicial de navegação)
 * @updated 1.0.3 - Adicionadas rotas "simple_recognition" e "exercise_result"
 * @validationStatus Em Desenvolvimento
 */
@Composable
fun PequenosPassosApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        // Rotas básicas do app
        composable("splash") { SplashScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("debug") { DebugScreen(navController) }

        // Rotas de teste/debug
        composable("tts_test_screen") { TtsTestScreen() }
        composable("asr_test_screen") { AsrTestScreen(navController) }

        // Novas rotas de exercícios (v1.0.3)
        composable("simple_recognition") { SimpleRecognitionExerciseScreen(navController) }
        composable("exercise_result/{accuracy}/{correct}/{total}") { backStackEntry ->
            val accuracy = backStackEntry.arguments?.getString("accuracy")?.toIntOrNull() ?: 0
            val correct = backStackEntry.arguments?.getString("correct")?.toIntOrNull() ?: 0
            val total = backStackEntry.arguments?.getString("total")?.toIntOrNull() ?: 0
            ExerciseResultScreen(navController, accuracy, correct, total)
        }
    }
}
