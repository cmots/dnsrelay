/**
 * @author: Liu Zhixin
 * @date: 2020/7/30 9:30
 * @description: This is the controller of handling the DNS response
 */
package control;

import vo.Message;

public class ResponseControl {
    public void response(int clientPort,SocketControl socket){
        Message message = socket.receive();
        socket.send(message.makePacket(false), "127.0.0.1", clientPort);
        return;
    }
}
