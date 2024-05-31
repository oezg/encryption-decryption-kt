package encryptdecrypt

import java.io.File

data class Arguments(val mode: (Algorithm, String, Int) -> String, val key: Int, val data: String, val alg: Algorithm, val out: File?)

fun parseArgs(args: Array<String>): Arguments {

    fun <T>fetchValueByName(name: String, default: T, defaultId: String? = null, decode: (String) -> T): T {
        val index = args.indexOf("-$name")
        val value = args.getOrNull(index + 1)
        return when {
            index < 0 || value == null || value.startsWith("-") || value == defaultId  -> default
            else -> decode(value)
        }
    }

    val mode = fetchValueByName("mode", DefaultMode, "enc") {
        when (it) {
            "dec" -> Algorithm::decrypt
            else -> throw IllegalArgumentException("Error: mode must be 'enc' or 'dec'")
        }
    }

    val key = fetchValueByName("key", DefaultKey) {
        try {
            it.toInt()
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("Error: key must be a number")
        }
    }

    val input = fetchValueByName("in", null) { filename -> File(filename).readText() }

    val data = fetchValueByName("data", input ?: DefaultData) { it }

    val out = fetchValueByName("out", null) { filename -> File(filename) }

    val alg = fetchValueByName("alg", DefaultAlgorithm, "shift") {
        when (it) {
            "unicode" -> Algorithm.Unicode
            else -> throw IllegalArgumentException("Error: algorithm must be 'shift' or 'unicode'")
        }
    }

    return Arguments(mode, key, data, alg, out)
}