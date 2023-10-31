import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) throws IOException {
        String Host = "localhost";
        int Puerto = 6000;
        System.out.println("PROGRAMA CLIENTE INICIADO ");

        Socket cliente = new Socket(Host, Puerto);
        // flujo de salida Para objetos
        ObjectOutputStream datosal = new ObjectOutputStream(cliente.getOutputStream());
        //flujo de entrada Para objetos
        ObjectInputStream datoent = new ObjectInputStream(cliente.getInputStream());
    }
}
