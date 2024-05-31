package encryptdecrypt

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class AlgorithmTest {

    @Test
    fun `Shift encrypt`() {
        assertEquals(
            /* expected = */ "Bjqhtrj yt mdujwxpnqq!",
            /* actual = */ Algorithm.Shift.encrypt("Welcome to hyperskill!", 5)
        )
    }

    @Test
    fun `Shift decrypt`() {
        assertEquals(
            /* expected = */ "Welcome to hyperskill!",
            /* actual = */ Algorithm.Shift.decrypt("Bjqhtrj yt mdujwxpnqq!", 5)
        )
    }

    @Test
    fun `Unicode encrypt`() {
        assertEquals(
            /* expected = */ "\\jqhtrj%yt%m~ujwxpnqq&",
            /* actual = */ Algorithm.Unicode.encrypt("Welcome to hyperskill!", 5)
        )
    }

    @Test
    fun `Unicode decrypt`() {
        assertEquals(
            /* expected = */ "Welcome to hyperskill!",
            /* actual = */ Algorithm.Unicode.decrypt("\\jqhtrj%yt%m~ujwxpnqq&", 5)
        )
    }


}