package com.currency_pricer.components

import android.content.Context
import android.widget.LinearLayout
import android.view.LayoutInflater
import android.widget.ProgressBar
import android.widget.TextView
import com.currency_pricer.myapplication.R


class SpreadView(context: Context/*, attrs: AttributeSet, defStyle: Int*/)
        : LinearLayout(context/*, attrs, defStyle*/) {

    private val bidTxt: TextView  // buy price
    private val bidProgress: ProgressBar

    private val askTxt: TextView  // sell price
    private val askProgress: ProgressBar

    private val brokerTxt: TextView
    private val updDate: TextView

    private var _brokerName: String = "SBERBANK"
    private var _updateDate: String = "1.1.2111"
    private var _maxPrice: Double = 0.0
    private var _bid: Double = 0.0
    private var _ask: Double = 0.0

    var bid: Double
        get() = _bid
        set(value) {
            _bid = value
            bidProgress.progress = (value * 100).toInt()
            bidTxt.text = value.toString()
        }

    var ask: Double
        get() = _ask
        set(value) {
            _ask = value
            askProgress.progress = (value * 100).toInt()
            askTxt.text = value.toString()
        }

    var brokerName: String
        get() = _brokerName
        set(value) {
            _brokerName = value
            brokerTxt.text = value
        }

    var updateDate: String
        get() = _updateDate
        set(value) {
            _updateDate = value
            updDate.text = value
        }



    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.spread_view, this)
        layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        orientation = LinearLayout.VERTICAL

        brokerTxt = findViewById(R.id.brokerName) as TextView
        updDate = findViewById(R.id.updDate) as TextView

        bidProgress = findViewById(R.id.bidProgress) as ProgressBar
        bidTxt = findViewById(R.id.bidText) as TextView

        askProgress = findViewById(R.id.askProgress) as ProgressBar
        askTxt = findViewById(R.id.askText) as TextView

        brokerName = "Empty"
        updateDate = "Empty date"
    }


    fun setMaxPrice(maxPrice: Double) {
        _maxPrice = maxPrice
        val progrMax = (maxPrice * 100).toInt()
        bidProgress.max = progrMax
        askProgress.max = progrMax
    }
}