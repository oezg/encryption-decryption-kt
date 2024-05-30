package encryptdecrypt

import java.io.File

data class Arguments(val mode: (Algorithm, String, Int) -> String, val key: Int, val data: String, val alg: Algorithm, val out: File?)

fun parseArgs(args: Array<String>): Arguments {
    val modeIndex = args.indexOf("-mode")
    val mode = when {
        modeIndex < 0 -> Algorithm::encrypt
        modeIndex == args.lastIndex -> Algorithm::encrypt
        args[modeIndex + 1].startsWith("-") -> Algorithm::encrypt
        args[modeIndex + 1] == "enc" -> Algorithm::encrypt
        args[modeIndex + 1] == "dec" -> Algorithm::decrypt
        else -> throw IllegalArgumentException("Error: mode must be 'enc' or 'dec'")
    }

    val keyIndex = args.indexOf("-key")
    val key = when {
        keyIndex < 0 -> 0
        keyIndex == args.lastIndex -> 0
        args[keyIndex + 1].startsWith("-") -> 0
        else -> try {
            args[keyIndex + 1].toInt()
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("Error: key must be a number")
        }
    }

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

    val algIndex = args.indexOf("-alg")
    val alg = when {
        algIndex < 0 -> Algorithm.Shift
        algIndex == args.lastIndex -> Algorithm.Shift
        args[algIndex + 1].startsWith("-") -> Algorithm.Shift
        args[algIndex + 1] == "shift" -> Algorithm.Shift
        args[algIndex + 1] == "unicode" -> Algorithm.Unicode
        else -> throw IllegalArgumentException("Error: algorithm must be 'shift' or 'unicode'")
    }

    return Arguments(mode, key, data, alg, out)
}