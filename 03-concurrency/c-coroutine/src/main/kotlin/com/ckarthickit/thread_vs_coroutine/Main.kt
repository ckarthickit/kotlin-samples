package com.ckarthickit.thread_vs_coroutine

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.awt.SystemColor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong
import kotlin.concurrent.thread


fun main() {
    var startTime = System.currentTimeMillis()
    spawnMillionCoroutines()
    println("spawning million coroutines took: ${TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startTime)} ")
    startTime = System.currentTimeMillis()
    spawnMillionThreads()
    println("spawning million threads took: ${TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startTime)} ")
}

fun spawnMillionThreads() {
    val c = AtomicLong()
    for(i in 1..1_000_000L) {
        thread(start = true) {
            c.addAndGet(i)
        }
    }
    println(c.get())
}

fun spawnMillionCoroutines() {
    val c = AtomicLong()
    for(i in 1..1_000_000L) {
        GlobalScope.launch {
            c.addAndGet(i)
        }
    }
    println(c.get())
}