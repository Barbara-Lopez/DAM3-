public class Hilo extends Thread{

    @Override
    public void run() {
        //while(true){
            for(int i=0;i<100;i++){
                System.out.println("El valor es "+i);
            }
        //}
    }
}
