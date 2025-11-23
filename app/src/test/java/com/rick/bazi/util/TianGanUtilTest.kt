package com.rick.bazi.util

import com.rick.bazi.data.BaziData
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.TianGan
import com.rick.bazi.ui.BaziViewModel
import com.rick.bazi.util.TianGanUtil.TianGanPos
import com.rick.bazi.util.TianGanUtil.TianGanaHeInfo
import org.junit.Assert.assertEquals
import org.junit.Test

class TianGanUtilTest {
    @Test
    fun testTianGanHe1() {
        var year = 1982
        var month = 7
        var day = 15
        var hour = 0
        var baziModel = BaziViewModel()
        var baziInfo = BaziInfo()
        var data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)

        val list = TianGanUtil().getTianGanHeList(data)
        assertEquals(2, list.size)

        assertEquals(TianGanPos.TIANGAN_YEAR, list.get(0).srcTgPos)
        assertEquals(TianGan.TIANGAN_REN, list.get(0).srcTg)
        assertEquals(TianGanPos.TIANGAN_MONTH, list.get(0).dstTgPos)
        assertEquals(TianGan.TIANGAN_DING, list.get(0).dstTg)

        assertEquals(TianGanPos.TIANGAN_DAY, list.get(1).srcTgPos)
        assertEquals(TianGan.TIANGAN_JI, list.get(1).srcTg)
        assertEquals(TianGanPos.TIANGAN_HOUR, list.get(1).dstTgPos)
        assertEquals(TianGan.TIANGAN_JIA, list.get(1).dstTg)
    }

    @Test
    fun testTianGanHe2(){
        var data = BaziData()

        data.yearTiangan = TianGan.TIANGAN_WU
        data.monthTiangan = TianGan.TIANGAN_XIN
        data.dayTiangan = TianGan.TIANGAN_XIN
        data.hourTiangan = TianGan.TIANGAN_WU

        data.yearDizhi = DiZhi.DIZHI_CHEN
        data.monthDizhi = DiZhi.DIZHI_YOU
        data.dayDizhi = DiZhi.DIZHI_YOU
        data.hourDizhi = DiZhi.DIZHI_XU

        var isTianGanHe = TianGanUtil().isTianGanHe(TianGan.TIANGAN_JIA, TianGan.TIANGAN_JI)
        assertEquals(true, isTianGanHe)
        isTianGanHe = TianGanUtil().isTianGanHe(TianGan.TIANGAN_JI, TianGan.TIANGAN_JIA)
        assertEquals(true, isTianGanHe)
        isTianGanHe = TianGanUtil().isTianGanHe(TianGan.TIANGAN_JIA, TianGan.TIANGAN_GENG)
        assertEquals(false, isTianGanHe)
    }

    @Test
    fun testTianGanHe3() {
        var year = 1982
        var month = 7
        var day = 15
        var hour = 6
        var baziModel = BaziViewModel()
        var baziInfo = BaziInfo()
        var data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)

        val list = TianGanUtil().getTianGanHeList(data)
        assertEquals(2, list.size)

        assertEquals(TianGanPos.TIANGAN_YEAR, list.get(0).srcTgPos)
        assertEquals(TianGan.TIANGAN_REN, list.get(0).srcTg)
        assertEquals(TianGanPos.TIANGAN_MONTH, list.get(0).dstTgPos)
        assertEquals(TianGan.TIANGAN_DING, list.get(0).dstTg)

        assertEquals(TianGanPos.TIANGAN_YEAR, list.get(1).srcTgPos)
        assertEquals(TianGan.TIANGAN_REN, list.get(1).srcTg)
        assertEquals(TianGanPos.TIANGAN_HOUR, list.get(1).dstTgPos)
        assertEquals(TianGan.TIANGAN_DING, list.get(1).dstTg)
    }

    @Test
    fun testTianGanHe4() {
        var year = 1987
        var month = 12
        var day = 29
        var hour = 7
        var baziModel = BaziViewModel()
        var baziInfo = BaziInfo()
        var data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)

        val list = TianGanUtil().getTianGanHeList(data)
        assertEquals(2, list.size)

        assertEquals(TianGanPos.TIANGAN_YEAR, list.get(0).srcTgPos)
        assertEquals(TianGan.TIANGAN_DING, list.get(0).srcTg)
        assertEquals(TianGanPos.TIANGAN_MONTH, list.get(0).dstTgPos)
        assertEquals(TianGan.TIANGAN_REN, list.get(0).dstTg)

        assertEquals(TianGanPos.TIANGAN_YEAR, list.get(1).srcTgPos)
        assertEquals(TianGan.TIANGAN_DING, list.get(1).srcTg)
        assertEquals(TianGanPos.TIANGAN_DAY, list.get(1).dstTgPos)
        assertEquals(TianGan.TIANGAN_REN, list.get(1).dstTg)


        assertEquals(true,TianGanUtil().hasTianGanHeHua(data))
        assertEquals(true,TianGanUtil().isTianGanHeHua(data, list.get(0)))
        assertEquals(false,TianGanUtil().isTianGanHeHua(data, list.get(1)))
    }
}