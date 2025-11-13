package com.rick.bazi.util

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.ui.BaziViewModel
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test


class GeJuUtilTest {


//    @get:Rule
//    val composeTestRule = createComposeRule()

//    @Test
//    @OptIn(ExperimentalTestApi::class)
//    fun myGreeting_displaysCorrectText() {
//        // Set the content to the Composable you want to test
//        composeTestRule.setContent {
//            MyGreeting(name = "World")
//        }
//
//        // Assert that the text is displayed correctly
//        composeTestRule.onNodeWithText("Hello, World!").assertExists()
//    }


    @Test
    fun addition_isCorrect() {
        var a = 12
        var b = 12
        assertEquals(a, b)
    }

//    @Test
////    @Composable
//    fun test2() {
////        val baziInfo by viewModel.uiState.collectAsState()
//        var str = "AAA"
////        composeTestRule.setContent {
//////            val wallpaper = createWallpaper()
//////                str = WuXingUtil().getDangLingStr(baziInfo, viewModel)
////        }
////        var
////        var a = 12
////        var str = "AAA"
//        var targetStr = "AAA"
//        var currentGameUiState = viewModel.uiState.value
//
//        assertEquals(str, targetStr)
//    }

//    @Test
//    fun test_paipan() {
//        var year = 2012
//        var month = 10
//        var day = 10
//        var hour = 10
//        var baziModel = BaziViewModel()
//        var baziInfo = BaziInfo()
//        BaziPaiPanUtil().paipan(year, month, day, hour, baziModel, baziInfo)
//        var a = 12
//        var b = 12
//        assertEquals(a, b)
//    }

}