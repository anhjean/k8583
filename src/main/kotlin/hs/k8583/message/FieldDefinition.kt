package hs.k8583.message

import com.solab.iso8583.parse.FieldParseInfo

class FieldDefinition(definition: FieldDefinition.() -> Unit) {
  val fieldMap = mutableMapOf<Int, FieldParseInfo>()

  init {
    definition()
  }

  infix fun Int.to(parseInfo: FieldParseInfo) {
    fieldMap[this] = parseInfo
  }
}
