/**
 * Created by martinlundquist on 2014-10-13.
 */
import java.io.*;
import java.net.*;

public class UdpClient {
    public static void main(String args[]) throws Exception{

        DatagramSocket clientSocket = null;
        int port = 1337;
        //Användaren skriver
        //BufferedReader userChoise = new BufferedReader(new InputStreamReader(System.in));
        //Startar en socket för klienten
        clientSocket = new DatagramSocket(8080);
        //konverterar om namnet på servern till själva ip adressen
        InetAddress nameServerIP = InetAddress.getByName("itchy.cs.umu.se");
        System.out.println(nameServerIP);
        System.out.println("Write something man");

        byte myValue = 3;
        PDU pdu = new PDU(4);
        //pdu.setShort(0, (short) myValue);
        pdu.setByte(0, myValue);
        //int myOtherValue = pdu.getShort(0);
        //System.out.println(myOtherValue);


       // byte[] sendCRequest = new byte[65507];

        //String sentence = userChoise.readLine();
        //byte[] sendCRequest = sentence.getBytes();
        DatagramPacket getList = new DatagramPacket (pdu.getBytes(), pdu.length(), nameServerIP,port);
        clientSocket.send(getList);


        byte[] receiveAnswer = new byte[65507];
        DatagramPacket receiveList = new DatagramPacket (receiveAnswer,receiveAnswer.length);

        clientSocket.receive(receiveList);

        PDU rPDU=new PDU(receiveAnswer.length);
        rPDU.setSubrange(0,receiveList.getData());
        String fromServer = new String(rPDU.getSubrange(0, rPDU.length()));
        System.out.println("From server: " + fromServer);
        clientSocket.close();
        int sOp=(int) rPDU.getByte(1);
        int antalservers=rPDU.getShort(2);
        System.out.print("OP-kod: " + sOp  + "  ");
        System.out.println("antalservers: " +antalservers + "  ");


    }
}