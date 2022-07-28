package tpfinal;

public class Estacion {
    private String nombre;
    private Domicilio domicilio;
    private int cantVias;
    private int cantPlataformas;
    
    public Estacion(String nombre, Domicilio domicilio, int cantVias, int cantPlataformas){
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.cantVias = cantVias;
        this.cantPlataformas = cantPlataformas;
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    public Domicilio getDomicilio(){
        return this.domicilio;
    }
    
    public int getCantVias(){
        return this.cantVias;
    }
    
    public int getCantPlat(){
        return this.cantPlataformas;
    }
    
    public void setDomicilio(Domicilio domicilio){
        this.domicilio = domicilio;
    }
    
    public void setCantVias(int cantVias){
        this.cantVias = cantVias;
    }
    
    public void setCantPlat(int cantPlataformas){
        this.cantPlataformas = cantPlataformas;
    }
    
    public String toString(){
        return "E;"+nombre+";"+domicilio.toString()+";"+cantVias+";"+cantPlataformas;
    }
}
