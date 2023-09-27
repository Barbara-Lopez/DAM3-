public class hiloCajeras extends Thread{
    private Integer cantProd;
    private Integer cantPrecio;
    private double[] cajera;
    private int cliente;
    private int numCajera;

    public hiloCajeras(double[] cajera,int cliente,int numCajera){
        this.cajera=cajera;
        this.cliente = cliente;
        this.numCajera=numCajera;
    }
    @Override
    public void run() {
        double suma= 0;
        for (int i = 0; i < cajera.length; i++) {
            suma=+cajera[i];
            System.out.println("La cajera numero"+numCajera+" procesando el producto "+i+" del cliente numero"
                    +cliente+" y el precio es: " + cajera[i]);
        }
        System.out.println("La cajera numero"+numCajera+" finaliza el cobro del cliente numero"
                +cliente+" y el total de la compra es: " + suma);
    }
}
