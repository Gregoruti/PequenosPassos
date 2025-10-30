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
import androidx.navigation.NavController
import com.pequenospassos.BuildConfig
import com.pequenospassos.R
import kotlinx.coroutines.delay

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
                text = "Versão 2.1.0",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}
