import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;

class FileSender {
    private static final int BUF_SIZE = 8192;
    private Socket socket;
    private BufferedReader in;
    private DataOutputStream out;
    private File file;
    private byte[] data;
    FileInputStream fileInputStream;

    FileSender(int port, InetAddress address, File file) throws IOException {
        this.file = file;
        socket = new Socket(address, port);
        data = new byte[BUF_SIZE];
    }

    void SendFile() throws IOException {
        long sent = 0;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            fileInputStream = new FileInputStream(file);

            out.writeUTF(file.getName());
            out.writeLong(file.length());
            while (fileInputStream.read(data) != -1) {
                out.write(data);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            in.close();
            out.close();
            fileInputStream.close();
        }
    }
}
