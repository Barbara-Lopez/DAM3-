import modelos.*;

import javax.crypto.Cipher;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.security.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Hilo extends Thread {

    private static SSLSocket cliente;
    private static Cipher rsaCipher;
    private static ObjectOutputStream objetoSalida;
    private static ObjectInputStream objetoEntrada;
    private static PublicKey clavepubClient;
    private static PrivateKey clavepriv;
    private static PublicKey clavepub;
    private static Integer opcion1=0;
    private static Integer opcion2=0;
    private static Client clienteEnSesion;
    private static DataOutputStream textoSalida;
    private static DataInputStream textoEntrada;
    private static Boolean sesionIniciada;
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
                    sesionIniciada=false;
                    switch(opcion1){
                        case 1: {
                            objetoEntrada =new ObjectInputStream(cliente.getInputStream());
                            Usuario user =(Usuario) objetoEntrada.readObject();
                            inicioSesion(user);
                            if(sesionIniciada){
                                textoSalida= new DataOutputStream(cliente.getOutputStream());
                                textoSalida.writeUTF("Tiene que firmar este texto para poder hacer cualquier " +
                                        "operacion como cliente");

                                objetoEntrada =new ObjectInputStream(cliente.getInputStream());
                                Mensaje m= (Mensaje) objetoEntrada.readObject();
                                Signature rsaSignature = Signature.getInstance("SHA256withRSA");
                                rsaSignature.initVerify(clavepubClient);
                                rsaSignature.update(m.getTexto().getBytes());
                                boolean check = rsaSignature.verify(m.getFirma());
                                if (check) {
                                    System.out.println("FiRMA VERIFICADA");
                                    textoSalida= new DataOutputStream(cliente.getOutputStream());
                                    textoSalida.writeBoolean(true);
                                    operacionesCuenta();
                                } else {
                                    System.out.println("FiRMA NO VERIFICADA");
                                    textoSalida= new DataOutputStream(cliente.getOutputStream());
                                    textoSalida.writeBoolean(false);
                                }
                            }
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
                Client client= (Client) dataIS.readObject();
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
                clienteEnSesion=c;
                sesionIniciada=true;
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
                Client client= (Client) dataIS.readObject();
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
            textoSalida= new DataOutputStream(cliente.getOutputStream());
            textoSalida.writeBoolean(true);
            textoSalida.writeUTF("Cliente nuevo creado correctamente");
            sesionIniciada=true;
        }else{

            textoSalida= new DataOutputStream(cliente.getOutputStream());
            textoSalida.writeBoolean(false);
            if(clienteRepe){
                textoSalida.writeUTF("El cliente que quiere crear ya está guardado, " +
                        "si quiere crear uno nuevo escribalo con otro nombre o apellido");
            }
            if(userNombreRepe){
                textoSalida.writeUTF("El cliente que quiere crear tiene un nombre de usuario ya existenete," +
                        "si quiere crear el usuario escribalo con un nombre de usuario diferente");
            }
        }
        dataOS.close();
    }
    public static void operacionesCuenta(){

        do {
            try {
                DataInputStream op2 =new DataInputStream(cliente.getInputStream());
                opcion2= op2.readInt();
                switch(opcion2){
                    case 1: {
                        Random random = new Random();
                        int lowerBound = 0;
                        int upperBound = 9;
                        String numCuenta="ES10";
                        Boolean numCuentaBalida=false;
                        while(!numCuentaBalida){
                            for (int i = 0; i < 20; i++) {
                                int randomNumber = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
                                numCuenta+=randomNumber;
                            }
                            if(clienteEnSesion.getCuentas().isEmpty()){
                                numCuentaBalida=true;
                            }else{
                                for (Cuenta c: clienteEnSesion.getCuentas()) {
                                    if(c.getNumeroCuenta().equals(numCuenta)){
                                        break;
                                    }
                                }
                            }
                        }
                        Cuenta c=new Cuenta(numCuenta, 00.00F);
                        clienteEnSesion.addCuentas(c);
                        modClient();
                        textoSalida= new DataOutputStream(cliente.getOutputStream());
                        textoSalida.writeBoolean(true);

                        break;
                    }
                    case 2: {


                        break;
                    }
                    case 3: {


                        break;
                    }
                    case 4: {


                        break;
                    }
                }

            } catch (NumberFormatException ex){
                System.out.println("Tienes que escribir un numero");
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        } while (opcion2!=4);

    }

    public static void modClient() throws IOException {
        Client c = null;
        File fichero = new File("src/Ficheros/Clientes.dat");
        FileInputStream filein = new FileInputStream(fichero);
        ObjectInputStream dataIS = new ObjectInputStream(filein);
        // aqui estaran los nuevos datos
        File ficheroaux = new File("src/Ficheros/AuxClientes.dat");
        FileOutputStream fileout = new FileOutputStream(ficheroaux);
        ObjectOutputStream dataOS = new ObjectOutputStream(fileout);
        // recuperar argumentos de main
        //String nombre = "Victoria";  nombre
        //int depmodif = Integer.parseInt(numerodep);
        int clienteexiste = 0;
        try {
            while (dataIS.available() != -1) { // lectura del fichero
                c = (Client) dataIS.readObject();
                if (Objects.equals(c.getNombre(), clienteEnSesion.getNombre()) && Objects.equals(c.getApellido(), clienteEnSesion.getApellido()) ) {
                    //System.out.println("Datos ANTIGUOS objeto: \n" + a.toString());
                    c.setCuentas(clienteEnSesion.getCuentas());
                    clienteexiste = 1;
                }

                Client c2 = new Client(c.getNombre(),c.getApellido(),c.getEdad(),c.getEmail(),c.getUser());
                c2.setCuentas(c2.getCuentas());
                dataOS.writeObject(c2); // inserto en fichero auxiliar
            }
        }catch (Exception e) {
            // Se produce EOFException al finalizar la lectura

            if (clienteexiste > 0) {
                crearNuevoCliente();
                ListadoNuevo(c);
            } else {
                System.out.println("===================================");
                System.out.println("El aldeano no existe");
                System.out.println("===================================");
            }
        }
    }
    public static void crearNuevoCliente() throws IOException {
        Client c;
        // Leo auxiliar e inserto en Departamentos
        File fichero = new File("src/Ficheros/AuxClientes.dat");
        FileInputStream filein = new FileInputStream(fichero);
        ObjectInputStream dataIS = new ObjectInputStream(filein);
        // aquí estarán los nuevos datos
        File ficheroaux = new File("src/Ficheros/Clientes.dat");
        FileOutputStream fileout = new FileOutputStream(ficheroaux);
        ObjectOutputStream dataOS = new ObjectOutputStream(fileout);
        try {
            while (true) { // lectura del fichero
                c = (Client) dataIS.readObject();
                // Leo fichero auxiliar
                Client a2 = new Client(c.getNombre(),c.getApellido(),c.getEdad(),c.getEmail(),c.getUser());
                a2.setCuentas(c.getCuentas());
                // inserto en nuevo fichero de Departamentos
                dataOS.writeObject(a2);
            }
        } catch (Exception e) {
        }
        // Se produce EOFException al finalizar la lectura
        dataIS.close(); // cerrar stream de entrada
        dataOS.close(); // cerrar stream de SALIDA
    }// fin Crear Nuevo Dep

    public static void ListadoNuevo(Client client) throws IOException {
        File fichero = new File("src/Ficheros/Aldeano.dat");
        FileInputStream filein = new FileInputStream(fichero);
        ObjectInputStream dataIS = new ObjectInputStream(filein);

        try {
            while (true) {
                Client a = (Client) dataIS.readObject();
                if (Objects.equals(a.getNombre(), client.getNombre()) && Objects.equals(a.getApellido(), client.getApellido()) ) {
                    System.out.println(a.toString());
                }
            }
        } catch (EOFException | ClassNotFoundException eo) {
            System.out.println("Fin de lectura");
        }
        System.out.println("=======================================================");
        dataIS.close(); //Cerramos el flujo de entrada
    }


}
