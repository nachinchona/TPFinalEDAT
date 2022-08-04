package tpfinal;

public class Modificacion {
    private Object objAModificar;
    private int campoAModificar;
    
    public Modificacion(Object objAModificar, int campoAModificar){
        this.objAModificar = objAModificar;
        this.campoAModificar = campoAModificar;
    }
    
    public Object getObjAModificar(){
        return this.objAModificar;
    }
    
    public int getCampoAModificar(){
        return this.campoAModificar;
    }
}
