import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by martinlundquist on 14-10-20.
 */
public class ClientNfo {
    private Socket clientSocket;
    private String userName;
    private InetAddress ip;
    private int tcpPort;
    private int time;
    private boolean connection = false;

    public ClientNfo(Socket s){
        this.setClientSocket(s);
    }

    public void setUserName(String nick) throws UnsupportedEncodingException {
        this.userName = nick;
    }
    public String getUserName(){
        return userName;
    }

    public void setTime(int time){
        this.time = time;
    }
    public int getTime(){
        return time;
    }

    public void setIpAdress(java.net.InetAddress ip) {
        this.ip = ip;
    }
    public InetAddress getIpAdress(){
        return ip;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void closeClientSocket() throws IOException {
        clientSocket.close();
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    public void setTcpPort(int p){
        tcpPort = p;
    }
    public int getTcpPort(){
        return tcpPort;
    }
    public void setConnection(boolean s){
        connection = s;
    }
    public boolean getConnection(){
        return connection;
    }
}