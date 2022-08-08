package estructuras;

public class testDicc {

    public static void main(String[] args) {
        DiccionarioAVL arbol = new DiccionarioAVL();
        arbol.insertar(29, null);
        arbol.insertar(23, null);
        arbol.insertar(36, null);
        arbol.insertar(21, null);
        arbol.insertar(27, null);
        arbol.insertar(34, null);
        arbol.insertar(39, null);
        arbol.insertar(19, null);
        arbol.insertar(22, null);
        arbol.insertar(26, null);
        arbol.insertar(28, null);
        arbol.insertar(33, null);
        arbol.insertar(35, null);
        arbol.insertar(37, null);
        arbol.insertar(48, null);
        arbol.insertar(40, null);
        arbol.insertar(51, null);
        System.out.println(arbol.eliminar(36));
        System.out.println(arbol.toString());
        System.out.println(arbol.eliminar(48));
        System.out.println(arbol.toString());
    }

}
