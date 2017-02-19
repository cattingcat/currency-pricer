package org.currency_pricer.sources

import org.currency_pricer.core.ISpreadDataSource

object BrokerDataFactory {
    private val source: ISpreadDataSource
    init {
        val bankiru = BankiruDataSource()
        val bankiru2 = BankiruDataSource(BankiruDataSource.mskDataSourceUrl)
        val kurs = KurscomruDataSource()
        source = CompositeDataSource(listOf(bankiru, kurs, bankiru2))
    }

    fun getInstance(): ISpreadDataSource {
        return source
    }
}