package Coches;

public class Subir extends Thread{
    Puente p;
    Coche c;

    public Subir(Puente p) {
        this.p = p;
    }

    @Override
    public void run() {
        while(true) {
            try {
                    c = new Coche((int)Math.floor(Math.random() * (15000 - 200 + 1) + 200));
                    p.subirCoche(c.getPeso(), Thread.currentThread().getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
