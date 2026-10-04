package com.example.opencity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.activity.enableEdgeToEdge
import com.example.opencity.ui.components.Conversation
import com.example.opencity.ui.components.SampleData
import com.example.opencity.ui.theme.OpenCityTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OpenCityTheme {
                Column (modifier = Modifier.fillMaxSize()) {
                    //*
                    // Fill the space of the notification bar.
                    Surface(modifier = Modifier.fillMaxWidth().windowInsetsTopHeight(WindowInsets.statusBars)) {}
                    // */
                    Surface(modifier = Modifier.fillMaxWidth().weight(1f)) {
                        Conversation(SampleData.conversationSample)
                    }
                    //*
                    // Fill the space of the navigation buttons.
                    Surface(modifier = Modifier.fillMaxWidth().windowInsetsBottomHeight(WindowInsets.navigationBars)) {}
                    // */
                }
            }
        }
    }
}
