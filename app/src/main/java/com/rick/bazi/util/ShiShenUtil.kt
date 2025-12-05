package com.rick.bazi.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rick.bazi.R
import com.rick.bazi.data.BaziData
import com.rick.bazi.data.BaziInfo
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.ShiShen
import com.rick.bazi.data.TianGan
import com.rick.bazi.ui.BaziViewModel

class ShiShenUtil {
    val shishenMap: Map<TianGan, Array<TianGan>> = mapOf(
        //[ShiShen] ZhengCai || PianCai || ZhengGuan || Qisha || ZhengYin || PianYin || ShiShen || ShangGuan || BiJian || JieCai
        //[Index]    0  1  2  3  4  5  6  7  8  9
        TianGan.TIANGAN_JIA to arrayOf(
            TianGan.TIANGAN_JI,
            TianGan.TIANGAN_WU,
            TianGan.TIANGAN_XIN,
            TianGan.TIANGAN_GENG,
            TianGan.TIANGAN_GUI,
            TianGan.TIANGAN_REN,
            TianGan.TIANGAN_BING,
            TianGan.TIANGAN_DING,
            TianGan.TIANGAN_JIA,
            TianGan.TIANGAN_YI
        ),
        TianGan.TIANGAN_YI to arrayOf(
            TianGan.TIANGAN_WU,
            TianGan.TIANGAN_JI,
            TianGan.TIANGAN_GENG,
            TianGan.TIANGAN_XIN,
            TianGan.TIANGAN_REN,
            TianGan.TIANGAN_GUI,
            TianGan.TIANGAN_DING,
            TianGan.TIANGAN_BING,
            TianGan.TIANGAN_YI,
            TianGan.TIANGAN_JIA
        ),
        TianGan.TIANGAN_BING to arrayOf(
            TianGan.TIANGAN_XIN,
            TianGan.TIANGAN_GENG,
            TianGan.TIANGAN_GUI,
            TianGan.TIANGAN_REN,
            TianGan.TIANGAN_YI,
            TianGan.TIANGAN_JIA,
            TianGan.TIANGAN_WU,
            TianGan.TIANGAN_JI,
            TianGan.TIANGAN_BING,
            TianGan.TIANGAN_DING
        ),
        TianGan.TIANGAN_DING to arrayOf(
            TianGan.TIANGAN_GENG,
            TianGan.TIANGAN_XIN,
            TianGan.TIANGAN_REN,
            TianGan.TIANGAN_GUI,
            TianGan.TIANGAN_JIA,
            TianGan.TIANGAN_YI,
            TianGan.TIANGAN_JI,
            TianGan.TIANGAN_WU,
            TianGan.TIANGAN_DING,
            TianGan.TIANGAN_BING
        ),
        TianGan.TIANGAN_WU to arrayOf(
            TianGan.TIANGAN_GUI,
            TianGan.TIANGAN_REN,
            TianGan.TIANGAN_YI,
            TianGan.TIANGAN_JIA,
            TianGan.TIANGAN_DING,
            TianGan.TIANGAN_BING,
            TianGan.TIANGAN_GENG,
            TianGan.TIANGAN_XIN,
            TianGan.TIANGAN_WU,
            TianGan.TIANGAN_JI
        ),
        TianGan.TIANGAN_JI to arrayOf(
            TianGan.TIANGAN_REN,
            TianGan.TIANGAN_GUI,
            TianGan.TIANGAN_JIA,
            TianGan.TIANGAN_YI,
            TianGan.TIANGAN_BING,
            TianGan.TIANGAN_DING,
            TianGan.TIANGAN_XIN,
            TianGan.TIANGAN_GENG,
            TianGan.TIANGAN_JI,
            TianGan.TIANGAN_WU
        ),
        TianGan.TIANGAN_GENG to arrayOf(
            TianGan.TIANGAN_YI,
            TianGan.TIANGAN_JIA,
            TianGan.TIANGAN_DING,
            TianGan.TIANGAN_BING,
            TianGan.TIANGAN_JI,
            TianGan.TIANGAN_WU,
            TianGan.TIANGAN_REN,
            TianGan.TIANGAN_GUI,
            TianGan.TIANGAN_GENG,
            TianGan.TIANGAN_XIN
        ),
        TianGan.TIANGAN_XIN to arrayOf(
            TianGan.TIANGAN_JIA,
            TianGan.TIANGAN_YI,
            TianGan.TIANGAN_BING,
            TianGan.TIANGAN_DING,
            TianGan.TIANGAN_WU,
            TianGan.TIANGAN_JI,
            TianGan.TIANGAN_GUI,
            TianGan.TIANGAN_REN,
            TianGan.TIANGAN_XIN,
            TianGan.TIANGAN_GENG
        ),
        TianGan.TIANGAN_REN to arrayOf(
            TianGan.TIANGAN_DING,
            TianGan.TIANGAN_BING,
            TianGan.TIANGAN_JI,
            TianGan.TIANGAN_WU,
            TianGan.TIANGAN_XIN,
            TianGan.TIANGAN_GENG,
            TianGan.TIANGAN_JIA,
            TianGan.TIANGAN_YI,
            TianGan.TIANGAN_REN,
            TianGan.TIANGAN_GUI
        ),
        TianGan.TIANGAN_GUI to arrayOf(
            TianGan.TIANGAN_BING,
            TianGan.TIANGAN_DING,
            TianGan.TIANGAN_WU,
            TianGan.TIANGAN_JI,
            TianGan.TIANGAN_GENG,
            TianGan.TIANGAN_XIN,
            TianGan.TIANGAN_YI,
            TianGan.TIANGAN_JIA,
            TianGan.TIANGAN_GUI,
            TianGan.TIANGAN_REN
        )
    )

