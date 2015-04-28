package com.groupone;



import java.io.IOException;
import java.net.Socket;

public class Main {
    private static final String IP_ADDRESS="localhost";
    private static final int PORT =81;

    private static Server server;
    private static Socket clientSocket;
    private static boolean CONNECTED = false;

    public static void main(String[] args)  throws IOException {

        new Thread() {
            public void run()   {
                try {
                    server = new Server(PORT);
                    server.wireConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (ClassNotFoundException e){
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread(){
            public void run(){
               try {
                   clientSocket = new Client().getClientSocket();
              }catch (IOException e){
        e.printStackTrace();
              }catch (ClassNotFoundException e){
                   e.printStackTrace();
               }
         }
        }.start();

//hello

    }

}
