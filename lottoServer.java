import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class lottoServer {
    private static ArrayList<Socket> listaClient = new ArrayList<>();

    public static void main(String[] args) {
        try {
            try (ServerSocket serverSocket = new ServerSocket(6789)) {
                System.out.println("Server in ascolto sulla porta 6789");

                while (true) {
                    Socket socket = serverSocket.accept();
                    listaClient.add(socket);
                    System.out.println("Connessione stabilita con il client " + socket.getInetAddress());

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (true) {
                                try {
                                    int[] numeriEstratti = estraiNumeriLotto();
                                    StringBuilder builder = new StringBuilder();
                                    for (int numero : numeriEstratti) {
                                        builder.append(numero).append(" ");
                                    }
                                    String messaggio = builder.toString();

                                    for (Socket client : listaClient) {
                                        DataOutputStream out = new DataOutputStream(client.getOutputStream());
                                        out.writeUTF(messaggio);
                                    }

                                    Thread.sleep(60000);
                                } catch (InterruptedException | IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    thread.start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[] estraiNumeriLotto() {
        int[] numeri = new int[5];
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            numeri[i] = random.nextInt(90) + 1;
        }
        return numeri;
    }
}   