    fun getShiShenTianGan(ss : ShiShen, dayTg : TianGan) : TianGan{
        //[ShiShen] ZhengCai || PianCai || ZhengGuan || Qisha || ZhengYin || PianYin || ShiShen || ShangGuan || BiJian || JieCai
        //[Index]    0  1  2  3  4  5  6  7  8  9
        var tg = TianGan.TIANGAN_JIA
        if(ss == ShiShen.SHISHEN_ZHENG_CAI){
            tg = getZhengcai(dayTg)
        }else if(ss == ShiShen.SHISHEN_PIAN_CAI){
            tg = getPiancai(dayTg)
        }else if(ss == ShiShen.SHISHEN_ZHENG_GUAN){
            tg = getZhengguan(dayTg)
        }else if(ss == ShiShen.SHISHEN_QI_SHA){
            tg = getQisha(dayTg)
        }else if(ss == ShiShen.SHISHEN_ZHENG_YIN){
            tg = getZhengyin(dayTg)
        }else if(ss == ShiShen.SHISHEN_PIAN_YIN){
            tg = getPianyin(dayTg)
        }else if(ss == ShiShen.SHISHEN_SHI_SHEN){
            tg = getShishen(dayTg)
        }else if(ss == ShiShen.SHISHEN_SHANG_GUAN){
            tg = getShangguan(dayTg)
        }else if(ss == ShiShen.SHISHEN_BI_JIAN){
            tg = getBijian(dayTg)
        }else if(ss == ShiShen.SHISHEN_JIE_CAI){
            tg = getJiecai(dayTg)
        }
        return tg
    }

    fun getZhengcai(tg: TianGan): TianGan {
        var ret = shishenMap.get(tg)!![0]
        return ret
    }

    fun getPiancai(tg: TianGan): TianGan {
        var ret = shishenMap.get(tg)!![1]
        return ret
    }

    fun getZhengguan(tg: TianGan): TianGan {
        var ret = shishenMap.get(tg)!![2]
        return ret
    }

    fun getQisha(tg: TianGan): TianGan {
        var ret = shishenMap.get(tg)!![3]
        return ret
    }

    fun getZhengyin(tg: TianGan): TianGan {
        var ret = shishenMap.get(tg)!![4]
        return ret
    }

    fun getPianyin(tg: TianGan): TianGan {
        var ret = shishenMap.get(tg)!![5]
        return ret
    }

    fun getShishen(tg: TianGan): TianGan {
        var ret = shishenMap.get(tg)!![6]
        return ret
    }

    fun getShangguan(tg: TianGan): TianGan {
        var ret = shishenMap.get(tg)!![7]
        return ret
    }

    fun getBijian(tg: TianGan): TianGan {
        var ret = shishenMap.get(tg)!![8]
        return ret
    }

    fun getJiecai(tg: TianGan): TianGan {
        var ret = shishenMap.get(tg)!![9]
        return ret
    }

