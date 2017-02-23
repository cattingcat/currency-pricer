package org.currency_pricer.sources

import org.currency_pricer.core.*
import com.mashape.unirest.http.Unirest
import org.currency_pricer.Utils
import org.jsoup.Jsoup
import java.util.*

class BankiruDataSource(val uri: String = spbDataSourceUrl) : ISpreadDataSource {

    companion object {
        private val baseUrl = "http://www.banki.ru/products/currency/bank_seller_rates_table"
        val spbDataSourceUrl = "$baseUrl/sankt-peterburg/"
        val mskDataSourceUrl = "$baseUrl/moskva"
    }


    override val name: String
        get() = "banki.ru"

    override fun loadData(): Collection<BrokerSpread> {
        val html = loadHtmlString()
        val brokers = parse(html)
        return brokers
    }

    private fun loadHtmlString(): String {
        val response = Unirest.get(uri)
            .header("x-requested-with", "XMLHttpRequest")
            .asString()

        return  response.body
    }

    private fun parse(html: String): Collection<BrokerSpread> {
        val doc = Jsoup.parseBodyFragment(html)

        val res = doc.select("[data-currencies-row]").map { currencyRow ->
            val brokerName = currencyRow.select("[data-currencies-bank-name]").text().trim()

            val brokerCurr = currencyRow.select("[data-currencies-code]")
                    .map { it.attr("data-currencies-code").toUpperCase() }
                    .distinct()

            val dateStr = currencyRow.select("td").last().text()
            val date = Utils.dateFormat.parse(dateStr)

            val spreads = brokerCurr.map {
                val tmp = currencyRow.select("[data-currencies-code=\"$it\"]")

                val buy = tmp.select("[data-currencies-rate-buy]")
                val buyPrice = buy.attr("data-currencies-rate-buy")

                val sell = tmp.select("[data-currencies-rate-sell]")
                val sellPrice = sell.attr("data-currencies-rate-sell")

                val currency = Currency.getInstance(it)
                Spread(currency, buyPrice.toDouble(), sellPrice.toDouble(), date)
            }

            BrokerSpread(brokerName, spreads)
        }

        return res
    }
}