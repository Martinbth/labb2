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
}
