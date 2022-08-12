package hs.k8583.message

class MessageDefinition(definition: MessageDefinition.() -> Unit) {
  val valueMap = mutableMapOf<Int, Any>()

  init {
    definition()
  }

  infix fun Int.from(value: Any) {
    valueMap[this] = value
  }
}
