package my.piglatin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class TranslatorServiceTest {

    private TranslatorService translatorService = new TranslatorService();

    @Test
    void paragraphShouldBeTranslated() {
        var paragraph = "    Sentence 1 about apples.\r\n   Sentence 2 about this-and-that and something else.\n";
        assertThat(translatorService.translate(paragraph))
            .isEqualTo("    Entencesay 1 aboutway applesway.\n   Entencesay 2 aboutway histay-andway-hattay andway omethingsay elseway.");
    }

    @Test
    void sentenceShouldBeTranslated() {
        var sentence = "Duncan McCloud've eaten a lot of apples, kids!";
        assertThat(translatorService.translate(sentence))
            .isEqualTo("Uncanday CcLoudvem'ay eatenway away otlay ofway applesway, idskay!");
    }

    @TestFactory
    Stream<DynamicTest> hyphenShouldBeConsideredAsWordSeparator() {
        return Stream.of(
                new SimpleImmutableEntry<>("tik-tok", "iktay-oktay"),
                new SimpleImmutableEntry<>("this-that", "histay-hattay"),
                new SimpleImmutableEntry<>("if-else-then", "ifway-elseway-hentay")
            )
            .map(entry ->
                dynamicTest(
                    entry.getKey() + " -> " + entry.getValue(),
                        () -> assertThat(translatorService.translate(entry.getKey())).isEqualTo(entry.getValue())
                )
            );
    }

}