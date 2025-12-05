package com.rick.bazi.util

import com.rick.bazi.data.BaziGeJu
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.ColumnPosition
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.DiZhiSanHeInfo
import com.rick.bazi.data.TongGuanYongShen
import com.rick.bazi.data.WuXing
import com.rick.bazi.ui.BaziViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class YongShenUtilTest {
    @Test
    fun testTiaohouCold() {
        var year = 1983
        var month = 12
        var day = 3
        var hour = 7
        var baziModel = BaziViewModel()
        var baziInfo = BaziInfo()
        var data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)

        YongShenUtil().calculateWuXingWeight(data)
        var isCold = YongShenUtil().isBaziCold(data)
        assertEquals(true, isCold)

    }

    @Test
    fun testTiaohouHot() {
        var year = 1966
        var month = 6
        var day = 8
        var hour = 11
        var baziModel = BaziViewModel()
        var baziInfo = BaziInfo()
        var data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)

        YongShenUtil().calculateWuXingWeight(data)
        var ret = YongShenUtil().isBaziHot(data)
        assertEquals(true, ret)
    }

    @Test
    fun testTiaohouDry() {
        var year = 1958
        var month = 10
        var day = 20
        var hour = 9
        var baziModel = BaziViewModel()
        var baziInfo = BaziInfo()
        var data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)

        YongShenUtil().calculateWuXingWeight(data)
        var ret = YongShenUtil().isBaziDry(data)
        assertEquals(true, ret)
    }

    @Test
    fun testTiaohouWet() {
        var year = 1983
        var month = 11
        var day = 18
        var hour = 6
        var baziModel = BaziViewModel()
        var baziInfo = BaziInfo()
        var data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male",baziModel, baziInfo)

        YongShenUtil().calculateWuXingWeight(data)
        var ret = YongShenUtil().isBaziWet(data)
        assertEquals(true, ret)
    }

    @Test
    fun test_jin_mu_fight() {
        var year = 2005
        var month = 9
        var day = 18
        var hour = 15
        var baziModel = BaziViewModel()
        var baziInfo = BaziInfo()
        var data = BaziPaiPanUtil().paipan(year, month, day, hour, "Male", baziModel, baziInfo)

        YongShenUtil().calculateWuXingWeight(data)
        assertEquals(TongGuanYongShen.TONG_GUAN_JIN_MU, data.tongguanYongShen)
    }
}