import modelos.Client;
import modelos.Cuenta;
import modelos.Mensaje;
import modelos.Usuario;

import javax.crypto.Cipher;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.security.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Se encarga de ejecutar lo que el cliente necesita para poder hacer las operaciones
 */
public class Cliente {
    private static PublicKey clavepubServer;
    private static SSLSocket cliente;
    private static PrivateKey clavepriv;
    private static ObjectInputStream objetoEntrada;
    private static ObjectOutputStream objetoSalida;
    private static PublicKey clavepub;
    private static  Cipher rsaCipher;
    private static Scanner lectura = new Scanner(System.in);
    private static DataOutputStream textoSalida;
    private static DataInputStream textoEntrada;

    /**
     *
     * @param args
     */
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

            int op = 0;
            do {
                try {
                    System.out.println("Elija lo que quiere hacer: \n 1.Iniciar sesión \n 2.Crear cuenta \n 3.Salir");
                    op = Integer.parseInt(lectura.next());
                    switch(op){
                        case 1: {
                            // ENviar al servidor que opcion ha elegido
                            DataOutputStream op1= new DataOutputStream(cliente.getOutputStream());
                            op1.writeInt(1);
                            // función para iniciar sesion
                            inicioSesion();
                            // flujo de entrada que se utiliza para recoger si el usuario y contraseña es correcto
                            textoEntrada =new DataInputStream(cliente.getInputStream());
                            Boolean inicioOk= textoEntrada.readBoolean();
                            if(inicioOk){
                                textoEntrada =new DataInputStream(cliente.getInputStream());
                                String textoFirmar= textoEntrada.readUTF();
                                while(true){
                                    System.out.println("El siguiente texto hay que firmarlo, escriba 'si' en el caso de querer firmarlo:\n" +
                                            textoFirmar);
                                    String firma = lectura.next();
                                    if(firma.equalsIgnoreCase("si")){
                                        Signature rsaSignature = Signature.getInstance("SHA256withRSA");
                                        rsaSignature.initSign(clavepriv);
                                        rsaSignature.update(textoFirmar.getBytes());
                                        byte[] firmaHecha = rsaSignature.sign();
                                        ObjectOutputStream firmaDigital= new ObjectOutputStream(cliente.getOutputStream());
                                        firmaDigital.writeObject(new Mensaje(textoFirmar,firmaHecha));
                                        break;
                                    }else{
                                        ObjectOutputStream firmaDigital= new ObjectOutputStream(cliente.getOutputStream());
                                        byte[] firmaHecha = "No se ha firmado".getBytes();
                                        firmaDigital.writeObject(new Mensaje(textoFirmar,firmaHecha));
                                        break;
                                    }
                                }
                                textoEntrada =new DataInputStream(cliente.getInputStream());
                                Boolean firma= textoEntrada.readBoolean();
                                if(firma){
                                    System.out.println("Firmado correctamente");
                                    operacionesCuenta();
                                }else{
                                    System.out.println("Fallo en la firma, no puede hacer ninguna operación");
                                }
                            }else{
                                System.out.println("Usuario o contraseña incorrecto");
                            }
                            break;
                        }
                        case 2: {
                            // ENviar al servidor que opcion ha elegido
                            DataOutputStream  op1= new DataOutputStream(cliente.getOutputStream());
                            op1.writeInt(2);
                            // función para crear una nueva cuenta
                            crearCuenta();
                            // flujo de entrada que se utiliza para recoger si la nueva cuenta se ha ceado correctamente
                            textoEntrada =new DataInputStream(cliente.getInputStream());
                            Boolean inicioOk= textoEntrada.readBoolean();
                            String testo=textoEntrada.readUTF();
                            if(inicioOk){
                                System.out.println(testo);
                                System.out.println("Inicia sesión con el usuario y contraseña que acaba de crear");
                            }else{
                                System.out.println(testo);
                            }
                            break;
                        }
                        case 3: {
                            // ENviar al servidor que opcion ha elegido
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
            cliente.close();

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Como el nombre de la funcion indica se encarga de recibir la clave publica del servidor
     * y enviar la suya al servidor
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void enviarRecibirClavePub() throws IOException, ClassNotFoundException {
        objetoEntrada=new ObjectInputStream(cliente.getInputStream());
        clavepubServer= (PublicKey) objetoEntrada.readObject();
        //System.out.println("Clave server:\n"+clavepubServer);
        objetoSalida = new ObjectOutputStream(cliente.getOutputStream());
        objetoSalida.writeObject(clavepub);
        //System.out.println("Clave cliente:\n"+clavepub);
    }

    /**
     * Envia al servidor el usuario y contraseña que nos teclean
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static void inicioSesion() throws IOException, NoSuchAlgorithmException {
        String user;
        while (true) {
            try{
                System.out.println("Escriba el usuario: ");
                user = lectura.next();
                break;
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        String contrasena;
        while (true) {
            try{
                System.out.println("Escriba la contraseña: ");
                contrasena = lectura.next();
                break;
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update(contrasena.getBytes());
        byte resumen[]=md.digest();

        Usuario u=new Usuario(user,resumen);
        objetoSalida = new ObjectOutputStream(cliente.getOutputStream());
        objetoSalida.writeObject(u);
    }

    /**
     * Se encarga de enviar al servidor toda la informacion del cliente que nosteclean
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static void crearCuenta() throws NoSuchAlgorithmException, IOException {
        String nombre;
        while (true) {
            try{
                System.out.println("Escriba el nombre: ");
                nombre = lectura.next();
                if(nombre.isBlank()){
                    throw new Exception("El nombre no puede estar vacio");
                }
                break;
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        String apellido;
        while (true) {
            try{
                System.out.println("Escriba el apellido: ");
                apellido = lectura.next();
                if(apellido.isBlank()){
                    throw new Exception("El apellido no puede estar vacio");
                }
                break;
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        Integer edad;
        while (true) {
            try{
                System.out.println("Escriba la edad: ");
                edad = Integer.parseInt(lectura.next());
                if(edad<18){
                    throw new Exception("Tiene que escribir un numero mayor o igual al 18 para poder ser un cliente");
                }
                break;
            }catch (NumberFormatException e) {
                System.out.println("Tiene que escribir un numero que corresponda contu edad");
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        String email;
        while (true) {
            try{
                System.out.println("Escriba el email: ");
                email = lectura.next();
                Pattern p = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
                Matcher m = p.matcher(email);
                if(!m.find()){
                    throw new Exception("El email escrito no es correcto, tiene que ser: \n " +
                            " 1. 'nimino de 1 caracter'@'nimino de 2 caracteres'.'minimo de 2 caracteres'\n" +
                            " 2. 'nimino de 1 caracter'.'nimino de 1 caracter'@'minimo de 2 caracteres'.'minimo de 2 caracteres' \n" +
                            " vuelva ha escribirlo");
                }
                break;
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        String user;
        while (true) {
            try{
                System.out.println("Escriba el usuario: ");
                user = lectura.next();
                if(user.isBlank()){
                    throw new Exception("El nombre del usuario no puede estar vacio");
                }
                break;
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        String contrasena;
        while (true) {
            try{
                System.out.println("Escriba la contraseña: ");
                contrasena = lectura.next();
                if(contrasena.isBlank()){
                    throw new Exception("La contraseña no puede estar vacia");
                }
                break;
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update(contrasena.getBytes());
        byte resumen[]=md.digest();

        Usuario u=new Usuario(user,resumen);
        Client c=new Client(nombre,apellido,edad,email,u);
        objetoSalida = new ObjectOutputStream(cliente.getOutputStream());
        objetoSalida.writeObject(c);
    }

    /**
     * Una vez inician sesión se encarga de hacer que las operaciones que quiere el usuario se envien al servidor
     */
    public static void operacionesCuenta(){
        int op = 0;
        do {
            try {
                System.out.println("Elija lo que quiere hacer: \n 1.Crear cuenta bancaria \n 2.Ver saldo de una cuenta bancaria " +
                        "\n 3.Mirar el registro de operaciones hechas \n 4.Ingresar dinero \n 5.Hacer transferencia \n 6.Salir");
                op = Integer.parseInt(lectura.next());
                switch(op){
                    case 1: {
                        // ENviar al servidor que opcion ha elegido
                        DataOutputStream op1= new DataOutputStream(cliente.getOutputStream());
                        op1.writeInt(1);
                        // flujo de entrada que se utiliza para recoger si el usuario y contraseña es correcto
                        textoEntrada =new DataInputStream(cliente.getInputStream());
                        Boolean creacionOk= textoEntrada.readBoolean();
                        objetoEntrada=new ObjectInputStream(cliente.getInputStream());
                        Cuenta c = (Cuenta) objetoEntrada.readObject();
                        if(creacionOk){
                            System.out.println("Cuenta creada correctamente, el numero de la cuenta es: "+
                                    c.getNumeroCuenta()+" y se crea con un saldo de 0€");
                        }else{
                            System.out.println("Ha ocurrido algun error al hacer la cuenta");
                        }
                        break;
                    }
                    case 2: {
                        // ENviar al servidor que opcion ha elegido
                        DataOutputStream  op1= new DataOutputStream(cliente.getOutputStream());
                        op1.writeInt(2);
                        // elegir la cuenta
                        textoEntrada =new DataInputStream(cliente.getInputStream());
                        Boolean ok= textoEntrada.readBoolean();
                        String c= textoEntrada.readUTF();
                        if(ok) {
                            while (true) {
                                try {
                                    String text = "Elija una de las cuentas (escriba el numero al lado del numero de la cuenta):\n";
                                    String[] listaCuenta = c.split(", ");
                                    for (int i = 0; i < listaCuenta.length; i++) {
                                        text += i + ". " + listaCuenta[i] + "\n";
                                    }
                                    System.out.println(text);
                                    Integer cuenta = Integer.parseInt(lectura.next());
                                    if (cuenta < listaCuenta.length) {
                                        String numCuenta = listaCuenta[cuenta];
                                        rsaCipher.init(Cipher.ENCRYPT_MODE, clavepubServer);
                                        byte[] cifrado = rsaCipher.doFinal(numCuenta.getBytes());
                                        objetoSalida = new ObjectOutputStream(cliente.getOutputStream());
                                        objetoSalida.writeObject(cifrado);
                                        break;
                                    } else {
                                        throw new Exception("Escriba uno de los numeros al lado del numero de la cuenta");
                                    }
                                } catch (NumberFormatException ex) {
                                    System.out.println("Tienes que escribir un numero");
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            // flujo de entrada que se utiliza para recoger si la nueva cuenta se ha
                            // encontrado correctamente y la muestra
                            textoEntrada = new DataInputStream(cliente.getInputStream());
                            Boolean inicioOk = textoEntrada.readBoolean();
                            if (inicioOk) {
                                objetoEntrada = new ObjectInputStream(cliente.getInputStream());
                                Cuenta cuenta = (Cuenta) objetoEntrada.readObject();
                                System.out.println(cuenta.toString());
                            } else {
                                System.out.println("No se ha encontrado la cuenta");
                            }
                        }else{
                            System.out.println(c);
                        }
                        break;
                    }
                    case 3: {
                        // ENviar al servidor que opcion ha elegido
                        DataOutputStream  op1= new DataOutputStream(cliente.getOutputStream());
                        op1.writeInt(3);
                        textoEntrada =new DataInputStream(cliente.getInputStream());
                        String log= textoEntrada.readUTF();
                        System.out.println(log);

                        break;
                    }
                    case 4: {
                        // ENviar al servidor que opcion ha elegido
                        DataOutputStream  op1= new DataOutputStream(cliente.getOutputStream());
                        op1.writeInt(4);

                        // elegir la cuenta
                        textoEntrada =new DataInputStream(cliente.getInputStream());
                        Boolean ok= textoEntrada.readBoolean();
                        String c= textoEntrada.readUTF();
                        if(ok) {
                            while (true) {
                                try {
                                    String text = "Elija una de las cuentas a ingresar dinero (escriba el numero al lado del numero de la cuenta):\n";
                                    String[] listaCuenta = c.split(", ");
                                    for (int i = 0; i < listaCuenta.length; i++) {
                                        text += i + ". " + listaCuenta[i] + "\n";
                                    }
                                    System.out.println(text);
                                    Integer cuenta = Integer.parseInt(lectura.next());
                                    if (cuenta < listaCuenta.length) {
                                        String numCuenta = listaCuenta[cuenta];
                                        rsaCipher.init(Cipher.ENCRYPT_MODE, clavepubServer);
                                        byte[] cifrado = rsaCipher.doFinal(numCuenta.getBytes());
                                        objetoSalida = new ObjectOutputStream(cliente.getOutputStream());
                                        objetoSalida.writeObject(cifrado);
                                        break;
                                    } else {
                                        throw new Exception("Escriba uno de los numeros al lado del numero de la cuenta");
                                    }
                                } catch (NumberFormatException ex) {
                                    System.out.println("Tienes que escribir un numero");
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            Float ingresos = null;
                            while (true) {
                                try {
                                    System.out.println("Escriba en numeros cual es la cantidad que quiere ingresar:");
                                    String saldo = lectura.next();
                                    Pattern p = Pattern.compile("^[0-9]{1,3}.[0-9]{2}$");
                                    Matcher m = p.matcher(saldo);
                                    if(m.find()) {
                                        ingresos= Float.valueOf(saldo);
                                        break;
                                    }else{
                                        throw new Exception("Tiene que escribir '0.00' o '00.00' ");
                                    }
                                } catch (NumberFormatException ex) {
                                    System.out.println("Tienes que escribir un numero");
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            textoSalida= new DataOutputStream(cliente.getOutputStream());
                            op1.writeFloat(ingresos);

                            objetoEntrada = new ObjectInputStream(cliente.getInputStream());
                            byte[] cuentaCifrada= (byte[]) objetoEntrada.readObject();
                            while (true) {
                                try {
                                    System.out.println("Acaba de recibir un codigo cifrado\n Cod: " +
                                            cuentaCifrada +"\n"+
                                            "Si quiere descifrarla y enviarla para verificar que es usted escriba 'si':");
                                    String respuesta = lectura.next();

                                    if(respuesta.equalsIgnoreCase("si")) {
                                        rsaCipher.init(Cipher.DECRYPT_MODE, clavepriv);
                                        String mensajeDescifrado = new String(rsaCipher.doFinal(cuentaCifrada));
                                        System.out.println("Cod descifrado: "+mensajeDescifrado);
                                        textoSalida= new DataOutputStream(cliente.getOutputStream());
                                        op1.writeUTF(mensajeDescifrado);
                                    }else{
                                        textoSalida= new DataOutputStream(cliente.getOutputStream());
                                        op1.writeUTF("");
                                    }
                                    break;
                                } catch (NumberFormatException ex) {
                                    System.out.println("Tienes que escribir un numero");
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            // flujo de entrada que se utiliza para recoger si la nueva cuenta se ha
                            // encontrado correctamente y la muestra
                            textoEntrada = new DataInputStream(cliente.getInputStream());
                            Boolean inicioOk = textoEntrada.readBoolean();
                            if (inicioOk) {
                                objetoEntrada = new ObjectInputStream(cliente.getInputStream());
                                Cuenta cuenta = (Cuenta) objetoEntrada.readObject();
                                System.out.println(cuenta.toString());
                            } else {
                                String text = textoEntrada.readUTF();
                                System.out.println(text);
                            }
                        }else{
                            System.out.println(c);
                        }
                        break;
                    }
                    case 5: {
                        // ENviar al servidor que opcion ha elegido
                        DataOutputStream  op1= new DataOutputStream(cliente.getOutputStream());
                        op1.writeInt(5);
                        // elegir la cuenta
                        textoEntrada =new DataInputStream(cliente.getInputStream());
                        Boolean ok= textoEntrada.readBoolean();
                        String c= textoEntrada.readUTF();
                        if(ok) {
                            String[] listaCuenta = c.split(", ");
                            ArrayList<String> lista = new ArrayList<>(java.util.Arrays.asList(listaCuenta));
                            String cuenta1="";
                            while (true) {
                                try {
                                    String text = "Elija una de las cuentas desde donde quiere hacer la transferencia  (escriba el numero al lado del numero de la cuenta):\n";

                                    for (int i = 0; i < listaCuenta.length; i++) {
                                        text += i + ". " + listaCuenta[i] + "\n";
                                    }
                                    System.out.println(text);
                                    Integer cuenta = Integer.parseInt(lectura.next());
                                    if (cuenta < listaCuenta.length) {
                                        String numCuenta = listaCuenta[cuenta];
                                        rsaCipher.init(Cipher.ENCRYPT_MODE, clavepubServer);
                                        byte[] cifrado = rsaCipher.doFinal(numCuenta.getBytes());
                                        objetoSalida = new ObjectOutputStream(cliente.getOutputStream());
                                        objetoSalida.writeObject(cifrado);
                                        if (lista.contains(listaCuenta[cuenta])) {
                                            // Eliminar el elemento
                                            cuenta1 =listaCuenta[cuenta];
                                            lista.remove(listaCuenta[cuenta]);
                                            //System.out.println("Elemento eliminado: " + listaCuenta[cuenta]);
                                        }
                                        break;
                                    } else {
                                        throw new Exception("Escriba uno de los numeros al lado del numero de la cuenta");
                                    }
                                } catch (NumberFormatException ex) {
                                    System.out.println("Tienes que escribir un numero");
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            while (true) {
                                try {
                                    String text = "Elija una de las cuentas a las que le quiere hacer la transferencia desde la cuenta "+cuenta1+"  (escriba el numero al lado del numero de la cuenta):\n";
                                    for (int i = 0; i < lista.size(); i++) {
                                        text += i + ". " + lista.get(i) + "\n";
                                    }
                                    System.out.println(text);
                                    Integer cuenta = Integer.parseInt(lectura.next());
                                    if (cuenta < listaCuenta.length) {
                                        String numCuenta = lista.get(cuenta);
                                        rsaCipher.init(Cipher.ENCRYPT_MODE, clavepubServer);
                                        byte[] cifrado = rsaCipher.doFinal(numCuenta.getBytes());
                                        objetoSalida = new ObjectOutputStream(cliente.getOutputStream());
                                        objetoSalida.writeObject(cifrado);
                                        break;
                                    } else {
                                        throw new Exception("Escriba uno de los numeros al lado del numero de la cuenta");
                                    }
                                } catch (NumberFormatException ex) {
                                    System.out.println("Tienes que escribir un numero");
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            Float ingresos = null;
                            while (true) {
                                try {
                                    System.out.println("Escriba en numeros cual es la cantidad que quiere transferir:");
                                    String saldo = lectura.next();
                                    Pattern p = Pattern.compile("^[0-9]{1,3}.[0-9]{2}$");
                                    Matcher m = p.matcher(saldo);
                                    if(m.find()) {
                                        ingresos= Float.valueOf(saldo);
                                        break;
                                    }else{
                                        throw new Exception("Tiene que escribir '0.00' o '00.00' ");
                                    }
                                } catch (NumberFormatException ex) {
                                    System.out.println("Tienes que escribir un numero");
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            textoSalida= new DataOutputStream(cliente.getOutputStream());
                            op1.writeFloat(ingresos);

                            objetoEntrada = new ObjectInputStream(cliente.getInputStream());
                            byte[] cuentaCifrada= (byte[]) objetoEntrada.readObject();
                            while (true) {
                                try {
                                    System.out.println("Acaba de recibir un codigo cifrado\n Cod: " +
                                            cuentaCifrada +"\n"+
                                            "Si quiere descifrarla y enviarla para verificar que es usted escriba 'si':");
                                    String respuesta = lectura.next();

                                    if(respuesta.equalsIgnoreCase("si")) {
                                        rsaCipher.init(Cipher.DECRYPT_MODE, clavepriv);
                                        String mensajeDescifrado = new String(rsaCipher.doFinal(cuentaCifrada));
                                        System.out.println("Cod descifrado: "+mensajeDescifrado);
                                        textoSalida= new DataOutputStream(cliente.getOutputStream());
                                        op1.writeUTF(mensajeDescifrado);
                                    }else{
                                        textoSalida= new DataOutputStream(cliente.getOutputStream());
                                        op1.writeUTF("");
                                    }
                                    break;
                                } catch (NumberFormatException ex) {
                                    System.out.println("Tienes que escribir un numero");
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                            // flujo de entrada que se utiliza para recoger si la nueva cuenta se ha
                            // encontrado correctamente y la muestra
                            textoEntrada = new DataInputStream(cliente.getInputStream());
                            Boolean inicioOk = textoEntrada.readBoolean();
                            if (inicioOk) {
                                objetoEntrada = new ObjectInputStream(cliente.getInputStream());
                                List<Cuenta> cuentas = (List<Cuenta>) objetoEntrada.readObject();
                                for (Cuenta cuenta:cuentas) {
                                    System.out.println(cuenta.toString());
                                }
                            } else {
                                String text = textoEntrada.readUTF();
                                System.out.println(text);
                            }
                        }else{
                            System.out.println(c);
                        }
                        break;
                    }
                    case 6: {
                        // ENviar al servidor que opcion ha elegido
                        DataOutputStream  op1= new DataOutputStream(cliente.getOutputStream());
                        op1.writeInt(6);
                        break;
                    }
                    default:
                        System.out.println("Escriba un numero del 1 al 4");
                }

            } catch (NumberFormatException ex){
                System.out.println("Tienes que escribir un numero");
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        } while (op!=6);

    }
}
