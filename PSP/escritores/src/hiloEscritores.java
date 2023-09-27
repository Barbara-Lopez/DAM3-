import java.util.concurrent.Semaphore;

public class hiloEscritores extends Thread {
    private Semaphore escritor;

    public hiloEscritores(Semaphore escritor) {
        this.escritor = escritor;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName()+": está intentando escribir");
            escritor.acquire(10);
            System.out.println(Thread.currentThread().getName()+": ya ha escrito");
            escritor.release(10);


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
