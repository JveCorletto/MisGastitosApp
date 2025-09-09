package com.misgastitos.app.ui.category

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import com.misgastitos.app.domain.repositories.Category
import androidx.core.graphics.toColorInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListScreen(
    onAddCategory: () -> Unit,
    onEditCategory: (Long) -> Unit,
    vm: CategoryViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsState()

    LaunchedEffect(Unit) {
        vm.loadCategories()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Categorías de Gastos") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddCategory,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar categoría")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (state.loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.categories.isEmpty()) {
                Text(
                    "No hay categorías. ¡Agrega una!",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(state.categories) { category ->
                        CategoryItem(
                            category = category,
                            onEdit = { onEditCategory(category.id) },
                            onDelete = { vm.deleteCategory(category.id) }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            // Mostrar errores
            state.error?.let { error ->
                Snackbar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                ) {
                    Text(error)
                }
            }
        }
    }
}

@Composable
fun CategoryItem(
    category: Category,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    vm: CategoryViewModel = hiltViewModel()
) {
    var categoryToDelete by remember { mutableStateOf<Category?>(null) }
    if (categoryToDelete != null) {
        AlertDialog(
            onDismissRequest = { categoryToDelete = null },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás seguro de que quieres eliminar '${categoryToDelete?.name}'?") },
            confirmButton = {
                Button(
                    onClick = {
                        vm.deleteCategory(categoryToDelete!!.id)
                        categoryToDelete = null
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { categoryToDelete = null }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Icono y color de la categoría
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = Color(category.color.toColorInt()),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = category.icon,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            // Botones de acción
            Row {
                IconButton(onClick = onEdit) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar categoría"
                    )
                }
                IconButton(onClick = { categoryToDelete = category }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar categoría",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}