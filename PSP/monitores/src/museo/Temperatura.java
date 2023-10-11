package museo;

public class Temperatura extends Thread{
    Museo m;

    public Temperatura(Museo m) {
        this.m = m;
    }

    @Override
    public void run() {
        try {
            m.temperatura();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
