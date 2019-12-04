package org.komputing.khex.extensions

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.komputing.khex.model.HexString
import kotlin.test.assertEquals

/**
 * Tests for the [String] extensions functions defined inside `HexStringExtensions.kt`
 */
class HexStringExtensionsTests {

    @Test
    fun detect0xWorks() {
        assertEquals(HexString("2").has0xPrefix(), false)
        assertEquals(HexString("0xFF").has0xPrefix(), true)
    }

    @Test
    fun prepend0xWorks() {
        assertEquals(HexString("2").prepend0xPrefix(), HexString("0x2"))
        assertEquals(HexString("0xFF").prepend0xPrefix(), HexString("0xFF"))
    }

    @Test
    fun clean0xWorks() {
        assertEquals(HexString("2").clean0xPrefix(), HexString("2"))
        assertEquals(HexString("0xFF").clean0xPrefix(), HexString("FF"))
    }


    @Test
    fun hexToByteArrayWorks() {
        assertThat(HexString("").hexToByteArray()).isEmpty()
        assertThat(HexString("02").hexToByteArray()).containsExactly(2)
        assertThat(HexString("0xFF").hexToByteArray()).containsExactly(0xFF.toByte())
        assertThat(HexString("0xFFaa").hexToByteArray()).containsExactly(0xFF.toByte(), 0xaa.toByte())
    }
}