package cuenta;

public class Ahorrar extends Thread{
    cuenta c;

    public Ahorrar(cuenta c) {
        this.c = c;
    }

    @Override
    public void run() {
        System.out.println("Vamos a ahorrar");
        try {
            c.ahorrar();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
