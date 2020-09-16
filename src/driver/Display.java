package driver;

import dao.DNSLook;

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
    	//System.out.println("Usage: dnsrelay [<dns-server>] [<db-file>]");
    	System.out.println("Name server "+new DNSLook().address +".");
    	System.out.println("Bind UDP port 53 ...OK!");
    	System.out.println("Loading dnsrelay.txt ... OK");
		System.out.println("Loading config.txt ... OK");
    }
    
}

