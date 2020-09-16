import control.RequestControl;
import control.ResponseControl;
import control.SocketControl;
import driver.CommandLine;
import driver.Display;
import vo.Message;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author: Cheng Sitong
 * @date: 2020/7/29 23:08
 * @description: This is the main module of entire project, which is used to call other sub-modules
 */

public class Main {
    public static void main(String[] args){
        SocketControl socketControl = new SocketControl();
        RequestControl requestControl = new RequestControl();
        ResponseControl responseControl = new ResponseControl();

        CommandLine commandLine = new CommandLine(args);
        Display display = new Display();
        display.welcome();
        while(true){
            Message message = socketControl.receive();
            requestControl.request(socketControl,message);
            //responseControl.response(,socketControl);
        }
    }

}
