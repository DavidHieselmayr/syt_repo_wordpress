package echoclient;

import java.io.*;
import java.net.*;
import java.util.Scanner;
 
public class EchoClient {
    public static void main(String[] args) throws IOException {
         
        Socket echoServerSocket = null;
        String hostName = "127.0.0.1";
        int portNumber = 6014;
 
        try {
            echoServerSocket = new Socket(hostName, portNumber);
            PrintWriter outToServer = new PrintWriter(echoServerSocket.getOutputStream(), true);
            Scanner inFromServer = new Scanner(echoServerSocket.getInputStream());
            Scanner stdIn = new Scanner(System.in);
            
            String exitMessage = "x";
            System.out.println("Enter a line for sending to the EchoServer ...");
            String userInput;
            System.out.println(inFromServer.nextLine());
            while ((userInput = stdIn.nextLine()) != null) {
                outToServer.println(userInput);
                System.out.println(inFromServer.nextLine());
                if(userInput.equalsIgnoreCase(exitMessage)){
                    break;
                }
            }
            
            echoServerSocket.close();

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        } finally {
            if (echoServerSocket != null) {
                try {
                    echoServerSocket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
