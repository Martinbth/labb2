import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
/**
 * Created by martinbaath on 14-10-21.
 */
public class StreamDread extends Thread {
    private Socket Socket;
    //private static int unixTime;


    public StreamDread(Socket socket) {

        this.Socket = socket;
    }

    public void run() {

        try {
            BufferedInputStream in = new BufferedInputStream(Socket.getInputStream());

            //Så länge servern är uppe lyssnar den på in och ut data

            while (true ) {
                Thread.sleep(100);
                byte[] receiveData=new byte[65503];
                int x;
                x =in.read(receiveData);
                byte[] receiveDatanew=new byte[x];
                for(int g = 0; g < x; g++){
                    receiveDatanew[g]=receiveData[g];
                }

                    PDU inPDU = new PDU(receiveData, receiveData.length);
                    //
                    //Beroende vilken operation kod man får in hanterar man den särskilt för sig

                    switch ((int) inPDU.getByte(0)) {
                        case OpCodes.NICKS:
                            Nicks(inPDU);
                            break;

                        case OpCodes.MESSAGE:

                            Message(inPDU);

                            break;

                        case OpCodes.ULEAVE:
                            ULeave(inPDU);

                            break;
                        case OpCodes.UJOIN:
                            UJoin(inPDU);

                            break;
                        case OpCodes.UCNICK:
                            UchNick(inPDU);
                    }
                }

        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Skriver ut en lista på alla användare som är anslutna till servern
     */
    public void Nicks(PDU inPDU){
        int nrOfNicks = inPDU.getByte(1);
        int nicksLength = inPDU.getShort(2);
        String nicks = new String(inPDU.getSubrange(4, nicksLength));
        //delar upp strängen av användare i en array
        String[] parts=nicks.split("\0");
        System.out.println("Connected clients: " + nrOfNicks);
        System.out.println("Length of the list: " + nicksLength);

        for (int i=0; i<nrOfNicks; i++) {
            System.out.println("User: " +parts[i]);
        }
        System.out.print("> ");
    }

    /**
     * Inkommande meddelanden från chatservern (Välkomstmeddelande från server och användares meddelande)
     */

    public void Message(PDU inPDU)throws Exception{
        String message;
        String user =new String(inPDU.getSubrange(12+inPDU.getShort(4), inPDU.getByte(2)), "UTF-8");
         Date date = new Date(inPDU.getInt(8)*1000);
         message = new String(inPDU.getSubrange(12,  inPDU.getShort(4)), "UTF-8");
         System.out.println(date + " " + user + ": " + message);
    }

    /**
     * Skickar ut ett meddelande om någon på servern har lämnat inkl sig själv
     */
    public void ULeave(PDU inPDU){
        String uleaveName = new String(inPDU.getSubrange(8, inPDU.getByte(1)));
        Date date = new Date(inPDU.getInt(4)*1000);


        System.out.println(date + " User: " + uleaveName.trim() + " has disconnected from the server.");
    }

    /**
     * Meddelande om en user har anslutit till servern

     */
    public void UJoin(PDU inPDU){
        String out = new String(inPDU.getSubrange(8, (int) inPDU.getByte(1)));
        Date date = new Date(inPDU.getInt(4)*1000);


        System.out.println(date + " User: " + out.trim() + " has connected.");
    }

    public void UchNick(PDU inPDU) {
        String oldNick = new String(inPDU.getSubrange(8, inPDU.getByte(1)));
        String newnick = new String(inPDU.getSubrange(8+inPDU.getByte(1), inPDU.getByte(2)));
        Date date = new Date(inPDU.getInt(4)*1000);

        System.out.println(date + " " + oldNick.trim() + " has changed name to " + newnick.trim() + ".");

    }
}