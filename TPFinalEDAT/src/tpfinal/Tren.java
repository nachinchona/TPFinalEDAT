package tpfinal;

public class Tren {
    private int id;
    private String tipoPropulsion;
    private int cantVagonesPas;
    private int cantVagonesCarga;
    private String linea;
    
    public Tren(int id, String tipoPropulsion, int cantVagPas, int cantVagCarga, String linea){
        this.id = id;
        this.tipoPropulsion = tipoPropulsion;
        this.cantVagonesCarga = cantVagCarga;
        this.cantVagonesPas = cantVagPas;
        this.linea = linea;
    }
    
    public int getId(){
        return this.id;
    }
    
    public String getTipoPropulsion(){
        return this.tipoPropulsion;
    }
    
    public int getCantVagonesPasajeros(){
        return this.cantVagonesPas;
    }
    
    public int getCantVagonesCarga(){
        return this.cantVagonesCarga;
    }
    
    public String getLinea(){
        return this.linea;
    }
    
    public void setTipoPropulsion(String tipoPropulsion){
        this.tipoPropulsion = tipoPropulsion;
    }
    
    public void setCantVagonesPasajeros(int cantVagPas){
        this.cantVagonesPas = cantVagPas;
    }
    
    public void setCantVagonesCarga(int cantVagCarga){
        this.cantVagonesCarga = cantVagCarga;
    }
    
    public void setLinea(String linea){
        this.linea = linea;
    }
}
