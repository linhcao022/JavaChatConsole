package com.company;

import java.io.*;
import java.net.Socket;
import java.security.KeyPair;

public class Client {

    public static void main(String[] args) {
        Socket socket = null;
        DataInputStream din = null;
        DataOutputStream dout = null;
        BufferedReader br = null;

        try {
            /*
             * Creates a stream socket and connects it to the
             * specified port number at the specified IP address.
             */
            socket = new Socket("localhost", 8088);
            din = new DataInputStream(socket.getInputStream());
            System.out.println(din);

            // -Read the key from the file generated by the server.
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("KeyFile.txt"));
            KeyPair keypair = (KeyPair) in.readObject();
            in.close();
            /*
             * returns the OutputStream attached with this socket.
             */
            OutputStream outputStream = socket.getOutputStream();
            dout = new DataOutputStream(outputStream);
            System.out.println(dout);
            br = new BufferedReader(new InputStreamReader(System.in));

            String strFromServer = "", strToClient = "";
            while (!strFromServer.equals("stop")) {
                strFromServer = br.readLine();
                dout.writeUTF(strFromServer);
                dout.flush();
                strToClient = din.readUTF();
                System.out.println("Server said: " + strToClient);
            }

        } catch (Exception exe) {
            exe.printStackTrace();
        } finally {
            try {

                if (br != null) {
                    br.close();
                }

                if (din != null) {
                    din.close();
                }

                if (dout != null) {
                    dout.close();
                }
                if (socket != null) {
                    /*
                     * closes this socket
                     */
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
