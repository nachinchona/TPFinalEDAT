package estructuras;

public class DiccionarioAVL {

    private NodoDiccAVL raiz;

    public DiccionarioAVL() {
        this.raiz = null;
    }

    private int balance(NodoDiccAVL nodo) {
        int altIzq = -1;
        int altDer = -1;

        if (nodo.getIzquierdo() != null) {
            altIzq = nodo.getIzquierdo().getAltura();
        }
        if (nodo.getDerecho() != null) {
            altDer = nodo.getDerecho().getAltura();
        }
        return altIzq - altDer;
    }
    
    public Object obtenerDato(Comparable clave){
        return obtenerNodo(this.raiz, clave).getDato();
    }
    
    private NodoDiccAVL obtenerNodo(NodoDiccAVL nodo, Comparable elem) {
        NodoDiccAVL retorno = null;
        if (nodo != null) {
            if (nodo.getClave().equals(elem)) {
                retorno = nodo;
            } else {
                if (nodo.getClave().compareTo(elem) < 0) {
                    retorno = obtenerNodo(nodo.getDerecho(), elem);
                } else {
                    retorno = obtenerNodo(nodo.getIzquierdo(), elem);
                }
            }
        }
        return retorno;
    }
    
    public boolean insertar(Comparable elem, Object dato) {
        boolean exito = true;
        if (this.raiz == null) {
            this.raiz = new NodoDiccAVL(elem, dato, null, null);
        } else {
            exito = insertarPR(null, this.raiz, elem, dato);
        }
        return exito;
    }

    private boolean insertarPR(NodoDiccAVL padre, NodoDiccAVL nodo, Comparable elem, Object dato) {
        boolean exito = true;
        if (nodo != null) {
            if (elem.compareTo(nodo.getClave()) == 0) {
                exito = false;
            } else {
                if (elem.compareTo(nodo.getClave()) < 0) {
                    //es menor
                    if (nodo.getIzquierdo() != null) {
                        exito = insertarPR(nodo, nodo.getIzquierdo(), elem, dato);
                    } else {
                        nodo.setIzquierdo(new NodoDiccAVL(elem, dato, null, null));
                    }
                } else {
                    //es mayor
                    if (nodo.getDerecho() != null) {
                        exito = insertarPR(nodo, nodo.getDerecho(), elem, dato);
                    } else {
                        nodo.setDerecho(new NodoDiccAVL(elem, dato, null, null));
                    }
                }
            }
            if (exito) {
                nodo.recalcularAltura();
                if (padre != null) {
                    balancear(padre, nodo, balance(nodo));
                }else{
                    balancearRaiz(nodo, balance(nodo));
                }
            }
        }

        return exito;
    }

    private void balancearRaiz(NodoDiccAVL nodo, int balance) {
        switch (balance) {
            default:
                break;
            case -2:
                if (balance(nodo.getDerecho()) == -1) {
                    //balance simple por izquierda
                    rotacionIzq(null, nodo);
                } else {
                    //balance doble derecha izquierda
                    nodo.setDerecho(rotacionDer(nodo, nodo.getDerecho()));
                    rotacionIzq(null, nodo);
                }
                break;
            case 2:
                if (balance(nodo.getIzquierdo()) == 1) {
                    //balance simple por derecha
                    rotacionDer(null, nodo);
                } else {
                    //balance doble izquierda derecha
                    nodo.setIzquierdo(rotacionIzq(nodo, nodo.getIzquierdo()));
                    rotacionDer(null, nodo);
                }
                break;
        }
    }
    
