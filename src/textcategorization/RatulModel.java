/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package textcategorization;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author yousufkhan
 */
public class RatulModel {

//    public ArrayList<String[]> trainingDocuments;
    ArrayList<Double> tfs, tfs2;
    ArrayList<Double> tf_idf, tf_idf2;
    ArrayList<String> allWord, allword2;
    //String [] allModelName = {"accident","art","crime","education","environment","international","opinion","science_tech","sports"};
    String [] allModelName = {"accident","art"};
    TrainingModel[] modelArray = new TrainingModel[9];
    
    Double[] storeCosineSimilarity = new Double[9];
    
        
    public RatulModel() {
        
        int counter = 0;
        
        for(String modelName : allModelName){
                String temp = "dataset/"+modelName;
                System.out.println(temp);
                modelArray[counter] = new TrainingModel(temp);
                modelArray[counter].train();
                counter++;
        }
        
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("JComboBox Test");
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton button = new JButton("Select File");
        button.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent ae) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
              File selectedFile = fileChooser.getSelectedFile();
              String temp = selectedFile.getAbsolutePath();

               //System.out.println();
              
              int counter = 0;
              for(double similarity : getResult(temp.substring(0, temp.lastIndexOf('.')))){
                  System.out.println(allModelName[counter]+"    " +  similarity);
                  counter++;
             }


            }
          }
        });
        frame.add(button);
        frame.pack();
        frame.setVisible(true);
        
        
    }
    
    public Double[] getResult(String filename){
        int counter = 0 ;
        storeCosineSimilarity = new Double[storeCosineSimilarity.length];
        for(String modelName : allModelName){
            Helper calculateTFIDF = new Helper();
                tf_idf = calculateTFIDF.getTFIDF(modelArray[counter], filename);
                tf_idf2 = calculateTFIDF.getTFIDF(modelArray[counter], modelName);
                storeCosineSimilarity[counter] = calculateTFIDF.getCosineSimilarity(tf_idf, tf_idf2);
                System.out.println(storeCosineSimilarity[counter]);
                counter++;
        }
        return  storeCosineSimilarity;
        
    }
    
    
}

