import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Objects;

class VisualCliente extends JPanel implements Runnable{
    private MulticastSocket escucha;
    private InetAddress dirIP ;
    private InetSocketAddress grupo;
    private JTextField campo1;
    private JTextField nombre;
    private JTextField ip;
    private JTextArea campoChat;
    private JButton miboton;
    private JComboBox listaDesplegable;
    public VisualCliente(){
        nombre=new JTextField(5);
        nombre.setText("Nombre");
        nombre.selectAll();
        add(nombre);
        JLabel texto=new JLabel("-CHAT-");
        add(texto);
        String[] items ={
                "Grupo1",
                "Grupo2"
        };
        listaDesplegable=new JComboBox<String>(items);
        listaDesplegable.setSelectedIndex(0);

        MouseListener l= new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if(listaDesplegable.getSelectedIndex()==0){
                        escucha = new MulticastSocket(12345);
                        dirIP = InetAddress.getByName("225.0.0.1");
                        grupo = new InetSocketAddress(dirIP, 12345);
                        System.out.println(listaDesplegable.getSelectedItem());
                    }else{
                        escucha = new MulticastSocket(12344);
                        dirIP = InetAddress.getByName("225.0.0.2");
                        grupo = new InetSocketAddress(dirIP, 12344);
                        System.out.println(listaDesplegable.getSelectedItem());
                    }
                } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
        listaDesplegable.addMouseListener(l);
        add(listaDesplegable);
        /*ip=new JTextField(8);
        ip.setText("localhost");
        ip.setEnabled(false);
        add(ip);*/

        campoChat = new JTextArea(12,20);
        campoChat.setEditable(false);
        campoChat.setBackground(Color.white);
        add(campoChat);
        campo1=new JTextField(20);

        add(campo1);

        miboton=new JButton("Enviar");
        EnviarTexto buttonEvent= new EnviarTexto();
        miboton.addActionListener(buttonEvent);
        add(miboton);

        Thread hiloCliente = new Thread(this);
        hiloCliente.start();
    }

    @Override
    public void run() {

        try{
            if(escucha==null || dirIP==null || grupo==null){
                escucha = new MulticastSocket(12345);
                dirIP = InetAddress.getByName("225.0.0.1");
                grupo = new InetSocketAddress(dirIP, 12345);
            }
            NetworkInterface red = NetworkInterface.getByName("localhost");
            escucha.joinGroup(grupo, red);
            String msg = "";
            byte[] buf = new byte[1000];
            while (true) {
                DatagramPacket paquete = new DatagramPacket(buf, buf.length);
                escucha.receive(paquete);
                msg = new String(paquete.getData());
                String[] parts = msg.split(" ");
                String part1 = parts[0]; // 123
                String part2 = parts[1];
                if(Objects.equals(part1, nombre.getText())){
                    campoChat.append("\n Yo: " + part2);
                    System.out.println("\n "+ msg.trim());
                }else{
                    campoChat.append("\n " + part1 + ": " + part2);
                    System.out.println("\n " + part1 + ": " + part2);
                }

            }
            //escucha.leaveGroup(grupo,red);//Abandonamos el grupo
            //escucha.close();
            /*ServerSocket servidorClient=new ServerSocket(4444);
            Socket cliente;
            Mensaje mensajeRecibido;
            while(true){
                cliente =servidorClient.accept();
                ObjectInputStream objetoEntrada=new ObjectInputStream(cliente.getInputStream());
                mensajeRecibido= (Mensaje) objetoEntrada.readObject();

                //DataInputStream flujoEntrada=new DataInputStream(cliente.getInputStream());
                //String mensaje=flujoEntrada.readUTF();
                if(Objects.equals(nombre.getText(), mensajeRecibido.getNombre())) {
                    campoChat.append("\n Yo: " + mensajeRecibido.getTexto());
                    System.out.println("\n Yo: " + mensajeRecibido.getTexto());
                }else{
                    campoChat.append("\n " + mensajeRecibido.getNombre() + ": " + mensajeRecibido.getTexto());
                    System.out.println("\n " + mensajeRecibido.getNombre() + ": " + mensajeRecibido.getTexto());

                }

            };*/

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    private class EnviarTexto implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //System.out.println(campo1.getText());
            try {
                if(!campo1.getText().isEmpty()) {
                    Socket cliente = new Socket("localhost", 9999);
                    Mensaje mensaje = new Mensaje();
                    mensaje.setNombre(nombre.getText());
                    mensaje.setIp((String) listaDesplegable.getSelectedItem());
                    mensaje.setTexto(campo1.getText());

                    ObjectOutputStream objetoSalida = new ObjectOutputStream(cliente.getOutputStream());
                    objetoSalida.writeObject(mensaje);
                    objetoSalida.close();

                    //DataOutputStream flujoSalida=new DataOutputStream(cliente.getOutputStream());
                    //flujoSalida.writeUTF(campo1.getText());
                    //flujoSalida.close();

                }
            } catch (IOException ex) {
                throw new RuntimeException(ex.getMessage());
            }

        }
    }
}





