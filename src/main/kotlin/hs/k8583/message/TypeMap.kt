package hs.k8583.message

import com.solab.iso8583.parse.FieldParseInfo

class TypeMap(definition: TypeMap.() -> Unit) {
  val typeMap = mutableMapOf<Int, Map<Int, FieldParseInfo>>()

  init {
    definition()
  }

  infix fun Int.has(fields: FieldDefinition.() -> Unit) {
    typeMap[this] = FieldDefinition(fields).fieldMap
  }
}
