package com.emrehancetin.ayarhesaplama.domain

import java.math.BigDecimal
import java.math.RoundingMode

object Constants {
    // Ayarlar TEK ölçekte: permille (örn. 916)
    const val TARGET_AYAR_PERMILLE = 916.0
    const val HAS_AYAR_PERMILLE = 995.0
    const val ROUND_DECIMALS = 3
}

fun Double.roundTo(decimals: Int = Constants.ROUND_DECIMALS): Double =
    BigDecimal(this).setScale(decimals, RoundingMode.HALF_UP).toDouble()