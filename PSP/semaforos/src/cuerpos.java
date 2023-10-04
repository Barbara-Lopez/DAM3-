import java.util.concurrent.Semaphore;

public class cuerpos extends Thread{


    Semaphore cuerpo;
    cestos c;
    public cuerpos(Semaphore cuerpo,cestos c) {
        this.cuerpo = cuerpo;
        this.c=c;
    }

    @Override
    public void run() {
        try {
            cuerpo.acquire();
            if(c.getCuerpos() <10){
                c.setCuerpos(c.getCuerpos()+1);
                System.out.println("El cesto de los cuerpos está con "+ c.getCuerpos()+" cuerpos");
            } else {
                System.out.println("El cesto de los cuerpos está lleno con "+ c.getCuerpos()+" cuerpos");
            }
            cuerpo.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
