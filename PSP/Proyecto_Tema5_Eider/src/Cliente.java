import modelos.Usuario;

import javax.crypto.Cipher;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.*;
import java.util.Scanner;

public class Cliente {
    private static PublicKey clavepubServer;
    private static SSLSocket cliente;
    private static PrivateKey clavepriv;
    private static ObjectInputStream objetoEntrada;
    private static ObjectOutputStream objetoSalida;
    private static PublicKey clavepub;
    private static  Cipher rsaCipher;

    public static void main(String[] args) {
        System.setProperty("javax.net.ssl.trustStore", "src/files/UsuarioAlmacenSSL");
        System.setProperty("javax.net.ssl.trustStorePassword", "890123");
        try{
            //Crear el Socket Seguro
            String host="localhost";
            int puerto=6000;
            System.out.println("Programa cliente iniciado..");
            SSLSocketFactory sfact=(SSLSocketFactory)SSLSocketFactory.getDefault();
            cliente=(SSLSocket) sfact.createSocket(host,puerto);

            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            //SE CREA EL PAR DE CLAVES PRIVADA Y PÚBLICA
            KeyPair par = keyGen.generateKeyPair();
            clavepriv = par.getPrivate();
            clavepub = par.getPublic();
            rsaCipher = Cipher.getInstance("RSA");
            enviarRecibirClavePub();
            Scanner lectura = new Scanner(System.in);
            int op = 0;
            do {
                try {
                    System.out.println("Elija lo que quiere hacer: \n 1.Iniciar sesión \n 2.Crear cuenta \n 3.Salir");
                    op = Integer.parseInt(lectura.next());
                    switch(op){
                        case 1: {
                            DataOutputStream op1= new DataOutputStream(cliente.getOutputStream());
                            op1.writeInt(1);
                            break;
                        }
                        case 2: {
                            DataOutputStream  op1= new DataOutputStream(cliente.getOutputStream());
                            op1.writeInt(2);
                            break;
                        }
                        case 3: {
                            DataOutputStream  op1= new DataOutputStream(cliente.getOutputStream());
                            op1.writeInt(3);
                            break;
                        }
                        default:
                            System.out.println("Escriba un numero del 1 al 3");
                    }





                } catch (NumberFormatException ex){
                    System.out.println("Tienes que escribir un numero");
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
            } while (op!=3);


        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void enviarRecibirClavePub() throws IOException, ClassNotFoundException {
        objetoEntrada=new ObjectInputStream(cliente.getInputStream());
        clavepubServer= (PublicKey) objetoEntrada.readObject();
        //System.out.println("Clave server:\n"+clavepubServer);
        objetoSalida = new ObjectOutputStream(cliente.getOutputStream());
        objetoSalida.writeObject(clavepub);
        //System.out.println("Clave cliente:\n"+clavepub);
    }
    public static void inicioSesion(Usuario user) throws IOException {

    }
    public static void crearCuenta(Cliente cli) throws IOException {

    }
}
