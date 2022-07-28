package estructuras;

public class Lista {

    Nodo cabecera;

    public Lista() {
        this.cabecera = null;
    }

    public void agregarElem(Object nuevo, int x) {
        int i = 1;
        Nodo aux = this.cabecera;
        int nuevaPos = i + x;
        this.cabecera = new Nodo(nuevo, aux);
        aux = this.cabecera;
        while (aux != null) {
            if (i == nuevaPos) {
                aux.setEnlace(new Nodo(nuevo, aux.getEnlace()));
                nuevaPos = i + x + 1;
            }
            i++;
            aux = aux.getEnlace();
        }
    }

    public boolean insertar(Object elem, int pos) {
        boolean exito = true;
        if (pos < 1 || pos > this.longitud() + 1) {
            exito = false;
        } else {
            if (pos == 1) {
                this.cabecera = new Nodo(elem, this.cabecera);
            } else {
                int i = 1;
                Nodo aux = this.cabecera;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                Nodo nuevo = new Nodo(elem, aux.getEnlace());
                aux.setEnlace(nuevo);
            }
        }
        return exito;
    }

    public boolean eliminar(int pos) {
        boolean exito = true;
        if (pos < 1 || pos > this.longitud() + 1 || this.cabecera == null) {
            exito = false;
        } else {
            if (pos == 1) {
                this.cabecera = this.cabecera.getEnlace();
            } else {
                int i = 1;
                Nodo aux = this.cabecera;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                aux.setEnlace(aux.getEnlace().getEnlace());
            }
        }
        return exito;
    }

    public Object recuperar(int pos) {
        //precondicion: la posicion es valida
        Object elem = null;
        if (!(pos <= 0 || pos > this.longitud())) {
            int i = 1;
            Nodo aux = this.cabecera;
            while (i < pos) {
                aux = aux.getEnlace();
                i++;
            }
            elem = aux.getElemento();
        }
        return elem;
    }

    public int localizar(Object elem) {
        int pos = -1;
        Nodo aux = this.cabecera;
        boolean encontro = false;
        int i = 1;
        int longitud = this.longitud();
        while (i <= longitud && !encontro) {
            encontro = elem.equals(aux.getElemento());
            if (encontro) {
                pos = i;
            } else {
                aux = aux.getEnlace();
                i++;
            }
        }
        return pos;
    }

    public int longitud() {
        int i = 0;
        if (cabecera != null) {
            i = 1;
            Nodo enlace = this.cabecera.getEnlace();
            while (enlace != null) {
                enlace = enlace.getEnlace();
                i++;
            }
        }
        return i;
    }

    public void vaciar() {
        this.cabecera = null;
    }

    public boolean esVacia() {
        return this.cabecera == null;
    }

    public Lista clone() {
        Lista clon = new Lista();
        if (this.cabecera != null) {
            Nodo aux = this.cabecera.getEnlace();
            Nodo nuevo;
            int longitud = this.longitud();
            clon.cabecera = new Nodo(this.cabecera.getElemento(), clon.cabecera);
            Nodo aux2 = clon.cabecera;
            for (int i = 1; i < longitud; i++) {
                nuevo = new Nodo(aux.getElemento(), null);
                aux2.setEnlace(nuevo);
                aux = aux.getEnlace();
                aux2 = aux2.getEnlace();
            }
        }
        return clon;
    }

    public String toString() {
        String toString = "";
        int longitud = this.longitud();
        Nodo aux = this.cabecera;

        for (int i = 1; i <= longitud; i++) {
            if (i == 1) {
                toString = toString + aux.getElemento().toString();
            } else {
                toString = toString + "," + aux.getElemento().toString();
            }
            aux = aux.getEnlace();
        }

        return toString;
    }

    public Lista obtenerMultiplos(int num) {
        Lista listaMultiplos = new Lista();
        if (num >= 1) {
            int i = 1;
            Nodo aux = this.cabecera;
            Nodo nuevaCabecera = null;
            while (aux != null) {
                if (nuevaCabecera == null && i == num) {
                    nuevaCabecera = new Nodo(aux.getElemento(), null);
                    listaMultiplos.cabecera = nuevaCabecera;
                } else {
                    if (nuevaCabecera != null && i % num == 0) {
                        nuevaCabecera.setEnlace(new Nodo(aux.getElemento(), null));
                        nuevaCabecera = nuevaCabecera.getEnlace();
                    }
                }
                aux = aux.getEnlace();
                i++;
            }
        }

        return listaMultiplos;
    }

    public void eliminarApariciones(Object x) {
        Nodo aux = this.cabecera;
        Nodo anterior = null;
        while (aux != null) {
            if (aux.getElemento().equals(x)) {
                if (aux == this.cabecera) {
                    this.cabecera = this.cabecera.getEnlace();
                } else {
                    anterior.setEnlace(aux.getEnlace());
                }
            }
            anterior = aux;
            aux = aux.getEnlace();
        }
    }

    /* public Lista obtenerMultiplos(int n){
        int longitud = this.longitud();
        Lista nueva = new Lista();
        int longitudNueva = nueva.longitud();
        Nodo aux = this.cabecera;
        for (int i = 1; i <= longitud; i++) {
            if (i % n == 0) {
                nueva.insertar(aux.getElemento(), longitudNueva +1);
            }
            aux = aux.getEnlace();
        }
        return nueva;
    } */
}
