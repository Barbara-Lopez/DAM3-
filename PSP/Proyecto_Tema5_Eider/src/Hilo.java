import modelos.Client;
import modelos.Usuario;

import javax.crypto.Cipher;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.security.*;
import java.util.Arrays;
import java.util.Objects;

public class Hilo extends Thread {

    private static SSLSocket cliente;
    private static Cipher rsaCipher;
    private static ObjectOutputStream objetoSalida;
    private static ObjectInputStream objetoEntrada;
    private static PublicKey clavepubClient;
    private static PrivateKey clavepriv;
    private static PublicKey clavepub;
    private static Integer opcion1=0;
    private static Client client;
    private static DataOutputStream textoSalida;
    private static DataInputStream textoEntrada;
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
                            Client cli =(Client) objetoEntrada.readObject();
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
        FileInputStream fileout;
        ObjectInputStream dataIS = null;
        File fichero = new File("src/files/Clientes.dat");
        Client c= null;
        try {
            fileout = new FileInputStream(fichero);
            dataIS = new ObjectInputStream(fileout);

            while (dataIS.available() != -1 ) {
                client= (Client) dataIS.readObject();
                System.out.println(client);
                if(client.getUser().getUsuario().equalsIgnoreCase(user.getUsuario())){
                    c=client;
                    break;
                }

            }
        }catch (EOFException eo){} catch (IOException | ClassNotFoundException e) {
            System.out.println("No hay nada");
        }
        if(dataIS!=null){
            dataIS.close();
        }
        textoSalida= new DataOutputStream(cliente.getOutputStream());
        if(c!=null){
            String contrasenaGuardada= Arrays.toString(c.getUser().getContrasena());
            String contrasenaEnviada=Arrays.toString(user.getContrasena());
            if(Objects.equals(contrasenaGuardada, contrasenaEnviada)){
                textoSalida.writeBoolean(true);
                client=c;
            }else{
                textoSalida.writeBoolean(false);
            }
        }else{
            textoSalida.writeBoolean(false);
        }
    }
    public static void crearCuenta(Client cli) throws IOException {
        File fichero = new File("src/files/Clientes.dat");
        FileOutputStream fileout = new FileOutputStream(fichero);
        ObjectOutputStream dataOS=new ObjectOutputStream(fileout);
        FileInputStream filein;
        ObjectInputStream dataIS = null;
        Client c= null;
        Boolean clienteRepe=false;
        Boolean userNombreRepe=false;
        try {
            filein = new FileInputStream(fichero);
            dataIS = new ObjectInputStream(filein);

            while (dataIS.available() != -1 ) {
                client= (Client) dataIS.readObject();
                System.out.println(client);
                if(client.getNombre().equalsIgnoreCase(cli.getNombre()) && client.getApellido().equalsIgnoreCase(cli.getApellido())){
                    c=client;
                    clienteRepe = true;
                    break;
                }
                if(client.getUser().getUsuario().equalsIgnoreCase(cli.getUser().getUsuario())){
                    c=client;
                    userNombreRepe = true;
                    break;
                }
            }
        }catch (EOFException eo){} catch (IOException | ClassNotFoundException e) {
            System.out.println("No hay nada");
        }
        if(dataIS!=null){
            dataIS.close();
        }
        if(c==null){
            dataOS.writeObject(cli);
            client= c;
            textoSalida= new DataOutputStream(cliente.getOutputStream());
            textoSalida.writeBoolean(true);
            textoSalida.writeUTF("Cliente nuevo crado correctamente");
        }else{

            textoSalida= new DataOutputStream(cliente.getOutputStream());
            textoSalida.writeBoolean(false);
            if(clienteRepe){
                textoSalida.writeUTF("El cliente que quiere crear ya esá guardado, " +
                        "si quiere crear uno nuevo escribalo con otro nombre y apellido");
            }
            if(userNombreRepe){
                textoSalida.writeUTF("El cliente que quiere crear, tiene un nombre de usuario ya existenete," +
                        "si quiere crear el usuario escribalo con un nombre de usuario diferente");
            }
        }
        dataOS.close();
    }
}
