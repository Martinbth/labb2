/**
 * Created by martinlundquist on 2014-10-13.
 */
import java.io.*;
import java.net.*;

public class UdpClient {
    public static void main(String args[]) throws Exception{

        DatagramSocket clientSocket = null;
        int port = 1337;

        BufferedReader userChoise = new BufferedReader(new InputStreamReader(System.in));

        clientSocket = new DatagramSocket();
        InetAddress nameServerIP = InetAddress.getByName("localhost");
        System.out.println("jo");

       // byte[] sendCRequest = new byte[65507];

        String sentence = userChoise.readLine();
        byte[] sendCRequest = sentence.getBytes();
        DatagramPacket sndPacket = new DatagramPacket (sendCRequest,sendCRequest.length,nameServerIP,port);
        clientSocket.send(sndPacket);


        byte[] receiveAnswer = new byte[65507];
        DatagramPacket receiveList = new DatagramPacket (receiveAnswer,receiveAnswer.length);
        System.out.println("jo2");
        clientSocket.receive(receiveList);
        System.out.print("jo3");
        String fromServer = new String(receiveList.getData());
        System.out.println("From server: " + fromServer);
        clientSocket.close();
    }



}
