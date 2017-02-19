package org.currency_pricer.storages

import org.currency_pricer.core.ISnapshotStorage

object StorageFactory {
    private val snapshotStorage: ISnapshotStorage = InMemoryStorage()

    fun getInstance(): ISnapshotStorage = snapshotStorage
}