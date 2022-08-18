package hs.k8583.util

import com.solab.iso8583.IsoType
import com.solab.iso8583.IsoValue
import hs.k8583.util.IsoValueUtil.component1
import hs.k8583.util.IsoValueUtil.component2
import hs.k8583.util.IsoValueUtil.component3
import hs.k8583.util.IsoValueUtil.component4
import hs.k8583.util.IsoValueUtil.component5
import hs.k8583.util.IsoValueUtil.component6
import org.junit.jupiter.api.Test

internal class IsoValueUtilTest {
  @Test
  fun `test destructure iso value`() {
    val isoValue = IsoValue(IsoType.NUMERIC, "123412341234", 12)
    val (type, value, encoder, length, encoding, timeZone) = isoValue
    kotlin.test.assertEquals(type, IsoType.NUMERIC)
    kotlin.test.assertEquals(value, "123412341234")
    kotlin.test.assertEquals(12, length)
    kotlin.test.assertNull(encoder)
    kotlin.test.assertNull(encoding)
    kotlin.test.assertNull(timeZone)
  }
}
