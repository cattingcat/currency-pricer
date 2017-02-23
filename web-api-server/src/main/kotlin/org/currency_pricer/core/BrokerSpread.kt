package org.currency_pricer.core

data class BrokerSpread(val nativeName: String, val spreads: Collection<Spread>) {}