import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;

class FileSender {
    private static final int BUF_SIZE = 8192;
    private Socket socket;
    private File file;
    private byte[] data;
    private DataOutputStream out;
    private DataInputStream in;
    private FileInputStream fileInputStream;

    FileSender(int port, InetAddress address, File file) throws IOException {
        this.file = file;
        socket = new Socket(address, port);
        data = new byte[BUF_SIZE];
    }

    void SendFile() throws Exception {
        try {
            int count;
            out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            fileInputStream = new FileInputStream(file);

            out.writeUTF(file.getName());
            out.writeLong(file.length());
            while ((count = fileInputStream.read(data)) != -1) {
                out.write(data, 0, count);
            }
            out.flush();

            boolean error = in.readBoolean();
            if (!error) {
                System.out.println("File sent");
            } else {
                System.out.println("Error was occurred");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            out.close();
            in.close();
            socket.close();
            fileInputStream.close();
        }
    }
}
