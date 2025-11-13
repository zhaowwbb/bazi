package com.rick.bazi.ui

import com.rick.bazi.data.BaziInfo
import androidx.lifecycle.ViewModel
import com.rick.bazi.data.BaziData
import com.rick.bazi.data.BaziGeJu
import com.rick.bazi.data.BaziStrength
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

    fun setBirthDate(dateMili: Long) {
        _uiState.update { currentState ->
            currentState.copy(
                birthDateMili = dateMili
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

    fun setYearBase(base: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                yearBase = base
            )
        }
    }

    fun setMonthBase(base: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                monthBase = base
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

    fun setMediumRootCount(n: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                mediumRootCount = n
            )
        }
    }

    fun setWeakRootCount(n: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                weakRootCount = n
            )
        }
    }

    fun setBaziStr(str: String) {
        _uiState.update { currentState ->
            currentState.copy(
                baziStr = str
            )
        }
    }

    fun setBaziOwnerStr(str: String) {
        _uiState.update { currentState ->
            currentState.copy(
                ownerStr = str
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

    fun setDeDiCheckStr(str: String) {
        _uiState.update { currentState ->
            currentState.copy(
                deDiCheckStr = str
            )
        }
    }

    fun setDeHelpStr(str: String) {
        _uiState.update { currentState ->
            currentState.copy(
                deHelpStr = str
            )
        }
    }

    fun setKeXieHaoStr(str: String) {
        _uiState.update { currentState ->
            currentState.copy(
                keXieHaoStr = str
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

    fun setIsDedi(flag: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isDedi = flag
            )
        }
    }

    fun setBaziXiJiSummary(str: String) {
        _uiState.update { currentState ->
            currentState.copy(
                baziXiJiSummary = str
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

    fun setBaziXiyongShenList(list: List<WuXing>) {
        _uiState.update { currentState ->
            currentState.copy(
                xiyongShenList = list
            )
        }
    }

    fun setBaziJiShenList(list: List<WuXing>) {
        _uiState.update { currentState ->
            currentState.copy(
                jiShenList = list
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

    fun setYearDzRootLevel(level: RootLevel) {
        _uiState.update { currentState ->
            currentState.copy(
                yearDzRootLevel = level
            )
        }
    }

    fun setMonthDzRootLevel(level: RootLevel) {
        _uiState.update { currentState ->
            currentState.copy(
                monthDzRootLevel = level
            )
        }
    }

    fun setDayDzRootLevel(level: RootLevel) {
        _uiState.update { currentState ->
            currentState.copy(
                dayDzRootLevel = level
            )
        }
    }

    fun setHourDzRootLevel(level: RootLevel) {
        _uiState.update { currentState ->
            currentState.copy(
                hourDzRootLevel = level
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
}