    fun getShiShen(tg: TianGan, dayTg: TianGan): ShiShen {
        var ss : ShiShen = ShiShen.SHISHEN_BI_JIAN
        if (tg == getBijian(dayTg)) {
            ss = ShiShen.SHISHEN_BI_JIAN
        } else if (tg == getJiecai(dayTg)) {
            ss = ShiShen.SHISHEN_JIE_CAI
        } else if (tg == getShishen(dayTg)) {
            ss = ShiShen.SHISHEN_SHI_SHEN
        } else if (tg == getShangguan(dayTg)) {
            ss = ShiShen.SHISHEN_SHANG_GUAN
        } else if (tg == getZhengyin(dayTg)) {
            ss = ShiShen.SHISHEN_ZHENG_YIN
        } else if (tg == getPianyin(dayTg)) {
            ss = ShiShen.SHISHEN_PIAN_YIN
        } else if (tg == getZhengcai(dayTg)) {
            ss = ShiShen.SHISHEN_ZHENG_CAI
        } else if (tg == getPiancai(dayTg)) {
            ss = ShiShen.SHISHEN_PIAN_CAI
        } else if (tg == getZhengguan(dayTg)) {
            ss = ShiShen.SHISHEN_ZHENG_GUAN
        } else if (tg == getQisha(dayTg)) {
            ss = ShiShen.SHISHEN_QI_SHA
        }
        return ss
    }


    @Composable
    fun getShiShenLabel(ss : ShiShen) : String{
        var str = stringResource(R.string.ts_bijian)

        if (ss == ShiShen.SHISHEN_BI_JIAN) {
            str = stringResource(R.string.ts_bijian)
        } else if (ss == ShiShen.SHISHEN_JIE_CAI) {
            str = stringResource(R.string.ts_jiecai)
        } else if (ss == ShiShen.SHISHEN_SHI_SHEN) {
            str = stringResource(R.string.ts_shishen)
        } else if (ss == ShiShen.SHISHEN_SHANG_GUAN) {
            str = stringResource(R.string.ts_shangguan)
        } else if (ss == ShiShen.SHISHEN_ZHENG_YIN) {
            str = stringResource(R.string.ts_zhengyin)
        } else if (ss == ShiShen.SHISHEN_PIAN_YIN) {
            str = stringResource(R.string.ts_pianyin)
        } else if (ss == ShiShen.SHISHEN_ZHENG_CAI) {
            str = stringResource(R.string.ts_zhengcai)
        } else if (ss == ShiShen.SHISHEN_PIAN_CAI) {
            str = stringResource(R.string.ts_piancai)
        } else if (ss == ShiShen.SHISHEN_ZHENG_GUAN) {
            str = stringResource(R.string.ts_zhengguan)
        } else if (ss == ShiShen.SHISHEN_QI_SHA) {
            str = stringResource(R.string.ts_qisha)
        }

        return str
    }

    @Composable
    fun getShiShenText(tg: TianGan, dayTg: TianGan): String {
        var ss : ShiShen = getShiShen(tg, dayTg)
        return getShiShenLabel(ss)
    }

    fun getMainTianGanFromDizhi(dz: DiZhi, dayTg: TianGan) : TianGan{
        var dzMainStar = TianGan.TIANGAN_JIA
        if(dz == DiZhi.DIZHI_ZI){
            dzMainStar = TianGan.TIANGAN_GUI
        }
        if(dz == DiZhi.DIZHI_CHOU){
            dzMainStar = TianGan.TIANGAN_JI
        }
        if(dz == DiZhi.DIZHI_YIN){
            dzMainStar = TianGan.TIANGAN_JIA
        }
        if(dz == DiZhi.DIZHI_MOU){
            dzMainStar = TianGan.TIANGAN_YI
        }
        if(dz == DiZhi.DIZHI_CHEN){
            dzMainStar = TianGan.TIANGAN_WU
        }
        if(dz == DiZhi.DIZHI_SI){
            dzMainStar = TianGan.TIANGAN_BING
        }
        if(dz == DiZhi.DIZHI_WU){
            dzMainStar = TianGan.TIANGAN_DING
        }
        if(dz == DiZhi.DIZHI_WEI){
            dzMainStar = TianGan.TIANGAN_JI
        }
        if(dz == DiZhi.DIZHI_SHEN){
            dzMainStar = TianGan.TIANGAN_GENG
        }
        if(dz == DiZhi.DIZHI_YOU){
            dzMainStar = TianGan.TIANGAN_XIN
        }
        if(dz == DiZhi.DIZHI_XU){
            dzMainStar = TianGan.TIANGAN_WU
        }
        if(dz == DiZhi.DIZHI_HAI){
            dzMainStar = TianGan.TIANGAN_REN
        }
        return dzMainStar
    }


//    @Composable
    fun getDiZhiMainTianGan(dz: DiZhi, dayTg: TianGan) : TianGan{
        var dzMainStar = TianGan.TIANGAN_JIA
        if(dz == DiZhi.DIZHI_ZI){
            dzMainStar = TianGan.TIANGAN_GUI
        }
        if(dz == DiZhi.DIZHI_CHOU){
            dzMainStar = TianGan.TIANGAN_JI
        }
        if(dz == DiZhi.DIZHI_YIN){
            dzMainStar = TianGan.TIANGAN_JIA
        }
        if(dz == DiZhi.DIZHI_MOU){
            dzMainStar = TianGan.TIANGAN_YI
        }
        if(dz == DiZhi.DIZHI_CHEN){
            dzMainStar = TianGan.TIANGAN_WU
        }
        if(dz == DiZhi.DIZHI_SI){
            dzMainStar = TianGan.TIANGAN_BING
        }
        if(dz == DiZhi.DIZHI_WU){
            dzMainStar = TianGan.TIANGAN_DING
        }
        if(dz == DiZhi.DIZHI_WEI){
            dzMainStar = TianGan.TIANGAN_JI
        }
        if(dz == DiZhi.DIZHI_SHEN){
            dzMainStar = TianGan.TIANGAN_GENG
        }
        if(dz == DiZhi.DIZHI_YOU){
            dzMainStar = TianGan.TIANGAN_XIN
        }
        if(dz == DiZhi.DIZHI_XU){
            dzMainStar = TianGan.TIANGAN_WU
        }
        if(dz == DiZhi.DIZHI_HAI){
            dzMainStar = TianGan.TIANGAN_REN
        }
        return dzMainStar
    }

