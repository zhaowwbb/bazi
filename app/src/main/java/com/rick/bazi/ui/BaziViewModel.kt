package com.rick.bazi.ui

import com.rick.bazi.data.BaziInfo
import androidx.lifecycle.ViewModel
import com.rick.bazi.data.BaziData
import com.rick.bazi.data.BaziGeJu
import com.rick.bazi.data.BaziStrength
import com.rick.bazi.data.BaziYongShenPickMode
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.RootLevel
import com.rick.bazi.data.ShiShen
import com.rick.bazi.data.TianGan
import com.rick.bazi.data.WuXing
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BaziViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BaziInfo())
    val uiState: StateFlow<BaziInfo> = _uiState.asStateFlow()

    fun setName(name: String) {
        _uiState.update { currentState ->
            currentState.copy(
                name = name
            )
        }
    }

    fun setBirthDateYear(year: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                birthDateYear = year
            )
        }
    }

    fun setBirthDateMonth(month: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                birthDateMonth = month
            )
        }
    }

    fun setBirthDateDay(day: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                birthDateDay = day
            )
        }
    }

    fun setBirthHour(hour: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                birthHour = hour
            )
        }
    }

    fun setBirthMinute(minute: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                birthMinute = minute
            )
        }
    }

    fun setGender(genderOption: String) {
        _uiState.update { currentState ->
            currentState.copy(
                gender = genderOption
            )
        }
    }

    fun setYearTiangan(tg: TianGan) {
        _uiState.update { currentState ->
            currentState.copy(
                yearTiangan = tg
            )
        }
    }

    fun setYearDiZhi(dz: DiZhi) {
        _uiState.update { currentState ->
            currentState.copy(
                yearDizhi = dz
            )
        }
    }

    fun setMonthTiangan(tg: TianGan) {
        _uiState.update { currentState ->
            currentState.copy(
                monthTiangan = tg
            )
        }
    }

    fun setMonthDiZhi(dz: DiZhi) {
        _uiState.update { currentState ->
            currentState.copy(
                monthDizhi = dz
            )
        }
    }

    fun setDayTiangan(tg: TianGan) {
        _uiState.update { currentState ->
            currentState.copy(
                dayTiangan = tg
            )
        }
    }

    fun setDayDiZhi(dz: DiZhi) {
        _uiState.update { currentState ->
            currentState.copy(
                dayDizhi = dz
            )
        }
    }

    fun setHourTiangan(tg: TianGan) {
        _uiState.update { currentState ->
            currentState.copy(
                hourTiangan = tg
            )
        }
    }

    fun setHourDiZhi(dz: DiZhi) {
        _uiState.update { currentState ->
            currentState.copy(
                hourDizhi = dz
            )
        }
    }

    fun setDayBase(base: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                dayBase = base
            )
        }
    }

    fun setDayunForward(isForward: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                dayunForward = isForward
            )
        }
    }

    fun setDayunDays(days: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                dayunDays = days
            )
        }
    }

    fun setDangLing(isDangLing: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isDangLing = isDangLing
            )
        }
    }

    fun setStrongRootCount(n: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                strongRootCount = n
            )
        }
    }

    fun setWuxingSummaryStr(str: String) {
        _uiState.update { currentState ->
            currentState.copy(
                wuxingSummaryStr = str
            )
        }
    }

    fun setDeLingCheckStr(str: String) {
        _uiState.update { currentState ->
            currentState.copy(
                deLingCheckStr = str
            )
        }
    }

    fun setShishenYearStr(str: String) {
        _uiState.update { currentState ->
            currentState.copy(
                shishenYearStr = str
            )
        }
    }

    fun setShishenMonthStr(str: String) {
        _uiState.update { currentState ->
            currentState.copy(
                shishenMonthStr = str
            )
        }
    }

    fun setShishenDayStr(str: String) {
        _uiState.update { currentState ->
            currentState.copy(
                shishenDayStr = str
            )
        }
    }

    fun setShishenHourStr(str: String) {
        _uiState.update { currentState ->
            currentState.copy(
                shishenHourStr = str
            )
        }
    }

    fun setHelpElementNum(n: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                helpElementNum = n
            )
        }
    }

    fun setImpedeElementNum(n: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                impedeElementNum = n
            )
        }
    }

    fun setYearTgShiShen(s: ShiShen) {
        _uiState.update { currentState ->
            currentState.copy(
                yearTgShiShen = s
            )
        }
    }

    fun setYearDzShiShen(s: ShiShen) {
        _uiState.update { currentState ->
            currentState.copy(
                yearDzShiShen = s
            )
        }
    }

    fun setMonthTgShiShen(s: ShiShen) {
        _uiState.update { currentState ->
            currentState.copy(
                monthTgShiShen = s
            )
        }
    }

    fun setMonthDzShiShen(s: ShiShen) {
        _uiState.update { currentState ->
            currentState.copy(
                monthDzShiShen = s
            )
        }
    }

    fun setDayDzShiShen(s: ShiShen) {
        _uiState.update { currentState ->
            currentState.copy(
                dayDzShiShen = s
            )
        }
    }

    fun setHourTgShiShen(s: ShiShen) {
        _uiState.update { currentState ->
            currentState.copy(
                hourTgShiShen = s
            )
        }
    }

    fun setHourDzShiShen(s: ShiShen) {
        _uiState.update { currentState ->
            currentState.copy(
                hourDzShiShen = s
            )
        }
    }

    fun setYinCount(n: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                yinCount = n
            )
        }
    }

    fun setBiJieCount(n: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                bijieCount = n
            )
        }
    }

    fun setYinString(str: String) {
        _uiState.update { currentState ->
            currentState.copy(
                yinString = str
            )
        }
    }

    fun setBiJieString(str: String) {
        _uiState.update { currentState ->
            currentState.copy(
                bijieString = str
            )
        }
    }

    fun setGuanShaCount(n: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                guanshaCount = n
            )
        }
    }

    fun setShiShangCount(n: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                shishangCount = n
            )
        }
    }

    fun setCaiCount(n: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                caiCount = n
            )
        }
    }

    fun setBaziStrength(s: BaziStrength) {
        _uiState.update { currentState ->
            currentState.copy(
                baziStrength = s
            )
        }
    }

    fun setBaziStrengthSummary(str: String) {
        _uiState.update { currentState ->
            currentState.copy(
                baziStrengthSummary = str
            )
        }
    }

    fun setBaziGJ(gj: BaziGeJu) {
        _uiState.update { currentState ->
            currentState.copy(
                baziGJ = gj
            )
        }
    }

    fun setBaziGJSummary(str: String) {
        _uiState.update { currentState ->
            currentState.copy(
                baziGJSummary = str
            )
        }
    }

    fun setBaziGJString(str: String) {
        _uiState.update { currentState ->
            currentState.copy(
                baziGJString = str
            )
        }
    }

    fun setBaziDayunSummary(str: String) {
        _uiState.update { currentState ->
            currentState.copy(
                baziDayunSummary = str
            )
        }
    }

    fun setBaziTiaohouShenList(list: List<WuXing>) {
        _uiState.update { currentState ->
            currentState.copy(
                tiaohouShenList = list
            )
        }
    }

    fun setBaziData(data: BaziData) {
        _uiState.update { currentState ->
            currentState.copy(
                baziData = data
            )
        }
    }

    fun setBaziYongShenPickMode(str: String) {
        _uiState.update { currentState ->
            currentState.copy(
                yongShenPickModeStr = str
            )
        }
    }

    /**
     * 深度拷贝 BaziData 对象到 BaziInfo.baziData
     * 创建一个全新的 BaziData 副本，确保与原对象完全独立
     *
     * @param source 源 BaziData 对象
     */
    fun setBaziDataFromObject(source: BaziData) {
        // 创建深拷贝的 BaziData
        val deepCopy = BaziData(
            // 基本信息
            birthDateYear = source.birthDateYear,
            birthDateMonth = source.birthDateMonth,
            birthDateDay = source.birthDateDay,
            birthHour = source.birthHour,
            gender = source.gender,
            birthMinute = source.birthMinute,
            birthSecond = source.birthSecond,

            // 四柱天干
            yearTiangan = source.yearTiangan,
            monthTiangan = source.monthTiangan,
            dayTiangan = source.dayTiangan,
            hourTiangan = source.hourTiangan,

            // 四柱地支
            yearDizhi = source.yearDizhi,
            monthDizhi = source.monthDizhi,
            dayDizhi = source.dayDizhi,
            hourDizhi = source.hourDizhi,

            // 基数
            yearBase = source.yearBase,
            monthBase = source.monthBase,
            dayBase = source.dayBase,

            // 大运相关
            dayunForward = source.dayunForward,
            dayunDays = source.dayunDays,
            isDangLing = source.isDangLing,

            // 根统计
            strongRootCount = source.strongRootCount,
            mediumRootCount = source.mediumRootCount,
            weakRootCount = source.weakRootCount,

            // 地支根级别
            yearDzRootLevel = source.yearDzRootLevel,
            monthDzRootLevel = source.monthDzRootLevel,
            dayDzRootLevel = source.dayDzRootLevel,
            hourDzRootLevel = source.hourDzRootLevel,

            // 用神忌神列表（深拷贝）
            yongShenList = source.yongShenList.toList(),
            jiShenList = source.jiShenList.toList(),
            xiShenList = source.xiShenList.toList(),
            tiaohouShenList = source.tiaohouShenList.toList(),
            tongguanShenList = source.tongguanShenList.toList(),
            tiaohouList = source.tiaohouList.toList(),
            allYongShenList = source.allYongShenList.toList(),

            // 五行权重
            jinWeight = source.jinWeight,
            muWeight = source.muWeight,
            shuiWeight = source.shuiWeight,
            huoWeight = source.huoWeight,
            tuWeight = source.tuWeight,

            // 十神权重
            yinWeight = source.yinWeight,
            bijieWeight = source.bijieWeight,
            caiWeight = source.caiWeight,
            shishangWeight = source.shishangWeight,
            guanshaWeight = source.guanshaWeight,

            // 格局
            tongguanYongShen = source.tongguanYongShen,
            tiaohouYongShen = source.tiaohouYongShen,
            gj = source.gj,

            // 大运起运时间
            daYunStartSeconds = source.daYunStartSeconds,
            daYunStartYear = source.daYunStartYear,
            daYunStartMonth = source.daYunStartMonth,
            daYunStartDay = source.daYunStartDay,
            daYunFirstYear = source.daYunFirstYear,
            daYunFirstMonth = source.daYunFirstMonth,

            // 权重
            daYunWeight = source.daYunWeight,
            liuNianWeight = source.liuNianWeight,

            // 用神列表（深拷贝）
            baziYongShenList = source.baziYongShenList.toList(),
            xiyongShenSet = source.xiyongShenSet.toSet(),
            jiShenSet = source.jiShenSet.toSet(),
            yongShenPickMode = source.yongShenPickMode
        )

        // 更新 BaziInfo 中的 baziData
        _uiState.update { currentState ->
            currentState.copy(
                baziData = deepCopy
            )
        }
    }

    // 保存用户选择的日期和时间（毫秒值）
    var selectedDateMillis: Long? = null
        private set

    // 保存用户选择的时间
    var selectedHour: Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        private set
    var selectedMinute: Int = Calendar.getInstance().get(Calendar.MINUTE)
        private set

    fun saveSelectedDate(millis: Long) {
        selectedDateMillis = millis
    }

    fun saveSelectedTime(hour: Int, minute: Int) {
        selectedHour = hour
        selectedMinute = minute
    }
}