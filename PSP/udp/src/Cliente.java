import java.io.IOException;
import java.net.*;

public class Cliente {
    public static void main(String[] args) throws IOException {
        DatagramSocket envio=new DatagramSocket();

        DatagramPacket enviar;

        String texto="Hora";
        enviar=new DatagramPacket(texto.getBytes(),texto.length(), InetAddress.getByName("localhost"),4444);

        envio.send(enviar);
    }
}
