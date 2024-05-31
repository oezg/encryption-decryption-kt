package encryptdecrypt

interface Algorithm {
    fun encrypt(data: String, key: Int): String
    fun decrypt(data: String, key: Int) = encrypt(data, -key)
}

object Shift : Algorithm {
        override fun encrypt(data: String, key: Int): String {
            val shift = ((key % 26) + 26) % 26
            return String(
                data.map { char ->
                    when {
                        char in 'a'..'z' -> 'a' + (((char - 'a') + shift) % 26)
                        char in 'A'..'Z' -> 'A' + (((char - 'A') + shift) % 26)
                        else -> char
                    }
                }.toCharArray()
            )
        }
}

object Unicode : Algorithm {
        override fun encrypt(data: String, key: Int) = String(data.map { it + key }.toCharArray())
}