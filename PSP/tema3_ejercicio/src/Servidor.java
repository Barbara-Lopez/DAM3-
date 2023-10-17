import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) throws IOException {

        ServerSocket servidor = new ServerSocket(3333);
        Socket cliente = servidor.accept();
        System.out.println("Conexión establecida");

        InputStream inp=cliente.getInputStream();
        OutputStream out=cliente.getOutputStream();
        inp.read();



    }
}
