package com.rick.bazi.util

import com.rick.bazi.data.WuXing

/**
 * WuXing 相关的扩展函数和工具
 */
object WuXingExt {

    /**
     * 五行相生关系
     */
    fun isSheng(sheng: WuXing, beiSheng: WuXing): Boolean {
        return when (sheng) {
            WuXing.WUXING_MU -> beiSheng == WuXing.WUXING_HUO
            WuXing.WUXING_HUO -> beiSheng == WuXing.WUXING_TU
            WuXing.WUXING_TU -> beiSheng == WuXing.WUXING_JIN
            WuXing.WUXING_JIN -> beiSheng == WuXing.WUXING_SHUI
            WuXing.WUXING_SHUI -> beiSheng == WuXing.WUXING_MU
        }
    }

    /**
     * 五行相克关系
     */
    fun isKe(ke: WuXing, beiKe: WuXing): Boolean {
        return when (ke) {
            WuXing.WUXING_MU -> beiKe == WuXing.WUXING_TU
            WuXing.WUXING_TU -> beiKe == WuXing.WUXING_SHUI
            WuXing.WUXING_SHUI -> beiKe == WuXing.WUXING_HUO
            WuXing.WUXING_HUO -> beiKe == WuXing.WUXING_JIN
            WuXing.WUXING_JIN -> beiKe == WuXing.WUXING_MU
        }
    }

    /**
     * 获取五行中文名
     */
    fun getWuXingText(wx: WuXing): String = when (wx) {
        WuXing.WUXING_JIN -> "金"
        WuXing.WUXING_MU -> "木"
        WuXing.WUXING_SHUI -> "水"
        WuXing.WUXING_HUO -> "火"
        WuXing.WUXING_TU -> "土"
    }

    /**
     * 获取五行权重（从 BaziData 中）
     */
    fun getWeight(data: com.rick.bazi.data.BaziData, wx: WuXing): Float = when (wx) {
        WuXing.WUXING_JIN -> data.jinWeight
        WuXing.WUXING_MU -> data.muWeight
        WuXing.WUXING_SHUI -> data.shuiWeight
        WuXing.WUXING_HUO -> data.huoWeight
        WuXing.WUXING_TU -> data.tuWeight
    }
}