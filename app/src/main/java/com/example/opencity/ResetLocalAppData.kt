package com.example.opencity

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity

// FIXME: Currently this activity is exported! Later, it is to be available only in specific dev versions
class ResetLocalAppData : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val manager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val accepted = manager.clearApplicationUserData()
        // Toast currently not working. probably because no visual interface is requested.
        // TODO: Maybe change to notifications?
        Toast.makeText(this,if (accepted) {"Local data reset requested"} else {"Could not reset local data"}, Toast.LENGTH_LONG).show()
        if (!accepted) finish()
    }
}
