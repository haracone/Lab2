import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private static ServerSocket serverSocket;
    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(Integer.valueOf(args[0]));
            Socket socket;
            while (true) {
                socket = serverSocket.accept();
                FileReceiver fileReceiver = new FileReceiver(socket);
                Thread t = new Thread(fileReceiver);
                t.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
