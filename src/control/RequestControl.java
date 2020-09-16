/**
 * @author: Liu Zhixin
 * @date: 2020/7/30 9:31
 * @description: This is the controller fo handling the DNS request
 */
package control;

import dao.DNSLook;
import org.xbill.DNS.Message;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;

import java.net.DatagramPacket;

public class RequestControl {
    public boolean localRequest(DatagramPacket packet){
        byte[] data = packet.getData();
        try {
            Message message = new Message(data);
            Record question = message.getQuestion();
            Name name = question.getName();

        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    public void request(SocketControl socket, DatagramPacket packet){
        byte[] data = packet.getData();
        byte[] target;
        int port = packet.getPort();
        DNSLook dnsLook = new DNSLook();
        String queryName = "";
        int i = 12;
        while(data[i]!=0) {
            int num = data[i];
            target = new byte[num];
            System.arraycopy(data, ++i, target, 0, num);
            queryName += new String(target);
            i += num;
            if(data[i]!=0){
                queryName += ".";
            }
        }
        String result = dnsLook.look(queryName);
        try{
            if(result.equals("ban")){
                System.out.println("IP Address is 0.0.0.0");
                //
                return;
                //
            }else if(result.equals("miss")){
                System.out.println("Query target not found ");
                socket.send(data, dnsLook.address, port);
            }else{
                System.out.println("Query target found ");

                socket.send(data, result, port);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
