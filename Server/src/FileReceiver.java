import java.io.*;
import java.net.Socket;

public class FileReceiver implements Runnable {
    private static final int BUF_SIZE = 8192;
    private volatile Socket socket;
    private volatile long fileSize;
    private volatile long currentBytes;
    private volatile boolean error = false;
    private volatile DataInputStream in;
    private volatile DataOutputStream out;

    FileReceiver(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        int currentGetBytes;
        long currentTime;
        long sentBytes = 0;
        boolean sizesIsEquals = false;
        long time = 0;
        byte[] buf = new byte[BUF_SIZE];

        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            File file = new File(System.getProperty("user.dir") + "\\uploads\\", in.readUTF());
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            if (!file.exists())
                file.createNewFile();
            fileSize = in.readLong();

            long startTime = System.currentTimeMillis();
            long finishTime;

            while (sentBytes < fileSize) {
                currentTime = System.currentTimeMillis();
                if (currentTime == 0) {
                    currentTime = System.currentTimeMillis();
                }
                currentGetBytes = in.read(buf);
                sentBytes += BUF_SIZE;
                fileOutputStream.write(buf);
                if ((System.currentTimeMillis() - currentTime) > 3000) {
                    time+=3;
                    currentBytes = currentGetBytes;
                    PrintSpeed(time);
                    currentBytes = 0;
                }
            }
            sizesIsEquals = (file.length() == fileSize);
            fileOutputStream.close();

            finishTime = System.currentTimeMillis();
            if (finishTime - startTime < 3000)
                if (finishTime - startTime == 0)
                    System.out.println("Byte/sec " + fileSize);
                else
                    System.out.println("Byte/sec " + fileSize / (finishTime - startTime) * 1000);


        } catch (Exception e) {
            error = true;
            e.printStackTrace();

        } finally {
            try {
                if (error) {
                    out.writeBoolean(error && sizesIsEquals);
                } else
                    out.writeBoolean(error);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in.close();
                out.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void PrintSpeed(long finishTime) {
        System.out.println("Client " + socket.getInetAddress() + " Current speed Byte/Sec " + currentBytes / 3 + " Average speed Byte/Sec " + fileSize / finishTime);
    }
}
