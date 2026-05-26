package com.example.main3.presentation.screen.reg

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.main3.presentation.screen.base.ScreenWrapper
import com.example.ui.components.Button
import com.example.ui.components.ButtonDef
import com.example.ui.components.InputDef
import com.example.ui.components.PasswordInput
import com.example.ui.theme.GlobalFontFamily
import com.example.ui.theme.Placeholder
import com.example.ui.theme.Primary
import com.example.ui.theme.Spaces
import com.example.ui.theme.Spaces.s16
import com.example.ui.theme.Spaces.s8
import com.example.ui.theme.TextB

@Composable
fun RegScreen(
    viewModel: RegViewModel = viewModel()
) {

    val uri = LocalUriHandler.current

    ScreenWrapper(viewModel, "RegScreen") { state, controller, logger ->

        LazyColumn(Modifier.fillMaxSize()) {
            item {
                Column(
                    Modifier.fillParentMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(Modifier
                        .padding(20.dp)
                        .fillMaxWidth()) {
                        TextB(
                            "HR Connect",
                            color = Primary,
                            style = GlobalFontFamily.ScreenHeader
                        )
                        Spacer(Modifier.height(s8))
                        TextB(
                            "Modern Talent Engine for HR Excellence",
                            color = Placeholder,
                            style = GlobalFontFamily.FieldLabel
                        )
                        Row(Modifier.fillMaxWidth()) {
                            InputDef(
                                state.firstName,
                                {viewModel.firstName(it)},
                                label = "First Name",
                                placeholder = "John",
                                modifier = Modifier.weight(1f),
                                modifierInput = Modifier.testTag("FN")
                            )
                            Spacer(Modifier.width(s16))
                            InputDef(
                                state.lastName,
                                {viewModel.lastName(it)},
                                label = "Last Name",
                                placeholder = "Doe",
                                modifier = Modifier.weight(1f),
                                modifierInput = Modifier.testTag("LN")
                            )
                        }
                        Spacer(Modifier.height(s16))
                        InputDef(
                            state.email,
                            {viewModel.email(it)},
                            label = "Email",
                            placeholder = "name@domain.ru",
                            modifier = Modifier.fillMaxWidth(),
                            modifierInput = Modifier.testTag("E")
                        )
                        Spacer(Modifier.height(s16))
                        PasswordInput(
                            state.password,
                            {viewModel.password(it)},
                            label = "Password",
                            placeholder = "••••••••",
                            modifier = Modifier.fillMaxWidth(),
                            modifierInput = Modifier.testTag("P")
                        )
                        Spacer(Modifier.height(s16))
                        PasswordInput(
                            state.password2,
                            {viewModel.password2(it)},
                            label = "Password Confirm",
                            placeholder = "••••••••",
                            modifier = Modifier.fillMaxWidth(),
                            modifierInput = Modifier.testTag("P2"),
                            error = ""
                        )
                        Spacer(Modifier.height(s16))
                        Row(Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center) {
                            Checkbox(
                                state.isChecked,
                                {viewModel.checked(it)},
                                modifier = Modifier.testTag("C")
                            )
                            Spacer(Modifier.width(s8))
                            TextB(
                                "I agree to the terms of use and privacy\n" +
                                        "policy.",
                                modifier = Modifier.clickable{
                                    uri.openUri("https://google.com")
                                }
                            )
                        }
                        Spacer(Modifier.height(32.dp))
                        ButtonDef(
                            "Register",
                            {
                                viewModel.reg()
                            },
                            Button.Primary,
                            enabled = state.isChecked && state.email.isNotBlank() && state.email.isNotBlank() &&
                                    state.firstName.isNotBlank() &&
                                    state.lastName.isNotBlank() &&
                                    state.password.isNotBlank() &&
                                    state.password2.isNotBlank(),
                            modifier = Modifier.fillMaxWidth().testTag("B")
                        )
                        Spacer(Modifier.height(40.dp))
                        Row(Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center) {
                            TextB("Already have an account? ")
                            TextB("Sign in",
                                color = Primary,
                                modifier = Modifier.clickable{
                                    controller.navigate("login")
                                })
                        }
                    }
                }
            }
        }

    }

}