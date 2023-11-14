import javax.swing.*;
import java.io.IOException;

class MarcoCliente extends JFrame {
    /**
     * Tamaño de la ventana y cargar el contenido de esa ventana
     * @param string en nombre del grupo al que se une el cliente
     * @throws IOException
     */
    public MarcoCliente(String string) throws IOException {

        setBounds(600,300,280,350);

        ContenidolCliente milamina=new ContenidolCliente(string);

        add(milamina);

        setVisible(true);
    }

}
