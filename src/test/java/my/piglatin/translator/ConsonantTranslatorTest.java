package my.piglatin.translator;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

class ConsonantTranslatorTest {

    private final Translator translator = new ConsonantTranslator();

    @TestFactory
    Stream<DynamicTest> nonConsonantWordsShouldBeIgnored() {
        return Stream.of(
                new SimpleImmutableEntry<>("starting with vowel", "abscb"),
                new SimpleImmutableEntry<>("digit", "100"),
                new SimpleImmutableEntry<>("hyphen", "-"),
                new SimpleImmutableEntry<>("empty value", "")
            )
            .map(entry ->
                    DynamicTest.dynamicTest(
                        entry.getKey(),
                        () -> Assertions.assertThat(translator.transform(entry.getValue())).isEqualTo(entry.getValue())
                    )
            );
    }

    @TestFactory
    Stream<DynamicTest> consonantWordShouldBeTransformed() {
        return Stream.of(
                    new SimpleImmutableEntry<>("Hello", "Ellohay"),
                    new SimpleImmutableEntry<>("HELlO", "ELLoHay"),
                    new SimpleImmutableEntry<>("bay", "aybay"),
                    new SimpleImmutableEntry<>("MacDonald", "AcdOnaldmay")
                )
                .map(entry ->
                        DynamicTest.dynamicTest(
                            entry.getKey() + " -> " + entry.getValue(),
                            () -> Assertions.assertThat(translator.transform(entry.getKey())).isEqualTo(entry.getValue())
                        )
                );
    }

    @TestFactory
    Stream<DynamicTest> exemptShouldntBeTransformed() {
        return Stream.of("", "!", "...", "?!")
                .map(suffix -> {
                    var value = "way" + suffix;
                    return DynamicTest.dynamicTest(
                            value + " -> " + value,
                            () -> Assertions.assertThat(translator.transform(value)).isEqualTo(value)
                    );
                });
    }

    @TestFactory
    Stream<DynamicTest> punctuationShouldBeKept() {
        return Stream.of(
                new SimpleImmutableEntry<>("Would've", "Ouldvew'ay"),
                new SimpleImmutableEntry<>("can't", "antca'y"),
                new SimpleImmutableEntry<>("goin'", "oingay'"),
                new SimpleImmutableEntry<>("can't!!", "antca'y!!"),
                new SimpleImmutableEntry<>("Could've?!", "Ouldvec'ay?!")
        )
                .map(entry ->
                        DynamicTest.dynamicTest(
                                entry.getKey() + " -> " + entry.getValue(),
                                () -> Assertions.assertThat(translator.transform(entry.getKey())).isEqualTo(entry.getValue())
                        )
                );
    }

}