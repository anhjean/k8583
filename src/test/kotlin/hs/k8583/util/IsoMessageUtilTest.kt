package hs.k8583.util

import com.solab.iso8583.IsoMessage
import com.solab.iso8583.IsoType
import com.solab.iso8583.IsoValue
import hs.k8583.util.IsoMessageUtil.get
import hs.k8583.util.IsoMessageUtil.set
import org.junit.jupiter.api.Test

internal class IsoMessageUtilTest {
  @Test
  fun `test setter getter`() {
    val message = IsoMessage()
    val cardNumber2 = "9004010088881234"
    message[2] = IsoValue(IsoType.LLVAR, cardNumber2)
    kotlin.test.assertEquals(cardNumber2, message[2])
  }
}
