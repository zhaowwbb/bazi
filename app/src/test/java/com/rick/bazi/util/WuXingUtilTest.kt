package com.rick.bazi.util

import com.rick.bazi.data.BaziData
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.TianGan
import com.rick.bazi.ui.BaziViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class WuXingUtilTest {

    @Test
    fun testDangLingYes() {
        var year = 1978
        var month = 8
        var day = 9
        var hour = 0
        var baziModel = BaziViewModel()
        var baziInfo = BaziInfo()
        var data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)

        assertEquals(year, data.birthDateYear)
        assertEquals(month, data.birthDateMonth)
        assertEquals(day, data.birthDateDay)
        assertEquals(hour, data.birthHour)

        assertEquals(TianGan.TIANGAN_WU, data.yearTiangan)
        assertEquals(DiZhi.DIZHI_WU, data.yearDizhi)
        assertEquals(TianGan.TIANGAN_GENG, data.monthTiangan)
        assertEquals(DiZhi.DIZHI_SHEN, data.monthDizhi)
        assertEquals(TianGan.TIANGAN_GUI, data.dayTiangan)
        assertEquals(DiZhi.DIZHI_MOU, data.dayDizhi)
        assertEquals(TianGan.TIANGAN_REN, data.hourTiangan)
        assertEquals(DiZhi.DIZHI_ZI, data.hourDizhi)

        var isDangLing = WuXingUtil().isDangling(data.monthDizhi, data.dayTiangan)
        assertEquals(true, isDangLing)
    }

    @Test
    fun testDangLingNo() {
        var year = 2025
        var month = 1
        var day = 1
        var hour = 0
        var baziModel = BaziViewModel()
        var baziInfo = BaziInfo()
        var data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)

        assertEquals(year, data.birthDateYear)
        assertEquals(month, data.birthDateMonth)
        assertEquals(day, data.birthDateDay)
        assertEquals(hour, data.birthHour)

        assertEquals(TianGan.TIANGAN_JIA, data.yearTiangan)
        assertEquals(DiZhi.DIZHI_CHEN, data.yearDizhi)
        assertEquals(TianGan.TIANGAN_BING, data.monthTiangan)
        assertEquals(DiZhi.DIZHI_ZI, data.monthDizhi)
        assertEquals(TianGan.TIANGAN_GENG, data.dayTiangan)
        assertEquals(DiZhi.DIZHI_WU, data.dayDizhi)
        assertEquals(TianGan.TIANGAN_BING, data.hourTiangan)
        assertEquals(DiZhi.DIZHI_ZI, data.hourDizhi)

        var isDangLing = WuXingUtil().isDangling(data.monthDizhi, data.dayTiangan)
        assertEquals(false, isDangLing)
    }

    @Test
    fun testDeDiNo() {
        var data = BaziData()
        var baziModel = BaziViewModel()
        var isDedi = false
        data.yearTiangan = TianGan.TIANGAN_JIA
        data.monthTiangan = TianGan.TIANGAN_DING
        data.dayTiangan = TianGan.TIANGAN_GENG
        data.hourTiangan = TianGan.TIANGAN_BING

        data.yearDizhi = DiZhi.DIZHI_ZI
        data.monthDizhi = DiZhi.DIZHI_MOU
        data.dayDizhi = DiZhi.DIZHI_WU
        data.hourDizhi = DiZhi.DIZHI_ZI
        isDedi = WuXingUtil().isBaziDedi(data)

        assertEquals(false, isDedi)
    }

    @Test
    fun testDeDiYes() {
        var data = BaziData()
        var baziModel = BaziViewModel()
        var isDedi = false
        data.yearTiangan = TianGan.TIANGAN_WU
        data.monthTiangan = TianGan.TIANGAN_XIN
        data.dayTiangan = TianGan.TIANGAN_XIN
        data.hourTiangan = TianGan.TIANGAN_WU

        data.yearDizhi = DiZhi.DIZHI_CHEN
        data.monthDizhi = DiZhi.DIZHI_YOU
        data.dayDizhi = DiZhi.DIZHI_YOU
        data.hourDizhi = DiZhi.DIZHI_XU
        isDedi = WuXingUtil().isBaziDedi(data)
        println("Expect Dedi")
        assertEquals(true, isDedi)
    }
}