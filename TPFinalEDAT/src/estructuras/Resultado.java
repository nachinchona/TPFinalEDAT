package estructuras;

public class Resultado {
    private int tope;
    private int km;
    
    public Resultado(int km, int tope){
        this.tope = tope;
        this.km = km;
    }
    
    public int getTope(){
        return this.tope;
    }
    
    public int getKm(){
        return this.km;
    }
    
    public void setKm(int km){
        this.km = km;
    }
    
    public void setTope(int tope){
        this.tope = tope;
    }
}   
