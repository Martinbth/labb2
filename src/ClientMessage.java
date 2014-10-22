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
        byte[] byteArray = new byte[1024];
        PDU strToPDU = pduHandler.stringToMsg(j);
        byteArray = strToPDU.getBytes();

        return strToPDU;
    }
}

    /*public void say(){
        System.out.print("You say: ");
        BufferedReader say = new BufferedReader(new InputStreamReader(System.in));
        setMessage(say);
    }
    public void toSerb(String mess){
        try {
            sendToSerber = new PrintStream(serverSocket.getOutputStream(), true);
            sendToSerber.write(inStrToPdu().getBytes());
        }catch (IOException ET){
            System.out.println("Something wrong with Host or Port");
        }
        catch (IOException ET){
            System.out.println("Couldn't deliver to either Host or Port");
        }
    }

    public String setMessage(){
        System.out.print("You talk: ");
        BufferedReader inputBuff= new BufferedReader(new InputStreamReader(System.in));
        String inToStr = inputBuff.toString();
        return inToStr;
    }

    public PDU inStrToPdu(){
        PDU strToPDU = pduHandler.stringToMsg(setMessage());
        return strToPDU;
    }
            */
