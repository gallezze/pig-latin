package my.piglatin;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import my.piglatin.translator.ConsonantTranslator;
import my.piglatin.translator.Translator;
import my.piglatin.translator.VowelTranslator;

public class TranslatorService {

    private final List<Translator> translators = List.of(new ConsonantTranslator(), new VowelTranslator());

    public String translate(String source) {
        return Stream.of(source.split("(\n|\r\n)")) //split paragraph into sentence(s)
                .map(sentence ->
                    Stream.of(sentence.split(" ")) //split sentence into words
                        .map(word ->
                            Stream.of(word.split("-")).map(this::transform).collect(Collectors.joining("-")) //split based on "-"
                        )
                        .collect(Collectors.joining(" ")) //put sentence back
                )
                .collect(Collectors.joining("\n")); //put paragraph back, but windows line separator (if any) could be lost, also we're loosing trailing line separator (if any)
    }

    private String transform(String source) {
        return translators.stream()
                .filter(translator -> translator.isApplicable(source))
                .findFirst()
                .map(translator -> translator.transform(source))
                .orElse(source);
    }
}
