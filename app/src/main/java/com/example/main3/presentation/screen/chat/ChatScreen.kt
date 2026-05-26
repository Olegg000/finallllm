package com.example.main3.presentation.screen.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.main3.R
import com.example.ui.components.Button
import com.example.ui.components.ButtonDef
import com.example.ui.components.InputDef
import com.example.ui.theme.TextB


@Composable
fun ChatScreen(
    vm: ChatViewModel = viewModel()
) {

    var input by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        vm.init()
    }

    Scaffold(
        bottomBar = {
            InputDef(
                input,
                { input = it },
                trailingIcon = {
                    Icon(
                        painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            vm.send(input.ifEmpty { return@clickable })
                            input = ""
                        })
                }
            )
        }
    ) { inne ->
        LazyColumn(Modifier
            .padding(inne)
            .padding(20.dp)) {
            items(vm.messages) {
                Bubble(it)
            }
            if (vm.isLoading) {
                item {
                    Bubble(
                        Message(
                            "",
                            "Inference in progress...",
                            true
                        )
                    )
                }
            }
            vm.error?.let {
                item {
                    TextB(it, color = Color.Red)
                    ButtonDef("Retry", { vm.repeat() }, Button.Error)
                }
            }
        }
    }

}