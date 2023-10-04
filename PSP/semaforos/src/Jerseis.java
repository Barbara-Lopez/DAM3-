import java.util.concurrent.Semaphore;

public class Jerseis extends Thread{
    cestos c;
    Semaphore m;
    Semaphore cu;
    public Jerseis(cestos c, Semaphore mangas,Semaphore cuerpo) {
        this.cu=cuerpo;
        this.m=mangas;
        this.c=c;

    }

    @Override
    public void run() {
        try {
            cu.acquire();
            m.acquire();
            if(c.getMangas()>1 & c.getCuerpos()>0){
                c.setMangas(c.getMangas()-2);
                c.setCuerpos(c.getCuerpos()-1);
                System.out.println("Jersei hecho");
            } else if (c.getMangas()<2) {
                System.out.println("no hay suficientes mangas");
            } else if (c.getCuerpos()==0) {
                System.out.println("no hay cuerpos");
            }
            cu.release();
            m.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
