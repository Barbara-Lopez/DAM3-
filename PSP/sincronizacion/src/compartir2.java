import java.util.ArrayList;

public class compartir2 {
    private int contador;

    public compartir2(int contador) {
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
}
