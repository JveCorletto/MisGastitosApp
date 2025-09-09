package com.misgastitos.app.ui.screens

import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import com.misgastitos.app.ui.auth.AuthViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(onLogin: () -> Unit, onRegister: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Bienvenido a MisGastitos", style = MaterialTheme.typography.headlineSmall)

            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = onLogin,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Iniciar Sesión") }

            TextButton(onClick = onRegister) {
                Text("¿No tienes cuenta? Regístrate")
            }
        }
    }
}

@Composable
fun RegisterScreen(
    onRegistered: () -> Unit,
    onGoLogin: () -> Unit,
    vm: AuthViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    val st by vm.state.collectAsState()

    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Crear cuenta", style = MaterialTheme.typography.headlineSmall)
        OutlinedTextField(email, { email = it }, label = { Text("Email") })
        OutlinedTextField(pass, { pass = it }, label = { Text("Contraseña") }, singleLine = true)
        if (st.error != null) Text(st.error ?: "", color = MaterialTheme.colorScheme.error)
        Button(
            onClick = { vm.register(email, pass, onRegistered) },
            enabled = !st.loading,
            modifier = Modifier.fillMaxWidth()
        ) { Text(if (st.loading) "Creando..." else "Registrarme") }
        TextButton(onClick = onGoLogin) { Text("Ya tengo cuenta") }
    }
}