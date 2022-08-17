# k8583

> This library is in early stages of development and is not suited for production use.

**k8583** is a Kotlin wrapper for **j8583**. Although the original library can be as easily used in Kotlin, this library adds a number of convenient features if
the primary language of your project is Kotlin.

### The Short Pitch
This library uses Kotlin features to make it easy to work with ISO8583. Using DSLs you can define message factories in a JSON like structure:

```kotlin
// messageFactory is of type KMessageFactory
val factory = messageFactory {
  characterEncoding = "UTF8"

  parseMap {
    0x1100 has {
      2 to LlvarParseInfo()
      3 to NumericParseInfo(6)
      12 to NumericParseInfo(12)
      // other fields omitted
    }
  }
}
```
To create messages, you only need to provide the field numbers and their values:
```kotlin
val message = factory.message(0x1100) {
  2 from "9004001200001234"
  3 from "010000"
  12 from "220817060000"
  // other fields omitted
}
```

## TL;DR
### Building a `MessageFactory`
You would normally build a `MessageFactory` like this:

```kotlin
val factory = MessageFactory<IsoMessage>()
factory.characterEncoding = "UTF-8"
factory.isUseBinaryBitmap = true
factory.isBinaryHeader = true
// NOTE: you are still adding a custom field in 
// your Java/Kotlin code
factory.setCustomField(48, CustomField48())
// the xml file contains your message types and fields
factory.setConfigPath("file.xml")
```
