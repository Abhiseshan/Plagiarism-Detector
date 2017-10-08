package com.abhiseshan;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Service that checks the similarity between the list of tuples
 */
class PlagiarismDetectorService {
    /**
     * Function that checks if the number of tuples are the same
     *
     * Calculation of percentage is handled differently based on the number of tuples in each file
     * case 1: both are equal - Compares each tuple with similar one with the other file
     * case 2: original is larger - Compares iteratively till the comparison tuples are exhausted and rest are ignored.
     * case 3: comparison is larger - Compares iteratively till the original tuples are exhausted and assumes not identical for the rest of the tuples.
     *
     * @param synonyms  Synonym list
     * @param orgTuples List of tuples from the original file
     * @param cmpTuples List of tuples from the comparison file
     * @return plagiarism percentage
     */
    static float generatePlagiarismScore(HashMap<String, HashSet<String>> synonyms, List<Tuple> orgTuples, List<Tuple> cmpTuples) {
        if (orgTuples.isEmpty() || cmpTuples.isEmpty()) {
            return 0L;
        }

        int score = 0;
        int size = (orgTuples.size() > cmpTuples.size())? cmpTuples.size() : orgTuples.size();
        for (int i = 0; i < size; i++) {
            score = (cmpTuples.get(i).compare(orgTuples.get(i), synonyms))? score+1 : score;
        }

        return ((float) score / (float) cmpTuples.size()) * 100;
    }
}