    private void balancear(NodoDiccAVL padre, NodoDiccAVL nodo, int balance) {
        switch (balance) {
            default:
                break;
            case -2:
                if (balance(nodo.getDerecho()) == -1) {
                    //balance simple por izquierda
                    if (padre.getClave().compareTo(nodo.getClave()) < 0) {
                        padre.setDerecho(rotacionIzq(padre, nodo));
                    } else {
                        padre.setIzquierdo(rotacionIzq(padre, nodo));
                    }
                } else {
                    //balance doble derecha izquierda
                    nodo.setDerecho(rotacionDer(nodo, nodo.getDerecho()));
                    if (padre.getClave().compareTo(nodo.getClave()) < 0) {
                        padre.setDerecho(rotacionIzq(padre, nodo));
                    } else {
                        padre.setIzquierdo(rotacionIzq(padre, nodo));
                    }
                }
                break;
            case 2:
                if (balance(nodo.getIzquierdo()) == 1) {
                    //balance simple por izquierda
                    if (padre.getClave().compareTo(nodo.getClave()) < 0) {
                        padre.setDerecho(rotacionDer(padre, nodo));
                    } else {
                        padre.setIzquierdo(rotacionDer(padre, nodo));
                    }
                } else {
                    //balance doble izquierda derecha
                    nodo.setIzquierdo(rotacionIzq(nodo, nodo.getIzquierdo()));
                    if (padre.getClave().compareTo(nodo.getClave()) < 0) {
                        padre.setDerecho(rotacionDer(padre, nodo));
                    } else {
                        padre.setIzquierdo(rotacionDer(padre, nodo));
                    }
                }
                break;
        }
    }

    private NodoDiccAVL rotacionIzq(NodoDiccAVL padre, NodoDiccAVL r) {
        NodoDiccAVL h;
        if (padre != null) {
            h = r.getDerecho();
            NodoDiccAVL temp = h.getIzquierdo();
            h.setIzquierdo(r);
            r.setDerecho(temp);
            h.recalcularAltura();
            r.recalcularAltura();
        } else {
            h = r.getDerecho();
            NodoDiccAVL temp = h.getIzquierdo();
            h.setIzquierdo(r);
            r.setDerecho(temp);
            this.raiz = h;
            h.recalcularAltura();
            r.recalcularAltura();
        }
        return h;
    }

    private NodoDiccAVL rotacionDer(NodoDiccAVL padre, NodoDiccAVL r) {
        NodoDiccAVL h;
        if (padre != null) {
            h = r.getIzquierdo();
            NodoDiccAVL temp = h.getDerecho();
            h.setDerecho(r);
            r.setIzquierdo(temp);
            h.recalcularAltura();
            r.recalcularAltura();
        } else {
            h = r.getIzquierdo();
            NodoDiccAVL temp = h.getDerecho();
            h.setDerecho(r);
            r.setIzquierdo(temp);
            this.raiz = h;
            h.recalcularAltura();
            r.recalcularAltura();
        }
        return h;
    }

    public boolean eliminar(Comparable elem) {
        boolean exito = true;
        if (this.raiz != null) {
            exito = eliminarPR(null, this.raiz, elem);
        }
        return exito;
    }

    private boolean eliminarPR(NodoDiccAVL padre, NodoDiccAVL nodo, Comparable elem) {
        boolean exito = false;
        if (nodo != null) {
            if (nodo.getClave().compareTo(elem) == 0) {
                exito = true;
                eliminarSegunCaso(padre, nodo, determinarCaso(nodo));
            } else {
                if (elem.compareTo(nodo.getClave()) < 0) {
                    exito = eliminarPR(nodo, nodo.getIzquierdo(), elem);
                } else {
                    exito = eliminarPR(nodo, nodo.getDerecho(), elem);
                }
            }
        }
        return exito;
    }

    private int determinarCaso(NodoDiccAVL nodo) {
        int caso;
        if (nodo.getDerecho() != null) {
            if (nodo.getIzquierdo() != null) {
                //dos hijos
                caso = 3;
            } else {
                //con subarbol derecho
                caso = 2;
            }
        } else {
            if (nodo.getIzquierdo() == null) {
                //sin hijos
                caso = 1;
            } else {
                //con subarbol izquierdo
                caso = 2;
            }
        }
        return caso;
    }

