package com.rick.bazi.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rick.bazi.R
import com.rick.bazi.data.BaziData
import com.rick.bazi.data.BaziGeJu
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.ShiShen
import com.rick.bazi.data.TianGan
import com.rick.bazi.data.WuXing
import com.rick.bazi.ui.BaziViewModel

class GeJuUtil {

    fun getGJByShiShen(ss: ShiShen): BaziGeJu {
        var gj = BaziGeJu.GJ_NONE
        if (ss == ShiShen.SHISHEN_ZHENG_GUAN) {
            return BaziGeJu.GJ_ZHENG_GUAN
        }
        if (ss == ShiShen.SHISHEN_QI_SHA) {
            return BaziGeJu.GJ_QI_SHA
        }
        if (ss == ShiShen.SHISHEN_SHI_SHEN) {
            return BaziGeJu.GJ_SHI_SHEN
        }
        if (ss == ShiShen.SHISHEN_SHANG_GUAN) {
            return BaziGeJu.GJ_SHANG_GUAN
        }
        if (ss == ShiShen.SHISHEN_ZHENG_YIN) {
            return BaziGeJu.GJ_ZHENG_YIN
        }
        if (ss == ShiShen.SHISHEN_PIAN_YIN) {
            return BaziGeJu.GJ_PIAN_YIN
        }
        if (ss == ShiShen.SHISHEN_ZHENG_CAI) {
            return BaziGeJu.GJ_ZHENG_CAI
        }
        if (ss == ShiShen.SHISHEN_PIAN_CAI) {
            return BaziGeJu.GJ_PIAN_CAI
        }
        if (ss == ShiShen.SHISHEN_BI_JIAN) {
            return BaziGeJu.GJ_JIAN_LU
        }
        if (ss == ShiShen.SHISHEN_JIE_CAI) {
            //todo, yangren?

            return BaziGeJu.GJ_NONE
        }
        return gj
    }

    @Composable
    fun getGJText(gj: BaziGeJu): String {
        var str = ""
        if (gj == BaziGeJu.GJ_NONE) {
            str = stringResource(R.string.app_bazi_gj_no_label)
        } else if (gj == BaziGeJu.GJ_ZHENG_GUAN) {
            str = stringResource(R.string.app_bazi_gj_zhengguan)
        } else if (gj == BaziGeJu.GJ_QI_SHA) {
            str = stringResource(R.string.app_bazi_gj_qisha)
        } else if (gj == BaziGeJu.GJ_SHI_SHEN) {
            str = stringResource(R.string.app_bazi_gj_shishen)
        } else if (gj == BaziGeJu.GJ_SHANG_GUAN) {
            str = stringResource(R.string.app_bazi_gj_shangguan)
        } else if (gj == BaziGeJu.GJ_ZHENG_YIN) {
            str = stringResource(R.string.app_bazi_gj_zhengyin)
        } else if (gj == BaziGeJu.GJ_PIAN_YIN) {
            str = stringResource(R.string.app_bazi_gj_pianyin)
        } else if (gj == BaziGeJu.GJ_ZHENG_CAI) {
            str = stringResource(R.string.app_bazi_gj_zhengcai)
        } else if (gj == BaziGeJu.GJ_PIAN_CAI) {
            str = stringResource(R.string.app_bazi_gj_piancai)
        } else if (gj == BaziGeJu.GJ_JIAN_LU) {
            str = stringResource(R.string.app_bazi_gj_jianlu)
        } else if (gj == BaziGeJu.GJ_QU_ZHI) {
            str = stringResource(R.string.app_bazi_gj_quzhi)
        } else if (gj == BaziGeJu.GJ_YAN_SHANG) {
            str = stringResource(R.string.app_bazi_gj_yanshang)
        } else if (gj == BaziGeJu.GJ_RUN_XIA) {
            str = stringResource(R.string.app_bazi_gj_runxia)
        } else if (gj == BaziGeJu.GJ_JIA_SE) {
            str = stringResource(R.string.app_bazi_gj_jiase)
        } else if (gj == BaziGeJu.GJ_CONG_GE) {
            str = stringResource(R.string.app_bazi_gj_congge)
        } else if (gj == BaziGeJu.GJ_YANG_REN) {
            str = stringResource(R.string.app_bazi_gj_yangren)
        } else if (gj == BaziGeJu.GJ_CONG_CAI) {
            str = stringResource(R.string.app_bazi_gj_congcai)
        } else if (gj == BaziGeJu.GJ_CONG_SHA) {
            str = stringResource(R.string.app_bazi_gj_congsha)
        } else if (gj == BaziGeJu.GJ_CONG_ER) {
            str = stringResource(R.string.app_bazi_gj_conger)
        }

        return str
    }

    @Composable
    fun getGJSummary(gj: BaziGeJu): String {
        var str = ""
        if (gj == BaziGeJu.GJ_NONE) {
            str = stringResource(R.string.app_bazi_gj_no_label)
        } else if (gj == BaziGeJu.GJ_ZHENG_GUAN) {
            str = stringResource(R.string.app_bazi_gj_zhengguan_desc)
        } else if (gj == BaziGeJu.GJ_QI_SHA) {
            str = stringResource(R.string.app_bazi_gj_qisha_desc)
        } else if (gj == BaziGeJu.GJ_SHI_SHEN) {
            str = stringResource(R.string.app_bazi_gj_shishen_desc)
        } else if (gj == BaziGeJu.GJ_SHANG_GUAN) {
            str = stringResource(R.string.app_bazi_gj_shangguan_desc)
        } else if (gj == BaziGeJu.GJ_ZHENG_YIN) {
            str = stringResource(R.string.app_bazi_gj_zhengyin_desc)
        } else if (gj == BaziGeJu.GJ_PIAN_YIN) {
            str = stringResource(R.string.app_bazi_gj_pianyin_desc)
        } else if (gj == BaziGeJu.GJ_ZHENG_CAI) {
            str = stringResource(R.string.app_bazi_gj_zhengcai_desc)
        } else if (gj == BaziGeJu.GJ_PIAN_CAI) {
            str = stringResource(R.string.app_bazi_gj_piancai_desc)
        } else if (gj == BaziGeJu.GJ_JIAN_LU) {
            str = stringResource(R.string.app_bazi_gj_jianlu_desc)
        }  else if (gj == BaziGeJu.GJ_QU_ZHI) {
            str = stringResource(R.string.app_bazi_gj_quzhi_desc)
        } else if (gj == BaziGeJu.GJ_YAN_SHANG) {
            str = stringResource(R.string.app_bazi_gj_yanshang_desc)
        }else if (gj == BaziGeJu.GJ_RUN_XIA) {
            str = stringResource(R.string.app_bazi_gj_runxia_desc)
        }else if (gj == BaziGeJu.GJ_JIA_SE) {
            str = stringResource(R.string.app_bazi_gj_jiase_desc)
        }else if (gj == BaziGeJu.GJ_CONG_GE) {
            str = stringResource(R.string.app_bazi_gj_congge_desc)
        }else if (gj == BaziGeJu.GJ_YANG_REN) {
            str = stringResource(R.string.app_bazi_gj_yangren_desc)
        }else if (gj == BaziGeJu.GJ_CONG_CAI) {
            str = stringResource(R.string.app_bazi_gj_congcai_desc)
        }else if (gj == BaziGeJu.GJ_CONG_SHA) {
            str = stringResource(R.string.app_bazi_gj_congsha_desc)
        }else if (gj == BaziGeJu.GJ_CONG_ER) {
            str = stringResource(R.string.app_bazi_gj_conger_desc)
        }
        else {
            str = "todo"
        }

        return str
    }

