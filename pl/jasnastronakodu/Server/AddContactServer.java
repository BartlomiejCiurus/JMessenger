package pl.jasnastronakodu.Server;

import pl.jasnastronakodu.Repositories.Account;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Karpatq on 2015-02-23.
 */
class AddContactServer extends Thread {

    private ServerSocket serverSocket;

    public AddContactServer() {
        try {
            serverSocket = new ServerSocket(103);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String name = in.readLine();
                String[] usernameAndContact = null;
                if (name.contains(" "))
                    usernameAndContact = name.split(" ");
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                assert usernameAndContact != null;
                File file = new File(".\\src\\pl\\jasnastronakodu\\Server\\Accounts\\" + usernameAndContact[1].trim());
                if (file.exists()) {
                    File usernameFile = new File(".\\src\\pl\\jasnastronakodu\\Server\\Accounts\\" + usernameAndContact[0]);
                    ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(usernameFile));
                    Account acc = (Account) objectInputStream.readObject();
                    acc.addContact(usernameAndContact[1]);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(usernameFile));
                    objectOutputStream.writeObject(acc);
                    dataOutputStream.writeBoolean(true);
                    objectInputStream.close();
                    objectOutputStream.close();
                } else {
                    dataOutputStream.writeBoolean(false);
                }
                in.close();
                dataOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}
