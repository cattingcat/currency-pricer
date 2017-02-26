package com.github.zxc123zxc.currencies.components

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import com.github.zxc123zxc.currencies.*
import com.github.zxc123zxc.currencies.common.Utils

class SpreadView: LinearLayout {
    private var _viewModel: SpreadViewModel? = null
    private val originalBg = this.background

    private val bidTxt: TextView  // buy price
    private val bidProgress: ProgressBar

    private val offerTxt: TextView  // sell price
    private val offerProgress: ProgressBar

    private val brokerTxt: TextView
    private val statusTxt: TextView

    var viewModel: SpreadViewModel?
    get() = _viewModel
    set(value) {
        if(value == null) return
        var isError = false
        try {
            with(value) {
                val dateStr = Utils.dateFormat.format(updateDate)
                brokerTxt.text = broker
                statusTxt.text = "Last update: $dateStr"
                bidTxt.text = bid.toString()
                offerTxt.text = offer.toString()

                bidProgress.max = 100
                offerProgress.max = 100

                val maxSpread = (maxPrice - minPrice)
                val avg = (maxPrice + minPrice) / 2.0
                if (maxSpread > 0) {
                    val bidRatio = (bid - minPrice + 1) / maxSpread
                    val offerRatio = (maxPrice - offer + 1) / maxSpread

                    bidProgress.progress = (bidRatio * 100).toInt()
                    offerProgress.progress = (offerRatio * 100).toInt()
                } else {
                    bidProgress.progress = 0
                    offerProgress.progress = 0
                    isError = true
                }
            }
        } catch (e: Exception) {
            isError = true
        }

        if(isError) {
            this.background = ColorDrawable(Color.YELLOW)
            statusTxt.text = "Update error"
            bidTxt.text = ""
            offerTxt.text = ""
        } else {
            this.background = this.originalBg
        }
    }

    constructor (context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
        : super(context, attrs, defStyleAttr, defStyleRes) {

        orientation = VERTICAL
        this.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val inflater = LayoutInflater.from(this.context)
        inflater.inflate(R.layout.spread_view, this)

        brokerTxt = findViewById(R.id.brokerName) as TextView
        statusTxt = findViewById(R.id.updDate) as TextView

        bidProgress = findViewById(R.id.bidProgress) as ProgressBar
        bidTxt = findViewById(R.id.bidText) as TextView

        offerProgress = findViewById(R.id.offerProgress) as ProgressBar
        offerTxt = findViewById(R.id.askText) as TextView
    }

    constructor (context: Context, attrs: AttributeSet?): this(context, attrs, 0, 0)

    constructor(context: Context, viewModel: SpreadViewModel): this(context, null, 0, 0) {
        this.viewModel = viewModel
    }
}