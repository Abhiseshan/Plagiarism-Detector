package com.abhiseshan;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Main {
    private static void print(String string) {
        System.out.println(string);
    }

    private static void printHelp() {
        print("usage: synonym_file original_file compare_file [n]");
        print("\toptions:");
        print("\t\toriginal_file --required File name of original file to be compared with");
        print("\t\tcompare_file  --required File name of file to be compared with");
        print("\t\tn             --optional Tuple size");
    }

    public static void main(String[] args) {
        if (args.length < 3 || args.length > 4) {
            printHelp();
            return;
        }

        final String synonymFile = args[0];
        final String orgFile = args[1];
        final String cmpFile = args[2];
        final int n = (args.length == 4 && args[3].matches("\\d+"))? Integer.parseInt(args[3]): 3;

        HashMap<String, HashSet<String>> synonymMap = FileService.readAndGenerateSynonyms(synonymFile);
        List<Tuple> orgTuples = FileService.readAndGenerateTuples(orgFile, n);
        List<Tuple> cmpTuples = FileService.readAndGenerateTuples(cmpFile, n);

        final float finalScore = PlagiarismDetectorService.generatePlagiarismScore(synonymMap, orgTuples, cmpTuples);
        print(finalScore + "%");
    }
}
