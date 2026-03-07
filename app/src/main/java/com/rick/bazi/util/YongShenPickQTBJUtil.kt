package com.rick.bazi.util

import com.rick.bazi.data.BaziData
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.ShiShen
import com.rick.bazi.data.TianGan

class YongShenPickQTBJUtil {

    fun calculateXiJiGui7(data: BaziData){
        val ysTG = TianGan.TIANGAN_DING
        val ys1 = ShiShenUtil().getShiShen(ysTG, data.dayTiangan)
        val xsTG1 = TianGan.TIANGAN_JIA
        val xs1 = ShiShenUtil().getShiShen(xsTG1, data.dayTiangan)
        val jsTG1 = TianGan.TIANGAN_GENG
        val js1 = ShiShenUtil().getShiShen(jsTG1, data.dayTiangan)

        data.yongShenList = listOf(ys1)
        data.xiShenList = listOf(xs1)
        data.jiShenList = listOf(js1)
    }

    fun checkXiJi(data: BaziData){
        if(data.yongShenPickMode == ConstUtil.YONGSHEN_PICKER_QTBJ){

            if(data.dayTiangan == TianGan.TIANGAN_GUI && data.monthDizhi == DiZhi.DIZHI_SHEN){
                calculateXiJiGui7(data)
            }

        }
    }
}