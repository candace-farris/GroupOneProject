package com.groupone;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Tyler on 4/28/2015.
 *
 *
 * This class is going to run as a shell for the rest of the program. It will designate the instance as either a Server or a Client, and then it will collect commands and pass them to their respective
 * instance.
 */
public class CommandLine {
    boolean isServer;
    private Server mServer;
    private Socket mSocket;
    private int port;
    private String hostName;


    private void isServer(int port)throws IOException{
       mServer = new Server(port);
    }

}
