package encryptdecrypt

import java.io.File

fun main(args: Array<String>) {
    val arguments = parseArgs(args)
    require(arguments.key >= 0) { "Error: ${arguments.key} must be positive" }

    val result = arguments.mode(arguments.data, arguments.key)
    if (arguments.out == null) {
        println(result)
    } else {
        arguments.out.writeText(result)
    }
}

fun parseArgs(args: Array<String>): Arguments {
    val modeIndex = args.indexOf("-mode")
    val mode = when {
        modeIndex < 0 ||
        modeIndex == args.lastIndex ||
        args[modeIndex + 1].startsWith("-") ||
        args[modeIndex + 1] == "enc" -> ::encrypt
        args[modeIndex + 1] == "dec" -> ::decrypt
        else -> throw IllegalArgumentException("Error: mode must be 'enc' or 'dec'")
    }

    val keyIndex = args.indexOf("-key")
    val key = when {
        keyIndex < 0 ||
        keyIndex == args.lastIndex ||
        args[keyIndex + 1].startsWith("-") -> 0
        else -> try {
            args[keyIndex + 1].toInt()
        } catch (e: NumberFormatException) {
            -1
        }
    }
    require( key >= 0) { "Error: key must be a positive number" }

    val dataIndex = args.indexOf("-data")
    var data = ""
    if (dataIndex >= 0 && dataIndex < args.lastIndex) {
        data = args[dataIndex + 1]
    } else {
        val inputIndex = args.indexOf("-in")
        if (inputIndex >= 0 && inputIndex < args.lastIndex) {
            val filename = args[inputIndex + 1]
            data = File(filename).readText()
        }
    }

    val outIndex = args.indexOf("-out")
    var out: File? = null
    if (outIndex >= 0 && outIndex < args.lastIndex) {
        val filename = args[outIndex + 1]
        out = File(filename)
    }

    return Arguments(mode, key, data, out)
}

fun encrypt(data: String, key: Int): String {
    return String(data.map { it + key }.toCharArray())
}

fun decrypt(data: String, key: Int): String {
    return encrypt(data, -key)
}

data class Arguments(val mode: (String, Int) -> String, val key: Int, val data: String, val out: File?)