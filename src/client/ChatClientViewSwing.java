package client;

import server.ChatServer;
import exceptions.LoginTakenException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class ChatClientViewSwing extends JFrame implements ChatClientView {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JTextField login = new JTextField("login");
    private final JPasswordField password = new JPasswordField("password");
    private final JTextField serverIp = new JTextField("127.0.0.1");
    private final JTextField serverPort = new JTextField("9100");
    private final JTextArea messages = new JTextArea();
    private final JTextField messageForSend = new JTextField();

    private final JButton buttonConnect = new JButton("connect");
    private final JButton buttonSendMessage = new JButton("send");
    private JPanel connectOption;
    private final ChatClient client;


    public ChatClientViewSwing(ChatServer server) {
        client = new ChatClient(this, server);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setTitle("Chat client");
        addPanels();
        addListeners();

        setVisible(true);

    }

    private void addPanels() {

        JPanel serverParameters = new JPanel(new GridLayout(1, 3));
        serverParameters.add(serverIp);
        serverParameters.add(serverPort);
        serverParameters.add(new Box(1));

        JPanel loginParameters = new JPanel(new GridLayout(1, 3));
        loginParameters.add(login);
        loginParameters.add(password);
        loginParameters.add(buttonConnect);

        connectOption = new JPanel(new GridLayout(2, 1));
        connectOption.add(serverParameters);
        connectOption.add(loginParameters);
        add(connectOption, BorderLayout.NORTH);

        messages.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messages);
        add(scrollPane, BorderLayout.CENTER);

        JPanel sendOptions = new JPanel(new BorderLayout());
        sendOptions.add(messageForSend, BorderLayout.CENTER);
        sendOptions.add(buttonSendMessage, BorderLayout.EAST);
        add(sendOptions, BorderLayout.SOUTH);

    }

    private void addListeners() {
        buttonConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    if (client.authorize(login.getText(), Arrays.hashCode(password.getPassword()))) {
                        messages.append("Connection success!\n\n");
                        messages.append(client.loadHistory());
                        connectOption.setVisible(false);
                    } else {
                        messages.append("Connection failed\n");
                    }
                } catch (LoginTakenException ex) {
                    messages.append(ex.getMessage() + "\n");
                }
            }
        });

        buttonSendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessageToServerAndClearField();
            }
        });

        messageForSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessageToServerAndClearField();
            }
        });
    }

    public void sendMessageToServerAndClearField() {
        String message = messageForSend.getText();
        if (!message.isBlank()) {
            client.sendMessageToServer(login.getText(), messageForSend.getText());
            messageForSend.setText("");
        }
    }

    @Override
    public void updateMessages(String message) {
        messages.append(message);
    }

    @Override
    public void disconnectAction() {
        messages.append("You have been disconnected from the server.\n");
        connectOption.setVisible(true);
    }











}
