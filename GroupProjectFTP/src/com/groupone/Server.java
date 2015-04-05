package com.groupone;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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


        wireServerIO();
        System.out.println("Streams all wired up.");
            runClientListener();

    }
    /*
   Wire input and output streams using ObjectInputStream() and ObjectOutputStream().
Chose to use these because they are more general and can handle most types of IO.

I think this will also allow us to pass classes and methods across the stream, which may
be very useful... not 100% on that though.

    https://docs.oracle.com/javase/7/docs/api/java/io/ObjectInputStream.html
    http://docs.oracle.com/javase/7/docs/api/java/io/ObjectOutputStream.html
     */

    private void wireServerIO() throws IOException{
        setObjectInputStream(new ObjectInputStream(getClientSocket().getInputStream()));
        setObjectOutputStream(new ObjectOutputStream(getClientSocket().getOutputStream()));
        getObjectOutputStream().flush();
    }

    /*
    A method to listen for commands from the client
     */
    private void runClientListener() throws ClassNotFoundException,IOException{
        System.out.println("Server is Listening");
        while(true) {
            String clientInput = (String) getObjectInputStream().readObject();
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
