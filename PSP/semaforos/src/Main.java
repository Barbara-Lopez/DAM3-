import java.util.concurrent.Semaphore;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws InterruptedException {
       /* Semaphore semaforo = new Semaphore(1);

        contador c=new contador(0,semaforo);
        hilo h1=new hilo(c);
        hilo h2=new hilo(c);
        hilo h3=new hilo(c);
        hilo h4=new hilo(c);

        h1.start();// cuando pones run() es secuencial
        h2.start();
        h3.start();
        h4.start();*/
        Semaphore mangas = new Semaphore(2);
        Semaphore cuerpos = new Semaphore(1);
        cestos c=new cestos(0,0);
        mangas m1=new mangas(mangas,c);
        mangas m2=new mangas(mangas,c);
        cuerpos c1=new cuerpos(cuerpos,c);
        cuerpos c2=new cuerpos(cuerpos,c);
        Jerseis j=new Jerseis(c,mangas,cuerpos);
        Jerseis j1=new Jerseis(c,mangas,cuerpos);
        m1.start();
        m2.start();
        c1.start();
        c2.start();
        j.start();
        j1.start();

    }
}