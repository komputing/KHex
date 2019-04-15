package org.komputing.khex

import kotlin.test.*

class TheHexFun {

    private val hexRegex = Regex("0[xX][0-9a-fA-F]+")

    @Test
    fun weCanProduceSingleDigitHex() {
        assertEquals(encode(0.toByte()), "00")
        assertEquals(encode(1.toByte()), "01")
        assertEquals(encode(15.toByte()), "0f")
    }

    @Test
    fun weCanProduceDoubleDigitHex() {
        assertEquals(encode(16.toByte()), "10")
        assertEquals(encode(42.toByte()), "2a")
        assertEquals(encode(255.toByte()), "ff")
    }

    @Test
    fun prefixIsIgnored() {
        assertTrue(decode("0xab").contentEquals(decode("ab")))
    }

    @Test
    fun sizesAreOk() {
        assertEquals(decode("0x").size, 0)
        assertEquals(decode("ff").size, 1)
        assertEquals(decode("ffaa").size, 2)
        assertEquals(decode("ffaabb").size, 3)
        assertEquals(decode("ffaabb44").size, 4)
        assertEquals(decode("0xffaabb4455").size, 5)
        assertEquals(decode("0xffaabb445566").size, 6)
        assertEquals(decode("ffaabb44556677").size, 7)
    }

    @Test
    fun exceptionOnOddInput() {
        assertFailsWith<IllegalArgumentException> {
            decode("0xa")
        }
    }

    @Test
    fun testRoundTrip() {
        assertEquals(encode(decode("00")), "0x00")
        assertEquals(encode(decode("ff")), "0xff")
        assertEquals(encode(decode("abcdef")), "0xabcdef")
        assertEquals(encode(decode("0xaa12456789bb")), "0xaa12456789bb")
    }

    @Test
    fun regexMatchesForHEX() {
        assertTrue(hexRegex.matches("0x00"))
        assertTrue(hexRegex.matches("0xabcdef123456"))
    }

    @Test
    fun regexFailsForNonHEX() {
        assertFalse(hexRegex.matches("q"))
        assertFalse(hexRegex.matches(""))
        assertFalse(hexRegex.matches("0x+"))
        assertFalse(hexRegex.matches("0xgg"))
    }

    @Test
    fun detectsInvalidHex() {
        assertFailsWith<IllegalArgumentException> {
            decode("0xx")
        }
    }
}
