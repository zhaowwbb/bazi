package com.rick.bazi.ui

import com.rick.bazi.data.BaziInfo
import androidx.lifecycle.ViewModel
import com.rick.bazi.data.DiZhi
import com.rick.bazi.data.TianGan
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
}