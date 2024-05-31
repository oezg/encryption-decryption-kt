package encryptdecrypt

enum class Algorithm {
    Shift
    {
        override fun encrypt(data: String, key: Int): String {
            val shift = if (key < 0) key + 26 else key
            return String(
                data.map { char ->
                    if (char.isLetter()) {
                        (((char.code or 32) - 97 + shift) % 26 + (char.code and 32) + 65).toChar()
                    } else
                    char
                }.toCharArray()
            )
        }
    },
    Unicode
    {
        override fun encrypt(data: String, key: Int) =
            String(data.map { it + key }.toCharArray())
    };

    abstract fun encrypt(data: String, key: Int): String
    open fun decrypt(data: String, key: Int): String = encrypt(data, -key)

}