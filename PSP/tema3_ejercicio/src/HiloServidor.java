import java.io.*;
import java.net.Socket;

public class HiloServidor extends Thread{
    Integer cont;
    Socket sock=null;
    PrintWriter fsalida;
    BufferedReader fentrada;
    public HiloServidor(Socket sock, Integer cont) throws IOException //constructor
    {
        this.cont=cont;
        this.sock=sock;
        // se crean los flujos de entrada y salida
        fsalida=new PrintWriter(sock.getOutputStream(),true);
        fentrada= new BufferedReader(new InputStreamReader(sock.getInputStream()));
    }

    @Override
    public void run() {
        try {
                System.out.println("Atiendo al cliente "+cont);
                OutputStream aux = sock.getOutputStream();
                DataOutputStream flujo = new DataOutputStream(aux);
                flujo.writeUTF("Hola cliente "+cont);
                flujo.close();
                sock.close();

        } catch (Exception e) {


            System.out.println(e.getMessage());


        }
    }
}
