/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alirezakhtm
 */
public class GsonTest {
    public static void main(String[] args) {
        try(Writer w = new FileWriter("obj.json")){
            Gson gson = new GsonBuilder().create();
            List<Person> lst = new ArrayList<>();
            Person p1 = new Person("Alireza", "Khatami Doost", 27, 1);
            Person p2 = new Person("Samaneh", "Khatami Doost", 27, 0);
            Person p3 = new Person("Mohammad", "Khatami Doost", 38, 1);
            Person p4 = new Person("Mahdokht", "Khatami Doost", 30, 0);
            lst.add(p1);
            lst.add(p2);
            lst.add(p3);
            lst.add(p4);
            gson.toJson(lst, w);
        }catch(IOException e){
            System.err.println(e);
        }
        
        try (Reader r = new FileReader("obj.json")){
            Gson g = new GsonBuilder().create();
            List<Person> lst = g.fromJson(r, new TypeToken<List<Person>>(){}.getType());
            lst.forEach((m) -> {
                System.out.println(m);
            });
        } catch (Exception e) {
            
        }
        
        Gson g2 = new GsonBuilder().create();
        Person m = new Person("Ghader", "Moeeni", 20, 1);
        String str = "";
        str = g2.toJson(m);
        
        System.out.println("output >>> " + str);
        
    }
}
