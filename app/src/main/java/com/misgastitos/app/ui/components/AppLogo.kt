package com.misgastitos.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.misgastitos.app.R

@Composable
fun AppLogo(
    modifier: Modifier = Modifier,
    size: Int = 120
) {
    Image(
        painter = painterResource(id = R.drawable.ic_app_logo),
        contentDescription = "Logo MisGastitos",
        modifier = modifier.size(size.dp)
    )
}