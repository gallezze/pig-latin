package my.piglatin.translator;

import java.util.ArrayList;
import java.util.List;

public class VowelTranslator extends AbstractTranslator {

    private final List<Character> SUFFIX = List.of('w', 'a', 'y');

    private static final List<Character> VOWELS = List.of('a', 'e', 'i', 'o', 'u');

    @Override
    public boolean isApplicable(String source) {
        return !source.isEmpty() && VOWELS.contains(Character.toLowerCase(source.charAt(0)));
    }

    @Override
    protected List<Character> transform(List<Character> characters) {
        var result = new ArrayList<>(characters);
        result.addAll(SUFFIX);
        return result;
    }

}
