package com.example.opencity.ui.components.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.opencity.R

@Composable
fun CompassIcon(orientationDegrees: Float) {
    FloatingActionButton(
        onClick = {},
        modifier = Modifier.size(48.dp),
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.compass_needle_no_interior),
            contentDescription = "Compass",
            modifier = Modifier.size(40.dp).graphicsLayer { rotationZ = -orientationDegrees }
        )
    }
}
