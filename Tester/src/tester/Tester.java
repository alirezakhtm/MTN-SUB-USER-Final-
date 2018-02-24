/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import javax.xml.ws.Holder;
import org.csapi.schema.parlayx.data.v1_0.NamedParameterList;
import org.csapi.schema.parlayx.data.v1_0.UserID;
import org.csapi.wsdl.parlayx.data.sync.v1_0.service.DataSyncService;

/**
 *
 * @author alirezakhtm
 */
public class Tester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DataSyncService dss = new DataSyncService();
        UserID uid = new UserID();
        uid.setID("1256");
        uid.setType(12);
        Holder<NamedParameterList> extensionInfo = 
                new Holder<>();
        NamedParameterList npl = new NamedParameterList();
        extensionInfo.value = npl;
        Holder<Integer> result = new Holder<>();
        result.value = 12;
        Holder<String> resultDescription = new Holder<>();
        resultDescription.value = "Alireza";
        dss.getDataSync().syncOrderRelation(
                uid,
                "122311",
                "8702413",
                "11212211",
                "111155555",
                0,
                "12:56:32",
                "Good",
                "12:16:32",
                "12:16:32",
                null,
                null,
                null);
    }
    
}
