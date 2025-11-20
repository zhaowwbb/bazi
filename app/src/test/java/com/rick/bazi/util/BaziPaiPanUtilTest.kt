package com.rick.bazi.util

import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.TianGan
import com.rick.bazi.ui.BaziViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class BaziPaiPanUtilTest {

    @Test
    fun testPaiPan1() {
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
    }

    @Test
    fun testPaiPan2() {
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
    }

    @Test
    fun testPaiPan3() {
        var year = 2025
        var month = 11
        var day = 12
        var hour = 7
        var baziModel = BaziViewModel()
        var baziInfo = BaziInfo()
        var data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)

        assertEquals(year, data.birthDateYear)
        assertEquals(month, data.birthDateMonth)
        assertEquals(day, data.birthDateDay)
        assertEquals(hour, data.birthHour)

        assertEquals(TianGan.TIANGAN_YI, data.yearTiangan)
        assertEquals(DiZhi.DIZHI_SI, data.yearDizhi)
        assertEquals(TianGan.TIANGAN_DING, data.monthTiangan)
        assertEquals(DiZhi.DIZHI_HAI, data.monthDizhi)
        assertEquals(TianGan.TIANGAN_YI, data.dayTiangan)
        assertEquals(DiZhi.DIZHI_YOU, data.dayDizhi)
        assertEquals(TianGan.TIANGAN_GENG, data.hourTiangan)
        assertEquals(DiZhi.DIZHI_CHEN, data.hourDizhi)
    }

    @Test
    fun testPaiPan4() {
        //1986年02月04日 19:56
        var year = 1986
        var month = 2
        var day = 4
        var hour = 19
        var baziModel = BaziViewModel()
        var baziInfo = BaziInfo()
        var data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)

        assertEquals(year, data.birthDateYear)
        assertEquals(month, data.birthDateMonth)
        assertEquals(day, data.birthDateDay)
        assertEquals(hour, data.birthHour)

        assertEquals(TianGan.TIANGAN_BING, data.yearTiangan)
        assertEquals(DiZhi.DIZHI_YIN, data.yearDizhi)
        assertEquals(TianGan.TIANGAN_GENG, data.monthTiangan)
        assertEquals(DiZhi.DIZHI_YIN, data.monthDizhi)
        assertEquals(TianGan.TIANGAN_JI, data.dayTiangan)
        assertEquals(DiZhi.DIZHI_MOU, data.dayDizhi)
        assertEquals(TianGan.TIANGAN_JIA, data.hourTiangan)
        assertEquals(DiZhi.DIZHI_XU, data.hourDizhi)
    }

    @Test
    fun testPaiPan5() {
//阳历：1986年01月02日 17:56
        //check month DiZhi Zi, should be the month in previous year
        var year = 1986
        var month = 1
        var day = 2
        var hour = 17
        var baziModel = BaziViewModel()
        var baziInfo = BaziInfo()
        var data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)

        assertEquals(year, data.birthDateYear)
        assertEquals(month, data.birthDateMonth)
        assertEquals(day, data.birthDateDay)
        assertEquals(hour, data.birthHour)

        assertEquals(TianGan.TIANGAN_YI, data.yearTiangan)
        assertEquals(DiZhi.DIZHI_CHOU, data.yearDizhi)
        assertEquals(TianGan.TIANGAN_WU, data.monthTiangan)
        assertEquals(DiZhi.DIZHI_ZI, data.monthDizhi)
        assertEquals(TianGan.TIANGAN_BING, data.dayTiangan)
        assertEquals(DiZhi.DIZHI_WU, data.dayDizhi)
        assertEquals(TianGan.TIANGAN_DING, data.hourTiangan)
        assertEquals(DiZhi.DIZHI_YOU, data.hourDizhi)
    }

    @Test
    fun testPaiPan6() {
//1985年12月26日 17:56
        //check month DiZhi Zi, should be the month in previous year
        var year = 1985
        var month = 12
        var day = 26
        var hour = 17
        var baziModel = BaziViewModel()
        var baziInfo = BaziInfo()
        var data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)

        assertEquals(year, data.birthDateYear)
        assertEquals(month, data.birthDateMonth)
        assertEquals(day, data.birthDateDay)
        assertEquals(hour, data.birthHour)

        assertEquals(TianGan.TIANGAN_YI, data.yearTiangan)
        assertEquals(DiZhi.DIZHI_CHOU, data.yearDizhi)
        assertEquals(TianGan.TIANGAN_WU, data.monthTiangan)
        assertEquals(DiZhi.DIZHI_ZI, data.monthDizhi)
        assertEquals(TianGan.TIANGAN_JI, data.dayTiangan)
        assertEquals(DiZhi.DIZHI_HAI, data.dayDizhi)
        assertEquals(TianGan.TIANGAN_GUI, data.hourTiangan)
        assertEquals(DiZhi.DIZHI_YOU, data.hourDizhi)
    }

    @Test
    fun testPaiPan7() {
        //just before the new lunar year
        //阳历：1986年02月03日 17:56
        var year = 1986
        var month = 2
        var day = 3
        var hour = 17
        var baziModel = BaziViewModel()
        var baziInfo = BaziInfo()
        var data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)

        assertEquals(year, data.birthDateYear)
        assertEquals(month, data.birthDateMonth)
        assertEquals(day, data.birthDateDay)
        assertEquals(hour, data.birthHour)

        assertEquals(TianGan.TIANGAN_YI, data.yearTiangan)
        assertEquals(DiZhi.DIZHI_CHOU, data.yearDizhi)
        assertEquals(TianGan.TIANGAN_JI, data.monthTiangan)
        assertEquals(DiZhi.DIZHI_CHOU, data.monthDizhi)
        assertEquals(TianGan.TIANGAN_WU, data.dayTiangan)
        assertEquals(DiZhi.DIZHI_YIN, data.dayDizhi)
        assertEquals(TianGan.TIANGAN_XIN, data.hourTiangan)
        assertEquals(DiZhi.DIZHI_YOU, data.hourDizhi)
    }

    @Test
    fun testPaiPan8() {
        //just before the new lunar year
        //阳历：1986年02月10日 17:56
        var year = 1986
        var month = 2
        var day = 10
        var hour = 17
        var baziModel = BaziViewModel()
        var baziInfo = BaziInfo()
        var data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)

        assertEquals(year, data.birthDateYear)
        assertEquals(month, data.birthDateMonth)
        assertEquals(day, data.birthDateDay)
        assertEquals(hour, data.birthHour)

        assertEquals(TianGan.TIANGAN_BING, data.yearTiangan)
        assertEquals(DiZhi.DIZHI_YIN, data.yearDizhi)
        assertEquals(TianGan.TIANGAN_GENG, data.monthTiangan)
        assertEquals(DiZhi.DIZHI_YIN, data.monthDizhi)
        assertEquals(TianGan.TIANGAN_YI, data.dayTiangan)
        assertEquals(DiZhi.DIZHI_YOU, data.dayDizhi)
        assertEquals(TianGan.TIANGAN_YI, data.hourTiangan)
        assertEquals(DiZhi.DIZHI_YOU, data.hourDizhi)
    }

    @Test
    fun testPaiPan9() {
        //just before the new lunar year and after xiaohan
        //阳历：1970年01月7日 5:56
        var year = 1970
        var month = 1
        var day = 7
        var hour = 5
        var baziModel = BaziViewModel()
        var baziInfo = BaziInfo()
        var data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)

        assertEquals(year, data.birthDateYear)
        assertEquals(month, data.birthDateMonth)
        assertEquals(day, data.birthDateDay)
        assertEquals(hour, data.birthHour)

        assertEquals(TianGan.TIANGAN_JI, data.yearTiangan)
        assertEquals(DiZhi.DIZHI_YOU, data.yearDizhi)
        assertEquals(TianGan.TIANGAN_DING, data.monthTiangan)
        assertEquals(DiZhi.DIZHI_CHOU, data.monthDizhi)
        assertEquals(TianGan.TIANGAN_DING, data.dayTiangan)
        assertEquals(DiZhi.DIZHI_HAI, data.dayDizhi)
        assertEquals(TianGan.TIANGAN_GUI, data.hourTiangan)
        assertEquals(DiZhi.DIZHI_MOU, data.hourDizhi)
    }

    @Test
    fun testPaiPan10() {
        //小寒 前
        //阳历：2025年01月4日 23:01
        var year = 2025
        var month = 1
        var day = 4
        var hour = 23
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
        assertEquals(TianGan.TIANGAN_JIA, data.dayTiangan)
        assertEquals(DiZhi.DIZHI_XU, data.dayDizhi)
        assertEquals(TianGan.TIANGAN_JIA, data.hourTiangan)
        assertEquals(DiZhi.DIZHI_ZI, data.hourDizhi)
    }

    @Test
    fun testPaiPan11() {
        //小寒
        //阳历：2025年01月5日 7:05
        var year = 2025
        var month = 1
        var day = 5
        var hour = 7
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
        assertEquals(TianGan.TIANGAN_JIA, data.dayTiangan)
        assertEquals(DiZhi.DIZHI_XU, data.dayDizhi)
        assertEquals(TianGan.TIANGAN_WU, data.hourTiangan)
        assertEquals(DiZhi.DIZHI_CHEN, data.hourDizhi)
    }

    @Test
    fun testPaiPan12() {
        //小寒
        var year = 2025
        var month = 1
        var day = 5
        var hour = 11
        var baziModel = BaziViewModel()
        var baziInfo = BaziInfo()
        var data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)

        assertEquals(year, data.birthDateYear)
        assertEquals(month, data.birthDateMonth)
        assertEquals(day, data.birthDateDay)
        assertEquals(hour, data.birthHour)

        assertEquals(TianGan.TIANGAN_JIA, data.yearTiangan)
        assertEquals(DiZhi.DIZHI_CHEN, data.yearDizhi)
        assertEquals(TianGan.TIANGAN_DING, data.monthTiangan)
        assertEquals(DiZhi.DIZHI_CHOU, data.monthDizhi)
        assertEquals(TianGan.TIANGAN_JIA, data.dayTiangan)
        assertEquals(DiZhi.DIZHI_XU, data.dayDizhi)
        assertEquals(TianGan.TIANGAN_GENG, data.hourTiangan)
        assertEquals(DiZhi.DIZHI_WU, data.hourDizhi)
    }

    @Test
    fun testPaiPan13() {
        //小寒
        var year = 2025
        var month = 1
        var day = 20
        var hour = 3
        var baziModel = BaziViewModel()
        var baziInfo = BaziInfo()
        var data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)

        assertEquals(year, data.birthDateYear)
        assertEquals(month, data.birthDateMonth)
        assertEquals(day, data.birthDateDay)
        assertEquals(hour, data.birthHour)

        assertEquals(TianGan.TIANGAN_JIA, data.yearTiangan)
        assertEquals(DiZhi.DIZHI_CHEN, data.yearDizhi)
        assertEquals(TianGan.TIANGAN_DING, data.monthTiangan)
        assertEquals(DiZhi.DIZHI_CHOU, data.monthDizhi)
        assertEquals(TianGan.TIANGAN_JI, data.dayTiangan)
        assertEquals(DiZhi.DIZHI_CHOU, data.dayDizhi)
        assertEquals(TianGan.TIANGAN_BING, data.hourTiangan)
        assertEquals(DiZhi.DIZHI_YIN, data.hourDizhi)
    }

    @Test
    fun testPaiPan14() {
        //立春
        var year = 2025
        var month = 2
        var day = 3
        var hour = 23
        var baziModel = BaziViewModel()
        var baziInfo = BaziInfo()
        var data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)

        assertEquals(year, data.birthDateYear)
        assertEquals(month, data.birthDateMonth)
        assertEquals(day, data.birthDateDay)
        assertEquals(hour, data.birthHour)

        assertEquals(TianGan.TIANGAN_YI, data.yearTiangan)
        assertEquals(DiZhi.DIZHI_SI, data.yearDizhi)
        assertEquals(TianGan.TIANGAN_WU, data.monthTiangan)
        assertEquals(DiZhi.DIZHI_YIN, data.monthDizhi)
        assertEquals(TianGan.TIANGAN_JIA, data.dayTiangan)
        assertEquals(DiZhi.DIZHI_CHEN, data.dayDizhi)
        assertEquals(TianGan.TIANGAN_JIA, data.hourTiangan)
        assertEquals(DiZhi.DIZHI_ZI, data.hourDizhi)
    }
}