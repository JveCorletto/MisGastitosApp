package com.misgastitos.app.ui.screens

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
import com.misgastitos.app.ui.add.AddExpenseViewModel
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.CenterAlignedTopAppBar

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreen(onAdd: () -> Unit, vm: HomeViewModel = hiltViewModel()) {
    val items by vm.items.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("MisGastitos") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) { Text("+") }
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
                }
            }
        }
    }
}

@Composable
fun AddExpenseScreen(onSaved: () -> Unit, vm: AddExpenseViewModel = hiltViewModel()) {
    var amount by remember { mutableStateOf(vm.amountText) }
    var note by remember { mutableStateOf(vm.note ?: "") }
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedTextField(amount, {
            amount = it; vm.amountText = it
        }, label = { Text("Monto") })
        OutlinedTextField(note, {
            note = it; vm.note = it
        }, label = { Text("Nota") })
        Spacer(Modifier.weight(1f))
        Button( onClick = { vm.save(onSaved) }, modifier = Modifier.fillMaxWidth() ) { Text("Guardar") }
    }
}