import java.io.UnsupportedEncodingException;

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

    public static PDU join(String nickName) throws Exception {


        PDU pdu = new PDU(4+nickName.length());
        pdu.setByte(0,(byte)OpCodes.JOIN);
        pdu.setShort(1,(short)Client.div4(nickName.length()));

        pdu.setSubrange(4, nickName.getBytes());

        return pdu;
    }
}
