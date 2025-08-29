package com.emrehancetin.ayarhesaplama.domain

sealed class CalcResult {
    data class Success(val hasGr: Double, val totalGr: Double) : CalcResult()
    data class Error(val message: String) : CalcResult()
}

/**
 * Girdiler:
 *  - takozGr: gram
 *  - takozAyarPermille: örn. 916 (permille)
 */
fun calculateHasGrAndTotal(
    takozGr: Double,
    takozAyarPermille: Double,
    targetPermille: Double = Constants.TARGET_AYAR_PERMILLE,
    hasPermille: Double = Constants.HAS_AYAR_PERMILLE
): CalcResult {
    if (takozGr <= 0.0) return CalcResult.Error("Takoz gram > 0 olmalı.")
    if (takozAyarPermille !in 0.0..1000.0) return CalcResult.Error("Ayar 0–1000 arasında olmalı.")
    if (targetPermille !in 0.0..1000.0 || hasPermille !in 0.0..1000.0) {
        return CalcResult.Error("Sabit ayarlar geçersiz.")
    }

    val target = targetPermille / 1000.0
    val has = hasPermille / 1000.0
    val takoz = takozAyarPermille / 1000.0
    val denom = has - target
    if (denom == 0.0) return CalcResult.Error("Has ayarı hedefe eşit olamaz.")

    val rawHas = takozGr * (target - takoz) / denom
    val needHas = kotlin.math.max(0.0, rawHas) // hedeften yüksekse 0 ekle
    val total = takozGr + needHas

    return CalcResult.Success(
        hasGr = needHas.roundTo(),
        totalGr = total.roundTo()
    )
}