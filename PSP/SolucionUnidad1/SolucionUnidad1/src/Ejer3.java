import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Ejer3 {
    public static void main(String[] args) throws IOException {


        Process p = Runtime.getRuntime().exec("CMD /C DIR");
        byte[] buffer = p.getInputStream().readAllBytes();
        File fichero = new File("salidaruntime.txt");
        OutputStream salida = new FileOutputStream(fichero);
        salida.write(buffer);

//Con ProcessBuilder


         try {
             ProcessBuilder pb = new ProcessBuilder
                     ("CMD", "/C", "DIRo");

             System.out.println(pb.command());

             pb.redirectOutput(new File("output.txt"));
                pb.redirectError(new File("mal.txt"));
             pb.start();
         } catch (IOException e) {
             e.printStackTrace();
         }


        Process aa=new ProcessBuilder("CMD", "/c", "dirw")
                .redirectError(new File("Sin error")).start();
    }

}