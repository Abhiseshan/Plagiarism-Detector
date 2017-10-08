package com.abhiseshan;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {
    @Test
    void readAndGenerateSynonyms_shouldGenerateSynonyms() {
        final String synonym_file = "files/syn1.txt";
        final HashSet<String> syn1 = new HashSet<>(Arrays.asList("run", "sprint", "jog"));
        final HashMap<String, HashSet<String>> expectedResult = new HashMap<>();
        expectedResult.put("run", syn1);
        expectedResult.put("sprint", syn1);
        expectedResult.put("jog", syn1);

        HashMap<String, HashSet<String>> result = FileService.readAndGenerateSynonyms(synonym_file);
        assertEquals(expectedResult, result);
    }

    @Test
    void readAndGenerateSynonyms_shouldGenerateMultiLineSynonyms() {
        final String synonym_file = "files/syn2.txt";
        final HashSet<String> syn1 = new HashSet<>(Arrays.asList("run", "sprint"));
        final HashSet<String> syn2 = new HashSet<>(Arrays.asList("happy", "joyful"));
        final HashSet<String> syn3 = new HashSet<>(Arrays.asList("beautiful", "attractive"));
        final HashMap<String, HashSet<String>> expectedResult = new HashMap<>();
        expectedResult.put("run", syn1);
        expectedResult.put("sprint", syn1);
        expectedResult.put("happy", syn2);
        expectedResult.put("joyful", syn2);
        expectedResult.put("beautiful", syn3);
        expectedResult.put("attractive", syn3);

        HashMap<String, HashSet<String>> result = FileService.readAndGenerateSynonyms(synonym_file);
        assertEquals(expectedResult, result);
    }

    @Test
    void readAndGenerateSynonyms_shouldAppendSynonymsWhenWordAlreadyExists() {
        final String synonym_file = "files/syn3.txt";
        final HashSet<String> syn = new HashSet<>(Arrays.asList("happy", "joyful"));
        final HashSet<String> combined2 = new HashSet<>(Arrays.asList("run", "sprint", "jog", "dash"));
        final HashMap<String, HashSet<String>> expectedResult = new HashMap<>();
        expectedResult.put("run", combined2);
        expectedResult.put("sprint", combined2);
        expectedResult.put("happy", syn);
        expectedResult.put("joyful", syn);
        expectedResult.put("jog", combined2);
        expectedResult.put("dash", combined2);

        HashMap<String, HashSet<String>> result = FileService.readAndGenerateSynonyms(synonym_file);
        assertEquals(expectedResult, result);
    }

    @Test
    void readAndGenerateSynonyms_shouldStripOutAllNonAlphabetCharacters() {
        final String synonym_file = "files/syn4.txt";
        final HashSet<String> syn = new HashSet<>(Arrays.asList("happy", "joyful"));
        final HashMap<String, HashSet<String>> expectedResult = new HashMap<>();
        expectedResult.put("happy", syn);
        expectedResult.put("joyful", syn);

        HashMap<String, HashSet<String>> result = FileService.readAndGenerateSynonyms(synonym_file);
        assertEquals(expectedResult, result);
    }

    @Test
    void readAndGenerateTuples_ShouldGenerateTuples() {
        final String tuple_file = "files/file1.txt";
        final List<Tuple> expected_result = new ArrayList<>();
        final Tuple t1 = new Tuple(new String[]{"go", "for", "a"});
        final Tuple t2 = new Tuple(new String[]{"for", "a", "run"});
        expected_result.add(t1);
        expected_result.add(t2);

        List<Tuple> result = FileService.readAndGenerateTuples(tuple_file, 3);
        assertEquals(expected_result, result);
    }

    @Test
    void readAndGenerateTuples_ShouldGenerateTuplesWithoutPunctuationOrUpperCase() {
        final String tuple_file = "files/file2.txt";
        final List<Tuple> expected_result = new ArrayList<>();
        final Tuple t1 = new Tuple(new String[]{"went", "for", "a"});
        final Tuple t2 = new Tuple(new String[]{"for", "a", "jog"});
        final Tuple t3 = new Tuple(new String[]{"a", "jog", "it"});
        final Tuple t4 = new Tuple(new String[]{"jog", "it", "was"});
        final Tuple t5 = new Tuple(new String[]{"it", "was", "nice"});
        expected_result.add(t1);
        expected_result.add(t2);
        expected_result.add(t3);
        expected_result.add(t4);
        expected_result.add(t5);

        List<Tuple> result = FileService.readAndGenerateTuples(tuple_file, 3);
        assertEquals(expected_result, result);
    }

    @Test
    void readAndGenerateTuples_ShouldGenerateEmptyTuplesWhenNLargerThanWords() {
        final String tuple_file = "files/file1.txt";
        final List<Tuple> expected_result = new ArrayList<>();

        List<Tuple> result = FileService.readAndGenerateTuples(tuple_file, 10);
        assertEquals(expected_result, result);
    }

}