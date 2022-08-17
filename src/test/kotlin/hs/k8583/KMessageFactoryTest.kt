package hs.k8583

import com.solab.iso8583.IsoType
import com.solab.iso8583.parse.LlvarParseInfo
import com.solab.iso8583.parse.NumericParseInfo
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class KMessageFactoryTest {
  @Test
  fun `test factory dsl`() {
    val factory1 = messageFactory {
      characterEncoding = "UTF8"

      parseMap {
        0x1100 has {
          2 to LlvarParseInfo()
          3 to NumericParseInfo(6)
          12 to NumericParseInfo(12)
          // other fields omitted
        }
      }
    }

    val message1 = factory1.newMessage(0x1100).apply {
      setValue(2, "9004001200001234", IsoType.LLVAR, 16)
      setValue(37, "123412341234", IsoType.NUMERIC, 12)
    }

    val message2 = factory1.message(0x1100) {
      2 from "9004001200001234"
      3 from "010000"
      12 from "220817060000"
      // other fields omitted
    }

    assertEquals(message1.debugString(), message2.debugString())
  }
}
