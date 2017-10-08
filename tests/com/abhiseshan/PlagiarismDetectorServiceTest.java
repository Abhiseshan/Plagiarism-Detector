package com.abhiseshan;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PlagiarismDetectorServiceTest {
    private HashMap<String, HashSet<String>> getSynonyms() {
        final HashSet<String> syn1 = new HashSet<>(Arrays.asList("run", "sprint"));
        final HashSet<String> syn2 = new HashSet<>(Arrays.asList("happy", "joyful"));
        final HashSet<String> syn3 = new HashSet<>(Arrays.asList("good", "nice"));
        final HashMap<String, HashSet<String>> synonyms = new HashMap<>();
        synonyms.put("run", syn1);
        synonyms.put("sprint", syn1);
        synonyms.put("happy", syn2);
        synonyms.put("joyful", syn2);
        synonyms.put("good", syn3);
        synonyms.put("nice", syn3);
        return synonyms;
    }

    private List<Tuple> getSynTup1() {
        final List<Tuple> tuples = new ArrayList<>();
        final Tuple t1 = new Tuple(new String[]{"run", "happy", "good"});
        tuples.add(t1);
        return tuples;
    }

    private List<Tuple> getSynTup2() {
        final List<Tuple> tuples = new ArrayList<>();
        final Tuple t1 = new Tuple(new String[]{"sprint", "joyful", "nice"});
        tuples.add(t1);
        return tuples;
    }

    private List<Tuple> getTup1() {
        final List<Tuple> tuples = new ArrayList<>();
        final Tuple t1 = new Tuple(new String[]{"went", "for", "a"});
        final Tuple t2 = new Tuple(new String[]{"for", "a", "jog"});
        final Tuple t3 = new Tuple(new String[]{"a", "jog", "it"});
        final Tuple t4 = new Tuple(new String[]{"jog", "it", "was"});
        final Tuple t5 = new Tuple(new String[]{"it", "was", "nice"});
        tuples.add(t1);
        tuples.add(t2);
        tuples.add(t3);
        tuples.add(t4);
        tuples.add(t5);
        return tuples;
    }

    private List<Tuple> getTup2() {
        final List<Tuple> tuples = new ArrayList<>();
        final Tuple t1 = new Tuple(new String[]{"went", "for", "a"});
        final Tuple t2 = new Tuple(new String[]{"for", "a", "jog"});
        tuples.add(t1);
        tuples.add(t2);
        return tuples;
    }

    private List<Tuple> getTup3() {
        final List<Tuple> tuples = new ArrayList<>();
        final Tuple t1 = new Tuple(new String[]{"went", "for", "a"});
        final Tuple t2 = new Tuple(new String[]{"for", "a", "jog"});
        final Tuple t3 = new Tuple(new String[]{"a", "jog", "it"});
        final Tuple t4 = new Tuple(new String[]{"jog", "it", "was"});
        final Tuple t5 = new Tuple(new String[]{"it", "was", "good"});
        tuples.add(t1);
        tuples.add(t2);
        tuples.add(t3);
        tuples.add(t4);
        tuples.add(t5);
        return tuples;
    }


    private List<Tuple> getTup4() {
        final List<Tuple> tuples = new ArrayList<>();
        final Tuple t1 = new Tuple(new String[]{"i", "am", "a"});
        final Tuple t2 = new Tuple(new String[]{"am", "a", "programmer"});
        tuples.add(t1);
        tuples.add(t2);
        return tuples;
    }

    @Test
    void generatePlagiarismScore_WhenBothTuplesAreSameSize() {
        final HashMap<String, HashSet<String>> synonyms = getSynonyms();
        final List<Tuple> tup1 = getTup1();
        final List<Tuple> tup2 = getTup3();

        float result = PlagiarismDetectorService.generatePlagiarismScore(synonyms, tup1, tup2);
        assertEquals(100L, result);
    }

    @Test
    void generatePlagiarismScore_WhenOriginalFileIsLarger() {
        final HashMap<String, HashSet<String>> synonyms = getSynonyms();
        final List<Tuple> tup1 = getTup1();
        final List<Tuple> tup2 = getTup2();

        float result = PlagiarismDetectorService.generatePlagiarismScore(synonyms, tup1, tup2);
        assertEquals(100L, result);
    }

    @Test
    void generatePlagiarismScore_WhenBothTuplesAreTotallyDifferent() {
        final HashMap<String, HashSet<String>> synonyms = getSynonyms();
        final List<Tuple> tup1 = getTup2();
        final List<Tuple> tup2 = getTup4();

        float result = PlagiarismDetectorService.generatePlagiarismScore(synonyms, tup1, tup2);
        assertEquals(0L, result);
    }

    @Test
    void generatePlagiarismScore_WhenBothAreTheSame() {
        final HashMap<String, HashSet<String>> synonyms = getSynonyms();
        final List<Tuple> tup1 = getTup2();

        float result = PlagiarismDetectorService.generatePlagiarismScore(synonyms, tup1, tup1);
        assertEquals(100L, result);
    }

    @Test
    void generatePlagiarismScore_WhenBothAreOnlySynonyms() {
        final HashMap<String, HashSet<String>> synonyms = getSynonyms();
        final List<Tuple> tup1 = getSynTup1();
        final List<Tuple> tup2 = getSynTup2();

        float result = PlagiarismDetectorService.generatePlagiarismScore(synonyms, tup1, tup2);
        assertEquals(100L, result);
    }

    @Test
    void generatePlagiarismScore_WhenOneIsEmpty() {
        final HashMap<String, HashSet<String>> synonyms = getSynonyms();
        final List<Tuple> tup = getSynTup1();
        final List<Tuple> empty = new ArrayList<>();

        float result = PlagiarismDetectorService.generatePlagiarismScore(synonyms, tup, empty);
        assertEquals(0L, result);
        float result2 = PlagiarismDetectorService.generatePlagiarismScore(synonyms, empty, tup);
        assertEquals(0L, result2);
    }
}