package org.currency_pricer.api

import org.currency_pricer.core.BrokerSpread
import org.currency_pricer.core.ISnapshotStorage
import org.currency_pricer.core.ISpreadDataSource
import org.currency_pricer.sources.BankiruDataSource
import org.currency_pricer.sources.BrokerDataFactory
import org.currency_pricer.sources.CompositeDataSource
import org.currency_pricer.sources.KurscomruDataSource
import org.currency_pricer.storages.StorageFactory
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CurrencyController {
    private val snapshotStorage: ISnapshotStorage = StorageFactory.getInstance()

    @RequestMapping("/spreads")
    fun spreads() : Collection<BrokerSpread> {
        return snapshotStorage.snapshot
    }
}