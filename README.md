# KHex
KHex is a Kotlin multiplatform library to deal with hexadecimal values.

It was written as part of [KEthereum](https://github.com/komputing/KEthereum) but then extracted as it can be useful 
outside this context and deduplicate code this way.

# Download
The following library is available on Jitpack. The current version is: 

[![](https://jitpack.io/v/komputing/khex.svg)](https://jitpack.io/#komputing/khex)

In order to use it, first of all include the Jitpack maven repository inside your project `build.gradle` file: 

```groovy
allprojects {
    repositories {
        //...
        maven { url 'https://jitpack.io' }
    }
}
```

Then, include the modules inside your project: 

```groovy
dependencies {
    implementation 'com.github.komputing.khex:{module}-{platform}:{version}'
}
```

## Available modules
| Module | Description | 
| :----- | :---------- |
| `core` | Contains the basic `Hex` object |
| `extensions` | Contains all the useful extension functions |

## Supported platforms
| Module | Supported platforms |
| :------- | :-------: |
| `core` | `common`, `jvm`, `native` |
| `extensions` | `common`, `jvm`, `native` | 


## Examples
```groovy
implementation 'com.github.komputing.khex:khex-common:{tag}'
implementation 'com.github.komputing.khex:khex-jvm:{tag}'

implementation 'com.github.komputing.khex:extensions-native:{tag}'
implementation 'com.github.komputing.khex:extensions-js:{tag}'
```

# Usage
## As a function
You can use the static functions contained inside this library in order to encode or decode any hexadecimal value.
 
```kotlin
// === Encoding ===

val myByteValue = 31.toByte()
encode(myByteValue)

val myByteArrayValue = byteArrayOf(31, 32 , 33)
encode(myByteArrayValue)


// === Decoding ===

val myHexString = "0xaa12456789bb"
decode(myHexString)
```

## As extension functions
By including the `extensions` module, you will be able to access a list of extensions functions that can be useful when 
working with strings and byte arrays/lists.

```kotlin
// === ByteArray ===
byteArrayOf(1, 2, 3).toHexString(prefix = "0x")
byteArrayOf(1, 2, 3).toNoPrefixHexString()

// === List<Byte> ===
listOf(1.toByte(), 2.toByte()).toHexString(prefix = "0x")
listOf(1.toByte(), 2.toByte()).toNoPrefixHexString()

// === String ===
"0xaa12456789bb".hexToByteArray()
"0xaa12456789bb".has0xPrefix()
"aa12456789bb".prepend0xPrefix()
"0xaa12456789bb".clean0xPrefix()

```  

# License
MIT
