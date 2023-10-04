import java.util.concurrent.Semaphore;

public class mangas extends Thread{




    Semaphore mangas;
    cestos c;

    public mangas(Semaphore mangas, cestos c) {
        this.c=c;
        this.mangas = mangas;
    }

    @Override
    public void run() {

        try {
            mangas.acquire();
            if( c.getMangas() <10){
                c.setMangas(c.getMangas()+1);
                System.out.println("El cesto de las mangas está con "+ c.getMangas()+" mangas");
            } else {
                System.out.println("El cesto de las mangas está lleno con "+ c.getMangas()+" mangas");
            }
            mangas.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
