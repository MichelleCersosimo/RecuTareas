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
import java.util.LinkedHashSet;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
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

   public HashMap<String,List<Integer>> parsear(File file,int docID,HashMap<String,List<Integer>> indice) throws IOException,SAXException, TikaException {
       
      //detecting the file type
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputstream = new FileInputStream(new File(file.getPath()));
        ParseContext pcontext = new ParseContext();

        //Html parser 
        HtmlParser htmlparser = new HtmlParser();
        htmlparser.parse(inputstream, handler, metadata,pcontext);
        String contenido = handler.toString();
        // Haciendo todo el documento a minusculas
        contenido = contenido.toLowerCase();
        
        // Tokenizando las palabras del documento 
        String [] tokens;
        tokens = contenido.split("[\\p{P} \\t\\n\\r 1234567890<>=]");
        // Para visualizar los tokens que hizo 
        /*for (String s: tokens){
            if (s.equals("")) continue;
             System.out.println(s);
        }*/
        
        // ordenando los tokens del documento 
        Arrays.sort(tokens);
        /*for (String token : tokens) {
            System.out.println(token);
        }*/
        //Elimino los duplicados y mantengo el orden de la lista
        List<String> list = Arrays.asList(tokens);
        List<String> listaFinal =  new ArrayList<String>(new LinkedHashSet<String>(list));
        for(String lista : listaFinal) {
            List<Integer> postingsList = new ArrayList<Integer>();
            //System.out.println(lista);
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
 
      
    
      // Muestra el contenido 
      //String estilizado = contenido;
      //System.out.println("Contents of the document:" + estilizado);
      
    
        // Esta metadata nos puede servir por si queremos la descripcion del link :P Dejemoslo para despues
      /*System.out.println("Metadata of the document:");
      String[] metadataNames = metadata.names();
      
      for(String name : metadataNames) {
         System.out.println(name + ":   " + metadata.get(name));  
      }*/
      return indice;
   }
}
