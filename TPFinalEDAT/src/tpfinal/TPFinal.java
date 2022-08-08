package tpfinal;

import estructuras.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
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
            salida = new FileWriter("test.txt");
            entrada = new FileReader("cargaInicial.txt");
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
            Tren tren;
            Estacion estacion;
            String nombreOrigen, nombreDestino;
            System.out.println("Ingrese la opción deseada:");
            seleccion = sc.nextInt();
            switch (seleccion) {
                case 1:
                    ABMTren();
                    break;
                case 2:
                    ABMEstacion();
                    break;
                case 3:
                    ABMLinea();
                    break;
                case 4:
                    ABMRiel();
                    break;
                case 5:
                    tren = buscarTren();
                    System.out.println(mostrarInformacionTren(tren));
                    break;
                case 6:
                    tren = buscarTren();
                    System.out.println(mostrarCiudadesVisitadasTren(tren));
                    break;
                case 7:
                    estacion = buscarEstacion();
                    System.out.println(mostrarInformacionEstacion(estacion));
                    break;
                case 8:
                    System.out.println("Ingrese el prefijo que desea buscar:");
                    sc.nextLine();
                    String prefijo = sc.nextLine();
                    System.out.println(mostrarEstacionesConPrefijo(prefijo));
                    break;
                case 9:
                    System.out.println("Ingrese el nombre de la estación origen");
                    sc.nextLine();
                    nombreOrigen = sc.nextLine();
                    System.out.println("Ingrese el nombre de la estación destino");
                    nombreDestino = sc.nextLine();
                    System.out.println(caminoPorMenosEstaciones(nombreOrigen, nombreDestino));
                    break;
                case 10:
                    System.out.println("Ingrese el nombre de la estación origen");
                    sc.nextLine();
                    nombreOrigen = sc.nextLine();
                    System.out.println("Ingrese el nombre de la estación destino");
                    nombreDestino = sc.nextLine();
                    System.out.println("Camino más corto según km: " + listarCaminoMasCortoSegunKm(nombreOrigen, nombreDestino).toString());
                    break;
                case 11:
                    System.out.println(mostrarSistema());
                    break;
            }
        } while (seleccion != 12);
        salida.write(mostrarSistema());
        salida.write("\nFin del log.");
        salida.close();
    }

    public static String mostrarSistema() {
        String line = "Estaciones:\n" + estaciones.toString() + "\nTrenes:\n" + trenes.toString() + "\nRieles:\n" + conexionEstaciones.toString() + "\nLíneas:\n" + lineaEstacion.toString() + "\n";
        return line;
    }

    public static Lista listarCaminoMasCortoSegunKm(String a, String b) {
        Estacion estacionA = (Estacion) estaciones.obtenerInformacion(a);
        Estacion estacionB = (Estacion) estaciones.obtenerInformacion(b);
        Lista retorno = new Lista();
        if (estacionA != null && estacionB != null) {
            retorno = conexionEstaciones.caminoMasLiviano(estacionA, estacionB);
        }
        return retorno;
    }

    public static Lista caminoPorMenosEstaciones(String nombreA, String nombreB) {
        Estacion origen = (Estacion) estaciones.obtenerInformacion(nombreA);
        Estacion destino = (Estacion) estaciones.obtenerInformacion(nombreB);
        return conexionEstaciones.caminoMasCorto(origen, destino);
    }

    //menu por pantalla
    public static void imprimirMenu() {
        System.out.println("-------------Menú de opciones---------------------");
        System.out.println("1. ABM Tren.");
        System.out.println("2. ABM Estación.");
        System.out.println("3. ABM línea.");
        System.out.println("4. ABM riel.");
        System.out.println("5. Mostrar información de tren.");
        System.out.println("6. Mostrar ciudades visitadas por un tren determinado según su línea.");
        System.out.println("7. Mostrar información de estación.");
        System.out.println("8. Mostrar todas las estaciones que empiezan por el prefijo ingresado.");
        System.out.println("9. Obtener camino de estación A a estación B que pase por menos estaciones.");
        System.out.println("10. Obtener camino de estación A a estación B que recorra la menor cantidad de kilometros.");
        System.out.println("11. Mostrar sistema.");
        System.out.println("12. Cerrar programa.\n");
    }

    //ABM Tren
    public static boolean ABMTren() throws IOException {
        boolean exito = false;
        System.out.println("Ingrese la opción deseada:");
        System.out.println("1. Alta");
        System.out.println("2. Baja");
        System.out.println("3. Modificación");
        int seleccion = sc.nextInt();
        System.out.println("Ingrese la ID del tren:");
        int idTren = sc.nextInt();
        switch (seleccion) {
            case 1:
                Tren tren;
                tren = pedirDatosTrenInsercion(idTren);
                exito = insertarTren(tren);
                break;
            case 2:
                exito = eliminarTren(idTren);
                break;
            case 3:
                Modificacion mod = pedirDatosTrenMod(idTren);
                exito = modificarTren(mod);
                break;
            default:
                System.out.println("Opción incorrecta.");
                break;
        }
        return exito;
    }

    //busqueda
    public static Tren buscarTren() {
        System.out.println("Ingrese la ID del tren:");
        int id = sc.nextInt();
        return (Tren) trenes.obtenerInformacion(id);
    }

    //insercion
    public static Tren pedirDatosTrenInsercion(int id) {
        int cantVagPas, cantVagCarga;
        String tipoPropulsion, lineaAsignada;
        boolean tieneLinea;
        Tren tren = null;
        if (!trenes.existeClave(id)) {
            System.out.println("Ingrese el tipo de propulsión:");
            System.out.println("1. Diesel");
            System.out.println("2. Gasolina");
            System.out.println("3. Híbrido");
            System.out.println("4. Electricidad");
            System.out.println("5. Fuel oil");
            int tipo = sc.nextInt();
            tipoPropulsion = "";
            switch (tipo) {
                case 1:
                    tipoPropulsion = "diesel";
                    break;
                case 2:
                    tipoPropulsion = "gasolina";
                    break;
                case 3:
                    tipoPropulsion = "híbrido";
                    break;
                case 4:
                    tipoPropulsion = "electricidad";
                    break;
                case 5:
                    tipoPropulsion = "fuel oil";
                    break;
                default:
                    System.out.println("Opción no existe.");
            }
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
        boolean exito = false;
        if (tren != null) {
            exito = trenes.insertar(tren.getId(), tren);
        }
        imprimirYEscribir(exito, tren, 'T', 'I');
        return exito;
    }

    //modificacion
    public static Modificacion pedirDatosTrenMod(int idTren) {
        int eleccion = 0;
        Tren tren = (Tren) trenes.obtenerInformacion(idTren);
        if (tren != null) {
            System.out.println("Ingrese el campo que desea modificar:");
            System.out.println("1. Tipo de propulsión.");
            System.out.println("2. Cantidad de vagones para pasajeros.");
            System.out.println("3. Cantidad de vagones para carga.");
            System.out.println("4. Linea asignada.");
            eleccion = sc.nextInt();
        } else {
            System.out.println("Error: tren inexistente.");
        }
        Modificacion mod = new Modificacion(tren, eleccion);
        return mod;
    }

    public static boolean modificarTren(Modificacion mod) throws IOException {
        Tren tren = (Tren) mod.getObjAModificar();
        int eleccion = mod.getCampoAModificar();
        boolean exito = true;
        if (tren != null) {
            switch (eleccion) {
                case 1:
                    System.out.println("Ingrese el tipo de propulsión:");
                    System.out.println("1. Diesel");
                    System.out.println("2. Gasolina");
                    System.out.println("3. Híbrido");
                    System.out.println("4. Electricidad");
                    System.out.println("5. Fuel oil");
                    int tipo = sc.nextInt();
                    String tipoPropulsion = "";
                    switch (tipo) {
                        case 1:
                            tipoPropulsion = "diesel";
                            break;
                        case 2:
                            tipoPropulsion = "gasolina";
                            break;
                        case 3:
                            tipoPropulsion = "híbrido";
                            break;
                        case 4:
                            tipoPropulsion = "electricidad";
                            break;
                        case 5:
                            tipoPropulsion = "fuel oil";
                            break;
                        default:
                            System.out.println("Opción no existe.");
                            exito = false;
                    }
                    tren.setTipoPropulsion(tipoPropulsion);
                    break;
                case 2:
                    System.out.println("Ingrese la cantidad de vagones para pasajeros:");
                    int cantVagPas = sc.nextInt();
                    tren.setCantVagonesPasajeros(cantVagPas);
                    break;
                case 3:
                    System.out.println("Ingrese la cantidad de vagones para carga:");
                    int cantVagCarga = sc.nextInt();
                    tren.setCantVagonesCarga(cantVagCarga);
                    break;
                case 4:
                    System.out.println("Ingrese la línea asignada:");
                    String linea = sc.nextLine();
                    if (!lineaEstacion.containsKey(linea)) {
                        exito = false;
                    } else {
                        tren.setLinea(linea);
                    }
                    break;
            }
        } else {
            exito = false;
        }
        imprimirYEscribir(exito, tren, 'T', 'M');
        return exito;
    }

    //eliminacion
    public static boolean eliminarTren(int idTren) throws IOException {
        boolean exito = false;
        if (trenes.existeClave(idTren)) {
            exito = trenes.eliminar(idTren);
        }
        imprimirYEscribir(exito, idTren, 'T', 'E');
        return exito;
    }

    //ABM Estacion
    public static boolean ABMEstacion() throws IOException {
        boolean exito = false;
        System.out.println("Ingrese la opción deseada:");
        System.out.println("1. Alta");
        System.out.println("2. Baja");
        System.out.println("3. Modificación");
        int seleccion = sc.nextInt();
        System.out.println("Ingrese el nombre de la estación:");
        sc.nextLine();
        String nombreEstacion = sc.nextLine();
        switch (seleccion) {
            case 1:
                Estacion estacion = pedirDatosEstacionInsercion(nombreEstacion);
                exito = insertarEstacion(estacion);
                break;
            case 2:
                exito = eliminarEstacion(nombreEstacion);
                break;
            case 3:
                Modificacion mod = pedirDatosEstacionMod(nombreEstacion);
                exito = modificarEstacion(mod);
                break;
            default:
                System.out.println("Opción incorrecta.");
        }
        return exito;
    }

    //busqueda
    public static Estacion buscarEstacion() {
        System.out.println("Ingrese el nombre de la estación:");
        sc.nextLine();
        String nombre = sc.nextLine();
        return (Estacion) estaciones.obtenerInformacion(nombre);
    }

    //insercion 
    public static Estacion pedirDatosEstacionInsercion(String nombre) throws IOException {
        Estacion estacion = null;
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
        boolean exito = false;
        if (estacion != null) {
            exito = estaciones.insertar(estacion.getNombre(), estacion);
            conexionEstaciones.insertarVertice(estacion);
        }
        imprimirYEscribir(exito, estacion, 'E', 'I');
        return exito;
    }

    //modificacion
    public static Modificacion pedirDatosEstacionMod(String nombre) throws IOException {
        Estacion estacion;
        int eleccion = 0;
        estacion = (Estacion) estaciones.obtenerInformacion(nombre);
        if (estacion != null) {
            System.out.println("Ingrese el campo que desea modificar:");
            System.out.println("1. Domicilio.");
            System.out.println("2. Cantidad de vías.");
            System.out.println("3. Cantidad de plataformas.");
            eleccion = sc.nextInt();
        }
        Modificacion mod = new Modificacion(estacion, eleccion);
        return mod;
    }

    public static boolean modificarEstacion(Modificacion mod) throws IOException {
        Estacion estacion = (Estacion) mod.getObjAModificar();
        int eleccion = mod.getCampoAModificar();
        boolean exito = true;
        if (estacion != null) {
            switch (eleccion) {
                case 1:
                    String calle,
                     ciudad,
                     cp,
                     domicilio;
                    int numCalle;
                    System.out.println("Ingrese el nombre de la calle:");
                    sc.nextLine();
                    calle = sc.nextLine();
                    System.out.println("Ingrese el número de la calle:");
                    numCalle = sc.nextInt();
                    System.out.println("Ingrese la ciudad:");
                    sc.nextLine();
                    ciudad = sc.nextLine();
                    System.out.println("Ingrese el código postal:");
                    cp = sc.nextLine();
                    domicilio = calle + ";" + numCalle + ";" + ciudad + ";" + cp;
                    estacion.setDomicilio(domicilio);
                    break;
                case 2:
                    System.out.println("Ingrese la cantidad de vías:");
                    int cantVias = sc.nextInt();
                    estacion.setCantVias(cantVias);
                    break;
                case 3:
                    System.out.println("Ingrese la cantidad de plataformas:");
                    int cantPlataformas = sc.nextInt();
                    estacion.setCantPlat(cantPlataformas);
                    break;
                default:
                    System.out.println("Opción no existe.");
                    exito = false;
            }
        } else {
            exito = false;
        }
        imprimirYEscribir(exito, estacion, 'E', 'M');
        return exito;
    }

    //eliminacion
    public static boolean eliminarEstacion(String nombre) throws IOException {
        boolean exito = false;
        if (estaciones.obtenerInformacion(nombre) != null) {
            Estacion estacion = (Estacion) estaciones.obtenerInformacion(nombre);
            conexionEstaciones.eliminarVertice(estacion);
            eliminarEstacionDeLinea(estacion);
            exito = estaciones.eliminar(nombre);
        }
        imprimirYEscribir(exito, nombre, 'E', 'E');
        return exito;
    }

    public static void eliminarEstacionDeLinea(Estacion estacion) {
        Set setLineas = lineaEstacion.keySet();
        Iterator iterador = setLineas.iterator();
        String linea;
        while (iterador.hasNext()) {
            linea = (String) iterador.next();
            Lista estacionesPorLinea = lineaEstacion.get(linea);
            estacionesPorLinea.eliminar(estacionesPorLinea.localizar(estacion));
        }
    }

    //ABM Riel
    public static boolean ABMRiel() throws IOException {
        boolean exito = false;
        String nombreEstacionA, nombreEstacionB;
        int km;
        System.out.println("Ingrese la opción deseada:");
        System.out.println("1. Alta");
        System.out.println("2. Baja");
        System.out.println("3. Modificación");
        int seleccion = sc.nextInt();
        System.out.println("Ingrese el nombre de la estación A:");
        sc.nextLine();
        nombreEstacionA = sc.nextLine();
        System.out.println("Ingrese el nombre de la estación B:");
        nombreEstacionB = sc.nextLine();
        switch (seleccion) {
            case 1:
                System.out.println("Ingrese los km entre la estación A y B:");
                km = sc.nextInt();
                exito = insertarRiel(nombreEstacionA, nombreEstacionB, km);
                break;
            case 2:
                exito = eliminarRiel(nombreEstacionA, nombreEstacionB);
                break;
            case 3:
                System.out.println("Ingrese los km entre la estación A y B:");
                km = sc.nextInt();
                exito = modificarRiel(nombreEstacionA, nombreEstacionB, km);
                break;
        }
        return exito;
    }

    //insercion
    public static boolean insertarRiel(String nombreA, String nombreB, int km) throws IOException {
        Estacion a = (Estacion) estaciones.obtenerInformacion(nombreA);
        Estacion b = (Estacion) estaciones.obtenerInformacion(nombreB);
        boolean exito = conexionEstaciones.insertarArco(a, b, km);
        imprimirYEscribir(exito, nombreA + ";" + nombreB + ";" + km, 'R', 'I');
        return exito;
    }

    //modificacion
    public static boolean modificarRiel(String nombreEstacionA, String nombreEstacionB, int km) throws IOException {
        boolean exito = false;
        Estacion estacionA = (Estacion) estaciones.obtenerInformacion(nombreEstacionA);
        Estacion estacionB = (Estacion) estaciones.obtenerInformacion(nombreEstacionB);
        if (estacionA != null && estacionB != null) {
            exito = true;
            //modificar arco
            conexionEstaciones.modificarArco(estacionA, estacionB, km);
        }
        imprimirYEscribir(exito, nombreEstacionA + ";" + nombreEstacionB + ";" + km, 'R', 'M');
        return exito;
    }

    //eliminacion
    public static boolean eliminarRiel(String nombreEstacionA, String nombreEstacionB) throws IOException {
        boolean exito = false;
        Estacion estacionA = (Estacion) estaciones.obtenerInformacion(nombreEstacionA);
        Estacion estacionB = (Estacion) estaciones.obtenerInformacion(nombreEstacionB);
        if (estacionA != null && estacionB != null) {
            exito = conexionEstaciones.eliminarArco(estacionA, estacionB);
        }
        imprimirYEscribir(exito, nombreEstacionA + ";" + nombreEstacionB, 'R', 'E');
        return exito;
    }

    //ABM Linea
    public static boolean ABMLinea() throws IOException {
        boolean exito = false;
        System.out.println("Ingrese la opción deseada:");
        System.out.println("1. Alta");
        System.out.println("2. Baja");
        System.out.println("3. Modificación");
        int seleccion = sc.nextInt();
        System.out.println("Ingrese el nombre de la línea:");
        sc.nextLine();
        String linea = sc.nextLine();
        switch (seleccion) {
            case 1:
                exito = pedirDatosLineaInsercion(linea);
                break;
            case 2:
                exito = eliminarLinea(linea);
                break;
            case 3:
                exito = modificarLinea(linea);
                break;
        }
        return exito;
    }

    //insercion
    public static boolean pedirDatosLineaInsercion(String linea) throws IOException {
        boolean exito = false;
        if (lineaEstacion.containsKey(linea)) {
            System.out.println("Error: la línea ya existe en el sistema.");
        } else {
            System.out.println("Ingrese el nombre de las estaciones que pertenecerán a la línea. Si no desea agregar más estaciones, ingrese #:");
            String nombreEstacion;
            Lista estacionesLinea = new Lista();
            lineaEstacion.put(linea, estacionesLinea);
            nombreEstacion = sc.nextLine();
            while (!nombreEstacion.equals("#")) {
                Estacion estacion = (Estacion) estaciones.obtenerInformacion(nombreEstacion);
                if (estacion == null) {
                    imprimirYEscribir("Error: estación inexistente.");
                } else {
                    estacionesLinea.insertar(estacion, estacionesLinea.longitud() + 1);
                }
                System.out.println("Ingrese otra estación o # para detener:");
                nombreEstacion = sc.nextLine();
            }
            exito = true;
        }
        imprimirYEscribir(exito, linea, 'L', 'I');
        return exito;
    }

    //modificacion
    public static boolean modificarLinea(String linea) throws IOException {
        Estacion estacion;
        boolean exito = true;
        if (lineaEstacion.containsKey(linea)) {
            System.out.println("Ingrese el nombre de las estaciones que pertenecerán a la línea. Si no desea agregar más estaciones, ingrese #:");
            String nombreEstacion;
            Lista estacionesLinea = new Lista();
            lineaEstacion.put(linea, estacionesLinea);
            sc.nextLine();
            nombreEstacion = sc.nextLine();
            while (!nombreEstacion.equals("#")) {
                estacion = (Estacion) estaciones.obtenerInformacion(nombreEstacion);
                if (estacion == null) {
                    System.out.println("Error: estación inexistente.");
                } else {
                    estacionesLinea.insertar(estacion, estacionesLinea.longitud() + 1);
                }
                System.out.println("Ingrese otra estación o # para detener:");
                nombreEstacion = sc.nextLine();
            }
        } else {
            System.out.println("Error: línea inexistente.");
            exito = false;
        }
        imprimirYEscribir(exito, linea, 'L', 'M');
        return exito;
    }

    //eliminacion
    public static boolean eliminarLinea(String linea) throws IOException {
        boolean exito = false;
        if (lineaEstacion.containsKey(linea)) {
            lineaEstacion.remove(linea);
            exito = true;
        }
        imprimirYEscribir(exito, linea, 'L', 'E');
        return exito;
    }

    public static String mostrarEstacionesConPrefijo(String prefijo) {
        Lista nombresEstaciones = estaciones.listarClaves();
        Lista estacionesConPrefijo = new Lista();
        int length = nombresEstaciones.longitud();
        for (int i = 1; i <= length; i++) {
            String nombre = (String) nombresEstaciones.recuperar(i);
            if (nombre.startsWith(prefijo)) {
                estacionesConPrefijo.insertar(nombre, estacionesConPrefijo.longitud() + 1);
            }
        }
        return estacionesConPrefijo.toString();
    }

    public static String mostrarInformacionEstacion(Estacion estacion) {
        String informacionEstacion = "Estación inexistente.";
        if (estacion != null) {
            informacionEstacion = estacion.toString();
        }
        return informacionEstacion;
    }

    public static String mostrarInformacionTren(Tren tren) {
        String retorno = "Tren inexistente.";
        if (tren != null) {
            retorno = tren.toString();
        }
        return retorno;
    }

    public static String mostrarCiudadesVisitadasTren(Tren tren) {
        Lista ciudades = new Lista();
        if (tren != null) {
            String linea = tren.getLinea();
            Lista estacionesLinea = lineaEstacion.get(linea);
            int length = estacionesLinea.longitud();
            for (int i = 1; i <= length; i++) {
                Estacion estacionTemp = (Estacion) estacionesLinea.recuperar(i);
                String domicilio = estacionTemp.getDomicilio();
                int posLim = domicilio.indexOf(";");
                String ciudad = domicilio.substring(0, posLim);
                ciudades.insertar(ciudad, i);
            }
        }
        return ciudades.toString();
    }

    //métodos para carga inicial
    public static void cargaInicial() throws IOException {
        salida.write("Inicio del log.\n\n");
        salida.write("INICIO CARGA INICIAL");
        salida.write("\n42 ESTACIONES 41 RIELES 3 LINEAS 20 TRENES\n");
        try ( BufferedReader input = new BufferedReader(entrada)) {
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

    public static void imprimirYEscribir(String line) throws IOException {
        System.out.println(line);
        salida.write(line);
    }

    public static void imprimirYEscribir(boolean exito, Object objAAgregar, char tipo, char operacion) throws IOException {
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
                if (lineaEstacion.containsKey((String) objAAgregar)) {
                    estacionesLinea = "Estaciones: " + lineaEstacion.get((String) objAAgregar).toString();
                } else {
                    estacionesLinea = "";
                }
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
        System.out.println(retorno);
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
