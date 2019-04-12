package org.komputing.khex

import kotlin.test.*

class TheHexFun {

    private val hexRegex = Regex("0[xX][0-9a-fA-F]+")

    @Test
    fun weCanProduceSingleDigitHex() {
        assertEquals(Hex.encode(0.toByte()), "00")
        assertEquals(Hex.encode(1.toByte()), "01")
        assertEquals(Hex.encode(15.toByte()), "0f")
    }

    @Test
    fun weCanProduceDoubleDigitHex() {
        assertEquals(Hex.encode(16.toByte()), "10")
        assertEquals(Hex.encode(42.toByte()), "2a")
        assertEquals(Hex.encode(255.toByte()), "ff")
    }

    @Test
    fun prefixIsIgnored() {
        assertTrue(Hex.decode("0xab").contentEquals(Hex.decode("ab")))
    }

    @Test
    fun sizesAreOk() {
        assertEquals(Hex.decode("0x").size, 0)
        assertEquals(Hex.decode("ff").size, 1)
        assertEquals(Hex.decode("ffaa").size, 2)
        assertEquals(Hex.decode("ffaabb").size, 3)
        assertEquals(Hex.decode("ffaabb44").size, 4)
        assertEquals(Hex.decode("0xffaabb4455").size, 5)
        assertEquals(Hex.decode("0xffaabb445566").size, 6)
        assertEquals(Hex.decode("ffaabb44556677").size, 7)
    }

    @Test
    fun exceptionOnOddInput() {
        assertFailsWith<IllegalArgumentException> {
            Hex.decode("0xa")
        }
    }

    @Test
    fun testRoundTrip() {
        assertEquals(Hex.encode(Hex.decode("00")), "0x00")
        assertEquals(Hex.encode(Hex.decode("ff")), "0xff")
        assertEquals(Hex.encode(Hex.decode("abcdef")), "0xabcdef")
        assertEquals(Hex.encode(Hex.decode("0xaa12456789bb")), "0xaa12456789bb")
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
            Hex.decode("0xx")
        }
    }
}
