package com.rick.bazi.util

import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.TianGan
import com.rick.bazi.data.WuXing
import com.rick.bazi.ui.BaziViewModel
import com.rick.bazi.util.TianGanUtil.TianGanPos
import org.junit.Assert.assertEquals
import org.junit.Test

class DiZhiUtilTest {

    @Test
    fun testDiZhiSanHe1() {
        var dz = DiZhi.DIZHI_YIN
        var dz1 = DiZhi.DIZHI_ZI
        var dz2 = DiZhi.DIZHI_YIN
        println("[testDiZhiSanHe1] dz=$dz")
        if(dz1 > dz2){
            println("DIZHI_ZI > DIZHI_YIN")
        }else{
            println("DIZHI_ZI < DIZHI_YIN")
        }
        //寅午戌三合火局
        var isSanHe = DiZhiUtil().isDiZhiSanHe(DiZhi.DIZHI_YIN, DiZhi.DIZHI_WU, DiZhi.DIZHI_XU)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHe(DiZhi.DIZHI_XU, DiZhi.DIZHI_WU, DiZhi.DIZHI_YIN)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHe(DiZhi.DIZHI_WU, DiZhi.DIZHI_YIN, DiZhi.DIZHI_XU)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHe(DiZhi.DIZHI_WU, DiZhi.DIZHI_XU, DiZhi.DIZHI_YIN)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHe(DiZhi.DIZHI_WU, DiZhi.DIZHI_XU, DiZhi.DIZHI_HAI)
        assertEquals(false, isSanHe)

        //申子辰三合水局
        isSanHe = DiZhiUtil().isDiZhiSanHe(DiZhi.DIZHI_SHEN, DiZhi.DIZHI_ZI, DiZhi.DIZHI_CHEN)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHe(DiZhi.DIZHI_CHEN, DiZhi.DIZHI_ZI, DiZhi.DIZHI_SHEN)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHe(DiZhi.DIZHI_ZI, DiZhi.DIZHI_CHEN, DiZhi.DIZHI_SHEN)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHe(DiZhi.DIZHI_ZI, DiZhi.DIZHI_SHEN, DiZhi.DIZHI_CHEN)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHe(DiZhi.DIZHI_SHEN, DiZhi.DIZHI_XU, DiZhi.DIZHI_CHEN)
        assertEquals(false, isSanHe)

        //亥卯未三合木局
        isSanHe = DiZhiUtil().isDiZhiSanHe(DiZhi.DIZHI_HAI, DiZhi.DIZHI_MOU, DiZhi.DIZHI_WEI)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHe(DiZhi.DIZHI_WEI, DiZhi.DIZHI_MOU, DiZhi.DIZHI_HAI)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHe(DiZhi.DIZHI_MOU, DiZhi.DIZHI_WEI, DiZhi.DIZHI_HAI)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHe(DiZhi.DIZHI_MOU, DiZhi.DIZHI_HAI, DiZhi.DIZHI_WEI)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHe(DiZhi.DIZHI_SHEN, DiZhi.DIZHI_MOU, DiZhi.DIZHI_WEI)
        assertEquals(false, isSanHe)

        //巳酉丑三合金局
        isSanHe = DiZhiUtil().isDiZhiSanHe(DiZhi.DIZHI_SI, DiZhi.DIZHI_YOU, DiZhi.DIZHI_CHOU)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHe(DiZhi.DIZHI_CHOU, DiZhi.DIZHI_YOU, DiZhi.DIZHI_SI)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHe(DiZhi.DIZHI_YOU, DiZhi.DIZHI_CHOU, DiZhi.DIZHI_SI)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHe(DiZhi.DIZHI_YOU, DiZhi.DIZHI_SI, DiZhi.DIZHI_CHOU)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHe(DiZhi.DIZHI_SI, DiZhi.DIZHI_XU, DiZhi.DIZHI_CHEN)
        assertEquals(false, isSanHe)
    }

    @Test
    fun testDiZhiSanHe2() {
        var year = 1928
        var month = 7
        var day = 31
        var hour = 0
        var baziModel = BaziViewModel()
        var baziInfo = BaziInfo()
        var data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)

