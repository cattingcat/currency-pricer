package org.currency_pricer.core

interface ISpreadDataSource {
    val name: String
    fun loadData(): Collection<BrokerSpread>
}