import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by martinbaath on 14-10-19.
 */
public class pduHandler {

    /**
     * Förfrågan om en serverList från klienten
     * @return en klar pdu
     */
    public static PDU getList() {

        PDU pdu = new PDU(4);

        pdu.setByte(0, (byte) OpCodes.GETLIST);

        return pdu;
    }

    public static PDU stringToMsg(String jamaica){

        PDU pdu = new PDU(12 + jamaica.length());
        pdu.setByte(0,(byte)OpCodes.MESSAGE);
        pdu.setShort(4, (short)Client.div4(jamaica.length()));
        pdu.setSubrange(12, jamaica.getBytes());

        return pdu;
    }

    public static PDU join(String nick) throws UnsupportedEncodingException{
        if(nick.getBytes("UTF-8").length % 4 != 0){
            do{
                nick += ' ';
            }while(nick.getBytes("UTF-8").length % 4 != 0);
        }
        byte[] nickName = nick.getBytes("UTF-8");
        byte[] Pad = new byte[] {0, 0};
        PDU pdu = new PDU(4+nickName.length);
        pdu.setByte(0, (byte) OpCodes.JOIN);
        pdu.setByte(1, (byte) nick.getBytes("UTF-8").length);
        pdu.setSubrange(3, Pad);
        pdu.setSubrange(4, nickName);

        return pdu;
    }

    public static PDU chnick(String nick) throws UnsupportedEncodingException{

        byte[] nickName = nick.getBytes("UTF-8");
        PDU pdu = new PDU(4+nickName.length);
        pdu.setByte(0, (byte) OpCodes.CHNICK);
        pdu.setByte(1, (byte) nick.getBytes("UTF-8").length);
        pdu.setSubrange(4, nickName);

        return pdu;
    }

    public static PDU quit(){
        PDU pdu = new PDU(4);
        pdu.setByte(0, (byte)OpCodes.QUIT);
        return pdu;
    }

    public static PDU message(String msg) throws Exception {

        byte[] mess = msg.getBytes("UTF-8");
        //byte[] Pad = new byte[]{0, 0};
        //byte[] oldmsg = mess;
        PDU pdu = new PDU(12 + Client.div4(mess.length));

        pdu.setByte(0, (byte) OpCodes.MESSAGE);
        pdu.setByte(2, (byte) 0);
        pdu.setShort(4,(short)Client.div4(msg.length()));
        //pdu.setSubrange(6, Pad);
        pdu.setInt(8, 0);
        pdu.setSubrange(12, mess);
        pdu.setByte(3, Checksum.calc(pdu.getBytes(), pdu.length()));

        return pdu;
    }

}
