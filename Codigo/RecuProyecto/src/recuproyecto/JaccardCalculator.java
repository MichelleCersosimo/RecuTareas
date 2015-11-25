/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recuproyecto;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * Calculates Jaccard coefficient for two sets of items. 
 * 
 * @author b21684
 */
public class JaccardCalculator  {
  
    public static double jc(String[] sSplit, String[] tSplit)  
    {  

          
        //calcula intersection  
        List<String> intersection=new ArrayList<String>();  
        for(int i=0;i<sSplit.length;i++)  
        {  
            for(int j=0;j<tSplit.length;j++)  
            {  
                if(!intersection.contains(sSplit[i]))      
                    if(sSplit[i].equals(tSplit[j]))   
                    {  
                        intersection.add(sSplit[i]);  
                        break;  
                    }  
            }  
        }  
          
        //calcula union  
        List<String> union=new ArrayList<String>();  
        if(sSplit.length>tSplit.length)                      
        {  
            for(int i=0;i<sSplit.length;i++)  
                if(!union.contains(sSplit[i]))  
                    union.add(sSplit[i]);  
            for(int i=0;i<tSplit.length;i++)  
                if(!union.contains(tSplit[i]))  
                    union.add(tSplit[i]);  
        }  
        else  
        {  
            for(int i=0;i<tSplit.length;i++)  
                if(!union.contains(tSplit[i]))  
                    union.add(tSplit[i]);  
            for(int i=0;i<sSplit.length;i++)  
                if(!union.contains(sSplit[i]))  
                    union.add(sSplit[i]);  
              
        }  
          
        return ((double)intersection.size())/((double)union.size());  
    } 
    
}

