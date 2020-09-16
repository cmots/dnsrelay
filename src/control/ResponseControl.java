/**
 * @author: Liu Zhixin
 * @date: 2020/7/30 9:30
 * @description: This is the controller of handling the DNS response
 */
package control;

import vo.Message;


import java.net.DatagramPacket;
import java.net.InetAddress;

public class ResponseControl {
    public void response(SocketControl socket){
        Message message = socket.receive();
        socket.send(message.makePacket(false), "127.0.0.1", 53);
        System.out.println(message.getQueryName());
        System.out.println("type:"+message.getQueryType());
        return;
    }
}
