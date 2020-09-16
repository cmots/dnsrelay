/**
 * @author: Liu Zhixin
 * @date: 2020/7/30 9:30
 * @description: This is the controller of handling the DNS response
 */
package control;

import org.xbill.DNS.*;


import java.net.DatagramPacket;
import java.net.InetAddress;

public class ResponseControl {
    public void response(SocketControl socket){
        Messages messages = socket.receive();
        Message outdata = messages.getOutdata();
        InetAddress sourceIpAddr = messages.getSourceIpAddr();
        InetAddress answerIpAddr = messages.getAnswerIpAddr();
        int sourcePort = messages.getSourcePort();
        int type = messages.getQueryType();
        Record question = messages.getQuestion();

        //when type is 28,it stands for ipv6
        if(type == 28){
            Record answer = new AAAARecord(question.getName(), question.getDClass(), 64, answerIpAddr);
            outdata.addRecord(answer, Section.ANSWER);
        }else if(type == 1){
            Record answer = new ARecord(question.getName(), question.getDClass(), 64, answerIpAddr);
            outdata.addRecord(answer, Section.ANSWER);
        }
        byte[] buf = outdata.toWire();
        socket.send(buf, sourceIpAddr.toString(), sourcePort);
    }
}
