import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by martinlundquist on 14-10-23.
 */
public class SendDread extends Thread{
    protected Socket Socket;
    protected String userOp;
    protected byte[] byteArray;
    protected DataOutputStream out;

    public SendDread(Socket socket) throws IOException {

        this.Socket = socket;
       }



    public  void run() {
        try {
            out = new DataOutputStream(Socket.getOutputStream());

            byte[] byteArray;

            BufferedReader nickIn = new BufferedReader(new InputStreamReader(System.in));
            String nickName;
            nickName=nickIn.readLine();
            PDU pduNick = pduHandler.join(nickName);
            //byteArray = pduNick.getBytes();
            //out.write(byteArray);
            out.write(pduNick.getBytes());

            //De val användaren kan göra på servern skrivs ut
            System.out.println("Enter /help for avaliable commands");

            while (true) {

                userOp = new String();
                BufferedReader kIn = new BufferedReader(new InputStreamReader(System.in));
                userOp = kIn.readLine();


                //beroende på vilken operation användaren väljer
                if (userOp.equals("/chnick")) {
                    BufferedReader chnick = new BufferedReader(new InputStreamReader(System.in));
                    System.out.println("Enter your new beloved nickname:");
                    String ChNick = chnick.readLine();
                    PDU chnickPDU = pduHandler.chnick(ChNick);
                    byteArray = chnickPDU.getBytes();
                    out.write(byteArray);
                } else if (userOp.equals("/help")) {
                    System.out.println("Commands:\n /chnick\n /leave\n /help");

                } else if (userOp.equals("/leave")) {
                    PDU quitPDU = pduHandler.quit();
                    byteArray = quitPDU.getBytes();
                    out.write(byteArray);
                    System.out.println("You left the server, good for you");
                } else {
                    // ClientMessage.toSerb(userOp);
                    try {
                        PDU msgPDU = pduHandler.message(userOp);
                        msgPDU.setByte(3, (byte) 0);
                        msgPDU.setByte(3, Checksum.calc(msgPDU.getBytes(), msgPDU.length()));
                        short checkSum = (byte) msgPDU.getByte(3);
                        msgPDU.setByte(3, (byte) checkSum);
                        byteArray = msgPDU.getBytes();


                        out.write(byteArray);
                    }catch (Exception ET){
                        ET.printStackTrace();
                    }
                }
            }
        }
        catch (IOException e){
        e.printStackTrace();
    }
    }
}