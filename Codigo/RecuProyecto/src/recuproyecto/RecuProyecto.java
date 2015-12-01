/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recuproyecto;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import javax.swing.DefaultListModel;
/**
 *
 * @author Pc
 */
public class RecuProyecto {

    /**
     * @param args the command line arguments
     */
    
    
    public static javax.swing.JTextArea textArea;
    static HashMap<String, Integer> listaDocumentos1 = new HashMap<String, Integer>();
    static HashMap<String, Integer> listaDocumentos2 = new HashMap<String, Integer>();
    static Map<String,Integer > sugerencias = new TreeMap<String,Integer>();
    static int docID;
    static int iDViejo;
    static HashMap<String, List<Integer>> indice = new HashMap<String, List<Integer>>();
    public static void bsbi(File[] files, HtmlParse parser )  throws IOException {
        busquedaVectorial bv = new busquedaVectorial();
        int cantidadArchivos=0;
        
        /*File bloques = new File("Bloques.txt");
        // Si no existe crea el archivo
        if (!bloques.exists()) {
                bloques.createNewFile();
                docID = 1;
                iDViejo=-1;
        }*/
        llenarSugerencias();
        File postings = new File("Documentos.txt");
        // Si no existe crea el archivo
        if (!postings.exists()) {
                docID=1;
                iDViejo=-1;
                postings.createNewFile();
        }
        else{//Si el archivo existe,primero obtengo sus datos para no duplicar archivos leidos
            if(listaDocumentos1.isEmpty()){
                String linea = null;
                try {

                    FileReader fileReader = 
                        new FileReader("Documentos.txt");


                    BufferedReader bufferedReader = 
                        new BufferedReader(fileReader);

                    while((linea = bufferedReader.readLine()) != null) {
                        String [] token = linea.split("\\|");
                        if(Integer.parseInt(token[1])+1>docID){
                            docID= Integer.parseInt(token[1])+1;
                        }                        
                        listaDocumentos1.put(token[0],Integer.parseInt(token[1]));
                        iDViejo = docID-1;
                        
                    }

                    bufferedReader.close();         
                }
                catch(FileNotFoundException ex) {
                    System.out.println(
                        "No se pudo abrir Documentos.txt");                
                }
            }
        }
        List<Integer> borrables = obtenerBorrables(files);
        
        if(iDViejo != docID-1 || listaDocumentos1.isEmpty() || !borrables.isEmpty()|| bv.termsDocsArray.isEmpty()){
            for(File file: files){
                try {
                    parser.parsear(file,listaDocumentos2.get(file.getName()));
                } catch (Exception e) {};
            
            }
            guardarArchivos();
            bv.tfIdfCalculator();
            
            /**
            if(listaDocumentos1.isEmpty()){
                cantidadArchivos = files.length;
                iDViejo = 0;
            }
            else{
                cantidadArchivos = docID-iDViejo-1;
            }
            
            System.out.println("Cantidad de archivos a indexar "+cantidadArchivos);
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
            int idABuscar = iDViejo;
            
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
                    if(listaDocumentos1.isEmpty()){
                        block[j] = files[i+j];
                    }
                    else{
                        idABuscar++;
                        block[j] = obtenerDocumento(files,idABuscar);
                        
                    }
                }
                blocksList.add(block);
            }
            //Leo cada uno de los archivos que estan en cada bloque
            crearBloque(blocksList,parser);
            if(!borrables.isEmpty()){
                borrarArchivos(borrables);
                borrables.clear();
            }
            try{
                merge(bloques);
            }catch(IOException e){ e.printStackTrace();};
            guardarArchivos();
            
            //Ahora listaDocumentos1 es la actual 
            listaDocumentos1.clear();
            listaDocumentos1.putAll(listaDocumentos2);
            
        }
        if(indice.isEmpty()){
            llenarIndice();*/
        }
        
    }
    
