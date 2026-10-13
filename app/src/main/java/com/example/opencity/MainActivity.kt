package com.example.opencity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.opencity.ui.theme.OpenCityTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            OpenCityTheme {
                ServerAddressGate {
                        serverAddress -> OpenCityApp(serverAddress = serverAddress)
                }
            }
        }
    }
}
