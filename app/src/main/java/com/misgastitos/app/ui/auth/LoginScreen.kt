package com.misgastitos.app.ui.auth

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.misgastitos.app.ui.components.AppLogo

@Composable
fun LoginScreen(
    onLoggedIn: () -> Unit,
    onGoRegister: () -> Unit,
    vm: AuthViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val state by vm.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppLogo(
            size = 200,
            modifier = Modifier
                .rotate(animateFloatAsState(targetValue = 0f).value)
                .scale(animateFloatAsState(targetValue = 1f).value)
        )

        Spacer(modifier = Modifier.height(32.dp)) // ← Espacio después del logo

        Text(
            text = "Bienvenido a MisGastitos",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { vm.login(email, password, onLoggedIn) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.loading && email.isNotBlank() && password.isNotBlank()
        ) {
            Text(if (state.loading) "Iniciando..." else "Iniciar Sesión")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onGoRegister) {
            Text("¿No tienes cuenta? Regístrate")
        }

        // Mostrar errores
        state.error?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}