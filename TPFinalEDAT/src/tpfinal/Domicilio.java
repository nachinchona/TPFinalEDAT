package tpfinal;

public class Domicilio {
    private String calle;
    private int numero;
    private String ciudad;
    private String cp;
    
    public Domicilio(String calle, int numero, String ciudad, String cp){
        this.calle = calle;
        this.numero = numero;
        this.ciudad = ciudad;
        this.cp = cp;
    }

    public String toString(){
        return calle+";"+numero+";"+ciudad+";"+cp;
    }
    
    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }
    
}
