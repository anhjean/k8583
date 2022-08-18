package hs.k8583.util

import com.solab.iso8583.CustomFieldEncoder
import com.solab.iso8583.IsoType
import com.solab.iso8583.IsoValue
import java.util.*

object IsoValueUtil {
  operator fun <T> IsoValue<T>.component1(): IsoType = type
  operator fun <T> IsoValue<T>.component2(): T = value
  operator fun <T> IsoValue<T>.component3(): CustomFieldEncoder<T>? = encoder
  operator fun <T> IsoValue<T>.component4(): Int = length
  operator fun <T> IsoValue<T>.component5(): String? = characterEncoding
  operator fun <T> IsoValue<T>.component6(): TimeZone? = timeZone
}
