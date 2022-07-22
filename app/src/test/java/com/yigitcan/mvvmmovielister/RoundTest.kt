package com.yigitcan.mvvmmovielister

import org.junit.Assert
import org.junit.Test
import kotlin.math.roundToInt

class RoundTest {

    @Test
    fun roundNumber() {
        val input = 32.12321
        val output = (input * 100.0).roundToInt() / 100.0
        Assert.assertEquals(32.12,output,0.001)
    }
}