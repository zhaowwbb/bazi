package com.rick.bazi.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rick.bazi.R
import com.rick.bazi.data.BaziData
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.WuXing

class DiZhiUtil {
    val sanHeMap : Map<DiZhi, WuXing> = mapOf(
        DiZhi.DIZHI_YIN to WuXing.WUXING_HUO,
        DiZhi.DIZHI_WU to WuXing.WUXING_HUO,
        DiZhi.DIZHI_XU to WuXing.WUXING_HUO,

        DiZhi.DIZHI_SHEN to WuXing.WUXING_SHUI,
        DiZhi.DIZHI_ZI to WuXing.WUXING_SHUI,
        DiZhi.DIZHI_CHEN to WuXing.WUXING_SHUI,

        DiZhi.DIZHI_HAI to WuXing.WUXING_MU,
        DiZhi.DIZHI_MOU to WuXing.WUXING_MU,
        DiZhi.DIZHI_WEI to WuXing.WUXING_MU,

        DiZhi.DIZHI_SI to WuXing.WUXING_JIN,
        DiZhi.DIZHI_YOU to WuXing.WUXING_JIN,
        DiZhi.DIZHI_CHOU to WuXing.WUXING_JIN
    )

    val sanHuiMap : Map<DiZhi, WuXing> = mapOf(
        DiZhi.DIZHI_SI to WuXing.WUXING_HUO,
        DiZhi.DIZHI_WU to WuXing.WUXING_HUO,
        DiZhi.DIZHI_WEI to WuXing.WUXING_HUO,

        DiZhi.DIZHI_HAI to WuXing.WUXING_SHUI,
        DiZhi.DIZHI_ZI to WuXing.WUXING_SHUI,
        DiZhi.DIZHI_CHOU to WuXing.WUXING_SHUI,

        DiZhi.DIZHI_YIN to WuXing.WUXING_MU,
        DiZhi.DIZHI_MOU to WuXing.WUXING_MU,
        DiZhi.DIZHI_CHEN to WuXing.WUXING_MU,

        DiZhi.DIZHI_SHEN to WuXing.WUXING_JIN,
        DiZhi.DIZHI_YOU to WuXing.WUXING_JIN,
        DiZhi.DIZHI_XU to WuXing.WUXING_JIN
    )

    fun isDiZhiSanHe(dz1 : DiZhi, dz2 : DiZhi, dz3 : DiZhi) : Boolean{
        var ret = false
        //sort 3 DiZhi
        var dzSmall = dz1
        var dzMedium= dz2
        var dzLarge = dz1
        if(dz2 < dzSmall){
            dzSmall = dz2
        }
        if(dz3 < dzSmall){
            dzSmall = dz3
        }
        if(dz2 > dzLarge){
            dzLarge = dz2
        }
        if(dz3 > dzLarge){
            dzLarge = dz3
        }
        if(dz1 != dzSmall && dz1 != dzLarge){
            dzMedium = dz1
        }
        if(dz2 != dzSmall && dz2 != dzLarge){
            dzMedium = dz2
        }
        if(dz3 != dzSmall && dz3 != dzLarge){
            dzMedium = dz3
        }

        //寅午戌三合火局
        if(dzSmall == DiZhi.DIZHI_YIN && dzMedium == DiZhi.DIZHI_WU && dzLarge == DiZhi.DIZHI_XU){
            ret = true
        }
        //申子辰三合水局
        if(dzSmall == DiZhi.DIZHI_ZI && dzMedium == DiZhi.DIZHI_CHEN && dzLarge == DiZhi.DIZHI_SHEN){
            ret = true
        }
        //亥卯未三合木局
        if(dzSmall == DiZhi.DIZHI_MOU && dzMedium == DiZhi.DIZHI_WEI && dzLarge == DiZhi.DIZHI_HAI){
            ret = true
        }
        //巳酉丑三合金局
        if(dzSmall == DiZhi.DIZHI_CHOU && dzMedium == DiZhi.DIZHI_SI && dzLarge == DiZhi.DIZHI_YOU){
            ret = true
        }
        return ret
    }

