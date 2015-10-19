/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recuproyecto;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
/**
 *
 * @author Pc
 */
public class RecuProyecto {

    /**
     * @param args the command line arguments
     */
    
    public static List<String> listaDocumentos;
    public static void showFiles(File[] files, HtmlParse parser )  throws IOException {
        int docID = 1;
        String nombre = "";
        boolean existe = false;
        List<String> listaDoc = new ArrayList<String>();
        HashMap<String, List<Integer>> indice = new HashMap<String, List<Integer>>();

        File indices = new File("Indices.txt");
        // Si no existe crea el archivo
        if (!indices.exists()) {
                indices.createNewFile();
        }
        File bloques = new File("Bloques.txt");
        // Si no existe crea el archivo
        if (!bloques.exists()) {
                bloques.createNewFile();
        }
        
        File postings = new File("Documentos.txt");
        // Si no existe crea el archivo
        if (!postings.exists()) {
                postings.createNewFile();
        }
        else{//Si el archivo existe,primero obtengo sus datos para no duplicar archivos leidos
            String linea = null;
            try {

                FileReader fileReader = 
                    new FileReader("Documentos.txt");


                BufferedReader bufferedReader = 
                    new BufferedReader(fileReader);

                while((linea = bufferedReader.readLine()) != null) {
                    String [] token = linea.split("\\|");
                    listaDoc.add(token[0]);
                    System.out.println(token[0]);
                    docID= Integer.parseInt(token[1]);
                }   

                bufferedReader.close();         
            }
            catch(FileNotFoundException ex) {
                System.out.println(
                    "No se pudo abrir Documentos.txt");                
            }
        }
        int cantidadArchivos = files.length;
       
        //int documentosPorBloque = Math.round(Math.round(cantidadArchivos /(Math.log(cantidadArchivos))))-1;
        int documentosPorBloque = 2; 
        System.out.println("documentos por bloque "+documentosPorBloque);
        ArrayList<File[]> blocksList = new ArrayList<File[]>();
        //Separo los archivos en bloques
        for(int i = 0; i < cantidadArchivos; i += documentosPorBloque) {
            int size = 0;
            if(i+documentosPorBloque<= cantidadArchivos){
                size = documentosPorBloque;
            }else{
                size = cantidadArchivos-i;
            }
            
            File[] block = new File[size];
            System.out.println("Tamaño del bloque "+size);
            for(int j = 0; j < size; j++) {
                    block[j] = files[i+j];
            }
            blocksList.add(block);
        }
        //Leo cada uno de los archivos que estan en cada bloque
        for(int f = 0; f < blocksList.size(); f++) {
            File[] block = blocksList.get(f);
            //int noDocs = block.length;
            indice.clear();
            for (File file : block) {
                existe = false;
                if (file.isDirectory()) {
                    System.out.println("Directory: " + file.getName());
                    showFiles(file.listFiles(), parser); // Calls same method again.
                } 
                else {
                    FileWriter fileWritter = new FileWriter(postings.getName(),true);
                    BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
                    nombre = file.getName();
                    if(listaDoc.size()>0){

                        for(int k =0;k <listaDoc.size();k++){
                            if(nombre.equals(listaDoc.get(k))){
                                existe = true;
                                k = listaDoc.size();
                            }
                        }
                        if(!existe){
                            bufferWritter.write(nombre+"|"+docID+"|\r\n");
                            try {
                                
                                indice = parser.parsear(file,docID,indice);
                            } catch (Exception e) {};
                            listaDoc.add(nombre);
                            docID++;
                            System.out.println("File: " + file.getName());
                        }
                    }
                    else{
                           bufferWritter.write(nombre+"|"+docID+"|\r\n"); 
                           try {
                                indice = parser.parsear(file,docID,indice);
                            } catch (Exception e) {};
                            listaDoc.add(nombre);
                            docID++;
                            System.out.println("File: " + file.getName());
                    }
                    bufferWritter.close();
                    
                }
            }
            //Se ordena el bloque con los terminos
            if(!indice.isEmpty()){
                Map<String, List<Integer>> treeMap = new TreeMap<String, List<Integer>>(indice);
                FileWriter fileWritter = new FileWriter(bloques.getName(),true);
                BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
                bufferWritter.write("------\r\n");
                Set s = treeMap.entrySet();
                Iterator it = s.iterator();
                while ( it.hasNext() ) {
                   String listaPostings = "";
                   Map.Entry entry = (Map.Entry) it.next();
                   String termino = (String) entry.getKey();
                   List<Integer> lista  = (List<Integer>) entry.getValue();
                   for(int c= 0;c<lista.size();c++){
                       listaPostings+= lista.get(c)+",";
                   }
                   bufferWritter.write(termino+"="+listaPostings+"\r\n");
                   System.out.println(termino+"="+listaPostings);
                }//while
                bufferWritter.close();
            }
        }
       
    }
    
    
    public static void main(String[] args) {
        // https://github.com/Snorremd/Block-Sort-Based-Indexer/blob/master/BlockSortBasedIndexer/src/no/uib/bsbi/BSBI.java
        // TODO code application logic here
        //Hay q cambiar esta ruta siempre porq yo uso otra XD
        File[] files = new File("C:/Users/Pc/Desktop/Recuperacion/Codigo/Docs").listFiles();
        HtmlParse parser = new HtmlParse();
        try{
            showFiles(files, parser); 
        }catch(IOException e){
            e.printStackTrace();
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Display().setVisible(true);
            }
        });
        
    }
    
}
