/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package textcategorization;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author yousufkhan
 */
public class WordDetectorModel {

    private String outputFile = "output/uniqueWords_TF.txt";

    private String tokens = " ,.:;\'\"\t\r\f\\\n()১২৩৪৫৬৭৮৯০‘’ঃ﻿[]<>!/“”*";
    private StringTokenizer tk;

    private ArrayList<String> allUniqueWords;
    private ArrayList<Double> tfArray;
    private ArrayList<String> allWords;
    private ArrayList<Integer> perWordFrequency;
    
    private static final String stopWordFile = "stopword.txt";
    private static ArrayList<String> stopWords;
    private static boolean stopWordLoaded=false;
    
    private BengaliStemmerLight stemmer;

    private String word;
    private int totalWords = 0, totalUniqueWords = 0, totalStopWords=0;
    
    private int index;

    public WordDetectorModel(ArrayList<String> allSentences) {
        
        allUniqueWords = new ArrayList<>();
        tfArray = new ArrayList<>();
        allWords = new ArrayList<>();
        perWordFrequency = new ArrayList<>();

        loadStopWords();
        stemmer = new BengaliStemmerLight();
        
        for (String s : allSentences) {

            tk = new StringTokenizer(s, tokens, false);
            while (tk.hasMoreTokens()) {
                word = tk.nextToken();
                
                if(stopWords.contains(word)) {
                    totalStopWords++;
                    continue;
                }
                
                word= stemmer.stem(word);
                allWords.add(word);
                totalWords++;

                boolean newUniqueWord = true;
                
                for (String string : allUniqueWords) {
                    if (word.equals(string)) {
                        index=allUniqueWords.indexOf(word);
                        perWordFrequency.set(index, perWordFrequency.get(index)+1);
                        newUniqueWord = false;
                        break;
                    }
                }
                if (newUniqueWord) {
                    allUniqueWords.add(word);
                    totalUniqueWords++;
                    perWordFrequency.add(1);
                }
            }
        }
        

        for(index = 0; index < totalUniqueWords; index++){
            tfArray.add((double)perWordFrequency.get(index)/totalWords);
        }
        
    }

    public void printAllUniqueWordsinFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

//            double termFrequency=0, maxTermFreq = 0;
//            String maxFreqWord = null;
            
//            double tf;
            DecimalFormat fourDecimal = new DecimalFormat("0.0000");

            int n=0;
            for (String s : allUniqueWords) {
                
//                termFrequency=perWordFrequency.get(n);
//
//                if (termFrequency > maxTermFreq) {
//                    maxTermFreq = termFrequency;
//                    maxFreqWord = s;
//                }
                
//                tf =(double) termFrequency/allWords.size();
                
                writer.write( String.format("\t%-20s\t%8s", s, fourDecimal.format(tfArray.get(n++))) );
                writer.newLine();
                writer.flush();
            }
//            System.out.println(maxFreqWord + " - " + maxTermFreq);
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
        
    }

    public int getTotalWordCount() {
        return totalWords;          //allWords.size() will return the same result
    }

    public int getTotalUniqueWordCount() {
        return totalUniqueWords;
    }

    public int getTotalStopWordsCount() {
        return totalStopWords;
    }

    public ArrayList<String> getAllUniqueWords() {
        return allUniqueWords;
    }
    
    public String[] getAllUniqueWordsinStringArray() {
        String[] a=new String[getTotalUniqueWordCount()];
        return allUniqueWords.toArray(a);
    }

    public ArrayList<Double> getTfArray() {
        return tfArray;
    }

    
    public void setOutputFileName(String outputFile) {
        this.outputFile = outputFile;
    }
    
    private void loadStopWords(){
       
        if (!stopWordLoaded) {
            try (BufferedReader reader = new BufferedReader(new FileReader(stopWordFile))) {
                stopWords = new ArrayList<>();
                while ((word = reader.readLine()) != null) {
                    stopWords.add(word.trim());
                }
            } catch (IOException ex) {
                System.out.println(ex.toString());
            }
            System.out.println("Stopword loaded");
            stopWordLoaded = true;
        }
    }

    
}
