/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alirzea
 */
public class NewClass {
    public static void main(String[] args) {
        String msisdn = "tel:989194018087";
        if(msisdn.contains(":"))
            msisdn = msisdn.substring(msisdn.indexOf(":")+1, msisdn.length()-1);
        System.out.println(">> " + msisdn);
    }
}
