package museo;

public class Personas extends Thread{
    Museo m;

    public Personas(Museo m) {
        this.m = m;
    }

    @Override
    public void run() {
        try {
            m.entar();
            sleep(2000);
            m.salir();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
