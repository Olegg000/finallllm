package com.example.main3

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.main3.presentation.common.LocalNavController
import com.example.main3.presentation.screen.chat.ChatScreen
import com.example.main3.presentation.screen.reg.RegScreen
import com.example.net.common.ContextHolder
import com.example.ui.theme.TextB

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ContextHolder.init(this)
        enableEdgeToEdge()
        setContent {
            val nav = rememberNavController()


            CompositionLocalProvider(
                LocalNavController provides nav
            ) {
                NavHost(
                    nav,
                    "reg"
                ) {

                    composable("reg") {
                        RegScreen()
                    }

                    composable("login") {
                        TextB("login")
                        ChatScreen()
                    }
                }
            }

        }
    }
}
