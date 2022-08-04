package estructuras;
public class TestMio {

    public static void main(String[] args) {
        DiccionarioAVL arbol = new DiccionarioAVL();
        arbol.insertar(20, null);
        arbol.insertar(10, null);
        arbol.insertar(30, null);
        arbol.insertar(15, null);
        arbol.insertar(25, null);
        arbol.insertar(12, null);
        arbol.insertar(11, null);
        arbol.insertar(5, null);
        arbol.insertar(23, null);
        arbol.insertar(3, null);
        arbol.insertar(26, null);
        arbol.insertar(27, null);
        System.out.println(arbol.toString());
        System.out.println("elimino 10");
        arbol.eliminar(10);
        System.out.println(arbol.toString());
        System.out.println("elimino 11");
        arbol.eliminar(11);
        System.out.println(arbol.toString());
        System.out.println("elimino 20");
        arbol.eliminar(20);
        System.out.println(arbol.toString());
        System.out.println("elimino 27");
        arbol.eliminar(27);
        System.out.println(arbol.toString());
        System.out.println("elimino 15");
        arbol.eliminar(15);
        System.out.println(arbol.toString());
    }
}
