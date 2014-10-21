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
    private static int unixTime;


    public StreamDread(Socket socket) {

        this.Socket = socket;
    }

    public void run() {
        //Sätter en klocka som tråden kan använda
        Date date = new Date();
        unixTime = (int) (System.currentTimeMillis() /1000L);
        date.setTime((long) unixTime * 1000);

        try {
            BufferedInputStream in = new BufferedInputStream(Socket.getInputStream());
            String out = new String();
            String client = new String();
            //Så länge servern är uppe lyssnar den på in och ut data
           //while (true) {
                byte[] receiveData = new byte[1024];
                in.read(receiveData);
                while(receiveData.length > 0) {

                    PDU inPDU = new PDU(receiveData, receiveData.length);
                    //Beroende vilken operation kod man får in hanterar man den särskilt för sig

                    switch ((int) inPDU.getByte(0)) {
                        case OpCodes.NICKS:
                            Nicks(inPDU);
                            break;

                        case OpCodes.MESSAGE:
                            out = new String(inPDU.getSubrange(12, inPDU.getShort(4)), "UTF-8");

                            System.out.println(date + " " + client + ": " + out);
                            //Message(inPDU, client,date);
                            int time = (int) inPDU.getInt(8);
                            date.setTime((long) time * 1000);
                            break;

                    }
                }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void Nicks(PDU inPDU){
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

    }

    public void Message(PDU inPDU, String user, Date date)throws Exception{
        int checkSum1;
        int checkSum2;
        String out = new String();
        checkSum1=inPDU.getByte(3);
        //inPDU.setByte(3,(byte)0);
        inPDU.setByte(3, Checksum.calc(inPDU.getBytes(),inPDU.length()));
        checkSum2=inPDU.getByte(3);
        //user =new String(inPDU.getSubrange(12+));
        if(checkSum1==checkSum2){


         out = new String(inPDU.getSubrange(12, (int) inPDU.getShort(4)), "UTF-8");
         System.out.println(date + " " + user + ": " + out);
         }

        }

    }

