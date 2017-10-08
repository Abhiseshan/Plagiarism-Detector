package com.abhiseshan;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

class Tuple {
    private final String[] words;

    Tuple(String[] nWords) {
        this.words = nWords;
    }

    public String[] getWords() {
        return words;
    }

    public class TupleMismatchException extends RuntimeException {
        TupleMismatchException(String message) {
            super(message);
        }
    }

    /**
     * Compares two tuples by consulting the synonym map when necessary
     *
     * @throws TupleMismatchException when size of words in current tuple is not the same as the size of words in the comparison tuple
     * @param tuple Tuple to be compared with
     * @param synonymMap HashMap of synonyms to be used
     * @return boolean whether the tuples are identical or not
     */
    boolean compare(final Tuple tuple, final HashMap<String, HashSet<String>> synonymMap) {
        final String[] other = tuple.getWords();

        if (words.length != other.length) {
            throw new TupleMismatchException("Was expecting tuple of size " + words.length + "but got size " + other.length);
        }

        final int n = words.length;
        for (int i=0; i < n; i++) {
            if (Objects.equals(words[i], other[i])) {
                continue;
            } else if (synonymMap.containsKey(words[i]) && synonymMap.get(words[i]).contains(other[i])) {
                continue;
            }

            return false;
        }

        return true;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }

        if(!(that instanceof Tuple)) {
            return false;
        }

        Tuple t = (Tuple) that;
        return Arrays.equals(this.words, t.getWords());
    }
}
