package estructuras;

public class TestGrafo {

    public static void main(String[] args) {
        Grafo grafo = new Grafo();
        grafo.insertarVertice('A');
        grafo.insertarVertice('B');
        grafo.insertarVertice('C');
        grafo.insertarVertice('D');
        grafo.insertarVertice('E');
        grafo.insertarVertice('F');
        grafo.insertarVertice('G');
        grafo.insertarVertice('H');
        grafo.insertarVertice('J');
        
        grafo.insertarArco('A', 'B', 0);
        grafo.insertarArco('A', 'G', 0);
        grafo.insertarArco('A', 'C', 0);
        grafo.insertarArco('B', 'D', 0);
        grafo.insertarArco('C', 'F', 0);
        grafo.insertarArco('G', 'J', 0);
        grafo.insertarArco('C', 'G', 0);
        grafo.insertarArco('D', 'G', 0);
        grafo.insertarArco('D', 'H', 0);
        grafo.insertarArco('H', 'J', 0);
        grafo.insertarArco('F', 'J', 0);
        grafo.insertarArco('G', 'H', 0);
        System.out.println(grafo.toString());
        System.out.println(grafo.caminoMasCorto('A', 'H'));
        System.out.println(grafo.toString());
    }

}
