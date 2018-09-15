/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rahkar.service;

import com.rahkar.xml.model.Root;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.springframework.stereotype.Component;

/**
 *
 * @author alirzea
 */
@Component
public class XMLService {
    
    private static Root root = null;
    
    private static final String FILE_ADDRESS = "dbuser.xml";
    
    private void readXmlFile(){
        File file = new File(FILE_ADDRESS);
        try{
            JAXBContext context = JAXBContext.newInstance(Root.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            root = (Root) unmarshaller.unmarshal(file);
        }catch(JAXBException e){
            System.err.println("XMLService/readXmlFile/readXmlFile - " + e);
        }
    }
    
    public Root getConfigFileData(){
        if(root == null){
            this.readXmlFile();
        }
        return root;
    }
    
    public void saveData(Root root){
        try{
            JAXBContext context = JAXBContext.newInstance(Root.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(root, new File(FILE_ADDRESS));
            marshaller.marshal(root, System.out);
        }catch(JAXBException e){
            System.err.println("XMLService/saveData - " + e);
        }
    }
    
}
