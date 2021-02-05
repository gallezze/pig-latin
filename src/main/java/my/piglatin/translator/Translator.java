package my.piglatin.translator;

public interface Translator {
    String transform(String source);
    boolean isApplicable(String source);
}
