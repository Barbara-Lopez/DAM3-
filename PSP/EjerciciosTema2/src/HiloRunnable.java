public class HiloRunnable implements Runnable{

    @Override
    public void run() {
        for (char i='A';i<'Z';i++){
            System.out.println("La letra es "+i);
        }
    }
}
