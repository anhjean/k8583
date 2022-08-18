package hs.k8583

import com.solab.iso8583.IsoMessage
import com.solab.iso8583.MessageFactory
import com.solab.iso8583.parse.FieldParseInfo
import hs.k8583.message.FieldDefinition
import hs.k8583.message.MessageDefinition
import org.slf4j.LoggerFactory

class KMessageFactory(definition: KMessageFactory.() -> Unit) : MessageFactory<IsoMessage>() {
  private val logger = LoggerFactory.getLogger(KMessageFactory::class.java)
  val fieldMap = mutableMapOf<Int, FieldParseInfo>()
  val typeInfo = mutableMapOf<Int, Map<Int, FieldParseInfo>>()

  init {
    definition()
  }

  fun fields(fieldDefinition: FieldDefinition.() -> Unit) {
    fieldMap.putAll(FieldDefinition(fieldDefinition).fieldMap)
  }

  fun message(type: Int, definition: MessageDefinition.() -> Unit): IsoMessage {
    return newMessage(type).apply {
      val typeFields = typeInfo[type]
      for ((field, value) in MessageDefinition(definition).valueMap) {
        typeFields?.get(field)?.let { fieldInfo ->
          setValue(field, value, fieldInfo.type, fieldInfo.length)
        } ?: logger.warn("field definition not found for %04x{} field {}", type, field)
      }
    }
  }

  infix fun Int.uses(fields: List<Int>) {
    typeInfo[this] = fieldMap.filterKeys { fields.contains(it) }
  }

  infix fun Int.extends(otherType: Int) {
    typeInfo[otherType]?.let { otherFields ->
      typeInfo[this] = otherFields
    }
  }
}

fun messageFactory(definition: KMessageFactory.() -> Unit): KMessageFactory {
  return KMessageFactory(definition)
}
