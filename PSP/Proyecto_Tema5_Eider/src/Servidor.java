import javax.crypto.Cipher;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;

public class Servidor {
    static final int PUERTO = 5000;
    private static ServerSocket Servidor;
    private static Socket socket;



    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        System.out.println("Incializando servidor");

        System.setProperty("javax.net.ssl.keyStore","src/files/AlmacenSSL.jks");
        System.setProperty("javax.net.ssl.keyStorePassword","12345Abcde");
        // Crea el Socket de servicio
        int puerto = 6000;
        SSLServerSocketFactory sfact = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket servidorSSL = (SSLServerSocket) sfact.createServerSocket(puerto);
        SSLSocket clienteConectado = null;




        System.out.println("Esperando conexion cliente");
        while (true) {
            clienteConectado = (SSLSocket) servidorSSL.accept();;

            Hilo hilo = new Hilo(clienteConectado);
            hilo.start();
        }
    }
}
