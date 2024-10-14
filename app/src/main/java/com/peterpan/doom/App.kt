package com.peterpan.doom

import android.app.Application
import android.content.Context
import android.os.Build
import com.peterpan.util.DoomTools
import java.io.File

class App : Application()  {
    companion object {
        lateinit var appContext : Context
        lateinit var directoryFile : File
        lateinit var packageFolder: String
        var versionString = ""
        var versionBuild  = 0L
    }

    override fun onCreate() {
        super.onCreate()

        appContext = this
        directoryFile = if (Build.VERSION.SDK_INT >= 24) appContext.dataDir else File(filesDir.path)
        packageFolder = com.gepetto.utils.gcGetAppFolder(this)
        versionString = com.gepetto.utils.gcGetAppVersion(this)
        versionBuild  = com.gepetto.utils.gcGetAppBuild(this)

        DoomTools.init(packageFolder)
    }
}

