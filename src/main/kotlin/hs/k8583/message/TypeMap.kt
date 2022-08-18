package hs.k8583.message

import com.solab.iso8583.parse.FieldParseInfo

class TypeMap(definition: TypeMap.() -> Unit) {
  val typeMap = mutableMapOf<Int, Map<Int, FieldParseInfo>>()

  init {
    definition()
  }

  infix fun Int.has(fields: FieldDefinition.() -> Unit): Pair<Int, MutableMap<Int, FieldParseInfo>> {
    val type = this
    return FieldDefinition(fields).fieldMap.let {
      typeMap[type] = it
      Pair(type, it)
    }
  }

  fun baseMap(fields: FieldDefinition.() -> Unit): TypeMap {
    typeMap[BASE_TYPE] = FieldDefinition(fields).fieldMap
    return this
  }

  infix fun Int.extendsBase(fields: FieldDefinition.() -> Unit): Pair<Int, Map<Int, FieldParseInfo>>? {
    val type = this
    return typeMap[BASE_TYPE]?.let {
      val allFields = it.plus(FieldDefinition(fields).fieldMap)
      typeMap[type] = allFields
      Pair(type, allFields)
    }
  }

  infix fun Int.extends(otherType: Int): Pair<Int, Map<Int, FieldParseInfo>>? {
    val type = this
    return typeMap[otherType]?.let {
      typeMap[type] = it
      Pair(type, it)
    }
  }

  infix fun Pair<Int, Map<Int, FieldParseInfo>>?.and(fields: FieldDefinition.() -> Unit) {
    this?.let { (type, _fields) ->
      typeMap[type] = _fields.plus(FieldDefinition(fields).fieldMap)
    }
  }

  companion object {
    private const val BASE_TYPE = 0
  }
}
