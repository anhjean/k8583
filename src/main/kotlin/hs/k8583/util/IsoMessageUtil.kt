package hs.k8583.util

import com.solab.iso8583.IsoMessage
import com.solab.iso8583.IsoValue

object IsoMessageUtil {
  operator fun IsoMessage.get(field: Int): String? {
    return getObjectValue<String>(field)
  }

  operator fun IsoMessage.set(field: Int, value: IsoValue<String>) {
    setValue(field, value.value, value.type, value.length)
  }
}
