package encryptdecrypt

fun main() {
    val text = "we found a treasure!"
    val result = mutableListOf<Char>()
    for (letter in text) {
        if (letter.isLetter()) {
            result.add(cipher(letter))
        } else {
            result.add(letter)
        }
    }
    println(result.joinToString(""))
}

fun cipher(c: Char) : Char {
    val idx = ('a'..'z').indexOf(c)
    return ('z' - idx)
}