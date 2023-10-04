public class mangas extends Thread{
    cestos c;

    public mangas(cestos c) {
        this.c = c;
    }

    @Override
    public void run() {
        try {
            c.sumarMangas();
            System.out.println("La cantidad de mangas es: "+c.getMangas());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
