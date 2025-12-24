package com.rick.bazi.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rick.bazi.R
import com.rick.bazi.data.BaziData
import com.rick.bazi.data.BaziGeJu
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.FEMALE
import com.rick.bazi.data.MALE
import com.rick.bazi.data.ShiShen
import com.rick.bazi.data.TianGan
import com.rick.bazi.data.WuXing
import com.rick.bazi.ui.BaziViewModel

class GeJuUtil {

    fun getGJByShiShen(ss: ShiShen): BaziGeJu {

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
            return BaziGeJu.GJ_YANG_REN
        }
        return BaziGeJu.GJ_NONE
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
        } else if (gj == BaziGeJu.GJ_QU_ZHI) {
            str = stringResource(R.string.app_bazi_gj_quzhi_desc)
        } else if (gj == BaziGeJu.GJ_YAN_SHANG) {
            str = stringResource(R.string.app_bazi_gj_yanshang_desc)
        } else if (gj == BaziGeJu.GJ_RUN_XIA) {
            str = stringResource(R.string.app_bazi_gj_runxia_desc)
        } else if (gj == BaziGeJu.GJ_JIA_SE) {
            str = stringResource(R.string.app_bazi_gj_jiase_desc)
        } else if (gj == BaziGeJu.GJ_CONG_GE) {
            str = stringResource(R.string.app_bazi_gj_congge_desc)
        } else if (gj == BaziGeJu.GJ_YANG_REN) {
            str = stringResource(R.string.app_bazi_gj_yangren_desc)
        } else if (gj == BaziGeJu.GJ_CONG_CAI) {
            str = stringResource(R.string.app_bazi_gj_congcai_desc)
        } else if (gj == BaziGeJu.GJ_CONG_SHA) {
            str = stringResource(R.string.app_bazi_gj_congsha_desc)
        } else if (gj == BaziGeJu.GJ_CONG_ER) {
            str = stringResource(R.string.app_bazi_gj_conger_desc)
        } else {
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

//    fun isTongGen(tg: TianGan, baziInfo: BaziData): Boolean {
//        val wx = WuXingUtil().getTgWX(tg)
//        if (wx == WuXingUtil().getTgWX(baziInfo.yearTiangan)) return true
//        if (wx == WuXingUtil().getTgWX(baziInfo.monthTiangan)) return true
//        if (wx == WuXingUtil().getTgWX(baziInfo.dayTiangan)) return true
//        if (wx == WuXingUtil().getTgWX(baziInfo.hourTiangan)) return true
//        return false
//    }
//
//    fun isTouChu(tg: TianGan, baziInfo: BaziData): Boolean {
//        if (tg == baziInfo.yearTiangan) return true
//        if (tg == baziInfo.monthTiangan) return true
//        if (tg == baziInfo.dayTiangan) return true
//        if (tg == baziInfo.hourTiangan) return true
//        return false
//    }

    fun isQuZhiGJ(data: BaziData): Boolean {
        var ret = false
        if (data.dayTiangan == TianGan.TIANGAN_JIA || data.dayTiangan == TianGan.TIANGAN_YI) {
            if (WuXingUtil().getJinNumber(data) == 0 && data.muWeight >= 7f) {
                ret = true
            }
        }
        return ret
    }

    fun isYanShangGJ(data: BaziData): Boolean {
        var ret = false
        if (data.dayTiangan == TianGan.TIANGAN_BING || data.dayTiangan == TianGan.TIANGAN_DING) {
            if (WuXingUtil().getShuiNumber(data) == 0 && data.huoWeight >= 7f) {
                ret = true
            }
        }
        return ret
    }

    fun isRunXiaGJ(data: BaziData): Boolean {
        var ret = false
        if (data.dayTiangan == TianGan.TIANGAN_REN || data.dayTiangan == TianGan.TIANGAN_GUI) {
            if (data.monthDizhi == DiZhi.DIZHI_HAI || data.monthDizhi == DiZhi.DIZHI_ZI) {
                if (WuXingUtil().getHuoNumber(data) == 0 && WuXingUtil().getTuNumber(data) == 0 && data.shuiWeight >= 5f) {
                    ret = true
                }
            }
        }
        return ret
    }

    fun isJiaSeGJ(data: BaziData): Boolean {
        var ret = false
        if (data.dayTiangan == TianGan.TIANGAN_WU || data.dayTiangan == TianGan.TIANGAN_JI) {
            if (WuXingUtil().getMuNumber(data) == 0 && data.tuWeight >= 7f) {
                ret = true
            }
        }
        return ret
    }

    fun isCongGeGJ(data: BaziData): Boolean {
        var ret = false
        if (data.dayTiangan == TianGan.TIANGAN_GENG || data.dayTiangan == TianGan.TIANGAN_XIN) {
            if (WuXingUtil().getHuoNumber(data) == 0 && data.jinWeight >= 6f) {
                ret = true
            }
        }
        return ret
    }

    fun isYangRenGJ(data: BaziData): Boolean {
        var ret = false
//        var weight = 0f
        val dayTg = data.dayTiangan
        val yangrenDz = BaziUtil().getYangren(dayTg)
//        var wx = WuXingUtil().getTgWX(dayTg)
        if (yangrenDz == data.monthDizhi) {
            ret = true
        }
        return ret
    }

    fun isJianLuGJ(data: BaziData): Boolean {
        var ret = false
        var luDz = TianGanUtil().getTianGanLu(data.dayTiangan)
        if (luDz == data.monthDizhi) {
            ret = true
        }
        return ret
    }

    fun getBaziWuXingWeight(data: BaziData, wx: WuXing): Float {
        var weight = 0f
        if (wx == WuXing.WUXING_SHUI) {
            weight = data.shuiWeight
        } else if (wx == WuXing.WUXING_MU) {
            weight = data.muWeight
        } else if (wx == WuXing.WUXING_TU) {
            weight = data.tuWeight
        } else if (wx == WuXing.WUXING_HUO) {
            weight = data.huoWeight
        } else if (wx == WuXing.WUXING_JIN) {
            weight = data.jinWeight
        }
        return weight
    }

    fun isCaiWeightStrong(data: BaziData): Boolean {
        var ret = false
        val caiWeight = getBaziWuXingWeight(data, WuXingUtil().getCaiWuXing(data.dayTiangan))
        val guanshaWeight =
            getBaziWuXingWeight(data, WuXingUtil().getGuanshaWuXing(data.dayTiangan))
        val shishangWeight =
            getBaziWuXingWeight(data, WuXingUtil().getShishangWuXing(data.dayTiangan))
        if (caiWeight > guanshaWeight && caiWeight > shishangWeight) {
            ret = true
        }
        return ret
    }

    fun isShaWeightStrong(data: BaziData): Boolean {
        var ret = false
        val caiWeight = getBaziWuXingWeight(data, WuXingUtil().getCaiWuXing(data.dayTiangan))
        val guanshaWeight =
            getBaziWuXingWeight(data, WuXingUtil().getGuanshaWuXing(data.dayTiangan))
        val shishangWeight =
            getBaziWuXingWeight(data, WuXingUtil().getShishangWuXing(data.dayTiangan))
        if (guanshaWeight > caiWeight && guanshaWeight > shishangWeight) {
            ret = true
        }
        return ret
    }

    fun isCongCaiGJ(data: BaziData): Boolean {
        var ret = false
        var yinWeight = getBaziWuXingWeight(data, WuXingUtil().getYinWuXing(data.dayTiangan))
        var bijieWeight = getBaziWuXingWeight(data, WuXingUtil().getBiJieWuXing(data.dayTiangan))

        if (yinWeight <= 2f && bijieWeight <= 2f) {
            if (isCaiWeightStrong(data)) {
                ret = true
            }
        }
        return ret
    }

    fun isCongShaGJ(data: BaziData): Boolean {
        var ret = false
        val yinWeight = getBaziWuXingWeight(data, WuXingUtil().getYinWuXing(data.dayTiangan))
        val bijieWeight = getBaziWuXingWeight(data, WuXingUtil().getBiJieWuXing(data.dayTiangan))

        if (yinWeight <= 2f && bijieWeight <= 2f) {
            if (isShaWeightStrong(data)) {
                ret = true
            }
        }
        return ret
    }

    fun isCongErGJ(data: BaziData): Boolean {
        var ret = false
        var yinWeight = getBaziWuXingWeight(data, WuXingUtil().getYinWuXing(data.dayTiangan))
        var bijieWeight = getBaziWuXingWeight(data, WuXingUtil().getBiJieWuXing(data.dayTiangan))
        val yinCount = ShiShenUtil().getShiShenCount(
            data,
            ShiShen.SHISHEN_ZHENG_YIN
        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_YIN)
        val caiCount = ShiShenUtil().getShiShenCount(
            data,
            ShiShen.SHISHEN_ZHENG_CAI
        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_CAI)
        val monthDzShiShen = ShiShenUtil().getDiZhiShiShen(data.monthDizhi, data.dayTiangan)
        val shishangWeight =
            getBaziWuXingWeight(data, WuXingUtil().getShishangWuXing(data.dayTiangan))

        if (monthDzShiShen == ShiShen.SHISHEN_SHI_SHEN || monthDzShiShen == ShiShen.SHISHEN_SHANG_GUAN) {
            if (yinCount == 0 && caiCount > 0) {
                if (shishangWeight > 3f) {
                    ret = true
                }
            }
        }

        return ret
    }

    fun isZhengYinGJ(data: BaziData): Boolean {
        var ret = false
        val zhengYinTG = ShiShenUtil().getZhengyin(data.dayTiangan)
        var cangganArray = DiZhiUtil().getCanggan(data.monthDizhi)
        if (zhengYinTG == cangganArray[0]) {
            ret = true
        } else {
            if (zhengYinTG == data.yearTiangan || zhengYinTG == data.monthTiangan || zhengYinTG == data.hourTiangan) {
                //check year
                cangganArray = DiZhiUtil().getCanggan(data.yearDizhi)
                for (tg in cangganArray) {
                    if (tg == zhengYinTG) {
                        ret = true
                        break
                    }
                }
                if (!ret) {
                    //check month
                    cangganArray = DiZhiUtil().getCanggan(data.monthDizhi)
                    for (tg in cangganArray) {
                        if (tg == zhengYinTG) {
                            ret = true
                            break
                        }
                    }
                }
                if (!ret) {
                    //check day
                    cangganArray = DiZhiUtil().getCanggan(data.dayDizhi)
                    for (tg in cangganArray) {
                        if (tg == zhengYinTG) {
                            ret = true
                            break
                        }
                    }
                }
                if (!ret) {
                    //check hour
                    cangganArray = DiZhiUtil().getCanggan(data.hourDizhi)
                    for (tg in cangganArray) {
                        if (tg == zhengYinTG) {
                            ret = true
                            break
                        }
                    }
                }
            }
        }
        return ret
    }

    fun isMonthDzTouTiangan(tg: TianGan, data: BaziData): Boolean {
        if (tg == data.yearTiangan) return true
        if (tg == data.monthTiangan) return true
        if (tg == data.hourTiangan) return true
        return false
    }

    fun isOtherGJ(baziData: BaziData): Boolean {
        if (isYanShangGJ(baziData)) {
            baziData.gj = BaziGeJu.GJ_YAN_SHANG
            return true
        }
        if (isQuZhiGJ(baziData)) {
            baziData.gj = BaziGeJu.GJ_QU_ZHI
            return true
        }
        if (isRunXiaGJ(baziData)) {
            baziData.gj = BaziGeJu.GJ_RUN_XIA
            return true
        }
        if (isJiaSeGJ(baziData)) {
            baziData.gj = BaziGeJu.GJ_JIA_SE
            return true
        }
        if (isCongGeGJ(baziData)) {
            baziData.gj = BaziGeJu.GJ_CONG_GE
            return true
        }

        if (isYangRenGJ(baziData)) {
            baziData.gj = BaziGeJu.GJ_YANG_REN
            return true
        }
        if (isJianLuGJ(baziData)) {
            baziData.gj = BaziGeJu.GJ_JIAN_LU
            return true
        }
        if (isCongCaiGJ(baziData)) {
            baziData.gj = BaziGeJu.GJ_CONG_CAI
            return true
        }
        if (isCongShaGJ(baziData)) {
            baziData.gj = BaziGeJu.GJ_CONG_SHA
            return true
        }
        if (isCongErGJ(baziData)) {
            baziData.gj = BaziGeJu.GJ_CONG_ER
            return true
        }
        return false
    }

    fun getGJ(baziData: BaziData): BaziGeJu {
        var gj = BaziGeJu.GJ_NONE
        var gjTG = TianGan.TIANGAN_JIA
        var foundGJ = false
        var tg = TianGan.TIANGAN_JIA
        val monthDz = baziData.monthDizhi
        var ss = ShiShen.SHISHEN_SHI_SHEN
        YongShenUtil().calculateWuXingWeight(baziData)

        if (isOtherGJ(baziData)) {
            calculateGeJuXiJi(baziData.gj, baziData)
            return baziData.gj
        }

        //normal GeJu
        var cangganArray = DiZhiUtil().diZhiCangGanMap[monthDz]!!

        //benqi touchu
        if (isMonthDzTouTiangan(cangganArray[0], baziData)) {
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
            //check other dizhi touchu
            cangganArray = DiZhiUtil().diZhiCangGanMap[baziData.yearDizhi]!!
            if (isMonthDzTouTiangan(cangganArray[0], baziData)) {
                gjTG = cangganArray[0]
                foundGJ = true
            }
            if (!foundGJ) {
                cangganArray = DiZhiUtil().diZhiCangGanMap[baziData.dayDizhi]!!
                if (isMonthDzTouTiangan(cangganArray[0], baziData)) {
                    gjTG = cangganArray[0]
                    foundGJ = true
                }
            }
            if (!foundGJ) {
                cangganArray = DiZhiUtil().diZhiCangGanMap[baziData.hourDizhi]!!
                if (isMonthDzTouTiangan(cangganArray[0], baziData)) {
                    gjTG = cangganArray[0]
                    foundGJ = true
                }
            }
            if (!foundGJ) {
                cangganArray = DiZhiUtil().diZhiCangGanMap[monthDz]!!
                gjTG = cangganArray[0]
                foundGJ = true
            }
        }

        ss = ShiShenUtil().getShiShen(gjTG, baziData.dayTiangan)
        gj = GeJuUtil().getGJByShiShen(ss)

        baziData.gj = gj
        calculateGeJuXiJi(gj, baziData)
        YongShenUtil().checkBaziYongShen(baziData)
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
        } else if (gj == BaziGeJu.GJ_SHI_SHEN) {
            str = stringResource(R.string.app_bazi_gj_job_shishen)
        } else if (gj == BaziGeJu.GJ_SHANG_GUAN) {
            str = stringResource(R.string.app_bazi_gj_job_shangguan)
        } else if (gj == BaziGeJu.GJ_ZHENG_GUAN) {
            str = stringResource(R.string.app_bazi_gj_job_zhengguan)
        } else if (gj == BaziGeJu.GJ_QI_SHA) {
            str = stringResource(R.string.app_bazi_gj_job_qisha)
        } else if (gj == BaziGeJu.GJ_ZHENG_CAI) {
            str = stringResource(R.string.app_bazi_gj_job_zhengcai)
        } else if (gj == BaziGeJu.GJ_PIAN_CAI) {
            str = stringResource(R.string.app_bazi_gj_job_piancai)
        } else if (gj == BaziGeJu.GJ_JIAN_LU) {
            str = stringResource(R.string.app_bazi_gj_job_jianlu)
        } else if (gj == BaziGeJu.GJ_QU_ZHI) {
            str = stringResource(R.string.app_bazi_gj_job_quzhi)
        } else if (gj == BaziGeJu.GJ_YAN_SHANG) {
            str = stringResource(R.string.app_bazi_gj_job_yanshang)
        } else if (gj == BaziGeJu.GJ_RUN_XIA) {
            str = stringResource(R.string.app_bazi_gj_job_runxia)
        } else if (gj == BaziGeJu.GJ_JIA_SE) {
            str = stringResource(R.string.app_bazi_gj_job_jiase)
        } else if (gj == BaziGeJu.GJ_CONG_GE) {
            str = stringResource(R.string.app_bazi_gj_job_congge)
        } else if (gj == BaziGeJu.GJ_YANG_REN) {
            str = stringResource(R.string.app_bazi_gj_job_yangren)
        } else if (gj == BaziGeJu.GJ_CONG_CAI) {
            str = stringResource(R.string.app_bazi_gj_job_congcai)
        } else if (gj == BaziGeJu.GJ_CONG_SHA) {
            str = stringResource(R.string.app_bazi_gj_job_congsha)
        } else if (gj == BaziGeJu.GJ_CONG_ER) {
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

//        yinCount = ShiShenUtil().getShiShenCount(
//            data, ShiShen.SHISHEN_ZHENG_YIN
//        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_YIN)
//        biJieCount = ShiShenUtil().getShiShenCount(
//            data, ShiShen.SHISHEN_BI_JIAN
//        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_JIE_CAI)
//
//        if (yinCount >= biJieCount) {
//            ret = true
//        }
//        println("isYinStrong=$ret")
        if (data.yinWeight >= data.bijieWeight) {
            ret = true
        }
        return ret
    }

    fun isBiJieStrong(data: BaziData): Boolean {
        var ret = false
//        var yinCount = 0
//        var biJieCount = 0
//
//        yinCount = ShiShenUtil().getShiShenCount(
//            data, ShiShen.SHISHEN_ZHENG_YIN
//        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_YIN)
//        biJieCount = ShiShenUtil().getShiShenCount(
//            data, ShiShen.SHISHEN_BI_JIAN
//        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_JIE_CAI)
//
//        if (biJieCount >= yinCount) {
//            ret = true
//        }
//        println("isBiJieStrong=$ret")
        if (data.bijieWeight >= data.yinWeight) {
            ret = true
        }
        return ret
    }

    fun isGuanShaStrong(data: BaziData): Boolean {
        var ret = false
//        var shiShangCount = 0
//        var guanShaCount = 0
//        var caiCount = 0
//
//        shiShangCount = ShiShenUtil().getShiShenCount(
//            data, ShiShen.SHISHEN_SHI_SHEN
//        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_SHANG_GUAN)
//        guanShaCount = ShiShenUtil().getShiShenCount(
//            data, ShiShen.SHISHEN_ZHENG_GUAN
//        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_QI_SHA)
//        caiCount = ShiShenUtil().getShiShenCount(
//            data, ShiShen.SHISHEN_ZHENG_CAI
//        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_CAI)
//
//        println("shiShangCount=$shiShangCount, guanShaCount=$guanShaCount, caiCount=$caiCount")
//
//        if (guanShaCount >= shiShangCount && guanShaCount >= caiCount) {
//            ret = true
//        }
//        println("isGuanShaStrong=$ret")
        if (data.guanshaWeight >= data.shishangWeight && data.guanshaWeight >= data.caiWeight) {
            ret = true
        }
        return ret
    }

    fun isShiShangStrong(data: BaziData): Boolean {
        var ret = false
//        var yinCount = 0
//        var shiShangCount = 0
//        var biJieCount = 0
//        var guanShaCount = 0
//        var caiCount = 0
//
////        yinCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_ZHENG_YIN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_YIN)
//        shiShangCount = ShiShenUtil().getShiShenCount(
//            data, ShiShen.SHISHEN_SHI_SHEN
//        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_SHANG_GUAN)
////        biJieCount = ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_BI_JIAN) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_JIE_CAI)
//        guanShaCount = ShiShenUtil().getShiShenCount(
//            data, ShiShen.SHISHEN_ZHENG_GUAN
//        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_QI_SHA)
//        caiCount = ShiShenUtil().getShiShenCount(
//            data, ShiShen.SHISHEN_ZHENG_CAI
//        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_CAI)
//
//        if (shiShangCount >= guanShaCount && shiShangCount >= caiCount) {
//            ret = true
//        }
//        println("isShiShangStrong=$ret")
        if (data.shishangWeight >= data.guanshaWeight && data.shishangWeight >= data.caiWeight) {
            ret = true
        }
        return ret
    }

    fun isCaiStrong(data: BaziData): Boolean {
        var ret = false
//        var shiShangCount = 0
//        var guanShaCount = 0
//        var caiCount = 0
//
//        shiShangCount = ShiShenUtil().getShiShenCount(
//            data, ShiShen.SHISHEN_SHI_SHEN
//        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_SHANG_GUAN)
//        guanShaCount = ShiShenUtil().getShiShenCount(
//            data, ShiShen.SHISHEN_ZHENG_GUAN
//        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_QI_SHA)
//        caiCount = ShiShenUtil().getShiShenCount(
//            data, ShiShen.SHISHEN_ZHENG_CAI
//        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_CAI)
//
//        if (caiCount >= guanShaCount && caiCount >= shiShangCount) {
//            ret = true
//        }
//        println("isCaiStrong=$ret")
        if (data.caiWeight >= data.guanshaWeight && data.caiWeight >= data.shishangWeight) {
            ret = true
        }
        return ret
    }

    fun checkZhengYinGJXiJi(data: BaziData) {
        var isBaziStrong = WuXingUtil().isBaziStrong(data)
        if (isBaziStrong) {
            //cai strong
            if (data.caiWeight > 3f) {
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
                data.xiShenList = listOf(ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
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
            } else {
                //bijie strong
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
                data.xiShenList = listOf(ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_YIN,
                    ShiShen.SHISHEN_PIAN_YIN,
                    ShiShen.SHISHEN_BI_JIAN,
                    ShiShen.SHISHEN_JIE_CAI
                )
            }
        } else {
            if (isGuanShaStrong(data)) {
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
                data.xiShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_CAI,
                    ShiShen.SHISHEN_PIAN_CAI,
                    ShiShen.SHISHEN_SHI_SHEN,
                    ShiShen.SHISHEN_SHANG_GUAN
                )
            } else {
                data.yongShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_CAI,
                    ShiShen.SHISHEN_PIAN_CAI,
                    ShiShen.SHISHEN_SHI_SHEN,
                    ShiShen.SHISHEN_SHANG_GUAN
                )
            }
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
            if(isBiJieStrong(data)){
                data.yongShenList = listOf(ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
                data.jiShenList = listOf(ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI)
            }else{
                data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_CAI, ShiShen.SHISHEN_PIAN_CAI)
                data.xiShenList = listOf(ShiShen.SHISHEN_SHI_SHEN, ShiShen.SHISHEN_SHANG_GUAN)
                data.jiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN,
                )
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
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
                data.jiShenList = listOf(
                    ShiShen.SHISHEN_ZHENG_CAI,
                    ShiShen.SHISHEN_PIAN_CAI
                )
            } else {
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
                data.xiShenList = listOf(ShiShen.SHISHEN_ZHENG_GUAN, ShiShen.SHISHEN_QI_SHA)
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

        shiShangCount = ShiShenUtil().getShiShenCount(
            data,
            ShiShen.SHISHEN_SHI_SHEN
        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_SHANG_GUAN)
        guanShaCount = ShiShenUtil().getShiShenCount(
            data,
            ShiShen.SHISHEN_ZHENG_GUAN
        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_QI_SHA)
        caiCount = ShiShenUtil().getShiShenCount(
            data,
            ShiShen.SHISHEN_ZHENG_CAI
        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_CAI)
        yinCount = ShiShenUtil().getShiShenCount(
            data,
            ShiShen.SHISHEN_ZHENG_YIN
        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_PIAN_YIN)
        biJieCount = ShiShenUtil().getShiShenCount(
            data,
            ShiShen.SHISHEN_BI_JIAN
        ) + ShiShenUtil().getShiShenCount(data, ShiShen.SHISHEN_JIE_CAI)

        var goodElements = yinCount + biJieCount
        var badElements = shiShangCount + guanShaCount + caiCount
        var isStrong = false
        if (goodElements >= 3) {
            isStrong = true
        }
        if (isStrong) {
            data.yongShenList = listOf(ShiShen.SHISHEN_QI_SHA)
            data.xiShenList = listOf(
                ShiShen.SHISHEN_ZHENG_GUAN
            )
            data.jiShenList = listOf(
                ShiShen.SHISHEN_SHI_SHEN,
                ShiShen.SHISHEN_SHANG_GUAN
            )
        } else {
            data.yongShenList = listOf(ShiShen.SHISHEN_ZHENG_YIN, ShiShen.SHISHEN_PIAN_YIN)
            data.xiShenList = listOf(
                ShiShen.SHISHEN_BI_JIAN, ShiShen.SHISHEN_JIE_CAI
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

    fun calculateGeJuXiJi(gj: BaziGeJu, data: BaziData) {
        if (gj == BaziGeJu.GJ_SHANG_GUAN) {
            checkShangGuanGJXiJi(gj, data)
        } else if (gj == BaziGeJu.GJ_PIAN_YIN) {
//            checkYinGJXiJi(gj, data)
            checkZhengYinGJXiJi(data)
        } else if (gj == BaziGeJu.GJ_ZHENG_YIN) {
            checkZhengYinGJXiJi(data)
        } else if (gj == BaziGeJu.GJ_ZHENG_GUAN) {
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
        } else if (gj == BaziGeJu.GJ_YAN_SHANG) {
            checkYanShangGJXiJi(data)
        } else if (gj == BaziGeJu.GJ_RUN_XIA) {
            checkRunXiaGJXiJi(data)
        } else if (gj == BaziGeJu.GJ_JIA_SE) {
            checkJiaSeGJXiJi(data)
        } else if (gj == BaziGeJu.GJ_CONG_GE) {
            checkCongGeGJXiJi(data)
        } else if (gj == BaziGeJu.GJ_YANG_REN) {
            checkYangRenGJXiJi(data)
        } else if (gj == BaziGeJu.GJ_CONG_CAI) {
            checkCongCaiGJXiJi(data)
        } else if (gj == BaziGeJu.GJ_CONG_SHA) {
            checkCongShaGJXiJi(data)
        } else if (gj == BaziGeJu.GJ_CONG_ER) {
            checkCongErGJXiJi(data)
        } else {
            //todo other GeJu
        }
    }

    fun addAnalysisString(sb: StringBuilder, str: String) {
        if (sb.isNotEmpty()) {
            sb.append(" ")
        }
        sb.append(str)
    }

    @Composable
    fun getZhengYinAnalysisString(data: BaziData): String {
        var sb = StringBuilder()
        addAnalysisString(sb, ZhengYinUtil().getZhengYinDesc1(data))
        addAnalysisString(sb, ZhengYinUtil().getZhengYinDesc2(data))
        addAnalysisString(sb, ZhengYinUtil().getZhengYinDesc3(data))
        addAnalysisString(sb, ZhengYinUtil().getZhengYinDesc4(data))
        addAnalysisString(sb, ZhengYinUtil().getZhengYinDesc5(data))
        addAnalysisString(sb, ZhengYinUtil().getZhengYinDesc6(data))
        return sb.toString()
    }

    fun isXiYongShen(data: BaziData, ss: ShiShen): Boolean {
        var ret = false

        for (shishen in data.yongShenList) {
            if (shishen == ss) return true
        }
        for (shishen in data.xiShenList) {
            if (shishen == ss) return true
        }
        for (shishen in data.tiaohouShenList) {
            if (shishen == ss) return true
        }
        for (shishen in data.tongguanShenList) {
            if (shishen == ss) return true
        }
        return ret
    }

    fun isJiShen(data: BaziData, ss: ShiShen): Boolean {
        var ret = false
        for (shishen in data.jiShenList) {
            if (shishen == ss) return true
        }
        return ret
    }

    @Composable
    fun getZhengCaiAnalysisString(gj: BaziGeJu, data: BaziData): String {
        var sb = StringBuilder()
        addAnalysisString(sb, ZhengCaiUtil().getZhengCaiDesc1(data))
        addAnalysisString(sb, ZhengCaiUtil().getZhengCaiDesc2(data))
        addAnalysisString(sb, ZhengCaiUtil().getZhengCaiDesc3(data))
        return sb.toString()
    }

    @Composable
    fun getPianCaiAnalysisString(gj: BaziGeJu, data: BaziData): String {
        var count = 0
        var isTGPianCai = false
        var sb = StringBuilder()
        var pianCaiTG = ShiShenUtil().getPiancai(data.dayTiangan)
        var cangganArray = DiZhiUtil().getCanggan(data.yearDizhi)
        if (data.yearTiangan == pianCaiTG) count += 1
        if (data.monthTiangan == pianCaiTG) count += 1
        if (data.hourTiangan == pianCaiTG) count += 1

        if (count > 0) isTGPianCai = true

        for (tg in cangganArray) {
            if (tg == pianCaiTG) count += 1
        }
        cangganArray = DiZhiUtil().getCanggan(data.monthDizhi)
        for (tg in cangganArray) {
            if (tg == pianCaiTG) count += 1
        }
        cangganArray = DiZhiUtil().getCanggan(data.dayDizhi)
        if (pianCaiTG == cangganArray[0]) {
            if (data.gender == MALE) {
                sb.append(stringResource(R.string.app_bazi_gj_piancai_desc4))
                sb.append(" ")
            }
        }

        for (tg in cangganArray) {
            if (tg == pianCaiTG) count += 1
        }
        cangganArray = DiZhiUtil().getCanggan(data.hourDizhi)
        for (tg in cangganArray) {
            if (tg == pianCaiTG) count += 1
        }
        if (isTGPianCai && cangganArray[0] == pianCaiTG) {
            sb.append(stringResource(R.string.app_bazi_gj_piancai_desc5))
            sb.append(" ")
        }

        if (gj == BaziGeJu.GJ_PIAN_CAI && data.gender == FEMALE) {
            sb.append(stringResource(R.string.app_bazi_gj_piancai_desc3))
            sb.append(" ")
        }

        if (count >= 2) {
            sb.append(stringResource(R.string.app_bazi_gj_piancai_desc1))
            sb.append(" ")
            if (data.gender == MALE) {
                sb.append(stringResource(R.string.app_bazi_gj_piancai_desc2))
                sb.append(" ")
            }
        }
        return sb.toString()
    }

    @Composable
    fun getGeJuAnalysis(gj: BaziGeJu, data: BaziData): String {
        var str = stringResource(R.string.app_bazi_gj_analysis_no)
        var tg = data.yearTiangan
        var sb = StringBuilder()

        sb.append(getZhengYinAnalysisString(data))
        if (sb.length > 0) sb.append(" ")
        sb.append(getPianCaiAnalysisString(gj, data))
        sb.append(getZhengCaiAnalysisString(gj, data))

        if (sb.length > 0) {
            return sb.toString()
        } else {
            return str
        }
    }
}