import java.io.IOException;
import java.net.*;
import java.util.Arrays;

public class Serbidor {
    public static void main(String[] args) throws IOException {
        // Socket multicast:
        MulticastSocket escucha = new MulticastSocket(55557);

        InetAddress dirIP = InetAddress.getByName("230.0.0.1");
        InetSocketAddress grupo = new InetSocketAddress(dirIP, 55557);
        NetworkInterface red = NetworkInterface.getByName("localhost");
        // Nos unimos al grupo multicast:
        escucha.joinGroup(grupo, red);
        // Recogeríamos el mensaje:
        byte[] datoRecogido = new byte[1024];
        // Recibimos la petición del servidor multicast:
        DatagramPacket dgp = new DatagramPacket(datoRecogido,
                datoRecogido.length);
        escucha.receive(dgp);
        // Obtención del dato ya relleno:
        byte[] datoRelleno = dgp.getData();
        // Hacemos algo con el dato, por ejemplo:
        System.out.println("Mensaje: " + Arrays.toString(datoRelleno));
        // Dejamos el grupo multicast:
        escucha.leaveGroup(grupo, red);
        // Cerramos:
        escucha.close();

    }
}
