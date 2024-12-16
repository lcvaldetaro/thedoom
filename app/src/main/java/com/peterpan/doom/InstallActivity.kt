package com.peterpan.doom

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
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
import androidx.lifecycle.lifecycleScope
import com.peterpan.doom.App.Companion.appContext
import com.peterpan.util.DoomTools
import kotlinx.coroutines.launch
import java.lang.Thread.sleep

open class InstallActivity : BaseActivity() {
    val TAG = "SplashActivity"
    lateinit var thisActivity: InstallActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        thisActivity = this
        setContent {
            GameLoadingState()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    fun startDoomClientActivity () {
        val intent = Intent(this, DoomClientActivity::class.java)
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
