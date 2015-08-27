/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package textcategorization;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yousufkhan
 */
public class Helper {
    private ArrayList<Double> tfs;
    private ArrayList<Double> tf_idf;
    private ArrayList<String> allWord;
    
    public ArrayList<Double> getTFIDF(TrainingModel model,String file) {
        SentenceDetectorModel smodel = new SentenceDetectorModel(file+".txt");
//        System.out.println("Total senetence : "+ smodel.getTotalSentenceCount());
//        model.printAllSentencesinFile();
 
        WordDetectorModel wModel = new WordDetectorModel(smodel.getAllSentences());
//        wModel.printAllUniqueWordsinFile();
       
//        System.out.println("Total legitimate Words : " + wModel.getTotalWordCount());
//        System.out.println("Total stopwords : "+wModel.getTotalStopWordsCount());
//        System.out.println("Total Unique Words : " + wModel.getTotalUniqueWordCount());
        
        TfIdf tfIdf = new TfIdf();
        tfs = wModel.getTfArray();
        tf_idf= new ArrayList<>();
        allWord = wModel.getAllUniqueWords();
        int index = 0;
        double idf, tf;
        DecimalFormat fourDecimal = new DecimalFormat("0.0000");
        
        try {
            for(String s : allWord){
                tf=tfs.get(index++);
                idf = tfIdf.idfCalculator(model.trainingDocuments, s);
                tf_idf.add(tf*idf);

            }
            return tf_idf;
        } catch (Exception ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
            return tf_idf;
        }
    }
    
    public Double getCosineSimilarity(ArrayList<Double> tf_idf1,ArrayList<Double> tf_idf2){
        CosineSimilarity cs = new CosineSimilarity();
        System.out.println(tf_idf1.size()+" "+tf_idf2.size());
        Double[] d1= new Double[tf_idf1.size()];
        Double[] d2= new Double[tf_idf2.size()];
        d1= tf_idf1.toArray(d1);
        d2= tf_idf2.toArray(d2);
        
        return cs.cosineSimilarity(d1, d2);
        
    }
    
}
