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

    public static PDU stringToMsg(String jamaica){
        PDU pdu = new PDU(12 + jamaica.length());
        pdu.setByte(0,(byte)OpCodes.MESSAGE);
        pdu.setShort(4, (short)Client.div4(jamaica.length()));
        pdu.setSubrange(12, jamaica.getBytes());
        return pdu;
    }

}
