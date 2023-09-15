import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        //Process proc= Runtime.getRuntime().exec("Notepad.exe");
        //System.out.println(proc.pid()); // pid del proceso
        //System.out.println(proc.waitFor()); // bloquear el proceso hasta lo que queramos que se haga
        //proc.destroy(); // cerrar la app o proceso

       /* Runtime r=Runtime.getRuntime();
        Process proccom= r.exec("cmd /C start /wait dir"); // abrir el cmd

        Process p= new ProcessBuilder("Notepad.exe").start();
        p.isAlive();*/
        Process pcom= new ProcessBuilder("cmd", "/C","dir").start();



    }
}