package com.misgastitos.app.ui.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.misgastitos.app.domain.repositories.Category
import com.misgastitos.app.ui.components.ColorPickerDialog
import com.misgastitos.app.ui.components.IconPickerDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryEditScreen(
    categoryId: Long? = null,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    vm: CategoryViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsState()
    var name by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("#FF6200EE") }
    var icon by remember { mutableStateOf("💰") }
    var showColorPicker by remember { mutableStateOf(false) }
    var showIconPicker by remember { mutableStateOf(false) }

    // Cargar datos cuando cambie el categoryId o se carguen las categorías
    LaunchedEffect(categoryId, state.categories) {
        if (categoryId != null && categoryId != 0L) {
            val category = state.categories.find { it.id == categoryId }
            category?.let {
                name = it.name
                color = it.color
                icon = it.icon
            }
        } else {
            // Resetear para nueva categoría
            name = ""
            color = "#FF6200EE"
            icon = "💰"
        }
    }

    // Dialogs
    if (showColorPicker) {
        ColorPickerDialog(
            currentColor = color,
            onColorSelected = { newColor ->
                color = newColor
            },
            onDismiss = { showColorPicker = false }
        )
    }

    if (showIconPicker) {
        IconPickerDialog(
            currentIcon = icon,
            onIconSelected = { newIcon ->
                icon = newIcon
            },
            onDismiss = { showIconPicker = false }
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(if (categoryId != null) "Editar Categoría" else "Nueva Categoría") },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre de la categoría") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Selector de Color Mejorado
            Text("Color:", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { showColorPicker = true }
                    .padding(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = Color(android.graphics.Color.parseColor(color)),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("✓", color = Color.White)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    "Seleccionar color",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Selector de Icono Mejorado
            Text("Icono:", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { showIconPicker = true }
                    .padding(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = icon,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    "Seleccionar icono",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        val category = Category(
                            id = categoryId ?: 0L,
                            name = name,
                            color = color,
                            icon = icon
                        )
                        vm.saveCategory(category, onSave)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank() && !state.loading
            ) {
                Text(if (state.loading) "Guardando..." else "Guardar")
            }
        }

        // Mostrar errores
        state.error?.let { error ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Snackbar {
                    Text(error)
                }
            }
        }
    }
}