/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recuproyecto;

import java.io.File;

/**
 *
 * @author Pc
 */
public class RecuProyecto {

    /**
     * @param args the command line arguments
     */
    

    public static void showFiles(File[] files, HtmlParse parser ) {
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("Directory: " + file.getName());
                showFiles(file.listFiles(), parser); // Calls same method again.
            } else {
                try {
                    parser.parsear(file);
                } catch (Exception e) {};
                System.out.println("File: " + file.getName());
            }
        }
    }
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        File[] files = new File("C:/Prueba/").listFiles();
        HtmlParse parser = new HtmlParse();
        showFiles(files, parser); 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Display().setVisible(true);
            }
        });
        
    }
    
}
