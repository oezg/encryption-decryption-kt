package encryptdecrypt

interface Algorithm {
    fun encrypt(data: String, key: Int): String
    fun decrypt(data: String, key: Int) = encrypt(data, -key)
}

object Shift : Algorithm {
    private const val width = 'z' - 'a' + 1

    override fun encrypt(data: String, key: Int): String {
        val shift = ((key % width) + width) % width
        return String(
            CharArray(data.length) { idx ->
                data[idx].shiftForRange('a'..'z', shift).shiftForRange('A'..'Z', shift)
            }
        )
    }

    private fun Char.shiftForRange(range: CharRange, shift: Int) =
        if (this in range)
            range.first + ((this - range.first + shift) % width)
        else
            this
}

object Unicode : Algorithm {
    override fun encrypt(data: String, key: Int) = String(data.map { it + key }.toCharArray())
}