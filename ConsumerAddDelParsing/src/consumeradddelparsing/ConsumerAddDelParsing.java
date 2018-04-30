/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consumeradddelparsing;

import com.mtn.data.parsing.DataParsing;

/**
 *
 * @author alirezakhtm
 */
public class ConsumerAddDelParsing {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Start Add and Del Parsing.");
//        if(args.length == 0){
//            DataParsing dataParsing = new DataParsing(1000);
//            dataParsing.startGuardTimer();
//        }else{
//            int TimeDelay = (int)Double.parseDouble(args[0]);
//            DataParsing dataParsing = new DataParsing(TimeDelay);
//            dataParsing.startGuardTimer();
//        }
        DataParsing dataParsing = new DataParsing(1000);
        dataParsing.startGuardTimer();
        
    }
    
}
