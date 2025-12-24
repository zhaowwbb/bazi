package com.rick.bazi.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rick.bazi.R
import com.rick.bazi.data.BaziData
import com.rick.bazi.data.TianGan

class ZhengYinUtil {
    @Composable
    fun getZhengYinDesc1(data: BaziData): String {
        //1
        if (ShiShenUtil().getZhengcai(data.dayTiangan) == data.yearTiangan || ShiShenUtil().getPiancai(
                data.dayTiangan
            ) == data.yearTiangan
        ) {
            if (ShiShenUtil().getZhengyin(data.dayTiangan) == data.monthTiangan || ShiShenUtil().getZhengyin(
                    data.dayTiangan
                ) == data.hourTiangan
            ) {
                return stringResource(R.string.app_bazi_gj_zhengyin_desc1)
            }
        }
        if (ShiShenUtil().getZhengcai(data.dayTiangan) == data.monthTiangan || ShiShenUtil().getPiancai(
                data.dayTiangan
            ) == data.monthTiangan
        ) {
            if (ShiShenUtil().getZhengyin(data.dayTiangan) == data.hourTiangan) {
                return stringResource(R.string.app_bazi_gj_zhengyin_desc1)
            }
        }
        return ""
    }

    @Composable
    fun getZhengYinDesc2(data: BaziData): String {
        //2
        if (ShiShenUtil().getZhengyin(data.dayTiangan) == data.yearTiangan) {
            if (ShiShenUtil().getZhengcai(data.dayTiangan) == data.monthTiangan || ShiShenUtil().getPiancai(
                    data.dayTiangan
                ) == data.monthTiangan
            ) {
                return stringResource(R.string.app_bazi_gj_zhengyin_desc2)
            }
            if (ShiShenUtil().getZhengcai(data.dayTiangan) == data.hourTiangan || ShiShenUtil().getPiancai(
                    data.dayTiangan
                ) == data.hourTiangan
            ) {
                return stringResource(R.string.app_bazi_gj_zhengyin_desc2)
            }
        }
        if (ShiShenUtil().getZhengyin(data.dayTiangan) == data.monthTiangan) {
            if (ShiShenUtil().getZhengcai(data.dayTiangan) == data.hourTiangan || ShiShenUtil().getPiancai(
                    data.dayTiangan
                ) == data.hourTiangan
            ) {
                return stringResource(R.string.app_bazi_gj_zhengyin_desc2)
            }
        }
        return ""
    }

    @Composable
    fun getZhengYinDesc3(data: BaziData): String {
        //3
        if (ShiShenUtil().getZhengyin(data.dayTiangan) == data.monthTiangan) {
            for (tg in DiZhiUtil().getCanggan(data.monthDizhi)) {
                if (ShiShenUtil().getZhengcai(data.dayTiangan) == tg) {
                    return stringResource(R.string.app_bazi_gj_zhengyin_desc3)
                }
            }
        }
        return ""
    }

    @Composable
    fun getZhengYinDesc4(data: BaziData): String {
        var tgArray: Array<TianGan> = arrayOf()
        //4
        if (ShiShenUtil().getZhengyin(data.dayTiangan) == data.yearTiangan) {
            tgArray = DiZhiUtil().getCanggan(data.yearDizhi)
            for (tg in tgArray) {
                if (ShiShenUtil().getZhengyin(data.dayTiangan) == tg) {
                    return stringResource(R.string.app_bazi_gj_zhengyin_desc4)
                }
            }
        }
        if (ShiShenUtil().getZhengyin(data.dayTiangan) == data.monthTiangan) {
            tgArray = DiZhiUtil().getCanggan(data.monthDizhi)
            for (tg in tgArray) {
                if (ShiShenUtil().getZhengyin(data.dayTiangan) == tg) {
                    return stringResource(R.string.app_bazi_gj_zhengyin_desc4)
                }
            }
        }
        if (ShiShenUtil().getZhengyin(data.dayTiangan) == data.hourTiangan) {
            tgArray = DiZhiUtil().getCanggan(data.hourDizhi)
            for (tg in tgArray) {
                if (ShiShenUtil().getZhengyin(data.dayTiangan) == tg) {
                    return stringResource(R.string.app_bazi_gj_zhengyin_desc4)
                }
            }
        }
        return ""
    }

    @Composable
    fun getZhengYinDesc5(data: BaziData): String {
        var tgArray: Array<TianGan> = arrayOf()
        //5
        if (ShiShenUtil().getZhengyin(data.dayTiangan) == data.yearTiangan) {
            tgArray = DiZhiUtil().getCanggan(data.yearDizhi)
            for (tg in tgArray) {
                if (ShiShenUtil().getPianyin(data.dayTiangan) == tg) {
                    return stringResource(R.string.app_bazi_gj_zhengyin_desc5)
                }
            }
        }
        if (ShiShenUtil().getZhengyin(data.dayTiangan) == data.monthTiangan) {
            tgArray = DiZhiUtil().getCanggan(data.monthDizhi)
            for (tg in tgArray) {
                if (ShiShenUtil().getPianyin(data.dayTiangan) == tg) {
                    return stringResource(R.string.app_bazi_gj_zhengyin_desc5)
                }
            }
        }
        if (ShiShenUtil().getZhengyin(data.dayTiangan) == data.hourTiangan) {
            tgArray = DiZhiUtil().getCanggan(data.hourDizhi)
            for (tg in tgArray) {
                if (ShiShenUtil().getPianyin(data.dayTiangan) == tg) {
                    return stringResource(R.string.app_bazi_gj_zhengyin_desc5)
                }
            }
        }
        return ""
    }

    @Composable
    fun getZhengYinDesc6(data: BaziData): String {
        var count = 0
        var zhengYinTg = ShiShenUtil().getZhengyin(data.dayTiangan)
        if(zhengYinTg == data.yearTiangan)count+=1
        if(zhengYinTg == data.monthTiangan)count+=1
        if(zhengYinTg == data.hourTiangan)count+=1

        for (tg in DiZhiUtil().getCanggan(data.yearDizhi)) {
            if (zhengYinTg == tg)count+=1
        }
        for (tg in DiZhiUtil().getCanggan(data.monthDizhi)) {
            if (zhengYinTg == tg)count+=1
        }
        for (tg in DiZhiUtil().getCanggan(data.dayDizhi)) {
            if (zhengYinTg == tg)count+=1
        }
        for (tg in DiZhiUtil().getCanggan(data.hourDizhi)) {
            if (zhengYinTg == tg)count+=1
        }
        if(count > 1){
            return stringResource(R.string.app_bazi_gj_zhengyin_desc6)
        }
        return ""
    }
}