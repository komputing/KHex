package org.komputing.khex.extensions

import org.komputing.khex.decode


/**
 * Parses this string as an hexadecimal value and returns its byte array representation.
 *
 * Note that either 0x-prefixed string and no-prefixed hex strings are supported.
 *
 * @throws IllegalArgumentException if this is not an hexadecimal string.
 */
fun String.hexToByteArray(): ByteArray {
    return decode(this)
}

/**
 * Returns `true` if and only if this value starts with the `0x` prefix.
 */
fun String.has0xPrefix() = startsWith("0x")

/**
 * Returns a new string obtained by prepends the `0x` prefix to this value,
 * without looking if it already is prefixed by it.
 *
 * Examples:
 * ```kotlin
 * val myString = "123"
 * assertEquals("0x123", myString.prepend0xPrefix())
 * assertEquals("0x0x123", myString.prepend0xPrefix().prepend0xPrefix())
 * ```
 */
fun String.prepend0xPrefix() = if (has0xPrefix()) this else "0x$this"

/**
 * Returns a new string obtained by removing the first occurrence of the `0x` prefix from this string, if it has it.
 *
 * Examples:
 * ```kotlin
 * assertEquals("123", "123".clean0xPrefix())
 * assertEquals("123", "0x123".clean0xPrefix())
 * assertEquals("0x123", "0x0x123".clean0xPrefix())
 * ```
 */
fun String.clean0xPrefix() = if (has0xPrefix()) this.substring(2) else this