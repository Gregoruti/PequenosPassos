package com.example.fonovirtual_v2.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pequenospassos.R
import kotlinx.coroutines.delay

/**
 * Tela de Splash (abertura) do app.
 *
 * Exibe a imagem "univesp.jpg" em fundo branco por 3 segundos ou até o usuário tocar.
 * Após isso, navega para a HomeScreen.
 *
 * @param navController Controlador de navegação para transição de telas.
 * @validationStatus Em Teste
 */
@Composable
fun SplashScreen(navController: NavController) {
    var navigateNext by remember { mutableStateOf(false) }

    // Timer de 3 segundos para navegação automática
    LaunchedEffect(Unit) {
        delay(3000)
        navigateNext = true
    }

    // Se o usuário tocar, navega imediatamente
    if (navigateNext) {
        navController.navigate("home") {
            popUpTo("splash") { inclusive = true }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .clickable { navigateNext = true },
            contentAlignment = Alignment.Center
        ) {
            // Tenta carregar a imagem, se não existir exibe placeholder visual e texto informativo
            val imageId = try { R.drawable.univesp } catch (e: Exception) { null }
            if (imageId != null) {
                Image(
                    painter = painterResource(id = imageId),
                    contentDescription = "Logo UNIVESP",
                    modifier = Modifier.size(200.dp)
                )
            } else {
                // Exibe ícone padrão do Android como placeholder visual
                Image(
                    painter = painterResource(id = android.R.drawable.ic_dialog_alert),
                    contentDescription = "Placeholder",
                    modifier = Modifier.size(100.dp)
                )
                Text(
                    text = "Imagem de abertura não encontrada.",
                    color = Color.Black
                )
            }
        }
    }
}
