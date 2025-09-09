package com.misgastitos.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.graphics.toColorInt

@Composable
fun ColorPickerDialog(
    currentColor: String,
    onColorSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val colors by remember {
        derivedStateOf {
            listOf(
                "#FF4A90E2", "#FF50E3C2", "#FFF5A623", "#FFFF6F61", "#FF9B51E0",
                "#FF27AE60", "#FFFFB6B9", "#FF7F8C8D", "#FFF2994A", "#FF56CCF2"
            )
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.width(300.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    "Seleccionar Color",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    modifier = Modifier.height(200.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(colors) { colorHex ->
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    color = Color(colorHex.toColorInt()),
                                    shape = CircleShape
                                )
                                .clickable {
                                    onColorSelected(colorHex)
                                    onDismiss()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (currentColor == colorHex) {
                                Text("✓", color = Color.White)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Cancelar")
                }
            }
        }
    }
}