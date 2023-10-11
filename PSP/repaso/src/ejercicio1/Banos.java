package ejercicio1;

public class Banos {
    Integer cantPersonas;
    String sexo;

    public Banos(Integer cantPersonas, String sexo) {
        this.cantPersonas = cantPersonas;
    }

    public synchronized void entrar(String s, String name) throws InterruptedException {
        if (cantPersonas==0) {
            sexo = s;
        }
        while(s != sexo & cantPersonas>3){
            System.out.println("La "+name+" ha intentado entrar pero no puede, la cantidad de personas es "+cantPersonas+
                    " y el sexo de las personas es "+sexo);
            wait();
        }
        cantPersonas+=1;
        System.out.println("La "+name+" ha entrado, la cantidad de personas es "+cantPersonas+" y el sexo es "+sexo);
        notifyAll();
    }
    public synchronized void salir(String s) throws InterruptedException {

        while(cantPersonas==0){
            wait();
        }
        cantPersonas-=1;
        System.out.println("Una persona ha salido");
        notifyAll();
    }
}
