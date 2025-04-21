package com.peterpan.doom

import android.content.Intent
import androidx.activity.compose.setContent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import club.gepetto.circum.circumIntentProcessor
import com.peterpan.doom.ui.GameLoadingState

open class InitialActivity : ComponentActivity() {
    private lateinit var dIp : DoomIntentProcessor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
    }

    override fun onResume() {
        super.onResume()

        dIp = circumIntentProcessor<DoomIntentProcessor>(this)

        dIp.onStateUpdated{ state ->
            when (state) {
                is DoomState.Loading -> {
                    setContent { GameLoadingState() }
                }
                is DoomState.Loaded -> {
                    App.gameInstalled = true
                    val intent = Intent(this, DoomClientActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    this.startActivity(intent)
                }
            }
        }
    }
}

