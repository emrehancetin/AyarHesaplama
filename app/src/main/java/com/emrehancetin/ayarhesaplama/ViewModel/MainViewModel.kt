package com.emrehancetin.ayarhesaplama.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


enum class TakozGrError { NOT_NUMBER, NON_POSITIVE }
enum class AyarError { NOT_NUMBER, OUT_OF_RANGE }


data class UiState(
    val takozGrInput: String = "",
    val takozAyarInput: String = "",
    val hasGr: Double = 1000.0,
    val totalGr: Double = 2000.0,
    val isHasGrSuccess: Boolean = false,
    val takozGrError: TakozGrError? = null,
    val takozAyarError: AyarError? = null,
)
class MainViewModel : ViewModel() {
    var uiState by mutableStateOf(UiState())
        private set

    fun onTakozGrChange(v: String) {
        val parsed = v.replace(',', '.').toDoubleOrNull()
        val err = when {
            v.isNotBlank() && parsed == null -> TakozGrError.NOT_NUMBER
            parsed != null && parsed <= 0.0 -> TakozGrError.NON_POSITIVE
            else -> null
        }
        uiState = uiState.copy(takozGrInput = v, takozGrError = err, isHasGrSuccess = false)
    }

    fun onTakozAyarChange(v: String) {
        val parsed = v.replace(',', '.').toDoubleOrNull()
        val err = when {
            v.isNotBlank() && parsed == null -> AyarError.NOT_NUMBER
            parsed != null && (parsed < 0.0 || parsed > 1000.0) -> AyarError.OUT_OF_RANGE
            else -> null
        }
        uiState = uiState.copy(takozAyarInput = v, takozAyarError = err, isHasGrSuccess = false)
    }

    fun setResults(hasGr: Double, totalGr: Double) {
        uiState = uiState.copy(hasGr = hasGr, totalGr = totalGr, isHasGrSuccess = true)
    }

    fun resetSuccessFlag() {
        uiState = uiState.copy(isHasGrSuccess = false)
    }
}