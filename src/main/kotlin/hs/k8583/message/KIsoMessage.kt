package hs.k8583.message

import com.solab.iso8583.IsoMessage
import com.solab.iso8583.parse.FieldParseInfo

open class KIsoMessage : IsoMessage() {
  var fieldInfo: Map<Int, FieldParseInfo>? = null

  operator fun get(field: Int): String? {
    return getObjectValue<String>(field)
  }

  operator fun <T> get(field: Int): T? {
    return getObjectValue<T>(field)
  }
  
  operator fun set(field: Int, value: String) {
    fieldInfo?.get(field)?.let { info ->
      setValue(field, value, info.type, info.length)
    }
  }
}
