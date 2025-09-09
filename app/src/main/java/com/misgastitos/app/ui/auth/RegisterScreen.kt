package com.misgastitos.app.ui.auth

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.misgastitos.app.ui.components.AppLogo

@Composable
fun RegisterScreen(
    onRegistered: () -> Unit,
    onGoLogin: () -> Unit,
    vm: AuthViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
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

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Crear Cuenta",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
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

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (password == confirmPassword) {
                    vm.register(email, password, onRegistered)
                } else {
                    // Manejar error de contraseñas no coincidentes
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.loading && email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank()
        ) {
            Text(if (state.loading) "Creando cuenta..." else "Registrarse")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onGoLogin) {
            Text("¿Ya tienes cuenta? Inicia sesión")
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