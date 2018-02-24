/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consumersmssender;

import com.mtn.sms.handler.SMSHandler;

/**
 *
 * @author alirezakhtm
 */
public class ConsumerSMSSender {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Start SMSSender Consumer.");
//        int timMiliSec = 0;
//        if(args.length == 0){
//            timMiliSec = 1000;
//        }else{
//            timMiliSec = (int)Double.parseDouble(args[0]);
//        }
        SMSHandler smsh = new SMSHandler(1000);
        smsh.startGuardTimer();
    }
    
}
