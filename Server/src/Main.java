import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            Server server = new Server(5666);
            server.getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
