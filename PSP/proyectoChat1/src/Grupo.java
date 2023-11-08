import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                "Grupo1",
                "Grupo2"
        };
        listaDesplegable = new JComboBox<String>(items);
        listaDesplegable.setSelectedIndex(0);
        listaDesplegable.setBounds(50, 50, 50, 50);
        add(listaDesplegable);
        JButton miboton = new JButton("Enviar");
        EnviarTexto buttonEvent = new EnviarTexto();
        miboton.addActionListener(buttonEvent);
        add(miboton);

    }

    private class EnviarTexto implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //System.out.println(campo1.getText());


            MarcoCliente mimarco = new MarcoCliente();

            mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //System.exit(0);
            Grupo.milamina.setVisible(false);
        }
    }

}
