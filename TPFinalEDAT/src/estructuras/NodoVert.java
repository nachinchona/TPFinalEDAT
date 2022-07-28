package estructuras;

public class NodoVert {
    private Object elem;
    private NodoVert sigVertice;
    private NodoAdy primerAdy;
    
    public NodoVert(Object elem, NodoVert sigVertice){
        this.elem = elem;
        this.sigVertice = sigVertice;
    }
    
    public Object getElem(){
        return this.elem;
    }
    
    public void setElem(Object elem){
        this.elem = elem;
    }
    
    public NodoVert getSigVertice(){
        return this.sigVertice;
    }
    
    public void setSigVert(NodoVert sigVertice){
        this.sigVertice = sigVertice;
    }
    
    public NodoAdy getPrimerAdy(){
        return this.primerAdy;
    }
    
    public void setPrimerAdy(NodoAdy nodoAdy){
        this.primerAdy = nodoAdy;
    }
}
