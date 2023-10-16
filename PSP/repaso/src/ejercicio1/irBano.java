package ejercicio1;

public class irBano extends Thread{
    Banos b;
    Persona p;

    public irBano(Banos b) {
        this.b = b;
    }

    @Override
    public void run() {
        while(true){
            try {
                int n = (int)Math.floor(Math.random() * (1 - 0 + 1) + 0);
                if(n==0){
                    p=new Persona("Mujer");
                }else{
                    p=new Persona("Hombre");
                }
                b.entrar(p.getSexo(),Thread.currentThread().getName());
                sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
