package com.rick.bazi.util

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.rick.bazi.data.BaziData
import com.rick.bazi.data.BaziGeJu
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.ShiShen
import com.rick.bazi.data.TianGan
import com.rick.bazi.ui.BaziViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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

    @Test
    fun test_gj_zhengyin1(){
        var data = BaziData()


        data.yearTiangan = TianGan.TIANGAN_JIA
        data.monthTiangan = TianGan.TIANGAN_BING
        data.dayTiangan = TianGan.TIANGAN_DING
        data.hourTiangan = TianGan.TIANGAN_WU

        data.yearDizhi = DiZhi.DIZHI_ZI
        data.monthDizhi = DiZhi.DIZHI_YIN
        data.dayDizhi = DiZhi.DIZHI_CHOU
        data.hourDizhi = DiZhi.DIZHI_SHEN
        var gj = GeJuUtil().getGJ(data)

        assertEquals(BaziGeJu.GJ_ZHENG_YIN, gj)
    }


    @Test
    fun test_gj_pianyin1(){
        var data = BaziData()
        data.yearTiangan = TianGan.TIANGAN_YI
        data.monthTiangan = TianGan.TIANGAN_GENG
        data.dayTiangan = TianGan.TIANGAN_DING
        data.hourTiangan = TianGan.TIANGAN_JI

        data.yearDizhi = DiZhi.DIZHI_HAI
        data.monthDizhi = DiZhi.DIZHI_CHEN
        data.dayDizhi = DiZhi.DIZHI_HAI
        data.hourDizhi = DiZhi.DIZHI_YOU
        var gj = GeJuUtil().getGJ(data)
        assertEquals(BaziGeJu.GJ_PIAN_YIN, gj)
    }

    @Test
    fun test_gj_shishen1(){
        var data = BaziData()
        data.yearTiangan = TianGan.TIANGAN_JIA
        data.monthTiangan = TianGan.TIANGAN_JI
        data.dayTiangan = TianGan.TIANGAN_JIA
        data.hourTiangan = TianGan.TIANGAN_DING

        data.yearDizhi = DiZhi.DIZHI_XU
        data.monthDizhi = DiZhi.DIZHI_SI
        data.dayDizhi = DiZhi.DIZHI_WU
        data.hourDizhi = DiZhi.DIZHI_MOU
        var gj = GeJuUtil().getGJ(data)
        assertEquals(BaziGeJu.GJ_SHI_SHEN, gj)
    }

    @Test
    fun test_gj_pianguan1(){
        var data = BaziData()
        data.yearTiangan = TianGan.TIANGAN_GENG
        data.monthTiangan = TianGan.TIANGAN_YI
        data.dayTiangan = TianGan.TIANGAN_YI
        data.hourTiangan = TianGan.TIANGAN_WU

        data.yearDizhi = DiZhi.DIZHI_ZI
        data.monthDizhi = DiZhi.DIZHI_YOU
        data.dayDizhi = DiZhi.DIZHI_CHOU
        data.hourDizhi = DiZhi.DIZHI_YIN
        var gj = GeJuUtil().getGJ(data)
        assertEquals(BaziGeJu.GJ_QI_SHA, gj)
    }

    @Test
    fun test_gj_zhengguan1(){
        var year = 2003
        var month = 8
        var day = 10
        var hour = 0
        var baziModel = BaziViewModel()
        var baziInfo = BaziInfo()
        var data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)

        var gj = GeJuUtil().getGJ(data)
//        GeJuUtil().checkShangGuanGJXiJi(gj, data)
        assertEquals(BaziGeJu.GJ_ZHENG_GUAN, gj)

    }

    @Test
    fun test_gj_checkShangGuanGJXiJi1(){
        var year = 2025
        var month = 1
        var day = 1
        var hour = 0
        var baziModel = BaziViewModel()
        var baziInfo = BaziInfo()
        var data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)

        var gj = GeJuUtil().getGJ(data)
        GeJuUtil().checkShangGuanGJXiJi(gj, data)
        assertEquals(BaziGeJu.GJ_SHANG_GUAN, gj)
