public class HiloLetra extends Thread{
    @Override
    public void run() {
        //while(true){
            for(char i='a'; i<'z';i++){
                System.out.println("La letra es "+i);
            }
        //}
    }
}
