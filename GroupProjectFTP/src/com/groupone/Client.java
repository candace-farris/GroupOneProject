package com.groupone;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Tyler on 4/4/2015.
 * Here is the client
 */
public class Client extends SocketInteractions {

    private final String DEFAULT_FILE_PATH = "C:\\Users\\Tyler\\Documents\\THIS IS A TEST FOLDER FOR THE CLIENT\\";


    public Client() throws IOException,  ClassNotFoundException{
        //We will eventually push socket IP and port assignment to a method buildConnection() to be executed by
        //the CommandLine. For now it is hard wired for ease of use.
       // setClientSocket(new Socket("localhost", 81));
        setFilePath(DEFAULT_FILE_PATH);



       // wireIO();

        runCommandLine();


    }
    /*
    a method which sets up a socket which seeks a server on a given ip and port
     */
    private void connect(String ip, int port) throws IOException{
        setClientSocket(new Socket(ip, port));
        System.out.println(Strings.CONNECTION_SEARCHING);
        System.out.println(Strings.CONNECTED
                + getClientSocket().getRemoteSocketAddress());
    }
    /*
    The commandline is going to take a shared group of enum COMMANDS()
    as commands and run them through a switch statement
    The switch statement will execute methods associate to specific commands

    **** prefix-command style? i.e. (Command: echo "this is what you should echo") would execute echo
     *command on server side.
     */
    private void runCommandLine() throws IOException, ClassNotFoundException{
        BufferedReader stdIn = new BufferedReader(
                new InputStreamReader(System.in));
        String[] tokenArray;
        String userInput;


        System.out.println(Strings.COMMAND_LINE_WELCOME);
        System.out.print(Strings.COMMAND_HOLD);
        while((userInput = stdIn.readLine().toLowerCase()) !=  null){
            tokenArray = userInput.split(" ");

            switch (tokenArray[0]){
                case (Strings.CONNECT_COMMAND):
                    try {
                        if(checkConnectInput(tokenArray)){
                        connect(tokenArray[1], Integer.parseInt(tokenArray[2]));
                        wireIO();}
                    }catch (IOException e){
                        System.out.println(Strings.COMPLAIN_NO_SERVER +tokenArray[1] +" Port: "+ tokenArray[2]);
                    }catch (NumberFormatException e){
                        System.out.println(Strings.COMPLAIN_COMMAND_FORMAT + Strings.CONNECT_FORMAT);
                    }
                    break;

                case(Strings.TEARDOWN_COMMAND):{
                    try{
                    getObjectOutputStream().writeObject(Strings.TEARDOWN_COMMAND);
                    }catch (IOException e){
                        System.out.println(Strings.COMPLAIN_TEARDOWN);
                        e.printStackTrace();
                        break;
                    }catch (NullPointerException e){
                        System.out.println(Strings.COMPLAIN_NO_CONNECTION);
                        break;
                    }
                    teardown();
                    break;
                }
                case(Strings.DOWNLOAD_COMMAND):
                    try {

                    System.out.println(Strings.DOWNLOAD_HAPPENING + tokenArray[1]);
                    getObjectOutputStream().writeObject(Strings.UPLOAD_COMMAND+ " " + tokenArray[1]);
                    downloadFile(tokenArray[1]);}
                    catch (FileNotFoundException e){
                        System.out.println(Strings.COMPLAIN_FILE_NOT_FOUND);
                    }catch (NullPointerException e){
                        System.out.println(Strings.COMPLAIN_NO_CONNECTION);
                        break;
                    }
                    break;

                default:
                    try {
                        getObjectOutputStream().writeObject(userInput);
                        System.out.println(getObjectInputStream().readObject());}
                    catch (NullPointerException e){
                        System.out.println(Strings.COMPLAIN_NO_CONNECTION);
                        break;
                    }
                    break;

            }
            System.out.print(Strings.COMMAND_HOLD);
        }
    }
    /*
    A method to check if the connect command is formatted correctly

    Currently incomplete -- finish this.
    it needs more verifiucation steps.
     */
    private boolean checkConnectInput(String[] stringArray){
        if (stringArray.length!=3){
            System.out.println();
            return false;
        }else return true;
    }


}
