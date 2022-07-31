package tpfinal;

import estructuras.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class TPFinal {

    //estructuras
    private static DiccionarioAVL estaciones = new DiccionarioAVL();
    private static DiccionarioAVL trenes = new DiccionarioAVL();
    private static Grafo conexionEstaciones = new Grafo();
    private static HashMap<String, Lista> hashMap = new HashMap<>();

    //entrada y salida
    private static FileWriter salida;
    private static FileReader entrada;

    static {
        try {
            salida = new FileWriter("C:\\Users\\nachinchona\\Desktop\\test.txt");
            entrada = new FileReader("C:\\Users\\nachinchona\\Desktop\\cargaInicial.txt");
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int seleccion;

        //carga de lote txt
        cargaInicial();

        //menu
        imprimirMenu();

        do {
            System.out.println("Ingrese la opción deseada:");
            seleccion = sc.nextInt();
            switch(seleccion){
                case 1: 
            }
        } while (seleccion != 20);

        salida.close();
    }
    
    public static void imprimirMenu() {
        System.out.println("-------------Menú de opciones---------------------");
        System.out.println("1. Insertar tren.");
        System.out.println("2. Insertar estación.");
        System.out.println("3. Insertar línea.");
        System.out.println("4. Insertar riel.");
        System.out.println("5. Modificar tren.");
        System.out.println("6. Modificar estación.");
        System.out.println("7. Modificar línea.");
        System.out.println("8. Modificar km de riel.");
        System.out.println("9. Eliminar tren.");
        System.out.println("10. Eliminar estación.");
        System.out.println("11. Eliminar línea.");
        System.out.println("12. Eliminar riel.");
        System.out.println("13. Mostrar información de tren.");
        System.out.println("14. Mostrar ciudades visitadas por un tren determinado según su línea.");
        System.out.println("15. Mostrar información de estación.");
        System.out.println("16. Mostrar todas las estaciones que empiezan por el prefijo ingresado.");
        System.out.println("17. Obtener camino de estación A a estación B que pase por menos estaciones.");
        System.out.println("18. Obtener camino de estación A a estación B que recorra la menor cantidad de kilometros.");
        System.out.println("19. Mostrar sistema.");
        System.out.println("20. Cerrar programa.");
    }
    
    //métodos para carga inicial
    
    public static void cargaInicial() throws IOException {
        salida.write("Inicio del log.\n\n");
        salida.write("INICIO CARGA INICIAL");
        salida.write("\n42 ESTACIONES 41 RIELES 3 LINEAS 20 TRENES\n");
        try (BufferedReader input = new BufferedReader(entrada)) {
            String line;
            while ((line = input.readLine()) != null) {
                leerEntrada(line);
            }
        }
        salida.write("\nFIN CARGA INICIAL\n");
        salida.write("\nFin del log.");
    }
    
    public static void leerEntrada(String line) throws IOException {
        StringTokenizer cadena = new StringTokenizer(line, ";");
        if (cadena.hasMoreTokens()) {
            switch (cadena.nextToken()) {
                case "E":
                    insertarEstacionSegunCadena(cadena);
                    break;
                case "T":
                    insertarTrenSegunCadena(cadena);
                    break;
                case "R":
                    insertarRielSegunCadena(cadena);
                    break;
                case "L":
                    insertarLineaSegunCadena(cadena);
                    break;
            }
        }
    }

    public static boolean insertarRielSegunCadena(StringTokenizer cadena) throws IOException {
        boolean exito = false;
        int length = cadena.countTokens();
        if (length == 3) {
            String origen = cadena.nextToken();
            String destino = cadena.nextToken();
            int km = Integer.parseInt(cadena.nextToken());
            Object estacionOrigen = estaciones.obtenerInformacion(origen);
            if (estacionOrigen != null) {
                Object estacionDestino = estaciones.obtenerInformacion(destino);
                if (estacionDestino != null) {
                    exito = conexionEstaciones.insertarArco(estacionOrigen, estacionDestino, km);
                }
            }
            escribir(exito, origen + ";" + destino + ";" + km, 'R');
        } else {
            salida.write("Error: formato de inserción de riel incorrecto.\n");
        }

        return exito;
    }

    public static boolean insertarEstacionSegunCadena(StringTokenizer cadena) throws IOException {
        String nombre, domicilio = "";
        boolean exito = false;
        int length = cadena.countTokens();
        if (length == 7) {
            int cantVias, cantPlataformas;
            nombre = cadena.nextToken();
            for (int i = 0; i < 4; i++) {
                if (i < 3) {
                    domicilio = domicilio + cadena.nextToken() + ";";
                } else {
                    domicilio = domicilio + cadena.nextToken();
                }
            }
            cantVias = Integer.parseInt(cadena.nextToken());
            cantPlataformas = Integer.parseInt(cadena.nextToken());
            Estacion estacionAAgregar = new Estacion(nombre, domicilio, cantVias, cantPlataformas);
            exito = estaciones.insertar(estacionAAgregar.getNombre(), estacionAAgregar);
            if (exito) {
                conexionEstaciones.insertarVertice(estacionAAgregar);
            }
            escribir(exito, estacionAAgregar, 'E');
        } else {
            salida.write("Error: formato de inserción de estación incorrecto.\n");
        }
        return exito;
    }

    public static boolean insertarTrenSegunCadena(StringTokenizer cadena) throws IOException {
        boolean exito = false;
        int length = cadena.countTokens();
        if (length == 5) {
            int id, cantVagPas, cantVagCarga;
            String tipoPropulsion, lineaAsignada;
            id = Integer.parseInt(cadena.nextToken());
            tipoPropulsion = cadena.nextToken();
            cantVagPas = Integer.parseInt(cadena.nextToken());
            cantVagCarga = Integer.parseInt(cadena.nextToken());
            lineaAsignada = cadena.nextToken();
            if (hashMap.containsKey(lineaAsignada) || lineaAsignada.equals("libre")) {
                Tren trenAAgregar = new Tren(id, tipoPropulsion, cantVagPas, cantVagCarga, lineaAsignada);
                exito = trenes.insertar(id, trenAAgregar);
                escribir(exito, trenAAgregar, 'T');
            } else {
                salida.write("Error: línea inexistente.\n");
            }

        } else {
            salida.write("Error: formato de inserción de tren incorrecto.\n");
        }
        return exito;
    }

    public static boolean insertarLineaSegunCadena(StringTokenizer cadena) throws IOException {
        int length = cadena.countTokens();
        if (length != 0) {
            String linea = cadena.nextToken();
            String aux = cadena.nextToken();
            Estacion estacionAux;
            Lista lineaAAgregar = new Lista();
            if (hashMap.containsKey(linea)) {
                lineaAAgregar = hashMap.get(linea);
            } else {
                hashMap.put(linea, lineaAAgregar);
            }
            while (cadena.hasMoreTokens()) {
                estacionAux = (Estacion) estaciones.obtenerInformacion(aux);
                if (estacionAux != null) {
                    lineaAAgregar.insertar(estacionAux, lineaAAgregar.longitud() + 1);
                }
                aux = cadena.nextToken();
            }
            escribir(true, linea, 'L');
        } else {
            salida.write("No se especificaron estaciones para agregar a la línea.\n");
        }
        return true;
    }

    public static void escribir(boolean exito, Object objAAgregar, char tipo) throws IOException {
        String obj = "";
        String terminacion = "o";
        String articulo = "El";
        switch (tipo) {
            case 'E':
                obj = "estación";
                terminacion = "a";
                articulo = "La";
                break;
            case 'T':
                obj = "tren";
                break;
            case 'R':
                obj = "riel";
                break;
            case 'L':
                obj = "línea";
                terminacion = "a";
                articulo = "La";
                break;
        }
        if (exito) {
            salida.write(articulo + " " + obj + " " + objAAgregar.toString() + " ha sido agregad" + terminacion + " con éxito.\n");
        } else {
            salida.write(articulo + " " + obj + " " + objAAgregar.toString() + " no ha podido ser agregad" + terminacion + ".\n");
        }
    }
}
