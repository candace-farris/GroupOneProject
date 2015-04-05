package com.groupone;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Tyler on 4/4/2015.
 */
public class Client extends SocketInteractions {

    private final String DEFAULT_FILE_PATH = "C:\\Users\\Tyler\\Documents\\THIS IS A TEST FOLDER FOR THE CLIENT\\";


    public Client() throws IOException,  ClassNotFoundException{
        //We will eventually push socket IP and port assignment to a method buildConnection() to be executed by
        //the CommandLine. For now it is hard wired for ease of use.
        setClientSocket(new Socket("localhost", 81));
        setFilePath(DEFAULT_FILE_PATH);

        System.out.println("Client searching for server");
        System.out.println("Client just connected to "
                + getClientSocket().getRemoteSocketAddress());

        wireIO();
        System.out.println("Client input/output wired up");
        runCommandLine();


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


        System.out.println("Welcome to the CommandLine!");
        System.out.print("Command: ");
        while((userInput = stdIn.readLine().toLowerCase()) !=  null){
            tokenArray = userInput.split(" ");

            switch (tokenArray[0]){
                case("teardown"):{
                    getObjectOutputStream().writeObject("teardown");
                }
                case("download"):
                    try {

                    System.out.println("The client is downloading the file " + tokenArray[1]);
                    getObjectOutputStream().writeObject("upload " + tokenArray[1]);
                    downloadFile(tokenArray[1]);}catch (FileNotFoundException e){
                        System.out.println("The Client could not find the file: " + tokenArray[1]);
                    }
                    break;

                default:
                    getObjectOutputStream().writeObject(userInput);
                    System.out.println(getObjectInputStream().readObject());
                    break;

            }
            System.out.print("Command: ");
        }
    }


}
