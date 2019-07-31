package com.ckarthickit

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun main() {
    GlobalScope.launch {
        println("I'm  a coroutine")
    }
    Thread.sleep(200)
}