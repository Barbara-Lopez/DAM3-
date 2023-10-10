package jerseis;

public class jerseis extends Thread{
    cestos c;

    public jerseis(cestos c) {
        this.c = c;
    }

    @Override
    public void run() {
        if(c.getMangas()>1 & c.getCuerpos()>0){
            try {
                c.restarCuerpos();
                c.restarMangas();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName()+" echo");
        } else if (c.getMangas()<2 & c.getCuerpos()==0) {
            System.out.println(Thread.currentThread().getName()+" no hay suficientes jerseis.mangas o jerseis.cuerpos cuficientes");
        }
    }
}
