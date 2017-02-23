package org.currency_pricer.sources

import com.google.gson.JsonParser
import com.mashape.unirest.http.Unirest
import org.currency_pricer.Utils
import org.currency_pricer.core.BrokerSpread
import org.currency_pricer.core.ISpreadDataSource
import org.currency_pricer.core.Spread
import java.text.SimpleDateFormat
import java.util.*


class KurscomruDataSource : ISpreadDataSource {
    companion object {
        fun getUri(currency: Currency): String {
            val cal = Calendar.getInstance()
            val format = SimpleDateFormat("dd.MM.yy")
            val dateStr = format.format(cal.time)

            val currCode = currency.currencyCode.toLowerCase()

            val uri = "https://kurs.com.ru/ajax/valyuta_nalichnie/all/$currCode/$dateStr"

            println("request data from uri: $uri")

            return uri
        }

        val usd = Currency.getInstance("USD")
        val eur = Currency.getInstance("EUR")
    }


    override val name: String
        get() = "kurs.com.ru"

    override fun loadData() : Collection<BrokerSpread> {
        val maps = arrayOf(usd, eur).map {
            val uri = getUri(it)
            val json = loadJsonString(uri, it)
            parse(json, it)
        }.toTypedArray()

        return merge(*maps)
    }


    private fun loadJsonString(uri: String, currency: Currency) : String {
        val response = Unirest.get(uri)
                .header("x-requested-with", "XMLHttpRequest")
                .asString()

        parse(response.body, currency)

        return response.body
    }

    private fun parse(jsonString: String, currency: Currency) : Map<String, Spread> {
        val p = JsonParser()
        val jsonEl = p.parse(jsonString)
        val items = jsonEl.asJsonObject["values"].asJsonObject["new"].asJsonArray

        val map = HashMap<String, Spread>() // broker name -> Spread

        val c = GregorianCalendar()
        val yyyy = c.get(Calendar.YEAR)

        for (i in items) {
            val tmp = i.asJsonObject
            val bid = tmp["bid"].asString.toDouble()
            val ask = tmp["ask"].asString.toDouble()
            val brokerName = tmp["nominative"].asString

            val ddmm = tmp["date"].asString  // dd.mm
            val enterDate = tmp["enter_date"].asString // dd.mm.yyyy if old or hh:mm if new


            val stringDate = if(enterDate.count{it == '.'} > 0) "$enterDate 00:00"
                                else "$ddmm.$yyyy $enterDate"

            val date = Utils.dateFormat.parse(stringDate)

            val spread = Spread(currency, bid, ask, date)
            map[brokerName] = spread
        }

        return map
    }

    private fun merge(vararg maps: Map<String, Spread>): Collection<BrokerSpread> {
        val keys = maps.flatMap { it.keys }.toSet()

        val brokers = LinkedList<BrokerSpread>()

        for(k in keys) {
            val spreads = maps.mapNotNull { it[k] }
            if(spreads.any()) {
                val brokerSpreads = BrokerSpread(k, spreads)
                brokers.add(brokerSpreads)
            }
        }

        return brokers
    }
}