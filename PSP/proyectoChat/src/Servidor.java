import javax.swing.*;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Objects;

public class Servidor  {

    public static void main(String[] args) {

        MarcoServidor mimarco=new MarcoServidor();

        mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}

class MarcoServidor extends JFrame {

    public MarcoServidor(){
        setBounds(1200,300,280,350);
        ContenidoServer server=new ContenidoServer();

        add(server);

        setVisible(true);
    }



}
class ContenidoServer extends JPanel implements Runnable{
    public ContenidoServer() {

        JLabel texto=new JLabel("-Server CHAT-");
        add(texto);


        areatexto = new JTextArea(17,22);
        areatexto.setEditable(false);
        areatexto.setBackground(Color.white);
        add(areatexto);

        Thread hiloServer =new Thread(this);
        hiloServer.start();
    }
    private	JTextArea areatexto;

    /**
     * El run del servidor es para que pueda
     */
    @Override
   public void run() {
        //System.out.println("hola");
        try {
            ServerSocket servidor=new ServerSocket(9999);
            Mensaje m;
            MulticastSocket ms = new MulticastSocket();
            int puerto = 0;
            InetAddress grupo = null;//Grupo
            String texto="";
            while(true){
                Socket cliente =servidor.accept();
                ObjectInputStream objetoEntrada=new ObjectInputStream(cliente.getInputStream());
                m= (Mensaje) objetoEntrada.readObject();

                //DataInputStream flujoEntrada=new DataInputStream(cliente.getInputStream());
                //String mensaje=flujoEntrada.readUTF();
                areatexto.append("\n"+m.getNombre()+": "+m.getTexto()+", "+m.getIp());
                if(Objects.equals(m.getIp(), "Grupo1")){
                    System.out.println("\n"+m.getNombre()+": "+m.getTexto()+", "+m.getIp());
                    puerto = 12345;
                    grupo = InetAddress.getByName("225.0.0.1");
                }else if(Objects.equals(m.getIp(), "Grupo2")){
                    System.out.println("\n"+m.getNombre()+": "+m.getTexto()+", "+m.getIp());
                    grupo = InetAddress.getByName("225.0.0.2");
                    puerto = 12344;
                }
                /*Socket destinatario = new Socket(m.getIp(),4444);
                ObjectOutputStream mensajeReenvio= new ObjectOutputStream(destinatario.getOutputStream());
                mensajeReenvio.writeObject(m);
                destinatario.close();*/

                texto=m.getNombre()+"::"+m.getTexto();
                DatagramPacket paquete = new DatagramPacket(texto.getBytes(), texto.length(),
                        grupo, puerto);
                ms.send(paquete);
                //ms.close();
                //cliente.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
