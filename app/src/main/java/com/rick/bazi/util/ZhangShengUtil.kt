package com.rick.bazi.util

import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.TianGan

class ZhangShengUtil {
    //from zhangsheng, muyu, guandai -> jue, tai, yang
    val zhangShengMap: Map<TianGan, Array<DiZhi>> = mapOf(
        TianGan.TIANGAN_JIA to
                arrayOf(
                    DiZhi.DIZHI_HAI,
                    DiZhi.DIZHI_ZI,
                    DiZhi.DIZHI_CHOU,
                    DiZhi.DIZHI_YIN,
                    DiZhi.DIZHI_MOU,
                    DiZhi.DIZHI_CHEN,
                    DiZhi.DIZHI_SI,
                    DiZhi.DIZHI_WU,
                    DiZhi.DIZHI_WEI,
                    DiZhi.DIZHI_SHEN,
                    DiZhi.DIZHI_YOU,
                    DiZhi.DIZHI_XU
                ),
        TianGan.TIANGAN_BING to
                arrayOf(
                    DiZhi.DIZHI_YIN,
                    DiZhi.DIZHI_MOU,
                    DiZhi.DIZHI_CHEN,
                    DiZhi.DIZHI_SI,
                    DiZhi.DIZHI_WU,
                    DiZhi.DIZHI_WEI,
                    DiZhi.DIZHI_SHEN,
                    DiZhi.DIZHI_YOU,
                    DiZhi.DIZHI_XU,
                    DiZhi.DIZHI_HAI,
                    DiZhi.DIZHI_ZI,
                    DiZhi.DIZHI_CHOU,
                ),
        TianGan.TIANGAN_WU to
                arrayOf(
                    DiZhi.DIZHI_YIN,
                    DiZhi.DIZHI_MOU,
                    DiZhi.DIZHI_CHEN,
                    DiZhi.DIZHI_SI,
                    DiZhi.DIZHI_WU,
                    DiZhi.DIZHI_WEI,
                    DiZhi.DIZHI_SHEN,
                    DiZhi.DIZHI_YOU,
                    DiZhi.DIZHI_XU,
                    DiZhi.DIZHI_HAI,
                    DiZhi.DIZHI_ZI,
                    DiZhi.DIZHI_CHOU,
                ),
        TianGan.TIANGAN_GENG to
                arrayOf(
                    DiZhi.DIZHI_SI,
                    DiZhi.DIZHI_WU,
                    DiZhi.DIZHI_WEI,
                    DiZhi.DIZHI_SHEN,
                    DiZhi.DIZHI_YOU,
                    DiZhi.DIZHI_XU,
                    DiZhi.DIZHI_HAI,
                    DiZhi.DIZHI_ZI,
                    DiZhi.DIZHI_CHOU,
                    DiZhi.DIZHI_YIN,
                    DiZhi.DIZHI_MOU,
                    DiZhi.DIZHI_CHEN,
                ),
        TianGan.TIANGAN_REN to
                arrayOf(
                    DiZhi.DIZHI_SHEN,
                    DiZhi.DIZHI_YOU,
                    DiZhi.DIZHI_XU,
                    DiZhi.DIZHI_HAI,
                    DiZhi.DIZHI_ZI,
                    DiZhi.DIZHI_CHOU,
                    DiZhi.DIZHI_YIN,
                    DiZhi.DIZHI_MOU,
                    DiZhi.DIZHI_CHEN,
                    DiZhi.DIZHI_SI,
                    DiZhi.DIZHI_WU,
                    DiZhi.DIZHI_WEI,
                ),
        TianGan.TIANGAN_YI to
                arrayOf(
                    DiZhi.DIZHI_WU,
                    DiZhi.DIZHI_SI,
                    DiZhi.DIZHI_CHEN,
                    DiZhi.DIZHI_MOU,
                    DiZhi.DIZHI_YIN,
                    DiZhi.DIZHI_CHOU,
                    DiZhi.DIZHI_ZI,
                    DiZhi.DIZHI_HAI,
                    DiZhi.DIZHI_XU,
                    DiZhi.DIZHI_YOU,
                    DiZhi.DIZHI_SHEN,
                    DiZhi.DIZHI_WEI,
                ),
        TianGan.TIANGAN_DING to
                arrayOf(
                    DiZhi.DIZHI_YOU,
                    DiZhi.DIZHI_SHEN,
                    DiZhi.DIZHI_WEI,
                    DiZhi.DIZHI_WU,
                    DiZhi.DIZHI_SI,
                    DiZhi.DIZHI_CHEN,
                    DiZhi.DIZHI_MOU,
                    DiZhi.DIZHI_YIN,
                    DiZhi.DIZHI_CHOU,
                    DiZhi.DIZHI_ZI,
                    DiZhi.DIZHI_HAI,
                    DiZhi.DIZHI_XU,
                ),
        TianGan.TIANGAN_JI to
                arrayOf(
                    DiZhi.DIZHI_YOU,
                    DiZhi.DIZHI_SHEN,
                    DiZhi.DIZHI_WEI,
                    DiZhi.DIZHI_WU,
                    DiZhi.DIZHI_SI,
                    DiZhi.DIZHI_CHEN,
                    DiZhi.DIZHI_MOU,
                    DiZhi.DIZHI_YIN,
                    DiZhi.DIZHI_CHOU,
                    DiZhi.DIZHI_ZI,
                    DiZhi.DIZHI_HAI,
                    DiZhi.DIZHI_XU,
                ),
        TianGan.TIANGAN_XIN to
                arrayOf(
                    DiZhi.DIZHI_ZI,
                    DiZhi.DIZHI_HAI,
                    DiZhi.DIZHI_XU,
                    DiZhi.DIZHI_YOU,
                    DiZhi.DIZHI_SHEN,
                    DiZhi.DIZHI_WEI,
                    DiZhi.DIZHI_WU,
                    DiZhi.DIZHI_SI,
                    DiZhi.DIZHI_CHEN,
                    DiZhi.DIZHI_MOU,
                    DiZhi.DIZHI_YIN,
                    DiZhi.DIZHI_CHOU,
                ),
        TianGan.TIANGAN_GUI to
                arrayOf(
                    DiZhi.DIZHI_MOU,
                    DiZhi.DIZHI_YIN,
                    DiZhi.DIZHI_CHOU,
                    DiZhi.DIZHI_ZI,
                    DiZhi.DIZHI_HAI,
                    DiZhi.DIZHI_XU,
                    DiZhi.DIZHI_YOU,
                    DiZhi.DIZHI_SHEN,
                    DiZhi.DIZHI_WEI,
                    DiZhi.DIZHI_WU,
                    DiZhi.DIZHI_SI,
                    DiZhi.DIZHI_CHEN,
                ),
    )

    fun getZhangSheng(tg : TianGan) : DiZhi{
        val array = zhangShengMap.get(tg)!!
        return array[0]
    }

    fun getMuYu(tg : TianGan) : DiZhi{
        val array = zhangShengMap.get(tg)!!
        return array[1]
    }

    fun getSi(tg : TianGan) : DiZhi{
        val array = zhangShengMap.get(tg)!!
        return array[7]
    }

    fun getJue(tg : TianGan) : DiZhi{
        val array = zhangShengMap.get(tg)!!
        return array[9]
    }
}