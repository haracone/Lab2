import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

public class Main {
    private final static int MAX_FILE_NAME = 4096;

    public static void main(String[] args) {
        if (args[1].length() > MAX_FILE_NAME)
            return;
        File file = new File(args[1]);
        try {
        InetAddress inetAddress = InetAddress.getByName(args[0]);
        FileSender fileSender = new FileSender(5666, inetAddress, file);
            fileSender.SendFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
