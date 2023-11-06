

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;


public class Cliente {

    public static void main(String[] args) {

        MarcoCliente mimarco=new MarcoCliente();

        mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}


class MarcoCliente extends JFrame{

    public MarcoCliente(){

        setBounds(600,300,280,350);

        LaminaMarcoCliente milamina=new LaminaMarcoCliente();

        add(milamina);

        setVisible(true);
    }

}

class LaminaMarcoCliente extends JPanel implements Runnable{

    public LaminaMarcoCliente(){
        nombre=new JTextField(5);
        nombre.setText("Nombre");
        nombre.selectAll();
        add(nombre);
        JLabel texto=new JLabel("-CHAT-");
        add(texto);
        ip=new JTextField(8);
        ip.setText("localhost");
        ip.setEnabled(false);
        add(ip);

        campoChat = new JTextArea(12,20);
        campoChat.setEnabled(false);
        add(campoChat);
        campo1=new JTextField(20);

        add(campo1);

        miboton=new JButton("Enviar");
        EnviarTexto buttonEvent= new EnviarTexto();
        miboton.addActionListener(buttonEvent);
        add(miboton);

        Thread hiloCliente =new Thread(this);
        hiloCliente.start();
    }

    @Override
    public void run() {
        try{
            ServerSocket servidorClient=new ServerSocket(9090);
            Socket cliente;
            Mensaje mensajeRecibido;
            while(true){
                cliente =servidorClient.accept();
                ObjectInputStream objetoEntrada=new ObjectInputStream(cliente.getInputStream());
                mensajeRecibido= (Mensaje) objetoEntrada.readObject();

                //DataInputStream flujoEntrada=new DataInputStream(cliente.getInputStream());
                //String mensaje=flujoEntrada.readUTF();
                if(Objects.equals(nombre.getText(), mensajeRecibido.getNombre())) {
                    campoChat.append("\nYo: " + mensajeRecibido.getTexto());
                }else{
                    campoChat.append("\n" + mensajeRecibido.getNombre() + ": " + mensajeRecibido.getTexto());
                }
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private class EnviarTexto implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //System.out.println(campo1.getText());
            try {
                Socket cliente = new Socket("localhost",9999);
                Mensaje mensaje = new Mensaje();
                mensaje.setNombre(nombre.getText());
                mensaje.setIp(ip.getText());
                mensaje.setTexto(campo1.getText());

                ObjectOutputStream objetoSalida=new ObjectOutputStream(cliente.getOutputStream());
                objetoSalida.writeObject(mensaje);
                objetoSalida.close();

                //DataOutputStream flujoSalida=new DataOutputStream(cliente.getOutputStream());
                //flujoSalida.writeUTF(campo1.getText());
                //flujoSalida.close();


            } catch (IOException ex) {
                throw new RuntimeException(ex.getMessage());
            }

        }
    }





    private JTextField campo1;
    private JTextField nombre;
    private JTextField ip;
    private JTextArea campoChat;
    private JButton miboton;

}