    public static Map ordenarSugerencias(HashMap unsortMap) {	 
         List list = new LinkedList(unsortMap.entrySet());

         Collections.sort(list,Collections.reverseOrder(new Comparator() {
                 public int compare(Object o1, Object o2) {
                         return ((Comparable) ((Map.Entry) (o1)).getValue())
                                                 .compareTo(((Map.Entry) (o2)).getValue());
                 }
         }));

         Map sortedMap = new LinkedHashMap();
         for (Iterator it = list.iterator(); it.hasNext();) {
                 Map.Entry entry = (Map.Entry) it.next();
                 sortedMap.put(entry.getKey(), entry.getValue());
         }
         return sortedMap;
    }
    //Llena el archivo de sugerencias
    public static void llenarSugerencias() throws IOException{
        File sugerenciasArchivo = new File("sugerencias.txt");
        // Si no existe crea el archivo
        if (!sugerenciasArchivo.exists()) {
                 sugerenciasArchivo.createNewFile();
        }
        else{//Si está vacio, entonces llega sugerencias
            if(sugerencias.isEmpty()){
                String linea = null;
                HashMap<String,Integer> sugerenciasTmp = new HashMap<String,Integer>();
                try {

                    FileReader fileReader = 
                        new FileReader("sugerencias.txt");


                    BufferedReader bufferedReader = 
                        new BufferedReader(fileReader);

                    while((linea = bufferedReader.readLine()) != null) {
                        String [] token = linea.split(",");
                        sugerenciasTmp.put(token[0],Integer.parseInt(token[1]));
                                                
                    }
                    Map <String, Integer> sugerenciasOrdenadas = ordenarSugerencias(sugerenciasTmp);
                    Map<String,Integer> tmp = new TreeMap<String,Integer>(sugerenciasOrdenadas);
                    sugerencias = tmp;
                    bufferedReader.close();         
                }
                catch(FileNotFoundException ex) {
                    System.out.println(
                        "No se pudo abrir sugerencias.txt");                
                }
            }
        }
    }
    
    
    public static void llenarIndice() throws IOException{
            System.out.println("LLenando indice...");
            String linea = null;
            FileReader fileReader = new FileReader("Indices.txt");
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);
            //Se itera sobre el archivo bloque para ir haciendo el merge de los indices
            //Se va guardando en una tabla Hash
            while((linea = bufferedReader.readLine()) != null) {
               List<Integer> postingsList = new ArrayList<Integer>();//se crea una lista de posting por cada termino
                String [] token = linea.split("=");
                String [] postings = token[1].split(",");
                //inserto la lista de postings en la lista postingsList
                for(int i=0;i<postings.length;i++){
                    postingsList.add(Integer.parseInt(postings[i]));
                }
                indice.put(token[0],postingsList);

            }
            bufferedReader.close();


        

    }
    public static void borrarArchivos(List<Integer> borrables) throws IOException{
        if(indice.isEmpty()){
            llenarIndice();
        }
        List<Integer> tmp = new ArrayList<Integer>();
        for(int i = 0; i < borrables.size();i++){
            for(Iterator<Map.Entry<String, List<Integer>>> it = indice.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, List<Integer>> entry = it.next();
                tmp = entry.getValue();
                if (tmp.contains(borrables.get(i))){
                    tmp.remove(borrables.get(i));
                    if(!tmp.isEmpty()){
                        indice.put(entry.getKey(), tmp);
                    }
                    else{
                        it.remove();
                    }
                }
            }
            
        }
    }
    
    public static void guardarArchivos() throws IOException{
        FileWriter fileWritter = new FileWriter("Documentos.txt",false);
        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
        for (final String key : listaDocumentos2.keySet()) {
            bufferWritter.write(key+"|"+listaDocumentos2.get(key)+"|\r\n");
        }
        bufferWritter.close();
    }
    public static void crearBloque(ArrayList<File[]> blocksList, HtmlParse parser) throws IOException{
        
        HashMap<String, List<Integer>> indiceTmp = new HashMap<String, List<Integer>>();
        FileWriter fileWritter = new FileWriter("Bloques.txt",true);
        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
        for(int f = 0; f < blocksList.size(); f++) {
            File[] block = blocksList.get(f);
            //int noDocs = block.length;
            indiceTmp.clear();
            for (File file : block) {
                iDViejo++;
                try {
                    //indiceTmp = parser.parsear(file,iDViejo,indiceTmp);
                } catch (Exception e) {};
                
            }
            //Se ordena el bloque con los terminos
            if(!indiceTmp.isEmpty()){
                //Crea un arbol apartir del hash de los indices del bloque, para que queden ordenados
                Map<String, List<Integer>> treeMap = new TreeMap<String, List<Integer>>(indiceTmp);
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
                
            }
        }
        bufferWritter.write("*\r\n");
        bufferWritter.close();
    
    }
    public static List<Integer> obtenerBorrables(File [] files){
        List<Integer> borrables = new ArrayList<>();
        listaDocumentos2.clear();
        for(File file:files){
            if(listaDocumentos1.containsKey(file.getName())){
                listaDocumentos2.put(file.getName(),listaDocumentos1.get(file.getName()));
                
            }
            else{
                listaDocumentos2.put(file.getName(),docID);
                docID++;
            }
        }
        System.out.println("Archivo a revisar "+listaDocumentos1.size());
        for (final String key : listaDocumentos1.keySet()) {
            
            if (!listaDocumentos2.containsKey(key)) {
                borrables.add(listaDocumentos1.get(key));
            }
        }
        return borrables;    
    }
    
    public static File obtenerDocumento(File [] files,int id){
        File archivo=null;
        String nombre = "";
        for (final String key : listaDocumentos2.keySet()) {
            if (listaDocumentos2.get(key)==id) {
                nombre = key;
                
                break;
            }
        }
        for(File file:files){
               if(file.getName().equals(nombre)){
                   System.out.println(file.getName());
                   archivo = file;
                   break;
               }     
        }
        return archivo;
    }
        
    public static void merge(File bloque) throws IOException{
        //HashMap<String, List<Integer>> indice = new HashMap<String, List<Integer>>();
        int contador=0;
        int pos = obtenerUltimoAsterisco();
        System.out.println("pos asterisco ="+pos);
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
                if(!linea.equals(separador) && !linea.equals("*") && contador>= pos){
                    
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
            //guardarIndice();
                     
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "No se pudo abrir Bloques.txt");                
        }

        
    
    }
    public static void guardarIndice() throws IOException{
        Map<String, List<Integer>> treeMap = new TreeMap<String, List<Integer>>(indice);
        FileWriter fileWritter = new FileWriter("Indices.txt");
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
    public static int obtenerUltimoAsterisco() throws IOException{
        int pos = 0;
        int posTmp= 0;
        int contador = 0;
        String linea = null;
        FileReader fileReader = new FileReader("Bloques.txt");
        BufferedReader bufferedReader = 
            new BufferedReader(fileReader);
        //Se itera sobre el archivo bloque para ir haciendo el merge de los indices
        //Se va guardando en una tabla Hash
        while((linea = bufferedReader.readLine()) != null) {
            contador++;
            if(linea.equals("*")){
                pos = posTmp;
                posTmp = contador;
                
            }
        }
        bufferedReader.close();
        return pos;
    }
    
    public void setTextField(javax.swing.JTextArea jTextArea1){
        this.textArea = jTextArea1;
    }
    
    
    /*
        Metodo que se encarga de procesar una consulta cuando el radio boton esta 
        marcado. Revisa el indice y devuelve los documentos esperados por el usuario 
        en donde salgan TODOS los terminos de la consulta
    */
    public String procesarAnd (String consulta)throws IOException {
        String result = "";
        consulta = consulta.toLowerCase();
        String [] palabras = consulta.split(" ");
        if (palabras[0].equals("")) {
                result = "No query terms found :( ";
        } else {
            Map<String, List<Integer>> treeMapand = new TreeMap<String, List<Integer>>(indice);
            Set sand = treeMapand.entrySet();
            Iterator itand = sand.iterator();
            
            if (palabras.length > 1 ) {
                result += "\t\t\tResultados de consulta AND\n";
                for (int i = 0; i < palabras.length; i++){
                    String p1 = palabras[i];
                    itand = sand.iterator();
                    ArrayList<Integer> p1Postings = new ArrayList<Integer>();
                    ArrayList<Integer> p2Postings = new ArrayList<Integer>();
                    p1Postings = getPostings(p1, itand, p1Postings);
                    if (palabras[i++] != null) {
                        String p2 = palabras[i++];
                        itand = sand.iterator();
                        p2Postings = getPostings(p2, itand, p2Postings);
                    } else {
                        String p2 = palabras[i--];
                        itand = sand.iterator();
                        p2Postings = getPostings(p2, itand, p2Postings);
                    }
                    ArrayList<Integer> intersectResult = new ArrayList<Integer>();
                    intersectResult = intersect(p1Postings, p2Postings, intersectResult);
                    result = imprimirIntersect(result, intersectResult, consulta);
                    result += "\n";
                    i++;
                }
            } else {
                result += "For an And Query you need more than just one term, do OR query if so.\n";
            }
        }
        
        return result; 
        
    }
    
    public String procesarOrSinSW(String consulta)throws IOException {
        
        String result = "";
        consulta = consulta.toLowerCase();
        String [] palabras = consulta.split(" ");
        if (palabras[0].equals("")) {
                result = "";
        } else {
            String line = "a the an and are as at be by for from has hein in its of on that to was were will with";
            String [] stopwords = line.split(" ");
            for (int i = 0; i < palabras.length; i++){
                boolean bandera = true;  
                for (int j = 0; j < stopwords.length; j++) {
                    if (palabras[i].equals(stopwords[j])) {
                        bandera = false; 
                    }
                }
                if(bandera){
                    result+=palabras[i]+" ";
                }
            }
            /*System.out.println("longitud stop vector: "+stopwords.length);
            System.out.println(stopwords[0]);
            Map<String, List<Integer>> treeMap = new TreeMap<String, List<Integer>>(indice);
            Set s = treeMap.entrySet();
            Iterator it = s.iterator();
            result += "\t\t\tResultados de consulta OR:\n";
            for (int i = 0; i < palabras.length; i++){
                boolean bandera = true;  
                for (int j = 0; j < stopwords.length; j++) {
                    if (palabras[i].equals(stopwords[j])) {
                        bandera = false; 
                    }
                }
                if (bandera) {
                    ArrayList<Integer> actualSearchDocs = new ArrayList<Integer>();
                    String searchWord = palabras[i];
                    it = s.iterator();
                    int flagPerWord = 0;
                    actualSearchDocs = getPostings(searchWord, it, actualSearchDocs);
                    result = imprimirRequest(result, actualSearchDocs, flagPerWord, searchWord);
                    result += "\n";
                }
            }
            if (result.equals("\t\t\tResultados de consulta OR:\n\n")) {
                result = "No docs found for that query :( ";
            }*/
        }
        return result; 
    }
    
     public String procesarOr(String consulta)throws IOException {
        
        String result = "";
        consulta = consulta.toLowerCase();
        String [] palabras = consulta.split(" ");
        if (palabras[0].equals("")) {
                result = "No query terms found :( ";
        } else {
            
            Map<String, List<Integer>> treeMap = new TreeMap<String, List<Integer>>(indice);
            Set s = treeMap.entrySet();
            Iterator it = s.iterator();
            result += "\t\t\tResultados de consulta OR:\n";
            for (int i = 0; i < palabras.length; i++){
                ArrayList<Integer> actualSearchDocs = new ArrayList<Integer>();
                String searchWord = palabras[i];
                it = s.iterator();
                int flagPerWord = 0;
                actualSearchDocs = getPostings(searchWord, it, actualSearchDocs);
                result = imprimirRequest(result, actualSearchDocs, flagPerWord, searchWord);
                result += "\n";
            }
            if (result.equals("\t\t\tResultados de consulta OR:\n\n")) {
                result = "No docs found for that query :( ";
            }
        }
        return result; 
    }
   
    public String imprimirIntersect(String result, ArrayList<Integer> intersectResult, String consulta)throws IOException {
        result+= "Los documentos en que aparece la consulta: " + consulta; 
        for(int c= 0; c < intersectResult.size();c++){
            String linea = null; 
            FileReader fileReader = new FileReader("Documentos.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((linea = bufferedReader.readLine()) != null) {
                String [] token = linea.split("\\|");
                int docID = Integer.parseInt(token[1]);
                if (docID == intersectResult.get(c)) {
                    result += "\n\t"+token[0]+".";
                    break; 
                }
            } 
            bufferedReader.close();  
        }
        return result; 
    }
    
    public ArrayList<Integer> getPostings(String searchWord, Iterator it, ArrayList<Integer> actualSearchDocs) {
        //Itera sobre el arbol en busqueda de la palabra
            while ( it.hasNext() ) {
               Map.Entry entry = (Map.Entry) it.next();
               String termino = (String) entry.getKey();
               // si el termino de busqueda esta en el diccionario 
               if (termino.equals(searchWord)) {
                     
                     List<Integer> lista  = (List<Integer>) entry.getValue();
                     //Itera sobre la lista de postings del termino, para guardarlo luego en disco
                     for(int c= 0;c <lista.size();c++){
                         actualSearchDocs.add(c,lista.get(c));
                     }
               }
            }//while
            return actualSearchDocs; 
    }
    
    
    public String imprimirRequest(String result, ArrayList<Integer> actualSearchDocs, int flagPerWord, String termino)throws IOException {
        // deberia ser el nombre del doc no el id
        // el siguiente codigo solo es ara sacar el nombre del documento
        for(int c= 0;c <actualSearchDocs.size();c++){
            String linea = null; 
            FileReader fileReader = new FileReader("Documentos.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((linea = bufferedReader.readLine()) != null) {
                String [] token = linea.split("\\|");
                int docID = Integer.parseInt(token[1]);
                if (docID == actualSearchDocs.get(c)) {
                    //System.out.println("termino "+termino+" sale en doc "+token[0]);
                    if (flagPerWord == 0) {
                        result += "\nTérmino "+termino+ ", aparece en los documentos: \n\n\t"+token[0]+".";
                        flagPerWord = 1; 
                        break; 
                    } else {
                        result += "\n\t"+token[0]+".";
                        break; 
                    }
                }
            } 
            bufferedReader.close();  
        }
        return result; 
        
    }
    
    
    public ArrayList<Integer> intersect(ArrayList<Integer> p1Postings, ArrayList<Integer> p2Postings, ArrayList<Integer> intersectResult) {
       int indexP1 = 0;
       int indexP2 = 0; 
       while (indexP1 < p1Postings.size() && indexP2 < p2Postings.size()) {
            System.out.println("Entro a comprobar");
            if (Objects.equals(p1Postings.get(indexP1), p2Postings.get(indexP2))) {
                System.out.println("Encuentro postings iguales");
                intersectResult.add(p1Postings.get(indexP1));
                indexP1++;
                indexP2++;
            } else {
                System.out.println("No hay iguales");
                if (p1Postings.get(indexP1) < p2Postings.get(indexP2)){
                    indexP1++;
                } else {
                    indexP2++;
                }
            }
       }
       
       return intersectResult; 
    }
    
    public Map sortByValue(HashMap unsortMap) {	 
         List list = new LinkedList(unsortMap.entrySet());

         Collections.sort(list,Collections.reverseOrder(new Comparator() {
                 public int compare(Object o1, Object o2) {
                         return ((Comparable) ((Map.Entry) (o1)).getValue())
                                                 .compareTo(((Map.Entry) (o2)).getValue());
                 }
         }));

         Map sortedMap = new LinkedHashMap();
         for (Iterator it = list.iterator(); it.hasNext();) {
                 Map.Entry entry = (Map.Entry) it.next();
                 sortedMap.put(entry.getKey(), entry.getValue());
         }
         return sortedMap;
    }
    
    public  String getRelevantDocs(String consulta,double relevancia) throws IOException{
        busquedaVectorial bv = new busquedaVectorial();
        String result="";
        consulta = consulta.toLowerCase();
        String [] palabras = consulta.split(" ");
        int cont = -1;
        if (palabras[0].equals("")) {
                result = "No query terms found :( ";
        } else {
            result += "\t\t\tResultados de la consulta:\n\n";
            HashMap<String, Double> hashMap = bv.getQueryWeight(palabras);
            HashMap<String, Double> relevantDocs = bv.getRelevantValues(hashMap);
            Map <String, Double> relevantDocsOrdered = sortByValue(relevantDocs);
            Set ss = relevantDocsOrdered.entrySet();
            Iterator itr = ss.iterator();   
            while(itr.hasNext()){
                Map.Entry entry = (Map.Entry) itr.next();
                if(relevancia < 0){
                    cont=0;
                    result+= entry.getKey()+" relevancia   "+entry.getValue()+"\n";
                }
                else{
                    if(Double.parseDouble(entry.getValue().toString())>= (relevancia/10)){
                        cont = 0;
                        result+= entry.getKey()+" relevancia   "+entry.getValue()+"\n";
                    }
                }
            }
            if (cont == -1) {
                result = "No docs found for that query :( ";
            }
        }
        return result;
    }
    
     public String getRelevantDocsJaccard(String consulta,double relevancia) throws IOException{
        busquedaVectorial bv = new busquedaVectorial();
        String result="";
        consulta = consulta.toLowerCase();
        String [] palabras = consulta.split(" ");
        int cont = -1;
        if (palabras[0].equals("")) {
                result = "No query terms found :( ";
        } else {
            result += "\t\t\tResultados de la consulta:\n\n";
            //HashMap<String, Double> hashMap = bv.getQueryWeight(palabras);
            HashMap<String, Double> relevantDocs = bv.getRelevantValuesJaccard(palabras);
            Map <String, Double> relevantDocsOrdered = sortByValue(relevantDocs);
            Set ss = relevantDocsOrdered.entrySet();
            Iterator itr = ss.iterator();   
            while(itr.hasNext()){
                Map.Entry entry = (Map.Entry) itr.next();
                if(relevancia < 0){
                    cont=0;
                    result+= entry.getKey()+" relevancia   "+entry.getValue()+"\n";
                }
                else{
                    if(Double.parseDouble(entry.getValue().toString())>= (relevancia/10)){
                        cont = 0;
                        result+= entry.getKey()+" relevancia   "+entry.getValue()+"\n";
                    }
                }
            
            }
            if (cont == -1) {
                result = "No docs found for that query :( ";
            }
        }
        return result;
    }
    //Método encargado de llenar la lista de sugerencias de la interfaz
    public DefaultListModel<String> obtenerSugerencias(String busqueda){
        int cont = 0;
        DefaultListModel<String> sugerenciasEncontradas = new DefaultListModel<>();
        Set s = sugerencias.entrySet();
        Iterator it = s.iterator();
        String[] busquedaTokenizada = busqueda.split(" ");
        //Itera sobre el arbol creado, para encontrar las 5 sugerencias
        while ( it.hasNext() && cont<5 ) {
           String listaPostings = "";
           Map.Entry entry = (Map.Entry) it.next();
           String termino = (String) entry.getKey();
           if(termino.contains(busqueda.toLowerCase())){
               sugerenciasEncontradas.addElement(termino);
           }
           cont++;
            
        }//while
         return sugerenciasEncontradas;
    }
    public static void inicializar(){
        // Cambiar por la direccion en donde se encuentre la carpeta Docs, es decir cambie 
        // b21684 por su usuario o toda la ruta. 
        
         //File[] files = new File("C:/Users/b21684/Documents/Github/RecuTareas/Codigo/Docs").listFiles();
        //C:\Users\b21684\Documents\GitHub\RecuTareas\Codigo\Docs
        File[] files = new File("C:/Users/Vitaly/Documents/RecuTareas/Codigo/Docs").listFiles();
 
        HtmlParse parser = new HtmlParse();
            try{
                bsbi(files, parser); 
            }catch(IOException e){
                e.printStackTrace();
            }
    }
    
    public static void main(String[] args) {
       // TODO code application logic here
        //File[] files = new File("C:/Users/b14011/Desktop/RecuTareas-master/Codigo/Docs").listFiles();
        HtmlParse parser = new HtmlParse();
        inicializar();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                
                RecuProyecto controlador = new RecuProyecto(); 
                //try{
                    //controlador.guardarIndice();
                //}catch(IOException e){};
            }
        }));
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Display().setVisible(true);
            }
        });
        
    }
    
}
