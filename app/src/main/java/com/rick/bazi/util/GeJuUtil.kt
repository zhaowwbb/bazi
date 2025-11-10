package com.rick.bazi.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rick.bazi.R
import com.rick.bazi.data.BaziGeJu
import com.rick.bazi.data.ShiShen

class GeJuUtil {
    @Composable
    fun getGJByShiShen(ss : ShiShen) : BaziGeJu {
        var gj = BaziGeJu.GJ_NONE
        if(ss == ShiShen.SHISHEN_ZHENG_GUAN){
            return BaziGeJu.GJ_ZHENG_GUAN
        }
        if(ss == ShiShen.SHISHEN_QI_SHA){
            return BaziGeJu.GJ_QI_SHA
        }
        if(ss == ShiShen.SHISHEN_SHI_SHEN){
            return BaziGeJu.GJ_SHI_SHEN
        }
        if(ss == ShiShen.SHISHEN_SHANG_GUAN){
            return BaziGeJu.GJ_SHANG_GUAN
        }
        if(ss == ShiShen.SHISHEN_ZHENG_YIN){
            return BaziGeJu.GJ_ZHENG_YIN
        }
        if(ss == ShiShen.SHISHEN_PIAN_YIN){
            return BaziGeJu.GJ_PIAN_YIN
        }
        if(ss == ShiShen.SHISHEN_ZHENG_CAI){
            return BaziGeJu.GJ_ZHENG_CAI
        }
        if(ss == ShiShen.SHISHEN_PIAN_CAI){
            return BaziGeJu.GJ_PIAN_CAI
        }
        if(ss == ShiShen.SHISHEN_BI_JIAN || ss == ShiShen.SHISHEN_JIE_CAI){
            return BaziGeJu.GJ_JIAN_LU
        }

        return gj
    }

    @Composable
    fun getGJText(gj : BaziGeJu) : String {
        var str = ""
        if(gj == BaziGeJu.GJ_NONE){
            str = stringResource(R.string.app_bazi_gj_no_label)
        }else if(gj == BaziGeJu.GJ_ZHENG_GUAN){
            str = stringResource(R.string.app_bazi_gj_zhengguan)
        }else if(gj == BaziGeJu.GJ_QI_SHA){
            str = stringResource(R.string.app_bazi_gj_qisha)
        }else if(gj == BaziGeJu.GJ_SHI_SHEN){
            str = stringResource(R.string.app_bazi_gj_shishen)
        }else if(gj == BaziGeJu.GJ_SHANG_GUAN){
            str = stringResource(R.string.app_bazi_gj_shangguan)
        }else if(gj == BaziGeJu.GJ_ZHENG_YIN){
            str = stringResource(R.string.app_bazi_gj_zhengyin)
        }else if(gj == BaziGeJu.GJ_PIAN_YIN){
            str = stringResource(R.string.app_bazi_gj_pianyin)
        }else if(gj == BaziGeJu.GJ_ZHENG_CAI){
            str = stringResource(R.string.app_bazi_gj_zhengcai)
        }else if(gj == BaziGeJu.GJ_PIAN_CAI){
            str = stringResource(R.string.app_bazi_gj_piancai)
        }else if(gj == BaziGeJu.GJ_JIAN_LU){
            str = stringResource(R.string.app_bazi_gj_jianlu)
        }

        return str
    }

    @Composable
    fun getGJSummary(gj : BaziGeJu) : String {
        var str = ""
        if(gj == BaziGeJu.GJ_NONE){
            str = stringResource(R.string.app_bazi_gj_no_label)
        }else if(gj == BaziGeJu.GJ_ZHENG_GUAN){
            str = stringResource(R.string.app_bazi_gj_zhengguan_desc)
        }else if(gj == BaziGeJu.GJ_QI_SHA){
            str = stringResource(R.string.app_bazi_gj_qisha_desc)
        }else if(gj == BaziGeJu.GJ_SHI_SHEN){
            str = stringResource(R.string.app_bazi_gj_shishen_desc)
        }else if(gj == BaziGeJu.GJ_SHANG_GUAN){
            str = stringResource(R.string.app_bazi_gj_shangguan_desc)
        }else if(gj == BaziGeJu.GJ_ZHENG_YIN){
            str = stringResource(R.string.app_bazi_gj_zhengyin_desc)
        }else if(gj == BaziGeJu.GJ_PIAN_YIN){
            str = stringResource(R.string.app_bazi_gj_pianyin_desc)
        }else if(gj == BaziGeJu.GJ_ZHENG_CAI){
            str = stringResource(R.string.app_bazi_gj_zhengcai_desc)
        }else if(gj == BaziGeJu.GJ_PIAN_CAI){
            str = stringResource(R.string.app_bazi_gj_piancai_desc)
        }else if(gj == BaziGeJu.GJ_JIAN_LU){
            str = stringResource(R.string.app_bazi_gj_jianlu_desc)
        }

        return str
    }
}