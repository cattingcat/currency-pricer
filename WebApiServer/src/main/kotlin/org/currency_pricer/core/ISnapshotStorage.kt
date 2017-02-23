package org.currency_pricer.core

interface ISnapshotStorage {
    fun putSnapshot(snapshot: Collection<BrokerSpread>)
    val snapshot: Collection<BrokerSpread>
}