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
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.pequenospassos.presentation.screens.AsrTestScreen
import com.pequenospassos.presentation.screens.DebugScreen
import com.pequenospassos.presentation.screens.HomeScreen
import com.pequenospassos.presentation.screens.SplashScreen
import com.pequenospassos.presentation.screens.TtsTestScreen
import com.pequenospassos.presentation.screens.childregistration.ChildRegistrationScreen
import com.pequenospassos.presentation.screens.taskform.TaskFormScreen
import com.pequenospassos.presentation.screens.tasklist.TaskListScreen
import com.pequenospassos.presentation.screens.taskmanagement.TaskManagementScreen
import com.pequenospassos.presentation.screens.execution.TaskExecutionScreen
import com.pequenospassos.presentation.screens.completion.TaskCompletionScreen
import dagger.hilt.android.AndroidEntryPoint
import com.pequenospassos.presentation.theme.PequenosPassosTheme

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
        
        // Rota para criar nova tarefa
        composable("task_form") { 
            TaskFormScreen(navController = navController, taskId = null) 
        }
        
        // Rota para editar tarefa existente
        composable(
            route = "task_form/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.LongType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getLong("taskId")
            TaskFormScreen(navController = navController, taskId = taskId)
        }

        // Rota para executar tarefa (MVP-07)
        composable(
            route = "task_execution/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.LongType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getLong("taskId") ?: 0L
            TaskExecutionScreen(navController = navController, taskId = taskId)
        }

        // Rota para tela de conclusão (MVP-07 v1.9.3)
        composable(
            route = "task_completion/{taskTitle}/{stars}",
            arguments = listOf(
                navArgument("taskTitle") { type = NavType.StringType },
                navArgument("stars") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val taskTitle = backStackEntry.arguments?.getString("taskTitle") ?: ""
            val stars = backStackEntry.arguments?.getInt("stars") ?: 5
            TaskCompletionScreen(
                navController = navController,
                taskTitle = taskTitle,
                stars = stars
            )
        }

        // Rota para lista de tarefas (Atividades) - Área de Execução
        composable("task_list") { TaskListScreen(navController) }

        // Rota para gerenciamento de tarefas (Edição de Tarefas) - Área de Configuração
        composable("task_management") { TaskManagementScreen(navController) }


        // Rota para cadastro da criança
        composable("child_registration") { ChildRegistrationScreen(navController) }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PequenosPassosTheme {
        PequenosPassosApp()
    }
}