    fun isDiZhiSanHui(dz1 : DiZhi, dz2 : DiZhi, dz3 : DiZhi) : Boolean{
        var ret = false
        //sort 3 DiZhi
        var dzSmall = dz1
        var dzMedium= dz2
        var dzLarge = dz1
        if(dz2 < dzSmall){
            dzSmall = dz2
        }
        if(dz3 < dzSmall){
            dzSmall = dz3
        }
        if(dz2 > dzLarge){
            dzLarge = dz2
        }
        if(dz3 > dzLarge){
            dzLarge = dz3
        }
        if(dz1 != dzSmall && dz1 != dzLarge){
            dzMedium = dz1
        }
        if(dz2 != dzSmall && dz2 != dzLarge){
            dzMedium = dz2
        }
        if(dz3 != dzSmall && dz3 != dzLarge){
            dzMedium = dz3
        }

        //寅卯辰会成东方木
        if(dzSmall == DiZhi.DIZHI_YIN && dzMedium == DiZhi.DIZHI_MOU && dzLarge == DiZhi.DIZHI_CHEN){
            ret = true
        }
        //巳午未会成南方火
        if(dzSmall == DiZhi.DIZHI_SI && dzMedium == DiZhi.DIZHI_WU && dzLarge == DiZhi.DIZHI_WEI){
            ret = true
        }
        //申酉戌会成西方金
        if(dzSmall == DiZhi.DIZHI_SHEN && dzMedium == DiZhi.DIZHI_YOU && dzLarge == DiZhi.DIZHI_XU){
            ret = true
        }
        //亥子丑会成北方水
        if(dzSmall == DiZhi.DIZHI_ZI && dzMedium == DiZhi.DIZHI_CHOU && dzLarge == DiZhi.DIZHI_HAI){
            ret = true
        }
        return ret
    }

    fun hasDiZhiSanHe(data : BaziData) : Boolean{
        var isSanHe = false
        isSanHe = isDiZhiSanHe(data.yearDizhi, data.monthDizhi, data.dayDizhi)
        if(isSanHe){
            return true
        }

        isSanHe = isDiZhiSanHe(data.yearDizhi, data.monthDizhi, data.hourDizhi)
        if(isSanHe){
            return true
        }

        isSanHe = isDiZhiSanHe(data.monthDizhi, data.dayDizhi, data.hourDizhi)
        if(isSanHe){
            return true
        }

        isSanHe = isDiZhiSanHe(data.yearDizhi, data.dayDizhi, data.hourDizhi)
        if(isSanHe){
            return true
        }
        return isSanHe
    }

    fun hasDiZhiSanHui(data : BaziData) : Boolean{
        var isSanHui = false
        isSanHui = isDiZhiSanHui(data.yearDizhi, data.monthDizhi, data.dayDizhi)
        if(isSanHui){
            return true
        }

        isSanHui = isDiZhiSanHui(data.yearDizhi, data.monthDizhi, data.hourDizhi)
        if(isSanHui){
            return true
        }

        isSanHui = isDiZhiSanHui(data.monthDizhi, data.dayDizhi, data.hourDizhi)
        if(isSanHui){
            return true
        }

        isSanHui = isDiZhiSanHui(data.yearDizhi, data.dayDizhi, data.hourDizhi)
        if(isSanHui){
            return true
        }
        return isSanHui
    }

    fun getSanHeWuXing(data : BaziData) : WuXing{
        var wx = WuXing.WUXING_MU
        var wx1 = sanHeMap.get(data.yearDizhi)!!
        var wx2 = sanHeMap.get(data.monthDizhi)!!
        var wx3 = sanHeMap.get(data.dayDizhi)!!
        if(wx1 == wx2){
           return wx1
        }else if(wx1 == wx3){
            return wx3
        }else if(wx2 == wx3){
            return wx2
        }

        return wx
    }

    fun getSanHuiWuXing(data : BaziData) : WuXing{
        var wx = WuXing.WUXING_MU
        var wx1 = sanHuiMap.get(data.yearDizhi)!!
        var wx2 = sanHuiMap.get(data.monthDizhi)!!
        var wx3 = sanHuiMap.get(data.dayDizhi)!!
        if(wx1 == wx2){
            return wx1
        }else if(wx1 == wx3){
            return wx3
        }else if(wx2 == wx3){
            return wx2
        }
        return wx
    }

