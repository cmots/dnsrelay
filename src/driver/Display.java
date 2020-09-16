package driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author: Liu Yutong
 * @date: 2020/7/29 23:22
 * @description: This class is responsible for all output on screen.
 */

public class Display {
    /**
     * the welcome text should be printed on screen
     * @author: Liu Yutong
     * @param
     * @return
     * @note: design this based on teacher's programme
     */
    public void welcome(){
    	System.out.println("DNSRELAY, Version 1.30, Build: Feb 22 2011 10:08:17");
    	System.out.println("Usage: dnsrelay [-d | -dd] [<dns-server>] [<db-file>]");
    	System.out.println("Name server 202.106.0.20:53.");
    	System.out.println("Debug level 0.");
    	System.out.println("Bind UDP port 53 ...OK!");
    	System.out.println("Try to load table dnsrelay.txt ... OK");
		System.out.println("Try to load table config.txt ... OK");
    }

    /**
     * print text on screen, with a '\n' at last line
     * @author: Liu Yutong
     * @param output text that is need to be printed on screen
     * @return
     * @throws InterruptedException 
     * @throws IOException 
     */
    public void displayResponse(String output) throws IOException, InterruptedException{
    	  Process p = Runtime.getRuntime().exec("ping www.baidu.com");	//��ɫ��Ϊִ�е�����
    	  InputStream is = p.getInputStream();
    	  BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    	  String line;
    	  while((line = reader.readLine())!= null){
    	   System.out.println(line);
    	  }
    	  p.waitFor();
    	  is.close();
    	  reader.close();
    	  p.destroy(); 
    }
    
}

