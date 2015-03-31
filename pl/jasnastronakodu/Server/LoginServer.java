package pl.jasnastronakodu.Server;

import pl.jasnastronakodu.Repositories.Account;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class LoginServer extends Thread {

    private static boolean checkLoginAndPassword(String login, String password) {
        System.out.println(login);
        System.out.println(password);
        File usernameFile = new File(".\\src\\pl\\jasnastronakodu\\Server\\Accounts\\" + login);
        if (usernameFile.exists()) {
            try {
                Account account;
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(usernameFile));
                account = (Account) ois.readObject();
                ois.close();
                return account.getPassword().equals(password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;

    }


    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(100);

            while (true) {
                Socket socket = ss.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String messageString = in.readLine();
                System.out.println("Odebralem: " + messageString);
                String[] loginAndPassword = messageString.split(" ");
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                if (loginAndPassword.length == 2) {
                    if (checkLoginAndPassword(loginAndPassword[0], loginAndPassword[1]))
                        out.writeBoolean(true);
                    else
                        out.writeBoolean(false);
                } else
                    out.writeBoolean(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
