// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Press Alt+Intro with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        int n=0;
        compartir c=new compartir(0);
        hilo hilo1= new hilo(c,"suma");
        hilo hilo2= new hilo(c,"suma");
        hilo hilo3= new hilo(c,"resta");

        hilo1.start();
        hilo1.setName("hilo1");

        hilo2.start();
        hilo2.setName("hilo2");

        hilo3.start();
        hilo3.setName("hilo3");

        hilo1.join();
        hilo2.join();
        hilo3.join();

        hilo1.resultadoContador();
    }
}