    @Composable
    fun isRootTouTianGan(baziInfo: BaziInfo, wx: WuXing): Boolean {
        var ret = false
        var isTianGanExist = false
        var isDiZhiExist = false
        if (WuXingUtil().getTianGanWuxing(baziInfo.yearTiangan) == wx || WuXingUtil().getTianGanWuxing(
                baziInfo.monthTiangan
            ) == wx || WuXingUtil().getTianGanWuxing(baziInfo.dayTiangan) == wx || WuXingUtil().getTianGanWuxing(
                baziInfo.hourTiangan
            ) == wx
        ) {
            isTianGanExist = true
        }

        if (WuXingUtil().getDiZhiWuxing(baziInfo.yearDizhi) == wx || WuXingUtil().getDiZhiWuxing(
                baziInfo.monthDizhi
            ) == wx || WuXingUtil().getDiZhiWuxing(baziInfo.dayDizhi) == wx || WuXingUtil().getDiZhiWuxing(
                baziInfo.hourDizhi
            ) == wx
        ) {
            isDiZhiExist = true
        }

        if (isTianGanExist && isDiZhiExist) {
            ret = true
        }

        return ret
    }

    fun isTongGen(tg: TianGan, baziInfo: BaziData): Boolean {
        var ret = false
        var wx = WuXingUtil().getTgWX(tg)
        if (wx == WuXingUtil().getTgWX(baziInfo.yearTiangan)) return true
        if (wx == WuXingUtil().getTgWX(baziInfo.monthTiangan)) return true
        if (wx == WuXingUtil().getTgWX(baziInfo.dayTiangan)) return true
        if (wx == WuXingUtil().getTgWX(baziInfo.hourTiangan)) return true
        return ret
    }

    fun isTouChu(tg: TianGan, baziInfo: BaziData): Boolean {
        var ret = false
        if (tg == baziInfo.yearTiangan) return true
        if (tg == baziInfo.monthTiangan) return true
        if (tg == baziInfo.dayTiangan) return true
        if (tg == baziInfo.hourTiangan) return true
        return ret
    }

    fun isQuZhiGJ(data: BaziData): Boolean {
        var ret = false
        if (data.dayTiangan == TianGan.TIANGAN_JIA || data.dayTiangan == TianGan.TIANGAN_YI) {
            if(WuXingUtil().getJinNumber(data) == 0 && data.muWeight >=7f){
                ret = true
            }
        }
        return ret
    }

    fun isYanShangGJ(data: BaziData): Boolean {
        var ret = false
        if (data.dayTiangan == TianGan.TIANGAN_BING || data.dayTiangan == TianGan.TIANGAN_DING) {
            if(WuXingUtil().getShuiNumber(data) == 0 && data.huoWeight >=7f){
                ret = true
            }
        }
        return ret
    }

    fun isRunXiaGJ(data: BaziData): Boolean {
        var ret = false
        if (data.dayTiangan == TianGan.TIANGAN_REN || data.dayTiangan == TianGan.TIANGAN_GUI) {
            if(data.monthDizhi == DiZhi.DIZHI_HAI || data.monthDizhi == DiZhi.DIZHI_ZI){
                if(WuXingUtil().getHuoNumber(data) == 0 && WuXingUtil().getTuNumber(data) == 0 && data.shuiWeight >=5f){
                    ret = true
                }
            }
        }
        return ret
    }

    fun isJiaSeGJ(data: BaziData): Boolean {
        var ret = false
        if (data.dayTiangan == TianGan.TIANGAN_WU || data.dayTiangan == TianGan.TIANGAN_JI) {
            if(WuXingUtil().getMuNumber(data) == 0 && data.tuWeight >=7f){
                ret = true
            }
        }
        return ret
    }

    fun isCongGeGJ(data: BaziData): Boolean {
        var ret = false
        if (data.dayTiangan == TianGan.TIANGAN_GENG || data.dayTiangan == TianGan.TIANGAN_XIN) {
            if(WuXingUtil().getHuoNumber(data) == 0 && data.jinWeight >=6f){
                ret = true
            }
        }
        return ret
    }

    fun isYangRenGJ(data: BaziData): Boolean {
        var ret = false
        var weight = 0f
        var dayTg = data.dayTiangan
        var yangrenDz = BaziUtil().getYangren(dayTg)
        var wx = WuXingUtil().getTgWX(dayTg)
        if(yangrenDz == data.monthDizhi){
            ret = true
        }
        return ret
    }

    fun isJianLuGJ(data: BaziData): Boolean {
        var ret = false
        var luDz = TianGanUtil().getTianGanLu(data.dayTiangan)
        if(luDz == data.monthDizhi){
            ret = true
        }
        return ret
    }

    fun getBaziWuXingWeight(data: BaziData, wx : WuXing): Float{
        var weight = 0f
        if(wx == WuXing.WUXING_SHUI){
            weight = data.shuiWeight
        }else if(wx == WuXing.WUXING_MU){
            weight = data.muWeight
        }else if(wx == WuXing.WUXING_TU){
            weight = data.tuWeight
        }else if(wx == WuXing.WUXING_HUO){
            weight = data.huoWeight
        }else if(wx == WuXing.WUXING_JIN){
            weight = data.jinWeight
        }
        return weight
    }

    fun isCaiWeightStrong(data: BaziData): Boolean {
        var ret = false
        val caiWeight = getBaziWuXingWeight(data, WuXingUtil().getCaiWuXing(data.dayTiangan))
        val guanshaWeight = getBaziWuXingWeight(data, WuXingUtil().getGuanshaWuXing(data.dayTiangan))
        val shishangWeight = getBaziWuXingWeight(data, WuXingUtil().getShishangWuXing(data.dayTiangan))
        if(caiWeight > guanshaWeight && caiWeight > shishangWeight){
            ret = true
        }
        return ret
    }

    fun isShaWeightStrong(data: BaziData): Boolean {
        var ret = false
        val caiWeight = getBaziWuXingWeight(data, WuXingUtil().getCaiWuXing(data.dayTiangan))
        val guanshaWeight = getBaziWuXingWeight(data, WuXingUtil().getGuanshaWuXing(data.dayTiangan))
        val shishangWeight = getBaziWuXingWeight(data, WuXingUtil().getShishangWuXing(data.dayTiangan))
        if(guanshaWeight > caiWeight && guanshaWeight > shishangWeight){
            ret = true
        }
        return ret
    }

    fun isCongCaiGJ(data: BaziData): Boolean {
        var ret = false
        var yinWeight = getBaziWuXingWeight(data, WuXingUtil().getYinWuXing(data.dayTiangan))
        var bijieWeight = getBaziWuXingWeight(data, WuXingUtil().getBiJieWuXing(data.dayTiangan))

        if(yinWeight <= 2f && bijieWeight <= 2f){
            if(isCaiWeightStrong(data)){
                ret = true
            }
        }
        return ret
    }

