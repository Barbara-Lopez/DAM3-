package Coches;

import java.util.ArrayList;
import java.util.List;

public class Puente {
    Integer maxCoches=5;
    Integer maxPeso=15000;
    Integer peso;
    Integer coches;
    List<Integer> pesos=new ArrayList<Integer>(5);

    public Puente(Integer peso, Integer coches) {
        this.peso = peso;
        this.coches = coches;
    }
    public synchronized void subirCoche(Integer pesoCoche, String name) throws InterruptedException {

        while ((coches+1)>=maxCoches || (peso + pesoCoche)>maxPeso){
            System.out.println("El "+name+" que intenta subir no puede, hay "+coches+
                    " coches con un peso de "+peso+" y el coche pesa "+pesoCoche);
            wait();
        }

        coches+=1;
        peso+=pesoCoche;
        pesos.add(pesoCoche);
        System.out.println(name +" ha subido, hay un total de "+coches+" con un peso de "+peso);
        notifyAll();
    }
    public synchronized void bajarCoche() throws InterruptedException {
        while (coches==0 & peso==0){

            wait();
        }
        coches-=1;
        peso-=pesos.get(0);
        pesos.remove(0);
        System.out.println("Un coche ha bajado, hay "+coches+" coches con un peso de "+peso);
        notifyAll();
    }
}
