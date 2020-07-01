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


    @Test
    fun detectingValidHexWorks() {
        assertThat(HexString("0x").isValidHex()).isTrue()
        assertThat(HexString("0x1a").isValidHex()).isTrue()
        assertThat(HexString("0x1abcdef").isValidHex()).isTrue()
        assertThat(HexString("").isValidHex()).isTrue()
        assertThat(HexString("1a").isValidHex()).isTrue()
        assertThat(HexString("1abcdef").isValidHex()).isTrue()
    }

    @Test
    fun detectingInvalidHexWorks() {
        assertThat(HexString("0x0x").isValidHex()).isFalse()
        assertThat(HexString("gg").isValidHex()).isFalse()
        assertThat(HexString("ab0xcd").isValidHex()).isFalse()
        assertThat(HexString("yolo").isValidHex()).isFalse()
        assertThat(HexString("0xyolo").isValidHex()).isFalse()
    }
}