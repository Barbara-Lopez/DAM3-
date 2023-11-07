import javax.swing.*;

class MarcoCliente extends JFrame {

    public MarcoCliente(){

        setBounds(600,300,280,350);

        VisualCliente milamina=new VisualCliente();

        add(milamina);

        setVisible(true);
    }

}
