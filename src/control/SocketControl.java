/**
 * @author: Cheng Sitong
 * @date: 2020/7/30 0:04
 * @description: This is controller of handling socket, which can be used in other modules
 */
package control;

import vo.Messages;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class SocketControl {

    private DatagramSocket socket;

    /**
     * Start the socket service
     * @author: Cheng Sitong
     * @param
     * @return
     */
    public SocketControl() {
        try {
            this.socket = new DatagramSocket(53);
        } catch (Exception e) {
            System.out.println("bug in socket create\n");
            e.printStackTrace();
        }
    }

    /**
     * send your UDP data
     * @author: Cheng Sitong
     * @param data data you want to send
     * @param IP your destination IP address;
     *           this may be decided in user's command or your setting
     * @param port the source port, DO NOT use 53 here
     * @return
     */
    protected void send(String data, String IP, int port) {
        byte[] bytes = string2Bytes(data);
        try {
            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, InetAddress.getByName(IP),port);
            socket.send(datagramPacket);
            System.out.println("Successfully send");
        }
        catch (Exception e) {
            System.out.println("bug in send\n");
            e.printStackTrace();
        }
    }

    protected void send(byte[] data, String IP, int port) {
        try {
            DatagramPacket datagramPacket = new DatagramPacket(data, data.length, InetAddress.getByName(IP),port);
            socket.send(datagramPacket);
            System.out.println("Successfully send");
        }
        catch (Exception e) {
            System.out.println("bug in send\n");
            e.printStackTrace();
        }
    }

    /**
     * receive UDP data from others at port 53
     * @author: Cheng Sitong
     * @param
     * @return
     */
    protected Messages receive(){
        byte[] bytes = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(bytes,bytes.length);
        try {
            socket.receive(datagramPacket);
            Messages message = new Messages(datagramPacket);
            return message;
        }
        catch (Exception e){
            System.out.println("bug in receive\n");
        }
        return null;
    }

    private byte[] string2Bytes(String input) {
        try {
            return input.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("bug in string2bytes\n");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * close the socket
     * @author: Cheng Sitong
     * @param
     * @return
     */
    protected void socketClose(){
        this.socket.close();
    }


}
