public class compartir {
    private int contador;

    public compartir(int contador) {
        this.contador = contador;
    }
    public void setContador(int contador) {
        this.contador = contador;
    }
    public int getContador() {
        return contador;
    }

    public synchronized void suma(){
        contador = contador+1;
    }
    public synchronized void resta(){
        contador = contador-1;
    }
}
