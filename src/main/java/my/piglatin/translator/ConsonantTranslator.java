package my.piglatin.translator;

import java.util.ArrayList;
import java.util.List;

public class ConsonantTranslator extends AbstractTranslator {

    private final List<Character> SUFFIX = List.of('a', 'y');

    private static final List<Character> CONSONANTS =
            List.of('b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z');

    @Override
    public boolean isApplicable(String source) {
        return !source.isEmpty() && CONSONANTS.contains(Character.toLowerCase(source.charAt(0)));
    }

    @Override
    protected List<Character> transform(List<Character> characters) {
        var result = new ArrayList<Character>(characters.size() + SUFFIX.size());
        for (int i = 1; i < characters.size(); i++) {
            result.add(i - 1, transform(characters.get(i - 1), characters.get(i)));
        }
        result.add(characters.size() - 1, transform(characters.get(characters.size() - 1), characters.get(0)));
        result.addAll(SUFFIX);
        return result;
    }

    private Character transform(Character previous, Character current) {
        return Character.isUpperCase(previous) ? Character.toUpperCase(current) : Character.toLowerCase(current);
    }

}
