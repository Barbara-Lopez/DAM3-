public class cestos {
    private int mangas;
    private int cuerpos;
    private int maxMangas=2;
    private int maxCuerpos=2;
    public cestos(int mangas, int cuerpos) {
        this.mangas = mangas;
        this.cuerpos = cuerpos;
    }

    public int getMangas() {
        return mangas;
    }

    public void setMangas(int mangas) {
        this.mangas = mangas;
    }

    public void setCuerpos(int cuerpos) {
        this.cuerpos = cuerpos;
    }

    public int getCuerpos() {
        return cuerpos;
    }
    public synchronized void sumarMangas() throws InterruptedException {
        while(mangas==maxMangas){
            wait();
        }

        mangas++;
        System.out.println("Mangas metidas en la cesta");
    }
    public synchronized void sumarCuerpos() throws InterruptedException {
        while(cuerpos==maxCuerpos){
            wait();
        }

        cuerpos++;
        System.out.println("Cuerpos metidos en la cesta");
    }
    public synchronized void restarCuerpos() throws InterruptedException {
        
        cuerpos--;
        notifyAll();
    }
    public synchronized void restarMangas() throws InterruptedException {
        mangas=mangas-2;
        notifyAll();
    }

}
