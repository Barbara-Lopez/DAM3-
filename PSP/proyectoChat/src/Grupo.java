import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.Objects;

public class Grupo extends JFrame {
    public static ContenidoGrupo milamina;
    public Grupo() throws HeadlessException {
        setBounds(600,300,280,350);

         milamina=new ContenidoGrupo();

        add(milamina);

        setVisible(true);
    }
}
class ContenidoGrupo extends JPanel {
    private JComboBox listaDesplegable;

    public ContenidoGrupo() {

        JLabel texto = new JLabel("-CHAT-");
        add(texto);
        String[] items = {
                "",
                "Grupo1",
                "Grupo2"
        };
        listaDesplegable = new JComboBox<String>(items);
        listaDesplegable.setSelectedIndex(0);
        listaDesplegable.setBounds(50, 50, 50, 50);
        CambiarGrupo desplegableEvent = new CambiarGrupo();
        listaDesplegable.addItemListener(desplegableEvent);
        add(listaDesplegable);
        JButton miboton = new JButton("Unirse");
        EnviarTexto buttonEvent = new EnviarTexto();
        miboton.addActionListener(buttonEvent);
        add(miboton);

    }
    private class CambiarGrupo implements ItemListener {
        /**
         *
         * @param e cambiar de color el desplegable cuando seleccionas algo que no sea la opcion uno que está en blanco
         */
        public void itemStateChanged(ItemEvent e) {
            if(listaDesplegable.getSelectedIndex()!=0){
                listaDesplegable.setBackground(Color.white);
            }
        }
    }
    private class EnviarTexto implements ActionListener {
        /**
         * 
         * @param e se encarga de enviar el nombre del grupo al que se quiere unira la interfaz grafica del chat
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            //System.out.println(campo1.getText());
            if(listaDesplegable.getSelectedIndex()!=0){

                MarcoCliente mimarco = null;
                try {
                    mimarco = new MarcoCliente(Objects.requireNonNull(listaDesplegable.getSelectedItem()).toString());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                //System.exit(0);
                Grupo.milamina.setVisible(false);
            }else{
                listaDesplegable.setBackground(Color.red);
            }
        }
    }

}
