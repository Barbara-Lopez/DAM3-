import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Cliente2 {

    public static void main(String[] args) throws IOException {

        try {


            Socket skCliente = new Socket("localhost", 4999);
            InputStream aux = skCliente.getInputStream();//Creamos un Input Stream para recibir las respuestas del server
            DataInputStream flujo = new DataInputStream(aux);
            System.out.println("Mensaje escrito desde el server: "+flujo.readUTF());
            aux.close();
            skCliente.close();


        } catch (Exception e) {


            System.out.println(e.getMessage());


        }


    }
}
