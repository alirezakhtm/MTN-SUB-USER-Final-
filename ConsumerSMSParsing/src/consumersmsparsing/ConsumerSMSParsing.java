/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consumersmsparsing;

import com.mtn.sms.parsing.SMSParsing;
/**
 *
 * @author alirezakhtm
 */
public class ConsumerSMSParsing {
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Start SMS Parsing Consumer.");
        SMSParsing smsp = new SMSParsing();
//        if(args.length == 1){
//            int timeDelay = (int)Double.parseDouble(args[0]);
//            smsp.setTimeOfCuardTimer(timeDelay);
//        }else{
//            // nothing
//        }
//        // start timer for check database of ActiveMQ and process of message parsing
//        // if this process stoped for any reason, this timer run it again
//        smsp.startGuardTimer(Boolean.TRUE, Boolean.FALSE);
        
        
        smsp.setTimeOfCuardTimer(1000);
        smsp.startGuardTimer(Boolean.TRUE, Boolean.FALSE);
    }
    
}