    fun isCongShaGJ(data: BaziData): Boolean {
        var ret = false
        var yinWeight = getBaziWuXingWeight(data, WuXingUtil().getYinWuXing(data.dayTiangan))
        var bijieWeight = getBaziWuXingWeight(data, WuXingUtil().getBiJieWuXing(data.dayTiangan))

        if(yinWeight <= 2f && bijieWeight <= 2f){
            if(isShaWeightStrong(data)){
                ret = true
            }
        }
        return ret
    }

    fun isCongErGJ(data: BaziData): Boolean {
        var ret = false
        var yinWeight = getBaziWuXingWeight(data, WuXingUtil().getYinWuXing(data.dayTiangan))
        var bijieWeight = getBaziWuXingWeight(data, WuXingUtil().getBiJieWuXing(data.dayTiangan))
        var yinCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_ZHENG_YIN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_YIN)
        var caiCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_ZHENG_CAI) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_CAI)
        var monthDzShiShen = ShiShenUtil().getDiZhiShiShen(data.monthDizhi, data.dayTiangan)
        var shishangWeight = getBaziWuXingWeight(data, WuXingUtil().getShishangWuXing(data.dayTiangan))

        if(monthDzShiShen == ShiShen.SHISHEN_SHI_SHEN ||monthDzShiShen == ShiShen.SHISHEN_SHANG_GUAN){
            if(yinCount == 0 && caiCount > 0 ){
                if(shishangWeight > 3f){
                    ret = true
                }
            }
        }

        return ret
    }

    fun isZhengYinGJ(data: BaziData): Boolean {
        var ret = false
        var zhengYinTG = ShiShenUtil().getZhengyin(data.dayTiangan)
        var cangganArray = DiZhiUtil().getCanggan(data.monthDizhi)
        if(zhengYinTG == cangganArray[0]){
            ret = true
        }else{
            if(zhengYinTG == data.yearTiangan || zhengYinTG == data.monthTiangan || zhengYinTG == data.hourTiangan){
                //check year
                cangganArray = DiZhiUtil().getCanggan(data.yearDizhi)
                for( tg in cangganArray){
                    if(tg == zhengYinTG){
                        ret = true
                        break
                    }
                }
                if(!ret){
                    //check month
                    cangganArray = DiZhiUtil().getCanggan(data.monthDizhi)
                    for( tg in cangganArray){
                        if(tg == zhengYinTG){
                            ret = true
                            break
                        }
                    }
                }
                if(!ret){
                    //check day
                    cangganArray = DiZhiUtil().getCanggan(data.dayDizhi)
                    for( tg in cangganArray){
                        if(tg == zhengYinTG){
                            ret = true
                            break
                        }
                    }
                }
                if(!ret){
                    //check hour
                    cangganArray = DiZhiUtil().getCanggan(data.hourDizhi)
                    for( tg in cangganArray){
                        if(tg == zhengYinTG){
                            ret = true
                            break
                        }
                    }
                }
            }
        }
        return ret
    }

    fun isMonthDzTouTiangan(tg : TianGan, data : BaziData) : Boolean{
        if(tg == data.yearTiangan)return true
        if(tg == data.monthTiangan)return true
        if(tg == data.hourTiangan)return true
        return false
    }

    fun getGJ(baziData: BaziData): BaziGeJu {
        var gj = BaziGeJu.GJ_NONE
        var gjTG = TianGan.TIANGAN_JIA
        var foundGJ = false
        var tg = TianGan.TIANGAN_JIA
        var monthDz = baziData.monthDizhi
        var ss = ShiShen.SHISHEN_SHI_SHEN
        YongShenUtil().calculateWuXingWeight(baziData)

        if (isYanShangGJ(baziData)) {
            gj = BaziGeJu.GJ_YAN_SHANG
            baziData.gj = gj
            return gj
        }
        if (isQuZhiGJ(baziData)) {
            gj = BaziGeJu.GJ_QU_ZHI
            baziData.gj = gj
            return gj
        }
        if (isRunXiaGJ(baziData)) {
            gj = BaziGeJu.GJ_RUN_XIA
            baziData.gj = gj
            return gj
        }
        if (isJiaSeGJ(baziData)) {
            gj = BaziGeJu.GJ_JIA_SE
            baziData.gj = gj
            return gj
        }
        if (isCongGeGJ(baziData)) {
            gj = BaziGeJu.GJ_CONG_GE
            baziData.gj = gj
            return gj
        }

        if (isYangRenGJ(baziData)) {
            gj = BaziGeJu.GJ_YANG_REN
            baziData.gj = gj
            return gj
        }
        if (isJianLuGJ(baziData)) {
            gj = BaziGeJu.GJ_JIAN_LU
            baziData.gj = gj
            return gj
        }
        if (isCongCaiGJ(baziData)) {
            gj = BaziGeJu.GJ_CONG_CAI
            baziData.gj = gj
            return gj
        }
        if (isCongShaGJ(baziData)) {
            gj = BaziGeJu.GJ_CONG_SHA
            baziData.gj = gj
            return gj
        }
        if (isCongErGJ(baziData)) {
            gj = BaziGeJu.GJ_CONG_ER
            baziData.gj = gj
            return gj
        }

        //normal GeJu
//        if (isZhengYinGJ(baziData)) {
//            gj = BaziGeJu.GJ_ZHENG_YIN
//            baziData.gj = gj
//            return gj
//        }

        var cangganArray = DiZhiUtil().diZhiCangGanMap[monthDz]!!

        //benqi touchu
        if(isMonthDzTouTiangan(cangganArray[0], baziData)){
            gjTG = cangganArray[0]
            foundGJ = true
        }
        //zhongqi touchu
        if (!foundGJ) {
            if (cangganArray.size == 2) {
                if (isMonthDzTouTiangan(cangganArray[1], baziData)) {
                    gjTG = cangganArray[1]
                    foundGJ = true
                }
            }
        }
        //yuqi touchu
        if (!foundGJ) {
            if (cangganArray.size == 3) {
                if (isMonthDzTouTiangan(cangganArray[2], baziData)) {
                    gjTG = cangganArray[2]
                    foundGJ = true
                }
            }
        }
        //no touchu
        if (!foundGJ) {
            println("Month DiZhi is not touchu TianGan")
            gjTG = cangganArray[0]
            foundGJ = true
        }

//        if (monthDz == DiZhi.DIZHI_ZI || monthDz == DiZhi.DIZHI_MOU || monthDz == DiZhi.DIZHI_YOU) {
//            gjTG = cangganArray[0]
//            foundGJ = true
//        }
//        if (!foundGJ) {
//            for (tg in cangganArray) {
//                if (isTouChu(tg, baziData)) {
//                    gjTG = tg
//                    println("Found tou chu gj TianGan: gjTG=$gjTG")
//                    foundGJ = true
//                    break
//                }
//            }
//        }
//        if (!foundGJ) {
//            gjTG = cangganArray[0]
//            foundGJ = true
//        }

        ss = ShiShenUtil().getShiShen(gjTG, baziData.dayTiangan)
        gj = GeJuUtil().getGJByShiShen(ss)

//        println("Found gj=$gj")
        baziData.gj = gj
        return gj
    }

    @Composable
    fun isCongShaGJ(baziInfo: BaziInfo, baziModel: BaziViewModel): Boolean {
        //1970年10月21日 巳时
        //八字: 庚戌 丁亥 己巳 丁巳
        var ret = false
        var gjString = ""
        var gjSummary = ""
        var gj = BaziGeJu.GJ_NONE
        if (baziInfo.isDangLing) return false
        //
        if (baziInfo.strongRootCount > 0 || baziInfo.mediumRootCount > 0 || baziInfo.weakRootCount > 1) return false

        //only Yin TianGan with weak root is CongSha
        if (baziInfo.weakRootCount == 1 && BaziUtil().isYangTianGan(baziInfo.dayTiangan)) return false

        if (baziInfo.guanshaCount >= 2 && (baziInfo.guanshaCount + baziInfo.caiCount) >= 4 && baziInfo.shishangCount == 0) {
            if (isRootTouTianGan(baziInfo, WuXingUtil().getGuanshaWuXing(baziInfo.dayTiangan))) {
                if (!isRootTouTianGan(baziInfo, WuXingUtil().getYinWuXing(baziInfo.dayTiangan))) {
                    ret = true
                    gj = BaziGeJu.GJ_CONG_SHA
                    gjSummary = stringResource(R.string.app_bazi_gj_no_label)
                    gjString = stringResource(R.string.app_bazi_gj_no_label)
                } else {
                    return false
                }
            } else {
                return false
            }

        } else {
            return false
        }

        baziModel.setBaziGJ(gj)
        baziModel.setBaziGJSummary(gjSummary)
        baziModel.setBaziGJString(gjString)
        return ret
    }

    @Composable
    fun getJobDescription(gj: BaziGeJu): String {
        var str = ""
        if (gj == BaziGeJu.GJ_ZHENG_YIN) {
            str = stringResource(R.string.app_bazi_gj_job_zhengyin)
        } else if (gj == BaziGeJu.GJ_PIAN_YIN) {
            str = stringResource(R.string.app_bazi_gj_job_pianyin)
        } else if (gj == BaziGeJu.GJ_SHI_SHEN){
            str = stringResource(R.string.app_bazi_gj_job_shishen)
        } else if (gj == BaziGeJu.GJ_SHANG_GUAN) {
            str = stringResource(R.string.app_bazi_gj_job_shangguan)
        } else if (gj == BaziGeJu.GJ_ZHENG_GUAN){
            str = stringResource(R.string.app_bazi_gj_job_zhengguan)
        } else if (gj == BaziGeJu.GJ_QI_SHA) {
            str = stringResource(R.string.app_bazi_gj_job_qisha)
        } else if (gj == BaziGeJu.GJ_ZHENG_CAI){
            str = stringResource(R.string.app_bazi_gj_job_zhengcai)
        } else if (gj == BaziGeJu.GJ_PIAN_CAI) {
            str = stringResource(R.string.app_bazi_gj_job_piancai)
        } else if (gj == BaziGeJu.GJ_JIAN_LU) {
            str = stringResource(R.string.app_bazi_gj_job_jianlu)
        } else if (gj == BaziGeJu.GJ_QU_ZHI) {
            str = stringResource(R.string.app_bazi_gj_job_quzhi)
        }else if (gj == BaziGeJu.GJ_YAN_SHANG) {
            str = stringResource(R.string.app_bazi_gj_job_yanshang)
        }else if (gj == BaziGeJu.GJ_RUN_XIA) {
            str = stringResource(R.string.app_bazi_gj_job_runxia)
        }else if (gj == BaziGeJu.GJ_JIA_SE) {
            str = stringResource(R.string.app_bazi_gj_job_jiase)
        }else if (gj == BaziGeJu.GJ_CONG_GE) {
            str = stringResource(R.string.app_bazi_gj_job_congge)
        }else if (gj == BaziGeJu.GJ_YANG_REN) {
            str = stringResource(R.string.app_bazi_gj_job_yangren)
        }else if (gj == BaziGeJu.GJ_CONG_CAI) {
            str = stringResource(R.string.app_bazi_gj_job_congcai)
        }else if (gj == BaziGeJu.GJ_CONG_SHA) {
            str = stringResource(R.string.app_bazi_gj_job_congsha)
        }else if (gj == BaziGeJu.GJ_CONG_ER) {
            str = stringResource(R.string.app_bazi_gj_job_conger)
        } else {
            str = stringResource(R.string.app_bazi_gj_job_tbd)
        }
        return str
    }

    fun isYinStrong(data: BaziData): Boolean {
        var ret = false
        var yinCount = 0
        var biJieCount = 0

        yinCount = ShiShenUtil().getShiShenCount(
            data, ShiShen.SHISHEN_ZHENG_YIN
        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_YIN)
        biJieCount = ShiShenUtil().getShiShenCount(
            data, ShiShen.SHISHEN_BI_JIAN
        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_JIE_CAI)

        if (yinCount >= biJieCount) {
            ret = true
        }
        println("isYinStrong=$ret")
        return ret
    }

    fun isBiJieStrong(data: BaziData): Boolean {
        var ret = false
        var yinCount = 0
        var biJieCount = 0

        yinCount = ShiShenUtil().getShiShenCount(
            data, ShiShen.SHISHEN_ZHENG_YIN
        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_YIN)
        biJieCount = ShiShenUtil().getShiShenCount(
            data, ShiShen.SHISHEN_BI_JIAN
        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_JIE_CAI)

        if (biJieCount >= yinCount) {
            ret = true
        }
        println("isBiJieStrong=$ret")
        return ret
    }

    fun isGuanShaStrong(data: BaziData): Boolean {
        var ret = false
        var shiShangCount = 0
        var guanShaCount = 0
        var caiCount = 0

        shiShangCount = ShiShenUtil().getShiShenCount(
            data, ShiShen.SHISHEN_SHI_SHEN
        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_SHANG_GUAN)
        guanShaCount = ShiShenUtil().getShiShenCount(
            data, ShiShen.SHISHEN_ZHENG_GUAN
        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_QI_SHA)
        caiCount = ShiShenUtil().getShiShenCount(
            data, ShiShen.SHISHEN_ZHENG_CAI
        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_CAI)

        println("shiShangCount=$shiShangCount, guanShaCount=$guanShaCount, caiCount=$caiCount")

        if (guanShaCount >= shiShangCount && guanShaCount >= caiCount) {
            ret = true
        }
        println("isGuanShaStrong=$ret")
        return ret
    }

    fun isShiShangStrong(data: BaziData): Boolean {
        var ret = false
        var yinCount = 0
        var shiShangCount = 0
        var biJieCount = 0
        var guanShaCount = 0
        var caiCount = 0

//        yinCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_ZHENG_YIN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_YIN)
        shiShangCount = ShiShenUtil().getShiShenCount(
            data, ShiShen.SHISHEN_SHI_SHEN
        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_SHANG_GUAN)
//        biJieCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_BI_JIAN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_JIE_CAI)
        guanShaCount = ShiShenUtil().getShiShenCount(
            data, ShiShen.SHISHEN_ZHENG_GUAN
        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_QI_SHA)
        caiCount = ShiShenUtil().getShiShenCount(
            data, ShiShen.SHISHEN_ZHENG_CAI
        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_CAI)

        if (shiShangCount >= guanShaCount && shiShangCount >= caiCount) {
            ret = true
        }
        println("isShiShangStrong=$ret")
        return ret
    }

    fun isCaiStrong(data: BaziData): Boolean {
        var ret = false
        var shiShangCount = 0
        var guanShaCount = 0
        var caiCount = 0

        shiShangCount = ShiShenUtil().getShiShenCount(
            data, ShiShen.SHISHEN_SHI_SHEN
        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_SHANG_GUAN)
        guanShaCount = ShiShenUtil().getShiShenCount(
            data, ShiShen.SHISHEN_ZHENG_GUAN
        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_QI_SHA)
        caiCount = ShiShenUtil().getShiShenCount(
            data, ShiShen.SHISHEN_ZHENG_CAI
        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_CAI)

        if (caiCount >= guanShaCount && caiCount >= shiShangCount) {
            ret = true
        }
        println("isCaiStrong=$ret")
        return ret
    }

    fun checkZhengYinGJXiJi(data: BaziData) {
        var isBaziStrong = WuXingUtil().isBaziStrong(data)
        if (isBaziStrong) {
            data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
            data.xiShenList = listOf()
            data.jiShenList = listOf(
                ShiShen.SHISHEN_ZHENG_CAI,
                ShiShen.SHISHEN_PIAN_CAI
            )
        }else{
            data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
            data.xiShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
            data.jiShenList = listOf(
                ShiShen.SHISHEN_ZHENG_CAI,
                ShiShen.SHISHEN_PIAN_CAI
            )
        }
    }

    fun checkYinGJXiJi(gj: BaziGeJu, data: BaziData) {
        var isBaziStrong = WuXingUtil().isBaziStrong(data)
        println("checkYinGJXiJi(), isBaziStrong=$isBaziStrong")
        if (isBaziStrong) {
            if (isYinStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
                data.xiShenList = listOf(ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_YIN,
                    ShiShen.SHISHEN_PIAN_YIN,
                    ShiShen.SHISHEN_BI_JIAN,
                    ShiShen.SHISHEN_JIE_CAI,
                    ShiShen.SHISHEN_ZHENG_GUAN,
                    ShiShen.SHISHEN_QI_SHA
                )
            } else if (isBiJieStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
                data.xiShenList = listOf(
                    ShiShen.SHISHEN_SHI_SHEN,
                    ShiShen.SHISHEN_SHANG_GUAN,
                    ShiShen.SHISHEN_ZHENG_CAI,
                    ShiShen.SHISHEN_PIAN_CAI
                )
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_JIE_CAI
                )
            } else if (isCaiStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
                data.xiShenList = listOf()
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_CAI,
                    ShiShen.SHISHEN_PIAN_CAI,
                    ShiShen.SHISHEN_SHI_SHEN,
                    ShiShen.SHISHEN_SHANG_GUAN
                )
            }
        } else {
            if (isGuanShaStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.xiShenList = listOf(ShiShen.SHISHEN_BI_JIAN)
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_CAI,
                    ShiShen.SHISHEN_PIAN_CAI,
                    ShiShen.SHISHEN_ZHENG_GUAN,
                    ShiShen.SHISHEN_QI_SHA
                )
            } else if (isShiShangStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.xiShenList = listOf(ShiShen.SHISHEN_BI_JIAN)
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_CAI,
                    ShiShen.SHISHEN_PIAN_CAI,
                    ShiShen.SHISHEN_SHI_SHEN,
                    ShiShen.SHISHEN_SHANG_GUAN
                )
            }
        }
    }

    fun checkCaiGJXiJi(gj: BaziGeJu, data: BaziData) {
        var isBaziStrong = WuXingUtil().isBaziStrong(data)
        println("checkCaiGJXiJi(), isBaziStrong=$isBaziStrong")
        if (isBaziStrong) {
            if (isYinStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
                data.xiShenList = listOf(ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_YIN,
                    ShiShen.SHISHEN_PIAN_YIN,
                    ShiShen.SHISHEN_BI_JIAN,
                    ShiShen.SHISHEN_JIE_CAI,
                    ShiShen.SHISHEN_ZHENG_GUAN,
                    ShiShen.SHISHEN_QI_SHA
                )
            } else if (isBiJieStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
                data.xiShenList = listOf()
                data.jiShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
            }
        } else {
            if (isShiShangStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.xiShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_CAI,
                    ShiShen.SHISHEN_PIAN_CAI,
                    ShiShen.SHISHEN_ZHENG_GUAN,
                    ShiShen.SHISHEN_QI_SHA
                )
            } else if (isCaiStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                data.xiShenList = listOf()
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_CAI,
                    ShiShen.SHISHEN_PIAN_CAI,
                    ShiShen.SHISHEN_SHI_SHEN,
                    ShiShen.SHISHEN_SHANG_GUAN,
                    ShiShen.SHISHEN_ZHENG_GUAN,
                    ShiShen.SHISHEN_QI_SHA
                )
            } else if (isGuanShaStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.xiShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
            }
        }
    }

    fun checkShangGuanGJXiJi(gj: BaziGeJu, data: BaziData) {
        var isBaziStrong = WuXingUtil().isBaziStrong(data)
        println("[ShangGuanGJ] isBaziStrong=$isBaziStrong")
        if (isBaziStrong) {
            if (isYinStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
                data.xiShenList = listOf(ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_BI_JIAN
                )
            } else if (isBiJieStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_QI_SHA)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN, ShiShen.SHISHEN_BI_JIAN
                )
            }
        } else {
            if (isGuanShaStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.xiShenList = listOf(ShiShen.SHISHEN_JIE_CAI)
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_CAI,
                    ShiShen.SHISHEN_PIAN_CAI,
                    ShiShen.SHISHEN_ZHENG_GUAN,
                    ShiShen.SHISHEN_QI_SHA
                )
            } else if (isCaiStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.xiShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_CAI,
                    ShiShen.SHISHEN_PIAN_CAI,
                    ShiShen.SHISHEN_ZHENG_GUAN,
                    ShiShen.SHISHEN_QI_SHA
                )
            } else if (isShiShangStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.xiShenList = listOf()
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_CAI,
                    ShiShen.SHISHEN_PIAN_CAI,
                    ShiShen.SHISHEN_SHI_SHEN,
                    ShiShen.SHISHEN_SHANG_GUAN
                )
            }
        }
    }

    fun checkZhengGuanGJXiJi(gj: BaziGeJu, data: BaziData) {
        //example, 2003,08, 10, 00
        var isBaziStrong = WuXingUtil().isBaziStrong(data)
        println("[ZhengGuanGJ] isBaziStrong=$isBaziStrong")
        if (isBaziStrong) {
            if (isBiJieStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_YIN,
                    ShiShen.SHISHEN_PIAN_YIN,
                    ShiShen.SHISHEN_BI_JIAN,
                    ShiShen.SHISHEN_JIE_CAI
                )
            } else if (isYinStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
                data.xiShenList = listOf(ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_YIN,
                    ShiShen.SHISHEN_PIAN_YIN,
                    ShiShen.SHISHEN_BI_JIAN,
                    ShiShen.SHISHEN_JIE_CAI
                )
            } else if (isShiShangStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
                data.xiShenList = listOf()
                data.jiShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
            }
        } else {
            if (isCaiStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
            } else if (isShiShangStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.xiShenList = listOf()
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
            } else if (isGuanShaStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.xiShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
            }
        }
    }

    @Composable
    fun getYongShenString(data: BaziData): String {
        var str = ""
        var elementStr = ""
        var tg = TianGan.TIANGAN_JIA
        for (ss in data.yongShenList) {
            tg = ShiShenUtil().getShiShenTianGan(ss, data.dayTiangan)
            elementStr = BaziUtil().getTianGanLabel(tg) + WuXingUtil().getTianGanWuxingText(tg)
            str = str + elementStr + "(" + ShiShenUtil().getShiShenLabel(ss) + ") "
        }

        return str
    }

    @Composable
    fun getXiShenString(data: BaziData): String {
        var str = ""
        var elementStr = ""
        var tg = TianGan.TIANGAN_JIA
        for (ss in data.xiShenList) {
            tg = ShiShenUtil().getShiShenTianGan(ss, data.dayTiangan)
            elementStr = BaziUtil().getTianGanLabel(tg) + WuXingUtil().getTianGanWuxingText(tg)
            str = str + elementStr + "(" + ShiShenUtil().getShiShenLabel(ss) + ") "
        }

        return str
    }

    @Composable
    fun getJiShenString(data: BaziData): String {
        var str = ""
        var elementStr = ""
        var tg = TianGan.TIANGAN_JIA
        for (ss in data.jiShenList) {
            tg = ShiShenUtil().getShiShenTianGan(ss, data.dayTiangan)
            elementStr = BaziUtil().getTianGanLabel(tg) + WuXingUtil().getTianGanWuxingText(tg)
            str = str + elementStr + "(" + ShiShenUtil().getShiShenLabel(ss) + ") "
        }

        return str
    }

    @Composable
    fun getXiJiString(data: BaziData): String {
        var str = ""
        var elementStr = ""
        var tg = TianGan.TIANGAN_JIA
        str = str + stringResource(R.string.app_bazi_yong_shen) + ":"
        for (ss in data.yongShenList) {
            tg = ShiShenUtil().getShiShenTianGan(ss, data.dayTiangan)
            elementStr = BaziUtil().getTianGanLabel(tg) + WuXingUtil().getTianGanWuxingText(tg)
            str = str + elementStr + "(" + ShiShenUtil().getShiShenLabel(ss) + ") "
        }
        str = str + "\n"
        str = str + stringResource(R.string.app_bazi_xi_shen) + ":"
        for (ss in data.xiShenList) {
            tg = ShiShenUtil().getShiShenTianGan(ss, data.dayTiangan)
            elementStr = BaziUtil().getTianGanLabel(tg) + WuXingUtil().getTianGanWuxingText(tg)
            str = str + elementStr + "(" + ShiShenUtil().getShiShenLabel(ss) + ") "
        }
        str = str + "\n"
        str = str + stringResource(R.string.app_bazi_ji_shen) + ":"
        for (ss in data.jiShenList) {
            tg = ShiShenUtil().getShiShenTianGan(ss, data.dayTiangan)
            elementStr = BaziUtil().getTianGanLabel(tg) + WuXingUtil().getTianGanWuxingText(tg)
            str = str + elementStr + "(" + ShiShenUtil().getShiShenLabel(ss) + ") "
        }
        return str
    }

    fun checkShiShenGJXiJi(gj: BaziGeJu, data: BaziData) {
        var isBaziStrong = WuXingUtil().isBaziStrong(data)
        println("checkShiShenGJXiJi(), isBaziStrong=$isBaziStrong")
        if (isBaziStrong) {
            if (isYinStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
                data.xiShenList = listOf(ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_YIN,
                    ShiShen.SHISHEN_PIAN_YIN,
                    ShiShen.SHISHEN_ZHENG_GUAN,
                    ShiShen.SHISHEN_QI_SHA
                )
            } else if (isBiJieStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_YIN,
                    ShiShen.SHISHEN_PIAN_YIN,
                    ShiShen.SHISHEN_BI_JIAN,
                    ShiShen.SHISHEN_JIE_CAI
                )
            } else if (isCaiStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
                data.xiShenList = listOf()
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_YIN,
                    ShiShen.SHISHEN_PIAN_YIN,
                    ShiShen.SHISHEN_BI_JIAN,
                    ShiShen.SHISHEN_JIE_CAI
                )
            }
        } else {
            if (isShiShangStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.xiShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_CAI,
                    ShiShen.SHISHEN_PIAN_CAI,
                    ShiShen.SHISHEN_SHI_SHEN,
                    ShiShen.SHISHEN_SHANG_GUAN
                )
            } else if (isGuanShaStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.xiShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI, ShiShen.SHISHEN_QI_SHA
                )
            } else if (isCaiStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_SHI_SHEN,
                    ShiShen.SHISHEN_SHANG_GUAN,
                    ShiShen.SHISHEN_ZHENG_CAI,
                    ShiShen.SHISHEN_PIAN_CAI
                )
            }
        }
    }

    fun checkQiShaGJXiJi(gj: BaziGeJu, data: BaziData) {
        var isBaziStrong = WuXingUtil().isBaziStrong(data)
        println("checkQiShaGJXiJi(), isBaziStrong=$isBaziStrong")
        if (isBaziStrong) {
            if (isYinStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
                data.xiShenList = listOf(ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_YIN,
                    ShiShen.SHISHEN_PIAN_YIN,
                    ShiShen.SHISHEN_ZHENG_GUAN,
                    ShiShen.SHISHEN_QI_SHA,
                    ShiShen.SHISHEN_BI_JIAN,
                    ShiShen.SHISHEN_JIE_CAI
                )
            } else if (isBiJieStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_YIN,
                    ShiShen.SHISHEN_PIAN_YIN,
                    ShiShen.SHISHEN_BI_JIAN,
                    ShiShen.SHISHEN_JIE_CAI
                )
            } else if (isGuanShaStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
                data.xiShenList = listOf()
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_YIN,
                    ShiShen.SHISHEN_PIAN_YIN,
                    ShiShen.SHISHEN_ZHENG_GUAN,
                    ShiShen.SHISHEN_QI_SHA
                )
            }
        } else {
            if (isShiShangStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.xiShenList = listOf()
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_CAI,
                    ShiShen.SHISHEN_PIAN_CAI,
                    ShiShen.SHISHEN_SHI_SHEN,
                    ShiShen.SHISHEN_SHANG_GUAN
                )
            } else if (isGuanShaStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.xiShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_CAI,
                    ShiShen.SHISHEN_PIAN_CAI,
                    ShiShen.SHISHEN_ZHENG_GUAN,
                    ShiShen.SHISHEN_QI_SHA
                )
            } else if (isCaiStrong(data)) {
                data.yongShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_SHI_SHEN,
                    ShiShen.SHISHEN_SHANG_GUAN,
                    ShiShen.SHISHEN_ZHENG_CAI,
                    ShiShen.SHISHEN_PIAN_CAI
                )
            }
        }
    }

    fun checkJianLuGJXiJi(gj: BaziGeJu, data: BaziData) {
        var isBaziStrong = WuXingUtil().isBaziStrong(data)
        var guanShaCount = ShiShenUtil().getShiShenCount(
            data, ShiShen.SHISHEN_ZHENG_GUAN
        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_QI_SHA)
        println("checkJianLuGJXiJi(), isBaziStrong=$isBaziStrong")
        if (isBaziStrong) {
            if (guanShaCount == 0) {
                data.yongShenList = listOf(ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
                data.xiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_CAI,
                    ShiShen.SHISHEN_PIAN_CAI,
                    ShiShen.SHISHEN_ZHENG_GUAN,
                    ShiShen.SHISHEN_QI_SHA
                )
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_YIN,
                    ShiShen.SHISHEN_PIAN_YIN,
                    ShiShen.SHISHEN_BI_JIAN,
                    ShiShen.SHISHEN_JIE_CAI
                )
            } else {
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
                data.xiShenList = listOf(
                    ShiShen.SHISHEN_SHI_SHEN,
                    ShiShen.SHISHEN_SHANG_GUAN,
                    ShiShen.SHISHEN_ZHENG_GUAN,
                    ShiShen.SHISHEN_QI_SHA
                )
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_YIN,
                    ShiShen.SHISHEN_PIAN_YIN,
                    ShiShen.SHISHEN_BI_JIAN,
                    ShiShen.SHISHEN_JIE_CAI
                )
            }
        } else {
            //unlikely happen
            data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
            data.xiShenList = listOf(
                ShiShen.SHISHEN_SHI_SHEN,
                ShiShen.SHISHEN_SHANG_GUAN,
                ShiShen.SHISHEN_ZHENG_GUAN,
                ShiShen.SHISHEN_QI_SHA
            )
            data.jiShenList = listOf(
                ShiShen.SHISHEN_ZHENG_YIN,
                ShiShen.SHISHEN_PIAN_YIN,
                ShiShen.SHISHEN_BI_JIAN,
                ShiShen.SHISHEN_JIE_CAI
            )
        }
    }

    fun checkQuZhiGJXiJi(data: BaziData) {
        data.yongShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
        data.xiShenList = listOf(
            ShiShen.SHISHEN_ZHENG_YIN,
            ShiShen.SHISHEN_PIAN_YIN,
            ShiShen.SHISHEN_SHI_SHEN,
            ShiShen.SHISHEN_SHANG_GUAN
        )
        data.jiShenList = listOf(
            ShiShen.SHISHEN_ZHENG_GUAN,
            ShiShen.SHISHEN_QI_SHA
        )
    }

    fun checkYanShangGJXiJi(data: BaziData) {
        data.yongShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
        data.xiShenList = listOf(
            ShiShen.SHISHEN_ZHENG_YIN,
            ShiShen.SHISHEN_PIAN_YIN
        )
        data.jiShenList = listOf(
            ShiShen.SHISHEN_ZHENG_GUAN,
            ShiShen.SHISHEN_QI_SHA
        )
    }

    fun checkRunXiaGJXiJi(data: BaziData) {
        data.yongShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
        data.xiShenList = listOf(
            ShiShen.SHISHEN_ZHENG_YIN,
            ShiShen.SHISHEN_PIAN_YIN
        )
        data.jiShenList = listOf(
            ShiShen.SHISHEN_ZHENG_GUAN,
            ShiShen.SHISHEN_QI_SHA,
            ShiShen.SHISHEN_ZHENG_CAI,
            ShiShen.SHISHEN_PIAN_CAI
        )
    }

    fun checkJiaSeGJXiJi(data: BaziData) {
        data.yongShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
        data.xiShenList = listOf(
            ShiShen.SHISHEN_ZHENG_YIN,
            ShiShen.SHISHEN_PIAN_YIN
        )
        data.jiShenList = listOf(
            ShiShen.SHISHEN_ZHENG_GUAN,
            ShiShen.SHISHEN_QI_SHA
        )
    }

    fun checkCongGeGJXiJi(data: BaziData) {
        data.yongShenList = listOf(ShiShen.SHISHEN_QI_SHA)
        data.xiShenList = listOf(
            ShiShen.SHISHEN_ZHENG_YIN,
            ShiShen.SHISHEN_PIAN_YIN
        )
        data.jiShenList = listOf(
            ShiShen.SHISHEN_ZHENG_GUAN,
            ShiShen.SHISHEN_QI_SHA
        )
    }

    fun checkYangRenGJXiJi(data: BaziData) {
        var shiShangCount = 0
        var guanShaCount = 0
        var caiCount = 0
        var yinCount = 0
        var biJieCount = 0

        shiShangCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_SHI_SHEN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_SHANG_GUAN)
        guanShaCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_ZHENG_GUAN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_QI_SHA)
        caiCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_ZHENG_CAI) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_CAI)
        yinCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_ZHENG_YIN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_YIN)
        biJieCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_BI_JIAN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_JIE_CAI)

        var goodElements = yinCount + biJieCount
        var badElements = shiShangCount + guanShaCount + caiCount
        var isStrong = false
        if(goodElements >= 3){
            isStrong = true
        }
        if(isStrong){
            data.yongShenList = listOf(ShiShen.SHISHEN_QI_SHA)
            data.xiShenList = listOf(
                ShiShen.SHISHEN_ZHENG_GUAN
            )
            data.jiShenList = listOf(
                ShiShen.SHISHEN_SHI_SHEN,
                ShiShen.SHISHEN_SHANG_GUAN
            )
        }else{
            data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
            data.xiShenList = listOf(
                ShiShen.SHISHEN_BI_JIAN,ShiShen.SHISHEN_JIE_CAI
            )
            data.jiShenList = listOf(
                ShiShen.SHISHEN_ZHENG_CAI,
                ShiShen.SHISHEN_PIAN_CAI,
                ShiShen.SHISHEN_ZHENG_GUAN,
                ShiShen.SHISHEN_QI_SHA,
                ShiShen.SHISHEN_SHI_SHEN,
                ShiShen.SHISHEN_SHANG_GUAN
            )
        }
    }

    fun checkCongCaiGJXiJi(data: BaziData) {
        data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
        data.xiShenList = listOf(
            ShiShen.SHISHEN_SHI_SHEN,
            ShiShen.SHISHEN_SHANG_GUAN
        )
        data.jiShenList = listOf(
            ShiShen.SHISHEN_ZHENG_YIN,
            ShiShen.SHISHEN_PIAN_YIN,
            ShiShen.SHISHEN_BI_JIAN,
            ShiShen.SHISHEN_JIE_CAI
        )
    }

    fun checkCongShaGJXiJi(data: BaziData) {
        data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
        data.xiShenList = listOf(
            ShiShen.SHISHEN_ZHENG_CAI,
            ShiShen.SHISHEN_PIAN_CAI
        )
        data.jiShenList = listOf(
            ShiShen.SHISHEN_SHI_SHEN,
            ShiShen.SHISHEN_SHANG_GUAN
        )
    }

    fun checkCongErGJXiJi(data: BaziData) {
        data.yongShenList = listOf(ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
        data.xiShenList = listOf(
            ShiShen.SHISHEN_ZHENG_CAI,
            ShiShen.SHISHEN_PIAN_CAI,
            ShiShen.SHISHEN_BI_JIAN,
            ShiShen.SHISHEN_JIE_CAI
        )
        data.jiShenList = listOf(
            ShiShen.SHISHEN_ZHENG_YIN,
            ShiShen.SHISHEN_PIAN_YIN
        )
    }

    @Composable
    fun getGeJuXiJi(gj: BaziGeJu, data: BaziData): String {
        var str = ""
        println("Bazi gj=$gj")
        if (gj == BaziGeJu.GJ_SHANG_GUAN) {
            checkShangGuanGJXiJi(gj, data)
        } else if (gj == BaziGeJu.GJ_PIAN_YIN) {
            checkYinGJXiJi(gj, data)
        } else if (gj == BaziGeJu.GJ_ZHENG_YIN) {
            checkZhengYinGJXiJi(data)
        }else if (gj == BaziGeJu.GJ_ZHENG_GUAN) {
            checkZhengGuanGJXiJi(gj, data)
        } else if (gj == BaziGeJu.GJ_ZHENG_CAI || gj == BaziGeJu.GJ_PIAN_CAI) {
            checkCaiGJXiJi(gj, data)
        } else if (gj == BaziGeJu.GJ_SHI_SHEN) {
            checkShiShenGJXiJi(gj, data)
        } else if (gj == BaziGeJu.GJ_QI_SHA) {
            checkQiShaGJXiJi(gj, data)
        } else if (gj == BaziGeJu.GJ_JIAN_LU) {
            checkJianLuGJXiJi(gj, data)
        } else if (gj == BaziGeJu.GJ_QU_ZHI) {
            checkQuZhiGJXiJi(data)
        }else if (gj == BaziGeJu.GJ_YAN_SHANG) {
            checkYanShangGJXiJi(data)
        }else if (gj == BaziGeJu.GJ_RUN_XIA) {
            checkRunXiaGJXiJi(data)
        }else if (gj == BaziGeJu.GJ_JIA_SE) {
            checkJiaSeGJXiJi(data)
        }else if (gj == BaziGeJu.GJ_CONG_GE) {
            checkCongGeGJXiJi(data)
        }else if (gj == BaziGeJu.GJ_YANG_REN) {
            checkYangRenGJXiJi(data)
        }else if (gj == BaziGeJu.GJ_CONG_CAI) {
            checkCongCaiGJXiJi(data)
        }else if (gj == BaziGeJu.GJ_CONG_SHA) {
            checkCongShaGJXiJi(data)
        }else if (gj == BaziGeJu.GJ_CONG_ER) {
            checkCongErGJXiJi(data)
        }else {
            str = "ToDo"
        }
        str = getXiJiString(data)
//            println("=============================")
//            println("data.xiShenList=${data.xiShenList}")
//            println("data.jiShenList=${data.jiShenList}")
//            println("data.yongShenList=${data.yongShenList}")
        return str
    }

    @Composable
    fun getGeJuAnalysis(gj: BaziGeJu, data: BaziData): String {
        var str = stringResource(R.string.app_bazi_gj_analysis_no)
        var tg = data.yearTiangan
        var tgArray : Array<TianGan> = arrayOf()
        if (gj == BaziGeJu.GJ_ZHENG_YIN) {
            //1
            if(ShiShenUtil().getZhengcai(data.dayTiangan) == data.yearTiangan || ShiShenUtil().getPiancai(data.dayTiangan) == data.yearTiangan){
                if(ShiShenUtil().getZhengyin(data.dayTiangan) == data.monthTiangan || ShiShenUtil().getZhengyin(data.dayTiangan) == data.hourTiangan){
                    return stringResource(R.string.app_bazi_gj_zhengyin_desc1)
                }
            }
            if(ShiShenUtil().getZhengcai(data.dayTiangan) == data.monthTiangan || ShiShenUtil().getPiancai(data.dayTiangan) == data.monthTiangan){
                if(ShiShenUtil().getZhengyin(data.dayTiangan) == data.hourTiangan){
                    return stringResource(R.string.app_bazi_gj_zhengyin_desc1)
                }
            }
            //2
            if(ShiShenUtil().getZhengyin(data.dayTiangan) == data.yearTiangan){
                if(ShiShenUtil().getZhengcai(data.dayTiangan) == data.monthTiangan || ShiShenUtil().getPiancai(data.dayTiangan) == data.monthTiangan){
                    return stringResource(R.string.app_bazi_gj_zhengyin_desc2)
                }
                if(ShiShenUtil().getZhengcai(data.dayTiangan) == data.hourTiangan || ShiShenUtil().getPiancai(data.dayTiangan) == data.hourTiangan){
                    return stringResource(R.string.app_bazi_gj_zhengyin_desc2)
                }
            }
            if(ShiShenUtil().getZhengyin(data.dayTiangan) == data.monthTiangan){
                if(ShiShenUtil().getZhengcai(data.dayTiangan) == data.hourTiangan || ShiShenUtil().getPiancai(data.dayTiangan) == data.hourTiangan){
                    return stringResource(R.string.app_bazi_gj_zhengyin_desc2)
                }
            }
            //3
            if(ShiShenUtil().getZhengyin(data.dayTiangan) == data.monthTiangan){
                tgArray = DiZhiUtil().getCanggan(data.monthDizhi)
                for(tg in tgArray){
                    if(ShiShenUtil().getZhengcai(data.dayTiangan) == tg){
                        return stringResource(R.string.app_bazi_gj_zhengyin_desc3)
                    }
                }
            }
            //4
            if(ShiShenUtil().getZhengyin(data.dayTiangan) == data.yearTiangan){
                tgArray = DiZhiUtil().getCanggan(data.yearDizhi)
                for(tg in tgArray){
                    if(ShiShenUtil().getZhengyin(data.dayTiangan) == tg){
                        return stringResource(R.string.app_bazi_gj_zhengyin_desc4)
                    }
                }
            }
            if(ShiShenUtil().getZhengyin(data.dayTiangan) == data.monthTiangan){
                tgArray = DiZhiUtil().getCanggan(data.monthDizhi)
                for(tg in tgArray){
                    if(ShiShenUtil().getZhengyin(data.dayTiangan) == tg){
                        return stringResource(R.string.app_bazi_gj_zhengyin_desc4)
                    }
                }
            }
            if(ShiShenUtil().getZhengyin(data.dayTiangan) == data.hourTiangan){
                tgArray = DiZhiUtil().getCanggan(data.hourDizhi)
                for(tg in tgArray){
                    if(ShiShenUtil().getZhengyin(data.dayTiangan) == tg){
                        return stringResource(R.string.app_bazi_gj_zhengyin_desc4)
                    }
                }
            }
            //5
            if(ShiShenUtil().getZhengyin(data.dayTiangan) == data.yearTiangan){
                tgArray = DiZhiUtil().getCanggan(data.yearDizhi)
                for(tg in tgArray){
                    if(ShiShenUtil().getPianyin(data.dayTiangan) == tg){
                        return stringResource(R.string.app_bazi_gj_zhengyin_desc4)
                    }
                }
            }
            if(ShiShenUtil().getZhengyin(data.dayTiangan) == data.monthTiangan){
                tgArray = DiZhiUtil().getCanggan(data.monthDizhi)
                for(tg in tgArray){
                    if(ShiShenUtil().getPianyin(data.dayTiangan) == tg){
                        return stringResource(R.string.app_bazi_gj_zhengyin_desc4)
                    }
                }
            }
            if(ShiShenUtil().getZhengyin(data.dayTiangan) == data.hourTiangan){
                tgArray = DiZhiUtil().getCanggan(data.hourDizhi)
                for(tg in tgArray){
                    if(ShiShenUtil().getPianyin(data.dayTiangan) == tg){
                        return stringResource(R.string.app_bazi_gj_zhengyin_desc4)
                    }
                }
            }

        }
        return str
    }
}