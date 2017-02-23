package org.currency_pricer.sources

import org.currency_pricer.core.BrokerSpread
import org.currency_pricer.core.ISpreadDataSource
import org.currency_pricer.core.Spread
import java.util.*

class CompositeDataSource(val sources: Collection<ISpreadDataSource>) : ISpreadDataSource {

    override val name: String
        get() = sources.map { it.name }.joinToString()

    override fun loadData(): Collection<BrokerSpread> {
        val datas = sources.map { it.loadData() }.toTypedArray()

        return merge(*datas)
    }


    private fun merge(vararg data: Collection<BrokerSpread>) : Collection<BrokerSpread> {
        val map = HashMap<String, Collection<Spread>>() // BrokerName -> Spread

        for(d in data) {
            d.forEach {
                val name = normalizeName(it.nativeName)
                val old = map[name]
                if(old != null) {
                    val newestSpreads = LinkedList<Spread>()
                    for(oldSpread in old) {
                        val newSpread = it.spreads.find { it.currency == oldSpread.currency }
                        if(newSpread != null && newSpread.date > oldSpread.date) {
                            newestSpreads.add(newSpread)
                        } else {
                            newestSpreads.add(oldSpread)
                        }
                    }

                    map[name] = newestSpreads
                } else {
                    map[name] = it.spreads
                }
            }
        }

        return map.map { BrokerSpread(it.key, it.value) }
    }

    private fun normalizeName(name: String) : String {
        return name.toUpperCase()
                .replace('»', '"')
                .replace('«', '"')
                .replace('—', '-')
    }
}