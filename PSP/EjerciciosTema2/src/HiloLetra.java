public class HiloLetra extends Thread{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        //while(true){
            for(char i='a'; i<'z';i++){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("La letra es "+i);
            }
        //}
    }
}