        var hasSanHe = DiZhiUtil().hasDiZhiSanHe(data)
        assertEquals(true, hasSanHe)
        assertEquals(WuXing.WUXING_SHUI, DiZhiUtil().getSanHeWuXing(data))
    }

    @Test
    fun testDiZhiSanHui1() {

        //寅卯辰会成东方木
        var isSanHe = DiZhiUtil().isDiZhiSanHui(DiZhi.DIZHI_YIN, DiZhi.DIZHI_MOU, DiZhi.DIZHI_CHEN)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHui(DiZhi.DIZHI_CHEN, DiZhi.DIZHI_MOU, DiZhi.DIZHI_YIN)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHui(DiZhi.DIZHI_CHEN, DiZhi.DIZHI_YIN, DiZhi.DIZHI_MOU)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHui(DiZhi.DIZHI_MOU, DiZhi.DIZHI_CHEN, DiZhi.DIZHI_YIN)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHui(DiZhi.DIZHI_WU, DiZhi.DIZHI_XU, DiZhi.DIZHI_HAI)
        assertEquals(false, isSanHe)

        //巳午未会成南方火
        isSanHe = DiZhiUtil().isDiZhiSanHui(DiZhi.DIZHI_SI, DiZhi.DIZHI_WU, DiZhi.DIZHI_WEI)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHui(DiZhi.DIZHI_WEI, DiZhi.DIZHI_WU, DiZhi.DIZHI_SI)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHui(DiZhi.DIZHI_SI, DiZhi.DIZHI_WEI, DiZhi.DIZHI_WU)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHui(DiZhi.DIZHI_WU, DiZhi.DIZHI_WEI, DiZhi.DIZHI_SI)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHui(DiZhi.DIZHI_WU, DiZhi.DIZHI_XU, DiZhi.DIZHI_HAI)
        assertEquals(false, isSanHe)

        //申酉戌会成西方金
        isSanHe = DiZhiUtil().isDiZhiSanHui(DiZhi.DIZHI_SHEN, DiZhi.DIZHI_YOU, DiZhi.DIZHI_XU)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHui(DiZhi.DIZHI_XU, DiZhi.DIZHI_YOU, DiZhi.DIZHI_SHEN)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHui(DiZhi.DIZHI_SHEN, DiZhi.DIZHI_XU, DiZhi.DIZHI_YOU)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHui(DiZhi.DIZHI_YOU, DiZhi.DIZHI_XU, DiZhi.DIZHI_SHEN)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHui(DiZhi.DIZHI_WU, DiZhi.DIZHI_XU, DiZhi.DIZHI_HAI)
        assertEquals(false, isSanHe)

        //亥子丑会成北方水
        isSanHe = DiZhiUtil().isDiZhiSanHui(DiZhi.DIZHI_HAI, DiZhi.DIZHI_ZI, DiZhi.DIZHI_CHOU)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHui(DiZhi.DIZHI_CHOU, DiZhi.DIZHI_ZI, DiZhi.DIZHI_HAI)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHui(DiZhi.DIZHI_HAI, DiZhi.DIZHI_CHOU, DiZhi.DIZHI_ZI)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHui(DiZhi.DIZHI_ZI, DiZhi.DIZHI_CHOU, DiZhi.DIZHI_HAI)
        assertEquals(true, isSanHe)
        isSanHe = DiZhiUtil().isDiZhiSanHui(DiZhi.DIZHI_WU, DiZhi.DIZHI_XU, DiZhi.DIZHI_HAI)
        assertEquals(false, isSanHe)
    }

    @Test
    fun testDiZhiSanHui2() {
        var year = 1998
        var month = 3
        var day = 18
        var hour = 8
        var baziModel = BaziViewModel()
        var baziInfo = BaziInfo()
        var data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)

        var hasSanHui = DiZhiUtil().hasDiZhiSanHui(data)
        assertEquals(true, hasSanHui)
        assertEquals(WuXing.WUXING_MU, DiZhiUtil().getSanHuiWuXing(data))

        hour = 12
        data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)
        hasSanHui = DiZhiUtil().hasDiZhiSanHui(data)
        assertEquals(false, hasSanHui)
    }

    @Test
    fun testDiZhi6He1() {
        var year = 2000
        var month = 2
        var day = 11
        var hour = 17
        var baziModel = BaziViewModel()
        var baziInfo = BaziInfo()
        var data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)

        var hasSanHui = DiZhiUtil().hasDiZhiSanHui(data)
        assertEquals(true, hasSanHui)
        assertEquals(WuXing.WUXING_MU, DiZhiUtil().getSanHuiWuXing(data))

        hour = 12
        data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)
        hasSanHui = DiZhiUtil().hasDiZhiSanHui(data)
        assertEquals(false, hasSanHui)
    }
}