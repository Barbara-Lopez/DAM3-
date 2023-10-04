import java.lang.reflect.Array;
import java.util.ArrayList;

public class hilo1 extends Thread{

    private Integer lista[];
    private compartir2 c;

    public hilo1(Integer lista[]) {
        this.lista = lista;
    }

    @Override
    public void run() {
        for (int i = 0; i < lista.length; i++) {
            c.suma();
        }
    }
}
