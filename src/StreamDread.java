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
            //&& (in.read(receiveData)) > 0

            while (true ) {
                Thread.sleep(100);
                byte[] receiveData = new byte[65507];
                //byte[] receiveData = new byte[1024];
                in.read(receiveData);
                PDU inPDU = new PDU(receiveData, receiveData.length);
                    //Beroende vilken operation kod man får in hanterar man den särskilt för sig

                    switch ((int) inPDU.getByte(0)) {
                        case OpCodes.NICKS:
                            Nicks(inPDU);
                            break;

                        case OpCodes.MESSAGE:

                            Message(inPDU,date);

                            break;

                        case OpCodes.ULEAVE:
                            ULeave(inPDU, date);

                            break;
                        case OpCodes.UJOIN:
                            UJoin(inPDU,date);

                            break;
                    }
                }
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            // TODO Auto-generated catch block
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
        System.out.println("Connected clients: " + nrOfNicks);
        System.out.println("Length of the list: " + nicksLength);

        for (int i=0; i<nrOfNicks; i++) {
            System.out.println("User: " +parts[i]);
        }
    }

    public void Message(PDU inPDU, Date date)throws Exception{

        int checkSum1;
        int checkSum2;
        String out = new String();
        checkSum1=inPDU.getByte(3);
        inPDU.setByte(3,(byte)0);
        inPDU.setByte(3, Checksum.calc(inPDU.getBytes(),inPDU.length()));
        checkSum2=inPDU.getByte(3);
        String user =new String(inPDU.getSubrange(12+inPDU.getShort(4), inPDU.getByte(2)), "UTF-8");

        if(checkSum1==checkSum2){

         out = new String(inPDU.getSubrange(12, (int) inPDU.getShort(4)), "UTF-8");
         System.out.println(date + " " + user + ": " + out);

         }

    }

    public void ULeave(PDU inPDU,Date date){
        String uleaveName = new String(inPDU.getSubrange(8, inPDU.getByte(1)));

        int uleaveTime = (int)inPDU.getInt(4);
        date.setTime((long)uleaveTime*1000);

        System.out.println(date + " User " + uleaveName.trim() + " has disconnected from the server.");
    }

    public void UJoin(PDU inPDU, Date date){
        String out = new String(inPDU.getSubrange(8, (int) inPDU.getByte(1)));
        date.setTime((long)inPDU.getInt(4)*1000);

        System.out.println(date + " " + out.trim() + " has connected.");
    }
}

