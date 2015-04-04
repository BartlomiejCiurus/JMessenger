package pl.jasnastronakodu.Client;

import net.miginfocom.swing.MigLayout;
import pl.jasnastronakodu.Repositories.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


class ConversationScreen extends JFrame {

    private final String username;
    private final String contactName;

    private final JTextArea conversationArea = new JTextArea(10, 43);
    private final JTextArea sendMessageArea = new JTextArea(5, 43);

    public ConversationScreen(String username, String contactName) {
        super("Conversation with " + contactName);
        setLayout(new MigLayout());
        this.username = username;
        this.contactName = contactName;
        setSize(new Dimension(500, 350));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getConversationArea().setEditable(false);
        getConversationArea().setLineWrap(true);
        getConversationArea().setWrapStyleWord(true);
        getConversationArea().append(getHistory());
        getSendMessageArea().setLineWrap(true);
        getSendMessageArea().setWrapStyleWord(true);
        JScrollPane conversationAreaScroll = new JScrollPane(getConversationArea());
        JScrollPane sendMessageAreaScroll = new JScrollPane(getSendMessageArea());
        JButton sendMessageButton = new JButton("Send message");
        sendMessageButton.addActionListener(new SendMessageButtonListener());
        add(conversationAreaScroll, "wrap");
        add(new JLabel(" "), "wrap");
        add(sendMessageAreaScroll, "wrap");
        JButton sendFileButton = new JButton("Send file");
        add(sendFileButton, "split, align right");
        add(sendMessageButton);
        setResizable(false);
        setVisible(true);
    }

    private String getHistory() {
        try {
            Socket socket = new Socket("localhost", 106);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println(getUsername() + " " + getContactName());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            String message = (String) inputStream.readObject();
            writer.close();
            inputStream.close();
            return message;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUsername() {
        return username;
    }

    public String getContactName() {
        return contactName;
    }

    public JTextArea getConversationArea() {
        return conversationArea;
    }

    public JTextArea getSendMessageArea() {
        return sendMessageArea;
    }

    private class SendMessageButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String message = getSendMessageArea().getText();
            getSendMessageArea().setText("");
            String formattedMessage = getMessageText(message);
            if (saveMessageInHistory(formattedMessage))
                getConversationArea().append(formattedMessage);
            else
                JOptionPane.showMessageDialog(new JFrame(), "Network error!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        private boolean saveMessageInHistory(String message) {
            try {
                Socket socket = new Socket("localhost", 105);
                Message messageObject = new Message(getUsername(), getContactName(), message);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(messageObject);
                outputStream.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        private String getMessageText(String message) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String result = getUsername();
            result += " " + dateFormat.format(new Date()) + "\n" + message + "\n";
            result += "---------------------------------------------------------------------------------\n";
            return result;
        }
    }

}
