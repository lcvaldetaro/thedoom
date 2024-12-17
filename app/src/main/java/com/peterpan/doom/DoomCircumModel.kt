package com.peterpan.doom

import android.os.Looper
import club.gepetto.circum.CircumModel
import com.peterpan.doom.App.Companion.appContext
import com.peterpan.util.DoomTools
import com.peterpan.util.DoomTools.sleep
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DoomCircumModel : CircumModel<DoomState, DoomIntentCommand, DoomEffect>() {
    override fun onAttach(state: DoomState?, newState: DoomState?) {
        if (state == null) {
            initGame()
            setState(DoomState.Loading)
        }
        else
            setState(DoomState.Loaded)
    }

    fun initGame() {
        CoroutineScope(Dispatchers.IO).launch {
            Looper.prepare()
            DoomTools.createFolders()
            DoomTools.copyGameFiles(appContext)
            DoomTools.copySavedGames(appContext)
            DoomTools.copySoundTrack(appContext)
            sleep(8000)
            setState(DoomState.Loaded)
        }
    }
}

sealed interface DoomState {
   object Loading : DoomState
   object Loaded : DoomState
}

object DoomIntentCommand
object DoomEffect