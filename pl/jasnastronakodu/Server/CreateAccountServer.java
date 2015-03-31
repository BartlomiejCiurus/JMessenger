package pl.jasnastronakodu.Server;

import pl.jasnastronakodu.Repositories.Account;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


class CreateAccountServer extends Thread {

    private static boolean createAccount(String login, String password) {
        Account account = new Account(login, password);
        File accountFile = new File(".\\src\\pl\\jasnastronakodu\\Server\\Accounts\\" + login);
        if (accountFile.exists())
            return false;
        else {
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(new FileOutputStream(accountFile));
                oos.writeObject(account);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (oos != null)
                        oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
    }

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(101);

            while (true) {
                Socket socket = ss.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String messageString = in.readLine();
                String[] loginAndPassword = messageString.split(" ");
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                if (createAccount(loginAndPassword[0], loginAndPassword[1]))
                    outputStream.writeBoolean(true);
                else
                    outputStream.writeBoolean(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
