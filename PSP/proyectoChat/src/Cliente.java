
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

/**
 * La visualización de la ventana del cliente
 *
 */
public class Cliente {
    /**
     * Crea la ventana que se tiene que visualizar.
     * En este caso primero la ventana para elegir el grupo y luego la ventana para poder enviar los mensajes y recibirlos
     */
    public static void main() {

        Grupo miVentana=new Grupo();

        miVentana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}



