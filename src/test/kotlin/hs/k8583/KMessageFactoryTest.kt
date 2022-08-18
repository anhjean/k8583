package hs.k8583

import com.solab.iso8583.IsoType
import com.solab.iso8583.IsoValue
import com.solab.iso8583.parse.LllvarParseInfo
import com.solab.iso8583.parse.LlvarParseInfo
import com.solab.iso8583.parse.NumericParseInfo
import hs.k8583.util.IsoMessageUtil.get
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class KMessageFactoryTest {
  @Test
  fun `test factory dsl`() {
    val factory1 = messageFactory {
      isBinaryHeader = true
      isUseBinaryBitmap = true
      characterEncoding = "UTF-8"

      fields {
        2 to LlvarParseInfo()
        3 to NumericParseInfo(6)
        12 to NumericParseInfo(12)
        // other fields omitted
        37 to NumericParseInfo(12)
        39 to NumericParseInfo(3)
        48 to LllvarParseInfo()
        49 to NumericParseInfo(3)
      }

      0x1804 uses listOf(12, 24, 37)
      0x1814 uses listOf(12, 37, 39)
      0x1100 uses listOf(2, 3, 12, 37, 48)
      0x1110 uses listOf(2, 3, 12, 37, 39, 48)
      0x1420 extends 0x1100
      0x1430 extends 0x1110
      0x1431 extends 0x1430
    }

    val cardNumber = "9004001200001234"
    val dateTime = "220817060000"
    val message1 = factory1.message(0x1100) {
      2 to cardNumber
      3 to "010000"
      12 to dateTime
      // other fields omitted
      37 to "123412341234"
      49 to "971"
    }

    val message2 = factory1.newMessage(0x1100)
    message2.setField(2, IsoValue(IsoType.LLVAR, cardNumber))
    message2.setValue(3, "010000", IsoType.NUMERIC, 6)
    message2.setValue(12, dateTime, IsoType.NUMERIC, 12)
    message2.setValue(37, "123412341234", IsoType.NUMERIC, 12)
    message2.setValue(49, "971", IsoType.NUMERIC, 3)

    assertEquals(cardNumber, message1[2])
    assertEquals(dateTime, message1[12])
  }
}
