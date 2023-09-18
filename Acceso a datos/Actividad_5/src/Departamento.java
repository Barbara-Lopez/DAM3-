public class Departamento {
    private Integer id_depart;
    private String nombre;
    private String ciudad;

    public Departamento(){
    }
    public Departamento(Integer id_depart,String nombre,String ciudad){
        this.id_depart=id_depart;
        this.nombre=nombre;
        this.ciudad=ciudad;
    }
    public Integer getId_depart(){
        return id_depart;
    }
    public void setId_depart(Integer num){
        id_depart=num;
    }
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nom){
        nombre=nom;
    }
    public String getCiudad(){
        return ciudad;
    }
    public void setCiudad(String c){
        ciudad=c;
    }
}
