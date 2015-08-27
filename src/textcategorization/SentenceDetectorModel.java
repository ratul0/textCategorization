/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package textcategorization;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yousufkhan
 */
public class SentenceDetectorModel {

    private String outputFile = "allSentences.txt";

    private File sourceFile;
    ArrayList<String> sentences;

    private int totalSentence = 0;
    private String sentence, tempString;
    private int c;
    private char character;

    public SentenceDetectorModel() {
    }

    public SentenceDetectorModel(String fileName) {
        sourceFile = new File(fileName);
        StringBuilder sb = new StringBuilder();
        sentences = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile))) {

            
            while (true) {
                c = reader.read();
                if (c == -1) {
                    break;
                }
                character = (char) c;
                if (character == '।' || character == '?') {
                    tempString = sb.toString();
                    if(!tempString.isEmpty()){
                        sentences.add(tempString.trim());
                        totalSentence++;
                    }
                    
                    sb = null;
                    sb = new StringBuilder();

//                  eliminate the leading blackspace  
//                    reader.mark(2);
//                    c = reader.read();
//                    if (c == -1) {
//                        break;
//                    }
//                    character = (char) c;
//                    if (character != ' ') {
//                        reader.reset();
//                    }

                } else if (!((character >= 'A' && character <= 'Z') || (character >= 'a' && character <= 'z')
                        || (character >= '0' && character <= '9') || character == '\n' || character == '\r')) {
                    sb.append(character);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(SentenceDetectorModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void printAllSentencesinFile(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))){
            for(String aSentence: sentences ){
                writer.write(aSentence);
                writer.newLine();
                writer.flush();
            }
            writer.close();
            
        }catch(IOException ex){
            Logger.getLogger(SentenceDetectorModel.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }

    public ArrayList<String> getAllSentences() {
        return sentences;
    }

    public int getTotalSentenceCount() {
        return totalSentence;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }
    
    
    
}
