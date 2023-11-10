import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Objects;

class ContenidolCliente extends JPanel implements Runnable{
    private static MulticastSocket escucha;
    private static InetAddress dirIP ;
    private static InetSocketAddress grupo;
    private JTextField campo1;
    private JTextField nombre;
    private JTextField ip;
    private JTextArea campoChat;
    private JButton miboton;
    private static JComboBox listaDesplegable;
    private static String nombreGrupo;

    /**
     *
     * @param g el nombre del grupo al que se une el cliente
     * @throws IOException
     */
    public ContenidolCliente(String g) throws IOException {
        if(Objects.equals(g, "Grupo1")){
            nombreGrupo = g;
            escucha = new MulticastSocket(12345);
            dirIP = InetAddress.getByName("225.0.0.1");
            grupo = new InetSocketAddress(dirIP, 12345);
            NetworkInterface red = NetworkInterface.getByName("localhost");
            escucha.joinGroup(grupo, red);
            System.out.println("Conectado a: "+escucha+" "+dirIP+" "+grupo+" "+escucha);

        }else if(Objects.equals(g, "Grupo2")){
            nombreGrupo = g;
            escucha = new MulticastSocket(12344);
            dirIP = InetAddress.getByName("225.0.0.2");
            grupo = new InetSocketAddress(dirIP, 12344);
            NetworkInterface red = NetworkInterface.getByName("localhost");
            escucha.joinGroup(grupo, red);
            System.out.println("Conectado a: "+escucha+" "+dirIP+" "+grupo+" "+escucha);
        }

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
        listaDesplegable.setSelectedItem(nombreGrupo);
        listaDesplegable.setEnabled(false);
        /*CambiarGrupo desplegableEvent = new CambiarGrupo();
        listaDesplegable.addItemListener(desplegableEvent);*/
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
        /**
         * iniciar el hilo del cliente, se encarga de recibir cada mensaje
         */
        Thread hiloCliente = new Thread(this);
        hiloCliente.start();

    }

    public static void setConexion(String g) throws IOException {

    }

    @Override
    public void run() {

            try{
                String msg = "";
                byte[] buf = new byte[1000];
                while (true) {
                    DatagramPacket paquete = new DatagramPacket(buf, buf.length);
                    escucha.receive(paquete);
                    msg = new String(paquete.getData());
                    String[] parts = msg.split("/");
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
        /**
         *
         * @param e la acción que hace el boton enviar, envia la clase mensajev al servidor
         */
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





