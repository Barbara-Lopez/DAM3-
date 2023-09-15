import java.io.*;

public class VerInf {
    public static void main(String[] args) {

        System.out.println("Información de este fichero:");
        File f = new File("src/VerInf.java");
        if (f.exists()){
            System.out.println("El nombre: " + f.getName());
            System.out.println("La ruta: " + f.getPath());
            System.out.println("La ruta absoluta: " + f.getAbsolutePath());
            System.out.println("El tamaño: " + f.length());
            if(f.canRead()){
                System.out.println("Se puede leer");
            }else{
                System.out.println("NO se puede leer");
            }
            if(f.canWrite()){
                System.out.println("Se puede escribir");
            }else{
                System.out.println("NO se puede escribir");
            }
            if(f.isDirectory()){
                System.out.println("Es un directorio");
            }else if(f.isFile()){
                System.out.println("Es un fichero");
            }

        }
    }

}
