import javax.swing.*;
import java.awt.*;

public class Grupo extends JFrame {
    public Grupo() throws HeadlessException {
        setBounds(600,300,280,350);

        GrupoVisual milamina=new GrupoVisual();

        add(milamina);

        setVisible(true);
    }
}
