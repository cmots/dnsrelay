import control.RequestControl;
import control.ResponseControl;
import control.SocketControl;
import driver.CommandLine;
import driver.Display;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author: Cheng Sitong
 * @date: 2020/7/29 23:08
 * @description: This is the main module of entire project, which is used to call other sub-modules
 */

public class Main {
    private DatagramSocket socket ;
    private DatagramPacket packet ;
    static SocketControl socketControl = new SocketControl();
    static RequestControl requestControl = new RequestControl();
    static ResponseControl responseControl = new ResponseControl();

    public void run(){
        try{
            while(true){
                this.socket.receive(this.packet);
                int QR = (int)this.packet.getData()[2] & 0xff & 0b10000000;
                if(QR == 0){
                    requestControl.request(socketControl, this.packet);
                }else{
                    responseControl.response(socketControl);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
    public static void main(String[] args){
        CommandLine commandLine = new CommandLine(args);
        Display display = new Display();
        display.welcome();
        Main m = new Main();
        m.run();
    }

}
