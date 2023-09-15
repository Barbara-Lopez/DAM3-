import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Ejercicio 1
        /*
        Process p = Runtime.getRuntime().exec("notepad.exe");
        System.out.println("Está en ejecucion : " + p.isAlive());
        System.out.println(p.info().user());
        p.destroy();
        System.out.println("Está en ejecucion : " + p.isAlive());
        */
        // Ejercicio 2
        /*
        Process proc= Runtime.getRuntime().exec("cmd /C start /wait dir");
        ProcessBuilder pcom= new ProcessBuilder("cmd", "/C","dir");
        pcom.inheritIO();
        pcom.start();
        //System.out.println("pcom está en ejecucion : " + pcom.isAlive());
        */
        // Ejercicio 3

        Process proc= Runtime.getRuntime().exec("cmd /C dir");
        
        ProcessBuilder pcom= new ProcessBuilder("cmd", "/C","dir");
        pcom.inheritIO();
        pcom.start();


        // Ejercicio 4

        // Ejercicio 5
    }
}