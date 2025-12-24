package com.rick.bazi.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rick.bazi.R
import com.rick.bazi.data.BaziData
import com.rick.bazi.data.ShiShen

class ZhengCaiUtil {
    @Composable
    fun getZhengCaiDesc1(data: BaziData): String {
        var sb = StringBuilder()
        var zhengCaiTG = ShiShenUtil().getZhengcai(data.dayTiangan)
        var isYearZhengCai = false
        for (tg in DiZhiUtil().getCanggan(data.yearDizhi)) {
            if (tg == zhengCaiTG) {
                isYearZhengCai = true
                break
            }
        }
        if (!isYearZhengCai) {
            if (zhengCaiTG == data.yearTiangan) {
                isYearZhengCai = true

            }
        }
        if (isYearZhengCai) {
            if (GeJuUtil().isXiYongShen(data, ShiShen.SHISHEN_ZHENG_CAI)) {
                sb.append(stringResource(R.string.app_bazi_gj_zhengcai_desc1))
//                sb.append(" ")…
            }
        }
        return sb.toString()
    }

    @Composable
    fun getZhengCaiDesc2(data: BaziData): String {
        var count = 0
        var zhengCaiTG = ShiShenUtil().getZhengcai(data.dayTiangan)
        var pianCaiTG = ShiShenUtil().getPiancai(data.dayTiangan)

        if(zhengCaiTG == data.yearTiangan || pianCaiTG == data.yearTiangan)count+=1
        if(zhengCaiTG == data.monthTiangan || pianCaiTG == data.monthTiangan)count+=1
        if(zhengCaiTG == data.hourTiangan || pianCaiTG == data.hourTiangan)count+=1

        for (tg in DiZhiUtil().getCanggan(data.yearDizhi)) {
            if (zhengCaiTG == tg || pianCaiTG == tg)count+=1
        }
        for (tg in DiZhiUtil().getCanggan(data.monthDizhi)) {
            if (zhengCaiTG == tg || pianCaiTG == tg)count+=1
        }
        for (tg in DiZhiUtil().getCanggan(data.dayDizhi)) {
            if (zhengCaiTG == tg || pianCaiTG == tg)count+=1
        }
        for (tg in DiZhiUtil().getCanggan(data.hourDizhi)) {
            if (zhengCaiTG == tg || pianCaiTG == tg)count+=1
        }
        if(count == 0){
            return stringResource(R.string.app_bazi_gj_zhengcai_desc2)
        }
        return ""
    }

    @Composable
    fun getZhengCaiDesc3(data: BaziData): String {
        var count = 0
        var zhengCaiTG = ShiShenUtil().getZhengcai(data.dayTiangan)
//        var pianCaiTG = ShiShenUtil().getPiancai(data.dayTiangan)

        if(zhengCaiTG == data.yearTiangan)count+=1
        if(zhengCaiTG == data.monthTiangan)count+=1
        if(zhengCaiTG == data.hourTiangan)count+=1

        for (tg in DiZhiUtil().getCanggan(data.yearDizhi)) {
            if (zhengCaiTG == tg)count+=1
        }
        for (tg in DiZhiUtil().getCanggan(data.monthDizhi)) {
            if (zhengCaiTG == tg)count+=1
        }
        for (tg in DiZhiUtil().getCanggan(data.dayDizhi)) {
            if (zhengCaiTG == tg)count+=1
        }
        for (tg in DiZhiUtil().getCanggan(data.hourDizhi)) {
            if (zhengCaiTG == tg)count+=1
        }
        if(count >= 3){
            return stringResource(R.string.app_bazi_gj_zhengcai_desc3)
        }
        return ""
    }
}