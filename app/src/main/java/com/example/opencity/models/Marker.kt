package com.example.opencity.models

import android.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector

data class Marker(
    val id: Int,
    val gps: GPS,
    val name: String,
    val color: Color,
    val icon: ImageVector = Icons.Default.LocationOn,
    val owner: Int? = null, // only used when the owner is not the user itself
)
