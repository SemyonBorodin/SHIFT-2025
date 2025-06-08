package common.protocol.chanel;

import common.protocol.messages.Message;
import common.protocol.serialization.JsonForm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;

public class ChatChannel implements Channel {
    private final Socket sock;
    private final PrintWriter writer;
    private final BufferedReader reader;
    private final JsonForm serializer = new JsonForm();
    private String username;

    public ChatChannel(Socket sock) throws IOException {
        this.sock = sock;
        this.writer = new PrintWriter(sock.getOutputStream(), true);
        this.reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
    }

    @Override
    public void close() throws IOException {
        try {
            writer.close();
            reader.close();
        } finally {
            sock.close();
        }
    }

    @Override
    public void send(Message message) throws Exception {
        String data = serializer.serialize(message) + "\n";
        writer.write(data);
        writer.flush();
    }

    @Override
    public void readLoop(Consumer<Message> messageConsumer) {
        System.out.println("Read loop started for " + sock.getInetAddress() + ":" + sock.getPort());
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String line = reader.readLine();
                if (line == null) break;
                Message message = serializer.deserialize(line);
                messageConsumer.accept(message);
            } catch (IOException e) {
                System.err.println("IO error on read: " + e.getMessage());
                break;
            } catch (Exception e) {
                System.err.println("Deserialization error (скип сообщения): " + e.getMessage());
            }
        }
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }
}