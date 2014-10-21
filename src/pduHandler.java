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
        byte[] Pad = new byte[]{0, 0, 0};
        pdu.setByte(0, (byte) OpCodes.GETLIST);
        pdu.setSubrange(1, Pad);

        return pdu;
    }
    public static PDU join(String nick) throws Exception {
        byte[] nickName = nick.getBytes("UTF-8");
        byte[] Pad = new byte[] {0, 0};
        PDU pdu = new PDU(4+nickName.length);
        pdu.setByte(0, (byte)OpCodes.JOIN);
        pdu.setByte(1, (byte)nick.getBytes("UTF-8").length);
        pdu.setSubrange(3, Pad);
        pdu.setSubrange(4, nickName);

        return pdu;
    }

    public static PDU stringToMsg(String jamaica){
        PDU pdu = new PDU(12 + jamaica.length());
        pdu.setByte(0,(byte)OpCodes.MESSAGE);
        pdu.setShort(4, (short)Client.div4(jamaica.length()));
        pdu.setSubrange(12, jamaica.getBytes());
        return pdu;
    }



}
