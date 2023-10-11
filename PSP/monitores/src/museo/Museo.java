package museo;

public class Museo {
    Integer temperatura;
    Integer personas;
    Integer maxPersona= 50;

    public Museo(Integer temperatura, Integer personas) {
        this.temperatura = temperatura;
        this.personas = personas;
    }

    public synchronized void entar() throws InterruptedException {
        while (personas>=maxPersona){
            System.out.println("una persona ha intentado entrar hay "+personas+" personas con una temperatura de "
                    +temperatura+"º");
            wait();
        }
        personas = personas + 1;
        System.out.println("Una persona nueva ha entrado, ahora hay "+personas+" personas");
        notifyAll();
    }
    public synchronized void salir() throws InterruptedException {
        while (personas==0){
            System.out.println("Han salido todas las personas");
            wait();
        }

        personas = personas - 1;
        System.out.println("Una persona ha salido, ahora hay "+personas+" personas");

        notifyAll();
    }
    public synchronized void temperatura() throws InterruptedException {

        temperatura = (int)Math.floor(Math.random() * (40 - 20 + 1) + 20);
        if(temperatura>30){
            maxPersona= 35;
        }else{
            maxPersona=50;
        }
        System.out.println("La temperatura es: "+temperatura);
        notifyAll();
    }
}
