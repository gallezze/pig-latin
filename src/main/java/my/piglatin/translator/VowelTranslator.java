package my.piglatin.translator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VowelTranslator extends AbstractTranslator {

    private final List<Character> SUFFIX = Arrays.asList('w', 'a', 'y');

    private static final List<Character> VOWELS = Arrays.asList('a', 'e', 'i', 'o', 'u');

    @Override
    public boolean isApplicable(String source) {
        return !source.isEmpty() && VOWELS.contains(Character.toLowerCase(source.charAt(0)));
    }

    @Override
    protected List<Character> transform(List<Character> toProcess) {
        var result = new ArrayList<>(toProcess);
        result.addAll(SUFFIX);
        return result;
    }

}
