package server;

import java.io.IOException;
import java.util.Properties;

public class Server {
    public static final String DEFAULT_PORT = "12345";

    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        try {
            props.load(Server.class.getClassLoader().getResourceAsStream("server.properties"));
        } catch (IOException e) {
            System.err.println("Не удалось подтянуть server.properties, дефолтный порт: " + DEFAULT_PORT);
        }
        int port = Integer.parseInt(props.getProperty("port", DEFAULT_PORT));

        ChatServer server = new ChatServer(port);
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Завершение работы сервера...");
            server.stop();
        }));
    }
}
