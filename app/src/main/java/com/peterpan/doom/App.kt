package com.peterpan.doom

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Looper
import com.peterpan.util.DoomTools
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class App : Application()  {
    companion object {
        lateinit var appContext : Context
        lateinit var directoryFile : File
        lateinit var packageFolder: String
        var versionString = ""
        var versionBuild  = 0L
        var gameInstalled = false
    }

    override fun onCreate() {
        super.onCreate()

        appContext = this
        directoryFile = appContext.dataDir
        packageFolder = getAppFolder(this)
        versionString = getAppVersion(this)
        versionBuild  = getAppBuild(this)

        DoomTools.init(packageFolder)

        CoroutineScope(Dispatchers.IO).launch {
            Looper.prepare()
            DoomTools.createFolders()
            DoomTools.copyGameFiles(appContext)
            DoomTools.copySavedGames(appContext)
            DoomTools.copySoundTrack(appContext)
            gameInstalled = true
        }
    }
}

fun getAppBuild(ctx: Context) : Long {
    return if (Build.VERSION.SDK_INT >= 28) {
        ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).longVersionCode
    } else {
        ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionCode.toLong()
    }
}

fun getAppVersion(ctx: Context) : String {
    return ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionName.toString()
}

fun makeFilesDirectory(packageFolder: String) : String {
    val packageFiles = "${packageFolder}/files"
    val packageFile = File(packageFiles)

    packageFile.mkdir()

    return packageFiles
}

fun getAppFolder(ctx: Context) : String {
    val packageFolder = ctx.getExternalFilesDir(null)!!.absolutePath + "/"

    makeFilesDirectory(packageFolder)

    return packageFolder
}
