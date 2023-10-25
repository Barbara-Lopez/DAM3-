
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
     static Integer cont=0;
    public static void main(String[] args) throws IOException {

            ServerSocket skServidor = new ServerSocket(4999);

            while(true) {
                Socket skCliente = skServidor.accept();// peticion del usuario

                cont++;
                HiloServidor h = new HiloServidor(skCliente, cont);
                h.start();


            }
    }

}

