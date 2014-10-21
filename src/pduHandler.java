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
    /*public static PDU join(String nickName) throws Exception {


        PDU pdu = new PDU(4+nickName.length());
        pdu.setByte(0,(byte)OpCodes.JOIN);
        pdu.setShort(1,(short)Client.div4(nickName.length()));

        pdu.setSubrange(4, nickName.getBytes());

        return pdu;
    }*/

    /* D� en klient har anslutit med PDU�n join returneras en lista med alla klienter som f�r tillf�llet
   * �r anslutna till servern. Mellan klients namn �terfinns ett null-tecken.  */
    public static PDU nicks(ArrayList<String> list, String ownName) throws UnsupportedEncodingException{
        String string = new String();
        int i;

        list.remove(ownName);
        list.add(0, ownName);

        for(i = 0; i < list.size(); i++){
            string = string + list.get(i) + '\0';
        }

        if(string.getBytes("UTF-8").length % 4 != 0){
            do{
                string += '\0';
            }while(string.getBytes("UTF-8").length % 4 != 0);
        }

        byte[] stringlength = string.getBytes("UTF-8");

        PDU pdu = new PDU(4+stringlength.length);

        pdu.setByte(0, (byte) OpCodes.NICKS);
        pdu.setByte(1, (byte) i);
        pdu.setShort(2, (short) string.getBytes("UTF-8").length);
        pdu.setSubrange(4, stringlength);

        return pdu;
    }






}
