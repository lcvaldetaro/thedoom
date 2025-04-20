package com.peterpan.doom

import android.os.Looper
import club.gepetto.circum.CircumIntentProcessor
import com.peterpan.doom.App.Companion.appContext
import com.peterpan.util.DoomTools
import com.peterpan.util.DoomTools.sleep
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DoomIntentProcessor : CircumIntentProcessor<DoomState, DoomIntentCommand, DoomEffect>() {

    init {
        setState(DoomState.Loading)
        installGameFiles()
    }

    private fun installGameFiles() {
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
   data object Loading : DoomState
   data object Loaded : DoomState
}

data object DoomIntentCommand
data object DoomEffect