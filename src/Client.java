
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
/**
 * Created by martinbaath on 14-10-19.
 */
public class Client {
    private static InetAddress nameServerAdress;
    private static DatagramSocket clientSocket;
    private static Socket serverSocket;
    private static String nickName;
    int lengthOfSlist = 0;
   // private static

    public static void main(String [] ags) throws IOException{

        try {
            clientSocket = new DatagramSocket();
            nameServerAdress = InetAddress.getByName("itchy.cs.umu.se");
            byte[] receiveData = new byte[65507];
            byte[] sendData = new byte[65507];
            PDU getList = pduHandler.getList();
            sendData = getList.getBytes();
            DatagramPacket getPackage = new DatagramPacket(sendData, sendData.length, nameServerAdress, 1337);
            clientSocket.send(getPackage);

            DatagramPacket receiveList = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receiveList);
            PDU rPDU = new PDU(receiveList.getData(), receiveList.getData().length);
            rPDU.setSubrange(0, receiveList.getData());

            int listLenght = 4;
            if (rPDU.getByte(0) == OpCodes.SLIST) {

                if (rPDU.getByte(1) == 0) {

                    for (int i = 0; i < rPDU.getShort(2); i++) {

                        byte[] serverIp = rPDU.getSubrange(listLenght, 4);
                        InetAddress server = InetAddress.getByAddress(serverIp);
                        listLenght = listLenght + 4;
                        int portNs = rPDU.getShort(listLenght);
                        listLenght = listLenght + 2;
                        int numC = rPDU.getByte(listLenght);
                        listLenght = listLenght + 1;
                        int serverNameLength = (int) rPDU.getByte(listLenght);
                        listLenght = listLenght + 1;


                        //InetAddress server = InetAddress.getByAddress(serverIp);

                        String topicNs = new String(rPDU.getSubrange(listLenght,
                                serverNameLength), "UTF-8");

                        System.out.println("Address: " + server + " Port: " + portNs + " Anslutna: " + numC + " Ämne: " +
                                topicNs);
                        System.out.println("--------------------------");
                        listLenght += div4(serverNameLength);
                        while (topicNs.length() % 4 != 0) {
                            topicNs = topicNs + '\0';
                        }
                    }
                }
            }
            //Användaren väljer server den vill ansluta sig till
            BufferedReader serverChoose = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Choose Server:  ");

            final InetAddress host = InetAddress.getByName(serverChoose.readLine());
            System.out.print("And the port number of the server: ");
            final int port = Integer.parseInt(serverChoose.readLine());
            serverSocket = new Socket(host, port);

            //Användaren väljer nickname den vill heta på servern
            System.out.print("Enter nickname: ");

            byte[] byteArray = new  byte[1024];
            DataOutputStream out = new DataOutputStream(serverSocket.getOutputStream());
            BufferedReader nickIn = new BufferedReader(new InputStreamReader(System.in));
            nickName = nickIn.readLine();
            PDU pduNick = pduHandler.join(nickName);
            byteArray = pduNick.getBytes();
            out.write(byteArray);
        }

        catch(Exception e){
            System.out.print(e);
            System.exit(0);
        }

    }
    public static int div4(int testInt){
        int ret = 0;
        if((4 -(testInt % 4)) != 0){
            ret = (4 -(testInt % 4));
        }
        return testInt + ret;
    }
}


