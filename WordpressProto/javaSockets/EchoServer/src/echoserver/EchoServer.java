package echoserver;

/**
 *
 * @author Nexo
 */
import java.net.*;
import java.io.*;
import java.util.Scanner;
 
public class EchoServer {
    public static void main(String[] args) throws IOException {        
        
        int portNumber = 6014;
        Socket clientSocket = null; 
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);               
            System.out.println("Server started on port " + portNumber);
            
            do{
            String clientName = null;    
            clientSocket = serverSocket.accept();   
            System.out.println("Connection established with  " + clientSocket.getRemoteSocketAddress());
            
            PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);              
            
            
            Scanner inFromClient = new Scanner(clientSocket.getInputStream());
            String inputLine;
            String exitUserInput = "x";
            outToClient.println("Hallo! Wie heißt du?");
            while ((inputLine = inFromClient.nextLine()) != null) {
                if(clientName == null){
                    clientName = inputLine;
                    outToClient.println("Hallo " + clientName +"!");
                }else if(inputLine.equalsIgnoreCase(exitUserInput)){
                    outToClient.println("Auf wiedersehen " + clientName +"!");
                    clientSocket.close();
                    break;
                }else{
                    System.out.println("Received >" + inputLine + "<");
                    outToClient.println(clientName + ": " + inputLine);
                }
            }
            }while(true);
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
        finally{
            if (clientSocket != null){
                try {clientSocket.close();} catch (IOException e) {}
            }
        }
    }
}
