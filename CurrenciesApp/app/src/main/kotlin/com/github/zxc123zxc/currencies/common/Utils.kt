package com.github.zxc123zxc.currencies.common

import java.text.SimpleDateFormat
import kotlin.jvm.internal.iterator

data class MinMax<T>(val min: T, val max: T)

fun <T> Iterable<T>.minMax(predicate: (a: T, b: T) -> Boolean ): MinMax<T>? {
    val iter = this.iterator()
    if(!iter.hasNext()) return null

    var min: T = iter.next()
    var max = min

    while (iter.hasNext()) {
        val i = iter.next()
        if(predicate(i, min)) min = i
        if(!predicate(i, max)) max = i
    }

    return MinMax(min, max)
}

object Utils {
    val dateFormat = SimpleDateFormat("dd-MM-yy hh:mm")
}