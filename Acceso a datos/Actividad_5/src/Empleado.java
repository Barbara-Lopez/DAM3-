public class Empleado {
    private Integer id_emp;
    private String nombre;
    private String puesto;
    private Integer depart;

    public Empleado(){
    }
    public Empleado(Integer id_emp,String nombre,String puesto,Integer depart){
        this.id_emp=id_emp;
        this.nombre= nombre;
        this.puesto=puesto;
        this.depart=depart;
    }
    public Integer getId_emp(){
        return id_emp;
    }
    public void setId_emp(Integer num){
        id_emp=num;
    }
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nom){
        nombre=nom;
    }
    public String getPuesto(){
        return puesto;
    }
    public void setPuesto(String nom){
        puesto=nom;
    }
    public Integer getDepart(){
        return depart;
    }
    public void setDepart(Integer dir){
        depart=dir;
    }


}
