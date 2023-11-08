import javax.swing.*;
import java.io.IOException;

class MarcoCliente extends JFrame {

    public MarcoCliente(String string) throws IOException {

        setBounds(600,300,280,350);

        ContenidolCliente milamina=new ContenidolCliente(string);

        add(milamina);

        setVisible(true);
    }

}