    fun getShiShenFromDizhi(dz: DiZhi, dayTg: TianGan) : ShiShen{
        var ss : ShiShen = ShiShen.SHISHEN_BI_JIAN
        var dzMainStar = getMainTianGanFromDizhi(dz, dayTg)
        return getShiShen(dzMainStar, dayTg)
    }

//    @Composable
    fun getDiZhiShiShen(dz: DiZhi, dayTg: TianGan) : ShiShen{
        var ss : ShiShen = ShiShen.SHISHEN_BI_JIAN
        var dzMainStar = getDiZhiMainTianGan(dz, dayTg)
        return getShiShen(dzMainStar, dayTg)
    }

    @Composable
    fun getDiZhiShiShenText(dz: DiZhi, dayTg: TianGan) : String{
        var dzMainStar = getDiZhiMainTianGan(dz, dayTg)

        return getShiShenText(dzMainStar, dayTg)
    }

    @Composable
    fun getCangganStarLabel(tgArray: Array<TianGan>, dayTg: TianGan): String {
        val builder = StringBuilder()
        if (tgArray.size == 1) {
            builder.append(getShiShenText(tgArray[0], dayTg))
        } else if (tgArray.size == 2) {
            builder.append(getShiShenText(tgArray[0], dayTg))
            builder.append("\n")
            builder.append(getShiShenText(tgArray[1], dayTg))
        } else if (tgArray.size == 3) {
            builder.append(getShiShenText(tgArray[0], dayTg))
            builder.append("\n")
            builder.append(getShiShenText(tgArray[1], dayTg))
            builder.append("\n")
            builder.append(getShiShenText(tgArray[2], dayTg))
        }
        return builder.toString()
    }

    @Composable
    fun isYin(ss : ShiShen) : Boolean{
        if(ss == ShiShen.SHISHEN_ZHENG_YIN || ss == ShiShen.SHISHEN_PIAN_YIN)return true
        return false
    }

    @Composable
    fun isBiJie(ss : ShiShen) : Boolean{
        if(ss == ShiShen.SHISHEN_BI_JIAN || ss == ShiShen.SHISHEN_JIE_CAI)return true
        return false
    }

    @Composable
    fun isGuanSha(ss : ShiShen) : Boolean{
        if(ss == ShiShen.SHISHEN_ZHENG_GUAN || ss == ShiShen.SHISHEN_QI_SHA)return true
        return false
    }

    @Composable
    fun isShiShang(ss : ShiShen) : Boolean{
        if(ss == ShiShen.SHISHEN_SHI_SHEN || ss == ShiShen.SHISHEN_SHANG_GUAN)return true
        return false
    }

    @Composable
    fun isCai(ss : ShiShen) : Boolean{
        if(ss == ShiShen.SHISHEN_ZHENG_CAI || ss == ShiShen.SHISHEN_PIAN_CAI)return true
        return false
    }

