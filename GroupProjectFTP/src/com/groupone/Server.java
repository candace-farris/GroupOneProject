package com.groupone;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by Tyler on 3/31/2015.
 *
 */
public class Server extends SocketInteractions{


    private final String DEFAULT_FILE_PATH = "C:\\Users\\Tyler\\Documents\\THIS IS A TEST FOLDER FOR THE SERVER\\";
    private String FILENAME;
    private ServerSocket mServerSocket;


    /*
    Constructor will take an (int)port as argument
    and assign it to the ServerSocket() superclass ,
    establishing the ServerSocket().
    Client will wait for getClientSocket() indefinitely;
     */


    public Server(int port)throws IOException{
        setFilePath(DEFAULT_FILE_PATH);
       mServerSocket = new ServerSocket(port);

    }

 /*
 A method to wire up the getClientSocket() socket as well as the IO streams once a getClientSocket() is made.
  */
    public void wireConnection() throws IOException, ClassNotFoundException{
        System.out.println(Strings.CONNECTION_SEARCHING);

           setClientSocket(mServerSocket.accept());
        System.out.println(Strings.CONNECTION_GOOD + ": "
                + getClientSocket().getRemoteSocketAddress());


        wireIO();
        runClientListener();

    }

    /*
   A method to listen for and execute commands from the client
    */
    private void runClientListener() throws ClassNotFoundException,IOException{
        String clientInput;
        while(( clientInput = (String) getObjectInputStream().readObject()) != null) {

            String [] tokenArray = clientInput.split(" ");

            //just a test case, commands will be filled into the switch as
            //developed
            //utilizing default as echo for testing
            switch (tokenArray[0]){
                case(Strings.UPLOAD_COMMAND):
                    try{
                    System.out.println(Strings.UPLOADING_FILE + tokenArray[1]);
                    uploadFile(tokenArray[1]);}catch (FileNotFoundException e){
                        System.out.println(Strings.COMPLAIN_FILE_NOT_FOUND);
                    }
                    break;
                case(Strings.TEARDOWN_COMMAND):
                    try {
                        teardown();
                        wireConnection();
                    }catch (IOException e){
                        System.out.println(Strings.COMPLAIN_TEARDOWN);
                        e.printStackTrace();
                        break;
                }
                    break;
                default:
                    System.out.println("The client says " + clientInput);
                    getObjectOutputStream().writeObject("echo: " + clientInput);

            }
        }
    }


    /*
    --------------------------GETTERS AND SETTERS------------------------------
     */

    public ServerSocket getServerSocket() {
        return mServerSocket;
    }


}
