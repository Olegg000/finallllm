package com.example.main3

import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.main3.domain.validator.RegValidator
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.openapitools.client.models.RegisterRequest

@RunWith(AndroidJUnit4::class)
class TDDTests {

    val mail = "test1@test.test"
    val pass = "P@ssw0rd"
    val test = "test"

    fun randMail(): String {
        return "test${System.currentTimeMillis()}@test.test"
    }

    @Test
    fun mail() {
        val res = RegValidator.mailCheck(
            RegisterRequest(
                test,
                test,
                mail,
                test,
                test
            )
        )
        assert(!res)
    }

    @Test
    fun passwordMatch() {
        val res = RegValidator.passwordMatch(
            RegisterRequest(
                test,
                test,
                mail,
                pass,
                test
            ),
            pass
        )
        assert(!res)
    }

    @Test
    fun passwordLength() {
        val res = RegValidator.passwordShort(
            RegisterRequest(
                test,
                test,
                mail,
                pass,
                test
            )
        )
        assert(!res)
    }

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun validReg() {
        rule.onNodeWithTag("FN").performTextInput(test)
        rule.onNodeWithTag("LN").performTextInput(test)
        rule.onNodeWithTag("E").performTextInput(randMail())
        rule.onNodeWithTag("P").performTextInput(pass)
        rule.onNodeWithTag("P2").performTextInput(pass)
        rule.onNodeWithTag("C").performClick()
        rule.onNodeWithTag("B").performClick()
        rule.waitUntil(5000,{
            rule.onNodeWithText("login").isDisplayed()
        })
        rule.onNodeWithText("login").assertExists()
    }

    @Test
    fun invalidReg() {
        rule.onNodeWithTag("FN").performTextInput(test)
        rule.onNodeWithTag("LN").performTextInput(test)
        rule.onNodeWithTag("E").performTextInput(mail)
        rule.onNodeWithTag("P").performTextInput(pass)
        rule.onNodeWithTag("P2").performTextInput(pass)
        rule.onNodeWithTag("C").performClick()
        rule.onNodeWithTag("B").performClick()
        rule.waitUntil(5000,{
            rule.onNodeWithText("409",substring = true).isDisplayed()
        })
        rule.onNodeWithText("409",substring = true).assertExists()
    }

}