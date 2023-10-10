package productor_consumidor;

import java.util.Objects;
import java.util.Random;

public class buffer {
    Integer cant;

    public buffer(Integer cant) {
        this.cant = cant;
    }
    public synchronized void productor() throws InterruptedException {
        while(cant!=0){
            System.out.println("El productor está esperando a que este vacio");
            wait();
        }

        cant= (Integer)(Math.random() * (100- 1 + 1) + 1);
        notifyAll();
    }
    public synchronized void consumidor() throws InterruptedException {
        while(cant==0){
            System.out.println("El consumidor está esperando a que este lleno");
            wait();
        }
        System.out.println("El consumidor esta consumiendo el valor "+ cant);
        cant = 0;
        notifyAll();
    }
    productor
}
