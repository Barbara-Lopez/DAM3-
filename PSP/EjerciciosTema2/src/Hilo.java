public class Hilo extends Thread{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        //while(true){
            for(int i=0;i<100;i++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("El valor es "+i);
            }
        //}
    }
}
