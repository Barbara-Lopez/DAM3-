import javax.swing.*;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class Servidor  {

    public static void main(String[] args) {

        MarcoServidor mimarco=new MarcoServidor();

        mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}

class MarcoServidor extends JFrame implements Runnable{

    public MarcoServidor(){

        setBounds(1200,300,280,350);

        JPanel milamina= new JPanel();

        milamina.setLayout(new BorderLayout());

        areatexto=new JTextArea();

        milamina.add(areatexto,BorderLayout.CENTER);
        areatexto.setEnabled(false);
        add(milamina);

        setVisible(true);
        Thread hiloServer =new Thread(this);
        hiloServer.start();
    }

    private	JTextArea areatexto;

    @Override
    public void run() {
        //System.out.println("hola");

        try {
            ServerSocket servidor=new ServerSocket(9999);
            Mensaje m;
            MulticastSocket ms = new MulticastSocket();
            int puerto = 12345;
            InetAddress grupo = InetAddress.getByName("225.0.0.1");//Grupo
            String texto="";
            while(true){
                Socket cliente =servidor.accept();
                ObjectInputStream objetoEntrada=new ObjectInputStream(cliente.getInputStream());
                m= (Mensaje) objetoEntrada.readObject();

                //DataInputStream flujoEntrada=new DataInputStream(cliente.getInputStream());
                //String mensaje=flujoEntrada.readUTF();
                areatexto.append("\n"+m.getNombre()+": "+m.getTexto());
                /*Socket destinatario = new Socket(m.getIp(),4444);
                ObjectOutputStream mensajeReenvio= new ObjectOutputStream(destinatario.getOutputStream());
                mensajeReenvio.writeObject(m);
                destinatario.close();*/

                texto=m.getNombre()+" "+m.getTexto();
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
