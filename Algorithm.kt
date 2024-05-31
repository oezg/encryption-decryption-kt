package encryptdecrypt

interface Algorithm {
    fun encrypt(data: String, key: Int): String
    fun decrypt(data: String, key: Int) = encrypt(data, -key)
}

object Shift : Algorithm {
    const val a = 'a'
    const val z = 'z'
    const val A = 'A'
    const val Z = 'Z'
    const val width = z - a + 1
    override fun encrypt(data: String, key: Int): String {
        val shift = ((key % width) + width) % width
        return String(
            data.map { char ->
                when {
                    char in a..z -> a + (((char - a) + shift) % width)
                    char in A..Z -> A + (((char - A) + shift) % width)
                    else -> char
                }
            }.toCharArray()
        )
    }
}

object Unicode : Algorithm {
    override fun encrypt(data: String, key: Int) = String(data.map { it + key }.toCharArray())
}