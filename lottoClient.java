import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class lottoClient {
    public static void main(String[] args) {
        try {
            try (Socket socket = new Socket("localhost", 6789)) {
                System.out.println("Connesso al server");

                DataInputStream in = new DataInputStream(socket.getInputStream());

                while (true) {
                    String messaggio = in.readUTF();
                    System.out.println("Numeri estratti: " + messaggio);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
