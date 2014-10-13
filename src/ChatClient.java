/**
 * Created by martinbaath on 2014-10-13.
 */
public class ChatClient {

public static void main(){
    public String fileName;
    public String requestMessageLine;
    public Socket connectionSocket;
    public String path;
    public int portNr;
    public int numOfBytes;
    public byte[] fileInBytes;
    public int errorCode;
    public BufferedReader inFromClient;
    public DataOutputStream outToClient;
    public FileInputStream inFile;
}
    public chatClient() throws Exception {


        ServerSocket listSocket = new ServerSocket(portNr);
        while(true) {
            try {
                connectionSocket = listSocket.accept();
                inFromClient();
            }catch(IOException e){
                System.out.println("Fuck this shit");
            }finally {
                closeSocket();
            }
        }
    }











    /*private void startServer(){
        nameServer = new Thread(new Runnable(){
            @Override
            public void run() {
                newServer =new clientThread(gui);
            }
        });
        threads.add(nameserver);
        nameserver.start();*/

    }
