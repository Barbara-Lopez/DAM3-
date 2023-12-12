import modelos.*;

import javax.crypto.Cipher;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.security.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Hilo que se encarga de hacer todo lo del servidor
 */
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
                                log(clienteEnSesion.getUser().getUsuario()+" ha iniciado sesión ");
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
                                    log(clienteEnSesion.getUser().getUsuario()+" ha firmado correctamente");
                                    System.out.println("FiRMA VERIFICADA");
                                    textoSalida= new DataOutputStream(cliente.getOutputStream());
                                    textoSalida.writeBoolean(true);
                                    operacionesCuenta();
                                } else {
                                    log(clienteEnSesion.getUser().getUsuario()+" no ha firmado correctamente para poder hacer opreraciones ");
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
    /**
     * Como el nombre de la funcion indica se encarga de recibir la clave publica del cliente
     * y enviar la suya al clliente
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void enviarRecibirClavePub() throws IOException, ClassNotFoundException {
        objetoSalida = new ObjectOutputStream(cliente.getOutputStream());
        objetoSalida.writeObject(clavepub);
        //System.out.println("Clave server:\n"+clavepub);
        objetoEntrada =new ObjectInputStream(cliente.getInputStream());
        clavepubClient= (PublicKey) objetoEntrada.readObject();
        //System.out.println("Clave cliente:\n"+clavepubClient);

    }

    /**
     * Se encarga de verificar si el fichero donde se guardan los datos existe, si no existe lo crea
     * @throws IOException
     */
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

    /**
     * verifica que el usuario y contraseña que recibimos del cliente sea correcto
     * @param user
     * @throws IOException
     */
    public static void inicioSesion(Usuario user) throws IOException {

        File fichero = new File("src/files/Clientes.dat");
        FileInputStream fileout = new FileInputStream(fichero);
        ObjectInputStream dataIS = new ObjectInputStream(fileout);
        Client c= null;
        try {
            while (dataIS.available() != -1 ) {
                Client client= (Client) dataIS.readObject();
                //System.out.println(client);
                if(client.getUser().getUsuario().equalsIgnoreCase(user.getUsuario())){
                    c=client;
                    break;
                }

            }

        }catch (EOFException | ClassNotFoundException eo){}

        dataIS.close();
        textoSalida= new DataOutputStream(cliente.getOutputStream());
        if(c!=null){
            String contrasenaGuardada= Arrays.toString(c.getUser().getContrasena());
            String contrasenaEnviada=Arrays.toString(user.getContrasena());
            if(Objects.equals(contrasenaGuardada, contrasenaEnviada)){
                textoSalida.writeBoolean(true);
                clienteEnSesion=c;
                sesionIniciada=true;
            }else{
                sesionIniciada=false;
                textoSalida.writeBoolean(false);
            }
        }else{
            sesionIniciada=false;
            textoSalida.writeBoolean(false);
        }
    }

    /**
     * Guarda el cliente en el caso de que no exista y si el usuario no está repetido
     * @param cli
     * @throws IOException
     */
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
            List<Client> lista= cogerAldeanos();
            lista.add(cli);
            for (Client persona:lista) {
                dataOS.writeObject(persona);
            }
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

    /**
     * coge todos los usuarios para que al escribir un dato no se sobreescriba
     * @return
     * @throws IOException
     */
    public static List<Client> cogerAldeanos() throws IOException {
        File fichero = new File("src/files/Clientes.dat");
        FileInputStream fileout = new FileInputStream(fichero);
        ObjectInputStream dataIS = new ObjectInputStream(fileout);

        List<Client> listaper = new ArrayList<>();
        try {
            while (true) { //lectura del fichero
                Client persona= (Client) dataIS.readObject(); //leer una Persona
                listaper.add(persona); //añaadir persona a la lista
            }
        }catch (EOFException eo){} catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        dataIS.close();
        return listaper;
    }

    /**
     * Una vez se inicia sesión correctamente hace que las operaciones se guarden en los ficheros
     * de esa manera tenemos la información actualizada
     */
    public static void operacionesCuenta(){

        do {
            try {
                DataInputStream op2 =new DataInputStream(cliente.getInputStream());
                opcion2= op2.readInt();
                switch(opcion2){
                    case 1: {
                        System.out.println("opcion 1");
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
                                Boolean repe=false;
                                for (Cuenta c: clienteEnSesion.getCuentas()) {
                                    if(c.getNumeroCuenta().equals(numCuenta)){
                                        repe=true;
                                        break;
                                    }
                                }
                                if(!repe) {
                                    numCuentaBalida = true;
                                }
                            }
                        }
                        //System.out.println(numCuenta);
                        Cuenta c=new Cuenta(numCuenta, 00.00F);
                        clienteEnSesion.addCuentas(c);
                        //System.out.println(clienteEnSesion.getCuentas());
                        modClient();
                        textoSalida= new DataOutputStream(cliente.getOutputStream());
                        textoSalida.writeBoolean(true);
                        objetoSalida = new ObjectOutputStream(cliente.getOutputStream());
                        objetoSalida.writeObject(c);
                        log(clienteEnSesion.getUser().getUsuario()+" ha creado una nueva cuenta bancaria, "+c.getNumeroCuenta());
                        break;
                    }
                    case 2: {
                        //System.out.println("opcion 2");
                        String cuentas="";
                        if(clienteEnSesion.getCuentas().isEmpty()){
                            log(clienteEnSesion.getUser().getUsuario()+" ha intentado mirar el saldo de una cuenta pero no tiene ninguna");
                            cuentas="No hay cuentas crea una";
                            textoSalida= new DataOutputStream(cliente.getOutputStream());
                            textoSalida.writeBoolean(false);

                            textoSalida.writeUTF(cuentas);
                        }else{
                            for (Cuenta c:clienteEnSesion.getCuentas()) {
                                cuentas+=c.getNumeroCuenta()+", ";
                            }
                            textoSalida= new DataOutputStream(cliente.getOutputStream());
                            textoSalida.writeBoolean(true);
                            textoSalida.writeUTF(cuentas);

                            objetoEntrada =new ObjectInputStream(cliente.getInputStream());
                            byte[] cuenta= (byte[]) objetoEntrada.readObject();

                            rsaCipher.init(Cipher.DECRYPT_MODE, clavepriv);
                            String mensajeDescifrado = new String(rsaCipher.doFinal(cuenta));
                            Cuenta verCuenta=null;
                            for (Cuenta c:clienteEnSesion.getCuentas()) {
                                if(c.getNumeroCuenta().equals(mensajeDescifrado)){
                                    verCuenta=c;
                                    break;
                                }
                            }

                            textoSalida= new DataOutputStream(cliente.getOutputStream());
                            if(verCuenta==null){
                                log(clienteEnSesion.getUser().getUsuario()+" ha intentado mirar el saldo de una cuenta");
                                System.out.println("Cuenta NO encontrada");
                                textoSalida.writeBoolean(false);
                            }else{
                                log(clienteEnSesion.getUser().getUsuario()+" ha mirado el saldo de la cuenta "+ verCuenta.getNumeroCuenta());
                                System.out.println("Cuenta encontrada");
                                textoSalida.writeBoolean(true);
                                objetoSalida = new ObjectOutputStream(cliente.getOutputStream());
                                objetoSalida.writeObject(verCuenta);
                            }
                        }
                        break;
                    }
                    case 3: {
                        String logCliente=cogerLog();
                        textoSalida= new DataOutputStream(cliente.getOutputStream());
                        textoSalida.writeUTF(logCliente);
                        break;
                    }
                    case 4: {
                        System.out.println("punto 4");
                        String cuentas="";
                        if(clienteEnSesion.getCuentas().isEmpty()){
                            log(clienteEnSesion.getUser().getUsuario()+" ha intentado hacer transferencias pero no tirnr ninguna cuenta hecha");
                            cuentas="No hay cuentas crea una";
                            textoSalida= new DataOutputStream(cliente.getOutputStream());
                            textoSalida.writeBoolean(false);
                            textoSalida.writeUTF(cuentas);
                        }else {
                            for (Cuenta c : clienteEnSesion.getCuentas()) {
                                cuentas += c.getNumeroCuenta() + ", ";
                            }
                            textoSalida = new DataOutputStream(cliente.getOutputStream());
                            textoSalida.writeBoolean(true);
                            textoSalida.writeUTF(cuentas);

                            objetoEntrada = new ObjectInputStream(cliente.getInputStream());
                            byte[] cuenta = (byte[]) objetoEntrada.readObject();


                            textoEntrada = new DataInputStream(cliente.getInputStream());
                            Float saldo = op2.readFloat();


                            Random random = new Random();
                            int lowerBound = 0;
                            int upperBound = 9;
                            String cod = "";
                            for (int i = 0; i < 4; i++) {
                                int randomNumber = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
                                cod += randomNumber;
                            }
                            System.out.println(cod);
                            rsaCipher.init(Cipher.ENCRYPT_MODE, clavepubClient);
                            byte[] cifrado = rsaCipher.doFinal(cod.getBytes());
                            objetoSalida = new ObjectOutputStream(cliente.getOutputStream());
                            objetoSalida.writeObject(cifrado);


                            textoEntrada = new DataInputStream(cliente.getInputStream());
                            String codDescifrado = op2.readUTF();

                            if (cod.equals(codDescifrado)) {

                                rsaCipher.init(Cipher.DECRYPT_MODE, clavepriv);
                                String mensajeDescifrado = new String(rsaCipher.doFinal(cuenta));
                                Cuenta verCuenta = null;
                                for (Cuenta c : clienteEnSesion.getCuentas()) {
                                    if (c.getNumeroCuenta().equals(mensajeDescifrado)) {
                                        c.setSaldo(c.getSaldo() + saldo);
                                        verCuenta = c;
                                        break;
                                    }
                                }
                                System.out.println(clienteEnSesion.getCuentas());
                                modClient();
                                textoSalida = new DataOutputStream(cliente.getOutputStream());
                                if (verCuenta == null) {
                                    log(clienteEnSesion.getUser().getUsuario()+" ha intentado hacer una tranferencia");
                                    System.out.println("Cuenta NO encontrada");
                                    textoSalida.writeBoolean(false);
                                    textoSalida.writeUTF("Cuenta NO encontrada");
                                } else {
                                    textoSalida.writeBoolean(true);
                                    objetoSalida = new ObjectOutputStream(cliente.getOutputStream());
                                    objetoSalida.writeObject(verCuenta);
                                    log(clienteEnSesion.getUser().getUsuario()+" ha hecho una transferencia a la cuenta "+verCuenta.getNumeroCuenta()
                                     +" de "+saldo+" y ahora tiene " +verCuenta.getSaldo());
                                }
                            }else{
                                log(clienteEnSesion.getUser().getUsuario()+" ha intentado hacer una tranferencia");
                                textoSalida = new DataOutputStream(cliente.getOutputStream());
                                textoSalida.writeBoolean(false);
                                textoSalida.writeUTF("El codigo descifrado no es correcto");
                            }
                        }

                        break;
                    }
                    case 5: {
                        log(clienteEnSesion.getUser().getUsuario()+" ha salido de las operaciones ");
                        System.out.println(clienteEnSesion.getNombre()+" Sale de las operaciones de la app");
                        break;
                    }
                }

            } catch (NumberFormatException ex){
                System.out.println("Tienes que escribir un numero");
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        } while (opcion2!=5);

    }

    public static void modClient() throws IOException {
        Client c = null;
        File fichero = new File("src/files/Clientes.dat");
        FileInputStream filein = new FileInputStream(fichero);
        ObjectInputStream dataIS = new ObjectInputStream(filein);
        // aqui estaran los nuevos datos
        File ficheroaux = new File("src/files/AuxClientes.dat");
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
                    System.out.println("Datos ANTIGUOS objeto: \n" + c.toString());
                    c.setCuentas(clienteEnSesion.getCuentas());
                    clienteexiste = 1;
                }

                Client c2 = new Client(c.getNombre(),c.getApellido(),c.getEdad(),c.getEmail(),c.getUser());
                c2.setCuentas(c.getCuentas());
                dataOS.writeObject(c2); // inserto en fichero auxiliar
            }
        }catch (Exception e) {
            // Se produce EOFException al finalizar la lectura

            if (clienteexiste > 0) {
                crearNuevoCliente();
                ListadoNuevo(c);
            } else {
                System.out.println("===================================");
                System.out.println("El cliente no existe");
                System.out.println("===================================");
            }
        }
    }
    public static void crearNuevoCliente() throws IOException {
        Client c;
        // Leo auxiliar e inserto en Departamentos
        File fichero = new File("src/files/AuxClientes.dat");
        FileInputStream filein = new FileInputStream(fichero);
        ObjectInputStream dataIS = new ObjectInputStream(filein);
        // aquí estarán los nuevos datos
        File ficheroaux = new File("src/files/Clientes.dat");
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
        File fichero = new File("src/files/Clientes.dat");
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
        }

        dataIS.close(); //Cerramos el flujo de entrada
    }
    public static void log(String testo) throws IOException {
        // Crea un objeto File con la ruta del archivo
        File archivo = new File("src/files/"+clienteEnSesion.getUser().getUsuario()+"log");

        // Verifica si el archivo existe
        if (!archivo.exists()) {
            System.out.println("Archivo log cliente no encontrado, se va ha proceder a crearlo");
            FileOutputStream fos = new FileOutputStream(archivo);
            // Cierra el flujo de salida
            fos.close();
            System.out.println("Archivo log cliente creado");
        }
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        String text=cogerLog();
        text+=formattedDate+" "+testo;
        FileWriter f = new FileWriter ("src/files/"+clienteEnSesion.getUser().getUsuario()+"log");
        f.write(text);
        f.close();
    }
    public static String cogerLog(){
        String text="";
        try {
            FileReader fr = new FileReader("src/files/"+clienteEnSesion.getUser().getUsuario()+"log");
            BufferedReader br = new BufferedReader(fr);

            String linea;

            while((linea = br.readLine())!= null)
                text+=linea+"\n";

            br.close();
        }
        catch (FileNotFoundException fn) {
            System.out.println("Error de lectura");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return text;
    }

}
