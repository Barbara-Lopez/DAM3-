import java.util.concurrent.Semaphore;

public class contador {
    private int cont;
    Semaphore semaforo;
    public int getCont() {
        return cont;
    }

    public void setCont(int cont) {
        this.cont = cont;

    }

    public contador(int cont,Semaphore s) {
        this.cont = cont;
        semaforo = s;
    }

    public void suma() throws InterruptedException {
        semaforo.acquire();
        cont = cont+1;
        semaforo.release();
    }
}
