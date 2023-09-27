import java.util.concurrent.Semaphore;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Press Alt+Intro with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.

        Semaphore lector=new Semaphore(10);

        hiloLectores l1 = new hiloLectores(lector);
        hiloLectores l2 = new hiloLectores(lector);
        hiloLectores l3 = new hiloLectores(lector);
        hiloLectores l4 = new hiloLectores(lector);
        hiloLectores l5 = new hiloLectores(lector);
        hiloLectores l6 = new hiloLectores(lector);
        hiloLectores l7 = new hiloLectores(lector);
        hiloLectores l8 = new hiloLectores(lector);
        hiloLectores l9 = new hiloLectores(lector);
        hiloLectores l10 = new hiloLectores(lector);
        hiloEscritores e1 = new hiloEscritores(lector);
        hiloEscritores e2 = new hiloEscritores(lector);
        hiloEscritores e3 = new hiloEscritores(lector);

        l1.start();
        l1.setName("lector1");
        l2.start();
        l2.setName("lector2");
        l3.start();
        l3.setName("lector3");
        l4.start();
        l4.setName("lector4");
        l5.start();
        l5.setName("lector5");
        l6.start();
        l6.setName("lector6");
        l7.start();
        l7.setName("lector7");
        l8 .start();
        l8.setName("lector8");
        l9.start();
        l10.start();
        e1.start();
        e2.start();
        e3.start();

    }
}