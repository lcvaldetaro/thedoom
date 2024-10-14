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
        packageFolder = gcGetAppFolder(this)
        versionString = gcGetAppVersion(this)
        versionBuild  = gcGetAppBuild(this)

        DoomTools.init(packageFolder)
    }
}

fun gcGetAppBuild(ctx: Context) : Long {
    return if (Build.VERSION.SDK_INT >= 28) {
        ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).longVersionCode
    } else {
        ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionCode.toLong()
    }
}

fun gcGetAppVersion(ctx: Context) : String {
    return ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionName.toString()
}

fun makeFilesDirectory(packageFolder: String) : String {
    val packageFiles = "${packageFolder}/files"
    val packageFile = File(packageFiles)

    packageFile.mkdir()

    return packageFiles
}

fun gcGetAppFolder(ctx: Context) : String {
    val packageFolder = ctx.getExternalFilesDir(null)!!.absolutePath + "/"

    makeFilesDirectory(packageFolder)

    return packageFolder
}
