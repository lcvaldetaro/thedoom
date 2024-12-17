package com.peterpan.doom

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import club.gepetto.circum.CircumActivity

open class InstallActivity : CircumActivity<Any>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setCircumModel(ViewModelProvider(this).get(DoomCircumModel::class.java))
    }

    override fun onStateUpdate(state: Any) {
        super.onStateUpdate(state)
        
        when (state) {
            is DoomState.Loading -> {
                setContent { GameLoadingState() }
            }
            is DoomState.Loaded -> {
                App.gameInstalled = true
                startDoomClientActivity()
            }
        }
    }

    fun startDoomClientActivity () {
        val intent = Intent(this, DoomClientActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        this.startActivity(intent)
    }
}

@Composable
fun GameLoadingState (
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            bitmap = BitmapFactory.decodeResource(
                LocalContext.current.applicationContext.resources,
                R.drawable.peterpan
            ).asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Text(text = "Loading game...")
        }
    }
}

@Preview
@Composable
private fun Preview() {
    Surface {
        GameLoadingState()
    }
}
