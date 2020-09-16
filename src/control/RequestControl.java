/**
 * @author: Liu Zhixin
 * @date: 2020/7/30 9:31
 * @description: This is the controller fo handling the DNS request
 */
package control;

import dao.DNSLook;
import vo.Message;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class RequestControl {
    public void request(SocketControl socket, Message message) {
        try {
            DNSLook dnsLook = new DNSLook();
            String ip = dnsLook.look(message.getQueryName());
            ResponseControl responseControl = new ResponseControl();
            int clientPort = message.getClientPort();
            switch (message.getQueryType()) {
                case 1:
                    System.out.println("** IPv4 query **");
                    switch (ip) {
                        case "ban":
                            System.out.println("Query target is banned");
                            message.setRelyCode(3);
                            message.setAddress("0.0.0.0");
                            socket.send(message.makePacket(true), "127.0.0.1", clientPort);
                            break;

                        case "miss":
                            System.out.println("Query target cannot found");
                            socket.send(message.makePacket(false), dnsLook.address, 3344);
                            responseControl.response(clientPort,socket);
                            break;

                        default:
                            System.out.println("in local");
                            message.setAddress(ip);
                            System.out.println(ip);
                            message.setAnswerRRs(1);
                            message.setQR(1);
                            message.setAnswerName(ip);
                            socket.send(message.makePacket(true), "127.0.0.1", clientPort);
                            break;
                    }
                    break;
                case 12:
                    break;
                case 28:
                    System.out.println("** IPv6 query **");
                    switch (ip) {
                        case "ban":
                            System.out.println("Query target is banned");
                            message.setRelyCode(3);
                            message.setAddress("0.0.0.0");
                            socket.send(message.makePacket(true), "127.0.0.1", clientPort);
                            break;

                        case "miss":
                            System.out.println("Query target cannot found");
                            socket.send(message.makePacket(false), dnsLook.address, 53);
                            responseControl.response(clientPort,socket);
                            break;

                        default:
                            System.out.println("in local");
                            message.setAnswerRRs(1);
                            message.setQR(1);
                            message.setAnswerName(ip);
                            socket.send(message.makePacket(true), "127.0.0.1", clientPort);
                            break;
                    }
                    break;

                default:
                    System.out.println("无可奈何花落去，似曾相识燕归来。\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return;
    }

}
