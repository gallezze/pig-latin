package my.piglatin.translator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

abstract class AbstractTranslator implements Translator {

    @Override
    public String transform(String source) {
        if (isApplicable(source)) {
            var holder = separate(source);
            if (!isExempt(holder.letters)) {
                var result = transform(holder.letters);
                for (Entry<Integer, Character> entry : holder.nonLettersPositionMap.entrySet()) {
                    var newIndex = result.size() - (holder.lastLetterIndex - entry.getKey());
                    if (newIndex < result.size()) {
                        result.add(newIndex, entry.getValue());
                    } else {
                        result.add(entry.getValue());
                    }

                }
                return result.stream().map(String::valueOf).collect(Collectors.joining());
            }
        }
        return source;
    }

    protected abstract List<Character> transform(List<Character> toProcess);

    private Holder separate(String source) {
        //here we separate letters and non-letters, also keeping latest letter index
        var chars = source.toCharArray();
        List<Character> letters = new ArrayList<>(chars.length);
        Map<Integer, Character> nonLettersPositionMap = new HashMap<>();
        int lastLetterIndex = 0;
        for (int i = 0; i < chars.length; i++) {
            var current = chars[i];
            if (Character.isLetter(current)) {
                letters.add(current);
                if (i > lastLetterIndex) {
                    lastLetterIndex = i;
                }
            } else {
                nonLettersPositionMap.put(i, current);
            }
        }
        return new Holder(letters, nonLettersPositionMap, lastLetterIndex);
    }

    private boolean isExempt(List<Character> characters) {
        var size = characters.size();
        //words that end in "way" aren't modified
        return size >= 3 &&
                characters.get(size - 3) == 'w' &&
                characters.get(size - 2) == 'a' &&
                characters.get(size - 1) == 'y';
    }

    private class Holder {

        private final List<Character> letters;
        private final Map<Integer, Character> nonLettersPositionMap;
        private final int lastLetterIndex;

        private Holder(List<Character> letters, Map<Integer, Character> nonLettersPositionMap, int lastLetterIndex) {
            this.letters = letters;
            this.nonLettersPositionMap = nonLettersPositionMap;
            this.lastLetterIndex = lastLetterIndex;
        }
    }
}
