package estructuras;
public class Grafo {

    private NodoVert inicio;

    public Grafo() {
        this.inicio = null;
    }
    
    //falta caminoMasCorto, caminoMasLargo, listarEnAnchura, clone
    
    public Lista caminoMasCorto(Object origen, Object destino){
        Lista ls = new Lista();
        ls.agregarElem(origen, ls.longitud()+1);
        
        return ls;
    }
    
    private void caminoMasCortoPR(NodoVert nodo, Lista ls){
        if (nodo != null) {
            
        }
    }
    
    public boolean existeCamino(Object origen, Object destino){
        boolean exito = false;
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;
        while((auxO == null || auxD == null) && aux != null){
            if (aux.getElem().equals(origen)) {
               auxO = aux; 
            }
            if (aux.getElem().equals(destino)) {
                auxD = aux;
            }
            aux = aux.getSigVertice();
        } 
        
        if (auxO != null && auxD != null) {
            Lista visitados = new Lista();
            exito = existeCaminoPR(auxO, destino, visitados);
        }
        return exito;
    }
    
    private boolean existeCaminoPR(NodoVert nodo, Object dest, Lista vis){
        boolean exito = false;
        if (nodo != null) {
            if (nodo.getElem().equals(dest)) {
                exito = true;
            }else{
                vis.insertar(nodo.getElem(), vis.longitud()+1);
                NodoAdy aux = nodo.getPrimerAdy();
                while(!exito && aux != null){
                    if (vis.localizar(aux.getVertice().getElem()) < 0) {
                        exito = existeCaminoPR(aux.getVertice(), dest, vis);
                    }
                    aux = aux.getSigAdyacente();
                }
            }
        }
        return exito;
    }
    
    public Lista listarEnProfundidad(){
        Lista visitados = new Lista();
        NodoVert aux = this.inicio;
        while(aux != null){
            if (visitados.localizar(aux.getElem()) < 0) {
                listarEnProfundidadPR(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }
    
    private void listarEnProfundidadPR(NodoVert nodo, Lista vis){
        if (nodo != null) {
            vis.insertar(nodo.getElem(), vis.longitud()+1);
            NodoAdy aux = nodo.getPrimerAdy();
            while(aux != null){
                if (vis.localizar(aux.getVertice().getElem())<0) {
                    listarEnProfundidadPR(aux.getVertice(), vis);
                }
                aux = aux.getSigAdyacente();
            }
        }
    }
    
    private NodoVert ubicarVertice(Object buscado) {
        NodoVert aux = this.inicio;
        while (aux != null && !aux.getElem().equals(buscado)) {
            aux = aux.getSigVertice();
        }
        return aux;
    }

    public boolean insertarVertice(Object elem) {
        boolean exito = false;
        NodoVert aux = ubicarVertice(elem);
        if (aux == null) {
            this.inicio = new NodoVert(elem, this.inicio);
            exito = true;
        }
        return exito;
    }

    public boolean existeArco(Object origen, Object destino) {
        return ubicarArco(origen, destino) != null;
    }

    private NodoAdy ubicarArco(Object origen, Object destino) {
        NodoVert vertOrigen = ubicarVertice(origen);
        NodoAdy aux = null;
        boolean encontro = false;
        if (vertOrigen != null) {
            aux = vertOrigen.getPrimerAdy();
            while (aux != null && !encontro) {
                if (aux.getVertice().getElem().equals(destino)) {
                    encontro = true;
                }else{
                    aux = aux.getSigAdyacente();
                }
            }
        }
        return aux;
    }

    private boolean insertarAdyacente(NodoVert a, NodoVert b, int etiqueta) {
        boolean exito = true;
        NodoAdy aux = a.getPrimerAdy();
        if (aux == null) {
            a.setPrimerAdy(new NodoAdy(b, null, etiqueta));
        } else {
            while (exito && aux.getSigAdyacente() != null) {
                if (b.getElem().equals(aux.getVertice().getElem())) {
                    exito = false;
                } else {
                    aux = aux.getSigAdyacente();
                }
            }
            if (exito) {
                aux.setSigAdyacente(new NodoAdy(b, null, etiqueta));
            }
        }
        return exito;
    }

    public boolean insertarArco(Object origen, Object destino, int etiqueta) {
        boolean exito = false;
        NodoVert vertOrigen = ubicarVertice(origen);
        if (vertOrigen != null) {
            NodoVert vertDestino = ubicarVertice(destino);
            if (vertDestino != null) {
                exito = insertarAdyacente(vertOrigen, vertDestino, etiqueta);
                if (exito) {
                    exito = insertarAdyacente(vertDestino, vertOrigen, etiqueta);
                }
            }
        }
        return exito;
    }

    public boolean eliminarArco(Object origen, Object destino) {
        NodoVert vertOrigen = ubicarVertice(origen);
        boolean encontro = false;
        if (vertOrigen != null) {
            NodoVert vertDestino = eliminarArcoAux(vertOrigen, destino);
            if (vertDestino != null) {
                encontro = true;
                eliminarArcoAux(vertDestino, origen);
            }
        }
        return encontro;
    }

    private NodoVert eliminarArcoAux(NodoVert a, Object b) {
        NodoAdy aux = a.getPrimerAdy();
        NodoVert vertDestino = null;
        boolean encontro = false;
        if (aux != null) {
            NodoAdy anterior = null;
            while(aux != null && !encontro){
                if (aux.getVertice().getElem().equals(b)) {
                    encontro = true;
                    vertDestino = aux.getVertice();
                }else{
                    anterior = aux;
                    aux = aux.getSigAdyacente();
                }
            }
            if (encontro) {
                if (anterior != null) {
                    anterior.setSigAdyacente(aux.getSigAdyacente());
                }else{
                    a.setPrimerAdy(aux.getSigAdyacente());
                }
            }
        }
        return vertDestino;
    }
    
    public boolean esVacio(){
        return this.inicio == null;
    }
    
    public String toString() {
        String toString = "";
        if (this.inicio != null) {
            toString = toStringPR(this.inicio);
        }
        return toString;
    }
    
    private String toStringPR(NodoVert vertice) {
        String toString = "";
        if (vertice != null) {
            toString = "Vertice: " + vertice.getElem().toString();
            if (vertice.getPrimerAdy() != null) {
                toString = toString + " ady: ";
                NodoAdy aux = vertice.getPrimerAdy();
                while (aux != null) {
                    toString = toString + aux.getVertice().getElem().toString();
                    aux = aux.getSigAdyacente();
                    if (aux != null) {
                        toString = toString + ", ";
                    }
                }
            }
            toString = toString + "\n" + toStringPR(vertice.getSigVertice());
        }
        return toString;
    }
}
