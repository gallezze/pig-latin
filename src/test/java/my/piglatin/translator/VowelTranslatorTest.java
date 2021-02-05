package my.piglatin.translator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

class VowelTranslatorTest {

    private final Translator translator = new VowelTranslator();

    @TestFactory
    Stream<DynamicTest> nonVowelWordsShouldBeIgnored() {
        return Stream.of(
                new SimpleImmutableEntry<>("starting with consonant", "dbmbmnb"),
                new SimpleImmutableEntry<>("digit", "100"),
                new SimpleImmutableEntry<>("hyphen", "-"),
                new SimpleImmutableEntry<>("empty value", "")
            )
            .map(entry ->
                    dynamicTest(
                        entry.getKey(),
                        () -> assertThat(translator.transform(entry.getValue())).isEqualTo(entry.getValue())
                    )
            );
    }

    @TestFactory
    Stream<DynamicTest> vowelWordShouldBeTranslated() {
        return Stream.of(
                new SimpleImmutableEntry<>("apple", "appleway"),
                new SimpleImmutableEntry<>("ilky", "ilkyway")
            )
            .map(entry ->
                    dynamicTest(
                        entry.getKey() + " -> " + entry.getValue(),
                        () -> assertThat(translator.transform(entry.getKey())).isEqualTo(entry.getValue())
                    )
            );
    }

    @TestFactory
    Stream<DynamicTest> exemptShouldntBeTranslated() {
        return Stream.of("", "!", "...", "?!")
                .map(suffix -> {
                    var value = "away" + suffix;
                    return dynamicTest(
                            value + " -> " + value,
                            () -> assertThat(translator.transform(value)).isEqualTo(value)
                    );
                });
    }

    @TestFactory
    Stream<DynamicTest> punctuationShouldBeKept() {
        return Stream.of(
                new SimpleImmutableEntry<>("oranges...", "orangesway..."),
                new SimpleImmutableEntry<>("ain'", "ainway'"),
                new SimpleImmutableEntry<>("ain'!!!", "ainway'!!!"),
                new SimpleImmutableEntry<>("Apple?!", "Appleway?!")
            )
            .map(entry ->
                dynamicTest(
                    entry.getKey() + " -> " + entry.getValue(),
                    () -> assertThat(translator.transform(entry.getKey())).isEqualTo(entry.getValue())
                )
            );
    }

}