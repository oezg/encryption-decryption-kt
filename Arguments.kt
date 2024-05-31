package encryptdecrypt

import java.io.File

data class Arguments(val mode: (Algorithm, String, Int) -> String, val key: Int, val data: String, val alg: Algorithm, val out: File?)

fun parseArgs(args: Array<String>): Arguments {

    fun <T>fetchValueByName(name: String, defaultValue: T, defaultArg: String? = null, convertToValue: (String) -> T): T {
        val index = args.indexOf("-$name")
        val argument = args.getOrNull(index + 1)
        return if (index < 0 || argument == null || argument.startsWith("-") || argument == defaultArg)
            defaultValue
        else
            convertToValue(argument)
    }

    val alg = fetchValueByName("alg", DefaultAlgorithm, "shift") {
        require(it == "unicode") { "Error: algorithm must be 'shift' or 'unicode'" }
        return@fetchValueByName Unicode
    }

    val mode = fetchValueByName("mode", DefaultMode, "enc") {
        require(it == "dec") { "Error: mode must be 'enc' or 'dec'" }
        return@fetchValueByName Algorithm::decrypt
    }

    val key = fetchValueByName("key", DefaultKey) {
        require(it.toIntOrNull() != null) { "Error: key must be a number" }
        return@fetchValueByName it.toInt()
    }

    val input = fetchValueByName("in", null) {
        require(File(it).isFile) { "Error: input file does not exist" }
        return@fetchValueByName File(it).readText()
    }

    val data = fetchValueByName("data", input ?: DefaultData) { it }

    val out = fetchValueByName("out", null) { filename -> File(filename) }

    return Arguments(mode, key, data, alg, out)
}