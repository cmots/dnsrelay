package driver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * @author: Liu Yutong, Cheng Sitong
 * @date: 2020/7/29 23:26
 * @description: This class is used to get and parse input,
 *               and send parsed information to other modules
 */

public class CommandLine {
    private int dbgLevel;
    private String dns_server;
    private String dns_file;

    /**
     * get and parse the command, set the config of program
     * @author: Liu Yutong
     * @param
     * @return
     * @throws InterruptedException 
     * @throws IOException 
     */
    public CommandLine(String[] commands){
        // TODO 分析指令，dbgLevel先不做

    }

    /**################# getter & setter ####################*/
    public int getDbgLevel() {
        return dbgLevel;
    }

    public void setDbgLevel(int dbgLevel) {
        this.dbgLevel = dbgLevel;
    }

    public String getDns_server() {
        return dns_server;
    }

    public void setDns_server(String dns_server) {
        this.dns_server = dns_server;
    }

    public String getDns_file() {
        return dns_file;
    }

    public void setDns_file(String dns_file) {
        this.dns_file = dns_file;
    }

}
