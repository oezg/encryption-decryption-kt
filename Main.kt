package encryptdecrypt

fun main(args: Array<String>) {
    val arguments = parseArgs(args)
    val result = arguments.mode(arguments.alg, arguments.data, arguments.key)
    if (arguments.out == null) {
        println(result)
    } else {
        arguments.out.writeText(result)
    }
}