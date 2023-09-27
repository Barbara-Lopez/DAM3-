import java.util.concurrent.Semaphore;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Semaphore semaforo = new Semaphore(1);

        contador c=new contador(0,semaforo);
        hilo h1=new hilo(c);
        hilo h2=new hilo(c);
        hilo h3=new hilo(c);
        hilo h4=new hilo(c);

        h1.start();// cuando pones run() es secuencial
        h2.start();
        h3.start();
        h4.start();

    }
}