import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Recibir {

    public static void main(String[] args) throws IOException {



        //recibir

        DatagramSocket recibir=new DatagramSocket(4444);

        DatagramPacket paqueteRecibido;

        byte[] lectura=new byte[64];

        paqueteRecibido=new DatagramPacket(lectura,64);

        recibir.receive(paqueteRecibido);


        System.out.println(paqueteRecibido.getAddress());
        System.out.println(paqueteRecibido.getData());
        String testo=new String(paqueteRecibido.getData());
        System.out.println(testo);
        System.out.println(testo.substring(0, paqueteRecibido.getLength()));
        System.out.println(paqueteRecibido.getPort());


    }
}
