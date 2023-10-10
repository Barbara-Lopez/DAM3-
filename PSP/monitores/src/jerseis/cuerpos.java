package jerseis;

public class cuerpos extends Thread{
    cestos c;

    public cuerpos(cestos c) {
        this.c = c;
    }

    @Override
    public void run() {
        try {
            c.sumarCuerpos();
            System.out.println("La cantidad de jerseis.cuerpos es: "+c.getCuerpos());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
