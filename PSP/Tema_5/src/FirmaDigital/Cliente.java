package FirmaDigital;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;

public class Cliente {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, ClassNotFoundException, InvalidKeyException, SignatureException {
        ServerSocket servidor = new ServerSocket(4444);
        Socket socket = servidor.accept();
        ObjectInputStream objetoEntrada=new ObjectInputStream(socket.getInputStream());
        PublicKey clavepub= (PublicKey) objetoEntrada.readObject();

        ObjectInputStream objetoEntrada2=new ObjectInputStream(socket.getInputStream());
        Mensaje m= (Mensaje) objetoEntrada2.readObject();

        Signature verificadsa = Signature.getInstance("SHA256withDSA");
        verificadsa.initVerify(clavepub);
        verificadsa.update(m.getTexto().getBytes());
        boolean check = verificadsa.verify(m.getFirma());
        if (check) {
            System.out.println("FiRMA VERIFICADA CON CLAVE PÚBLICA.");
        } else {
            System.out.println("FiRMA NO VERIFICADA");
        }
    }
}
