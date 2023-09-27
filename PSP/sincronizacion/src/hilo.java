import java.util.Objects;

public class hilo extends Thread{
    private compartir c;
    private String accion;

    public hilo(compartir c, String accion) {
        this.c = c;
        this.accion = accion;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            if (Objects.equals(accion, "suma")){
                c.suma();
                System.out.println("El contadordel hilo "+Thread.currentThread().getName()+" es: "+ c.getContador());

            }else{
                c.resta();
                System.out.println("El contador hilo "+Thread.currentThread().getName()+ " es: "+ c.getContador());

            }
        }
    }

    public void resultadoContador() {
        System.out.println("El resultado del contador es: "+ c.getContador());

    }
}
