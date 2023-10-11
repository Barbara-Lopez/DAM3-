package Coches;

public class Bajar extends Thread{
    Puente p;

    public Bajar(Puente p) {
        this.p = p;
    }

    @Override
    public void run() {
        while(true) {
            try {

                p.bajarCoche();


            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
