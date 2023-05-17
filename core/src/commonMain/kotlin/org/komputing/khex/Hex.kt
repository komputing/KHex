package org.komputing.khex


/**
 * Represents all the chars used for nibble
 */
private const val CHARS = "0123456789abcdef"

/**
 * Encodes the given byte [value] as an hexadecimal character.
 */
public fun encode(value: Byte): String {
    return CHARS[value.toInt().shr(4) and 0x0f].toString() + CHARS[value.toInt().and(0x0f)].toString()
}

/**
 * Encodes the given byte array [value] to its hexadecimal representations, and prepends the given [prefix] to it.
 *
 * Note that by default the 0x prefix is prepended to the result of the conversion.
 * If you want to have the representation without the 0x prefix, pass to this method an empty [prefix].
 */
public fun encode(value: ByteArray, prefix: String = "0x"): String {
    return prefix + value.joinToString("") { encode(it) }
}

/**
 * Converts the given [ch] into its integer representation considering it as an hexadecimal character.
 */
private fun hexToBin(ch: Char): Int = when (ch) {
    in '0'..'9' -> ch - '0'
    in 'A'..'F' -> ch - 'A' + 10
    in 'a'..'f' -> ch - 'a' + 10
    else -> throw(IllegalArgumentException("'$ch' is not a valid hex character"))
}

/**
 * Parses the given [value] reading it as an hexadecimal string, and returns its byte array representation.
 *
 * Note that either 0x-prefixed string and no-prefixed hex strings are supported.
 *
 * @throws IllegalArgumentException if the [value] is not an hexadecimal string.
 */
public fun decode(value: String): ByteArray {
    // An hex string must always have length multiple of 2
    if (value.length % 2 != 0) {
        throw IllegalArgumentException("hex-string must have an even number of digits (nibbles)")
    }

    // Remove the 0x prefix if it is set
    val cleanInput = if (value.startsWith("0x")) value.substring(2) else value

    return ByteArray(cleanInput.length / 2).apply {
        var i = 0
        while (i < cleanInput.length) {
            this[i / 2] = ((hexToBin(cleanInput[i]) shl 4) + hexToBin(
                cleanInput[i + 1]
            )).toByte()
            i += 2
        }
    }
}