    @Composable
    fun getSanHeStringByWuXing(wx : WuXing) : String{
        var sb = StringBuilder()
        sb.append("    ")
        if(wx == WuXing.WUXING_HUO){
            sb.append(stringResource(R.string.app_bazi_dizhi_3he_huo_desc))
        }else if(wx == WuXing.WUXING_SHUI){
            sb.append(stringResource(R.string.app_bazi_dizhi_3he_shui_desc))
        }else if(wx == WuXing.WUXING_MU){
            sb.append(stringResource(R.string.app_bazi_dizhi_3he_mu_desc))
        }else if(wx == WuXing.WUXING_JIN){
            sb.append(stringResource(R.string.app_bazi_dizhi_3he_jin_desc))
        }
        return sb.toString()
    }

    @Composable
    fun getSanHuiStringByWuXing(wx : WuXing) : String{
        var sb = StringBuilder()
        sb.append("    ")
        if(wx == WuXing.WUXING_HUO){
            sb.append(stringResource(R.string.app_bazi_dizhi_3hui_huo_desc))
        }else if(wx == WuXing.WUXING_SHUI){
            sb.append(stringResource(R.string.app_bazi_dizhi_3hui_shui_desc))
        }else if(wx == WuXing.WUXING_MU){
            sb.append(stringResource(R.string.app_bazi_dizhi_3hui_mu_desc))
        }else if(wx == WuXing.WUXING_JIN){
            sb.append(stringResource(R.string.app_bazi_dizhi_3hui_jin_desc))
        }
        return sb.toString()
    }

    @Composable
    fun getDiZhi3HeString(data : BaziData) : String {
        var sb = StringBuilder()
        var isSanHe = false
        var wx = WuXing.WUXING_MU

        isSanHe = isDiZhiSanHe(data.yearDizhi, data.monthDizhi, data.dayDizhi)
        if(isSanHe){
            wx = sanHeMap.get(data.yearDizhi)!!
            sb.append(getSanHeStringByWuXing(wx))
        }

        isSanHe = isDiZhiSanHe(data.yearDizhi, data.monthDizhi, data.hourDizhi)
        if(isSanHe){
            wx = sanHeMap.get(data.yearDizhi)!!
            sb.append(getSanHeStringByWuXing(wx))
        }

        isSanHe = isDiZhiSanHe(data.monthDizhi, data.dayDizhi, data.hourDizhi)
        if(isSanHe){
            wx = sanHeMap.get(data.monthDizhi)!!
            sb.append(getSanHeStringByWuXing(wx))
        }

        isSanHe = isDiZhiSanHe(data.yearDizhi, data.dayDizhi, data.hourDizhi)
        if(isSanHe){
            wx = sanHeMap.get(data.yearDizhi)!!
            sb.append(getSanHeStringByWuXing(wx))
        }

        //no sanhe
        if(sb.length == 0){
            sb.append("    ")
            sb.append(stringResource(R.string.app_bazi_dizhi_3he_no_label))
        }
        return sb.toString()
    }

    @Composable
    fun getDiZhi3HuiString(data : BaziData) : String {
        var sb = StringBuilder()
        var isSanHui = false
        var wx = WuXing.WUXING_MU

        isSanHui = isDiZhiSanHui(data.yearDizhi, data.monthDizhi, data.dayDizhi)
        if(isSanHui){
            wx = sanHuiMap.get(data.yearDizhi)!!
            sb.append(getSanHuiStringByWuXing(wx))
        }

        isSanHui = isDiZhiSanHui(data.yearDizhi, data.monthDizhi, data.hourDizhi)
        if(isSanHui){
            wx = sanHuiMap.get(data.yearDizhi)!!
            sb.append(getSanHuiStringByWuXing(wx))
        }

        isSanHui = isDiZhiSanHui(data.monthDizhi, data.dayDizhi, data.hourDizhi)
        if(isSanHui){
            wx = sanHuiMap.get(data.monthDizhi)!!
            sb.append(getSanHuiStringByWuXing(wx))
        }

        isSanHui = isDiZhiSanHui(data.yearDizhi, data.dayDizhi, data.hourDizhi)
        if(isSanHui){
            wx = sanHuiMap.get(data.yearDizhi)!!
            sb.append(getSanHuiStringByWuXing(wx))
        }

        //no sanhui
        if(sb.length == 0){
            sb.append("    ")
            sb.append(stringResource(R.string.app_bazi_dizhi_3hui_no_label))
        }
        return sb.toString()
    }
}