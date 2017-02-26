package com.github.zxc123zxc.currencies.components

import android.os.AsyncTask
import com.github.zxc123zxc.currencies.common.minMax
import com.github.zxc123zxc.currencies.services.SpreadDataSource
import java.util.*

class FillAdapterTask(val adapter: SpreadAdapter): AsyncTask<String, Int, Collection<SpreadViewModel>>() {
    override fun onPostExecute(result: Collection<SpreadViewModel>) {
        adapter.addAll(result)
        super.onPostExecute(result)
    }

    override fun doInBackground(vararg params: String?): Collection<SpreadViewModel> {
        val instrumentIso = params[0] ?: "USD"

        val dataSource = SpreadDataSource()
        val data = dataSource.loadData()
        val list = data[instrumentIso] ?: listOf()
        val tmp = list.flatMap { listOf(it.bid, it.offer) }.minMax { a, b -> a < b } ?: return listOf()

        val res = list.map {
            SpreadViewModel(it.broker, Date(it.updateTimestamp), it.bid, it.offer, tmp.min, tmp.max)
        }
        return res.sortedBy { (tmp.min/it.offer) + (it.bid/tmp.max)}
    }
}