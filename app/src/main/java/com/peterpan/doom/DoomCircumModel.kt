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
            // Initial instantiation
            loadGameFiles()
            setState(DoomState.Loading)
        }
        else {
            // reconfiguration
            setState(DoomState.Loaded)
        }
    }

    fun loadGameFiles() {
        CoroutineScope(Dispatchers.IO).launch {
            Looper.prepare() // needed to issue toasts
            DoomTools.createFolders()
            DoomTools.copyGameFiles(appContext)
            DoomTools.copySavedGames(appContext)
            DoomTools.copySoundTrack(appContext)
            sleep(8000) // wait for all the toasts to finish
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