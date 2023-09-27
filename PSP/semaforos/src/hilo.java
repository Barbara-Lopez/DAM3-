public class hilo extends Thread{
    contador c;

    public hilo(contador c) {
        this.c=c;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5000; i++) {
            try {
                c.suma();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("El valor del contador es: "+c.getCont());

    }
    public void resultado(){
        System.out.println("El resultado del contador es: "+c.getCont());
    }
}
