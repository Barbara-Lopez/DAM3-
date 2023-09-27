import java.util.concurrent.Semaphore;

public class hiloLectores extends Thread{
    private Semaphore lector;

    public hiloLectores(Semaphore lector) {
        this.lector = lector;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName()+": está intentando leer");
            lector.acquire(1);
                System.out.println(Thread.currentThread().getName()+": ya ha leido");
            lector.release();


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
