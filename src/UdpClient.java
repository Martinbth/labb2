/**
 * Created by martinlundquist on 2014-10-13.
 */
import java.io.*;
import java.net.*;

public class UdpClient {
    public static void main(String args[]) throws Exception{

        BufferedReader userChoiseOfNameServer =
                new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress nameServerIP = InetAddress.getByName("itchy.umu.se");

        byte[] sendConnectRequest = new byte[1024];
        byte[] receiveAnswer = new byte[1024];

        String sentence = userChoiseOfNameServer.readLine();
        sendConnectRequest = sentence.getBytes();
        DatagramPacket reqPacket = new DatagramPacket
                (sendConnectRequest, sendConnectRequest.length, nameServerIP, 1337);
        clientSocket.send(reqPacket);
        DatagramPacket receivePacket = new DatagramPacket(receiveAnswer, receiveAnswer.length);
        clientSocket.receive(receivePacket);
        String fromServer = new String(receivePacket.getData());
        System.out.println("From server: " + fromServer);
        clientSocket.close();
    }



}
