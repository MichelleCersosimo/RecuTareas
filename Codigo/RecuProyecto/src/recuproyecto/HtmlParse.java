/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recuproyecto;

/**
 *
 * @author b21684
 */
import java.io.BufferedReader;
import java.util.LinkedHashSet;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;

import org.xml.sax.SAXException;

public class HtmlParse {

   public void parsear(File file,int docID) throws IOException,SAXException, TikaException {
       
      //detecting the file type
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputstream = new FileInputStream(new File(file.getPath()));
        ParseContext pcontext = new ParseContext();
        HtmlParser htmlparser = new HtmlParser();
        htmlparser.parse(inputstream, handler, metadata,pcontext);
        String contenido = handler.toString();
        contenido = contenido.toLowerCase();                // Haciendo todo el documento a minusculas
        String [] tokens;           // Tokenizando las palabras del documento 
        tokens = contenido.split("[\\p{P} \\t\\n\\r 1234567890<>=`+\n]");
        Arrays.sort(tokens);                // ordenando los tokens del documento 
        List<String> list = Arrays.asList(tokens);
        busquedaVectorial bv = new busquedaVectorial(); 
        bv.rating(tokens,docID);
        
        
        
        
        //Elimino los duplicados y mantengo el orden de la lista
		
		/* experimento modulo 2 */
		
		/*   
			ArrayList<Integer> tokensWSW = new ArrayList<Integer>();  // lista de tokens withoud stop words
			String line = "a the an and are as at be by for from has hein its of on that to was were will with";
            String [] stopwords = line.split(" ");
            Map<String, List<Integer>> treeMap = new TreeMap<String, List<Integer>>(indice);
            Set s = treeMap.entrySet();
            Iterator it = s.iterator();
            for (int i = 0; i < tokens.length; i++){
                for (int j = 0; j < stopwords.length; j++) {
                    if (tokens[i].equals(stopwords[j])) {
						// si va dentro del nuevo indice pues no es igual a un stop word. 
						tokensWSW.add(tokens[i]);
                    }
                }
            }
			
			
			// luego se usa tokensWSW en lugar de listaFinal. 
		*/
	/**	
		
        List<String> listaFinal =  new ArrayList<String>(new LinkedHashSet<String>(list));
        for(String lista : listaFinal) {
            List<Integer> postingsList = new ArrayList<Integer>();

            if(indice.isEmpty()){
                postingsList.add(docID);
                indice.put(lista,postingsList);
            }
            else{
                if(!indice.containsKey(lista)){
                    postingsList.add(docID);
                    indice.put(lista,postingsList);
                
                }
                else{
                    postingsList = indice.get(lista);
                    postingsList.add(docID);
                    indice.put(lista, postingsList);
                }
            
            }
        }
 
    */  
   }
   
  
   
   
}
