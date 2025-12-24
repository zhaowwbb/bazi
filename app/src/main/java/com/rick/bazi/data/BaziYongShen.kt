package com.rick.bazi.data

data class BaziYongShen(
    var columnPosition: ColumnPosition = ColumnPosition.COLUMN_YEAR,
    var tg: TianGan = TianGan.TIANGAN_JIA,
    var dz: DiZhi = DiZhi.DIZHI_ZI,
    var isTianGan : Boolean = true,
    var weight : Int = 0,
    var isTongGen : Boolean = false,
    var isTouchu : Boolean = false,
    var isCangGan : Boolean = false,
    var yongshenTG: TianGan = TianGan.TIANGAN_JIA,
    )