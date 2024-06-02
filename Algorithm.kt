package encryptdecrypt

import encryptdecrypt.Shift.width

interface Algorithm {
    fun encrypt(data: String, key: Int): String
    fun decrypt(data: String, key: Int) = encrypt(data, -key)
}


object Shift : Algorithm {
    private const val width = 'z' - 'a' + 1
    private val lowerCase = 'a'..'z'
    private val upperCase = 'A'..'Z'

    override fun encrypt(data: String, key: Int): String {
        val shift = ((key % width) + width) % width
        return String(
            CharArray(data.length) { idx ->
                data[idx].shiftForRange(lowerCase, shift).shiftForRange(upperCase, shift)
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