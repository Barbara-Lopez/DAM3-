import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;


public class Has {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, ClassNotFoundException {
        MessageDigest md = MessageDigest.getInstance("SHA");
        String contrasenaHas = "12345Abcde";
        md.update(contrasenaHas.getBytes());

        /*FileWriter f = new FileWriter ("src/log.txt");
        f.write("Barbara");
        f.close();*/
        FileOutputStream fileout= new FileOutputStream("datos.dat");
        ObjectOutputStream dataOS= new ObjectOutputStream(fileout);

        byte resumen[]=md.digest();//SE CALCULA EL RESUMEN
        dataOS.writeObject(contrasenaHas);//se escriben los datos
        dataOS.writeObject(resumen);//se escribe el resumen
        dataOS.close();
        fileout.close();

        Scanner sc = new Scanner(System.in);
        System.out.print("Escriba la contraseña: ");
        String contrasena = sc.nextLine();

        FileInputStream fileout1= new FileInputStream("datos.dat");
        ObjectInputStream dataOS1= new ObjectInputStream(fileout1);
        Object o = dataOS1.readObject();
        String datos=contrasena;


        //segunda lectura, se obtiene el resumen
        o=dataOS1.readObject();
        byte resumenOriginal[]=(byte[])o;
        MessageDigest md2=MessageDigest.getInstance("SHA");
        //se calcula el resumen del String leido del fichero
        md2.update(datos.getBytes());//TEXTO A RESUMIR
        byte resumenActual[]=md2.digest();//SE CALCULA EL RESUMEN
        //se comprueban los dos resumenes
        if(MessageDigest.isEqual(resumenActual, resumenOriginal))
            System.out.println("Contraseña valida");
        else
            System.out.println("Contraseña NO valida");
        dataOS1.close();
        fileout1.close();

    }
}
