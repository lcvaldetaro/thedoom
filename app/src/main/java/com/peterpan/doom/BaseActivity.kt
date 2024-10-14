package com.peterpan.doom

import android.os.Bundle
import androidx.activity.enableEdgeToEdge

open class BaseActivity : androidx.appcompat.app.AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
    }
}