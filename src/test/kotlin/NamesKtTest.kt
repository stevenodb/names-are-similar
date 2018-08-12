import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class NamesKtTest {

    @Test
    fun main() {
        val allNames = namesFromFile(resourceContentByName("voornamen_jongens_1995_2016.csv"))
        assertThat(namesAreSimilar("Steven", "Stephen", allNames)).isTrue()
        assertThat(namesAreSimilar("Mark", "Marc", allNames)).isTrue()
        assertThat(namesAreSimilar("Tibo", "Tibault", allNames)).isTrue()
        assertThat(namesAreSimilar("Dan", "Daan", allNames)).isTrue()
        assertThat(namesAreSimilar("Steven", "Daan", allNames)).isFalse()
    }
}