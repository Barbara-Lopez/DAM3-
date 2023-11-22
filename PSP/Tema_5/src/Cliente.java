import javax.crypto.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {
    final int                PUERTO  = 5000;
    private Socket           cliente;
    private String           mensaje = "";
    private String           key;
    private Cipher           desCipher;
    private String           mensajeEnviado  = "";
    private byte[]           mensajeEnviadoCifrado;

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, ClassNotFoundException
    {
        try {
            // Crea el cliente
            Cliente c = new Cliente();
            c.initClient();
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initClient() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, ClassNotFoundException
    {
        try
        {


            // /////////////////////////////////////////////////////////////////////////
            // Crea el socket y solicita conexiÃ³n
            cliente = new Socket("localhost", PUERTO);
            //creamos los flujos
            ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
            //recogemos del flujo la clave simetrica
            KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
            KeyPair keypair = keygen.generateKeyPair();

            SecretKey key = (SecretKey) ois.readObject();
            System.out.println("le clave es : " + key);
            System.out.println("Configurando Cipher para encriptar");

            Cipher rsaCipher = Cipher.getInstance("RSA");
            desCipher.init(Cipher.ENCRYPT_MODE, key);
            Scanner sc = new Scanner(System.in);
            System.out.print("Reocgiendo mensajes\n");

            do
            {
                System.out.print("Escribir nuevo texto, finaliza con end\n");
                mensajeEnviado = sc.nextLine();

                // CIFRAR MENSAJE

                mensajeEnviadoCifrado = desCipher.doFinal(mensajeEnviado.getBytes());

                oos.writeObject(mensajeEnviadoCifrado);

            } while (!mensajeEnviado.equals("end"));


            // cierra salida, entrada y el socket
            oos.close();
            ois.close();
            cliente.close();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /*public static void main(String[] args) {
        try {
            KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
            KeyPair keypair = keygen.generateKeyPair();
            Cipher rsaCipher = Cipher.getInstance("RSA");
            Socket cliente = new Socket("localhost", 5000);
            DataOutputStream clave= new DataOutputStream(cliente.getOutputStream());
            clave.writeUTF(String.valueOf(keypair.getPublic()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
