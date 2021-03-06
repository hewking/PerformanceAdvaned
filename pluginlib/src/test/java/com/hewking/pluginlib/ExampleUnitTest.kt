package com.hewking.pluginlib

import com.hewking.pluginlib.util.Reflect
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    /**
     * 测试Reflect反射工具类对于class的处理
     */
    @Test
    fun testReflectClass(){
        val num: Int = 10
        Reflect.on(num).call("toHexString",2)
    }
}
