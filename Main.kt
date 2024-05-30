package encryptdecrypt

const val DefaultKey = 0
val DefaultMode = Algorithm::encrypt
const val DefaultData = ""
val DefaultAlgortihm = Algorithm.Shift

fun main(args: Array<String>) {
    val arguments = parseArgs(args)
    val result = arguments.mode(arguments.alg, arguments.data, arguments.key)
    if (arguments.out == null) {
        println(result)
    } else {
        arguments.out.writeText(result)
    }
}