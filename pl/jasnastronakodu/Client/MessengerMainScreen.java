package pl.jasnastronakodu.Client;

import net.miginfocom.swing.MigLayout;
import pl.jasnastronakodu.Repositories.Account;
import pl.jasnastronakodu.Repositories.ImageListCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

class MessengerMainScreen extends JFrame {

    private final String username;

    private final JList list;
    private final DefaultListModel model;


    public MessengerMainScreen(final String username, String password) {
        super("JMessenger");
        this.username = username;
        String password1 = password;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(300, 500);
        JMenu account = new JMenu("Account");
        JMenu contacts = new JMenu("Contacts");
        JMenuItem addContact = new JMenuItem("Add contact");
        ActionListener addContactListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddContactScreen(username);
            }
        };
        addContact.addActionListener(addContactListener);
        JMenuItem logOut = new JMenuItem("Log out");
        ActionListener logOutListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new LoginScreen();
                    setVisible(false);
                    dispose();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        };
        logOut.addActionListener(logOutListener);
        JMenuItem deleteContact = new JMenuItem("Delete contact");
        ActionListener deleteContactListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!list.isSelectionEmpty()) {
                    try {
                        String deletedContact = (String) list.getSelectedValue();
                        Socket socket = new Socket("localhost", 104);
                        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                        writer.println(username + " " + deletedContact);
                        model.removeElement(list.getSelectedValue());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else
                    JOptionPane.showMessageDialog(new JFrame(), "Select contact to delete", "Error", JOptionPane.ERROR_MESSAGE);
            }
        };
        deleteContact.addActionListener(deleteContactListener);
        account.add(logOut);
        JMenuItem changePassword = new JMenuItem("Change password");
        account.add(changePassword);
        contacts.add(addContact);
        contacts.add(deleteContact);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(account);
        menuBar.add(contacts);
        setJMenuBar(menuBar);
        model = insertArrayToModel(getContacts());
        list = new JList(model);
        list.setCellRenderer(new ImageListCellRenderer());
        MouseAdapter listListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                if (evt.getClickCount() == 2) {
                    new ConversationScreen(username, (String) list.getSelectedValue());
                }
            }
        };
        list.addMouseListener(listListener);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setFixedCellHeight(40);
        add(new JScrollPane(list));
        JScrollPane pane = new JScrollPane();
        pane.getViewport().add(list);
        add(list);
        this.setLocationRelativeTo(null);
        setVisible(true);
    }

    DefaultListModel insertArrayToModel(String[] array) {
        if (array.length < 1)
            return new DefaultListModel();
        DefaultListModel model = new DefaultListModel();
        for (String s : array) {
            model.addElement(s);
        }
        return model;
    }

    String[] getContacts() {
        try {
            Socket socket = new Socket("localhost", 102);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println(username);
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            Account account = (Account) inputStream.readObject();
            String[] contacts = account.getContacts().toArray(new String[account.getContacts().size()]);
            writer.close();
            inputStream.close();
            return contacts;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    class AddContactScreen extends JFrame {

        private final JTextField contactNameField = new JTextField(15);
        private final JButton addContactButton = new JButton("Add contact");

        private final String username;

        private final ActionListener addContactButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Socket socket = new Socket("localhost", 103);
                    PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                    if (!contactNameField.getText().isEmpty()) {
                        writer.println(username + " " + contactNameField.getText());
                        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                        boolean result = inputStream.readBoolean();
                        if (result) {
                            JOptionPane.showMessageDialog(new JFrame(), "Contact added");
                            inputStream.close();
                            model.addElement(contactNameField.getText());
                            setVisible(false);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(new JFrame(), "There is no " + contactNameField.getText() + " contact name in data base.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(new JFrame(), "Enter your contact name", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    writer.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        };

        AddContactScreen(String username) {
            super("Add contact screen");
            this.username = username;
            setLayout(new MigLayout("", "[center][right][left]", "[top][center][b]"));
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            setSize(new Dimension(300, 130));
            addContactButton.addActionListener(addContactButtonListener);
            add(new JLabel("Contact name"), "split");
            add(contactNameField, "wrap");
            add(new JLabel(" "), "wrap");
            add(addContactButton, "align right");
            setVisible(true);
        }

    }
}


