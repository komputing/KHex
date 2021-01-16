package org.komputing.khex.extensions

import org.komputing.khex.decode
import org.komputing.khex.model.HexString

internal val HEX_REGEX = Regex("(0[xX])?[0-9a-fA-F]*")

/**
 * Parses [this] [String] as an hexadecimal value and returns its [ByteArray] representation.
 *
 * Note that either 0x-prefixed string and no-prefixed hex strings are supported.
 *
 * @throws IllegalArgumentException if [this] is not an hexadecimal string.
 */
public fun HexString.hexToByteArray(): ByteArray = decode(string)

/**
 * Returns `true` if and only if [this] value starts with the `0x` prefix.
 */
public fun HexString.has0xPrefix(): Boolean = string.startsWith("0x")

/**
 * Returns a new [String] obtained by prepends the `0x` prefix to [this] value,
 * without looking if it already is prefixed by it.
 *
 * Examples:
 * ```kotlin
 * val myString = HexString("123")
 * assertEquals("0x123", myString.prepend0xPrefix().string)
 * assertEquals("0x0x123", myString.prepend0xPrefix().prepend0xPrefix().string)
 * ```
 */
public fun HexString.prepend0xPrefix(): HexString = HexString(if (has0xPrefix()) string else "0x$string")

/**
 * Returns a new [String] obtained by removing the first occurrence of the `0x` prefix from [this] string, if it has it.
 *
 * Examples:
 * ```kotlin
 * assertEquals("123", HexString("123").clean0xPrefix().string)
 * assertEquals("123", HexString("0x123").clean0xPrefix().string)
 * assertEquals("0x123", HexString("0x0x123").clean0xPrefix().string)
 * ```
 */
public fun HexString.clean0xPrefix(): HexString = HexString(if (has0xPrefix()) string.substring(2) else string)

/**
 * Returns if a given string is a valid hex-string - either with or without 0x prefix
 */
public fun HexString.isValidHex(): Boolean = HEX_REGEX.matches(string)