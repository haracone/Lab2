import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;

public class Server implements Runnable {
    private Socket socket;
    private ServerSocket serverSocket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private long bytes;
    private long currentBytes;
    private long time;

    Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    void getFile() throws IOException {
        int r;

        byte[] buf = new byte[8192];
        socket = serverSocket.accept();

        DataInputStream in = new DataInputStream(socket.getInputStream());
        File file = new File("C:\\Users\\a\\IdeaProjects\\Lab2\\uploads", in.readUTF());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        if (!file.exists())
            file.createNewFile();
        bytes = in.readLong();
        long currentTime = System.currentTimeMillis();
        while (in.read(buf) > 0) {
            fileOutputStream.write(buf);
            if (System.currentTimeMillis() - currentTime > 1) {
       /*         currentBytes = r;*/
              /*  PrintSpeed();*/
                currentBytes = 0;
            }
        }

        fileOutputStream.close();
    }

    @Override
    public void run() {

    }

    private void PrintSpeed() {
        System.out.println("Client " + socket.getInetAddress() + "Current speed" + currentBytes / 3 + "Average speed " + bytes / time);
    }
}
