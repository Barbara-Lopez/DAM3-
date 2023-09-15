import java.io.*;
import java.util.Arrays;

public class VerInf {
    public static void main(String[] args) throws IOException, EOFException{

       /* System.out.println("Información de este fichero:");
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
        File fichero = new File("C:\\Users\\9fdam03\\Desktop\\Clase\\DAM3-\\Acceso a datos\\Actividad1\\src\\fichero");//declarar fichero
        FileReader fic = new FileReader(fichero); //crear el flujo de entrada
        int i;
        while ((i = fic.read()) != -1) //se va leyendo un carácter
            System.out.println((char) i);
        fic.close(); //cerrar flujo de entrada


        File fis = new File("C:\\Users\\9fdam03\\Desktop\\Clase\\DAM3-\\Acceso a datos\\Actividad1\\src\\fichero");//declarar fichero
        FileReader isr = new FileReader(fis);
        char[] cbuf = new char[10];
        // lee los datos en el buffer
        i = isr.read(cbuf, 2, 5);
        for(char c:cbuf)
        {
        // caracteres vacios
            if(((int)c)==0)
                c='-';
            System.out.println(c);
        }

        try {
            FileReader fr = new FileReader("C:\\Users\\9fdam03\\Desktop\\Clase\\DAM3-\\Acceso a datos\\Actividad1\\src\\fichero");
            BufferedReader br = new BufferedReader(fr);

            String linea;

            while((linea = br.readLine())!= null)
                System.out.println(linea);

            br.close();
        }
        catch (FileNotFoundException fn) {
            System.out.println("Error de lectura");
        }

        catch (IOException io){
            System.out.println("Error de E/S");
        }
        String texto = "hola mundo!";
        FileWriter f = new FileWriter ("C:\\Users\\9fdam03\\Desktop\\Clase\\DAM3-\\Acceso a datos\\Actividad1\\src\\fichero2.txt", true);
        char[] array= texto.toCharArray();
        int i=0;
        while(i != array.length ) {
            f.append(array[i]);
            i++;
        }
        f.close();

        String prov[]={"gipuzkoa","bizkaia","araba"};
        FileWriter f = new FileWriter ("C:\\Users\\9fdam03\\Desktop\\Clase\\DAM3-\\Acceso a datos\\Actividad1\\src\\fichero1");
        int i=0;
        while(i != prov.length ) {
            f.write(prov[i]);
            i++;
        }
        f.close();

        File fichero = new File("C:\\Users\\9fdam03\\Desktop\\Clase\\DAM3-\\Acceso a datos\\Actividad1\\src\\FichData.dat");
        FileOutputStream fileout = new FileOutputStream(fichero);
        DataOutputStream dataOS = new DataOutputStream(fileout);
        String nombres[] = {"Ana","Luis, Miguel","Alicia","Pedro","Manuel","Andrés",
                "Julio","Antonio","María Jesús"};
        int edades[] = {14,15,13,15,16,12,16,14,13};
        for (int i=0;i<edades.length; i++){
            dataOS.writeUTF(nombres[i]); //inserta nombre
            dataOS.writeInt(edades[i]); //inserta edad
        }
        dataOS.close();
        */
        File fichero = new File("C:\\Users\\9fdam03\\Desktop\\Clase\\DAM3-\\Acceso a datos\\Actividad1\\src\\FichData.dat");
        FileInputStream fileout = new FileInputStream(fichero);
        DataInputStream dataOS = new DataInputStream(fileout);
        try {
            while (dataOS.available() != -1) { //lee datos del flujo de entrada
                System.out.println(dataOS.readUTF() + ", " + dataOS.readInt());

            }
        }catch (EOFException eo){}

        dataOS.close(); //cerrar stream de entrada

    }

}
