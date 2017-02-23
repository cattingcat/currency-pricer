package com.currency_pricer

import org.json.JSONArray
import org.json.JSONObject
import okhttp3.OkHttpClient
import okhttp3.Request

data class SpreadItem(val brokerName: String, val bid: Double, val ask: Double, val date: Long)

class SpreadDataSource {
    fun loadData(): Map<String, Collection<SpreadItem>>{
        return parse(loadJson())
    }

    private fun parse(json: String): Map<String, Collection<SpreadItem>> {
        val o = JSONArray(json)
        val map: MutableMap<String, MutableList<SpreadItem>> = mutableMapOf(
                "EUR" to mutableListOf(),
                "USD" to mutableListOf())

        if(o.length() == 0) {
            return map
        }

        for(i in 0.rangeTo(o.length() - 1)){
            val broker = (o[i] as JSONObject)
            val brokerName = broker.getString("nativeName")
            val spreads = broker.getJSONArray("spreads")
            for(j in 0.rangeTo(spreads.length() - 1)) {
                val spread = (spreads[j] as JSONObject)
                val bid = spread.getString("bid").toDouble()
                val ask = spread.getString("ask").toDouble()
                val date = spread.getLong("date")
                val currIso = spread.getString("currency")

                map[currIso]?.add(SpreadItem(brokerName, bid, ask, date))
            }
        }

        for(i in map.keys) {
            map[i]?.sortByDescending { it.date }
        }

        return map
    }

    private fun loadJson(): String {
        val client = OkHttpClient()
        val builder = Request.Builder()
        val req = builder.url("https://currency-pricer-web-api.herokuapp.com/spreads").build()

        val json = client.newCall(req).execute().body().string()

        return json
    }
}