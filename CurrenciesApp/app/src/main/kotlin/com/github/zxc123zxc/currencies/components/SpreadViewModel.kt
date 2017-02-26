package com.github.zxc123zxc.currencies.components

import java.util.*

class SpreadViewModel(
        val broker: String,
        val updateDate: Date,
        val bid: Double,
        val offer: Double,
        val minPrice: Double,
        val maxPrice: Double)