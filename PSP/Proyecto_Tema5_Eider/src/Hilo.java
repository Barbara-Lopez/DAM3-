import modelos.Usuario;

import javax.crypto.Cipher;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Hilo extends Thread {

    private static SSLSocket cliente;
    private static Cipher rsaCipher;
    private static ObjectOutputStream objetoSalida;
    private static ObjectInputStream objetoEntrada;
    private static PublicKey clavepubClient;
    private static PrivateKey clavepriv;
    private static PublicKey clavepub;
    private static Integer opcion1=0;
    private static Cliente client;
    public Hilo(SSLSocket c) {
        this.cliente = c;

    }

    public void run() {

        try{
            verificarFicheroDat();
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            //SE CREA EL PAR DE CLAVES PRIVADA Y PÚBLICA
            KeyPair par = keyGen.generateKeyPair();
            clavepriv = par.getPrivate();
            clavepub = par.getPublic();
            rsaCipher = Cipher.getInstance("RSA");
            enviarRecibirClavePub();
            do {
                try {
                    DataInputStream op1 =new DataInputStream(cliente.getInputStream());
                    opcion1= op1.readInt();

                    switch(opcion1){
                        case 1: {
                            objetoEntrada =new ObjectInputStream(cliente.getInputStream());
                            Usuario user =(Usuario) objetoEntrada.readObject();
                            inicioSesion(user);
                            break;
                        }
                        case 2: {
                            objetoEntrada =new ObjectInputStream(cliente.getInputStream());
                            Cliente cli =(Cliente) objetoEntrada.readObject();
                            crearCuenta(cli);
                            break;
                        }
                        case 3: {
                            System.out.println("Un cliente sale de la aplicación");
                            break;
                        }
                    }


                } catch (NumberFormatException ex){
                    System.out.println("Tienes que escribir un numero");
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
            } while (opcion1!=3);
            cliente.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void enviarRecibirClavePub() throws IOException, ClassNotFoundException {
        objetoSalida = new ObjectOutputStream(cliente.getOutputStream());
        objetoSalida.writeObject(clavepub);
        //System.out.println("Clave server:\n"+clavepub);
        objetoEntrada =new ObjectInputStream(cliente.getInputStream());
        clavepubClient= (PublicKey) objetoEntrada.readObject();
        //System.out.println("Clave cliente:\n"+clavepubClient);

    }
    public static void verificarFicheroDat() throws IOException {
        // Crea un objeto File con la ruta del archivo
        File archivo = new File("src/files/Clientes.dat");

        // Verifica si el archivo existe
        if (archivo.exists()) {
            System.out.println("Archivo cliente encontrado");
        } else {
            System.out.println("Archivo cliente no encontrado, se va ha proceder a crearlo");
            FileOutputStream fos = new FileOutputStream(archivo);
            // Cierra el flujo de salida
            fos.close();
            System.out.println("Archivo cliente creado");
        }
    }
    public static void inicioSesion(Usuario user) throws IOException {

    }
    public static void crearCuenta(Cliente cli) throws IOException {

    }
}