    private void eliminarSegunCaso(NodoDiccAVL padre, NodoDiccAVL nodo, int caso) {
        System.out.println(caso);
        switch (caso) {
            case 1:
                padre.setIzquierdo(null);
                padre.setDerecho(null);
                break;
            case 2:
                if (nodo.getClave().compareTo(padre.getClave()) > 0) {
                    padre.setDerecho(nodo.getDerecho());
                } else {
                    padre.setDerecho(nodo.getIzquierdo());
                }
                break;
            case 3:
                NodoDiccAVL padreAux = nodo;
                NodoDiccAVL aux = nodo.getIzquierdo();
                if (aux.getDerecho() != null) {
                    padreAux = nodo.getIzquierdo();
                }
                while (aux.getDerecho() != null) {
                    padreAux = nodo.getIzquierdo();
                    aux = aux.getDerecho();
                }
                intercambiar(nodo, aux);
                System.out.println(padreAux.getClave());
                System.out.println(padreAux.getIzquierdo().getClave());
                System.out.println(padreAux.getDerecho().getClave());
                if (padreAux.getIzquierdo().getClave().compareTo(padreAux.getClave()) == 0) {
                    padreAux.setIzquierdo(null);
                } else {
                    padreAux.setDerecho(null);
                }
                break;
        }
    }

    private void intercambiar(NodoDiccAVL nodo1, NodoDiccAVL nodo2) {
        nodo1.setClave(nodo2.getClave());
    }

    public boolean existeClave(Comparable elem) {
        boolean pertenece = false;
        if (this.raiz != null) {
            pertenece = pertenecePR(this.raiz, elem);
        }
        return pertenece;
    }

    private boolean pertenecePR(NodoDiccAVL nodo, Comparable elem) {
        boolean pertenece = false;
        if (nodo != null) {
            if (nodo.getClave().compareTo(elem) == 0) {
                pertenece = true;
            } else {
                if (nodo.getClave().compareTo(elem) < 0) {
                    pertenece = pertenecePR(nodo.getIzquierdo(), elem);
                } else {
                    pertenece = pertenecePR(nodo.getDerecho(), elem);
                }
            }
        }
        return pertenece;
    }

    public String toString() {
        String toString = toStringPR(this.raiz);
        return toString;
    }

    public Lista listarDatos() {
        Lista lista = new Lista();
        listarDatosPR(this.raiz, lista);
        return lista;
    }
    
    private void listarDatosPR(NodoDiccAVL nodo, Lista temp) {
        if (nodo != null) {
            listarDatosPR(nodo.getIzquierdo(), temp);
            temp.insertar(nodo.getDato(), temp.longitud() + 1);
            listarDatosPR(nodo.getDerecho(), temp);
        }
    }
    
    public Lista listarClaves() {
        Lista lista = new Lista();
        listarClavesPR(this.raiz, lista);
        return lista;
    }
    
    private void listarClavesPR(NodoDiccAVL nodo, Lista temp) {
        if (nodo != null) {
            listarClavesPR(nodo.getIzquierdo(), temp);
            temp.insertar(nodo.getClave(), temp.longitud() + 1);
            listarClavesPR(nodo.getDerecho(), temp);
        }
    }

    public void vaciar() {
        this.raiz = null;
    }

    private String toStringPR(NodoDiccAVL nodo) {
        String toString = "Arbol vacio";
        if (nodo != null) {
            toString = nodo.getClave().toString() + " altura: " + nodo.getAltura();
            NodoDiccAVL hijoIzq = nodo.getIzquierdo();
            NodoDiccAVL hijoDer = nodo.getDerecho();
            if (hijoIzq != null) {
                toString = toString + ", H.I: " + hijoIzq.getClave().toString();

            } else {
                toString = toString + ", H.I: -";
            }
            if (hijoDer != null) {
                toString = toString + ", H.D: " + hijoDer.getClave().toString() + "\n";
            } else {
                toString = toString + ", H.D: -\n";
            }

            if (hijoIzq != null) {
                toString = toString + toStringPR(hijoIzq);
            }

            if (hijoDer != null) {
                toString = toString + toStringPR(hijoDer);
            }
        }
        return toString;
    }
}