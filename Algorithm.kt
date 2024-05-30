package encryptdecrypt

enum class Algorithm {
    Shift
    {
        private val alphabet = ('a'..'z') + ('A'..'Z')
        private fun shifted(shift: Int) =
            alphabet.subList(shift, 26) + alphabet.take(shift) + alphabet.drop(26 + shift) + alphabet.subList(26, 26 + shift)

        private fun translate(shift: Int): Map<Char, Char> =
            alphabet
                .zip(shifted(shift))
                .toMap()

        override fun encrypt(data: String, key: Int): String {
            val table = translate(((key % 26) + 26) % 26)
            return String(data.map { table.getOrDefault(it, it) }.toCharArray())
        }
        override fun decrypt(data: String, key: Int) = encrypt(data, -key)
    },
    Unicode
    {
        override fun encrypt(data: String, key: Int) = String(data.map { it + key }.toCharArray())
        override fun decrypt(data: String, key: Int) = encrypt(data, -key)
    };

    abstract fun encrypt(data: String, key: Int): String
    abstract fun decrypt(data: String, key: Int): String

}