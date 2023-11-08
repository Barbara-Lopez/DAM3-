import javax.swing.*;

class MarcoCliente extends JFrame {

    public MarcoCliente(){

        setBounds(600,300,280,350);

        ContenidoCliente cliente=new ContenidoCliente();

        add(cliente);

        setVisible(true);
    }

}
