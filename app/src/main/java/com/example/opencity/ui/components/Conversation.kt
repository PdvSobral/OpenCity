package com.example.opencity.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.opencity.models.Message
import com.example.opencity.ui.theme.OpenCityTheme


/**
 * SampleData for Jetpack Compose Tutorial
 */
object SampleData {
    // Sample conversation data
    val conversationSample = listOf(
        Message("Lexi", "Test...Test...Test..."),
        Message("Lexi","List of Android versions:\n|Android KitKat (API 19)\n|Android Lollipop (API 21)\n|Android Marshmallow (API 23)\n|Android Nougat (API 24)\n|Android Oreo (API 26)\n|Android Pie (API 28)\n|Android 10 (API 29)\n|Android 11 (API 30)\n|Android 12 (API 31)".trim()),
        Message("Lexi","I think Kotlin is my favorite programming language.\n|It's so much fun!".trim()),
        Message("Lexi", "Searching for alternatives to XML layouts..."),
        Message("Lexi","Hey, take a look at Jetpack Compose, it's great!\n|It's the Android's modern toolkit for building native UI.\n|It simplifies and accelerates UI development on Android.\n|Less code, powerful tools, and intuitive Kotlin APIs :)".trim()),
        Message("Lexi", "It's available from API 21+ :)"),
        Message("Lexi", "Writing Kotlin for UI seems so natural, Compose where have you been all my life?"),
        Message("Lexi", "Android Studio next version's name is Arctic Fox"),
        Message("Lexi", "Android Studio Arctic Fox tooling for Compose is top notch ^_^"),
        Message("Lexi", "I didn't know you can now run the emulator directly from Android Studio"),
        Message("Lexi", "Compose Previews are great to check quickly how a composable layout looks like"),
        Message("Lexi", "Previews are also interactive after enabling the experimental setting"),
        Message("Lexi", "Have you tried writing build.gradle with KTS?"),
    )
}


@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message -> MessageCard(message) }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode",
    showBackground = true
)
@Composable
fun PreviewConversation() {
    OpenCityTheme {
        Conversation(SampleData.conversationSample)
    }
}
