package pl.jasnastronakodu.Repositories;

import java.io.Serializable;

/**
 * Created by Karpatq on 2015-03-01.
 */
public class Message implements Serializable {

    private final String receiverName;
    private final String senderName;
    private final String message;

    public Message(String receiverName, String senderName, String message) {
        this.receiverName = receiverName;
        this.senderName = senderName;
        this.message = message;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getMessage() {
        return message;
    }
}
