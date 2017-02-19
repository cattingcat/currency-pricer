package org.currency_pricer.storages

import org.currency_pricer.core.BrokerSpread
import org.currency_pricer.core.ISnapshotStorage

class InMemoryStorage : ISnapshotStorage {
    private var _snapshot: Collection<BrokerSpread> = listOf()


    override fun putSnapshot(snapshot: Collection<BrokerSpread>) {
        _snapshot = snapshot
    }

    override val snapshot: Collection<BrokerSpread>
        get() = _snapshot
}