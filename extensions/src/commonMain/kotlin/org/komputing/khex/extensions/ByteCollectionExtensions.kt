package org.komputing.khex.extensions

import org.komputing.khex.encode

/**
 * Converts [this] [Collection] of bytes into its hexadecimal string representation prepending to it the given [prefix].
 *
 * Note that by default the 0x prefix is prepended to the result of the conversion.
 * If you want to have the representation without the 0x prefix, use the [toNoPrefixHexString] method or
 * pass to this method an empty [prefix].
 */
public fun Collection<Byte>.toHexString(prefix: String = "0x"): String = encode(this.toByteArray(), prefix)

/**
 * Converts [this] [Collection] of bytes into its hexadecimal representation without prepending any prefix to it.
 */
public fun Collection<Byte>.toNoPrefixHexString(): String = toHexString("")