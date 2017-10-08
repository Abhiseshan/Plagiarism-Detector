package com.abhiseshan;

import java.io.*;
import java.util.*;

/**
 * Responsible for reading and serializing file input
 */
class FileService {
    private static final String FILE_NOT_FOUND = "File not found: ";
    private static final String IO_EXCEPTION = "IO Exception: ";

    static HashMap<String, HashSet<String>> readAndGenerateSynonyms(final String fileName) {
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;

        HashMap<String, HashSet<String>> synonymMap = new HashMap<>();

        try {
            fileInputStream = new FileInputStream(fileName);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            String line;
            while ((line  = bufferedReader.readLine()) != null) {
                String[] synonyms = line
                        .replaceAll("[^a-zA-Z ]", "") //Strip out all non alphabet characters (safety, there shouldn't exist any)
                        .toLowerCase()
                        .split("\\s+");
                HashSet<String> synonymSet = new HashSet<>(Arrays.asList(synonyms));

                for (String word : synonyms) {
                    if (synonymMap.containsKey(word)) {
                        // The synonyms of the word already exist in the map so we append the existing synonyms with the
                        //  new ones and update all existing references
                        HashSet<String> existingWords = synonymMap.get(word);
                        existingWords.addAll(synonymSet);
                        synonymSet.addAll(existingWords);
                    } else {
                        synonymMap.put(word, synonymSet);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println(FILE_NOT_FOUND + ex.getMessage());
        } catch (IOException x) {
            System.err.println(IO_EXCEPTION + x.getMessage());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }

                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException x) {
                System.err.println(IO_EXCEPTION + x.getMessage());
            }
        }
        return synonymMap;
    }

    public static class TupleGenerationException {
        private final String TAG = "Tuple Generation Exception: ";
        private final String IN_LINE = "in line: ";

        TupleGenerationException(final String message, final String line, final int n, final int length) {
            System.err.println(TAG + message);
            System.err.println(IN_LINE + line);
            System.err.println("Expected length >= " + n + " but got length " + length);
        }
    }

    static List<Tuple> readAndGenerateTuples(final String fileName, final int n) {
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;

        List<Tuple> tuples = new ArrayList<>();

        try {
            fileInputStream = new FileInputStream(fileName);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] words = line
                        .replaceAll("[^a-zA-Z0-9 ]", "") //Strip out all non alpha numeric characters
                        .toLowerCase()
                        .split("\\s+");

                if (n > words.length) {
                    new TupleGenerationException("N is larger than length of line. The line is ignored.", line, n, words.length);
                }

                for (int i = 0; i < words.length - n + 1; i++) {
                    tuples.add(new Tuple(Arrays.copyOfRange(words, i, i+n)));
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println(FILE_NOT_FOUND + ex.getMessage());
        } catch (IOException x) {
            System.err.println(IO_EXCEPTION + x.getMessage());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }

                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException x) {
                System.err.println(IO_EXCEPTION + x.getMessage());
            }
        }
        return tuples;
    }
}
