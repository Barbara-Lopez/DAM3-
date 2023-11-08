import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Objects;

public class GrupoVisual extends JPanel {
    private JComboBox listaDesplegable;

    public GrupoVisual(){

        JLabel texto=new JLabel("-CHAT-");
        add(texto);
        String[] items ={
                "Grupo1",
                "Grupo2"
        };
        listaDesplegable=new JComboBox<String>(items);
        listaDesplegable.setSelectedIndex(0);
        listaDesplegable.setBounds(50,50,50,50);
        add(listaDesplegable);
        JButton miboton = new JButton("Enviar");
        EnviarTexto buttonEvent= new EnviarTexto();
        miboton.addActionListener(buttonEvent);
        add(miboton);

    }

    private class EnviarTexto implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //System.out.println(campo1.getText());
            new VisualCliente();
            MarcoCliente mimarco = new MarcoCliente();

            mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        }
    }

}
