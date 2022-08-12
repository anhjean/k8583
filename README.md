# k8583

> This library is in early stages of development and is not suited for production use.

**k8583** is a Kotlin wrapper for **j8583**. Although the original library can be as easily used in Kotlin, this library adds a number of convenient features if the primary language of your project is Kotlin.

The `KMessageFactory` class extends the `MessageFactory` class and caches the message parse map during factory set up. It also adds a DSL for building the
factory, setting up message types and fields:

```kotlin
val factory = messageFactory {
  characterEncoding = "UTF8"

  parseMap {
    0x1100 has {
      2 to LlvarParseInfo()
      37 to NumericParseInfo(12)
    }
  }
}
```

To create messages with the factory, you only need to set the field and it's basic value. The other information about the field, e.g. it's `IsoType`
and `length` are taken from the cached type and field map:

```kotlin
val message = factory.message(0x1100) {
  2 from "9004001200001234"
  37 from "123412341234"
}
```
