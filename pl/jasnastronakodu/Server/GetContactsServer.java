package pl.jasnastronakodu.Server;


import pl.jasnastronakodu.Repositories.Account;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class GetContactsServer extends Thread {

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(102);

            while (true) {
                Socket socket = ss.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String messageString = in.readLine();
                Account account = getAccount(messageString);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(account);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Account getAccount(String username) {
        FileInputStream fis;
        ObjectInputStream ois;
        try {
            fis = new FileInputStream(".\\src\\com\\byethost17\\codeside\\Server\\Accounts\\" + username);
            ois = new ObjectInputStream(fis);
            Account account = (Account) ois.readObject();
            fis.close();
            ois.close();
            return account;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
