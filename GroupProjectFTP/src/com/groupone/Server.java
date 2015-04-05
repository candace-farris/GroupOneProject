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
       System.out.println("Server port number: " + this.getServerSocket().getLocalPort());

    }

 /*
 A method to wire up the getClientSocket() socket as well as the IO streams once a getClientSocket() is made.
  */
    public void wireConnection() throws IOException, ClassNotFoundException{
        System.out.println("Waiting for getClientSocket()...");

           setClientSocket(mServerSocket.accept());
        System.out.println("Just connected to "
                + getClientSocket().getRemoteSocketAddress());


        wireIO();
        System.out.println("Streams all wired up.");
            runClientListener();

    }
    /*
   A method to listen for commands from the client
    */
    private void runClientListener() throws ClassNotFoundException,IOException{
        System.out.println("Server is Listening");
        String clientInput;
        while(( clientInput = (String) getObjectInputStream().readObject()) != null) {

            String [] tokenArray = clientInput.split(" ");

            //just a test case, commands will be filled into the switch as
            //developed
            //utilizing default as echo for testing
            switch (tokenArray[0]){
                case("test"):
                    System.out.println("test heard");
                    getObjectOutputStream().writeObject("test success");
                    break;
                case("upload"):
                    try{
                    System.out.println("The server is uploading the file " + tokenArray[1]);
                    uploadFile(tokenArray[1]);}catch (FileNotFoundException e){
                        System.out.println("The server could not find the file +" +tokenArray[1] + " in the provided directory. " +getFilePath());
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
