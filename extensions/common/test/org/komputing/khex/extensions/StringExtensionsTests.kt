package org.komputing.khex.extensions

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests for the [String] extensions functions defined inside `StringExtensions.kt`
 */
class StringExtensionsTests {

    @Test
    fun detect0xWorks() {
        assertEquals("2".has0xPrefix(), false)
        assertEquals("0xFF".has0xPrefix(), true)
    }

    @Test
    fun prepend0xWorks() {
        assertEquals("2".prepend0xPrefix(), "0x2")
        assertEquals("0xFF".prepend0xPrefix(), "0xFF")
    }

    @Test
    fun clean0xWorks() {
        assertEquals("2".clean0xPrefix(), "2")
        assertEquals("0xFF".clean0xPrefix(), "FF")
    }
}