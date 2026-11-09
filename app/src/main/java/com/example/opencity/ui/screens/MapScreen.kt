package com.example.opencity.ui.screens

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Report
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.example.opencity.R
import com.example.opencity.models.Report
import com.example.opencity.models.ReportStatus
import com.example.opencity.models.enums.MapOrientationMode
import com.example.opencity.ui.components.TopBar
import com.example.opencity.ui.components.map.CompassIcon
import com.example.opencity.ui.components.map.MapContainer


private data class MarkerInfo(
    val report_id: Int
)
@Composable
fun MapScreen(
    onCreateReport: () -> Unit,
    mapMode: MapOrientationMode
) {
    var selectedMarker by remember { mutableStateOf<MarkerInfo?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(all = 0.dp)) {
        // UI for when a marker is "clicked"
        selectedMarker?.let { report_id ->
            // TODO: pull from repo/modelview
            val report: Report = Report(
                id = 67,
                title = "Report Title",
                category = "Categoria",
                description = "Report description",
                address = "GPS coords",
                status = ReportStatus.RESOLVED,
                date = "Data"
            )
            AlertDialog(
                onDismissRequest = { selectedMarker = null },
                title = { Text(text = report.title) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(text = "Localização: ${report.address}")
                        Text(text = report.description)
                    }
                },
                confirmButton = {
                    TextButton( onClick = { selectedMarker = null }
                    ) { Text("Fechar") }
                }
            )
        }

        Row(modifier = Modifier.fillMaxWidth().padding(all = 0.dp)) {
            TopBar("OpenCity", stringResource(R.string.map_screen_topbar_description))
        }
        Box(
            modifier = Modifier.weight(1f).fillMaxWidth()
        ) {
            MapContainer()
            /* FIXME: //
             onClick = { selectedMarker = MarkerInfo(1) }
            */
            Column(
                modifier = Modifier.align(Alignment.TopEnd).padding(top = 4.dp, bottom = 0.dp, start = 0.dp, end = 10.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) { TopRightColumnContent(onCreateReport, getCompassOrientation(mapMode)) }
            Column(
                modifier = Modifier.align(Alignment.BottomEnd).padding(top = 0.dp, bottom = 10.dp, start = 0.dp, end = 10.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) { BottomRightColumnContent() }
        }
    }
}

@Composable
private fun getCompassOrientation(mode: MapOrientationMode): Float {
    var azimuthDegrees by remember { mutableFloatStateOf(0f) }

    if (mode == MapOrientationMode.COMPASS) {
        // TODO: Later, it will be tied to the map. for now, it itself has the de sensor orientation.
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current
        val sensorManager = remember(context) {
            context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        }
        val rotationSensor = remember(sensorManager) {
            sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        }
        DisposableEffect(lifecycleOwner, sensorManager, rotationSensor) {
            val listener = object : SensorEventListener {
                private val rotationMatrix = FloatArray(9)
                private val orientation = FloatArray(3)

                override fun onSensorChanged(event: SensorEvent) {
                    SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
                    SensorManager.getOrientation(rotationMatrix, orientation)
                    azimuthDegrees = Math.toDegrees(orientation[0].toDouble()).toFloat().let { (it + 360f) % 360f }
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
            }

            fun startListening() {
                rotationSensor?.let {
                    sensorManager.registerListener(
                        listener,
                        it,
                        SensorManager.SENSOR_DELAY_UI
                    )
                }
            }

            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_START -> startListening()
                    Lifecycle.Event.ON_STOP -> sensorManager.unregisterListener(listener)
                    else -> Unit
                }
            }

            lifecycleOwner.lifecycle.addObserver(observer)
            if (lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                startListening()
            }

            onDispose {
                sensorManager.unregisterListener(listener)
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    } else if (mode == MapOrientationMode.NORTH_UP) { azimuthDegrees = 0f }
    else if (mode == MapOrientationMode.MANUAL_ROTATION) { azimuthDegrees = 90f }
    return azimuthDegrees
}


@Composable
private fun TopRightColumnContent(
    onReportButtonClick: () -> Unit,
    compassOrientation: Float
) {
    OutlinedButton(
        modifier = Modifier.size(height = 40.dp, width = 135.dp), //TODO: a ver tamanho, possivelmente dinâmico
        onClick = onReportButtonClick
    ) {
        Icon(
            imageVector = Icons.Default.Report,
            contentDescription = stringResource(id=R.string.map_screen_create_report_button_icon)
        )
        Text(
            text = stringResource(id=R.string.map_screen_create_report_button_text),
            modifier = Modifier.padding(all = 0.dp)
        )
    }
    CompassIcon(compassOrientation)
}

@Composable
private fun BottomRightColumnContent() {
    FloatingActionButton(
        onClick = {},
        modifier = Modifier.size(48.dp),
        elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Zoom in"
        )
    }
    FloatingActionButton(
        onClick = {},
        modifier = Modifier.size(48.dp),
        elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Remove,
            contentDescription = "Zoom out"
        )
    }
}
