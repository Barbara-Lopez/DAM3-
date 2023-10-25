import java.io.IOException;
import java.net.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {



        //enviar

        DatagramSocket envio=new DatagramSocket();

        DatagramPacket enviar;

        String texto="Hola";
        enviar=new DatagramPacket(texto.getBytes(),texto.length(), InetAddress.getByName("localhost"),4444);

        envio.send(enviar);



    }
}