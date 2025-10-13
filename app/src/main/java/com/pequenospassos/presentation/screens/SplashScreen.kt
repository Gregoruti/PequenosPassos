package com.pequenospassos.presentation.screens

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
import com.pequenospassos.R
import kotlinx.coroutines.delay

/**
 * Tela de Splash (abertura) do app PequenosPassos.
 * Exibe o logo UNIVESP por 3 segundos ou até o usuário tocar.
 * Após isso, navega para a HomeScreen.
 * @param navController Controlador de navegação para transição de telas.
 */
@Composable
fun SplashScreen(navController: NavController) {
    var navigateNext by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(3000)
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
        Image(
            painter = painterResource(id = R.drawable.univesp),
            contentDescription = "Logo UNIVESP",
            modifier = Modifier.size(200.dp)
        )
    }
}

