import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;

public class Ejer1 {
    public static void main(String[] args) throws IOException, InterruptedException {
       // Process a=Runtime.getRuntime().exec("powershell");
  // Process a=Runtime.getRuntime().exec("Notepad.exe notas2.txt");
        Process a=Runtime.getRuntime().exec(
                "C:\\Program Files\\Adobe\\Acrobat DC\\Acrobat\\Acrobat.exe");
        System.out.println(a.isAlive());
        System.out.println(a.info().startInstant());
        System.out.println(a.info().user());
        System.out.println(a.info().totalCpuDuration().get().toNanos());
        System.out.println(a.pid());
        int n= (int) a.pid();
        Thread.sleep(3000);



        a.destroy();
        System.out.println(a.isAlive());




    }
}
