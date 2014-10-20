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

}
