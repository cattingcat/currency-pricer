package org.currency_pricer

import org.currency_pricer.core.ISnapshotStorage
import org.currency_pricer.core.ISpreadDataSource
import org.currency_pricer.sources.BrokerDataFactory
import org.currency_pricer.storages.StorageFactory
import java.util.*
import kotlin.concurrent.schedule

class SnapshotScheduler {
    private val store: ISnapshotStorage = StorageFactory.getInstance()
    private val src: ISpreadDataSource = BrokerDataFactory.getInstance()
    private val t = Timer()

    fun start() {
        // update snapshot every 5 mins
        t.schedule(0, 300000) {
            val snapshot = src.loadData()
            store.putSnapshot(snapshot)
        }
    }
}