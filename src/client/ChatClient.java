package client;

import server.ChatServer;
import exceptions.LoginTakenException;

public class ChatClient {
    private final ChatClientView view;
    private final ChatServer server;
    private final ChatClient instance;


    public ChatClient(ChatClientView view, ChatServer server) {
        instance = this;
        this.view = view;
        this.server = server;
    }

    public void sendMessageToServer(String login,String message) {
        server.receiveMessage(login, message);
    }

    public boolean authorize(String login, int passwordHash) throws LoginTakenException {
        return server.authorize(instance, login, passwordHash);
    }

    public void receiveMessageFromServer(String message) {
        view.updateMessages(message);
    }

    public String loadHistory() {
        return server.loadMessages();
    }

    public void disconnect() {
        view.disconnectAction();
    }
}
