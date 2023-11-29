package Certificado;

import FirmaDigital.Mensaje;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.security.*;

public class Cliente {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, ClassNotFoundException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        System.setProperty("javax.net.ssl.trustStore", "src/Certificado/files/UsuarioAlmacenSSL");
        System.setProperty("javax.net.ssl.trustStorePassword", "890123");

        String host="localhost";
        int puerto=6000;
        System.out.println("Programa cliente iniciado..");
        SSLSocketFactory sfact=(SSLSocketFactory)SSLSocketFactory.getDefault();
        SSLSocket Cliente=(SSLSocket) sfact.createSocket(host,puerto);

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        //SE CREA EL PAR DE CLAVES PRIVADA Y PÚBLICA
        KeyPair par = keyGen.generateKeyPair();
        PrivateKey clavepriv = par.getPrivate();
        PublicKey clavepub = par.getPublic();
        Cipher rsaCipher = Cipher.getInstance("RSA");

        System.out.println(clavepub);

        ObjectInputStream objetoEntrada2=new ObjectInputStream(Cliente.getInputStream());
        PublicKey clavepubServer= (PublicKey) objetoEntrada2.readObject();

        ObjectOutputStream clavePublica= new ObjectOutputStream(Cliente.getOutputStream());
        clavePublica.writeObject(clavepub);

        rsaCipher.init(Cipher.ENCRYPT_MODE, clavepubServer);
        String mensaje = "Hola servidor";
        byte[] cifrado = rsaCipher.doFinal(mensaje.getBytes());
        //Creo flujo de salida
        ObjectOutputStream flujoSalida= new ObjectOutputStream
                (Cliente.getOutputStream());

        //envio texto al servidor
        flujoSalida.writeObject(cifrado);

        //creo flujo de entrada del server
        ObjectInputStream flujoEntrada= new ObjectInputStream (Cliente.getInputStream());
        byte[]  mensajeCliente = (byte[] ) flujoEntrada.readObject();
        rsaCipher.init(Cipher.DECRYPT_MODE, clavepriv);
        String mensajeDescifrado = new String(rsaCipher.doFinal(mensajeCliente));
        //Leo la informacion del server
        System.out.println("Recibiendo del server \t\n"+ mensajeDescifrado);

        //cerrar
        flujoEntrada.close();
        flujoSalida.close();
        Cliente.close();

    }
}
