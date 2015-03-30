package pl.jasnastronakodu.Repositories;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Account implements Serializable {

    private final String username;
    private final String password;
    private final List<String> contacts = new ArrayList<String>();
    private final Map<String, File> history = new Hashtable<String, File>();
    //private Properties properties;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static String historyFileName(String firstUsername, String secondUsername) {
        String historyFileName = ".\\src\\com\\byethost17\\codeside\\Server\\History\\" + firstUsername + secondUsername + ".txt";
        if (firstUsername.compareTo(secondUsername) > 0)
            historyFileName = ".\\src\\com\\byethost17\\codeside\\Server\\History\\" + secondUsername + firstUsername + ".txt";
        return historyFileName;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getContacts() {
        return contacts;
    }

    public void addContact(String newContact) {
        contacts.add(newContact);
        String historyFileName = historyFileName(this.username, newContact);
        File historyFile = new File(historyFileName);
        if (!historyFile.exists()) {
            try {
                history.put(newContact, historyFile);
                historyFile.createNewFile();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void deleteContact(String contactName) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).equals(contactName)) {
                contacts.remove(i);
                return;
            }
        }
    }


}
