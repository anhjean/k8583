package hs.k8583.message

class MessageDefinition(definition: MessageDefinition.() -> Unit) {
  val valueMap = mutableMapOf<Int, Any>()

  init {
    definition()
  }

  infix fun Int.to(value: Any) {
    valueMap[this] = value
  }
}
