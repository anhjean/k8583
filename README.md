# k8583

> This project is in the early stages of development and not suited for production use.

**k8583** is a Kotlin wrapper for its Java counterpart, the [**j8583**.](https://bitbucket.org/chochos/j8583/src/master/) Although the original library can be
as easily used in Kotlin, this library adds a number of convenient features if the primary language of your project is Kotlin.

### Message Factory

k8583 uses Kotlin features to make it easy to work with the ISO8583 standard. Using DSLs you can define message factories in a JSON like structure:

```kotlin
val factory = messageFactory {
  isBinaryHeader = true
  isUseBinaryBitmap = true
  characterEncoding = "UTF-8"

  // define all fields once
  fields {
    2 to LlvarParseInfo()
    3 to NumericParseInfo(6)
    12 to NumericParseInfo(12)
    // other fields omitted
    37 to NumericParseInfo(12)
    39 to NumericParseInfo(3)
    48 to LllvarParseInfo()
    49 to NumericParseInfo(3)
  }

  // define message types by only mentioning the fields used
  // network management
  0x1804 uses listOf(12, 24, 37)
  0x1814 uses listOf(12, 37, 39)
  // financial messages
  0x1100 uses listOf(2, 3, 12, 37, 48)
  0x1110 uses listOf(2, 3, 12, 37, 39, 48)
  // reversals
  0x1420 extends 0x1100
  0x1421 extends 0x1100
  0x1430 extends 0x1110
}
```

### Messages

To create messages, provide the field numbers and their values:

```kotlin
val message = factory.message(0x1100) {
  2 to "9004010088881234"
  3 to "010000"
  12 to "220820064847"
  // other fields omitted
  37 to "123412341234"
  49 to "971"
}
```

### Utility Methods

Get/set message values using an array index syntax:

```kotlin
import hs.k8583.util.IsoMessageUtil.get
import hs.k8583.util.IsoMessageUtil.set

val cardNumber = "9004010088881234"
// setter (only when passing string values)
message[2] = IsoValue(IsoType.LLVAR, cardNumber)
// getter
assertEquals(cardNumber, message[2])
```

Destructure `IsoValue`s to get their component parts:

```kotlin
import hs.k8583.util.IsoValueUtil.component1
import hs.k8583.util.IsoValueUtil.component2
import hs.k8583.util.IsoValueUtil.component3
import hs.k8583.util.IsoValueUtil.component4
import hs.k8583.util.IsoValueUtil.component5
import hs.k8583.util.IsoValueUtil.component6

val isoValue = IsoValue(IsoType.NUMERIC, "123412341234", 12)
val (type, value, encoder, length, encoding, timeZone) = isoValue
```
