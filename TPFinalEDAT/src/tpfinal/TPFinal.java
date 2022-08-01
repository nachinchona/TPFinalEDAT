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
    private static HashMap<String, Lista> lineaEstacion = new HashMap<>();
    private static Scanner sc = new Scanner(System.in, "ISO_8859_1");
    
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
        int seleccion;

        //carga de lote txt
        cargaInicial();

        //menu
        imprimirMenu();
        do {
            System.out.println("Ingrese la opción deseada:");
            seleccion = sc.nextInt();
            switch (seleccion) {
                case 1:
                    Tren tren = pedirDatosTren();
                    insertarTren(tren);
                    break;
                case 2:
                    Estacion estacion = pedirDatosEstacion();
                    insertarEstacion(estacion);
                    break;
                case 3:
                    pedirDatosLinea();
                    break;
                case 4:
                    pedirDatosRiel();
                    break;
            }
        } while (seleccion != 20);
        salida.write("\nFin del log.");
        salida.close();
    }

    public static void pedirDatosRiel() {
        String estacionA, estacionB;
        System.out.println("Ingrese el nombre de la estación A");
        estacionA = sc.nextLine();
        
    }

    public static void pedirDatosLinea() throws IOException {
        String linea;
        System.out.println("Ingrese el nombre de la línea que desea agregar:");
        linea = sc.next();
        if (lineaEstacion.containsKey(linea)) {
            System.out.println("Error: la línea ya existe en el sistema.");
        } else {
            System.out.println("Ingrese el nombre de las estaciones que pertenecerán a la línea. Si no desea agregar más estaciones, ingrese #:");
            String nombreEstacion;
            Lista estacionesLinea = new Lista();
            lineaEstacion.put(linea, estacionesLinea);
            sc.nextLine();
            nombreEstacion = sc.nextLine();
            while (!nombreEstacion.equals("#")) {
                Estacion estacion = (Estacion) estaciones.obtenerInformacion(nombreEstacion);
                if (estacion == null) {
                    System.out.println("Error: estación inexistente.");
                } else {
                    estacionesLinea.insertar(estacion, estacionesLinea.longitud() + 1);
                }
                System.out.println("Ingrese otra estación o # para detener:");
                nombreEstacion = sc.nextLine();
            }
        }
        escribir(true, linea, 'L', 'I');
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
        System.out.println("20. Cerrar programa.\n");
    }

    public static Estacion pedirDatosEstacion() throws IOException {
        String nombre;
        Estacion estacion = null;
        System.out.println("Ingrese el nombre de la estación");
        sc.nextLine();
        nombre = sc.nextLine();
        if (estaciones.obtenerInformacion(nombre) != null) {
            System.out.println("Error: estación " + nombre + " ya existe en el sistema.");
        } else {
            System.out.println("Ingrese el nombre de la calle donde se encuentra la estación:");
            String calle = sc.nextLine();
            System.out.println("Ingrese el número de domicilio:");
            int numCalle = sc.nextInt();
            System.out.println("Ingrese la ciudad:");
            sc.nextLine();
            String ciudad = sc.nextLine();
            System.out.println("Ingrese el código postal:");
            int cp = sc.nextInt();
            System.out.println("Ingrese la cantidad de vías:");
            int cantVias = sc.nextInt();
            System.out.println("Ingrese la cantidad de plataformas:");
            int cantPlataformas = sc.nextInt();
            String domicilio = calle + ";" + numCalle + ";" + ciudad + ";" + cp;
            estacion = new Estacion(nombre, domicilio, cantVias, cantPlataformas);
        }
        return estacion;
    }

    public static boolean insertarEstacion(Estacion estacion) throws IOException {
        boolean exito = estacion == null || estaciones.insertar(estacion.getNombre(), estacion);
        System.out.println(escribir(exito, estacion, 'E', 'I'));
        return exito;
    }

    public static Tren pedirDatosTren() {
        int id, cantVagPas, cantVagCarga;
        String tipoPropulsion, lineaAsignada;
        boolean tieneLinea;
        Tren tren = null;
        System.out.println("Ingrese el id del tren:");
        id = sc.nextInt();
        if (trenes.obtenerInformacion(id) == null) {
            System.out.println("Inserte el tipo de propulsión del tren:");
            tipoPropulsion = sc.next();
            System.out.println("Ingrese la cantidad de vagones para pasajeros:");
            cantVagPas = sc.nextInt();
            System.out.println("Ingrese la cantidad de vagones para carga:");
            cantVagCarga = sc.nextInt();
            System.out.println("¿Tiene línea asignada? Y/N");
            char letra = sc.next().charAt(0);
            tieneLinea = letra == 'Y' || letra == 'y';
            if (tieneLinea) {
                System.out.println("Ingrese la línea del tren:");
                lineaAsignada = sc.next();
                while (!lineaEstacion.containsKey(lineaAsignada)) {
                    System.out.println("Línea inexistente. Escriba @ si desea agregar dicha línea o escriba # si desea ingresar otra:");
                    char seleccionLetra = sc.next().charAt(0);
                    boolean sigue = true;
                    while (sigue || (seleccionLetra != '@' || seleccionLetra != '#')) {
                        if (seleccionLetra == '@') {
                            //insertar linea
                            lineaEstacion.put(lineaAsignada, null);
                            sigue = false;
                        } else {
                            if (seleccionLetra == '#') {
                                System.out.println("Ingrese la línea del tren:");
                                lineaAsignada = sc.nextLine();
                                sigue = false;
                            } else {
                                System.out.println("Opción inválida. Escriba @ si desea agregar dicha línea o escriba # si desea ingresar otra:");
                            }
                        }
                    }
                }
            } else {
                lineaAsignada = "libre";
            }
            tren = new Tren(id, tipoPropulsion, cantVagPas, cantVagCarga, lineaAsignada);
        } else {
            System.out.println("Error: tren ya existe en el sistema.");
        }
        return tren;
    }

    public static boolean insertarTren(Tren tren) throws IOException {
        boolean exito = tren == null || trenes.insertar(tren.getId(), tren);
        System.out.println(escribir(exito, tren, 'T', 'I'));

        return exito;
    }

    //métodos para carga inicial
    public static void cargaInicial() throws IOException  {
        salida.write("Inicio del log.\n\n");
        salida.write("INICIO CARGA INICIAL");
        salida.write("\n42 ESTACIONES 41 RIELES 3 LINEAS 20 TRENES\n");
        try (BufferedReader input = new BufferedReader(entrada)) {
            String line;
            while ((line = input.readLine()) != null) {
                leerCadena(line);
            }
        }
        salida.write("\nFIN CARGA INICIAL\n\n");
    }

    public static void leerCadena(String line) throws IOException {
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
            Estacion estacionOrigen = (Estacion) estaciones.obtenerInformacion(origen);
            if (estacionOrigen != null) {
                Estacion estacionDestino = (Estacion) estaciones.obtenerInformacion(destino);
                if (estacionDestino != null) {
                    exito = conexionEstaciones.insertarArco(estacionOrigen, estacionDestino, km);
                }
            }
            escribir(exito, origen + ";" + destino + ";" + km, 'R', 'I');
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
            escribir(exito, estacionAAgregar, 'E', 'I');
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
            if (lineaEstacion.containsKey(lineaAsignada) || lineaAsignada.equals("libre")) {
                Tren trenAAgregar = new Tren(id, tipoPropulsion, cantVagPas, cantVagCarga, lineaAsignada);
                exito = trenes.insertar(id, trenAAgregar);
                escribir(exito, trenAAgregar, 'T', 'I');
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
            if (lineaEstacion.containsKey(linea)) {
                lineaAAgregar = lineaEstacion.get(linea);
            } else {
                lineaEstacion.put(linea, lineaAAgregar);
            }
            while (cadena.hasMoreTokens()) {
                estacionAux = (Estacion) estaciones.obtenerInformacion(aux);
                if (estacionAux != null) {
                    lineaAAgregar.insertar(estacionAux, lineaAAgregar.longitud() + 1);
                }
                aux = cadena.nextToken();
            }
            escribir(true, linea, 'L', 'I');
        } else {
            salida.write("No se especificaron estaciones para agregar a la línea.\n");
        }
        return true;
    }

    public static String escribir(boolean exito, Object objAAgregar, char tipo, char operacion) throws IOException {
        String obj = "";
        String terminacion = "o";
        String articulo = "El";
        String operacionCadena = "";
        String retorno;
        String estacionesLinea = "";
        switch (operacion) {
            case 'I':
                operacionCadena = "agregad";
                break;
            case 'M':
                operacionCadena = "modificad";
                break;
            case 'E':
                operacionCadena = "eliminad";
                break;
        }
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
                estacionesLinea = "Estaciones: " + lineaEstacion.get((String) objAAgregar).toString();
                break;
        }
        if (objAAgregar != null) {
            if (exito) {
                if (tipo != 'L') {
                    retorno = articulo + " " + obj + " " + objAAgregar.toString() + " ha sido " + operacionCadena + terminacion + " con éxito.\n";
                    salida.write(retorno);
                } else {
                    retorno = articulo + " " + obj + " " + objAAgregar.toString() + " ha sido " + operacionCadena + terminacion + " con éxito. " + estacionesLinea + "\n";
                    salida.write(retorno);
                }
            } else {
                retorno = articulo + " " + obj + " " + objAAgregar.toString() + " no ha podido ser " + operacionCadena + terminacion + ".\n";
                salida.write(retorno);
            }
        } else {
            retorno = "Error: no se pudo realizar la operación " + operacion + " para " + obj + " correctamente\n";
            salida.write(retorno);
        }
        return retorno;
    }
}
