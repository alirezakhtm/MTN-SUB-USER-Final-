
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alireza
 */
public class NewClass {
    public static void main(String[] args) {
        try{
            URL url = new URL("http://www.google.com");
            URLConnection urlConnection = url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            BufferedReader bis = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            urlConnection.connect();
            StringBuilder sb = new StringBuilder();
            String line = "";
            while((line = bis.readLine()) != null){
                sb.append(line);
            }
            System.out.println(sb.toString());
        }catch(IOException e){
            System.err.println(e);
        }
    }
}
