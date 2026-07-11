package com.rick.bazi.navigation

sealed class BaziScreen(val route: String) {
    object Pan : BaziScreen("bazi_pan")               // 八字排盘主页
    object Analysis : BaziScreen("analysis/{type}") { // 通用分析页，type区分类别
        fun createRoute(type: String) = "analysis/$type"
    }
}