package my.piglatin.translator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConsonantTranslator extends AbstractTranslator {

    private final List<Character> SUFFIX = Arrays.asList('a', 'y');

    private static final List<Character> CONSONANTS =
            Arrays.asList('b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z');

    @Override
    public boolean isApplicable(String source) {
        return !source.isEmpty() && CONSONANTS.contains(Character.toLowerCase(source.charAt(0)));
    }

    @Override
    protected List<Character> transform(List<Character> toProcess) {
        var result = new ArrayList<Character>(toProcess.size() + SUFFIX.size());
        for (int i = 1; i < toProcess.size(); i++) {
            result.add(i - 1, transform(toProcess.get(i - 1), toProcess.get(i)));
        }
        result.add(toProcess.size() - 1, transform(toProcess.get(toProcess.size() - 1), toProcess.get(0)));
        result.addAll(SUFFIX);
        return result;
    }

    private Character transform(Character previous, Character current) {
        return Character.isUpperCase(previous) ? Character.toUpperCase(current) : Character.toLowerCase(current);
    }

}
