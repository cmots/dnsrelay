package dao;

import java.io.*;
import java.util.ArrayList;

public class DNSLook {
    public ArrayList<String[]> list;
    public String address;

    public DNSLook(){
        File file = new File("resources/dnsrelay.txt");
        if(file.isFile() && file.exists()){
            try {
                list = new ArrayList<>();
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String str;
                while ((str = bufferedReader.readLine()) != null) {
                    String[] entity = str.split(" ");
                    list.add(entity);
                }
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("dnsrelay.txt cannot found!");
        }

        file = new File("resources/config.txt");
        if(file.isFile() && file.exists()) {
            try {
                list = new ArrayList<>();
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String str;
                while ((str = bufferedReader.readLine()) != null) {
                    address = str;
                }
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            System.out.println("config.txt cannot found!");
        }
    }

    public String look(String queryname) {
        for(int i=0; i<list.size(); i++) {
            if(list.get(i)[1].equals(queryname)) {
                if(list.get(i)[0].equals("0.0.0.0")) {
                    return "ban";
                }
                else {
                    return list.get(i)[0];
                }
            }
        }
        return "miss";
    }

}
