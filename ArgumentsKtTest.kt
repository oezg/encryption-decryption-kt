package encryptdecrypt

import java.io.File
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ArgumentsKtTest {

    @Test
    fun `parseArgs for shift`() {
        assertEquals(
            Arguments(
                mode = DefaultMode,
                key = 5,
                data = "Welcome to hyperskill!",
                alg = Algorithm.Shift,
                out = null
            ),
            parseArgs(
                arrayOf("-key", "5", "-alg", "shift", "-data", "Welcome to hyperskill!", "-mode", "enc")
            )
        )
        assertEquals(
            Arguments(
                mode = Algorithm::decrypt,
                key = 5,
                data = "Bjqhtrj yt mdujwxpnqq!",
                alg = Algorithm.Shift,
                out = null
            ),
            parseArgs(
                arrayOf("-key", "5", "-alg", "shift", "-data", "Bjqhtrj yt mdujwxpnqq!", "-mode", "dec")
            )
        )
        assertEquals(
            Arguments(
                mode = Algorithm::decrypt,
                key = 5,
                data = "Bjqhtrj yt mdujwxpnqq!",
                alg = Algorithm.Shift,
                out = File("dummy.txt")
            ),
            parseArgs(
                arrayOf("-key", "5", "-alg", "shift", "-data", "Bjqhtrj yt mdujwxpnqq!", "-mode", "dec", "-out", "dummy.txt")
            )
        )
    }
    @Test

    fun `parseArgs for unicode`() {
        assertEquals(
            Arguments(
                mode = DefaultMode,
                key = 5,
                data = "Welcome to hyperskill!",
                alg = Algorithm.Unicode,
                out = null
            ),
            parseArgs(
                arrayOf("-mode", "enc", "-key", "5", "-data", "Welcome to hyperskill!", "-alg", "unicode")
            )
        )
        assertEquals(
            Arguments(
                mode = Algorithm::decrypt,
                key = 5,
                data = "\\jqhtrj%yt%m~ujwxpnqq&",
                alg = Algorithm.Unicode,
                out = null
            ),
            parseArgs(
                arrayOf("-key", "5", "-alg", "unicode", "-data", "\\jqhtrj%yt%m~ujwxpnqq&", "-mode", "dec")
            )
        )
        assertEquals(
            Arguments(
                mode = Algorithm::decrypt,
                key = 5,
                data = "\\jqhtrj%yt%m~ujwxpnqq&",
                alg = Algorithm.Unicode,
                out = File("dummy.txt")
            ),
            parseArgs(
                arrayOf("-key", "5", "-alg", "unicode", "-data", "\\jqhtrj%yt%m~ujwxpnqq&", "-mode", "dec", "-out", "dummy.txt")
            )
        )
    }
}