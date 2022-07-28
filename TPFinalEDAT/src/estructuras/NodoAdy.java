package estructuras;

public class NodoAdy {
    private NodoVert vertice;
    private NodoAdy sigAdyacente;
    private int etiqueta;
    
    public NodoAdy(NodoVert vertice, NodoAdy sigAdyacente, int etiqueta){
        this.vertice = vertice;
        this.sigAdyacente = sigAdyacente;
        this.etiqueta = etiqueta;
    }
    
    public NodoVert getVertice(){
        return this.vertice;
    }
    
    public void setVertice(NodoVert vertice){
        this.vertice = vertice;
    }
    
    public NodoAdy getSigAdyacente(){
        return this.sigAdyacente;
    }
    
    public void setSigAdyacente(NodoAdy sigAdyacente){
        this.sigAdyacente = sigAdyacente;
    }
    
    public int getEtiqueta(){
        return this.etiqueta;
    }
    
    public void setEtiqueta(int etiqueta){
        this.etiqueta = etiqueta;
    }
}
