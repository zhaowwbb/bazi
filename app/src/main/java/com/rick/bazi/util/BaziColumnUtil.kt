package com.rick.bazi.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rick.bazi.R
import com.rick.bazi.data.ColumnPosition

class BaziColumnUtil {
    @Composable
    fun getBaziColumnString(columnPosition: ColumnPosition, isTG : Boolean) : String{
        var sb = StringBuilder()
        var gzStr = ""
        if(isTG){
            gzStr = stringResource(R.string.app_label_gan)
        }else{
            gzStr = stringResource(R.string.app_label_zhi)
        }
        if(columnPosition == ColumnPosition.COLUMN_YEAR){
            sb.append(stringResource(R.string.app_label_year))
        }
        if(columnPosition == ColumnPosition.COLUMN_MONTH){
            sb.append(stringResource(R.string.app_label_month))
        }
        if(columnPosition == ColumnPosition.COLUMN_DAY){
            sb.append(stringResource(R.string.app_label_day))
        }
        if(columnPosition == ColumnPosition.COLUMN_HOUR){
            sb.append(stringResource(R.string.app_label_hour))
        }
        if(columnPosition == ColumnPosition.COLUMN_DA_YUN){
            if(isTG){
                return stringResource(R.string.dayun_tiangan_label)
            }else{
                return stringResource(R.string.dayun_dizhi_label)
            }
//            sb.append(stringResource(R.string.app_bazi_dayun))
        }
        if(columnPosition == ColumnPosition.COLUMN_LIU_YEAR){
            if(isTG){
                return stringResource(R.string.liunian_tiangan_label)
            }else{
                return stringResource(R.string.liunian_dizhi_label)
            }
//            sb.append(stringResource(R.string.bazi_liunian))
        }
        if(columnPosition == ColumnPosition.COLUMN_LIU_MONTH){
            sb.append(stringResource(R.string.bazi_liu_month))
        }
        sb.append(gzStr)

        return sb.toString()
    }
}