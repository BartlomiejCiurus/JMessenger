package pl.jasnastronakodu.Server;

import pl.jasnastronakodu.Repositories.Account;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class GetHistoryServer extends Thread {

    private ServerSocket serverSocket;

    public GetHistoryServer() {
        try {
            this.serverSocket = new ServerSocket(106);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String[] usernameAndContactName = bufferedReader.readLine().split(" ");
                File historyFile = new File(Account.historyFileName(usernameAndContactName[0], usernameAndContactName[1]));
                BufferedReader fileReader = new BufferedReader(new FileReader(historyFile));
                String history = "";
                String temp;
                while ((temp = fileReader.readLine()) != null)
                    history += temp + "\n";
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(history);
                bufferedReader.close();
                fileReader.close();
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
