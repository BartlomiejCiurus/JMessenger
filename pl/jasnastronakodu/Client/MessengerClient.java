package pl.jasnastronakodu.Client;

import javax.swing.*;
import java.io.IOException;


class MessengerClient {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new LoginScreen();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(new JFrame(), "Connection error :(", "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        });
    }
}
