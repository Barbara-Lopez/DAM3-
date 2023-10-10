package cuenta;

public class cuenta {
    Integer cant;

    public cuenta() {
    }

    public cuenta(Integer cant) {
        this.cant = cant;
    }

    public Integer getCant() {
        return cant;
    }

    public void setCant(Integer cant) {
        this.cant = cant;
    }
    public synchronized void gastar() throws InterruptedException {
        while(cant<0){
            wait();
        }
        cant = cant-10;
        System.out.println("Hemos gastado :"+cant+"€");
        notifyAll();
    }
    public synchronized void ahorrar() throws InterruptedException {
        while(cant>=250){
            wait();
        }
        cant = cant+10;
        System.out.println("Hemos ahorrado: "+cant+"€");
        notifyAll();
    }
    public void saldoFinal(){
        System.out.println("El saldo final es de "+cant+"€");
    }
}
