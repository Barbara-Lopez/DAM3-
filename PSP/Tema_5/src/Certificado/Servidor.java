package Certificado;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.security.*;

public class Servidor {
    public static void main(String[] args) {
        System.setProperty("javax.net.ssl.keyStore","src/Certificado/files/AlmacenSSL.jks");
        System.setProperty("javax.net.ssl.keyStorePassword","12345Abcde");
        try {
            int puerto = 6000;
            SSLServerSocketFactory sfact = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            SSLServerSocket servidorSSL = (SSLServerSocket) sfact.createServerSocket(puerto);
            SSLSocket clienteConectado = null;
            ObjectInputStream flujoEntrada = null;

            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            //SE CREA EL PAR DE CLAVES PRIVADA Y PÚBLICA
            KeyPair par = keyGen.generateKeyPair();
            PrivateKey clavepriv = par.getPrivate();
            PublicKey clavepub = par.getPublic();
            Cipher rsaCipher = Cipher.getInstance("RSA");



            //FLUJO de entrada de cliente
            ObjectOutputStream flujoSalida = null;//Flujo de salida al cliente
            for (int i = 1; i < 5; i++) {
                System.out.println("Esperando al cliente" + i);
                clienteConectado = (SSLSocket) servidorSSL.accept();

                ObjectOutputStream clavePublica= new ObjectOutputStream(clienteConectado.getOutputStream());
                clavePublica.writeObject(clavepub);

                ObjectInputStream objetoEntrada2=new ObjectInputStream(clienteConectado.getInputStream());
                PublicKey clavepubCliente= (PublicKey) objetoEntrada2.readObject();

                System.out.println(clavepubCliente);

                flujoEntrada = new ObjectInputStream(clienteConectado.getInputStream());
                byte[]  mensajeCliente = (byte[] ) flujoEntrada.readObject();
                rsaCipher.init(Cipher.DECRYPT_MODE, clavepriv);
                String mensajeDescifrado = new String(rsaCipher.doFinal(mensajeCliente));

                //EL cliente envia un mensaje
                System.out.println("Recibien del cliente" + i + "\n\t" + mensajeDescifrado);
                flujoSalida = new ObjectOutputStream(clienteConectado.getOutputStream());

                //envio un saludo al cliente
                rsaCipher.init(Cipher.ENCRYPT_MODE, clavepubCliente);
                String mensaje = "HOla cliente desde el servidor";
                byte[] cifrado = rsaCipher.doFinal(mensaje.getBytes());
                flujoSalida.writeObject(cifrado);
            }

            //cerrar streams y sockets
            flujoEntrada.close();
            flujoSalida.close();
            clienteConectado.close();
            servidorSSL.close();
        }catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
