package estructuras;

public class DiccionarioAVL {

    private NodoDiccAVL raiz;

    public DiccionarioAVL() {
        this.raiz = null;
    }

    public boolean esVacio() {
        return this.raiz == null;
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

    public Object obtenerInformacion(Comparable clave) {
        NodoDiccAVL nodo = obtenerNodo(this.raiz, clave);
        Object retorno;
        if (nodo != null) {
            retorno = nodo.getDato();
        } else {
            retorno = null;
        }
        return retorno;
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
                } else {
                    balancearRaiz(nodo, balance(nodo));
                }
            }
        }

        return exito;
    }

    private void balancearRaiz(NodoDiccAVL nodo, int balance) {
        int balanceAux;
        switch (balance) {
            default:
                break;
            case -2:
                balanceAux = balance(nodo.getDerecho());
                if (balanceAux == -1 || balanceAux == 0) {
                    //balance simple por izquierda
                    this.raiz = rotacionIzq(null, nodo);
                } else {
                    //balance doble derecha izquierda
                    nodo.setDerecho(rotacionDer(nodo, nodo.getDerecho()));
                    rotacionIzq(null, nodo);
                }
                break;
            case 2:
                balanceAux = balance(nodo.getIzquierdo());
                if (balanceAux == 1 || balanceAux == 0) {
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
        int balanceAux;
        switch (balance) {
            default:
                break;
            case -2:
                balanceAux = balance(nodo.getDerecho());
                if (balanceAux == -1 || balanceAux == 0) {
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
                balanceAux = balance(nodo.getIzquierdo());
                if (balanceAux == 1 || balanceAux == 0) {
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
            h = r.getDerecho();
            NodoDiccAVL temp = h.getIzquierdo();
            h.setIzquierdo(r);
            r.setDerecho(temp);
            h.recalcularAltura();
            r.recalcularAltura();
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
            if (this.raiz.getClave().equals(elem)) {
                exito = true;
                eliminarRaiz(determinarCaso(this.raiz));
            } else {
                exito = eliminarPR(null, this.raiz, elem);
            }
            if (exito) {
                this.raiz.recalcularAltura();
                balancearRaiz(this.raiz, balance(this.raiz));
            }
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
            if (exito) {
                nodo.recalcularAltura();
                if (padre == null) {
                    balancearRaiz(nodo, balance(nodo));
                } else {
                    padre.recalcularAltura();
                    balancear(padre, nodo, balance(nodo));
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

    private void eliminarRaiz(int caso) {
        NodoDiccAVL nodo = this.raiz;
        switch (caso) {
            case 1:
                this.raiz = null;
                break;
            case 2:
                if (nodo.getIzquierdo() != null) {
                    this.raiz = nodo.getIzquierdo();
                } else {
                    this.raiz = nodo.getDerecho();
                }
                break;
            case 3:
                NodoDiccAVL padreAux = nodo;
                NodoDiccAVL aux = nodo.getDerecho();
                if (aux.getIzquierdo() == null) {
                    intercambiar(nodo, aux);
                    nodo.setDerecho(aux.getDerecho());
                } else {
                    while (aux.getIzquierdo() != null) {
                        padreAux = aux;
                        aux = aux.getIzquierdo();
                    }
                    intercambiar(nodo, aux);
                    padreAux.setIzquierdo(aux.getDerecho());
                }
                balancear(nodo, padreAux, balance(padreAux));
                break;
        }
    }

    private void eliminarSegunCaso(NodoDiccAVL padre, NodoDiccAVL nodo, int caso) {
        switch (caso) {
            //sin hijos
            case 1:
                if (nodo.getClave().compareTo(padre.getClave()) < 0) {
                    padre.setIzquierdo(null);
                } else {
                    padre.setDerecho(null);
                }
                break;
            //con un hijo
            case 2:
                if (nodo.getClave().compareTo(padre.getClave()) < 0) {
                    if (nodo.getDerecho() != null) {
                        padre.setIzquierdo(nodo.getDerecho());
                    } else {
                        padre.setIzquierdo(nodo.getIzquierdo());
                    }
                } else {
                    if (nodo.getDerecho() != null) {
                        padre.setDerecho(nodo.getDerecho());
                    } else {
                        padre.setDerecho(nodo.getIzquierdo());
                    }
                }
                break;
            //con dos hijos
            case 3:
                NodoDiccAVL padreAux = nodo;
                NodoDiccAVL aux = nodo.getDerecho();
                if (aux.getIzquierdo() == null) {
                    intercambiar(nodo, aux);
                    nodo.setDerecho(aux.getDerecho());
                } else {
                    while (aux.getIzquierdo() != null) {
                        padreAux = aux;
                        aux = aux.getIzquierdo();
                    }
                    intercambiar(nodo, aux);
                    padreAux.setIzquierdo(aux.getDerecho());
                }
                break;
        }
    }

    private void intercambiar(NodoDiccAVL nodo1, NodoDiccAVL nodo2) {
        nodo1.setClave(nodo2.getClave());
        nodo1.setDato(nodo2.getDato());
    }

    public boolean existeClave(Comparable elem) {
        return pertenecePR(this.raiz, elem);
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

    @Override
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
