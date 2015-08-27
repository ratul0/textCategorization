/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package textcategorization;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author yousufkhan
 */
public class TrainingModel {
    
    public ArrayList<String[]> trainingDocuments;
    private final String filePath;

    public TrainingModel(String path) {
        
        filePath = path;
        trainingDocuments = new ArrayList<>();
        
//        File[] inputFiles = new File(path).listFiles();
//        System.out.println("All text files in directory");
//        for(File f : inputFiles){
//            if(f.getName().endsWith(".txt")){
//                System.out.println(f.getName());
//            }
//        }
    }
    
    public void train(){
        
        File[] inputFiles = new File(filePath).listFiles();
        SentenceDetectorModel sModel;
        WordDetectorModel wModel;
        
        for(File f : inputFiles){
            if(f.getName().endsWith(".txt")){
                System.out.println("Filename : " + f.getName());
                sModel = new SentenceDetectorModel(f.getPath());
//                System.out.println("Sentence : "+ sModel.getTotalSentenceCount());
                wModel = new WordDetectorModel(sModel.getAllSentences());
//                System.out.println("Unique Word : "+ wModel.getTotalUniqueWordCount());
                trainingDocuments.add(wModel.getAllUniqueWordsinStringArray());
            }
        }
        System.out.println("Done");
    }
    
    
    
}
