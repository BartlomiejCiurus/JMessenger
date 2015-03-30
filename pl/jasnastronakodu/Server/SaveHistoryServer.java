package pl.jasnastronakodu.Server;

import pl.jasnastronakodu.Repositories.Account;
import pl.jasnastronakodu.Repositories.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Karpatq on 2015-03-01.
 */
class SaveHistoryServer extends Thread {

    private ServerSocket serverSocket;

    SaveHistoryServer() {
        try {
            serverSocket = new ServerSocket(105);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) inputStream.readObject();
                File historyFile = new File(Account.historyFileName(message.getReceiverName(), message.getSenderName()));
                PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(historyFile, true)));
                out.print(message.getMessage());
                inputStream.close();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
