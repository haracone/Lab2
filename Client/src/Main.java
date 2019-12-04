import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

public class Main {

    public static void main(String[] args) throws IOException {
        File file = new File("C:/Users/a/Desktop/Test.txt");
        InetAddress inetAddress = InetAddress.getByName(args[0]);
        FileSender fileSender = new FileSender(5666, inetAddress, file);
        fileSender.SendFile();
    }
}
