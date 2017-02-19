package org.currency_pricer.core

import java.util.*

/** @param bid - buy price
 *  @param ask - sell price */
data class Spread(val currency: Currency, val bid: Double, val ask: Double, val date: Date) {
    fun spread() = ask - bid
}