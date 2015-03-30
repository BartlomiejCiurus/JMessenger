package pl.jasnastronakodu.Client;

import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;

class LoginScreen extends JFrame {

    private final JTextField loginField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);


    public LoginScreen() throws IOException {
        super("Login Screen");
        setLayout(new MigLayout("", "[center][right][left]", "[top][center][b]"));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(600, 360));
        setLocationRelativeTo(null);
        setResizable(false);
        JButton loginButton = new JButton("Login");
        ActionListener loginButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!loginField.getText().equals("") && passwordField.getPassword().length != 0) {
                    if (checkLoginAndPassword(loginField.getText(), String.valueOf(passwordField.getPassword()))) {
                        String login = loginField.getText();
                        String password = String.valueOf(passwordField.getPassword());
                        setVisible(false);
                        dispose();
                        new MessengerMainScreen(login, password);
                    } else {
                        JFrame jFrame = new JFrame();
                        JOptionPane.showMessageDialog(jFrame, "Wrong username or password", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JFrame jFrame = new JFrame();
                    JOptionPane.showMessageDialog(jFrame, "Enter your username and password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        loginButton.addActionListener(loginButtonListener);
        JButton createAccountButton = new JButton("Create account");
        ActionListener createAccountButtonListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateAccountScreen();
            }

        };
        createAccountButton.addActionListener(createAccountButtonListener);
        add(new JLabel(new ImageIcon(ImageIO.read(new URL("http://code-side.byethost17.com/images/NewLogo.png")))), "wrap");
        add(new JLabel(" "), "wrap,grow");
        add(new JLabel("Login:"), "split, gapright 31");
        add(loginField, "wrap");
        add(new JLabel("Password:"), "split");
        add(passwordField, "wrap");
        add(new JLabel(" "), "wrap,grow");
        add(createAccountButton, "split");
        add(loginButton);
        setVisible(true);
    }

    private boolean checkLoginAndPassword(String login, String password) {
        try {
            System.out.println("Connecting...");
            Socket socket = new Socket("localhost", 100);
            System.out.println("Connected");
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(login + " " + password);
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            boolean result = inputStream.readBoolean();
            System.out.println(result);
            if (result)
                return true;
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


}
