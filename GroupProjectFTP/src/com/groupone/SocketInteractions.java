package com.groupone;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Tyler on 4/4/2015.
 *
 * Using an abstract class to encapsulate both the server and the client as they share a lot of basic functionality
 *
 */
public abstract class SocketInteractions {

    private String FILEPATH;
    private ObjectInputStream mObjectInputStream;
    private ObjectOutputStream mObjectOutputStream;
    private Socket clientSocket;

    /*
    A method to wire up the the socket's input and output streams
     */
    public void wireIO() throws IOException {
        mObjectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        mObjectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        mObjectOutputStream.flush();
        System.out.println("Client input/output wired up");

        /*
        A method to execute the basic file transfer functionality
        builds and fills a buffer and reads it to the methods output stream
        which will be either a filewriter or a socket's output stream.
        This method will provide the essential functionality for the upload and downlaod methods
         */
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException{
        byte[] buffer = new byte[8192]; //2^13 bytes.
        int count = 0;
        while ((count = in.read(buffer)) != -1) {
            out.write(buffer, 0, count);
        }
        out.flush();
        System.out.println("endo of copy file");

    }

    public void downloadFile(String fileName) throws FileNotFoundException, IOException{
        copyFile(mObjectInputStream, new FileOutputStream(FILEPATH+fileName));
    }

    public void uploadFile(String fileName) throws IOException,FileNotFoundException{
        copyFile(new FileInputStream(FILEPATH+fileName), mObjectOutputStream);
    }


    /*
    -------------------------------------GETTERS AND SETTERS------------------------------------------
     */
    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }


    public ObjectOutputStream getObjectOutputStream() {
        return mObjectOutputStream;
    }

    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        mObjectOutputStream = objectOutputStream;
    }

    public ObjectInputStream getObjectInputStream() {
        return mObjectInputStream;
    }

    public void setObjectInputStream(ObjectInputStream objectInputStream) {
        mObjectInputStream = objectInputStream;
    }

    public String getFilePath() {
        return FILEPATH;
    }

    public void setFilePath(String FILEPATH) {
        this.FILEPATH = FILEPATH;
    }


}
