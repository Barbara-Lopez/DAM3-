import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) throws IOException {
        ServerSocket servidor = new ServerSocket(4999);
        int numCli;
        for (numCli = 1; numCli < 6; numCli++) {
            Socket cliente = servidor.accept();
            System.out.println("Atiendo al cliente"+numCli);
            OutputStream aux=cliente.getOutputStream();
            DataOutputStream flujo=new DataOutputStream(aux);
            flujo.writeUTF("hola cliente"+numCli);
            flujo.close();
        }


    }
}
