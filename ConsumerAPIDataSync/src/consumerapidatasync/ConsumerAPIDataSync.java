/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consumerapidatasync;

import com.fidar.logic.AppLogic;

/**
 *
 * @author alireza
 */
public class ConsumerAPIDataSync {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AppLogic appLogic = new AppLogic();
        appLogic.startGuardTimer();
    }
    
}
