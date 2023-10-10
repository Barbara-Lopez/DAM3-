package cuenta;

public class Gastar extends Thread{
    cuenta c;

    public Gastar(cuenta c) {
        this.c = c;
    }

    @Override
    public void run() {
        System.out.println("Vamos a gastar");
        try {
            c.gastar();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
