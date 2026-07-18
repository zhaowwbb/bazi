package com.rick.bazi.util

import com.rick.bazi.data.BaziData
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.TianGan
import com.rick.bazi.util.BaziFormatter.getTianGanWuxing

/**
 * 计算八字中各柱地支的根气数量
 */
object RootCounter {

    /**
     * 计算八字中所有根气数量并更新到 BaziData
     * @param data BaziData 对象（会被直接修改）
     */
    fun calculateRootCounts(data: BaziData) {
        val dayMasterWx = getTianGanWuxing(data.dayTiangan)

        // 收集四个地支
        val dizhiList = listOf(
            data.yearDizhi,
            data.monthDizhi,
            data.dayDizhi,
            data.hourDizhi
        )

        var strongCount = 0  // 本气根
        var mediumCount = 0  // 中气根
        var weakCount = 0    // 余气根

        for (dz in dizhiList) {
            val cangGans = DiZhiUtil().getCanggan(dz)

            for (cg in cangGans) {
                val cgWx = getTianGanWuxing(cg)

                // 只计算与日主五行相同的藏干
                if (cgWx == dayMasterWx) {
                    when (dz) {
                        // 子、午、卯、酉：只有本气，权重最高
                        DiZhi.DIZHI_ZI, DiZhi.DIZHI_WU,
                        DiZhi.DIZHI_MOU, DiZhi.DIZHI_YOU -> {
                            strongCount++
                        }

                        // 寅、申、巳、亥：本气+中气+余气
                        DiZhi.DIZHI_YIN, DiZhi.DIZHI_SHEN,
                        DiZhi.DIZHI_SI, DiZhi.DIZHI_HAI -> {
                            // 第一个藏干是本气
                            if (cg == cangGans[0]) {
                                strongCount++
                            }
                            // 第二个藏干是中气
                            else if (cg == cangGans[1]) {
                                mediumCount++
                            }
                            // 第三个藏干是余气
                            else {
                                weakCount++
                            }
                        }

                        // 辰、戌、丑、未：本气+中气+余气
                        DiZhi.DIZHI_CHEN, DiZhi.DIZHI_XU,
                        DiZhi.DIZHI_CHOU, DiZhi.DIZHI_WEI -> {
                            // 第一个藏干是本气
                            if (cg == cangGans[0]) {
                                strongCount++
                            }
                            // 第二个藏干是中气
                            else if (cg == cangGans[1]) {
                                mediumCount++
                            }
                            // 第三个藏干是余气
                            else {
                                weakCount++
                            }
                        }
                    }
                }
            }
        }

        // 更新到 BaziData
        data.strongRootCount = strongCount
        data.mediumRootCount = mediumCount
        data.weakRootCount = weakCount
    }

    /**
     * 计算指定地支中与日主五行相同的根气数量
     * @param dz 地支
     * @param dayMasterWx 日主五行
     * @return Triple<本气根数, 中气根数, 余气根数>
     */
    fun calculateSingleRootCount(dz: DiZhi, dayMasterWx: com.rick.bazi.data.WuXing): Triple<Int, Int, Int> {
        val cangGans = DiZhiUtil().getCanggan(dz)

        var strong = 0
        var medium = 0
        var weak = 0

        for (cg in cangGans) {
            val cgWx = getTianGanWuxing(cg)

            if (cgWx == dayMasterWx) {
                when (dz) {
                    DiZhi.DIZHI_ZI, DiZhi.DIZHI_WU,
                    DiZhi.DIZHI_MOU, DiZhi.DIZHI_YOU -> {
                        strong++
                    }

                    DiZhi.DIZHI_YIN, DiZhi.DIZHI_SHEN,
                    DiZhi.DIZHI_SI, DiZhi.DIZHI_HAI,
                    DiZhi.DIZHI_CHEN, DiZhi.DIZHI_XU,
                    DiZhi.DIZHI_CHOU, DiZhi.DIZHI_WEI -> {
                        if (cg == cangGans[0]) {
                            strong++
                        } else if (cg == cangGans[1]) {
                            medium++
                        } else {
                            weak++
                        }
                    }
                }
            }
        }

        return Triple(strong, medium, weak)
    }
}