    @Composable
    fun getBaziShiShengString(baziInfo: BaziInfo, baziModel: BaziViewModel) : String{
        var str = ""
        var data = baziInfo.baziData
        var shishenYearStr = stringResource(R.string.bazi_year) + ": " + getTianganStr(
            baziInfo,
            baziInfo.yearTiangan
        ) + "(" + ShiShenUtil().getShiShenText(
            baziInfo.yearTiangan,
            baziInfo.dayTiangan
        ) + ") 、 " + getDizhiStr(
            baziInfo,
            baziInfo.yearDizhi
        ) + "(" + ShiShenUtil().getDiZhiShiShenText(baziInfo.yearDizhi, baziInfo.dayTiangan) + ")"
        baziModel.setShishenYearStr(shishenYearStr)
        baziModel.setYearTgShiShen(
            ShiShenUtil().getShiShen(
                baziInfo.yearTiangan,
                baziInfo.dayTiangan
            )
        )
        baziModel.setYearDzShiShen(
            ShiShenUtil().getDiZhiShiShen(
                baziInfo.yearDizhi,
                baziInfo.dayTiangan
            )
        )

        var shishenMonthStr = stringResource(R.string.bazi_month) + ": " + getTianganStr(
            baziInfo,
            baziInfo.monthTiangan
        ) + "(" + ShiShenUtil().getShiShenText(
            baziInfo.monthTiangan,
            baziInfo.dayTiangan
        ) + ") 、 " + BaziUtil().getDizhiText(
            baziInfo,
            baziInfo.monthDizhi
        ) + "(" + ShiShenUtil().getDiZhiShiShenText(baziInfo.monthDizhi, baziInfo.dayTiangan) + ")"
        baziModel.setShishenMonthStr(shishenMonthStr)
        baziModel.setMonthTgShiShen(
            ShiShenUtil().getShiShen(
                baziInfo.monthTiangan,
                baziInfo.dayTiangan
            )
        )
        baziModel.setMonthDzShiShen(
            ShiShenUtil().getDiZhiShiShen(
                baziInfo.monthDizhi,
                baziInfo.dayTiangan
            )
        )

        var shishenDayStr = stringResource(R.string.bazi_day) + ": " + getTianganStr(
            baziInfo,
            baziInfo.dayTiangan
        ) + "(" + stringResource(R.string.bazi_owner) + ") 、 " + getDizhiStr(
            baziInfo,
            baziInfo.dayDizhi
        ) + "(" + ShiShenUtil().getDiZhiShiShenText(baziInfo.dayDizhi, baziInfo.dayTiangan) + ")"
        baziModel.setShishenDayStr(shishenDayStr)
        baziModel.setDayDzShiShen(
            ShiShenUtil().getDiZhiShiShen(
                baziInfo.dayDizhi,
                baziInfo.dayTiangan
            )
        )


        var shishenHourStr = stringResource(R.string.bazi_hour) + ": " + getTianganStr(
            baziInfo,
            baziInfo.hourTiangan
        ) + "(" + ShiShenUtil().getShiShenText(
            baziInfo.hourTiangan,
            baziInfo.dayTiangan
        ) + ") 、 " + getDizhiStr(
            baziInfo,
            baziInfo.hourDizhi
        ) + "(" + ShiShenUtil().getDiZhiShiShenText(baziInfo.hourDizhi, baziInfo.dayTiangan) + ")"
        baziModel.setShishenHourStr(shishenHourStr)
        baziModel.setHourTgShiShen(
            ShiShenUtil().getShiShen(
                baziInfo.hourTiangan,
                baziInfo.dayTiangan
            )
        )
        baziModel.setHourDzShiShen(
            ShiShenUtil().getDiZhiShiShen(
                baziInfo.hourDizhi,
                baziInfo.dayTiangan
            )
        )
        return str
    }

