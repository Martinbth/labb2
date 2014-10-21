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
    private Socket clientSocket;


    public StreamDread(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        //Sätter en klocka som tråden kan använda
        Date date = new Date();
        int unixTime = (int) (System.currentTimeMillis() / 1000L);
        date.setTime((long) unixTime * 1000);

        try {
            BufferedInputStream in = new BufferedInputStream(clientSocket.getInputStream());
            String out = new String();
            String client = new String();
            //Så länge servern är uppe lyssnar den på in och ut data
            while (true) {
                byte[] receiveData = new byte[1024];
                in.read(receiveData);
                PDU inPDU = new PDU(receiveData, receiveData.length);
                //Beroende vilken operation kod man får in hanterar man den särskilt för sig

                switch ((int) inPDU.getByte(0)) {
                    case OpCodes.NICKS:
                        int nrOfNicks = inPDU.getByte(1);
                        int nicksLength = inPDU.getShort(2);
                        String nicks = new String(inPDU.getSubrange(4, nicksLength));
                        //ArrayList list= new ArrayList ();
                       // list.add(nicks.split("\0"));
                        String[] parts=nicks.split("\0");
                        System.out.println("Number of connected Clients: " + nrOfNicks);
                        System.out.println("Length of the list: " + nicksLength);

                        for (int i=0; i<nrOfNicks; i++) {
                            System.out.println("List: " +parts[i]);
                        }
                        break;



                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
