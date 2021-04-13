package kz.edu.astanait.gambit_cinema.tools;

import java.util.List;

public class ListOfExceptionMessages {
    private List<String> messages;

    public ListOfExceptionMessages(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
