package com.misgastitos.app.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.material3.ListItem
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import com.misgastitos.app.ui.home.HomeViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import com.misgastitos.app.ui.components.AppLogo

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreen(
    onAdd: () -> Unit,
    onLogout: () -> Unit,
    onManageCategories: () -> Unit, // ← Agregar este parámetro
    vm: HomeViewModel = hiltViewModel()
) {
    val items by vm.items.collectAsState()
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("MisGastitos")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { showMenu = true },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Más opciones"
                        )
                    }

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Categorías") },
                            onClick = {
                                showMenu = false
                                onManageCategories()
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Gestionar categorías"
                                )
                            }
                        )

                        DropdownMenuItem(
                            text = { Text("Cerrar sesión") },
                            onClick = {
                                showMenu = false
                                onLogout()
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.ExitToApp,
                                    contentDescription = "Cerrar sesión"
                                )
                            }
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAdd,
                shape = CircleShape
            ) {
                Text("+")
            }
        }
    ) { p ->
        if (items.isEmpty()) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(p),
                contentAlignment = Alignment.Center
            ) { Text("Sin gastos aún") }
        } else {
            LazyColumn(Modifier.padding(p)) {
                items(items) { e ->
                    ListItem(
                        headlineContent = { Text("$${"%.2f".format(e.amount)}") },
                        supportingContent = { Text(e.note ?: "") }
                    )
                    Divider()
                }
            }
        }
    }
}