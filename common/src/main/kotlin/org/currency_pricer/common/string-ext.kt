package org.currency_pricer.common

/**
 * Created by PC2User on 18.02.2017.
 */

fun String.count(c: Char): Int {
    return this.filter { it == c }.length
}