    fun getShiShenCount(data : BaziData, targetShiShen : ShiShen) : Int{
        var count = 0
        var ss = getShiShen(data.yearTiangan, data.dayTiangan)
        if(ss == targetShiShen){
            count = count + 1
        }
        ss = getShiShen(data.monthTiangan, data.dayTiangan)
        if(ss == targetShiShen){
            count = count + 1
        }
        ss = getShiShen(data.hourTiangan, data.dayTiangan)
        if(ss == targetShiShen){
            count = count + 1
        }
        ss = getShiShenFromDizhi(data.yearDizhi, data.dayTiangan)
//        ss = getShiShen(data.hourTiangan, data.dayTiangan)
        if(ss == targetShiShen){
            count = count + 1
        }
        ss = getShiShenFromDizhi(data.monthDizhi, data.dayTiangan)
        if(ss == targetShiShen){
            count = count + 1
        }
        ss = getShiShenFromDizhi(data.dayDizhi, data.dayTiangan)
        if(ss == targetShiShen){
            count = count + 1
        }
        ss = getShiShenFromDizhi(data.hourDizhi, data.dayTiangan)
        if(ss == targetShiShen){
            count = count + 1
        }
        return count
    }

    @Composable
    fun getBaziShiShenStatString(data : BaziData, targetShiShen : ShiShen) : String{
        val sb = StringBuilder()
        var tg = data.yearTiangan
        var dz = data.yearDizhi

        var ss = getShiShen(tg, data.dayTiangan)
        if(ss == targetShiShen){
            sb.append(stringResource(R.string.app_label_year))
            sb.append(stringResource(R.string.app_label_gan))
            sb.append(" ")
            sb.append(WuXingUtil().getTianGanWuXingText(tg))
            sb.append("(")
            sb.append(getShiShenText(tg, data.dayTiangan))
            sb.append(") ")
        }
        tg = data.monthTiangan
        ss = getShiShen(tg, data.dayTiangan)
        if(ss == targetShiShen){
            sb.append(stringResource(R.string.app_label_month))
            sb.append(stringResource(R.string.app_label_gan))
            sb.append(" ")
            sb.append(WuXingUtil().getTianGanWuXingText(tg))
            sb.append("(")
            sb.append(getShiShenText(tg, data.dayTiangan))
            sb.append(") ")
        }
        tg = data.hourTiangan
        ss = getShiShen(tg, data.dayTiangan)
        if(ss == targetShiShen){
            sb.append(stringResource(R.string.app_label_hour))
            sb.append(stringResource(R.string.app_label_gan))
            sb.append(" ")
            sb.append(WuXingUtil().getTianGanWuXingText(tg))
            sb.append("(")
            sb.append(getShiShenText(tg, data.dayTiangan))
            sb.append(") ")
        }

        //check dizhi
        dz = data.yearDizhi
        ss = getShiShenFromDizhi(dz, data.dayTiangan)
        if(ss == targetShiShen){
            sb.append(stringResource(R.string.app_label_year))
            sb.append(stringResource(R.string.app_label_zhi))
            sb.append(" ")
            sb.append(BaziUtil().getDizhiText(dz))
            sb.append(WuXingUtil().getDiZhiWuxingText(dz))
            sb.append("(")
            sb.append(getDiZhiShiShenText(dz, data.dayTiangan))
            sb.append(") ")
        }
        dz = data.monthDizhi
        ss = getShiShenFromDizhi(dz, data.dayTiangan)
        if(ss == targetShiShen){
            sb.append(stringResource(R.string.app_label_month))
            sb.append(stringResource(R.string.app_label_zhi))
            sb.append(" ")
            sb.append(BaziUtil().getDizhiText(dz))
            sb.append(WuXingUtil().getDiZhiWuxingText(dz))
            sb.append("(")
            sb.append(getDiZhiShiShenText(dz, data.dayTiangan))
            sb.append(") ")
        }
        dz = data.dayDizhi
        ss = getShiShenFromDizhi(dz, data.dayTiangan)
        if(ss == targetShiShen){
            sb.append(stringResource(R.string.app_label_day))
            sb.append(stringResource(R.string.app_label_zhi))
            sb.append(" ")
            sb.append(BaziUtil().getDizhiText(dz))
            sb.append(WuXingUtil().getDiZhiWuxingText(dz))
            sb.append("(")
            sb.append(getDiZhiShiShenText(dz, data.dayTiangan))
            sb.append(") ")
        }
        dz = data.hourDizhi
        ss = getShiShenFromDizhi(dz, data.dayTiangan)
        if(ss == targetShiShen){
            sb.append(stringResource(R.string.app_label_hour))
            sb.append(stringResource(R.string.app_label_zhi))
            sb.append(" ")
            sb.append(BaziUtil().getDizhiText(dz))
            sb.append(WuXingUtil().getDiZhiWuxingText(dz))
            sb.append("(")
            sb.append(getDiZhiShiShenText(dz, data.dayTiangan))
            sb.append(") ")
        }

        val finalString = sb.toString()
        return finalString
    }
}
