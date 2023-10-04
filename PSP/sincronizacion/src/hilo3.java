import java.util.Objects;

public class hilo3 extends Thread{
    private compartir c;
    private String accion;

    public hilo3(compartir c, String accion) {
        this.c = c;
        this.accion = accion;
    }

    @Override
    public void run() {

            if (Objects.equals(accion, "entrar")){
                c.suma();
                System.out.println("La cantidad de visitantes en "+Thread.currentThread().getName()+" es: "+ c.getContador());

            }else{
                c.resta();
                System.out.println("La cantidad de visitantes en "+Thread.currentThread().getName()+ " es: "+ c.getContador());

            }

    }

    public void resultadoContador() {
        System.out.println("La cantidad de visitantes al final del dia es: "+ c.getContador());

    }
}
