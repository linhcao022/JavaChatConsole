package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.security.KeyPair;

import static com.company.Asymmetric.generateRSAKkeyPair;

public class Server {
    public static void main(String[] args) {
        DataInputStream din = null;
        ServerSocket serverSocket = null;
        DataOutputStream dout = null;
        BufferedReader br = null;
        try
        {
            KeyPair keypair = generateRSAKkeyPair();
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("KeyFile.txt"));
            out.writeObject(keypair);
            out.close();
            /*
             * Creates a server socket, bound to the specified port.
             */
            serverSocket = new ServerSocket(8088);
            System.out.println("Server is waiting for client request... ");

            /*
             * Listens for a connection to be made to this socket and
             * accepts it. The method blocks until a connection is
             * made.
             */
            Socket socket = serverSocket.accept();
            din = new DataInputStream(socket.getInputStream());

            OutputStream outputStream = socket.getOutputStream();
            dout = new DataOutputStream(outputStream);

            br = new BufferedReader(new InputStreamReader(System.in));
            String strFromClient = "", strToClient = "";
            while (!strFromClient.equals("stop"))
            {
                InetAddress ip;
                ip = InetAddress.getLocalHost();
                strFromClient = din.readUTF();
                System.out.println(ip+ " said: " + strFromClient);
                strToClient = br.readLine();
                dout.writeUTF(strToClient);
                dout.flush();
            }
        }
        catch (Exception exe)
        {
            exe.printStackTrace();
        }
        finally
        {
            try
            {
                if (br != null)
                {
                    br.close();
                }

                if (din != null)
                {
                    din.close();
                }

                if (dout != null)
                {
                    dout.close();
                }
                if (serverSocket != null)
                {
                    /*
                     * closes the server socket.
                     */
                    serverSocket.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
