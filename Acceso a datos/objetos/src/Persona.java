public class Persona {

    private String nombre;
    private String ciudad;
    private Integer edad;
    private Double altura;

    public Persona(){
    }
    public Persona(String nombre,String ciudad,Integer edad,Double altura){
        this.nombre=nombre;
        this.ciudad= ciudad;
        this.edad= edad;
        this.altura=altura;
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
    public void setCiudad(String dir){
        ciudad=dir;
    }
    public Integer getEdad(){
        return edad;
    }
    public void setEdad(Integer dir){
        edad=dir;
    }
    public Double getAltura(){
        return altura;
    }
    public void setAltura(Double dir){
        altura=dir;
    }


}
