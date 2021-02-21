![CI](https://github.com/komputing/KHex/workflows/Continuous%20Integration/badge.svg)
# What is it?

KHex is a Kotlin multiplatform library to deal with hexadecimal encoding and decoding.

It was incubated as part of [KEthereum](https://github.com/komputing/KEthereum) but then extracted as it can be useful
outside this context.


# Get it

## GitHub Packages

This library is available through GitHub Packages.

![badge][badge-js]
![badge][badge-jvm]

In order to use it, first include the GitHub Packages maven repository inside your project build.gradle.kts file:

```kotlin
repositories {
    maven {
        name = "komputing/KHex GitHub Packages"
        url = uri("https://maven.pkg.github.com/komputing/KHex")
        credentials {
            username = "token"
            password = "\u0039\u0032\u0037\u0034\u0031\u0064\u0038\u0033\u0064\u0036\u0039\u0061\u0063\u0061\u0066\u0031\u0062\u0034\u0061\u0030\u0034\u0035\u0033\u0061\u0063\u0032\u0036\u0038\u0036\u0062\u0036\u0032\u0035\u0065\u0034\u0061\u0065\u0034\u0032\u0062"
        }
    }
}
```
When 'username' could be anything and 'password' is an [encoded access token for public access](https://github.community/t/download-from-github-package-registry-without-authentication/14407/44).


## JitPack (JVM only!)

This library is available on Jitpack. The current version is:

[![](https://jitpack.io/v/komputing/khex.svg)](https://jitpack.io/#komputing/khex)
![badge][badge-jvm]

In order to use it, first include the Jitpack maven repository inside your project `build.gradle` file: 

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

[badge-js]: http://img.shields.io/badge/platform-js-yellow.svg?style=flat
[badge-jvm]: http://img.shields.io/badge/platform-jvm-orange.svg?style=flat