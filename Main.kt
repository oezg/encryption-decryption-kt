package encryptdecrypt

fun main(args: Array<String>) {
    val (mode, key, data) = parseArgs(args)
    require(mode == "enc" || mode == "dec") { "$mode must be 'enc' or 'dec'" }
    require(key >= 0) { "$key must be positive" }

    if (mode == "enc")
        data.encrypt(key)
    else
        data.decrypt(key)
}

fun parseArgs(args: Array<String>): Triple<String, Int, String> {
    val modeIndex = args.indexOf("-mode")
    var mode = "enc"
    if (modeIndex >= 0 && modeIndex < args.lastIndex) {
        mode = args[modeIndex + 1]
    }
    val keyIndex = args.indexOf("-key")
    var key = 0
    if (keyIndex >= 0 && keyIndex < args.lastIndex) {
        key = args[keyIndex + 1].toInt()
    }
    val dataIndex = args.indexOf("-data")
    var data = ""
    if (dataIndex >= 0 && dataIndex < args.lastIndex) {
        data = args[dataIndex + 1]
    }
    return Triple(mode, key, data)
}

fun String.encrypt(key: Int) {
    this.map { it + key }.forEach(::print)
}

fun String.decrypt(key: Int) {
    this.encrypt(-key)
}