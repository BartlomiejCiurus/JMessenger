package pl.jasnastronakodu.Client;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

class CreateAccountScreen extends JFrame {

    private final JTextField usernameField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);
    private final JPasswordField repeatPasswordField = new JPasswordField(15);


    public CreateAccountScreen() {
        super("Create Account Screen");
        setLayout(new MigLayout("", "[center][right][left]", "[top][center][b]"));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(320, 190));
        setLocationRelativeTo(null);
        setResizable(false);
        JButton createAccountButton = new JButton("Create Account");
        ActionListener createAccountButtonListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkFieldsAreNotEmpty()) {
                    if (checkPasswordsSame()) {
                        try {
                            System.out.println("Connecting...");
                            Socket socket = new Socket("localhost", 101);
                            System.out.println("Connected");
                            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                            out.println(usernameField.getText() + " " + new String(passwordField.getPassword()));
                            DataInputStream in = new DataInputStream(socket.getInputStream());
                            boolean result = in.readBoolean();
                            if (!result) {
                                JFrame jFrame = new JFrame();
                                JOptionPane.showMessageDialog(jFrame, "Username already taken", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                JFrame jFrame = new JFrame();
                                JOptionPane.showMessageDialog(jFrame, "Account created!");
                                setVisible(false);
                                dispose();
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }

        };
        createAccountButton.addActionListener(createAccountButtonListener);
        add(new JLabel("Username:"), "split, align left, gapright 51");
        add(usernameField, "wrap, ");
        add(new JLabel("Password:"), "split, align left, gapright 52");
        add(passwordField, "wrap, align left");
        add(new JLabel("Confirm password:"), "split, align left");
        add(repeatPasswordField, "wrap, align left");
        add(new JLabel(" "), "wrap,grow, align left");
        add(createAccountButton, "align right");
        setVisible(true);
    }

    boolean checkPasswordsSame() {
        if (Arrays.equals(passwordField.getPassword(), repeatPasswordField.getPassword()))
            return true;
        else {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "Passwords are different", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    boolean checkFieldsAreNotEmpty() {
        if (!usernameField.getText().isEmpty()
                && (passwordField.getPassword().length > 0)
                && (repeatPasswordField.getPassword().length > 0))
            return true;
        else {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "You have to fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }


}
