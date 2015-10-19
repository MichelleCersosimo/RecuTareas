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
    public static void bsbi(File[] files, HtmlParse parser )  throws IOException {
        int docID = 1;
        int iDViejo=-1;
        String nombre = "";
        boolean existe = false;
        List<String> listaDoc = new ArrayList<String>();
        HashMap<String, List<Integer>> indice = new HashMap<String, List<Integer>>();

        
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
                    docID= Integer.parseInt(token[1])+1;
                    iDViejo = docID-1;
                }
                
                bufferedReader.close();         
            }
            catch(FileNotFoundException ex) {
                System.out.println(
                    "No se pudo abrir Documentos.txt");                
            }
        }
        int cantidadArchivos = files.length;
        System.out.println("Cantidad de archivos "+cantidadArchivos);
        int documentosPorBloque =0;
        if(cantidadArchivos<5){
            if(cantidadArchivos == 1){
                documentosPorBloque = 1;
            }
            else{
                documentosPorBloque = 2;
            }
            
        }
        else{
            //Estimado de cantidad de archivos por bloque, se usa doble Math.round para obtener un valor "int"
            documentosPorBloque = Math.round(Math.round(cantidadArchivos /(Math.log(cantidadArchivos))));
        }
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
            //LLeno el array de block con los archivos que tendrá cada bloque
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
                    bsbi(file.listFiles(), parser); //Si es directorio se llama a si mismo
                } 
                else {
                    FileWriter fileWritter = new FileWriter(postings.getName(),true);
                    BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
                    nombre = file.getName();
                    //Revisa si ya existe el documento en la lista de postings, sino, le asigna un ID,y obtiene el diccionario
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
                //Crea un arbol apartir del hash de los indices del bloque, para que queden ordenados
                Map<String, List<Integer>> treeMap = new TreeMap<String, List<Integer>>(indice);
                FileWriter fileWritter = new FileWriter(bloques.getName(),true);
                BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
                Set s = treeMap.entrySet();
                Iterator it = s.iterator();
                //Itera sobre el arbol creado, para luego guardar el indice del bloque en disco
                while ( it.hasNext() ) {
                   String listaPostings = "";
                   Map.Entry entry = (Map.Entry) it.next();
                   String termino = (String) entry.getKey();
                   List<Integer> lista  = (List<Integer>) entry.getValue();
                   //Itera sobre la lista de postings del termino, para guardarlo luego en disco
                   for(int c= 0;c<lista.size();c++){
                       listaPostings+= lista.get(c)+",";
                   }
                   bufferWritter.write(termino+"="+listaPostings+"\r\n");
                   //System.out.println(termino+"="+listaPostings);
                }//while
                bufferWritter.write("------\r\n");
                bufferWritter.close();
            }
        }
        try{
            merge(bloques,iDViejo);
        }catch(IOException e){ e.printStackTrace();};
        
       
    }
    public static void merge(File bloque,int pos) throws IOException{
        HashMap<String, List<Integer>> indice = new HashMap<String, List<Integer>>();
        int contador=0;
        String separador = "------";
        File indices = new File("Indices.txt");
        // Si no existe crea el archivo
        if (!indices.exists()) {
                indices.createNewFile();
                pos = -1;
        }
        try {
            String linea = null;
            FileReader fileReader = new FileReader(bloque);
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);
            //Se itera sobre el archivo bloque para ir haciendo el merge de los indices
            //Se va guardando en una tabla Hash
            while((linea = bufferedReader.readLine()) != null) {
                if(!linea.equals(separador) && contador>= pos){
                    List<Integer> postingsList = new ArrayList<Integer>();//se crea una lista de posting por cada termino
                    String [] token = linea.split("=");
                    String [] postings = token[1].split(",");
                    //inserto la lista de postings en la lista postingsList
                    for(int i=0;i<postings.length;i++){
                        postingsList.add(Integer.parseInt(postings[i]));
                    }
                    //Si nuestro hashTable está vacio, solo inserta el termino y su lista de postings dentro
                    if(indice.isEmpty()){
                        indice.put(token[0],postingsList);
                    }
                    else{//Si ya tiene elementos, revisa si ya existe o no
                        //Si no existe, inserta el termino con su lista de postings
                        if(!indice.containsKey(token[0])){
                            indice.put(token[0],postingsList);

                        }
                        else{//Si si existe, solo añade los nuevos postings al termino
                            List<Integer>tmp = new ArrayList<Integer>(indice.get(token[0]));
                            tmp.addAll(postingsList);//concateno las listas
                            indice.put(token[0], tmp);
                        }

                    }
                }
                else{
                    contador++;
                }
                
            }
            bufferedReader.close();
            Map<String, List<Integer>> treeMap = new TreeMap<String, List<Integer>>(indice);
            FileWriter fileWritter = new FileWriter(indices.getName(),true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            Set s = treeMap.entrySet();
            Iterator it = s.iterator();
            //Itera sobre el arbol creado, para luego guardar el indice
            while ( it.hasNext() ) {
               String listaPostings = "";
               Map.Entry entry = (Map.Entry) it.next();
               String termino = (String) entry.getKey();
               List<Integer> lista  = (List<Integer>) entry.getValue();
               //Itera sobre la lista de postings del termino, para guardarlo luego en disco
               for(int c= 0;c<lista.size();c++){
                   listaPostings+= lista.get(c)+",";
               }
               bufferWritter.write(termino+"="+listaPostings+"\r\n");
               
            }//while
            bufferWritter.close();
                     
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "No se pudo abrir Bloques.txt");                
        }

        
    
    }
    
    public static void main(String[] args) {
        // https://github.com/Snorremd/Block-Sort-Based-Indexer/blob/master/BlockSortBasedIndexer/src/no/uib/bsbi/BSBI.java
        // TODO code application logic here
        //Hay q cambiar esta ruta siempre porq yo uso otra XD
        File[] files = new File("C:/Users/Vitaly/Documents/RecuTareas/Codigo/Docs").listFiles();
        HtmlParse parser = new HtmlParse();
        try{
            bsbi(files, parser); 
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
