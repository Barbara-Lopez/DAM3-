import java.lang.reflect.Array;

public class hiloTotalMedia extends Thread{
    private Integer inicioArray;
    private Integer finalArray;
    private int[] array;
    public hiloTotalMedia(Integer inicioArray, Integer finalArray,int[] array){
        this.array= array;
        this.finalArray= finalArray;
        this.inicioArray=inicioArray;
    }
    @Override
    public void run() {
        Integer suma= 0;
        for (int i = inicioArray; i < finalArray; i++) {
            suma=+array[i];
        }
        System.out.println("Media: " + suma/500);
    }
}
