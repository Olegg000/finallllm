package com.example.main3.presentation.screen.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.main3.common.AppLogger
import com.example.main3.presentation.common.LocalNavController
import com.example.ui.theme.TextB

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T>ScreenWrapper(
    viewModel: BaseViewModel<T>,
    screenName: String,
    content: @Composable (T, NavController, AppLogger) -> Unit
) {

    val state by viewModel.state.collectAsState()
    val error by viewModel.error.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val nav = LocalNavController.current
    val logger = remember { AppLogger(screenName) }

    DisposableEffect(Unit) {
        logger.i("Screen","Load")
        onDispose {

        logger.i("Screen","UnLoad")
        }
    }

    LaunchedEffect(Unit) {
        viewModel.init()
        viewModel.nav.collect { nav.navigate(it) }
    }

    if (error != null) {
        ModalBottomSheet(
            {
                viewModel.clearError()
            }
        ) {
            TextB(error.toString())
        }
    }

    if (isLoading) {
        Box(Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    content(state,nav,logger)

}