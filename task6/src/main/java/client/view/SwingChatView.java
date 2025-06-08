package client.view;

import common.protocol.messages.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SwingChatView implements ChatView {
    private JFrame frame;
    private JDialog loginDialog;
    private JTextField addressField;
    private JTextField portField;
    private JTextField usernameField;
    private JTextPane messageArea;
    private JTextField inputField;
    private JList<String> participantsList;
    private DefaultListModel<String> participantsModel;
    private JButton sendButton;
    private JButton connectButton;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public SwingChatView() {
        initLoginDialog();
        initChatWindow();
        setupColorsAndStyles();
    }

    private void setupColorsAndStyles() {
        Color bgColor = new Color(245, 245, 250);
        Color systemColor = new Color(0, 120, 0);
        Color errorColor = new Color(200, 0, 0);

        // Стилии messageArea
        messageArea.setBackground(bgColor);
        messageArea.setForeground(Color.BLACK);
        messageArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        // Стилии поля ввода
        inputField.setBackground(Color.WHITE);
        inputField.setForeground(Color.BLACK);
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputField.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 1));

        // Стилили списка чаттерсов
        participantsList.setBackground(new Color(230, 230, 230));
        participantsList.setForeground(new Color(50, 48, 48));
        participantsList.setFont(new Font("Arial", Font.PLAIN, 12));

        frame.getContentPane().setBackground(bgColor);
        loginDialog.getContentPane().setBackground(bgColor);

        styleButton(connectButton, new Color(63, 213, 129));
        styleButton(sendButton, new Color(61, 107, 145));
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorder(BorderFactory.createLineBorder(color.darker(), 1));
        button.setOpaque(true);
        button.setBorderPainted(true);
    }

    private void initLoginDialog() {
        loginDialog = new JDialog((Frame) null, "Вход в чат", true);
        loginDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        loginDialog.setLayout(new GridLayout(4, 2, 10, 10));
        loginDialog.setSize(320, 220);
        loginDialog.setLocationRelativeTo(null);

        JLabel addressLabel = new JLabel("Адрес сервера:");
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        loginDialog.add(addressLabel);
        addressField = new JTextField("localhost");
        addressField.setFont(new Font("Arial", Font.PLAIN, 14));
        loginDialog.add(addressField);

        JLabel portLabel = new JLabel("Порт сервера:");
        portLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        loginDialog.add(portLabel);
        portField = new JTextField("12345");
        portField.setFont(new Font("Arial", Font.PLAIN, 14));
        loginDialog.add(portField);

        JLabel usernameLabel = new JLabel("Ваше имя:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        loginDialog.add(usernameLabel);
        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        loginDialog.add(usernameField);

        connectButton = new JButton("Подключиться");
        connectButton.setPreferredSize(new Dimension(100, 30));
        loginDialog.add(new JPanel());
        loginDialog.add(connectButton);
    }

    private void initChatWindow() {
        frame = new JFrame("💬 Чат");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setMinimumSize(new Dimension(400, 300));
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        messageArea = new JTextPane();
        messageArea.setEditable(false);
        messageArea.setContentType("text/html");
        messageArea.setText("<html><body style='font-family: Monospaced; font-size: 14px;'></body></html>");
        JScrollPane messageScroll = new JScrollPane(messageArea);
        messageScroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(messageScroll, BorderLayout.CENTER);
        participantsModel = new DefaultListModel<>();
        participantsList = new JList<>(participantsModel);
        participantsList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JScrollPane participantsScroll = new JScrollPane(participantsList);
        participantsScroll.setPreferredSize(new Dimension(150, 0));
        participantsScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(150, 150, 150)),
                "Чаттерсы",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12),
                new Color(50, 50, 50)
        ));
        frame.add(participantsScroll, BorderLayout.EAST);

        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputField = new JTextField();
        sendButton = new JButton("Отправить");
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        frame.add(inputPanel, BorderLayout.SOUTH);
    }

    @Override
    public void showLoginDialog() {
        loginDialog.setVisible(true);
    }

    @Override
    public void showChatWindow() {
        loginDialog.dispose();
        frame.setVisible(true);
    }

    @Override
    public void appendMessage(Message message) {
        System.out.println("Добавление сообщения: " + message);
        var time = dateFormat.format(new Date(getTimestamp(message)));
        String htmlText = messageArea.getText();
        int bodyEnd = htmlText.lastIndexOf("</body>");

        String newMessage = "";
        if (message instanceof TextMessage) {
            var msg = (TextMessage) message;
            newMessage = String.format(
                    "<span style='font-style: italic; color: #555555;'>%s</span> " +
                            "<b>%s</b>: %s<br>",
                    time, msg.getSender(), msg.getText()
            );
        } else if (message instanceof SystemMessage) {
            SystemMessage msg = (SystemMessage) message;
            newMessage = String.format(
                    "<span style='background-color: #e6f3e6; padding: 2px;'>" +
                            "<span style='font-style: italic; color: #555555;'>%s</span> " +
                            "<span style='color: #006400;'>Сервер: %s</span></span><br>",
                    time, msg.getText()
            );
        } else if (message instanceof DisconnectMessage) {
            var msg = (DisconnectMessage) message;
            newMessage = String.format(
                    "<span style='background-color: #e6f3e6; padding: 2px;'>" +
                            "<span style='font-style: italic; color: #555555;'>%s</span> " +
                            "<span style='color: #006400;'>Сервер: %s покинул чат</span></span><br>",
                    time, msg.getUsername()
            );
        }

        htmlText = htmlText.substring(0, bodyEnd) + newMessage + htmlText.substring(bodyEnd);
        messageArea.setText(htmlText);
        messageArea.setCaretPosition(messageArea.getDocument().getLength());
    }

    private long getTimestamp(Message message) {
        if (message instanceof ServerMessage msg) return msg.getTimestamp();
        return System.currentTimeMillis();
    }

    @Override
    public void updateParticipants(String[] participants) {
        participantsModel.clear();
        for (String participant : participants) {
            participantsModel.addElement(participant);
        }
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void setConnectAction(Runnable action) {
        connectButton.addActionListener(e -> action.run());
    }

    @Override
    public void setSendAction(Runnable action) {
        sendButton.addActionListener(e -> action.run());
        inputField.addActionListener(e -> action.run());
    }

    @Override
    public void setDisconnectAction(Runnable action) {
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                action.run();
            }
        });
    }

    @Override
    public String getUsername() {
        return usernameField.getText().trim();
    }

    @Override
    public String getServerAddress() {
        return addressField.getText().trim();
    }

    @Override
    public int getServerPort() {
        try {
            return Integer.parseInt(portField.getText().trim());
        } catch (NumberFormatException e) {
            return 12345;
        }
    }

    @Override
    public String getMessageText() {
        String text = inputField.getText().trim();
        inputField.setText("");
        return text;
    }
}