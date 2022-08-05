package estructuras;

/**
 *
 * @author ignacio.navarro
 */
public class Cola {

    private Nodo frente;
    private Nodo fin;

    public Cola() {
        this.frente = null;
        this.fin = null;
    }

    public boolean poner(Object elem) {
        Nodo nuevo = new Nodo(elem, null);
        if (this.frente == null) {
            this.frente = nuevo;
            this.fin = nuevo;
        } else {
            this.fin.setEnlace(nuevo);
            this.fin = nuevo;
        }
        return true;
    }

    public boolean sacar() {
        boolean exito;
        if (frente != null) {
            this.frente = this.frente.getEnlace();
            if (frente == null) {
                fin = null;
            }
            exito = true;
        } else {
            exito = false;
        }

        return exito;
    }

    public Object obtenerFrente() {
        Object elem;
        if (!this.esVacia()) {
            elem = this.frente.getElemento();
        } else {
            elem = null;
        }
        return elem;
    }

    public void vaciar() {
        this.frente = null;
        this.fin = null;
    }

    public boolean esVacia() {
        return frente == null;
    }

    public Cola clone() {
        Cola clon = new Cola();
        if (this.frente != null) {
            clonePR(clon, this.frente);
        }
        return clon;
    }

    public String toString() {
        String toString = toStringPR(this.frente);
        return toString;
    }

    private String toStringPR(Nodo temp) {
        String toString = "";
        if (temp != null) {
            Nodo enlace = temp.getEnlace();
            if (enlace != null) {
                toString = temp.getElemento().toString() + " / " + toStringPR(enlace);
            } else {
                toString = temp.getElemento().toString();
            }

        }
        return toString;
    }

    private void clonePR(Cola clon, Nodo temp) {
        if (temp != null) {
            Nodo aux = new Nodo(temp.getElemento(), null);
            if (temp == this.frente) {
                clon.frente = aux;
                clon.fin = aux;
            } else {
                clon.fin.setEnlace(aux);
                clon.fin = aux;
            }

            Nodo siguiente = temp.getEnlace();
            if (siguiente != null) {
                clonePR(clon, siguiente);
            }
        }
    }
}
