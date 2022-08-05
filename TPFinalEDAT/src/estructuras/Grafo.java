package estructuras;

import java.util.HashMap;

public class Grafo {

    private NodoVert inicio;
    private int cantVertices;

    public Grafo() {
        this.inicio = null;
        this.cantVertices = 0;
    }

    //falta caminoMasCorto, caminoMasLargo, listarEnAnchura, clone
    public boolean existeCamino(Object origen, Object destino) {
        boolean exito = false;
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;
        while ((auxO == null || auxD == null) && aux != null) {
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

    private boolean existeCaminoPR(NodoVert nodo, Object dest, Lista vis) {
        boolean exito = false;
        if (nodo != null) {
            if (nodo.getElem().equals(dest)) {
                exito = true;
            } else {
                vis.insertar(nodo.getElem(), vis.longitud() + 1);
                NodoAdy aux = nodo.getPrimerAdy();
                while (!exito && aux != null) {
                    if (vis.localizar(aux.getVertice().getElem()) < 0) {
                        exito = existeCaminoPR(aux.getVertice(), dest, vis);
                    }
                    aux = aux.getSigAdyacente();
                }
            }
        }
        return exito;
    }

    public Lista listarEnAnchura() {
        Lista visitados = new Lista();
        NodoVert aux = this.inicio;
        while (aux != null) {
            if (visitados.localizar(aux.getElem()) < 0) {
                listarEnAnchuraPR(aux, visitados);
            }
        }
        return visitados;
    }

    public void listarEnAnchuraPR(NodoVert nodo, Lista vis) {
        Cola q = new Cola();
        vis.insertar(nodo.getElem(), vis.longitud() + 1);
        q.poner(nodo);
        while (!q.esVacia()) {
            NodoVert aux = (NodoVert) q.obtenerFrente();
            q.sacar();
            NodoAdy auxAdy = aux.getPrimerAdy();
            while (auxAdy != null) {
                if (vis.localizar(auxAdy.getVertice().getElem()) < 0) {
                    vis.insertar(auxAdy.getVertice().getElem(), vis.longitud() + 1);
                    q.poner(aux);
                }
            }
        }
    }

    public Lista caminoMasCortoPonderado(Object origen, Object destino) {
        HashMap visitados = new HashMap();
        return null;
    }

    public Lista caminoMasCorto(Object origen, Object destino) {
        HashMap visitados = new HashMap();
        Cola q = new Cola();
        Lista ls = new Lista();
        visitados.put(origen, origen);
        boolean encontro = false;
        NodoVert aux = ubicarVertice(origen);
        q.poner(aux);
        while (!q.esVacia() && !encontro) {
            aux = (NodoVert) q.obtenerFrente();
            q.sacar();
            NodoAdy auxAdy = aux.getPrimerAdy();
            while (auxAdy != null && !encontro) {
                if (!visitados.containsKey(auxAdy.getVertice().getElem())) {
                    if (auxAdy.getVertice().getElem().equals(destino)) {
                        encontro = true;
                    } else {
                        q.poner(auxAdy.getVertice());
                    }
                    visitados.put(auxAdy.getVertice().getElem(), aux.getElem());
                }
                auxAdy = auxAdy.getSigAdyacente();
            }
        }
        //reconstruir camino de destino hacia origen insertando en pos 1
        ls.insertar(destino, 1);
        Object backtrack = destino;
        while (!backtrack.equals(origen)) {
            backtrack = visitados.get(backtrack);
            ls.insertar(backtrack, 1);
        }
        return ls;
    }

    public Lista listarEnProfundidad() {
        Lista visitados = new Lista();
        NodoVert aux = this.inicio;
        while (aux != null) {
            if (visitados.localizar(aux.getElem()) < 0) {
                listarEnProfundidadPR(aux, visitados);
            }
            aux = aux.getSigVertice();
        }
        return visitados;
    }

    private void listarEnProfundidadPR(NodoVert nodo, Lista vis) {
        if (nodo != null) {
            vis.insertar(nodo.getElem(), vis.longitud() + 1);
            NodoAdy aux = nodo.getPrimerAdy();
            while (aux != null) {
                if (vis.localizar(aux.getVertice().getElem()) < 0) {
                    listarEnProfundidadPR(aux.getVertice(), vis);
                }
                aux = aux.getSigAdyacente();
            }
        }
    }

    private void caminoLiviano(NodoVert nodo, Lista vis, Lista caminoMasCorto, int km, int tope) {
        if (nodo != null) {
            vis.insertar(nodo.getElem(), vis.longitud() + 1);
            NodoAdy aux = nodo.getPrimerAdy();
            while (aux != null) {
                if (vis.localizar(aux.getVertice().getElem()) < 0) {
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
        this.cantVertices++;
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
                } else {
                    aux = aux.getSigAdyacente();
                }
            }
        }
        return aux;
    }

    public boolean modificarArco(Object origen, Object destino, int etiqueta) {
        boolean exito = false;
        NodoAdy arco = ubicarArco(origen, destino);
        if (arco != null) {
            exito = true;
            arco.setEtiqueta(etiqueta);
        }
        return exito;
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
            while (aux != null && !encontro) {
                if (aux.getVertice().getElem().equals(b)) {
                    encontro = true;
                    vertDestino = aux.getVertice();
                } else {
                    anterior = aux;
                    aux = aux.getSigAdyacente();
                }
            }
            if (encontro) {
                if (anterior != null) {
                    anterior.setSigAdyacente(aux.getSigAdyacente());
                } else {
                    a.setPrimerAdy(aux.getSigAdyacente());
                }
            }
        }
        return vertDestino;
    }

    public boolean esVacio() {
        return this.inicio == null;
    }

    public String toString() {
        String toString = "";
        if (this.inicio != null) {
            toString = toStringPR(this.inicio);
        }
        return toString;
    }

    public boolean eliminarVertice(Object elemVertice) {
        boolean exito = false;
        NodoVert vertice = null;
        NodoVert auxVert = this.inicio;
        NodoVert vertAnterior = null;
        while (vertice == null && auxVert != null) {
            if (auxVert.getElem().equals(elemVertice)) {
                vertice = auxVert;
            } else {
                vertAnterior = auxVert;
            }
            auxVert = auxVert.getSigVertice();
        }
        if (vertice != null) {
            exito = true;
            NodoAdy aux = vertice.getPrimerAdy();
            while (aux != null) {
                eliminarArcoAux(aux.getVertice(), elemVertice);
                aux = aux.getSigAdyacente();
            }
            if (vertAnterior != null) {
                vertAnterior.setSigVert(auxVert);
            } else {
                this.inicio = auxVert;
            }
        }
        if (exito) {
            this.cantVertices--;
        }
        return exito;
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
