package hs.k8583

import com.solab.iso8583.MessageFactory
import com.solab.iso8583.parse.FieldParseInfo
import hs.k8583.message.KIsoMessage
import hs.k8583.message.MessageDefinition
import hs.k8583.message.TypeMap
import org.slf4j.LoggerFactory

class KMessageFactory<T : KIsoMessage> : MessageFactory<T>() {
  private val logger = LoggerFactory.getLogger(KMessageFactory::class.java)
  val typeInfo = mutableMapOf<Int, Map<Int, FieldParseInfo>>()

  fun parseMap(definition: TypeMap.() -> Unit) = this.apply {
    typeInfo.putAll(TypeMap(definition).typeMap)

    for ((type, fields) in typeInfo) {
      setParseMap(type, fields)
    }
  }

  fun message(type: Int, definition: MessageDefinition.() -> Unit): T {
    return newMessage(type).apply {
      fieldInfo = typeInfo[type]

      for ((field, value) in MessageDefinition(definition).valueMap) {
        fieldInfo?.get(field)?.let { fieldInfo ->
          setValue(field, value, fieldInfo.type, fieldInfo.length)
        } ?: logger.warn("field definition not found for %04x{} field {}", type, field)
      }
    }
  }
}

fun messageFactory(definition: KMessageFactory<KIsoMessage>.() -> Unit) = KMessageFactory<KIsoMessage>().apply {
  definition()
}
