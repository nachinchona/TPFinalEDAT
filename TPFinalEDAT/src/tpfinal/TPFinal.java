package tpfinal;
import estructuras.*;
import java.util.HashMap;
public class TPFinal {

    public static void main(String[] args) {
        Domicilio a = new Domicilio("ABC", 123, "San Martín", "123A");
        //linea A
        Estacion a1 = new Estacion("Retiro", a, 3, 1);
        Estacion a2 = new Estacion("Caballito", a, 3, 2);
        Estacion a3 = new Estacion("Liniers", a, 4, 2);
        //linea B
        Estacion b1 = new Estacion("Haedo", a, 3, 2);
        Estacion b2 = new Estacion("Ituzaingó", a, 3, 0);
        //linea C (comparte Moreno con linea B)
        Estacion bc1 = new Estacion("Moreno", a, 4, 1);
        
        //inserto las estaciones en un diccionario AVL
        DiccionarioAVL estaciones = new DiccionarioAVL();
        estaciones.insertar(a1.getNombre(), a1);
        estaciones.insertar(a2.getNombre(), a2);
        estaciones.insertar(a3.getNombre(), a3);
        estaciones.insertar(b1.getNombre(), b1);
        estaciones.insertar(b2.getNombre(), b2);
        estaciones.insertar(bc1.getNombre(), bc1);
        System.out.println("estaciones");
        System.out.println(estaciones.toString());
        //rieles que conectan estaciones representadas en un grafo
        Grafo conexionEstaciones = new Grafo();
        
        //insertar estaciones
        conexionEstaciones.insertarVertice(a1);
        conexionEstaciones.insertarVertice(a2);
        conexionEstaciones.insertarVertice(a3);
        conexionEstaciones.insertarVertice(b1);
        conexionEstaciones.insertarVertice(b2);
        conexionEstaciones.insertarVertice(bc1);
        
        //insertar rieles
        conexionEstaciones.insertarArco(a1, a2, 54);
        conexionEstaciones.insertarArco(a2, a3, 76);
        conexionEstaciones.insertarArco(b1, a3, 71);
        conexionEstaciones.insertarArco(b1, b2, 45);
        conexionEstaciones.insertarArco(b2, bc1, 145);
        
        System.out.println("");
        System.out.println("grafo");
        System.out.println(conexionEstaciones.toString());
        //mapeo lineas estaciones
        HashMap<String, Estacion> estacionLinea = new HashMap<>();
        estacionLinea.put("A", a1);
        estacionLinea.put("A", a2);
        estacionLinea.put("A", a3);
        estacionLinea.put("B", b1);
        estacionLinea.put("B", b2);
        estacionLinea.put("B", bc1);
        estacionLinea.put("C", bc1);
        
        System.out.println("hashmap");
        System.out.println(estacionLinea.toString());
        
        //trenes
        Tren tren1 = new Tren(001, "diesel", 2, 3, "A");
        Tren tren2 = new Tren(002, "oil", 2, 3, "B");
        Tren tren3 = new Tren(003, "electricidad", 2, 3, "B");
        Tren tren4 = new Tren(004, "diesel", 2, 3, "C");
        
        //diccionario trenes
        DiccionarioAVL trenes = new DiccionarioAVL();
        trenes.insertar(tren1.getId(), tren1);
        trenes.insertar(tren2.getId(), tren2);
        trenes.insertar(tren3.getId(), tren3);
        trenes.insertar(tren4.getId(), tren4);
        System.out.println("");
        System.out.println("trenes");
        System.out.println(trenes.toString());
    }

}
