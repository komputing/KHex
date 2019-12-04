# What is it?
KHex is a Kotlin library to deal with hexadecimal encoding and decoding.

It was incubated as part of [KEthereum](https://github.com/komputing/KEthereum) but then extracted as it can be useful
outside this context.

# Get it

This library is available on Jitpack. The current version is:

[![](https://jitpack.io/v/komputing/khex.svg)](https://jitpack.io/#komputing/khex)

In order to use it, first of all include the Jitpack maven repository inside your project `build.gradle` file: 

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```

Then, include the modules inside your project: 

```groovy
dependencies {
    implementation 'com.github.komputing.khex:<module>:<version>'
}
```

# Available modules

| Module | Description | 
| :----- | :---------- |
| `core` | Contains the core code |
| `extensions` | Contains useful extension functions |

# Usage
## As a function
You can use the functions contained inside this library in order to encode or decode any hexadecimal value.
 
```kotlin
// Encoding

val myByteValue = 31.toByte()
encode(myByteValue)

val myByteArrayValue = byteArrayOf(31, 32 , 33)
encode(myByteArrayValue)


// Decoding

val myHexString = "0xaa12456789bb"
decode(myHexString)
```

## As extension functions
By including the `extensions` module, you will be able to access a list of extensions functions that can be useful when 
working with strings and byte arrays/lists.

```kotlin
// ByteArray
byteArrayOf(1, 2, 3).toHexString(prefix = "0x")
byteArrayOf(1, 2, 3).toNoPrefixHexString()

// List<Byte>
listOf(1.toByte(), 2.toByte()).toHexString(prefix = "0x")
listOf(1.toByte(), 2.toByte()).toNoPrefixHexString()

// StringHexString("0xaa12456789bb").hexToByteArray()
HexString("0xaa12456789bb").has0xPrefix()
HexString("aa12456789bb").prepend0xPrefix()
HexString("0xaa12456789bb").clean0xPrefix()
```  

# License
MIT
