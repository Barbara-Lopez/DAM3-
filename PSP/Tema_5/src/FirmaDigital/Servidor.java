package FirmaDigital;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.security.*;

public class Servidor {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeyException, SignatureException, ClassNotFoundException {

        Socket destinatario = new Socket(InetAddress.getLocalHost(),4444);
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
        //SE CREA EL PAR DE CLAVES PRIVADA Y PÚBLICA
        KeyPair par = keyGen.generateKeyPair();
        PrivateKey clavepriv = par.getPrivate();
        PublicKey clavepub = par.getPublic();

        ObjectInputStream objetoEntrada2=new ObjectInputStream(destinatario.getInputStream());
        Mensaje m= (Mensaje) objetoEntrada2.readObject();

        ObjectOutputStream clavePublica= new ObjectOutputStream(destinatario.getOutputStream());
        clavePublica.writeObject(clavepub);

        
        //FIRMA CON CLAVE PRIVADA EL MENSAJE
        //AL OBJETO Signature SE LE SUMINISTRAN LOS DATOS A FIRMAR
        Signature dsa = Signature.getInstance("SHA256withDSA");
        dsa.initSign(clavepriv);
        String mensaje = "Este mensaje va a ser firmado";
        System.out.println("Mensaje: "+mensaje);
        dsa.update(mensaje.getBytes());
        byte[] firma = dsa.sign(); //MENSAJE FIRMADO
        System.out.println("Firma: "+ new String(firma));
        ObjectOutputStream firmaDigital= new ObjectOutputStream(destinatario.getOutputStream());
        firmaDigital.writeObject(new Mensaje(mensaje,firma));
        destinatario.close();
    }
}
