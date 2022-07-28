package estructuras;

public class NodoDiccAVL {
    private Comparable clave;
    private Object dato;
    private int altura = 0;
    private NodoDiccAVL izquierdo;
    private NodoDiccAVL derecho;
    
    public NodoDiccAVL(Comparable elem, Object dato, NodoDiccAVL izquierdo, NodoDiccAVL derecho){
        this.clave = elem;
        this.dato = dato;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
        recalcularAltura();
    }
    
    public Object getDato(){
        return this.dato;
    }
    
    public void setDato(Object dato){
        this.dato = dato;
    }
    
    public Comparable getClave(){
        return this.clave;
    }
    
    public void setClave(Comparable elem){
        this.clave = elem;
    }
    
    public int getAltura(){
        return this.altura;
    }
    
    public void recalcularAltura(){
        int altDerecho, altIzquierdo;
        if (this.derecho == null) {
            altDerecho = -1;
        }else{
            altDerecho = this.derecho.getAltura();
        }
        if (this.izquierdo == null) {
            altIzquierdo = -1;
        }else{
            altIzquierdo = this.izquierdo.getAltura();
        }
        this.altura = max(altDerecho, altIzquierdo) + 1;
    }
    
    public void setDerecho(NodoDiccAVL nodo){
        this.derecho = nodo;
        recalcularAltura();
    }
    
    public void setIzquierdo(NodoDiccAVL nodo){
        this.izquierdo = nodo;
        recalcularAltura();
    }
    
    public NodoDiccAVL getDerecho(){
        return this.derecho;
    }
    
    public NodoDiccAVL getIzquierdo(){
        return this.izquierdo;
    }
    
    private int max(int a, int b){
        int retorno;
        if (a > b) {
            retorno = a;
        }else{
            retorno = b;
        }
        return retorno;
    }
}
