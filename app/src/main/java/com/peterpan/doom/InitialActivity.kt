package com.peterpan.doom

import android.content.Intent
import androidx.activity.compose.setContent
import android.os.Bundle
import club.gepetto.circum.CircumActivity
import com.peterpan.doom.ui.GameLoadingState

open class InitialActivity : CircumActivity<Any>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setIntentProcessor(DoomIntentProcessor::class.java)
    }

    override fun onStateUpdate(state: Any) {
        super.onStateUpdate(state)
        
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

