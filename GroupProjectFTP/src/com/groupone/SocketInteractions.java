package groupone;

/**
 *
 * @author Candace
 */
import java.io.*;
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
   Wire input and output streams using ObjectInputStream() and ObjectOutputStream().
Chose to use these because they are more general and can handle most types of IO.
I think this will also allow us to pass classes and methods across the stream, which may
be very useful... not 100% on that though.
    https://docs.oracle.com/javase/7/docs/api/java/io/ObjectInputStream.html
    http://docs.oracle.com/javase/7/docs/api/java/io/ObjectOutputStream.html
     */




    public void wireIO() throws IOException {
        mObjectOutputStream = new ObjectOutputStream(getClientSocket().getOutputStream());
        mObjectInputStream = new ObjectInputStream(getClientSocket().getInputStream());
        mObjectOutputStream.flush();


        /*
        A method to execute the basic file transfer functionality
        builds and fills a buffer and reads it to the methods output stream
        which will be either a filewriter or a socket's output stream.
        This method will provide the essential functionality for the upload and downlaod methods
         */
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException{
        byte[] buffer = new byte[8192]; //2^13 bytes.
        int count;
        while ((count = in.read(buffer)) != -1) {
            out.write(buffer, 0, count);
        }
        out.flush();
        System.out.println("end of copy file");

    }

    public void downloadFile(String fileName) throws IOException{
        copyFile(mObjectInputStream, new FileOutputStream(FILEPATH+fileName));
    }

    public void uploadFile(String fileName) throws IOException{
        copyFile(new FileInputStream(FILEPATH+fileName), mObjectOutputStream);
    }

    /*
    Method closes the server and client sockets and closes the program.
    */
    private void closeProgram() throws IOException{
        clientSocket.close();
       //server socket
        System.out.println("Closing server and client sockets...");
        System.out.println("\nThe server and client have been closed. Thank you.");
        int status = 0;
        Runtime.getRuntime().exit(status); //Terminates currently running program
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
