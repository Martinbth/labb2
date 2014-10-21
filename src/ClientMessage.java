import java.io.*;
import java.net.*;
import java.security.*;

/**
 * Created by martinlundquist on 14-10-21.
 */

public class ClientMessage {


    protected PrintStream sendToSerber;
    protected Socket recServerSocket;

    /*public void say(){
    System.out.print("You say: ");
    BufferedReader say = new BufferedReader(new InputStreamReader(System.in));
    setMessage(say);
    }*/
    public static void main(String args){

    }
    public void toSerb(){
        try {
            sendToSerber = new PrintStream(recServerSocket.getOutputStream(), true);
            sendToSerber.write(inStrToPdu().getBytes());
        }
        catch (IOException ET){
            System.out.println("Couldn't deliver to either Host or Port");
        }
    }

    public static String setMessage(){
        System.out.print("You talk: ");
        BufferedReader inputBuff= new BufferedReader(new InputStreamReader(System.in));
        String inToStr = inputBuff.toString();
        return inToStr;
    }

    public PDU inStrToPdu(){
        PDU strToPDU = pduHandler.stringToMsg(setMessage());
        toSerb();
        return strToPDU;
    }
}
