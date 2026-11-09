package com.example.opencity.ui.components.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MarkerComponent(
    color: Color,
    description: String,
    icon: ImageVector,
    x: Dp,
    y: Dp,
    onClick: () -> Unit = {}
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .padding(start = x, top = y)
            .size(42.dp)
            .clip(CircleShape)
            .background(color)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            tint = Color.White
        )
    }
}