//        println("yongShenList.size=$data.yongShenList.size")
        println("data=$data")
        assertTrue(data.yongShenList.size == 3 )
        assertTrue(data.yongShenList.get(0) == ShiShen.SHISHEN_ZHENG_YIN )
        assertTrue(data.yongShenList.get(1) == ShiShen.SHISHEN_PIAN_YIN )
        assertTrue(data.yongShenList.get(2) == ShiShen.SHISHEN_JIE_CAI )

        assertTrue(data.xiShenList.size == 3 )
        assertTrue(data.xiShenList.get(0) == ShiShen.SHISHEN_ZHENG_YIN )
        assertTrue(data.xiShenList.get(1) == ShiShen.SHISHEN_PIAN_YIN )
        assertTrue(data.xiShenList.get(2) == ShiShen.SHISHEN_JIE_CAI )

        assertTrue(data.jiShenList.size == 4 )
        assertTrue(data.jiShenList.get(0) == ShiShen.SHISHEN_ZHENG_CAI )
        assertTrue(data.jiShenList.get(1) == ShiShen.SHISHEN_PIAN_CAI )
        assertTrue(data.jiShenList.get(2) == ShiShen.SHISHEN_ZHENG_GUAN )
        assertTrue(data.jiShenList.get(3) == ShiShen.SHISHEN_QI_SHA )
    }

    @Test
    fun test_gj_checkZhengYinGJXiJi1(){
        var data = BaziData()
        data.yearTiangan = TianGan.TIANGAN_BING
        data.monthTiangan = TianGan.TIANGAN_WU
        data.dayTiangan = TianGan.TIANGAN_XIN
        data.hourTiangan = TianGan.TIANGAN_WU

        data.yearDizhi = DiZhi.DIZHI_YIN
        data.monthDizhi = DiZhi.DIZHI_XU
        data.dayDizhi = DiZhi.DIZHI_YOU
        data.hourDizhi = DiZhi.DIZHI_ZI

        var gj = GeJuUtil().getGJ(data)
        assertEquals(BaziGeJu.GJ_ZHENG_YIN, gj)
        GeJuUtil().checkYinGJXiJi(gj, data)

//        println("yongShenList.size=$data.yongShenList.size")
        println("data=$data")
        assertTrue(data.yongShenList.size == 2 )
        assertTrue(data.yongShenList.get(0) == ShiShen.SHISHEN_ZHENG_CAI )
        assertTrue(data.yongShenList.get(1) == ShiShen.SHISHEN_PIAN_CAI )

        assertTrue(data.xiShenList.size == 4 )
        assertTrue(data.xiShenList.get(0) == ShiShen.SHISHEN_ZHENG_CAI )
        assertTrue(data.xiShenList.get(1) == ShiShen.SHISHEN_PIAN_CAI )
        assertTrue(data.xiShenList.get(2) == ShiShen.SHISHEN_SHI_SHEN )
        assertTrue(data.xiShenList.get(3) == ShiShen.SHISHEN_SHANG_GUAN )

        assertTrue(data.jiShenList.size == 6 )
        assertTrue(data.jiShenList.get(0) == ShiShen.SHISHEN_ZHENG_YIN )
        assertTrue(data.jiShenList.get(1) == ShiShen.SHISHEN_PIAN_YIN )
        assertTrue(data.jiShenList.get(2) == ShiShen.SHISHEN_BI_JIAN )
        assertTrue(data.jiShenList.get(3) == ShiShen.SHISHEN_JIE_CAI )
        assertTrue(data.jiShenList.get(4) == ShiShen.SHISHEN_ZHENG_GUAN )
        assertTrue(data.jiShenList.get(5) == ShiShen.SHISHEN_QI_SHA )
    }

    @Test
    fun test_gj_checkZhengGuanGJXiJi1(){
        var data = BaziData()
        data.yearTiangan = TianGan.TIANGAN_JIA
        data.monthTiangan = TianGan.TIANGAN_BING
        data.dayTiangan = TianGan.TIANGAN_JI
        data.hourTiangan = TianGan.TIANGAN_JIA

        data.yearDizhi = DiZhi.DIZHI_ZI
        data.monthDizhi = DiZhi.DIZHI_YIN
        data.dayDizhi = DiZhi.DIZHI_CHOU
        data.hourDizhi = DiZhi.DIZHI_ZI

        var gj = GeJuUtil().getGJ(data)
        assertEquals(BaziGeJu.GJ_ZHENG_GUAN, gj)
        GeJuUtil().checkZhengGuanGJXiJi(gj, data)

        println("data=$data")
        assertTrue(data.yongShenList.size == 2 )
        assertTrue(data.yongShenList.get(0) == ShiShen.SHISHEN_ZHENG_YIN )
        assertTrue(data.yongShenList.get(1) == ShiShen.SHISHEN_PIAN_YIN )

        assertTrue(data.xiShenList.size == 4 )
        assertTrue(data.xiShenList.get(0) == ShiShen.SHISHEN_ZHENG_YIN )
        assertTrue(data.xiShenList.get(1) == ShiShen.SHISHEN_PIAN_YIN )
        assertTrue(data.xiShenList.get(2) == ShiShen.SHISHEN_BI_JIAN )
        assertTrue(data.xiShenList.get(3) == ShiShen.SHISHEN_JIE_CAI )

        assertTrue(data.jiShenList.size == 2 )
        assertTrue(data.jiShenList.get(0) == ShiShen.SHISHEN_ZHENG_CAI )
        assertTrue(data.jiShenList.get(1) == ShiShen.SHISHEN_PIAN_CAI )
    }

    @Test
    fun test_gj_checkCaiGJXiJi1(){
        var data = BaziData()
        data.yearTiangan = TianGan.TIANGAN_WU
        data.monthTiangan = TianGan.TIANGAN_GUI
        data.dayTiangan = TianGan.TIANGAN_XIN
        data.hourTiangan = TianGan.TIANGAN_JIA

        data.yearDizhi = DiZhi.DIZHI_YIN
        data.monthDizhi = DiZhi.DIZHI_HAI
        data.dayDizhi = DiZhi.DIZHI_YOU
        data.hourDizhi = DiZhi.DIZHI_WU

        var gj = GeJuUtil().getGJ(data)
        assertEquals(BaziGeJu.GJ_ZHENG_CAI, gj)
        GeJuUtil().checkCaiGJXiJi(gj, data)

        println("data=$data")
        assertTrue(data.yongShenList.size == 2 )
        assertTrue(data.yongShenList.get(0) == ShiShen.SHISHEN_ZHENG_YIN )
        assertTrue(data.yongShenList.get(1) == ShiShen.SHISHEN_PIAN_YIN )

        assertTrue(data.xiShenList.size == 4 )
        assertTrue(data.xiShenList.get(0) == ShiShen.SHISHEN_ZHENG_YIN )
        assertTrue(data.xiShenList.get(1) == ShiShen.SHISHEN_PIAN_YIN )
        assertTrue(data.xiShenList.get(2) == ShiShen.SHISHEN_BI_JIAN )
        assertTrue(data.xiShenList.get(3) == ShiShen.SHISHEN_JIE_CAI )

        assertTrue(data.jiShenList.size == 4 )
        assertTrue(data.jiShenList.get(0) == ShiShen.SHISHEN_ZHENG_CAI )
        assertTrue(data.jiShenList.get(1) == ShiShen.SHISHEN_PIAN_CAI )
        assertTrue(data.jiShenList.get(2) == ShiShen.SHISHEN_ZHENG_GUAN )
        assertTrue(data.jiShenList.get(3) == ShiShen.SHISHEN_QI_SHA )
    }

    @Test
    fun test_gj_checkCaiGJXiJi2(){
        //阳历：1969年06月28日 15:56
        //正财格
        var year = 1969
        var month = 6
        var day = 28
        var hour = 15
        var baziModel = BaziViewModel()
        var baziInfo = BaziInfo()
        var data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)

        var gj = GeJuUtil().getGJ(data)
        assertEquals(BaziGeJu.GJ_ZHENG_CAI, gj)
        GeJuUtil().checkCaiGJXiJi(gj, data)

        println("data=$data")
        assertTrue(data.yongShenList.size == 2 )
        assertTrue(data.yongShenList.get(0) == ShiShen.SHISHEN_ZHENG_YIN )
        assertTrue(data.yongShenList.get(1) == ShiShen.SHISHEN_PIAN_YIN )

        assertTrue(data.xiShenList.size == 4 )
        assertTrue(data.xiShenList.get(0) == ShiShen.SHISHEN_ZHENG_YIN )
        assertTrue(data.xiShenList.get(1) == ShiShen.SHISHEN_PIAN_YIN )
        assertTrue(data.xiShenList.get(2) == ShiShen.SHISHEN_BI_JIAN )
        assertTrue(data.xiShenList.get(3) == ShiShen.SHISHEN_JIE_CAI )

        assertTrue(data.jiShenList.size == 2 )
        assertTrue(data.jiShenList.get(0) == ShiShen.SHISHEN_ZHENG_CAI )
        assertTrue(data.jiShenList.get(1) == ShiShen.SHISHEN_PIAN_CAI )

    }

    @Test
    fun test_gj_checkShiShenGJXiJi1(){
        //阳历：1967年09月22日 5:36
        //食神格
        var year = 1967
        var month = 9
        var day = 22
        var hour = 5
        var baziModel = BaziViewModel()
        var baziInfo = BaziInfo()
        var data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)

        var gj = GeJuUtil().getGJ(data)
        assertEquals(BaziGeJu.GJ_SHI_SHEN, gj)
        GeJuUtil().checkShiShenGJXiJi(gj, data)

        println("data=$data")
        assertTrue(data.yongShenList.size == 2 )
        assertTrue(data.yongShenList.get(0) == ShiShen.SHISHEN_SHI_SHEN )
        assertTrue(data.yongShenList.get(1) == ShiShen.SHISHEN_SHANG_GUAN )

        assertTrue(data.xiShenList.size == 4 )
        assertTrue(data.xiShenList.get(0) == ShiShen.SHISHEN_SHI_SHEN )
        assertTrue(data.xiShenList.get(1) == ShiShen.SHISHEN_SHANG_GUAN )
        assertTrue(data.xiShenList.get(2) == ShiShen.SHISHEN_ZHENG_CAI )
        assertTrue(data.xiShenList.get(3) == ShiShen.SHISHEN_PIAN_CAI )

        assertTrue(data.jiShenList.size == 4 )
        assertTrue(data.jiShenList.get(0) == ShiShen.SHISHEN_ZHENG_YIN )
        assertTrue(data.jiShenList.get(1) == ShiShen.SHISHEN_PIAN_YIN )
        assertTrue(data.jiShenList.get(2) == ShiShen.SHISHEN_BI_JIAN )
        assertTrue(data.jiShenList.get(3) == ShiShen.SHISHEN_JIE_CAI)

    }

    @Test
    fun test_gj_checkQiShaGJXiJi1(){
        //阳历：2027年06月1日 0:36
        //偏官格
        var year = 2027
        var month = 6
        var day = 1
        var hour = 0
        var baziModel = BaziViewModel()
        var baziInfo = BaziInfo()
        var data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)

        var gj = GeJuUtil().getGJ(data)
        assertEquals(BaziGeJu.GJ_QI_SHA, gj)
        GeJuUtil().checkQiShaGJXiJi(gj, data)

        println("data=$data")
        assertTrue(data.yongShenList.size == 2 )
        assertTrue(data.yongShenList.get(0) == ShiShen.SHISHEN_ZHENG_YIN )
        assertTrue(data.yongShenList.get(1) == ShiShen.SHISHEN_PIAN_YIN )

        assertTrue(data.xiShenList.size == 4 )
        assertTrue(data.xiShenList.get(0) == ShiShen.SHISHEN_ZHENG_YIN )
        assertTrue(data.xiShenList.get(1) == ShiShen.SHISHEN_PIAN_YIN )
        assertTrue(data.xiShenList.get(2) == ShiShen.SHISHEN_BI_JIAN )
        assertTrue(data.xiShenList.get(3) == ShiShen.SHISHEN_JIE_CAI )

        assertTrue(data.jiShenList.size == 4 )
        assertTrue(data.jiShenList.get(0) == ShiShen.SHISHEN_ZHENG_CAI )
        assertTrue(data.jiShenList.get(1) == ShiShen.SHISHEN_PIAN_CAI )
        assertTrue(data.jiShenList.get(2) == ShiShen.SHISHEN_ZHENG_GUAN )
        assertTrue(data.jiShenList.get(3) == ShiShen.SHISHEN_QI_SHA)

    }
}