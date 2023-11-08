import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
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
        setVisible(true);
    }



}
