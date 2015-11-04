/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recuproyecto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/**
 *
 * @author b21684
 */
public class busquedaVectorial {
    
    //  sera un array que tenga la lista de tokens por cada documento
    public ArrayList<String[]> termsDocsArray = new ArrayList<String[]>();
    // simple array de tokens de todos los documentos
    public ArrayList<String> allTerms = new ArrayList<String>(); 
    // utilizado ara lo del cosine 
    private ArrayList<double[]> tfidfDocsVector = new ArrayList<double[]>();
    



    // Assign a score to each query-document pair, say in [0, 1].
    // This score measures how well document and query “match”
    // If the query term does not occur in the document: score
    // should be 0.
    
    /*
        Take 1: Jaccard coefficient
        A commonly used measure of overlap of two sets
        Let A and B be two sets
        Jaccard coefficient:
        jaccard(A, B) = |A ∩ B|
        |A ∪ B|
        (A 6= ∅ or B 6= ∅)
        jaccard(A, A) = 1
        jaccard(A, B) = 0 if A ∩ B = 0
        A and B don’t have to be the same size.
        Always assigns a number between 0 and 1
        Ejemplo:
            Query: “ides of March”
            Document “Caesar died in March”
            jaccard(q, d) = 1/6
    Problema:  doesn’t consider term frequency
    */
    public void jaccard() {
        // puede ser un experimento
    }
    
    
    
    /*
        The term frequency tft,d of term t in document d is defined
        as the number of times that t occurs in d.
        Metodo:
        busca la frecuencia de termino  "term"
        en la lista de tokens del documento "doc"
        TF(t,d) = Term Frequency(t,d): is the number of times that term t occurs in document d.
     */
     public double tf(String[] totalterms, String termino) {
        double count = 0;  //to count the overall occurrence of the term termToCheck
        for (String s : totalterms) {
            if (s.equalsIgnoreCase(termino)) {
                count++;
            }
        }
        return count / totalterms.length;
    }
    
    /*
        busca el idf del termino term en todos los terminos de todos los documentos allTerms
        IDF(t,D) = Inverse Term Frequency(t,D): measures the importance of term t in all documents (D)
    */
    public double idf(ArrayList<String[]> allTerms, String term) {
        double count = 0;
        for(int c= 0; c < allTerms.size();c++){
                String[] docTerms = allTerms.get(c);
                for(int h= 0; h < docTerms.length ;h++){
                    if (docTerms[h].equalsIgnoreCase(term)) {
                        count++;
                        break;
                    }
                }
        }
        return 1 + Math.log(allTerms.size() / count);
    }
    
    /*
        busca el tfidf
        the weight is obtained by multiplying the two measures:
        TF-IDF(t,d) = TF(t,d) * IDF(t,D) = Term Frequency(t,d) * Inverse Term Frequency(t,D)
        
        esto seria solo para 1 termino en un documento en especifico comparado con todos los demás terminos de todos los docs. 
    */
    
    public double tfIdf(String[] doc, ArrayList<String[]> docs, String term) {
        return tf(doc, term) * idf(docs, term);
    }
    
    
   // este metodo se llama desde el htmlParse para q me de todos los tokens de un doc en especifico. 
    public void rating(String [] tokens) {
        for (String term : tokens) {
            if (!allTerms.contains(term)) {  //avoid duplicate entry
                allTerms.add(term);
            }
        }
        termsDocsArray.add(tokens);
    }

    
     /**
     * Method to create termVector according to its tfidf score.
     * este metodo me ayuda a tener el vector para poder hacer lo del coseno 
     * donde guardo por cada documento su peso tfidf 
     */
    public void tfIdfCalculator() {
        double tf; //term frequency
        double idf; //inverse document frequency
        double tfidf; //term requency inverse document frequency        
        
            for(int c= 0; c < termsDocsArray.size();c++){
                String [] terminosDeUnDoc = termsDocsArray.get(c);
            
                double[] tfidfvectors = new double[allTerms.size()];
                int count = 0;
                for(int h= 0; h < allTerms.size();h++){
                    tf = tf(terminosDeUnDoc, allTerms.get(h));
                    idf = idf(termsDocsArray, allTerms.get(h));
                    tfidf = tf * idf;
                    tfidfvectors[count] = tfidf;
                    count++;
                }
                tfidfDocsVector.add(tfidfvectors);  //storing document vectors;            
        }
    }
    /**
     * metodo para calcular la similitud de coseno entre todos los documentos 
     */
    public void getCosineSimilarity() {
        for (int i = 0; i < tfidfDocsVector.size(); i++) {
            for (int j = 0; j < tfidfDocsVector.size(); j++) {
                System.out.println("Entre " + i + " y " + j + "  =  " + cosineSimilarity(tfidfDocsVector.get(i), tfidfDocsVector.get(j)));
            }
        }
    }
    
     /**
     * Metodo para calcular la similitud de cosenos entre 2 documentos nada mas 
     */
    public double cosineSimilarity(double[] docVector1, double[] docVector2) {
        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;
        double cosineSimilarity = 0.0;

        for (int i = 0; i < docVector1.length; i++) //docVector1 and docVector2 must be of same length
        {
            dotProduct += docVector1[i] * docVector2[i];  //a.b
            magnitude1 += Math.pow(docVector1[i], 2);  //(a^2)
            magnitude2 += Math.pow(docVector2[i], 2); //(b^2)
        }

        magnitude1 = Math.sqrt(magnitude1);//sqrt(a^2)
        magnitude2 = Math.sqrt(magnitude2);//sqrt(b^2)

        if (magnitude1 != 0.0 | magnitude2 != 0.0) {
            cosineSimilarity = dotProduct / (magnitude1 * magnitude2);
        } else {
            return 0.0;
        }
        return cosineSimilarity;
    }
    
    
}
