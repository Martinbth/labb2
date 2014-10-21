import java.io.*;
import java.net.*;
import java.security.*;

/**
 * Created by martinlundquist on 14-10-21.
 */

public class ClientMessage {


    protected static DataOutputStream sendToSerber;
    protected static Socket recServerSocket;


    public static void toSerb(String o){
        try {
            sendToSerber = new DataOutputStream(recServerSocket.getOutputStream());
            sendToSerber.write(inStrToPdu(o).getBytes());

        }
        catch (IOException ET){
            System.out.println("Couldn't deliver to either Host or Port");
        }
    }

    public static PDU inStrToPdu(String j){
        byte[] byteArray = new  byte[1024];
        PDU strToPDU = pduHandler.stringToMsg(j);
        byteArray = strToPDU.getBytes();

        return strToPDU;
    }
}
