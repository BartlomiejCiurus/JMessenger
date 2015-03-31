package pl.jasnastronakodu.Server;

import pl.jasnastronakodu.Repositories.Account;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


class DeleteContactServer extends Thread {

    private ServerSocket serverSocket;

    DeleteContactServer() {
        try {
            serverSocket = new ServerSocket(104);
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
                String[] usernameAndContact = name.split(" ");
                File usernameFile = new File(".\\src\\pl\\jasnastronakodu\\Server\\Accounts\\" + usernameAndContact[0]);
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(usernameFile));
                Account acc = (Account) objectInputStream.readObject();
                acc.deleteContact(usernameAndContact[1].trim());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(usernameFile));
                objectOutputStream.writeObject(acc);
                objectInputStream.close();
                objectOutputStream.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
