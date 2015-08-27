/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package textcategorization;

import java.util.ArrayList;

/**
 *
 * @author yousufkhan
 */
public class TfIdf {
    
    /**
     * Calculates the tf of term termToCheck
     * @param totalterms : Array of all the words under processing document
     * @param termToCheck : term of which tf is to be calculated.
     * @return tf(term frequency) of term termToCheck
     */
    
    private int termCount=0;
    
    public double tfCalculator(ArrayList<String> totalterms, String termToCheck) {
        double count = 0;  //to count the overall occurrence of the term termToCheck
        for (String s : totalterms) {
            if (s.equalsIgnoreCase(termToCheck)) {
                count++;
            }
        }
        termCount=(int)count;
        return count / totalterms.size();
    }

    /**
     * Calculates idf of term termToCheck
     * @param allTerms : all the terms of all the documents
     * @param termToCheck
     * @return idf(inverse document frequency) score
     */
    public double idfCalculator(ArrayList<String[]> allTerms, String termToCheck) {
        double count = 0, df;
        for (String[] ss : allTerms) {
            for (String s : ss) {
                if (s.equalsIgnoreCase(termToCheck)) {
                    count++;
                    break;
                }
            }
        }
        df = Math.log(allTerms.size() / count);
        if( Double.isInfinite(df)){
            return 1;
        }
        return 1 + Math.log(allTerms.size() / count);
    }

    public int getTermCount() {
        return termCount;
    }
    
    
}