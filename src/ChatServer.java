/**
 * Created by martinlundquist on 14-10-19.
 */
import java.net.*;
import java.util.List;

public class ChatServer {
    private int connected;
    private int serverPort = 8080;
    private String serverName = "Jamaica Code Suppliers INC.";
    private ServerSocket serverSocket = null;
    private InetAddress ip;
    private List clientList = null;
    private DatagramPacket cnPacket = null;


    protected ChatServer(int a,int b,String c,InetAddress d) throws Exception{
        this.connected = a;
        this.serverPort = b;
        this.serverName = c;
        this.ip = d;
    }


    private void connectToNs() throws Exception {
        InetAddress nsAdress = InetAddress.getByName("itchy.cs.umu.se");
        //cnPacket = new DatagramPacket(nsAdress, );
    }
    private int getConnected(){
          return connected;
    }

    private int getPort(){
        return serverPort;
    }

    private String getServerName(){

        return serverName;
    }

    private InetAddress getIp() throws Exception{
        ip = InetAddress.getLocalHost();
        return ip;
    }

    private List connectedClients(){

      return clientList;
    }


}